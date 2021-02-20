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

    var data = {"OkPercent": 97.2, "KoPercent": 2.8};
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
    createTable($("#apdexTable"), {"supportsControllersDiscrimination": true, "overall": {"data": [0.3295, 500, 1500, "Total"], "isController": false}, "titles": ["Apdex", "T (Toleration threshold)", "F (Frustration threshold)", "Label"], "items": [{"data": [0.52, 500, 1500, "GET /login"], "isController": false}, {"data": [0.36, 500, 1500, "POST /graphql - Obtain Groups List"], "isController": false}, {"data": [0.17, 500, 1500, "POST /graphql - Create User"], "isController": false}, {"data": [0.37, 500, 1500, "POST /graphql - Obtain Updated Users List"], "isController": false}, {"data": [0.015, 500, 1500, "POST /graphql - Login"], "isController": false}, {"data": [0.3383333333333333, 500, 1500, "POST /graphql - Obtain Recent Pages"], "isController": false}, {"data": [0.3466666666666667, 500, 1500, "POST /graphql - Obtain Users List"], "isController": false}, {"data": [0.41, 500, 1500, "GET /a - Administration Page"], "isController": false}, {"data": [0.4483333333333333, 500, 1500, "GET /"], "isController": false}, {"data": [0.31666666666666665, 500, 1500, "POST /graphql - Obtain System Info"], "isController": false}]}, function(index, item){
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
    createTable($("#statisticsTable"), {"supportsControllersDiscrimination": true, "overall": {"data": ["Total", 3000, 84, 2.8, 5426.578999999992, 48, 49127, 2407.5, 13740.2, 18365.749999999978, 43308.97, 39.8406374501992, 227.17520542828686, 35.30134669654715], "isController": false}, "titles": ["Label", "#Samples", "FAIL", "Error %", "Average", "Min", "Max", "Median", "90th pct", "95th pct", "99th pct", "Transactions/s", "Received", "Sent"], "items": [{"data": ["GET /login", 300, 0, 0.0, 4533.946666666666, 51, 22563, 664.5, 18760.600000000002, 20917.0, 22264.27, 11.757789535567314, 26.537086027826767, 1.412312610229277], "isController": false}, {"data": ["POST /graphql - Obtain Groups List", 300, 15, 5.0, 2918.7533333333354, 49, 17019, 1755.5, 6937.500000000003, 9634.649999999996, 16437.85000000001, 4.585122804872458, 2.8191340995583, 4.704109129743691], "isController": false}, {"data": ["POST /graphql - Create User", 300, 11, 3.6666666666666665, 6447.213333333334, 52, 30060, 5246.0, 13734.500000000002, 15388.0, 24059.77, 4.49734656552634, 2.839711289089437, 7.237272977693161], "isController": false}, {"data": ["POST /graphql - Obtain Updated Users List", 300, 12, 4.0, 3004.043333333336, 54, 16914, 1844.5, 6919.500000000002, 8491.099999999993, 16900.810000000005, 5.3754770735902815, 151.65974154034296, 5.7669489912021366], "isController": false}, {"data": ["POST /graphql - Login", 300, 7, 2.3333333333333335, 20919.363333333335, 500, 49127, 15641.5, 43308.7, 45301.15, 48557.55, 5.357142857142857, 7.483694893973214, 3.7039620535714284], "isController": false}, {"data": ["POST /graphql - Obtain Recent Pages", 300, 3, 1.0, 3579.356666666667, 52, 17143, 1960.5, 8715.6, 10156.599999999999, 16311.0, 4.857984907860219, 5.936277280621498, 5.875947813097127], "isController": false}, {"data": ["POST /graphql - Obtain Users List", 300, 15, 5.0, 3573.1466666666647, 52, 17303, 2022.5, 8683.00000000001, 13029.849999999995, 17175.05, 4.6667185190946565, 77.76939058003423, 5.006574725830287], "isController": false}, {"data": ["GET /a - Administration Page", 300, 7, 2.3333333333333335, 2089.2766666666625, 48, 15225, 1111.0, 5722.600000000001, 6698.349999999999, 9623.630000000001, 5.5409848177016, 12.12346555123564, 4.667486078275645], "isController": false}, {"data": ["GET /", 300, 0, 0.0, 3547.7966666666657, 88, 13741, 2619.0, 9491.5, 9617.65, 13726.970000000005, 21.56411730879816, 69.04111558366877, 2.484927580506038], "isController": false}, {"data": ["POST /graphql - Obtain System Info", 300, 14, 4.666666666666667, 3652.8933333333352, 50, 18432, 1953.5, 9859.7, 12452.2, 16602.350000000002, 5.021256653165065, 3.1696192266009438, 5.52913584172999], "isController": false}]}, function(index, item){
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
    createTable($("#errorsTable"), {"supportsControllersDiscrimination": false, "titles": ["Type of error", "Number of errors", "% in errors", "% in all samples"], "items": [{"data": ["502/Bad Gateway", 43, 51.19047619047619, 1.4333333333333333], "isController": false}, {"data": ["Test failed: text expected not to contain /&quot;errors&quot;:/", 34, 40.476190476190474, 1.1333333333333333], "isController": false}, {"data": ["403/Forbidden", 7, 8.333333333333334, 0.23333333333333334], "isController": false}]}, function(index, item){
        switch(index){
            case 2:
            case 3:
                item = item.toFixed(2) + '%';
                break;
        }
        return item;
    }, [[1, 1]]);

        // Create top5 errors by sampler
    createTable($("#top5ErrorsBySamplerTable"), {"supportsControllersDiscrimination": false, "overall": {"data": ["Total", 3000, 84, "502/Bad Gateway", 43, "Test failed: text expected not to contain /&quot;errors&quot;:/", 34, "403/Forbidden", 7, null, null, null, null], "isController": false}, "titles": ["Sample", "#Samples", "#Errors", "Error", "#Errors", "Error", "#Errors", "Error", "#Errors", "Error", "#Errors", "Error", "#Errors"], "items": [{"data": [], "isController": false}, {"data": ["POST /graphql - Obtain Groups List", 300, 15, "502/Bad Gateway", 9, "Test failed: text expected not to contain /&quot;errors&quot;:/", 6, null, null, null, null, null, null], "isController": false}, {"data": ["POST /graphql - Create User", 300, 11, "Test failed: text expected not to contain /&quot;errors&quot;:/", 7, "502/Bad Gateway", 4, null, null, null, null, null, null], "isController": false}, {"data": ["POST /graphql - Obtain Updated Users List", 300, 12, "Test failed: text expected not to contain /&quot;errors&quot;:/", 7, "502/Bad Gateway", 5, null, null, null, null, null, null], "isController": false}, {"data": ["POST /graphql - Login", 300, 7, "502/Bad Gateway", 7, null, null, null, null, null, null, null, null], "isController": false}, {"data": ["POST /graphql - Obtain Recent Pages", 300, 3, "502/Bad Gateway", 3, null, null, null, null, null, null, null, null], "isController": false}, {"data": ["POST /graphql - Obtain Users List", 300, 15, "502/Bad Gateway", 8, "Test failed: text expected not to contain /&quot;errors&quot;:/", 7, null, null, null, null, null, null], "isController": false}, {"data": ["GET /a - Administration Page", 300, 7, "403/Forbidden", 7, null, null, null, null, null, null, null, null], "isController": false}, {"data": [], "isController": false}, {"data": ["POST /graphql - Obtain System Info", 300, 14, "502/Bad Gateway", 7, "Test failed: text expected not to contain /&quot;errors&quot;:/", 7, null, null, null, null, null, null], "isController": false}]}, function(index, item){
        return item;
    }, [[0, 0]], 0);

});
