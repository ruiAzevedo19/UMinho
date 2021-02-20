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

    var data = {"OkPercent": 68.22, "KoPercent": 31.78};
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
    createTable($("#apdexTable"), {"supportsControllersDiscrimination": true, "overall": {"data": [0.3031, 500, 1500, "Total"], "isController": false}, "titles": ["Apdex", "T (Toleration threshold)", "F (Frustration threshold)", "Label"], "items": [{"data": [0.961, 500, 1500, "GET /login"], "isController": false}, {"data": [0.319, 500, 1500, "POST /graphql - Obtain Groups List"], "isController": false}, {"data": [0.0, 500, 1500, "POST /graphql - Create User"], "isController": false}, {"data": [0.017, 500, 1500, "POST /graphql - Obtain Updated Users List"], "isController": false}, {"data": [0.0, 500, 1500, "POST /graphql - Login"], "isController": false}, {"data": [0.523, 500, 1500, "POST /graphql - Obtain Recent Pages"], "isController": false}, {"data": [0.366, 500, 1500, "POST /graphql - Obtain Users List"], "isController": false}, {"data": [0.358, 500, 1500, "GET /a - Administration Page"], "isController": false}, {"data": [0.415, 500, 1500, "GET /"], "isController": false}, {"data": [0.072, 500, 1500, "POST /graphql - Obtain System Info"], "isController": false}]}, function(index, item){
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
    createTable($("#statisticsTable"), {"supportsControllersDiscrimination": true, "overall": {"data": ["Total", 5000, 1589, 31.78, 10566.980999999994, 47, 120363, 416.0, 61917.9, 71540.0, 74681.99, 32.693628011900486, 78.19486985892699, 19.845606895903487], "isController": false}, "titles": ["Label", "#Samples", "FAIL", "Error %", "Average", "Min", "Max", "Median", "90th pct", "95th pct", "99th pct", "Transactions/s", "Received", "Sent"], "items": [{"data": ["GET /login", 500, 0, 0.0, 192.074, 57, 2407, 84.0, 151.0, 1456.0, 1488.8700000000001, 138.50415512465375, 278.63140581717454, 17.44827735457064], "isController": false}, {"data": ["POST /graphql - Obtain Groups List", 500, 317, 63.4, 854.0579999999998, 49, 29233, 78.0, 396.0, 1066.75, 21946.0, 6.6496435791041595, 3.8142329594571236, 4.159741686283116], "isController": false}, {"data": ["POST /graphql - Create User", 500, 317, 63.4, 24765.603999999992, 47, 71553, 79.0, 71542.0, 71544.0, 71550.99, 6.641164594623313, 3.861422138986293, 8.029972198424716], "isController": false}, {"data": ["POST /graphql - Obtain Updated Users List", 500, 317, 63.4, 1331.63, 50, 13014, 101.5, 6554.800000000019, 7545.0, 8760.76, 6.642399766187529, 80.56810577025267, 4.466572745901639], "isController": false}, {"data": ["POST /graphql - Login", 500, 4, 0.8, 67182.52600000001, 61627, 120363, 62489.0, 74681.9, 74742.0, 75902.93, 4.154031487558676, 4.265013903024135, 2.8732916545507416], "isController": false}, {"data": ["POST /graphql - Obtain Recent Pages", 500, 0, 0.0, 1217.2299999999996, 94, 1548, 1290.0, 1382.0, 1391.95, 1535.0, 6.570820301206402, 6.391149433595291, 5.316794647081242], "isController": false}, {"data": ["POST /graphql - Obtain Users List", 500, 317, 63.4, 156.372, 48, 488, 76.0, 325.0, 393.79999999999995, 470.0, 6.654865372073523, 4.60883221254309, 4.47495503806583], "isController": false}, {"data": ["GET /a - Administration Page", 500, 0, 0.0, 8237.552000000014, 126, 29584, 12530.0, 13318.9, 13328.0, 13351.98, 5.711348449368896, 11.344612056656576, 2.524215225027129], "isController": false}, {"data": ["GET /", 500, 0, 0.0, 1087.8760000000002, 765, 2055, 977.5, 1635.0, 1678.9, 1831.98, 225.42831379621282, 637.0991602795311, 27.297959873760146], "isController": false}, {"data": ["POST /graphql - Obtain System Info", 500, 317, 63.4, 644.8880000000003, 57, 1915, 174.0, 1562.0, 1723.95, 1871.99, 6.53406863385693, 7.298018666200569, 4.578774118880844], "isController": false}]}, function(index, item){
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
    createTable($("#errorsTable"), {"supportsControllersDiscrimination": false, "titles": ["Type of error", "Number of errors", "% in errors", "% in all samples"], "items": [{"data": ["Test failed: text expected not to contain /&quot;errors&quot;:/", 1585, 99.74826935179358, 31.7], "isController": false}, {"data": ["Non HTTP response code: org.apache.http.NoHttpResponseException/Non HTTP response message: 34.77.156.245:10000 failed to respond", 4, 0.2517306482064191, 0.08], "isController": false}]}, function(index, item){
        switch(index){
            case 2:
            case 3:
                item = item.toFixed(2) + '%';
                break;
        }
        return item;
    }, [[1, 1]]);

        // Create top5 errors by sampler
    createTable($("#top5ErrorsBySamplerTable"), {"supportsControllersDiscrimination": false, "overall": {"data": ["Total", 5000, 1589, "Test failed: text expected not to contain /&quot;errors&quot;:/", 1585, "Non HTTP response code: org.apache.http.NoHttpResponseException/Non HTTP response message: 34.77.156.245:10000 failed to respond", 4, null, null, null, null, null, null], "isController": false}, "titles": ["Sample", "#Samples", "#Errors", "Error", "#Errors", "Error", "#Errors", "Error", "#Errors", "Error", "#Errors", "Error", "#Errors"], "items": [{"data": [], "isController": false}, {"data": ["POST /graphql - Obtain Groups List", 500, 317, "Test failed: text expected not to contain /&quot;errors&quot;:/", 317, null, null, null, null, null, null, null, null], "isController": false}, {"data": ["POST /graphql - Create User", 500, 317, "Test failed: text expected not to contain /&quot;errors&quot;:/", 317, null, null, null, null, null, null, null, null], "isController": false}, {"data": ["POST /graphql - Obtain Updated Users List", 500, 317, "Test failed: text expected not to contain /&quot;errors&quot;:/", 317, null, null, null, null, null, null, null, null], "isController": false}, {"data": ["POST /graphql - Login", 500, 4, "Non HTTP response code: org.apache.http.NoHttpResponseException/Non HTTP response message: 34.77.156.245:10000 failed to respond", 4, null, null, null, null, null, null, null, null], "isController": false}, {"data": [], "isController": false}, {"data": ["POST /graphql - Obtain Users List", 500, 317, "Test failed: text expected not to contain /&quot;errors&quot;:/", 317, null, null, null, null, null, null, null, null], "isController": false}, {"data": [], "isController": false}, {"data": [], "isController": false}, {"data": ["POST /graphql - Obtain System Info", 500, 317, "Test failed: text expected not to contain /&quot;errors&quot;:/", 317, null, null, null, null, null, null, null, null], "isController": false}]}, function(index, item){
        return item;
    }, [[0, 0]], 0);

});
