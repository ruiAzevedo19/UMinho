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

    var data = {"OkPercent": 96.3, "KoPercent": 3.7};
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
    createTable($("#apdexTable"), {"supportsControllersDiscrimination": true, "overall": {"data": [0.33675, 500, 1500, "Total"], "isController": false}, "titles": ["Apdex", "T (Toleration threshold)", "F (Frustration threshold)", "Label"], "items": [{"data": [0.4175, 500, 1500, "GET /login"], "isController": false}, {"data": [0.38, 500, 1500, "POST /graphql - Obtain Groups List"], "isController": false}, {"data": [0.145, 500, 1500, "POST /graphql - Create User"], "isController": false}, {"data": [0.4725, 500, 1500, "POST /graphql - Obtain Updated Users List"], "isController": false}, {"data": [0.04, 500, 1500, "POST /graphql - Login"], "isController": false}, {"data": [0.3, 500, 1500, "POST /graphql - Obtain Recent Pages"], "isController": false}, {"data": [0.425, 500, 1500, "POST /graphql - Obtain Users List"], "isController": false}, {"data": [0.52, 500, 1500, "GET /a - Administration Page"], "isController": false}, {"data": [0.2975, 500, 1500, "GET /"], "isController": false}, {"data": [0.37, 500, 1500, "POST /graphql - Obtain System Info"], "isController": false}]}, function(index, item){
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
    createTable($("#statisticsTable"), {"supportsControllersDiscrimination": true, "overall": {"data": ["Total", 2000, 74, 3.7, 4303.445000000004, 49, 31732, 2034.0, 13114.700000000006, 17678.85, 27435.350000000002, 35.088950489490855, 138.152051387768, 30.89280544931401], "isController": false}, "titles": ["Label", "#Samples", "FAIL", "Error %", "Average", "Min", "Max", "Median", "90th pct", "95th pct", "99th pct", "Transactions/s", "Received", "Sent"], "items": [{"data": ["GET /login", 200, 0, 0.0, 1969.6000000000001, 53, 5930, 1312.5, 4685.0, 4727.45, 5671.92, 13.81788033715628, 30.941121795978997, 1.659764923310764], "isController": false}, {"data": ["POST /graphql - Obtain Groups List", 200, 11, 5.5, 2541.16, 51, 10124, 1437.0, 8028.300000000002, 8843.899999999998, 9872.51, 4.1608588012565795, 2.5612036324297334, 4.235461699294734], "isController": false}, {"data": ["POST /graphql - Create User", 200, 11, 5.5, 5632.4299999999985, 51, 17018, 3828.0, 15180.5, 15687.0, 16698.980000000003, 4.093076560997074, 2.57959754824714, 6.552439985264924], "isController": false}, {"data": ["POST /graphql - Obtain Updated Users List", 200, 9, 4.5, 2201.4200000000014, 52, 9969, 997.0, 6547.700000000003, 8334.099999999999, 8857.91, 4.222616333079976, 79.30918821520565, 4.49626166497762], "isController": false}, {"data": ["POST /graphql - Login", 200, 7, 3.5, 14996.425000000001, 428, 31732, 17636.0, 27420.5, 29267.65, 31718.92, 6.267824124855057, 8.686965521091228, 4.333612773825567], "isController": false}, {"data": ["POST /graphql - Obtain Recent Pages", 200, 9, 4.5, 3989.165000000002, 55, 17232, 2068.5, 13511.900000000009, 15835.299999999996, 16642.73, 4.832902399536041, 5.778268136976536, 5.806845504192543], "isController": false}, {"data": ["POST /graphql - Obtain Users List", 200, 9, 4.5, 2702.005, 52, 15791, 1239.0, 8330.300000000001, 11641.449999999999, 15787.360000000004, 4.342162396873643, 37.03432004450716, 4.62355487407729], "isController": false}, {"data": ["GET /a - Administration Page", 200, 8, 4.0, 1382.9100000000003, 49, 4991, 881.0, 4048.1000000000004, 4520.849999999998, 4938.6, 6.805962022731913, 14.87405115318519, 5.678458704825427], "isController": false}, {"data": ["GET /", 200, 0, 0.0, 3400.1500000000024, 98, 8430, 3103.5, 8142.9, 8253.7, 8415.85, 22.341376228775694, 70.86296183813673, 2.574494526362824], "isController": false}, {"data": ["POST /graphql - Obtain System Info", 200, 10, 5.0, 4219.185, 50, 17962, 1787.5, 13695.200000000004, 14766.0, 16616.720000000005, 5.910340140075061, 3.76556197176335, 6.460740565619552], "isController": false}]}, function(index, item){
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
    createTable($("#errorsTable"), {"supportsControllersDiscrimination": false, "titles": ["Type of error", "Number of errors", "% in errors", "% in all samples"], "items": [{"data": ["502/Bad Gateway", 34, 45.945945945945944, 1.7], "isController": false}, {"data": ["Test failed: text expected not to contain /&quot;errors&quot;:/", 33, 44.5945945945946, 1.65], "isController": false}, {"data": ["403/Forbidden", 7, 9.45945945945946, 0.35], "isController": false}]}, function(index, item){
        switch(index){
            case 2:
            case 3:
                item = item.toFixed(2) + '%';
                break;
        }
        return item;
    }, [[1, 1]]);

        // Create top5 errors by sampler
    createTable($("#top5ErrorsBySamplerTable"), {"supportsControllersDiscrimination": false, "overall": {"data": ["Total", 2000, 74, "502/Bad Gateway", 34, "Test failed: text expected not to contain /&quot;errors&quot;:/", 33, "403/Forbidden", 7, null, null, null, null], "isController": false}, "titles": ["Sample", "#Samples", "#Errors", "Error", "#Errors", "Error", "#Errors", "Error", "#Errors", "Error", "#Errors", "Error", "#Errors"], "items": [{"data": [], "isController": false}, {"data": ["POST /graphql - Obtain Groups List", 200, 11, "Test failed: text expected not to contain /&quot;errors&quot;:/", 6, "502/Bad Gateway", 5, null, null, null, null, null, null], "isController": false}, {"data": ["POST /graphql - Create User", 200, 11, "Test failed: text expected not to contain /&quot;errors&quot;:/", 7, "502/Bad Gateway", 4, null, null, null, null, null, null], "isController": false}, {"data": ["POST /graphql - Obtain Updated Users List", 200, 9, "Test failed: text expected not to contain /&quot;errors&quot;:/", 7, "502/Bad Gateway", 2, null, null, null, null, null, null], "isController": false}, {"data": ["POST /graphql - Login", 200, 7, "502/Bad Gateway", 7, null, null, null, null, null, null, null, null], "isController": false}, {"data": ["POST /graphql - Obtain Recent Pages", 200, 9, "502/Bad Gateway", 9, null, null, null, null, null, null, null, null], "isController": false}, {"data": ["POST /graphql - Obtain Users List", 200, 9, "Test failed: text expected not to contain /&quot;errors&quot;:/", 7, "502/Bad Gateway", 2, null, null, null, null, null, null], "isController": false}, {"data": ["GET /a - Administration Page", 200, 8, "403/Forbidden", 7, "502/Bad Gateway", 1, null, null, null, null, null, null], "isController": false}, {"data": [], "isController": false}, {"data": ["POST /graphql - Obtain System Info", 200, 10, "Test failed: text expected not to contain /&quot;errors&quot;:/", 6, "502/Bad Gateway", 4, null, null, null, null, null, null], "isController": false}]}, function(index, item){
        return item;
    }, [[0, 0]], 0);

});
