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

    var data = {"OkPercent": 85.23809523809524, "KoPercent": 14.761904761904763};
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
    createTable($("#apdexTable"), {"supportsControllersDiscrimination": true, "overall": {"data": [0.21785714285714286, 500, 1500, "Total"], "isController": false}, "titles": ["Apdex", "T (Toleration threshold)", "F (Frustration threshold)", "Label"], "items": [{"data": [0.37666666666666665, 500, 1500, "GET /login"], "isController": false}, {"data": [0.0033333333333333335, 500, 1500, "POST /graphql - Login"], "isController": false}, {"data": [0.16166666666666665, 500, 1500, "POST /graphql - Tree (List Directories)"], "isController": false}, {"data": [0.0, 500, 1500, "POST /graphql - Create Page"], "isController": false}, {"data": [0.23333333333333334, 500, 1500, "GET /e/en/{new-page-path} - Edit New Page"], "isController": false}, {"data": [0.4, 500, 1500, "GET /"], "isController": false}, {"data": [0.35, 500, 1500, "GET /en/{new-page-path} - View New Page"], "isController": false}]}, function(index, item){
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
    createTable($("#statisticsTable"), {"supportsControllersDiscrimination": true, "overall": {"data": ["Total", 2100, 310, 14.761904761904763, 16738.24571428572, 51, 124749, 4360.0, 66550.6, 87975.14999999998, 122704.95999999998, 11.24160894189694, 90.90695611356166, 9.052736361519436], "isController": false}, "titles": ["Label", "#Samples", "FAIL", "Error %", "Average", "Min", "Max", "Median", "90th pct", "95th pct", "99th pct", "Transactions/s", "Received", "Sent"], "items": [{"data": ["GET /login", 300, 0, 0.0, 3157.310000000001, 53, 16370, 2764.5, 5688.200000000001, 12996.95, 15159.92, 14.026556947821208, 31.521262287848323, 1.6848305708808677], "isController": false}, {"data": ["POST /graphql - Login", 300, 23, 7.666666666666667, 20706.663333333352, 61, 42378, 22897.5, 37503.5, 40562.55, 42373.96, 6.304905216258248, 8.490790405247783, 4.359250872178555], "isController": false}, {"data": ["POST /graphql - Tree (List Directories)", 300, 14, 4.666666666666667, 6089.749999999998, 71, 25058, 4457.0, 14379.300000000001, 16107.299999999997, 22176.86, 5.6213461250187375, 245.85081708608155, 6.582171783653125], "isController": false}, {"data": ["POST /graphql - Create Page", 300, 110, 36.666666666666664, 78012.77666666661, 52, 124749, 75638.5, 122080.7, 123401.25, 124533.23, 1.7583963425356075, 1.0753463674462223, 3.3054874787527107], "isController": false}, {"data": ["GET /e/en/{new-page-path} - Edit New Page", 300, 0, 0.0, 4820.693333333335, 54, 18493, 2426.0, 13020.10000000003, 17284.0, 18065.88, 5.60779109108922, 14.636334747742865, 4.658555619941305], "isController": false}, {"data": ["GET /", 300, 0, 0.0, 2930.346666666666, 84, 9820, 2516.5, 6729.500000000001, 7008.4, 9799.94, 28.65876958349255, 90.83337313717998, 3.3024754012227744], "isController": false}, {"data": ["GET /en/{new-page-path} - View New Page", 300, 163, 54.333333333333336, 1450.1800000000003, 51, 17746, 102.0, 3952.6000000000113, 11377.949999999993, 16389.62, 1.809900153841513, 5.224001067086966, 1.5000018853126602], "isController": false}]}, function(index, item){
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
    createTable($("#errorsTable"), {"supportsControllersDiscrimination": false, "titles": ["Type of error", "Number of errors", "% in errors", "% in all samples"], "items": [{"data": ["502/Bad Gateway", 125, 40.32258064516129, 5.9523809523809526], "isController": false}, {"data": ["Test failed: text expected not to contain /&quot;errors&quot;:/", 22, 7.096774193548387, 1.0476190476190477], "isController": false}, {"data": ["500/Internal Server Error", 137, 44.193548387096776, 6.523809523809524], "isController": false}, {"data": ["404/Not Found", 26, 8.387096774193548, 1.2380952380952381], "isController": false}]}, function(index, item){
        switch(index){
            case 2:
            case 3:
                item = item.toFixed(2) + '%';
                break;
        }
        return item;
    }, [[1, 1]]);

        // Create top5 errors by sampler
    createTable($("#top5ErrorsBySamplerTable"), {"supportsControllersDiscrimination": false, "overall": {"data": ["Total", 2100, 310, "500/Internal Server Error", 137, "502/Bad Gateway", 125, "404/Not Found", 26, "Test failed: text expected not to contain /&quot;errors&quot;:/", 22, null, null], "isController": false}, "titles": ["Sample", "#Samples", "#Errors", "Error", "#Errors", "Error", "#Errors", "Error", "#Errors", "Error", "#Errors", "Error", "#Errors"], "items": [{"data": [], "isController": false}, {"data": ["POST /graphql - Login", 300, 23, "502/Bad Gateway", 23, null, null, null, null, null, null, null, null], "isController": false}, {"data": ["POST /graphql - Tree (List Directories)", 300, 14, "502/Bad Gateway", 14, null, null, null, null, null, null, null, null], "isController": false}, {"data": ["POST /graphql - Create Page", 300, 110, "502/Bad Gateway", 88, "Test failed: text expected not to contain /&quot;errors&quot;:/", 22, null, null, null, null, null, null], "isController": false}, {"data": [], "isController": false}, {"data": [], "isController": false}, {"data": ["GET /en/{new-page-path} - View New Page", 300, 163, "500/Internal Server Error", 137, "404/Not Found", 26, null, null, null, null, null, null], "isController": false}]}, function(index, item){
        return item;
    }, [[0, 0]], 0);

});
