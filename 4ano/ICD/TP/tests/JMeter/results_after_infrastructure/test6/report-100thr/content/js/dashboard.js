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

    var data = {"OkPercent": 98.7215909090909, "KoPercent": 1.2784090909090908};
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
    createTable($("#apdexTable"), {"supportsControllersDiscrimination": true, "overall": {"data": [0.390625, 500, 1500, "Total"], "isController": false}, "titles": ["Apdex", "T (Toleration threshold)", "F (Frustration threshold)", "Label"], "items": [{"data": [0.585, 500, 1500, "GET /login"], "isController": false}, {"data": [0.0, 500, 1500, "POST /graphql - Update Page"], "isController": false}, {"data": [0.0, 500, 1500, "GET /en/{searched-page} - View the Searched Page-0"], "isController": false}, {"data": [0.07, 500, 1500, "POST /graphql - Login"], "isController": false}, {"data": [0.0, 500, 1500, "GET /en/{searched-page} - View the Searched Page-1"], "isController": false}, {"data": [0.65, 500, 1500, "GET /e/en/{searched-page} - Edit Page"], "isController": false}, {"data": [1.0, 500, 1500, "GET /e/en/{searched-page} - Edit Page-0"], "isController": false}, {"data": [0.0, 500, 1500, "GET /e/en/{searched-page} - Edit Page-1"], "isController": false}, {"data": [0.395, 500, 1500, "GET /"], "isController": false}, {"data": [0.54, 500, 1500, "POST /graphql - Page Search"], "isController": false}, {"data": [0.5, 500, 1500, "GET /en/{searched-page} - View the Searched Page"], "isController": false}]}, function(index, item){
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
    createTable($("#statisticsTable"), {"supportsControllersDiscrimination": true, "overall": {"data": ["Total", 704, 9, 1.2784090909090908, 4809.409090909092, 49, 31634, 1727.5, 14556.0, 20865.5, 30506.000000000036, 14.542750315024065, 29.841146855440105, 11.90904341780454], "isController": false}, "titles": ["Label", "#Samples", "FAIL", "Error %", "Average", "Min", "Max", "Median", "90th pct", "95th pct", "99th pct", "Transactions/s", "Received", "Sent"], "items": [{"data": ["GET /login", 100, 0, 0.0, 959.1399999999999, 49, 3096, 1083.5, 2023.9, 2070.65, 3093.759999999999, 12.651821862348179, 28.35552509014423, 1.5197012588562753], "isController": false}, {"data": ["POST /graphql - Update Page", 100, 3, 3.0, 17915.62, 140, 31634, 16366.0, 29608.8, 30994.75, 31630.75, 2.3110166162094705, 1.4505917285826535, 4.29878429693097], "isController": false}, {"data": ["GET /en/{searched-page} - View the Searched Page-0", 1, 0, 0.0, 1712.0, 1712, 1712, 1712.0, 1712.0, 1712.0, 1712.0, 0.5841121495327103, 0.2424293589369159, 0.502541800525701], "isController": false}, {"data": ["POST /graphql - Login", 100, 1, 1.0, 7790.41, 64, 15322, 8122.0, 13986.1, 14769.7, 15321.44, 5.488172987212557, 7.7356977354426215, 3.794557104439932], "isController": false}, {"data": ["GET /en/{searched-page} - View the Searched Page-1", 1, 1, 100.0, 55.0, 55, 55, 55.0, 55.0, 55.0, 55.0, 18.18181818181818, 41.58380681818182, 15.625], "isController": false}, {"data": ["GET /e/en/{searched-page} - Edit Page", 100, 1, 1.0, 1322.87, 57, 8676, 304.5, 4588.3, 5473.0, 8657.35999999999, 5.31236719082023, 15.573121414152144, 4.620929398640033], "isController": false}, {"data": ["GET /e/en/{searched-page} - Edit Page-0", 1, 0, 0.0, 51.0, 51, 51, 51.0, 51.0, 51.0, 51.0, 19.607843137254903, 8.214613970588236, 16.90793504901961], "isController": false}, {"data": ["GET /e/en/{searched-page} - Edit Page-1", 1, 1, 100.0, 247.0, 247, 247, 247.0, 247.0, 247.0, 247.0, 4.048582995951417, 9.927726467611336, 3.4871584008097165], "isController": false}, {"data": ["GET /", 100, 0, 0.0, 1713.9300000000003, 80, 4232, 1340.0, 3466.1000000000004, 4035.899999999994, 4230.889999999999, 20.242914979757085, 64.30881515688259, 2.3326796558704452], "isController": false}, {"data": ["POST /graphql - Page Search", 100, 1, 1.0, 1835.5599999999995, 56, 7577, 488.5, 5591.600000000004, 7319.299999999988, 7576.19, 5.678914191606565, 3.8593324081435627, 6.8488592481117605], "isController": false}, {"data": ["GET /en/{searched-page} - View the Searched Page", 100, 1, 1.0, 2300.0599999999995, 49, 10484, 884.5, 7491.9, 9244.449999999995, 10475.479999999996, 5.241639584862145, 17.42169481863927, 4.549067479557606], "isController": false}]}, function(index, item){
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
    createTable($("#errorsTable"), {"supportsControllersDiscrimination": false, "titles": ["Type of error", "Number of errors", "% in errors", "% in all samples"], "items": [{"data": ["502/Bad Gateway", 4, 44.44444444444444, 0.5681818181818182], "isController": false}, {"data": ["500/Internal Server Error", 2, 22.22222222222222, 0.2840909090909091], "isController": false}, {"data": ["Test failed: text expected not to contain /&quot;errors&quot;:/", 1, 11.11111111111111, 0.14204545454545456], "isController": false}, {"data": ["404/Not Found", 2, 22.22222222222222, 0.2840909090909091], "isController": false}]}, function(index, item){
        switch(index){
            case 2:
            case 3:
                item = item.toFixed(2) + '%';
                break;
        }
        return item;
    }, [[1, 1]]);

        // Create top5 errors by sampler
    createTable($("#top5ErrorsBySamplerTable"), {"supportsControllersDiscrimination": false, "overall": {"data": ["Total", 704, 9, "502/Bad Gateway", 4, "500/Internal Server Error", 2, "404/Not Found", 2, "Test failed: text expected not to contain /&quot;errors&quot;:/", 1, null, null], "isController": false}, "titles": ["Sample", "#Samples", "#Errors", "Error", "#Errors", "Error", "#Errors", "Error", "#Errors", "Error", "#Errors", "Error", "#Errors"], "items": [{"data": [], "isController": false}, {"data": ["POST /graphql - Update Page", 100, 3, "502/Bad Gateway", 2, "Test failed: text expected not to contain /&quot;errors&quot;:/", 1, null, null, null, null, null, null], "isController": false}, {"data": [], "isController": false}, {"data": ["POST /graphql - Login", 100, 1, "502/Bad Gateway", 1, null, null, null, null, null, null, null, null], "isController": false}, {"data": ["GET /en/{searched-page} - View the Searched Page-1", 1, 1, "404/Not Found", 1, null, null, null, null, null, null, null, null], "isController": false}, {"data": ["GET /e/en/{searched-page} - Edit Page", 100, 1, "500/Internal Server Error", 1, null, null, null, null, null, null, null, null], "isController": false}, {"data": [], "isController": false}, {"data": ["GET /e/en/{searched-page} - Edit Page-1", 1, 1, "500/Internal Server Error", 1, null, null, null, null, null, null, null, null], "isController": false}, {"data": [], "isController": false}, {"data": ["POST /graphql - Page Search", 100, 1, "502/Bad Gateway", 1, null, null, null, null, null, null, null, null], "isController": false}, {"data": ["GET /en/{searched-page} - View the Searched Page", 100, 1, "404/Not Found", 1, null, null, null, null, null, null, null, null], "isController": false}]}, function(index, item){
        return item;
    }, [[0, 0]], 0);

});
