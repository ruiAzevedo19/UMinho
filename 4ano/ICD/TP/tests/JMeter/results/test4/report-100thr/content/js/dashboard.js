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

    var data = {"OkPercent": 100.0, "KoPercent": 0.0};
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
    createTable($("#apdexTable"), {"supportsControllersDiscrimination": true, "overall": {"data": [0.4745, 500, 1500, "Total"], "isController": false}, "titles": ["Apdex", "T (Toleration threshold)", "F (Frustration threshold)", "Label"], "items": [{"data": [0.305, 500, 1500, "GET /login"], "isController": false}, {"data": [0.315, 500, 1500, "POST /graphql - Obtain Groups List"], "isController": false}, {"data": [0.0, 500, 1500, "POST /graphql - Create User"], "isController": false}, {"data": [0.53, 500, 1500, "POST /graphql - Obtain Updated Users List"], "isController": false}, {"data": [0.0, 500, 1500, "POST /graphql - Login"], "isController": false}, {"data": [0.985, 500, 1500, "POST /graphql - Obtain Recent Pages"], "isController": false}, {"data": [0.795, 500, 1500, "POST /graphql - Obtain Users List"], "isController": false}, {"data": [0.76, 500, 1500, "GET /a - Administration Page"], "isController": false}, {"data": [0.315, 500, 1500, "GET /"], "isController": false}, {"data": [0.74, 500, 1500, "POST /graphql - Obtain System Info"], "isController": false}]}, function(index, item){
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
    createTable($("#statisticsTable"), {"supportsControllersDiscrimination": true, "overall": {"data": ["Total", 1000, 0, 0.0, 7851.316000000003, 46, 38506, 2237.0, 29604.2, 31366.14999999999, 33844.0, 12.441060475994975, 33.77604555449806, 11.557599388521878], "isController": false}, "titles": ["Label", "#Samples", "FAIL", "Error %", "Average", "Min", "Max", "Median", "90th pct", "95th pct", "99th pct", "Transactions/s", "Received", "Sent"], "items": [{"data": ["GET /login", 100, 0, 0.0, 2427.3400000000006, 46, 3737, 3423.5, 3628.0, 3635.95, 3737.0, 8.45594452900389, 17.01098215795704, 1.0652508244545915], "isController": false}, {"data": ["POST /graphql - Obtain Groups List", 100, 0, 0.0, 5210.280000000002, 92, 10980, 6401.5, 10153.300000000001, 10165.95, 10979.99, 5.682141030740383, 3.262791919995454, 6.164901059719302], "isController": false}, {"data": ["POST /graphql - Create User", 100, 0, 0.0, 26398.57, 10123, 32871, 29259.5, 32470.0, 32869.0, 32871.0, 2.534404541652939, 1.4924276744303926, 4.224436570190334], "isController": false}, {"data": ["POST /graphql - Obtain Updated Users List", 100, 0, 0.0, 5506.97, 178, 19008, 650.0, 17794.600000000002, 18622.0, 19006.89, 3.3759832551230544, 51.66975340552311, 3.821059172546504], "isController": false}, {"data": ["POST /graphql - Login", 100, 0, 0.0, 28179.249999999993, 8615, 38506, 30234.5, 33844.0, 37868.9, 38505.99, 2.506579771901241, 3.537116963278606, 1.7477519112670759], "isController": false}, {"data": ["POST /graphql - Obtain Recent Pages", 100, 0, 0.0, 253.47000000000003, 130, 778, 193.5, 408.70000000000005, 447.2499999999996, 776.1899999999991, 76.21951219512195, 74.13538490853658, 96.68861947408536], "isController": false}, {"data": ["POST /graphql - Obtain Users List", 100, 0, 0.0, 1327.3900000000003, 54, 6410, 192.0, 6210.8, 6317.65, 6410.0, 14.40299582313121, 13.024584113495608, 16.3018282802823], "isController": false}, {"data": ["GET /a - Administration Page", 100, 0, 0.0, 577.3200000000003, 55, 3111, 86.0, 2242.0, 2375.95, 3108.1699999999987, 3.194581988946746, 6.345488052263361, 2.8794913826150847], "isController": false}, {"data": ["GET /", 100, 0, 0.0, 3645.0499999999997, 100, 7979, 4530.0, 6941.8, 6987.45, 7978.9, 11.285407967498026, 31.89450259564383, 1.3665923710642138], "isController": false}, {"data": ["POST /graphql - Obtain System Info", 100, 0, 0.0, 4987.5199999999995, 251, 27341, 392.0, 24767.7, 26563.799999999996, 27335.389999999996, 3.515185601799775, 1.997888691647919, 4.07816454583802], "isController": false}]}, function(index, item){
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
    createTable($("#errorsTable"), {"supportsControllersDiscrimination": false, "titles": ["Type of error", "Number of errors", "% in errors", "% in all samples"], "items": []}, function(index, item){
        switch(index){
            case 2:
            case 3:
                item = item.toFixed(2) + '%';
                break;
        }
        return item;
    }, [[1, 1]]);

        // Create top5 errors by sampler
    createTable($("#top5ErrorsBySamplerTable"), {"supportsControllersDiscrimination": false, "overall": {"data": ["Total", 1000, 0, null, null, null, null, null, null, null, null, null, null], "isController": false}, "titles": ["Sample", "#Samples", "#Errors", "Error", "#Errors", "Error", "#Errors", "Error", "#Errors", "Error", "#Errors", "Error", "#Errors"], "items": [{"data": [], "isController": false}, {"data": [], "isController": false}, {"data": [], "isController": false}, {"data": [], "isController": false}, {"data": [], "isController": false}, {"data": [], "isController": false}, {"data": [], "isController": false}, {"data": [], "isController": false}, {"data": [], "isController": false}, {"data": [], "isController": false}]}, function(index, item){
        return item;
    }, [[0, 0]], 0);

});
