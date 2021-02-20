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

    var data = {"OkPercent": 96.225, "KoPercent": 3.775};
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
    createTable($("#apdexTable"), {"supportsControllersDiscrimination": true, "overall": {"data": [0.1755, 500, 1500, "Total"], "isController": false}, "titles": ["Apdex", "T (Toleration threshold)", "F (Frustration threshold)", "Label"], "items": [{"data": [0.48125, 500, 1500, "GET /login"], "isController": false}, {"data": [0.0775, 500, 1500, "POST /graphql - Obtain Groups List"], "isController": false}, {"data": [0.0375, 500, 1500, "POST /graphql - Create User"], "isController": false}, {"data": [0.17125, 500, 1500, "POST /graphql - Obtain Updated Users List"], "isController": false}, {"data": [0.00625, 500, 1500, "POST /graphql - Login"], "isController": false}, {"data": [0.0825, 500, 1500, "POST /graphql - Obtain Recent Pages"], "isController": false}, {"data": [0.08875, 500, 1500, "POST /graphql - Obtain Users List"], "isController": false}, {"data": [0.405, 500, 1500, "GET /a - Administration Page"], "isController": false}, {"data": [0.32375, 500, 1500, "GET /"], "isController": false}, {"data": [0.08125, 500, 1500, "POST /graphql - Obtain System Info"], "isController": false}]}, function(index, item){
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
    createTable($("#statisticsTable"), {"supportsControllersDiscrimination": true, "overall": {"data": ["Total", 4000, 151, 3.775, 7647.445249999995, 47, 49163, 5052.5, 16722.0, 33974.299999999996, 47172.94, 39.41974140649637, 257.0681347902624, 34.66034657343898], "isController": false}, "titles": ["Label", "#Samples", "FAIL", "Error %", "Average", "Min", "Max", "Median", "90th pct", "95th pct", "99th pct", "Transactions/s", "Received", "Sent"], "items": [{"data": ["GET /login", 400, 0, 0.0, 4567.879999999998, 51, 18153, 974.5, 12633.6, 16019.95, 17733.130000000005, 18.590816136828405, 41.71754631727552, 2.2330765476854437], "isController": false}, {"data": ["POST /graphql - Obtain Groups List", 400, 21, 5.25, 5072.127499999995, 49, 13051, 4406.5, 10532.900000000001, 11373.65, 12551.060000000001, 4.576920876480347, 2.8238015082384575, 4.651117054751416], "isController": false}, {"data": ["POST /graphql - Create User", 400, 24, 6.0, 11717.047499999988, 53, 34403, 10611.0, 22880.7, 23884.5, 24954.710000000003, 4.665158265494157, 2.9381727121772294, 7.46270424646031], "isController": false}, {"data": ["POST /graphql - Obtain Updated Users List", 400, 25, 6.25, 4714.440000000001, 53, 12686, 3784.5, 10633.7, 11331.649999999998, 12432.61, 5.522497273266971, 202.1182767910149, 5.870889191092212], "isController": false}, {"data": ["POST /graphql - Login", 400, 15, 3.75, 26605.84249999999, 257, 49163, 33925.5, 47172.4, 48254.2, 48426.0, 6.576675819207182, 9.099527840918434, 4.5471547656237155], "isController": false}, {"data": ["POST /graphql - Obtain Recent Pages", 400, 7, 1.75, 5648.240000000002, 52, 29126, 5053.0, 10758.5, 12264.5, 26735.58, 5.238138889252649, 6.371231711529144, 6.284743593101371], "isController": false}, {"data": ["POST /graphql - Obtain Users List", 400, 21, 5.25, 5266.192499999999, 53, 29163, 4804.5, 11075.2, 12229.15, 16139.630000000001, 4.886570482670999, 80.60606787980869, 5.194844362730127], "isController": false}, {"data": ["GET /a - Administration Page", 400, 15, 3.75, 2030.4149999999988, 47, 25689, 1055.5, 5211.8, 7678.8, 10650.810000000003, 6.951323358184315, 15.363476823853466, 5.787791303894479], "isController": false}, {"data": ["GET /", 400, 0, 0.0, 4932.080000000001, 106, 12374, 5154.0, 12151.300000000001, 12248.0, 12355.95, 30.546009927453227, 97.02489619129439, 3.5199503627338675], "isController": false}, {"data": ["POST /graphql - Obtain System Info", 400, 23, 5.75, 5920.187499999996, 58, 29500, 4962.5, 11701.600000000017, 16059.699999999999, 28423.050000000014, 5.898834980091432, 3.7832471934449194, 6.4380253649904144], "isController": false}]}, function(index, item){
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
    createTable($("#errorsTable"), {"supportsControllersDiscrimination": false, "titles": ["Type of error", "Number of errors", "% in errors", "% in all samples"], "items": [{"data": ["502/Bad Gateway", 62, 41.05960264900662, 1.55], "isController": false}, {"data": ["Test failed: text expected not to contain /&quot;errors&quot;:/", 74, 49.00662251655629, 1.85], "isController": false}, {"data": ["403/Forbidden", 15, 9.933774834437086, 0.375], "isController": false}]}, function(index, item){
        switch(index){
            case 2:
            case 3:
                item = item.toFixed(2) + '%';
                break;
        }
        return item;
    }, [[1, 1]]);

        // Create top5 errors by sampler
    createTable($("#top5ErrorsBySamplerTable"), {"supportsControllersDiscrimination": false, "overall": {"data": ["Total", 4000, 151, "Test failed: text expected not to contain /&quot;errors&quot;:/", 74, "502/Bad Gateway", 62, "403/Forbidden", 15, null, null, null, null], "isController": false}, "titles": ["Sample", "#Samples", "#Errors", "Error", "#Errors", "Error", "#Errors", "Error", "#Errors", "Error", "#Errors", "Error", "#Errors"], "items": [{"data": [], "isController": false}, {"data": ["POST /graphql - Obtain Groups List", 400, 21, "Test failed: text expected not to contain /&quot;errors&quot;:/", 15, "502/Bad Gateway", 6, null, null, null, null, null, null], "isController": false}, {"data": ["POST /graphql - Create User", 400, 24, "Test failed: text expected not to contain /&quot;errors&quot;:/", 15, "502/Bad Gateway", 9, null, null, null, null, null, null], "isController": false}, {"data": ["POST /graphql - Obtain Updated Users List", 400, 25, "Test failed: text expected not to contain /&quot;errors&quot;:/", 15, "502/Bad Gateway", 10, null, null, null, null, null, null], "isController": false}, {"data": ["POST /graphql - Login", 400, 15, "502/Bad Gateway", 15, null, null, null, null, null, null, null, null], "isController": false}, {"data": ["POST /graphql - Obtain Recent Pages", 400, 7, "502/Bad Gateway", 7, null, null, null, null, null, null, null, null], "isController": false}, {"data": ["POST /graphql - Obtain Users List", 400, 21, "Test failed: text expected not to contain /&quot;errors&quot;:/", 15, "502/Bad Gateway", 6, null, null, null, null, null, null], "isController": false}, {"data": ["GET /a - Administration Page", 400, 15, "403/Forbidden", 15, null, null, null, null, null, null, null, null], "isController": false}, {"data": [], "isController": false}, {"data": ["POST /graphql - Obtain System Info", 400, 23, "Test failed: text expected not to contain /&quot;errors&quot;:/", 14, "502/Bad Gateway", 9, null, null, null, null, null, null], "isController": false}]}, function(index, item){
        return item;
    }, [[0, 0]], 0);

});
