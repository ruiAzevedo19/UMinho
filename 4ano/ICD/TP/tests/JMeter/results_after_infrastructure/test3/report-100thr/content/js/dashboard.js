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

    var data = {"OkPercent": 98.41269841269842, "KoPercent": 1.5873015873015872};
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
    createTable($("#apdexTable"), {"supportsControllersDiscrimination": true, "overall": {"data": [0.49007936507936506, 500, 1500, "Total"], "isController": false}, "titles": ["Apdex", "T (Toleration threshold)", "F (Frustration threshold)", "Label"], "items": [{"data": [0.59, 500, 1500, "GET /login"], "isController": false}, {"data": [0.5, 500, 1500, "GET /en/{searched-page} - View the Searched Page-0"], "isController": false}, {"data": [0.06, 500, 1500, "POST /graphql - Login"], "isController": false}, {"data": [0.0, 500, 1500, "GET /en/{searched-page} - View the Searched Page-1"], "isController": false}, {"data": [0.6, 500, 1500, "GET /"], "isController": false}, {"data": [0.585, 500, 1500, "POST /graphql - Page Search"], "isController": false}, {"data": [0.625, 500, 1500, "GET /en/{searched-page} - View the Searched Page"], "isController": false}]}, function(index, item){
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
    createTable($("#statisticsTable"), {"supportsControllersDiscrimination": true, "overall": {"data": ["Total", 504, 8, 1.5873015873015872, 2599.3908730158714, 48, 16777, 949.0, 7327.0, 12736.5, 15987.949999999999, 28.662420382165607, 60.63162445262739, 17.230574492436308], "isController": false}, "titles": ["Label", "#Samples", "FAIL", "Error %", "Average", "Min", "Max", "Median", "90th pct", "95th pct", "99th pct", "Transactions/s", "Received", "Sent"], "items": [{"data": ["GET /login", 100, 0, 0.0, 1283.47, 48, 6009, 665.5, 3567.0, 4085.3499999999985, 6009.0, 7.707129094412331, 17.2036066955684, 0.9257586705202312], "isController": false}, {"data": ["GET /en/{searched-page} - View the Searched Page-0", 2, 0, 0.0, 3182.0, 422, 5942, 3182.0, 5942.0, 5942.0, 5942.0, 0.3365870077415012, 0.13969675614271287, 0.28958315802760015], "isController": false}, {"data": ["POST /graphql - Login", 100, 2, 2.0, 6569.729999999998, 434, 16777, 3993.5, 14654.900000000005, 15987.949999999999, 16773.199999999997, 5.78368999421631, 8.097730805378832, 3.998879410063621], "isController": false}, {"data": ["GET /en/{searched-page} - View the Searched Page-1", 2, 2, 100.0, 259.0, 55, 463, 259.0, 463.0, 463.0, 463.0, 0.5998800239952009, 1.371991226754649, 0.5155218956208758], "isController": false}, {"data": ["GET /", 100, 0, 0.0, 1658.92, 85, 6404, 409.5, 4809.100000000004, 6282.95, 6403.67, 14.086491055078179, 44.66573196929145, 1.6232479926750245], "isController": false}, {"data": ["POST /graphql - Page Search", 100, 2, 2.0, 1719.9100000000008, 52, 8681, 633.5, 6638.500000000001, 7223.299999999998, 8676.019999999997, 6.623393826996954, 4.32279937740098, 7.942380613326268], "isController": false}, {"data": ["GET /en/{searched-page} - View the Searched Page", 100, 2, 2.0, 1800.0799999999988, 50, 10361, 317.5, 7426.0, 9308.65, 10358.919999999998, 6.866717022591499, 21.641692559912105, 5.970825036050264], "isController": false}]}, function(index, item){
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
    createTable($("#errorsTable"), {"supportsControllersDiscrimination": false, "titles": ["Type of error", "Number of errors", "% in errors", "% in all samples"], "items": [{"data": ["502/Bad Gateway", 4, 50.0, 0.7936507936507936], "isController": false}, {"data": ["404/Not Found", 4, 50.0, 0.7936507936507936], "isController": false}]}, function(index, item){
        switch(index){
            case 2:
            case 3:
                item = item.toFixed(2) + '%';
                break;
        }
        return item;
    }, [[1, 1]]);

        // Create top5 errors by sampler
    createTable($("#top5ErrorsBySamplerTable"), {"supportsControllersDiscrimination": false, "overall": {"data": ["Total", 504, 8, "502/Bad Gateway", 4, "404/Not Found", 4, null, null, null, null, null, null], "isController": false}, "titles": ["Sample", "#Samples", "#Errors", "Error", "#Errors", "Error", "#Errors", "Error", "#Errors", "Error", "#Errors", "Error", "#Errors"], "items": [{"data": [], "isController": false}, {"data": [], "isController": false}, {"data": ["POST /graphql - Login", 100, 2, "502/Bad Gateway", 2, null, null, null, null, null, null, null, null], "isController": false}, {"data": ["GET /en/{searched-page} - View the Searched Page-1", 2, 2, "404/Not Found", 2, null, null, null, null, null, null, null, null], "isController": false}, {"data": [], "isController": false}, {"data": ["POST /graphql - Page Search", 100, 2, "502/Bad Gateway", 2, null, null, null, null, null, null, null, null], "isController": false}, {"data": ["GET /en/{searched-page} - View the Searched Page", 100, 2, "404/Not Found", 2, null, null, null, null, null, null, null, null], "isController": false}]}, function(index, item){
        return item;
    }, [[0, 0]], 0);

});
