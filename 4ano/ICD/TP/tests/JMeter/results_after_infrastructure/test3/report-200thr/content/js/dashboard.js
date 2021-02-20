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

    var data = {"OkPercent": 97.43589743589743, "KoPercent": 2.5641025641025643};
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
    createTable($("#apdexTable"), {"supportsControllersDiscrimination": true, "overall": {"data": [0.39447731755424065, 500, 1500, "Total"], "isController": false}, "titles": ["Apdex", "T (Toleration threshold)", "F (Frustration threshold)", "Label"], "items": [{"data": [0.4025, 500, 1500, "GET /login"], "isController": false}, {"data": [0.21428571428571427, 500, 1500, "GET /en/{searched-page} - View the Searched Page-0"], "isController": false}, {"data": [0.01, 500, 1500, "POST /graphql - Login"], "isController": false}, {"data": [0.0, 500, 1500, "GET /en/{searched-page} - View the Searched Page-1"], "isController": false}, {"data": [0.3, 500, 1500, "GET /"], "isController": false}, {"data": [0.56, 500, 1500, "POST /graphql - Page Search"], "isController": false}, {"data": [0.72, 500, 1500, "GET /en/{searched-page} - View the Searched Page"], "isController": false}]}, function(index, item){
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
    createTable($("#statisticsTable"), {"supportsControllersDiscrimination": true, "overall": {"data": ["Total", 1014, 26, 2.5641025641025643, 4871.987179487173, 49, 27033, 2268.5, 16462.0, 21439.5, 25811.5, 33.65081472140179, 71.1424792793615, 20.365863681428998], "isController": false}, "titles": ["Label", "#Samples", "FAIL", "Error %", "Average", "Min", "Max", "Median", "90th pct", "95th pct", "99th pct", "Transactions/s", "Received", "Sent"], "items": [{"data": ["GET /login", 200, 1, 0.5, 1878.2150000000004, 51, 7163, 1410.5, 4163.0, 4382.55, 5562.600000000009, 14.597474636887817, 32.570258899897816, 1.7534075979855483], "isController": false}, {"data": ["GET /en/{searched-page} - View the Searched Page-0", 7, 0, 0.0, 2721.4285714285716, 537, 5037, 3567.0, 5037.0, 5037.0, 5037.0, 0.4399472063352398, 0.18259527606687198, 0.37850926638803345], "isController": false}, {"data": ["POST /graphql - Login", 200, 4, 2.0, 14017.03, 354, 27033, 16547.0, 24975.7, 25812.5, 26611.9, 6.708932944215222, 9.393161291134145, 4.638598168461306], "isController": false}, {"data": ["GET /en/{searched-page} - View the Searched Page-1", 7, 7, 100.0, 3881.8571428571427, 62, 7788, 2253.0, 7788.0, 7788.0, 7788.0, 0.3259604190919674, 0.7408232683352736, 0.28012223515715945], "isController": false}, {"data": ["GET /", 200, 0, 0.0, 2830.7400000000002, 85, 7458, 2922.0, 6299.0, 6437.6, 7451.95, 24.163344206838225, 76.78775711308445, 2.784447867584874], "isController": false}, {"data": ["POST /graphql - Page Search", 200, 7, 3.5, 3689.9500000000016, 51, 15323, 68.0, 12423.4, 14402.6, 15115.91, 7.259528130671506, 4.718409709618875, 8.705195099818512], "isController": false}, {"data": ["GET /en/{searched-page} - View the Searched Page", 200, 7, 3.5, 2053.9249999999997, 49, 12469, 60.0, 9017.900000000001, 10882.749999999989, 12355.0, 8.21827744904668, 26.014660315479126, 7.251265486316568], "isController": false}]}, function(index, item){
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
    createTable($("#errorsTable"), {"supportsControllersDiscrimination": false, "titles": ["Type of error", "Number of errors", "% in errors", "% in all samples"], "items": [{"data": ["502/Bad Gateway", 12, 46.15384615384615, 1.183431952662722], "isController": false}, {"data": ["404/Not Found", 14, 53.84615384615385, 1.3806706114398422], "isController": false}]}, function(index, item){
        switch(index){
            case 2:
            case 3:
                item = item.toFixed(2) + '%';
                break;
        }
        return item;
    }, [[1, 1]]);

        // Create top5 errors by sampler
    createTable($("#top5ErrorsBySamplerTable"), {"supportsControllersDiscrimination": false, "overall": {"data": ["Total", 1014, 26, "404/Not Found", 14, "502/Bad Gateway", 12, null, null, null, null, null, null], "isController": false}, "titles": ["Sample", "#Samples", "#Errors", "Error", "#Errors", "Error", "#Errors", "Error", "#Errors", "Error", "#Errors", "Error", "#Errors"], "items": [{"data": ["GET /login", 200, 1, "502/Bad Gateway", 1, null, null, null, null, null, null, null, null], "isController": false}, {"data": [], "isController": false}, {"data": ["POST /graphql - Login", 200, 4, "502/Bad Gateway", 4, null, null, null, null, null, null, null, null], "isController": false}, {"data": ["GET /en/{searched-page} - View the Searched Page-1", 7, 7, "404/Not Found", 7, null, null, null, null, null, null, null, null], "isController": false}, {"data": [], "isController": false}, {"data": ["POST /graphql - Page Search", 200, 7, "502/Bad Gateway", 7, null, null, null, null, null, null, null, null], "isController": false}, {"data": ["GET /en/{searched-page} - View the Searched Page", 200, 7, "404/Not Found", 7, null, null, null, null, null, null, null, null], "isController": false}]}, function(index, item){
        return item;
    }, [[0, 0]], 0);

});
