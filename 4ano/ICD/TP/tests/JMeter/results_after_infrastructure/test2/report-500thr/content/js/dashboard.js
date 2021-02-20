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

    var data = {"OkPercent": 76.68571428571428, "KoPercent": 23.314285714285713};
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
    createTable($("#apdexTable"), {"supportsControllersDiscrimination": true, "overall": {"data": [0.16385714285714287, 500, 1500, "Total"], "isController": false}, "titles": ["Apdex", "T (Toleration threshold)", "F (Frustration threshold)", "Label"], "items": [{"data": [0.406, 500, 1500, "GET /login"], "isController": false}, {"data": [0.001, 500, 1500, "POST /graphql - Login"], "isController": false}, {"data": [0.026, 500, 1500, "POST /graphql - Tree (List Directories)"], "isController": false}, {"data": [0.0, 500, 1500, "POST /graphql - Create Page"], "isController": false}, {"data": [0.122, 500, 1500, "GET /e/en/{new-page-path} - Edit New Page"], "isController": false}, {"data": [0.409, 500, 1500, "GET /"], "isController": false}, {"data": [0.183, 500, 1500, "GET /en/{new-page-path} - View New Page"], "isController": false}]}, function(index, item){
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
    createTable($("#statisticsTable"), {"supportsControllersDiscrimination": true, "overall": {"data": ["Total", 3500, 816, 23.314285714285713, 25169.76485714286, 55, 129216, 8309.0, 120204.9, 121343.8, 123937.79, 13.805126829672346, 285.42898599617797, 11.295659840688836], "isController": false}, "titles": ["Label", "#Samples", "FAIL", "Error %", "Average", "Min", "Max", "Median", "90th pct", "95th pct", "99th pct", "Transactions/s", "Received", "Sent"], "items": [{"data": ["GET /login", 500, 0, 0.0, 4885.334000000001, 61, 25924, 2671.5, 12335.6, 13077.699999999997, 25488.99, 12.433171702101207, 27.890493868892204, 1.4934376165609846], "isController": false}, {"data": ["POST /graphql - Login", 500, 22, 4.4, 30132.261999999977, 227, 53395, 30900.0, 53090.5, 53216.4, 53351.81, 7.651929051313836, 10.540382816445526, 5.290591570634957], "isController": false}, {"data": ["POST /graphql - Tree (List Directories)", 500, 15, 3.0, 13754.553999999996, 93, 43042, 8080.0, 37906.3, 42276.15, 42908.91, 5.394616173059287, 712.5026130172088, 6.4378422209634785], "isController": false}, {"data": ["POST /graphql - Create Page", 500, 397, 79.4, 105190.25400000003, 133, 129216, 120706.5, 123286.8, 125062.8, 127988.53000000001, 2.11102291726479, 1.1040402471796733, 4.016666393992873], "isController": false}, {"data": ["GET /e/en/{new-page-path} - Edit New Page", 500, 0, 0.0, 13624.441999999995, 59, 120117, 9246.5, 34019.0, 42032.5, 42669.94, 2.5519839122934167, 6.752723883953636, 2.177679646907506], "isController": false}, {"data": ["GET /", 500, 0, 0.0, 6341.01, 86, 22611, 3854.5, 18360.4, 18587.25, 18745.0, 21.373001624348124, 67.80434486406772, 2.462904484055741], "isController": false}, {"data": ["GET /en/{new-page-path} - View New Page", 500, 382, 76.4, 2260.498000000001, 55, 50993, 109.5, 4917.100000000005, 18533.74999999999, 35563.100000000006, 2.1239539526783062, 5.713950527802558, 1.8082812964614927], "isController": false}]}, function(index, item){
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
    createTable($("#errorsTable"), {"supportsControllersDiscrimination": false, "titles": ["Type of error", "Number of errors", "% in errors", "% in all samples"], "items": [{"data": ["502/Bad Gateway", 414, 50.73529411764706, 11.82857142857143], "isController": false}, {"data": ["Test failed: text expected not to contain /&quot;errors&quot;:/", 20, 2.450980392156863, 0.5714285714285714], "isController": false}, {"data": ["500/Internal Server Error", 351, 43.01470588235294, 10.028571428571428], "isController": false}, {"data": ["404/Not Found", 31, 3.799019607843137, 0.8857142857142857], "isController": false}]}, function(index, item){
        switch(index){
            case 2:
            case 3:
                item = item.toFixed(2) + '%';
                break;
        }
        return item;
    }, [[1, 1]]);

        // Create top5 errors by sampler
    createTable($("#top5ErrorsBySamplerTable"), {"supportsControllersDiscrimination": false, "overall": {"data": ["Total", 3500, 816, "502/Bad Gateway", 414, "500/Internal Server Error", 351, "404/Not Found", 31, "Test failed: text expected not to contain /&quot;errors&quot;:/", 20, null, null], "isController": false}, "titles": ["Sample", "#Samples", "#Errors", "Error", "#Errors", "Error", "#Errors", "Error", "#Errors", "Error", "#Errors", "Error", "#Errors"], "items": [{"data": [], "isController": false}, {"data": ["POST /graphql - Login", 500, 22, "502/Bad Gateway", 22, null, null, null, null, null, null, null, null], "isController": false}, {"data": ["POST /graphql - Tree (List Directories)", 500, 15, "502/Bad Gateway", 15, null, null, null, null, null, null, null, null], "isController": false}, {"data": ["POST /graphql - Create Page", 500, 397, "502/Bad Gateway", 377, "Test failed: text expected not to contain /&quot;errors&quot;:/", 20, null, null, null, null, null, null], "isController": false}, {"data": [], "isController": false}, {"data": [], "isController": false}, {"data": ["GET /en/{new-page-path} - View New Page", 500, 382, "500/Internal Server Error", 351, "404/Not Found", 31, null, null, null, null, null, null], "isController": false}]}, function(index, item){
        return item;
    }, [[0, 0]], 0);

});
