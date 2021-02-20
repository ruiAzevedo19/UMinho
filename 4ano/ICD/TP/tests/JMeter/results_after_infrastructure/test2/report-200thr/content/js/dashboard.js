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

    var data = {"OkPercent": 91.35714285714286, "KoPercent": 8.642857142857142};
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
    createTable($("#apdexTable"), {"supportsControllersDiscrimination": true, "overall": {"data": [0.2689285714285714, 500, 1500, "Total"], "isController": false}, "titles": ["Apdex", "T (Toleration threshold)", "F (Frustration threshold)", "Label"], "items": [{"data": [0.4275, 500, 1500, "GET /login"], "isController": false}, {"data": [0.0025, 500, 1500, "POST /graphql - Login"], "isController": false}, {"data": [0.2775, 500, 1500, "POST /graphql - Tree (List Directories)"], "isController": false}, {"data": [0.0, 500, 1500, "POST /graphql - Create Page"], "isController": false}, {"data": [0.42, 500, 1500, "GET /e/en/{new-page-path} - Edit New Page"], "isController": false}, {"data": [0.315, 500, 1500, "GET /"], "isController": false}, {"data": [0.44, 500, 1500, "GET /en/{new-page-path} - View New Page"], "isController": false}]}, function(index, item){
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
    createTable($("#statisticsTable"), {"supportsControllersDiscrimination": true, "overall": {"data": ["Total", 1400, 121, 8.642857142857142, 11360.838571428572, 52, 87147, 2603.0, 35321.80000000003, 65653.15, 75826.89, 12.733176290825746, 52.939805827588245, 10.510732532810668], "isController": false}, "titles": ["Label", "#Samples", "FAIL", "Error %", "Average", "Min", "Max", "Median", "90th pct", "95th pct", "99th pct", "Transactions/s", "Received", "Sent"], "items": [{"data": ["GET /login", 200, 0, 0.0, 1807.61, 53, 4848, 1265.0, 4019.9000000000015, 4518.95, 4847.58, 19.107671730199673, 42.74763094726283, 2.2951597879048435], "isController": false}, {"data": ["POST /graphql - Login", 200, 5, 2.5, 15752.324999999999, 533, 29127, 20780.0, 25294.5, 27393.75, 28905.78, 6.670224119530417, 9.307535633587912, 4.611834645144077], "isController": false}, {"data": ["POST /graphql - Tree (List Directories)", 200, 7, 3.5, 3887.4199999999987, 69, 15918, 1598.0, 12482.300000000003, 13991.599999999999, 15377.0, 6.427561383211209, 102.98409580280241, 7.75450130158118], "isController": false}, {"data": ["POST /graphql - Create Page", 200, 8, 4.0, 51691.505, 57, 87147, 60986.5, 75622.4, 76175.55, 83880.93000000001, 1.9373462231435379, 1.2953827136650717, 3.7096774681790885], "isController": false}, {"data": ["GET /e/en/{new-page-path} - Edit New Page", 200, 0, 0.0, 3069.094999999998, 54, 16899, 1339.0, 11757.500000000002, 13475.4, 16888.36, 6.336332530731212, 16.843227995105185, 5.487734246293245], "isController": false}, {"data": ["GET /", 200, 0, 0.0, 2749.7699999999995, 84, 6163, 2698.5, 5819.6, 5956.299999999999, 6122.81, 30.51571559353067, 96.54498922413792, 3.5164594140982603], "isController": false}, {"data": ["GET /en/{new-page-path} - View New Page", 200, 101, 50.5, 568.1449999999999, 52, 17477, 102.0, 1111.4, 1701.5999999999997, 12069.390000000036, 2.0399628726757175, 6.034066742485287, 1.762774948745933], "isController": false}]}, function(index, item){
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
    createTable($("#errorsTable"), {"supportsControllersDiscrimination": false, "titles": ["Type of error", "Number of errors", "% in errors", "% in all samples"], "items": [{"data": ["502/Bad Gateway", 15, 12.396694214876034, 1.0714285714285714], "isController": false}, {"data": ["Test failed: text expected not to contain /&quot;errors&quot;:/", 5, 4.132231404958677, 0.35714285714285715], "isController": false}, {"data": ["500/Internal Server Error", 93, 76.85950413223141, 6.642857142857143], "isController": false}, {"data": ["404/Not Found", 8, 6.6115702479338845, 0.5714285714285714], "isController": false}]}, function(index, item){
        switch(index){
            case 2:
            case 3:
                item = item.toFixed(2) + '%';
                break;
        }
        return item;
    }, [[1, 1]]);

        // Create top5 errors by sampler
    createTable($("#top5ErrorsBySamplerTable"), {"supportsControllersDiscrimination": false, "overall": {"data": ["Total", 1400, 121, "500/Internal Server Error", 93, "502/Bad Gateway", 15, "404/Not Found", 8, "Test failed: text expected not to contain /&quot;errors&quot;:/", 5, null, null], "isController": false}, "titles": ["Sample", "#Samples", "#Errors", "Error", "#Errors", "Error", "#Errors", "Error", "#Errors", "Error", "#Errors", "Error", "#Errors"], "items": [{"data": [], "isController": false}, {"data": ["POST /graphql - Login", 200, 5, "502/Bad Gateway", 5, null, null, null, null, null, null, null, null], "isController": false}, {"data": ["POST /graphql - Tree (List Directories)", 200, 7, "502/Bad Gateway", 7, null, null, null, null, null, null, null, null], "isController": false}, {"data": ["POST /graphql - Create Page", 200, 8, "Test failed: text expected not to contain /&quot;errors&quot;:/", 5, "502/Bad Gateway", 3, null, null, null, null, null, null], "isController": false}, {"data": [], "isController": false}, {"data": [], "isController": false}, {"data": ["GET /en/{new-page-path} - View New Page", 200, 101, "500/Internal Server Error", 93, "404/Not Found", 8, null, null, null, null, null, null], "isController": false}]}, function(index, item){
        return item;
    }, [[0, 0]], 0);

});
