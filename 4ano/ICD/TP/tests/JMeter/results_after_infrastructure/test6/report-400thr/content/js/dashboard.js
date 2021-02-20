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

    var data = {"OkPercent": 94.56824512534818, "KoPercent": 5.43175487465181};
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
    createTable($("#apdexTable"), {"supportsControllersDiscrimination": true, "overall": {"data": [0.26340529247910865, 500, 1500, "Total"], "isController": false}, "titles": ["Apdex", "T (Toleration threshold)", "F (Frustration threshold)", "Label"], "items": [{"data": [0.34, 500, 1500, "GET /login"], "isController": false}, {"data": [0.0, 500, 1500, "POST /graphql - Update Page"], "isController": false}, {"data": [0.25, 500, 1500, "GET /en/{searched-page} - View the Searched Page-0"], "isController": false}, {"data": [0.0, 500, 1500, "POST /graphql - Login"], "isController": false}, {"data": [0.0, 500, 1500, "GET /en/{searched-page} - View the Searched Page-1"], "isController": false}, {"data": [0.41, 500, 1500, "GET /e/en/{searched-page} - Edit Page"], "isController": false}, {"data": [0.9722222222222222, 500, 1500, "GET /e/en/{searched-page} - Edit Page-0"], "isController": false}, {"data": [0.0, 500, 1500, "GET /e/en/{searched-page} - Edit Page-1"], "isController": false}, {"data": [0.33875, 500, 1500, "GET /"], "isController": false}, {"data": [0.31, 500, 1500, "POST /graphql - Page Search"], "isController": false}, {"data": [0.4375, 500, 1500, "GET /en/{searched-page} - View the Searched Page"], "isController": false}]}, function(index, item){
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
    createTable($("#statisticsTable"), {"supportsControllersDiscrimination": true, "overall": {"data": ["Total", 2872, 156, 5.43175487465181, 12947.202646239579, 44, 79366, 3843.0, 46689.0, 54067.4, 74985.35, 20.368649867731435, 41.29402644972376, 16.31827134647981], "isController": false}, "titles": ["Label", "#Samples", "FAIL", "Error %", "Average", "Min", "Max", "Median", "90th pct", "95th pct", "99th pct", "Transactions/s", "Received", "Sent"], "items": [{"data": ["GET /login", 400, 0, 0.0, 3858.9724999999994, 54, 12442, 3682.0, 9411.9, 10097.349999999999, 11817.570000000005, 20.041084222656448, 44.95177026967784, 2.4072786712761163], "isController": false}, {"data": ["POST /graphql - Update Page", 400, 32, 8.0, 39675.04750000001, 49, 79366, 45493.5, 61640.1, 75056.5, 76004.46, 3.370833860026124, 2.1257650212783887, 6.123149333206927], "isController": false}, {"data": ["GET /en/{searched-page} - View the Searched Page-0", 18, 0, 0.0, 5557.333333333333, 51, 11172, 6346.5, 9928.200000000003, 11172.0, 11172.0, 0.3642102707296346, 0.15116148931649873, 0.28552725759783093], "isController": false}, {"data": ["POST /graphql - Login", 400, 29, 7.25, 32118.16249999999, 102, 55259, 41452.5, 51942.0, 54163.55, 55013.4, 7.055793688592545, 9.529714124596497, 4.87841985500344], "isController": false}, {"data": ["GET /en/{searched-page} - View the Searched Page-1", 18, 18, 100.0, 5788.555555555556, 56, 31131, 81.5, 31122.9, 31131.0, 31131.0, 0.4043671653861706, 0.9240421552769914, 0.3166138742867412], "isController": false}, {"data": ["GET /e/en/{searched-page} - Edit Page", 400, 18, 4.5, 3373.36, 56, 35752, 2312.0, 6241.8, 7192.7, 29433.79, 7.907951445178127, 23.07998182406785, 6.748478954964217], "isController": false}, {"data": ["GET /e/en/{searched-page} - Edit Page-0", 18, 0, 0.0, 134.66666666666666, 44, 759, 54.5, 389.1000000000006, 759.0, 759.0, 0.44848635853992774, 0.18789125763049708, 0.3524725146380964], "isController": false}, {"data": ["GET /e/en/{searched-page} - Edit Page-1", 18, 18, 100.0, 147.05555555555554, 47, 1673, 54.0, 258.2000000000022, 1673.0, 1673.0, 0.438489646772229, 1.0752417021924483, 0.34418772838002437], "isController": false}, {"data": ["GET /", 400, 0, 0.0, 2884.3674999999994, 94, 8081, 2743.5, 6260.400000000001, 7462.349999999999, 8039.84, 44.449383264807196, 141.0639593635404, 5.122096899655517], "isController": false}, {"data": ["POST /graphql - Page Search", 400, 18, 4.5, 7494.739999999994, 55, 36134, 2928.0, 29913.7, 31832.0, 34722.93, 7.237853976296028, 4.866290543291414, 8.417963448837419], "isController": false}, {"data": ["GET /en/{searched-page} - View the Searched Page", 400, 23, 5.75, 3033.0224999999978, 49, 42305, 1185.0, 4828.9, 13684.399999999994, 38942.55000000001, 7.059778675938509, 23.415851850544485, 6.010257086252846], "isController": false}]}, function(index, item){
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
    createTable($("#errorsTable"), {"supportsControllersDiscrimination": false, "titles": ["Type of error", "Number of errors", "% in errors", "% in all samples"], "items": [{"data": ["502/Bad Gateway", 51, 32.69230769230769, 1.775766016713092], "isController": false}, {"data": ["500/Internal Server Error", 41, 26.28205128205128, 1.4275766016713092], "isController": false}, {"data": ["Test failed: text expected not to contain /&quot;errors&quot;:/", 28, 17.94871794871795, 0.9749303621169917], "isController": false}, {"data": ["404/Not Found", 36, 23.076923076923077, 1.2534818941504178], "isController": false}]}, function(index, item){
        switch(index){
            case 2:
            case 3:
                item = item.toFixed(2) + '%';
                break;
        }
        return item;
    }, [[1, 1]]);

        // Create top5 errors by sampler
    createTable($("#top5ErrorsBySamplerTable"), {"supportsControllersDiscrimination": false, "overall": {"data": ["Total", 2872, 156, "502/Bad Gateway", 51, "500/Internal Server Error", 41, "404/Not Found", 36, "Test failed: text expected not to contain /&quot;errors&quot;:/", 28, null, null], "isController": false}, "titles": ["Sample", "#Samples", "#Errors", "Error", "#Errors", "Error", "#Errors", "Error", "#Errors", "Error", "#Errors", "Error", "#Errors"], "items": [{"data": [], "isController": false}, {"data": ["POST /graphql - Update Page", 400, 32, "Test failed: text expected not to contain /&quot;errors&quot;:/", 28, "502/Bad Gateway", 4, null, null, null, null, null, null], "isController": false}, {"data": [], "isController": false}, {"data": ["POST /graphql - Login", 400, 29, "502/Bad Gateway", 29, null, null, null, null, null, null, null, null], "isController": false}, {"data": ["GET /en/{searched-page} - View the Searched Page-1", 18, 18, "404/Not Found", 18, null, null, null, null, null, null, null, null], "isController": false}, {"data": ["GET /e/en/{searched-page} - Edit Page", 400, 18, "500/Internal Server Error", 18, null, null, null, null, null, null, null, null], "isController": false}, {"data": [], "isController": false}, {"data": ["GET /e/en/{searched-page} - Edit Page-1", 18, 18, "500/Internal Server Error", 18, null, null, null, null, null, null, null, null], "isController": false}, {"data": [], "isController": false}, {"data": ["POST /graphql - Page Search", 400, 18, "502/Bad Gateway", 18, null, null, null, null, null, null, null, null], "isController": false}, {"data": ["GET /en/{searched-page} - View the Searched Page", 400, 23, "404/Not Found", 18, "500/Internal Server Error", 5, null, null, null, null, null, null], "isController": false}]}, function(index, item){
        return item;
    }, [[0, 0]], 0);

});
