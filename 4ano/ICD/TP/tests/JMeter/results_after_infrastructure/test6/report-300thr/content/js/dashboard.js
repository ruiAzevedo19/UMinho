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

    var data = {"OkPercent": 95.65420560747664, "KoPercent": 4.345794392523365};
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
    createTable($("#apdexTable"), {"supportsControllersDiscrimination": true, "overall": {"data": [0.2866822429906542, 500, 1500, "Total"], "isController": false}, "titles": ["Apdex", "T (Toleration threshold)", "F (Frustration threshold)", "Label"], "items": [{"data": [0.415, 500, 1500, "GET /login"], "isController": false}, {"data": [0.0, 500, 1500, "POST /graphql - Update Page"], "isController": false}, {"data": [0.15, 500, 1500, "GET /en/{searched-page} - View the Searched Page-0"], "isController": false}, {"data": [0.0, 500, 1500, "POST /graphql - Login"], "isController": false}, {"data": [0.0, 500, 1500, "GET /en/{searched-page} - View the Searched Page-1"], "isController": false}, {"data": [0.40166666666666667, 500, 1500, "GET /e/en/{searched-page} - Edit Page"], "isController": false}, {"data": [0.6, 500, 1500, "GET /e/en/{searched-page} - Edit Page-0"], "isController": false}, {"data": [0.0, 500, 1500, "GET /e/en/{searched-page} - Edit Page-1"], "isController": false}, {"data": [0.44666666666666666, 500, 1500, "GET /"], "isController": false}, {"data": [0.355, 500, 1500, "POST /graphql - Page Search"], "isController": false}, {"data": [0.40166666666666667, 500, 1500, "GET /en/{searched-page} - View the Searched Page"], "isController": false}]}, function(index, item){
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
    createTable($("#statisticsTable"), {"supportsControllersDiscrimination": true, "overall": {"data": ["Total", 2140, 93, 4.345794392523365, 11561.590654205607, 50, 97324, 3653.5, 31517.40000000001, 46902.3499999998, 93969.47, 15.808991918206935, 32.133542119143655, 12.788116453725454], "isController": false}, "titles": ["Label", "#Samples", "FAIL", "Error %", "Average", "Min", "Max", "Median", "90th pct", "95th pct", "99th pct", "Transactions/s", "Received", "Sent"], "items": [{"data": ["GET /login", 300, 1, 0.3333333333333333, 3227.236666666666, 50, 14128, 2825.0, 7496.0, 7741.85, 14096.95, 14.025901164149797, 31.336977023820655, 1.6847517999906494], "isController": false}, {"data": ["POST /graphql - Update Page", 300, 15, 5.0, 45035.849999999984, 56, 97324, 32110.5, 91840.50000000001, 94252.8, 96801.43000000001, 2.536097115612214, 1.6024798538785379, 4.65243383428296], "isController": false}, {"data": ["GET /en/{searched-page} - View the Searched Page-0", 10, 0, 0.0, 3439.3, 1144, 6707, 3104.5, 6641.200000000001, 6707.0, 6707.0, 1.0144045445323595, 0.42101751115845, 0.8030042224589166], "isController": false}, {"data": ["POST /graphql - Login", 300, 14, 4.666666666666667, 20957.56666666667, 308, 40812, 22020.5, 35401.0, 40757.15, 40806.95, 7.30549129428954, 10.044812720686716, 5.051062340192378], "isController": false}, {"data": ["GET /en/{searched-page} - View the Searched Page-1", 10, 10, 100.0, 3066.2, 57, 8677, 1881.5, 8428.1, 8677.0, 8677.0, 1.023017902813299, 2.3379555626598463, 0.8088235294117647], "isController": false}, {"data": ["GET /e/en/{searched-page} - Edit Page", 300, 10, 3.3333333333333335, 2697.3800000000006, 56, 18738, 1188.0, 7318.0, 9361.25, 14833.030000000004, 7.247077012271717, 21.245896153915837, 6.249188478355396], "isController": false}, {"data": ["GET /e/en/{searched-page} - Edit Page-0", 10, 0, 0.0, 1141.5, 51, 6186, 631.0, 5770.300000000001, 6186.0, 6186.0, 1.341741580571582, 0.5621163457668052, 1.0647453206762376], "isController": false}, {"data": ["GET /e/en/{searched-page} - Edit Page-1", 10, 10, 100.0, 386.6, 51, 1069, 331.0, 1047.2, 1069.0, 1069.0, 2.3579344494223062, 5.782005275878331, 1.8688472647960388], "isController": false}, {"data": ["GET /", 300, 0, 0.0, 2942.76, 84, 10127, 2367.0, 6975.400000000001, 7144.7, 10082.97, 27.90957298353335, 88.57110254442273, 3.216142199274351], "isController": false}, {"data": ["POST /graphql - Page Search", 300, 10, 3.3333333333333335, 4564.983333333327, 52, 24634, 1570.0, 18641.600000000002, 23178.7, 24508.24, 7.875876191226273, 5.313909043474837, 9.299892034863879], "isController": false}, {"data": ["GET /en/{searched-page} - View the Searched Page", 300, 23, 7.666666666666667, 2779.116666666666, 50, 22378, 1353.5, 8364.100000000008, 11696.05, 14668.86, 7.714263673532361, 25.40046872187508, 6.636476574352645], "isController": false}]}, function(index, item){
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
    createTable($("#errorsTable"), {"supportsControllersDiscrimination": false, "titles": ["Type of error", "Number of errors", "% in errors", "% in all samples"], "items": [{"data": ["502/Bad Gateway", 26, 27.956989247311828, 1.2149532710280373], "isController": false}, {"data": ["500/Internal Server Error", 33, 35.483870967741936, 1.5420560747663552], "isController": false}, {"data": ["Test failed: text expected not to contain /&quot;errors&quot;:/", 14, 15.053763440860216, 0.6542056074766355], "isController": false}, {"data": ["404/Not Found", 20, 21.50537634408602, 0.9345794392523364], "isController": false}]}, function(index, item){
        switch(index){
            case 2:
            case 3:
                item = item.toFixed(2) + '%';
                break;
        }
        return item;
    }, [[1, 1]]);

        // Create top5 errors by sampler
    createTable($("#top5ErrorsBySamplerTable"), {"supportsControllersDiscrimination": false, "overall": {"data": ["Total", 2140, 93, "500/Internal Server Error", 33, "502/Bad Gateway", 26, "404/Not Found", 20, "Test failed: text expected not to contain /&quot;errors&quot;:/", 14, null, null], "isController": false}, "titles": ["Sample", "#Samples", "#Errors", "Error", "#Errors", "Error", "#Errors", "Error", "#Errors", "Error", "#Errors", "Error", "#Errors"], "items": [{"data": ["GET /login", 300, 1, "502/Bad Gateway", 1, null, null, null, null, null, null, null, null], "isController": false}, {"data": ["POST /graphql - Update Page", 300, 15, "Test failed: text expected not to contain /&quot;errors&quot;:/", 14, "502/Bad Gateway", 1, null, null, null, null, null, null], "isController": false}, {"data": [], "isController": false}, {"data": ["POST /graphql - Login", 300, 14, "502/Bad Gateway", 14, null, null, null, null, null, null, null, null], "isController": false}, {"data": ["GET /en/{searched-page} - View the Searched Page-1", 10, 10, "404/Not Found", 10, null, null, null, null, null, null, null, null], "isController": false}, {"data": ["GET /e/en/{searched-page} - Edit Page", 300, 10, "500/Internal Server Error", 10, null, null, null, null, null, null, null, null], "isController": false}, {"data": [], "isController": false}, {"data": ["GET /e/en/{searched-page} - Edit Page-1", 10, 10, "500/Internal Server Error", 10, null, null, null, null, null, null, null, null], "isController": false}, {"data": [], "isController": false}, {"data": ["POST /graphql - Page Search", 300, 10, "502/Bad Gateway", 10, null, null, null, null, null, null, null, null], "isController": false}, {"data": ["GET /en/{searched-page} - View the Searched Page", 300, 23, "500/Internal Server Error", 13, "404/Not Found", 10, null, null, null, null, null, null], "isController": false}]}, function(index, item){
        return item;
    }, [[0, 0]], 0);

});
