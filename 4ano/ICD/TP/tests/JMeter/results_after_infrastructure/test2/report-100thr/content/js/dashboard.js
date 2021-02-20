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

    var data = {"OkPercent": 95.28571428571429, "KoPercent": 4.714285714285714};
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
    createTable($("#apdexTable"), {"supportsControllersDiscrimination": true, "overall": {"data": [0.37214285714285716, 500, 1500, "Total"], "isController": false}, "titles": ["Apdex", "T (Toleration threshold)", "F (Frustration threshold)", "Label"], "items": [{"data": [0.655, 500, 1500, "GET /login"], "isController": false}, {"data": [0.035, 500, 1500, "POST /graphql - Login"], "isController": false}, {"data": [0.455, 500, 1500, "POST /graphql - Tree (List Directories)"], "isController": false}, {"data": [0.0, 500, 1500, "POST /graphql - Create Page"], "isController": false}, {"data": [0.475, 500, 1500, "GET /e/en/{new-page-path} - Edit New Page"], "isController": false}, {"data": [0.355, 500, 1500, "GET /"], "isController": false}, {"data": [0.63, 500, 1500, "GET /en/{new-page-path} - View New Page"], "isController": false}]}, function(index, item){
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
    createTable($("#statisticsTable"), {"supportsControllersDiscrimination": true, "overall": {"data": ["Total", 700, 33, 4.714285714285714, 6459.434285714286, 47, 53183, 1471.5, 17629.6, 42283.049999999996, 51211.670000000006, 11.387668781519439, 24.01616133886449, 9.462885858955588], "isController": false}, "titles": ["Label", "#Samples", "FAIL", "Error %", "Average", "Min", "Max", "Median", "90th pct", "95th pct", "99th pct", "Transactions/s", "Received", "Sent"], "items": [{"data": ["GET /login", 100, 0, 0.0, 805.0599999999998, 47, 2855, 648.0, 2305.500000000001, 2464.75, 2855.0, 12.56913021618904, 28.157552083333332, 1.5097685708898942], "isController": false}, {"data": ["POST /graphql - Login", 100, 1, 1.0, 6417.8099999999995, 379, 11713, 7287.0, 10365.9, 11126.249999999998, 11713.0, 6.604583581005218, 9.309302452777228, 4.566450366554389], "isController": false}, {"data": ["POST /graphql - Tree (List Directories)", 100, 1, 1.0, 1502.4199999999994, 58, 6000, 819.0, 3902.500000000001, 4514.049999999996, 5987.719999999994, 6.942034015966678, 10.5939235508504, 8.446774123568206], "isController": false}, {"data": ["POST /graphql - Create Page", 100, 2, 2.0, 32216.270000000008, 208, 53183, 36495.5, 50165.200000000004, 51850.299999999996, 53178.88, 1.799467357662132, 1.2043778791477722, 3.4613738708342328], "isController": false}, {"data": ["GET /e/en/{new-page-path} - Edit New Page", 100, 0, 0.0, 1734.3599999999994, 57, 5654, 1024.0, 4586.300000000001, 5330.699999999998, 5653.99, 7.159221076746849, 18.89775681468356, 6.270470897766323], "isController": false}, {"data": ["GET /", 100, 0, 0.0, 2233.729999999999, 86, 5107, 1790.0, 4933.3, 4997.55, 5106.79, 17.699115044247787, 56.013896570796454, 2.039546460176991], "isController": false}, {"data": ["GET /en/{new-page-path} - View New Page", 100, 29, 29.0, 306.3899999999999, 56, 4113, 93.5, 720.2, 809.8999999999993, 4113.0, 1.9824749216922404, 6.171885841659728, 1.7324972245351096], "isController": false}]}, function(index, item){
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
    createTable($("#errorsTable"), {"supportsControllersDiscrimination": false, "titles": ["Type of error", "Number of errors", "% in errors", "% in all samples"], "items": [{"data": ["502/Bad Gateway", 3, 9.090909090909092, 0.42857142857142855], "isController": false}, {"data": ["Test failed: text expected not to contain /&quot;errors&quot;:/", 1, 3.0303030303030303, 0.14285714285714285], "isController": false}, {"data": ["500/Internal Server Error", 27, 81.81818181818181, 3.857142857142857], "isController": false}, {"data": ["404/Not Found", 2, 6.0606060606060606, 0.2857142857142857], "isController": false}]}, function(index, item){
        switch(index){
            case 2:
            case 3:
                item = item.toFixed(2) + '%';
                break;
        }
        return item;
    }, [[1, 1]]);

        // Create top5 errors by sampler
    createTable($("#top5ErrorsBySamplerTable"), {"supportsControllersDiscrimination": false, "overall": {"data": ["Total", 700, 33, "500/Internal Server Error", 27, "502/Bad Gateway", 3, "404/Not Found", 2, "Test failed: text expected not to contain /&quot;errors&quot;:/", 1, null, null], "isController": false}, "titles": ["Sample", "#Samples", "#Errors", "Error", "#Errors", "Error", "#Errors", "Error", "#Errors", "Error", "#Errors", "Error", "#Errors"], "items": [{"data": [], "isController": false}, {"data": ["POST /graphql - Login", 100, 1, "502/Bad Gateway", 1, null, null, null, null, null, null, null, null], "isController": false}, {"data": ["POST /graphql - Tree (List Directories)", 100, 1, "502/Bad Gateway", 1, null, null, null, null, null, null, null, null], "isController": false}, {"data": ["POST /graphql - Create Page", 100, 2, "502/Bad Gateway", 1, "Test failed: text expected not to contain /&quot;errors&quot;:/", 1, null, null, null, null, null, null], "isController": false}, {"data": [], "isController": false}, {"data": [], "isController": false}, {"data": ["GET /en/{new-page-path} - View New Page", 100, 29, "500/Internal Server Error", 27, "404/Not Found", 2, null, null, null, null, null, null], "isController": false}]}, function(index, item){
        return item;
    }, [[0, 0]], 0);

});
