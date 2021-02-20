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

    var data = {"OkPercent": 76.10714285714286, "KoPercent": 23.892857142857142};
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
    createTable($("#apdexTable"), {"supportsControllersDiscrimination": true, "overall": {"data": [0.3339285714285714, 500, 1500, "Total"], "isController": false}, "titles": ["Apdex", "T (Toleration threshold)", "F (Frustration threshold)", "Label"], "items": [{"data": [0.9425, 500, 1500, "GET /login"], "isController": false}, {"data": [0.0, 500, 1500, "POST /graphql - Login"], "isController": false}, {"data": [0.23375, 500, 1500, "POST /graphql - Tree (List Directories)"], "isController": false}, {"data": [0.0, 500, 1500, "POST /graphql - Create Page"], "isController": false}, {"data": [0.49625, 500, 1500, "GET /e/en/{new-page-path} - Edit New Page"], "isController": false}, {"data": [0.665, 500, 1500, "GET /"], "isController": false}, {"data": [0.0, 500, 1500, "GET /en/{new-page-path} - View New Page"], "isController": false}]}, function(index, item){
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
    createTable($("#statisticsTable"), {"supportsControllersDiscrimination": true, "overall": {"data": ["Total", 2800, 669, 23.892857142857142, 22410.14785714286, 45, 124933, 894.5, 75237.9, 124567.95, 124880.0, 10.735702344984126, 18.906825426456603, 5.364114345294695], "isController": false}, "titles": ["Label", "#Samples", "FAIL", "Error %", "Average", "Min", "Max", "Median", "90th pct", "95th pct", "99th pct", "Transactions/s", "Received", "Sent"], "items": [{"data": ["GET /login", 400, 0, 0.0, 218.14749999999998, 45, 2554, 86.0, 149.90000000000003, 2382.0, 2395.0, 92.98000929800094, 187.04962807996282, 11.077696420269643], "isController": false}, {"data": ["POST /graphql - Login", 400, 7, 1.75, 69031.29249999997, 61696, 120157, 63513.0, 75270.9, 75598.95, 120155.99, 3.328700891259664, 3.630266888996147, 2.258014822601047], "isController": false}, {"data": ["POST /graphql - Tree (List Directories)", 400, 0, 0.0, 7529.307499999994, 194, 13984, 10116.0, 13968.9, 13980.0, 13982.99, 6.873442735630209, 4.738916573588797, 5.919618309133087], "isController": false}, {"data": ["POST /graphql - Create Page", 400, 400, 100.0, 56122.03249999998, 46, 124933, 73.0, 124868.0, 124887.95, 124914.99, 3.200153607373154, 3.9297198765540746, 2.189847300170408], "isController": false}, {"data": ["GET /e/en/{new-page-path} - Edit New Page", 400, 0, 0.0, 909.11, 784, 1985, 887.0, 1009.9000000000001, 1038.3999999999999, 1414.1700000000008, 8.561827093901838, 17.784081255752476, 4.4617654219910525], "isController": false}, {"data": ["GET /", 400, 0, 0.0, 703.8725000000001, 112, 2347, 592.5, 1409.7, 1519.0, 1589.5500000000004, 162.53555465258023, 459.35341324664773, 18.57095692807802], "isController": false}, {"data": ["GET /en/{new-page-path} - View New Page", 400, 262, 65.5, 22357.2725, 369, 58157, 802.5, 54153.3, 55963.2, 57994.520000000004, 2.1875017089857103, 5.260423573817246, 1.13568509135554], "isController": false}]}, function(index, item){
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
    createTable($("#errorsTable"), {"supportsControllersDiscrimination": false, "titles": ["Type of error", "Number of errors", "% in errors", "% in all samples"], "items": [{"data": ["Test failed: text expected not to contain /&quot;errors&quot;:/", 220, 32.88490284005979, 7.857142857142857], "isController": false}, {"data": ["500/Internal Server Error", 42, 6.278026905829597, 1.5], "isController": false}, {"data": ["404/Not Found", 220, 32.88490284005979, 7.857142857142857], "isController": false}, {"data": ["Non HTTP response code: org.apache.http.NoHttpResponseException/Non HTTP response message: 34.77.156.245:10000 failed to respond", 187, 27.952167414050823, 6.678571428571429], "isController": false}]}, function(index, item){
        switch(index){
            case 2:
            case 3:
                item = item.toFixed(2) + '%';
                break;
        }
        return item;
    }, [[1, 1]]);

        // Create top5 errors by sampler
    createTable($("#top5ErrorsBySamplerTable"), {"supportsControllersDiscrimination": false, "overall": {"data": ["Total", 2800, 669, "Test failed: text expected not to contain /&quot;errors&quot;:/", 220, "404/Not Found", 220, "Non HTTP response code: org.apache.http.NoHttpResponseException/Non HTTP response message: 34.77.156.245:10000 failed to respond", 187, "500/Internal Server Error", 42, null, null], "isController": false}, "titles": ["Sample", "#Samples", "#Errors", "Error", "#Errors", "Error", "#Errors", "Error", "#Errors", "Error", "#Errors", "Error", "#Errors"], "items": [{"data": [], "isController": false}, {"data": ["POST /graphql - Login", 400, 7, "Non HTTP response code: org.apache.http.NoHttpResponseException/Non HTTP response message: 34.77.156.245:10000 failed to respond", 7, null, null, null, null, null, null, null, null], "isController": false}, {"data": [], "isController": false}, {"data": ["POST /graphql - Create Page", 400, 400, "Test failed: text expected not to contain /&quot;errors&quot;:/", 220, "Non HTTP response code: org.apache.http.NoHttpResponseException/Non HTTP response message: 34.77.156.245:10000 failed to respond", 180, null, null, null, null, null, null], "isController": false}, {"data": [], "isController": false}, {"data": [], "isController": false}, {"data": ["GET /en/{new-page-path} - View New Page", 400, 262, "404/Not Found", 220, "500/Internal Server Error", 42, null, null, null, null, null, null], "isController": false}]}, function(index, item){
        return item;
    }, [[0, 0]], 0);

});
