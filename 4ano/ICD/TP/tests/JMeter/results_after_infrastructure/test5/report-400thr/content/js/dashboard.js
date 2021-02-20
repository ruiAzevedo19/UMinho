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

    var data = {"OkPercent": 97.03571428571429, "KoPercent": 2.9642857142857144};
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
    createTable($("#apdexTable"), {"supportsControllersDiscrimination": true, "overall": {"data": [0.5639285714285714, 500, 1500, "Total"], "isController": false}, "titles": ["Apdex", "T (Toleration threshold)", "F (Frustration threshold)", "Label"], "items": [{"data": [0.335, 500, 1500, "GET /login"], "isController": false}, {"data": [0.00125, 500, 1500, "POST /graphql - Login"], "isController": false}, {"data": [0.90125, 500, 1500, "POST /graphql - Obtain Recent Pages"], "isController": false}, {"data": [0.9425, 500, 1500, "POST /graphql - Obtain Pages"], "isController": false}, {"data": [0.71375, 500, 1500, "GET /a - Administration Page"], "isController": false}, {"data": [0.28375, 500, 1500, "GET /"], "isController": false}, {"data": [0.77, 500, 1500, "POST /graphql - Obtain System Info"], "isController": false}]}, function(index, item){
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
    createTable($("#statisticsTable"), {"supportsControllersDiscrimination": true, "overall": {"data": ["Total", 2800, 83, 2.9642857142857144, 6589.600000000003, 47, 59935, 246.5, 27375.5, 43935.85, 59823.95, 45.38676003371587, 281.96301472942196, 33.350404023212086], "isController": false}, "titles": ["Label", "#Samples", "FAIL", "Error %", "Average", "Min", "Max", "Median", "90th pct", "95th pct", "99th pct", "Transactions/s", "Received", "Sent"], "items": [{"data": ["GET /login", 400, 1, 0.25, 4073.9650000000024, 67, 11041, 2395.0, 9144.900000000005, 10993.0, 11031.99, 19.889612649793648, 44.36757829981105, 2.389084331957635], "isController": false}, {"data": ["POST /graphql - Login", 400, 23, 5.75, 31331.44749999999, 50, 59935, 37127.0, 57214.00000000003, 59840.95, 59930.99, 6.628552489850029, 9.04637203268705, 4.583022619935372], "isController": false}, {"data": ["POST /graphql - Obtain Recent Pages", 400, 5, 1.25, 1728.9975000000009, 50, 41324, 101.0, 795.0, 18752.449999999808, 40459.61000000001, 8.137524158274845, 27.593080879361203, 9.65154867256637], "isController": false}, {"data": ["POST /graphql - Obtain Pages", 400, 3, 0.75, 426.40999999999997, 84, 27269, 139.0, 491.80000000000007, 572.0, 2949.780000000003, 8.702273468943762, 265.26479896388554, 9.870961601218317], "isController": false}, {"data": ["GET /a - Administration Page", 400, 23, 5.75, 1573.7549999999997, 47, 11082, 87.0, 6789.700000000002, 8568.0, 10627.420000000002, 7.108330963889679, 15.688169738058004, 5.820778984219506], "isController": false}, {"data": ["GET /", 400, 0, 0.0, 3954.967499999999, 90, 11293, 3590.0, 8379.8, 8501.7, 11248.97, 32.89203190527095, 103.80672269447415, 3.790292739083957], "isController": false}, {"data": ["POST /graphql - Obtain System Info", 400, 28, 7.0, 3037.657499999998, 50, 41337, 112.5, 6133.800000000039, 26916.55, 40250.72, 7.209935290830765, 4.72701382505092, 7.769831828259341], "isController": false}]}, function(index, item){
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
    createTable($("#errorsTable"), {"supportsControllersDiscrimination": false, "titles": ["Type of error", "Number of errors", "% in errors", "% in all samples"], "items": [{"data": ["502/Bad Gateway", 40, 48.19277108433735, 1.4285714285714286], "isController": false}, {"data": ["Test failed: text expected not to contain /&quot;errors&quot;:/", 20, 24.096385542168676, 0.7142857142857143], "isController": false}, {"data": ["403/Forbidden", 23, 27.710843373493976, 0.8214285714285714], "isController": false}]}, function(index, item){
        switch(index){
            case 2:
            case 3:
                item = item.toFixed(2) + '%';
                break;
        }
        return item;
    }, [[1, 1]]);

        // Create top5 errors by sampler
    createTable($("#top5ErrorsBySamplerTable"), {"supportsControllersDiscrimination": false, "overall": {"data": ["Total", 2800, 83, "502/Bad Gateway", 40, "403/Forbidden", 23, "Test failed: text expected not to contain /&quot;errors&quot;:/", 20, null, null, null, null], "isController": false}, "titles": ["Sample", "#Samples", "#Errors", "Error", "#Errors", "Error", "#Errors", "Error", "#Errors", "Error", "#Errors", "Error", "#Errors"], "items": [{"data": ["GET /login", 400, 1, "502/Bad Gateway", 1, null, null, null, null, null, null, null, null], "isController": false}, {"data": ["POST /graphql - Login", 400, 23, "502/Bad Gateway", 23, null, null, null, null, null, null, null, null], "isController": false}, {"data": ["POST /graphql - Obtain Recent Pages", 400, 5, "502/Bad Gateway", 5, null, null, null, null, null, null, null, null], "isController": false}, {"data": ["POST /graphql - Obtain Pages", 400, 3, "502/Bad Gateway", 3, null, null, null, null, null, null, null, null], "isController": false}, {"data": ["GET /a - Administration Page", 400, 23, "403/Forbidden", 23, null, null, null, null, null, null, null, null], "isController": false}, {"data": [], "isController": false}, {"data": ["POST /graphql - Obtain System Info", 400, 28, "Test failed: text expected not to contain /&quot;errors&quot;:/", 20, "502/Bad Gateway", 8, null, null, null, null, null, null], "isController": false}]}, function(index, item){
        return item;
    }, [[0, 0]], 0);

});
