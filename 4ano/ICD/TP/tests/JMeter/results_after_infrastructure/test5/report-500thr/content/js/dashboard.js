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

    var data = {"OkPercent": 93.42857142857143, "KoPercent": 6.571428571428571};
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
    createTable($("#apdexTable"), {"supportsControllersDiscrimination": true, "overall": {"data": [0.4825714285714286, 500, 1500, "Total"], "isController": false}, "titles": ["Apdex", "T (Toleration threshold)", "F (Frustration threshold)", "Label"], "items": [{"data": [0.404, 500, 1500, "GET /login"], "isController": false}, {"data": [0.004, 500, 1500, "POST /graphql - Login"], "isController": false}, {"data": [0.69, 500, 1500, "POST /graphql - Obtain Recent Pages"], "isController": false}, {"data": [0.716, 500, 1500, "POST /graphql - Obtain Pages"], "isController": false}, {"data": [0.534, 500, 1500, "GET /a - Administration Page"], "isController": false}, {"data": [0.434, 500, 1500, "GET /"], "isController": false}, {"data": [0.596, 500, 1500, "POST /graphql - Obtain System Info"], "isController": false}]}, function(index, item){
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
    createTable($("#statisticsTable"), {"supportsControllersDiscrimination": true, "overall": {"data": ["Total", 3500, 230, 6.571428571428571, 7624.274857142869, 44, 52764, 570.5, 34227.4, 43314.9, 52665.909999999996, 59.874093335158065, 357.8426809534094, 42.94903698935951], "isController": false}, "titles": ["Label", "#Samples", "FAIL", "Error %", "Average", "Min", "Max", "Median", "90th pct", "95th pct", "99th pct", "Transactions/s", "Received", "Sent"], "items": [{"data": ["GET /login", 500, 2, 0.4, 7076.945999999997, 68, 28663, 7023.0, 20915.0, 24730.75, 27380.99, 11.55107887076653, 25.69855600684979, 1.3874831065471516], "isController": false}, {"data": ["POST /graphql - Login", 500, 51, 10.2, 35909.779999999984, 46, 52764, 38413.0, 52431.4, 52693.95, 52740.99, 8.802197028378284, 11.643742215557003, 6.085894039152172], "isController": false}, {"data": ["POST /graphql - Obtain Recent Pages", 500, 35, 7.0, 1501.1019999999996, 46, 17407, 281.5, 6292.8, 6727.55, 17398.93, 14.124692787931863, 45.49778727859544, 16.320530770643238], "isController": false}, {"data": ["POST /graphql - Obtain Pages", 500, 26, 5.2, 907.4780000000005, 48, 19273, 390.5, 1219.0, 3464.8999999999983, 15429.350000000057, 14.077369221239936, 410.17087726645644, 15.537236401261332], "isController": false}, {"data": ["GET /a - Administration Page", 500, 57, 11.4, 1690.9039999999989, 44, 28078, 114.0, 5739.800000000001, 6526.399999999999, 17724.910000000033, 11.34790404212342, 23.86023158944418, 8.945251327704772], "isController": false}, {"data": ["GET /", 500, 0, 0.0, 3798.1420000000003, 112, 14058, 3602.0, 9942.300000000001, 10130.95, 14045.93, 33.74502260916515, 107.33883060842275, 3.888586589728015], "isController": false}, {"data": ["POST /graphql - Obtain System Info", 500, 59, 11.8, 2485.571999999999, 46, 21456, 351.0, 7140.0, 14674.35, 17409.94, 13.63252174387218, 8.819762299942743, 14.274102298443166], "isController": false}]}, function(index, item){
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
    createTable($("#errorsTable"), {"supportsControllersDiscrimination": false, "titles": ["Type of error", "Number of errors", "% in errors", "% in all samples"], "items": [{"data": ["502/Bad Gateway", 177, 76.95652173913044, 5.057142857142857], "isController": false}, {"data": ["Test failed: text expected not to contain /&quot;errors&quot;:/", 24, 10.434782608695652, 0.6857142857142857], "isController": false}, {"data": ["403/Forbidden", 29, 12.608695652173912, 0.8285714285714286], "isController": false}]}, function(index, item){
        switch(index){
            case 2:
            case 3:
                item = item.toFixed(2) + '%';
                break;
        }
        return item;
    }, [[1, 1]]);

        // Create top5 errors by sampler
    createTable($("#top5ErrorsBySamplerTable"), {"supportsControllersDiscrimination": false, "overall": {"data": ["Total", 3500, 230, "502/Bad Gateway", 177, "403/Forbidden", 29, "Test failed: text expected not to contain /&quot;errors&quot;:/", 24, null, null, null, null], "isController": false}, "titles": ["Sample", "#Samples", "#Errors", "Error", "#Errors", "Error", "#Errors", "Error", "#Errors", "Error", "#Errors", "Error", "#Errors"], "items": [{"data": ["GET /login", 500, 2, "502/Bad Gateway", 2, null, null, null, null, null, null, null, null], "isController": false}, {"data": ["POST /graphql - Login", 500, 51, "502/Bad Gateway", 51, null, null, null, null, null, null, null, null], "isController": false}, {"data": ["POST /graphql - Obtain Recent Pages", 500, 35, "502/Bad Gateway", 35, null, null, null, null, null, null, null, null], "isController": false}, {"data": ["POST /graphql - Obtain Pages", 500, 26, "502/Bad Gateway", 26, null, null, null, null, null, null, null, null], "isController": false}, {"data": ["GET /a - Administration Page", 500, 57, "403/Forbidden", 29, "502/Bad Gateway", 28, null, null, null, null, null, null], "isController": false}, {"data": [], "isController": false}, {"data": ["POST /graphql - Obtain System Info", 500, 59, "502/Bad Gateway", 35, "Test failed: text expected not to contain /&quot;errors&quot;:/", 24, null, null, null, null, null, null], "isController": false}]}, function(index, item){
        return item;
    }, [[0, 0]], 0);

});
