/*
   Licensed to the Apache Software Foundation (ASF) under one or more
   contributor license agreements.  See the NOTICE file distributed with
   this work for additional information regarding copyright ownership.
   The ASF licenses this file to You under the Apache License, Version 2.0
   (the "License"); you may not use this file except in compliance with
   the License.  You may obtain a copy of the License at

       http://www.apache.org/licenses/LICENSE-2.0

   Unless required by applicable law or agreed to in writing, software
   distributed under the License is distributed on an "AS IS" BASIS,
   WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
   See the License for the specific language governing permissions and
   limitations under the License.
*/
var showControllersOnly = false;
var seriesFilter = "";
var filtersOnlySampleSeries = true;

/*
 * Add header in statistics table to group metrics by category
 * format
 *
 */
function summaryTableHeader(header) {
    var newRow = header.insertRow(-1);
    newRow.className = "tablesorter-no-sort";
    var cell = document.createElement('th');
    cell.setAttribute("data-sorter", false);
    cell.colSpan = 1;
    cell.innerHTML = "Requests";
    newRow.appendChild(cell);

    cell = document.createElement('th');
    cell.setAttribute("data-sorter", false);
    cell.colSpan = 3;
    cell.innerHTML = "Executions";
    newRow.appendChild(cell);

    cell = document.createElement('th');
    cell.setAttribute("data-sorter", false);
    cell.colSpan = 7;
    cell.innerHTML = "Response Times (ms)";
    newRow.appendChild(cell);

    cell = document.createElement('th');
    cell.setAttribute("data-sorter", false);
    cell.colSpan = 1;
    cell.innerHTML = "Throughput";
    newRow.appendChild(cell);

    cell = document.createElement('th');
    cell.setAttribute("data-sorter", false);
    cell.colSpan = 2;
    cell.innerHTML = "Network (KB/sec)";
    newRow.appendChild(cell);
}

/*
 * Populates the table identified by id parameter with the specified data and
 * format
 *
 */
function createTable(table, info, formatter, defaultSorts, seriesIndex, headerCreator) {
    var tableRef = table[0];

    // Create header and populate it with data.titles array
    var header = tableRef.createTHead();

    // Call callback is available
    if(headerCreator) {
        headerCreator(header);
    }

    var newRow = header.insertRow(-1);
    for (var index = 0; index < info.titles.length; index++) {
        var cell = document.createElement('th');
        cell.innerHTML = info.titles[index];
        newRow.appendChild(cell);
    }

    var tBody;

    // Create overall body if defined
    if(info.overall){
        tBody = document.createElement('tbody');
        tBody.className = "tablesorter-no-sort";
        tableRef.appendChild(tBody);
        var newRow = tBody.insertRow(-1);
        var data = info.overall.data;
        for(var index=0;index < data.length; index++){
            var cell = newRow.insertCell(-1);
            cell.innerHTML = formatter ? formatter(index, data[index]): data[index];
        }
    }

    // Create regular body
    tBody = document.createElement('tbody');
    tableRef.appendChild(tBody);

    var regexp;
    if(seriesFilter) {
        regexp = new RegExp(seriesFilter, 'i');
    }
    // Populate body with data.items array
    for(var index=0; index < info.items.length; index++){
        var item = info.items[index];
        if((!regexp || filtersOnlySampleSeries && !info.supportsControllersDiscrimination || regexp.test(item.data[seriesIndex]))
                &&
                (!showControllersOnly || !info.supportsControllersDiscrimination || item.isController)){
            if(item.data.length > 0) {
                var newRow = tBody.insertRow(-1);
                for(var col=0; col < item.data.length; col++){
                    var cell = newRow.insertCell(-1);
                    cell.innerHTML = formatter ? formatter(col, item.data[col]) : item.data[col];
                }
            }
        }
    }

    // Add support of columns sort
    table.tablesorter({sortList : defaultSorts});
}

