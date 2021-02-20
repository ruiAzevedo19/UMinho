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

    var data = {"OkPercent": 75.0, "KoPercent": 25.0};
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
    createTable($("#apdexTable"), {"supportsControllersDiscrimination": true, "overall": {"data": [0.31825, 500, 1500, "Total"], "isController": false}, "titles": ["Apdex", "T (Toleration threshold)", "F (Frustration threshold)", "Label"], "items": [{"data": [0.4475, 500, 1500, "GET /login"], "isController": false}, {"data": [0.44125, 500, 1500, "POST /graphql - Obtain Groups List"], "isController": false}, {"data": [0.0, 500, 1500, "POST /graphql - Create User"], "isController": false}, {"data": [0.045, 500, 1500, "POST /graphql - Obtain Updated Users List"], "isController": false}, {"data": [0.0, 500, 1500, "POST /graphql - Login"], "isController": false}, {"data": [0.51125, 500, 1500, "POST /graphql - Obtain Recent Pages"], "isController": false}, {"data": [0.46, 500, 1500, "POST /graphql - Obtain Users List"], "isController": false}, {"data": [0.44, 500, 1500, "GET /a - Administration Page"], "isController": false}, {"data": [0.62, 500, 1500, "GET /"], "isController": false}, {"data": [0.2175, 500, 1500, "POST /graphql - Obtain System Info"], "isController": false}]}, function(index, item){
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
    createTable($("#statisticsTable"), {"supportsControllersDiscrimination": true, "overall": {"data": ["Total", 4000, 1000, 25.0, 12136.061500000003, 49, 79701, 711.5, 64416.0, 76704.85, 79149.99, 24.646628957324364, 72.74069238349846, 16.649616205774706], "isController": false}, "titles": ["Label", "#Samples", "FAIL", "Error %", "Average", "Min", "Max", "Median", "90th pct", "95th pct", "99th pct", "Transactions/s", "Received", "Sent"], "items": [{"data": ["GET /login", 400, 0, 0.0, 1499.9799999999996, 63, 3928, 2277.5, 2939.0, 2951.95, 3926.99, 35.23918597480398, 70.89133116025019, 4.439311514404017], "isController": false}, {"data": ["POST /graphql - Obtain Groups List", 400, 200, 50.0, 1387.5900000000001, 49, 62763, 183.0, 295.90000000000003, 5610.95, 48873.16000000003, 6.231403156205698, 3.575145853780125, 4.503162437101774], "isController": false}, {"data": ["POST /graphql - Create User", 400, 200, 50.0, 36523.84499999998, 55, 78070, 8131.0, 77207.0, 77215.0, 78066.74, 4.9830576041459045, 2.905161513354594, 6.50843771800877], "isController": false}, {"data": ["POST /graphql - Obtain Updated Users List", 400, 200, 50.0, 1759.7775000000008, 52, 10932, 292.0, 4391.6, 7070.749999999993, 10883.770000000015, 4.956015363647627, 87.82779153373188, 3.8138086978069636], "isController": false}, {"data": ["POST /graphql - Login", 400, 0, 0.0, 70652.60500000013, 63244, 79701, 66859.5, 79149.9, 79217.95, 79252.99, 4.964873519847082, 5.4618457227614625, 3.4618356378621256], "isController": false}, {"data": ["POST /graphql - Obtain Recent Pages", 400, 0, 0.0, 937.6575000000003, 232, 1153, 968.0, 1081.0, 1087.0, 1143.7200000000003, 175.28483786152498, 170.4918930762489, 158.851884312007], "isController": false}, {"data": ["POST /graphql - Obtain Users List", 400, 200, 50.0, 218.79999999999993, 51, 601, 190.0, 405.4000000000002, 553.95, 592.0, 258.06451612903226, 190.27217741935485, 198.58870967741936], "isController": false}, {"data": ["GET /a - Administration Page", 400, 0, 0.0, 5302.872499999998, 114, 13001, 4519.5, 12863.8, 12883.0, 12995.99, 29.40311673037342, 58.404237724198765, 15.850117612466923], "isController": false}, {"data": ["GET /", 400, 0, 0.0, 2119.6425, 124, 8046, 527.0, 7957.9, 8002.8, 8040.96, 44.84304932735426, 126.73416479820628, 5.430213004484305], "isController": false}, {"data": ["POST /graphql - Obtain System Info", 400, 200, 50.0, 957.8450000000001, 56, 9127, 517.5, 1370.6000000000001, 2954.95, 9113.0, 37.216226274655746, 37.2525702456271, 29.693024283587647], "isController": false}]}, function(index, item){
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
    createTable($("#errorsTable"), {"supportsControllersDiscrimination": false, "titles": ["Type of error", "Number of errors", "% in errors", "% in all samples"], "items": [{"data": ["Test failed: text expected not to contain /&quot;errors&quot;:/", 1000, 100.0, 25.0], "isController": false}]}, function(index, item){
        switch(index){
            case 2:
            case 3:
                item = item.toFixed(2) + '%';
                break;
        }
        return item;
    }, [[1, 1]]);

        // Create top5 errors by sampler
    createTable($("#top5ErrorsBySamplerTable"), {"supportsControllersDiscrimination": false, "overall": {"data": ["Total", 4000, 1000, "Test failed: text expected not to contain /&quot;errors&quot;:/", 1000, null, null, null, null, null, null, null, null], "isController": false}, "titles": ["Sample", "#Samples", "#Errors", "Error", "#Errors", "Error", "#Errors", "Error", "#Errors", "Error", "#Errors", "Error", "#Errors"], "items": [{"data": [], "isController": false}, {"data": ["POST /graphql - Obtain Groups List", 400, 200, "Test failed: text expected not to contain /&quot;errors&quot;:/", 200, null, null, null, null, null, null, null, null], "isController": false}, {"data": ["POST /graphql - Create User", 400, 200, "Test failed: text expected not to contain /&quot;errors&quot;:/", 200, null, null, null, null, null, null, null, null], "isController": false}, {"data": ["POST /graphql - Obtain Updated Users List", 400, 200, "Test failed: text expected not to contain /&quot;errors&quot;:/", 200, null, null, null, null, null, null, null, null], "isController": false}, {"data": [], "isController": false}, {"data": [], "isController": false}, {"data": ["POST /graphql - Obtain Users List", 400, 200, "Test failed: text expected not to contain /&quot;errors&quot;:/", 200, null, null, null, null, null, null, null, null], "isController": false}, {"data": [], "isController": false}, {"data": [], "isController": false}, {"data": ["POST /graphql - Obtain System Info", 400, 200, "Test failed: text expected not to contain /&quot;errors&quot;:/", 200, null, null, null, null, null, null, null, null], "isController": false}]}, function(index, item){
        return item;
    }, [[0, 0]], 0);

});
