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

    var data = {"OkPercent": 80.26666666666667, "KoPercent": 19.733333333333334};
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
    createTable($("#apdexTable"), {"supportsControllersDiscrimination": true, "overall": {"data": [0.457, 500, 1500, "Total"], "isController": false}, "titles": ["Apdex", "T (Toleration threshold)", "F (Frustration threshold)", "Label"], "items": [{"data": [0.84, 500, 1500, "GET /login"], "isController": false}, {"data": [0.57, 500, 1500, "POST /graphql - Obtain Groups List"], "isController": false}, {"data": [0.0, 500, 1500, "POST /graphql - Create User"], "isController": false}, {"data": [0.2633333333333333, 500, 1500, "POST /graphql - Obtain Updated Users List"], "isController": false}, {"data": [0.0, 500, 1500, "POST /graphql - Login"], "isController": false}, {"data": [0.5166666666666667, 500, 1500, "POST /graphql - Obtain Recent Pages"], "isController": false}, {"data": [0.605, 500, 1500, "POST /graphql - Obtain Users List"], "isController": false}, {"data": [0.5966666666666667, 500, 1500, "GET /a - Administration Page"], "isController": false}, {"data": [0.875, 500, 1500, "GET /"], "isController": false}, {"data": [0.30333333333333334, 500, 1500, "POST /graphql - Obtain System Info"], "isController": false}]}, function(index, item){
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
    createTable($("#statisticsTable"), {"supportsControllersDiscrimination": true, "overall": {"data": ["Total", 3000, 592, 19.733333333333334, 11967.644333333354, 55, 120363, 412.5, 71075.0, 72837.9, 73082.0, 20.204741379310345, 64.42441611454404, 14.732387148100756], "isController": false}, "titles": ["Label", "#Samples", "FAIL", "Error %", "Average", "Min", "Max", "Median", "90th pct", "95th pct", "99th pct", "Transactions/s", "Received", "Sent"], "items": [{"data": ["GET /login", 300, 0, 0.0, 444.65333333333336, 64, 1134, 146.0, 1122.0, 1126.0, 1132.0, 176.056338028169, 354.17583626760563, 22.17897227112676], "isController": false}, {"data": ["POST /graphql - Obtain Groups List", 300, 118, 39.333333333333336, 387.9766666666668, 57, 8652, 243.0, 328.0, 492.0, 8608.86000000001, 4.119068541300527, 2.3636641946534493, 3.2950402982205627], "isController": false}, {"data": ["POST /graphql - Create User", 300, 118, 39.333333333333336, 42742.97666666667, 56, 71105, 70154.0, 71090.9, 71099.0, 71105.0, 4.117429077284144, 2.4056401058179273, 5.6953479913808485], "isController": false}, {"data": ["POST /graphql - Obtain Updated Users List", 300, 118, 39.333333333333336, 813.5433333333334, 55, 7167, 711.5, 1176.7, 3027.2499999999977, 4997.0, 4.096178265678122, 82.22849858425838, 3.4687376261281555], "isController": false}, {"data": ["POST /graphql - Login", 300, 2, 0.6666666666666666, 69831.67666666668, 62792, 120363, 72837.0, 73082.0, 73102.9, 73500.99, 2.4924603075696017, 2.928056691009696, 1.7263208481842427], "isController": false}, {"data": ["POST /graphql - Obtain Recent Pages", 300, 0, 0.0, 787.4033333333325, 384, 18608, 676.5, 781.0, 822.0, 840.98, 4.102844638949671, 3.9906574808533914, 4.035318654266958], "isController": false}, {"data": ["POST /graphql - Obtain Users List", 300, 118, 39.333333333333336, 236.16333333333327, 69, 536, 238.0, 348.0, 380.0, 478.99, 4.119068541300527, 3.1837503604184976, 3.488121636094025], "isController": false}, {"data": ["GET /a - Administration Page", 300, 0, 0.0, 3365.056666666667, 121, 10180, 246.5, 7899.9, 10149.9, 10171.99, 4.818348270212971, 9.570820685169124, 2.9698090327968907], "isController": false}, {"data": ["GET /", 300, 0, 0.0, 419.88999999999965, 176, 1019, 312.5, 816.8000000000001, 986.95, 1018.99, 294.40628066732086, 832.0427502453387, 35.65076054955839], "isController": false}, {"data": ["POST /graphql - Obtain System Info", 300, 118, 39.333333333333336, 647.1033333333336, 86, 2532, 769.0, 1143.0, 1153.95, 1259.88, 5.427997611681051, 4.932339444353978, 4.750275357795509], "isController": false}]}, function(index, item){
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
    createTable($("#errorsTable"), {"supportsControllersDiscrimination": false, "titles": ["Type of error", "Number of errors", "% in errors", "% in all samples"], "items": [{"data": ["Test failed: text expected not to contain /&quot;errors&quot;:/", 590, 99.66216216216216, 19.666666666666668], "isController": false}, {"data": ["Non HTTP response code: org.apache.http.NoHttpResponseException/Non HTTP response message: 34.77.156.245:10000 failed to respond", 2, 0.33783783783783783, 0.06666666666666667], "isController": false}]}, function(index, item){
        switch(index){
            case 2:
            case 3:
                item = item.toFixed(2) + '%';
                break;
        }
        return item;
    }, [[1, 1]]);

        // Create top5 errors by sampler
    createTable($("#top5ErrorsBySamplerTable"), {"supportsControllersDiscrimination": false, "overall": {"data": ["Total", 3000, 592, "Test failed: text expected not to contain /&quot;errors&quot;:/", 590, "Non HTTP response code: org.apache.http.NoHttpResponseException/Non HTTP response message: 34.77.156.245:10000 failed to respond", 2, null, null, null, null, null, null], "isController": false}, "titles": ["Sample", "#Samples", "#Errors", "Error", "#Errors", "Error", "#Errors", "Error", "#Errors", "Error", "#Errors", "Error", "#Errors"], "items": [{"data": [], "isController": false}, {"data": ["POST /graphql - Obtain Groups List", 300, 118, "Test failed: text expected not to contain /&quot;errors&quot;:/", 118, null, null, null, null, null, null, null, null], "isController": false}, {"data": ["POST /graphql - Create User", 300, 118, "Test failed: text expected not to contain /&quot;errors&quot;:/", 118, null, null, null, null, null, null, null, null], "isController": false}, {"data": ["POST /graphql - Obtain Updated Users List", 300, 118, "Test failed: text expected not to contain /&quot;errors&quot;:/", 118, null, null, null, null, null, null, null, null], "isController": false}, {"data": ["POST /graphql - Login", 300, 2, "Non HTTP response code: org.apache.http.NoHttpResponseException/Non HTTP response message: 34.77.156.245:10000 failed to respond", 2, null, null, null, null, null, null, null, null], "isController": false}, {"data": [], "isController": false}, {"data": ["POST /graphql - Obtain Users List", 300, 118, "Test failed: text expected not to contain /&quot;errors&quot;:/", 118, null, null, null, null, null, null, null, null], "isController": false}, {"data": [], "isController": false}, {"data": [], "isController": false}, {"data": ["POST /graphql - Obtain System Info", 300, 118, "Test failed: text expected not to contain /&quot;errors&quot;:/", 118, null, null, null, null, null, null, null, null], "isController": false}]}, function(index, item){
        return item;
    }, [[0, 0]], 0);

});
