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

    var data = {"OkPercent": 91.25835189309576, "KoPercent": 8.74164810690423};
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
    createTable($("#apdexTable"), {"supportsControllersDiscrimination": true, "overall": {"data": [0.17678173719376392, 500, 1500, "Total"], "isController": false}, "titles": ["Apdex", "T (Toleration threshold)", "F (Frustration threshold)", "Label"], "items": [{"data": [0.373, 500, 1500, "GET /login"], "isController": false}, {"data": [0.0, 500, 1500, "POST /graphql - Update Page"], "isController": false}, {"data": [0.54, 500, 1500, "GET /en/{searched-page} - View the Searched Page-0"], "isController": false}, {"data": [0.0, 500, 1500, "POST /graphql - Login"], "isController": false}, {"data": [0.0, 500, 1500, "GET /en/{searched-page} - View the Searched Page-1"], "isController": false}, {"data": [0.185, 500, 1500, "GET /e/en/{searched-page} - Edit Page"], "isController": false}, {"data": [0.7857142857142857, 500, 1500, "GET /e/en/{searched-page} - Edit Page-0"], "isController": false}, {"data": [0.0, 500, 1500, "GET /e/en/{searched-page} - Edit Page-1"], "isController": false}, {"data": [0.378, 500, 1500, "GET /"], "isController": false}, {"data": [0.077, 500, 1500, "POST /graphql - Page Search"], "isController": false}, {"data": [0.197, 500, 1500, "GET /en/{searched-page} - View the Searched Page"], "isController": false}]}, function(index, item){
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
    createTable($("#statisticsTable"), {"supportsControllersDiscrimination": true, "overall": {"data": ["Total", 3592, 314, 8.74164810690423, 16000.929008908703, 43, 110425, 5047.5, 56848.40000000006, 68864.05, 95596.33000000002, 20.104665155458537, 39.32268163357401, 16.30615869784233], "isController": false}, "titles": ["Label", "#Samples", "FAIL", "Error %", "Average", "Min", "Max", "Median", "90th pct", "95th pct", "99th pct", "Transactions/s", "Received", "Sent"], "items": [{"data": ["GET /login", 500, 1, 0.2, 4043.8679999999995, 53, 11422, 3665.5, 9138.9, 9615.95, 11297.99, 23.569341001225606, 52.682448795606675, 2.831082952295654], "isController": false}, {"data": ["POST /graphql - Update Page", 500, 70, 14.0, 51195.98, 44, 110425, 57177.5, 93651.10000000008, 102330.69999999997, 109627.64, 3.3237167129771197, 2.061483358150418, 6.087309260207134], "isController": false}, {"data": ["GET /en/{searched-page} - View the Searched Page-0", 25, 0, 0.0, 1793.8400000000001, 49, 8105, 385.0, 5656.200000000003, 7611.199999999999, 8105.0, 0.40097517161737345, 0.1664203593138513, 0.32292598098575737], "isController": false}, {"data": ["POST /graphql - Login", 500, 25, 5.0, 38757.02999999997, 74, 68950, 41491.5, 68792.6, 68879.6, 68931.92, 7.211469120489226, 9.892882077155507, 4.986054821588254], "isController": false}, {"data": ["GET /en/{searched-page} - View the Searched Page-1", 25, 25, 100.0, 4312.76, 45, 27738, 390.0, 16500.60000000004, 27696.6, 27738.0, 0.39414769502427954, 0.7565942141089109, 0.3170425521851548], "isController": false}, {"data": ["GET /e/en/{searched-page} - Edit Page", 500, 69, 13.8, 4353.145999999999, 44, 19221, 3036.0, 15442.000000000002, 17656.8, 18757.66, 6.767273465520741, 18.123458859206874, 5.875130058536915], "isController": false}, {"data": ["GET /e/en/{searched-page} - Edit Page-0", 21, 0, 0.0, 513.4285714285714, 49, 3087, 170.0, 1668.2, 2948.699999999998, 3087.0, 0.41733738746795446, 0.17484154221069576, 0.3462091543452771], "isController": false}, {"data": ["GET /e/en/{searched-page} - Edit Page-1", 21, 21, 100.0, 472.23809523809524, 51, 4314, 188.0, 1172.4, 4002.1999999999957, 4314.0, 0.4346836124278115, 0.9023363920742689, 0.3601745073585726], "isController": false}, {"data": ["GET /", 500, 0, 0.0, 4337.457999999998, 87, 9721, 4381.0, 9376.7, 9545.55, 9677.52, 50.12028869286287, 158.98292621040497, 5.775580142341619], "isController": false}, {"data": ["POST /graphql - Page Search", 500, 36, 7.2, 8090.995999999996, 44, 49138, 4106.5, 21861.800000000003, 33714.4, 47810.94, 6.659563132658497, 4.440783997069793, 7.848399207511988], "isController": false}, {"data": ["GET /en/{searched-page} - View the Searched Page", 500, 67, 13.4, 3825.468, 43, 54619, 2609.0, 10985.2, 12954.25, 31997.490000000038, 6.3972159316265556, 19.738172447350912, 5.576023314653463], "isController": false}]}, function(index, item){
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
    createTable($("#errorsTable"), {"supportsControllersDiscrimination": false, "titles": ["Type of error", "Number of errors", "% in errors", "% in all samples"], "items": [{"data": ["502/Bad Gateway", 214, 68.15286624203821, 5.957683741648107], "isController": false}, {"data": ["500/Internal Server Error", 40, 12.738853503184714, 1.1135857461024499], "isController": false}, {"data": ["Test failed: text expected not to contain /&quot;errors&quot;:/", 20, 6.369426751592357, 0.5567928730512249], "isController": false}, {"data": ["404/Not Found", 40, 12.738853503184714, 1.1135857461024499], "isController": false}]}, function(index, item){
        switch(index){
            case 2:
            case 3:
                item = item.toFixed(2) + '%';
                break;
        }
        return item;
    }, [[1, 1]]);

        // Create top5 errors by sampler
    createTable($("#top5ErrorsBySamplerTable"), {"supportsControllersDiscrimination": false, "overall": {"data": ["Total", 3592, 314, "502/Bad Gateway", 214, "500/Internal Server Error", 40, "404/Not Found", 40, "Test failed: text expected not to contain /&quot;errors&quot;:/", 20, null, null], "isController": false}, "titles": ["Sample", "#Samples", "#Errors", "Error", "#Errors", "Error", "#Errors", "Error", "#Errors", "Error", "#Errors", "Error", "#Errors"], "items": [{"data": ["GET /login", 500, 1, "502/Bad Gateway", 1, null, null, null, null, null, null, null, null], "isController": false}, {"data": ["POST /graphql - Update Page", 500, 70, "502/Bad Gateway", 50, "Test failed: text expected not to contain /&quot;errors&quot;:/", 20, null, null, null, null, null, null], "isController": false}, {"data": [], "isController": false}, {"data": ["POST /graphql - Login", 500, 25, "502/Bad Gateway", 25, null, null, null, null, null, null, null, null], "isController": false}, {"data": ["GET /en/{searched-page} - View the Searched Page-1", 25, 25, "404/Not Found", 20, "502/Bad Gateway", 5, null, null, null, null, null, null], "isController": false}, {"data": ["GET /e/en/{searched-page} - Edit Page", 500, 69, "502/Bad Gateway", 52, "500/Internal Server Error", 17, null, null, null, null, null, null], "isController": false}, {"data": [], "isController": false}, {"data": ["GET /e/en/{searched-page} - Edit Page-1", 21, 21, "500/Internal Server Error", 17, "502/Bad Gateway", 4, null, null, null, null, null, null], "isController": false}, {"data": [], "isController": false}, {"data": ["POST /graphql - Page Search", 500, 36, "502/Bad Gateway", 36, null, null, null, null, null, null, null, null], "isController": false}, {"data": ["GET /en/{searched-page} - View the Searched Page", 500, 67, "502/Bad Gateway", 41, "404/Not Found", 20, "500/Internal Server Error", 6, null, null, null, null], "isController": false}]}, function(index, item){
        return item;
    }, [[0, 0]], 0);

});
