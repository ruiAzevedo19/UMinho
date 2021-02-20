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

    var data = {"OkPercent": 94.70752089136491, "KoPercent": 5.2924791086350975};
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
    createTable($("#apdexTable"), {"supportsControllersDiscrimination": true, "overall": {"data": [0.2865598885793872, 500, 1500, "Total"], "isController": false}, "titles": ["Apdex", "T (Toleration threshold)", "F (Frustration threshold)", "Label"], "items": [{"data": [0.47, 500, 1500, "GET /login"], "isController": false}, {"data": [0.0, 500, 1500, "POST /graphql - Update Page"], "isController": false}, {"data": [0.2777777777777778, 500, 1500, "GET /en/{searched-page} - View the Searched Page-0"], "isController": false}, {"data": [0.0025, 500, 1500, "POST /graphql - Login"], "isController": false}, {"data": [0.0, 500, 1500, "GET /en/{searched-page} - View the Searched Page-1"], "isController": false}, {"data": [0.4375, 500, 1500, "GET /e/en/{searched-page} - Edit Page"], "isController": false}, {"data": [0.7222222222222222, 500, 1500, "GET /e/en/{searched-page} - Edit Page-0"], "isController": false}, {"data": [0.0, 500, 1500, "GET /e/en/{searched-page} - Edit Page-1"], "isController": false}, {"data": [0.3225, 500, 1500, "GET /"], "isController": false}, {"data": [0.3325, 500, 1500, "POST /graphql - Page Search"], "isController": false}, {"data": [0.4475, 500, 1500, "GET /en/{searched-page} - View the Searched Page"], "isController": false}]}, function(index, item){
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
    createTable($("#statisticsTable"), {"supportsControllersDiscrimination": true, "overall": {"data": ["Total", 1436, 76, 5.2924791086350975, 12033.591225626742, 45, 111114, 3824.5, 29350.69999999999, 75691.04999999996, 107669.0799999999, 9.989217766338562, 20.069437802163403, 8.050650683889256], "isController": false}, "titles": ["Label", "#Samples", "FAIL", "Error %", "Average", "Min", "Max", "Median", "90th pct", "95th pct", "99th pct", "Transactions/s", "Received", "Sent"], "items": [{"data": ["GET /login", 200, 0, 0.0, 2283.835000000001, 49, 7746, 981.5, 6991.9, 7308.95, 7730.99, 13.023376961646155, 29.293059557530768, 1.5643314123852317], "isController": false}, {"data": ["POST /graphql - Update Page", 200, 12, 6.0, 51864.595, 51, 111114, 61946.0, 106181.2, 109032.75, 110800.48000000001, 1.4833384014062048, 0.9383419104286107, 2.707244682695374], "isController": false}, {"data": ["GET /en/{searched-page} - View the Searched Page-0", 9, 0, 0.0, 2945.222222222222, 45, 7159, 3210.0, 7159.0, 7159.0, 7159.0, 0.33895751732449536, 0.14068061021768605, 0.2657300415222959], "isController": false}, {"data": ["POST /graphql - Login", 200, 12, 6.0, 14945.525, 844, 32601, 15729.0, 29532.0, 30016.6, 32583.91, 6.001140216641162, 8.175967496324303, 4.149225852912053], "isController": false}, {"data": ["GET /en/{searched-page} - View the Searched Page-1", 9, 9, 100.0, 5403.666666666667, 57, 15998, 2321.0, 15998.0, 15998.0, 15998.0, 0.3598560575769692, 0.7378689149340264, 0.2817622950819672], "isController": false}, {"data": ["GET /e/en/{searched-page} - Edit Page", 200, 9, 4.5, 3777.2449999999985, 54, 20307, 979.0, 11368.1, 11947.0, 19434.470000000038, 4.250255015300918, 12.179616275023378, 3.663603605278817], "isController": false}, {"data": ["GET /e/en/{searched-page} - Edit Page-0", 9, 0, 0.0, 980.9999999999998, 51, 5451, 107.0, 5451.0, 5451.0, 5451.0, 0.39563917707051166, 0.16575117867504835, 0.310939079809214], "isController": false}, {"data": ["GET /e/en/{searched-page} - Edit Page-1", 9, 9, 100.0, 1689.6666666666667, 58, 4758, 690.0, 4758.0, 4758.0, 4758.0, 0.36573472041612487, 0.8682231134184005, 0.28707953206274384], "isController": false}, {"data": ["GET /", 200, 0, 0.0, 2643.7699999999995, 81, 9223, 2335.0, 6483.5, 9061.6, 9210.97, 20.261371694863744, 64.41839969987844, 2.334806503900314], "isController": false}, {"data": ["POST /graphql - Page Search", 200, 9, 4.5, 5641.04, 54, 15974, 6178.0, 13214.2, 15289.55, 15973.45, 4.99637762622099, 3.359039305878238, 5.853959004721577], "isController": false}, {"data": ["GET /en/{searched-page} - View the Searched Page", 200, 16, 8.0, 4749.294999999999, 50, 20412, 1021.0, 12716.3, 14761.0, 18981.0, 4.712868487404859, 15.209563404281642, 4.052744730423923], "isController": false}]}, function(index, item){
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
    createTable($("#errorsTable"), {"supportsControllersDiscrimination": false, "titles": ["Type of error", "Number of errors", "% in errors", "% in all samples"], "items": [{"data": ["502/Bad Gateway", 24, 31.57894736842105, 1.6713091922005572], "isController": false}, {"data": ["500/Internal Server Error", 24, 31.57894736842105, 1.6713091922005572], "isController": false}, {"data": ["Test failed: text expected not to contain /&quot;errors&quot;:/", 12, 15.789473684210526, 0.8356545961002786], "isController": false}, {"data": ["404/Not Found", 16, 21.05263157894737, 1.1142061281337048], "isController": false}]}, function(index, item){
        switch(index){
            case 2:
            case 3:
                item = item.toFixed(2) + '%';
                break;
        }
        return item;
    }, [[1, 1]]);

        // Create top5 errors by sampler
    createTable($("#top5ErrorsBySamplerTable"), {"supportsControllersDiscrimination": false, "overall": {"data": ["Total", 1436, 76, "502/Bad Gateway", 24, "500/Internal Server Error", 24, "404/Not Found", 16, "Test failed: text expected not to contain /&quot;errors&quot;:/", 12, null, null], "isController": false}, "titles": ["Sample", "#Samples", "#Errors", "Error", "#Errors", "Error", "#Errors", "Error", "#Errors", "Error", "#Errors", "Error", "#Errors"], "items": [{"data": [], "isController": false}, {"data": ["POST /graphql - Update Page", 200, 12, "Test failed: text expected not to contain /&quot;errors&quot;:/", 12, null, null, null, null, null, null, null, null], "isController": false}, {"data": [], "isController": false}, {"data": ["POST /graphql - Login", 200, 12, "502/Bad Gateway", 12, null, null, null, null, null, null, null, null], "isController": false}, {"data": ["GET /en/{searched-page} - View the Searched Page-1", 9, 9, "404/Not Found", 8, "502/Bad Gateway", 1, null, null, null, null, null, null], "isController": false}, {"data": ["GET /e/en/{searched-page} - Edit Page", 200, 9, "500/Internal Server Error", 9, null, null, null, null, null, null, null, null], "isController": false}, {"data": [], "isController": false}, {"data": ["GET /e/en/{searched-page} - Edit Page-1", 9, 9, "500/Internal Server Error", 9, null, null, null, null, null, null, null, null], "isController": false}, {"data": [], "isController": false}, {"data": ["POST /graphql - Page Search", 200, 9, "502/Bad Gateway", 9, null, null, null, null, null, null, null, null], "isController": false}, {"data": ["GET /en/{searched-page} - View the Searched Page", 200, 16, "404/Not Found", 8, "500/Internal Server Error", 6, "502/Bad Gateway", 2, null, null, null, null], "isController": false}]}, function(index, item){
        return item;
    }, [[0, 0]], 0);

});
