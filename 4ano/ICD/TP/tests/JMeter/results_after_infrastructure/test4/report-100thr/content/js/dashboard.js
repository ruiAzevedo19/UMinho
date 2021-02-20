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

    var data = {"OkPercent": 99.3, "KoPercent": 0.7};
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
    createTable($("#apdexTable"), {"supportsControllersDiscrimination": true, "overall": {"data": [0.441, 500, 1500, "Total"], "isController": false}, "titles": ["Apdex", "T (Toleration threshold)", "F (Frustration threshold)", "Label"], "items": [{"data": [0.555, 500, 1500, "GET /login"], "isController": false}, {"data": [0.545, 500, 1500, "POST /graphql - Obtain Groups List"], "isController": false}, {"data": [0.215, 500, 1500, "POST /graphql - Create User"], "isController": false}, {"data": [0.56, 500, 1500, "POST /graphql - Obtain Updated Users List"], "isController": false}, {"data": [0.08, 500, 1500, "POST /graphql - Login"], "isController": false}, {"data": [0.44, 500, 1500, "POST /graphql - Obtain Recent Pages"], "isController": false}, {"data": [0.61, 500, 1500, "POST /graphql - Obtain Users List"], "isController": false}, {"data": [0.625, 500, 1500, "GET /a - Administration Page"], "isController": false}, {"data": [0.41, 500, 1500, "GET /"], "isController": false}, {"data": [0.37, 500, 1500, "POST /graphql - Obtain System Info"], "isController": false}]}, function(index, item){
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
    createTable($("#statisticsTable"), {"supportsControllersDiscrimination": true, "overall": {"data": ["Total", 1000, 7, 0.7, 2479.1739999999995, 49, 17303, 1162.0, 6876.599999999999, 9441.899999999998, 16255.89, 29.613835583984837, 83.48906624800107, 26.568122001599146], "isController": false}, "titles": ["Label", "#Samples", "FAIL", "Error %", "Average", "Min", "Max", "Median", "90th pct", "95th pct", "99th pct", "Transactions/s", "Received", "Sent"], "items": [{"data": ["GET /login", 100, 0, 0.0, 1010.0800000000003, 50, 2737, 782.0, 2623.9000000000024, 2681.9, 2736.99, 14.923145799134456, 33.40103156245337, 1.792526302044471], "isController": false}, {"data": ["POST /graphql - Obtain Groups List", 100, 2, 2.0, 1271.8600000000001, 50, 6929, 453.5, 3061.3, 4349.449999999992, 6914.369999999993, 4.328442193654504, 2.667571581612777, 4.510202949833355], "isController": false}, {"data": ["POST /graphql - Create User", 100, 0, 0.0, 3882.8300000000004, 428, 10081, 4047.5, 8620.1, 9277.35, 10078.179999999998, 3.79247572815534, 2.4036296363015777, 6.158476894341627], "isController": false}, {"data": ["POST /graphql - Obtain Updated Users List", 100, 0, 0.0, 1103.9799999999996, 50, 4498, 601.5, 2597.1000000000004, 3734.7499999999986, 4497.129999999999, 3.856983067844332, 40.79799171712886, 4.19974230531878], "isController": false}, {"data": ["POST /graphql - Login", 100, 0, 0.0, 8808.38, 431, 17303, 8284.0, 16254.9, 16598.95, 17301.11, 4.643603436266543, 6.589019328999304, 3.210616438356164], "isController": false}, {"data": ["POST /graphql - Obtain Recent Pages", 100, 4, 4.0, 2080.3799999999997, 51, 9521, 947.5, 5627.100000000005, 8299.149999999996, 9520.26, 3.826286588865506, 4.589152477520566, 4.689443036158408], "isController": false}, {"data": ["POST /graphql - Obtain Users List", 100, 0, 0.0, 1277.2899999999997, 50, 8443, 413.0, 3667.6000000000004, 4985.449999999999, 8435.779999999997, 4.151961801951422, 22.98260879437409, 4.520934969898277], "isController": false}, {"data": ["GET /a - Administration Page", 100, 0, 0.0, 907.3799999999997, 49, 3652, 524.0, 2737.700000000001, 2920.7, 3646.6799999999976, 4.964996772752098, 10.856857591480066, 4.261945471922943], "isController": false}, {"data": ["GET /", 100, 0, 0.0, 1667.5099999999993, 83, 3572, 1234.5, 3294.1, 3431.1499999999996, 3571.9, 24.073182474723158, 76.40437868620607, 2.7740581367356767], "isController": false}, {"data": ["POST /graphql - Obtain System Info", 100, 1, 1.0, 2782.0500000000006, 50, 9421, 2391.5, 8130.700000000003, 8891.999999999998, 9420.84, 4.150410890678177, 2.5417618883331947, 4.636787166929526], "isController": false}]}, function(index, item){
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
    createTable($("#errorsTable"), {"supportsControllersDiscrimination": false, "titles": ["Type of error", "Number of errors", "% in errors", "% in all samples"], "items": [{"data": ["502/Bad Gateway", 7, 100.0, 0.7], "isController": false}]}, function(index, item){
        switch(index){
            case 2:
            case 3:
                item = item.toFixed(2) + '%';
                break;
        }
        return item;
    }, [[1, 1]]);

        // Create top5 errors by sampler
    createTable($("#top5ErrorsBySamplerTable"), {"supportsControllersDiscrimination": false, "overall": {"data": ["Total", 1000, 7, "502/Bad Gateway", 7, null, null, null, null, null, null, null, null], "isController": false}, "titles": ["Sample", "#Samples", "#Errors", "Error", "#Errors", "Error", "#Errors", "Error", "#Errors", "Error", "#Errors", "Error", "#Errors"], "items": [{"data": [], "isController": false}, {"data": ["POST /graphql - Obtain Groups List", 100, 2, "502/Bad Gateway", 2, null, null, null, null, null, null, null, null], "isController": false}, {"data": [], "isController": false}, {"data": [], "isController": false}, {"data": [], "isController": false}, {"data": ["POST /graphql - Obtain Recent Pages", 100, 4, "502/Bad Gateway", 4, null, null, null, null, null, null, null, null], "isController": false}, {"data": [], "isController": false}, {"data": [], "isController": false}, {"data": [], "isController": false}, {"data": ["POST /graphql - Obtain System Info", 100, 1, "502/Bad Gateway", 1, null, null, null, null, null, null, null, null], "isController": false}]}, function(index, item){
        return item;
    }, [[0, 0]], 0);

});