$(document).ready(function() {

    // Customize table sorter default options
    $.extend( $.tablesorter.defaults, {
        theme: 'blue',
        cssInfoBlock: "tablesorter-no-sort",
        widthFixed: true,
        widgets: ['zebra']
    });

    var data = {"OkPercent": 92.94, "KoPercent": 7.06};
    var dataset = [
        {
            "label" : "FAIL",
            "data" : data.KoPercent,
            "color" : "#FF6347"
        },
        {
            "label" : "PASS",
            "data" : data.OkPercent,
            "color" : "#9ACD32"
        }];
    $.plot($("#flot-requests-summary"), dataset, {
        series : {
            pie : {
                show : true,
                radius : 1,
                label : {
                    show : true,
                    radius : 3 / 4,
                    formatter : function(label, series) {
                        return '<div style="font-size:8pt;text-align:center;padding:2px;color:white;">'
                            + label
                            + '<br/>'
                            + Math.round10(series.percent, -2)
                            + '%</div>';
                    },
                    background : {
                        opacity : 0.5,
                        color : '#000'
                    }
                }
            }
        },
        legend : {
            show : true
        }
    });

    // Creates APDEX table
    createTable($("#apdexTable"), {"supportsControllersDiscrimination": true, "overall": {"data": [0.3382, 500, 1500, "Total"], "isController": false}, "titles": ["Apdex", "T (Toleration threshold)", "F (Frustration threshold)", "Label"], "items": [{"data": [0.747, 500, 1500, "GET /login"], "isController": false}, {"data": [0.173, 500, 1500, "POST /graphql - Obtain Groups List"], "isController": false}, {"data": [0.025, 500, 1500, "POST /graphql - Create User"], "isController": false}, {"data": [0.2, 500, 1500, "POST /graphql - Obtain Updated Users List"], "isController": false}, {"data": [0.0, 500, 1500, "POST /graphql - Login"], "isController": false}, {"data": [0.441, 500, 1500, "POST /graphql - Obtain Recent Pages"], "isController": false}, {"data": [0.309, 500, 1500, "POST /graphql - Obtain Users List"], "isController": false}, {"data": [0.381, 500, 1500, "GET /a - Administration Page"], "isController": false}, {"data": [0.767, 500, 1500, "GET /"], "isController": false}, {"data": [0.339, 500, 1500, "POST /graphql - Obtain System Info"], "isController": false}]}, function(index, item){
        switch(index){
            case 0:
                item = item.toFixed(3);
                break;
            case 1:
            case 2:
                item = formatDuration(item);
                break;
        }
        return item;
    }, [[0, 0]], 3);

    // Create statistics table
    createTable($("#statisticsTable"), {"supportsControllersDiscrimination": true, "overall": {"data": ["Total", 5000, 353, 7.06, 8743.229600000004, 43, 68856, 1631.0, 25212.700000000128, 62261.9, 68453.89, 39.988163503602934, 309.8932091101034, 35.68977957524572], "isController": false}, "titles": ["Label", "#Samples", "FAIL", "Error %", "Average", "Min", "Max", "Median", "90th pct", "95th pct", "99th pct", "Transactions/s", "Received", "Sent"], "items": [{"data": ["GET /login", 500, 1, 0.2, 2734.3000000000006, 48, 57774, 129.5, 7384.000000000037, 8978.0, 56705.97, 8.127966707848366, 18.216773634095194, 0.9763085010403798], "isController": false}, {"data": ["POST /graphql - Obtain Groups List", 500, 39, 7.8, 4067.8399999999997, 43, 12983, 2855.0, 9493.900000000001, 10660.05, 11870.49, 5.478191320353672, 3.3386045232056185, 5.670569992111404], "isController": false}, {"data": ["POST /graphql - Create User", 500, 49, 9.8, 13154.848000000002, 45, 27404, 14461.0, 22220.9, 24414.45, 26515.58, 5.106991471324243, 3.1654968464327666, 8.26658335886829], "isController": false}, {"data": ["POST /graphql - Obtain Updated Users List", 500, 40, 8.0, 4544.0120000000015, 43, 15244, 3832.0, 10478.2, 11375.8, 13803.750000000007, 5.100531475379735, 212.98299243718697, 5.518735208458722], "isController": false}, {"data": ["POST /graphql - Login", 500, 5, 1.0, 50303.960000000036, 651, 68856, 62261.0, 68452.9, 68531.75, 68849.92, 7.199216725220296, 10.147450644869838, 4.977583438921846], "isController": false}, {"data": ["POST /graphql - Obtain Recent Pages", 500, 39, 7.8, 3846.715999999999, 44, 15385, 757.5, 13786.5, 14389.3, 15365.550000000001, 6.559527714004592, 7.679656752213841, 7.994168169891768], "isController": false}, {"data": ["POST /graphql - Obtain Users List", 500, 41, 8.2, 2832.3780000000024, 43, 15047, 1605.0, 8283.5, 9709.25, 13865.98, 6.176270767710456, 147.84467451053055, 6.6826767185473415], "isController": false}, {"data": ["GET /a - Administration Page", 500, 75, 15.0, 1192.9760000000017, 44, 54535, 905.0, 1714.0, 2697.1499999999983, 7530.700000000006, 7.688640802079008, 15.146352076317447, 6.547057845489074], "isController": false}, {"data": ["GET /", 500, 0, 0.0, 704.9140000000001, 94, 4396, 298.5, 2072.7000000000003, 4142.649999999977, 4374.0, 99.38382031405288, 315.4281346278076, 11.452432419002188], "isController": false}, {"data": ["POST /graphql - Obtain System Info", 500, 64, 12.8, 4050.351999999999, 44, 48257, 1127.0, 13186.700000000006, 13492.5, 13783.0, 6.943962224845497, 4.2155275675300325, 7.709968057773766], "isController": false}]}, function(index, item){
        switch(index){
            // Errors pct
            case 3:
                item = item.toFixed(2) + '%';
                break;
            // Mean
            case 4:
            // Mean
            case 7:
            // Median
            case 8:
            // Percentile 1
            case 9:
            // Percentile 2
            case 10:
            // Percentile 3
            case 11:
            // Throughput
            case 12:
            // Kbytes/s
            case 13:
            // Sent Kbytes/s
                item = item.toFixed(2);
                break;
        }
        return item;
    }, [[0, 0]], 0, summaryTableHeader);

    // Create error table
    createTable($("#errorsTable"), {"supportsControllersDiscrimination": false, "titles": ["Type of error", "Number of errors", "% in errors", "% in all samples"], "items": [{"data": ["502/Bad Gateway", 324, 91.78470254957507, 6.48], "isController": false}, {"data": ["Test failed: text expected not to contain /&quot;errors&quot;:/", 24, 6.798866855524079, 0.48], "isController": false}, {"data": ["403/Forbidden", 5, 1.4164305949008498, 0.1], "isController": false}]}, function(index, item){
        switch(index){
            case 2:
            case 3:
                item = item.toFixed(2) + '%';
                break;
        }
        return item;
    }, [[1, 1]]);

        // Create top5 errors by sampler
    createTable($("#top5ErrorsBySamplerTable"), {"supportsControllersDiscrimination": false, "overall": {"data": ["Total", 5000, 353, "502/Bad Gateway", 324, "Test failed: text expected not to contain /&quot;errors&quot;:/", 24, "403/Forbidden", 5, null, null, null, null], "isController": false}, "titles": ["Sample", "#Samples", "#Errors", "Error", "#Errors", "Error", "#Errors", "Error", "#Errors", "Error", "#Errors", "Error", "#Errors"], "items": [{"data": ["GET /login", 500, 1, "502/Bad Gateway", 1, null, null, null, null, null, null, null, null], "isController": false}, {"data": ["POST /graphql - Obtain Groups List", 500, 39, "502/Bad Gateway", 34, "Test failed: text expected not to contain /&quot;errors&quot;:/", 5, null, null, null, null, null, null], "isController": false}, {"data": ["POST /graphql - Create User", 500, 49, "502/Bad Gateway", 44, "Test failed: text expected not to contain /&quot;errors&quot;:/", 5, null, null, null, null, null, null], "isController": false}, {"data": ["POST /graphql - Obtain Updated Users List", 500, 40, "502/Bad Gateway", 35, "Test failed: text expected not to contain /&quot;errors&quot;:/", 5, null, null, null, null, null, null], "isController": false}, {"data": ["POST /graphql - Login", 500, 5, "502/Bad Gateway", 5, null, null, null, null, null, null, null, null], "isController": false}, {"data": ["POST /graphql - Obtain Recent Pages", 500, 39, "502/Bad Gateway", 39, null, null, null, null, null, null, null, null], "isController": false}, {"data": ["POST /graphql - Obtain Users List", 500, 41, "502/Bad Gateway", 37, "Test failed: text expected not to contain /&quot;errors&quot;:/", 4, null, null, null, null, null, null], "isController": false}, {"data": ["GET /a - Administration Page", 500, 75, "502/Bad Gateway", 70, "403/Forbidden", 5, null, null, null, null, null, null], "isController": false}, {"data": [], "isController": false}, {"data": ["POST /graphql - Obtain System Info", 500, 64, "502/Bad Gateway", 59, "Test failed: text expected not to contain /&quot;errors&quot;:/", 5, null, null, null, null, null, null], "isController": false}]}, function(index, item){
        return item;
    }, [[0, 0]], 0);

});
