var chartInstanceRtt;
var chartInstancePacketLoss;
document.addEventListener('DOMContentLoaded', () => {

    initializeDatepickers();
    console.log("Before hitting dd")
    fetchClientsAndPopulateDropdown()
    document.getElementById('applyButton').addEventListener('click', refreshChart)
	//setInterval(refreshChart, 5000)

});

function initializeDatepickers(){
    flatpickr("#startDateTimePicker", {
        enableTime: true,
        dateFormat: "Y-m-dTH:i:S",
    });

    flatpickr("#endDateTimePicker", {
        enableTime: true,
        dateFormat: "Y-m-dTH:i:S",
    });


}

function fetchClientsAndPopulateDropdown() {
    // API endpoint
    const apiURL = 'http://localhost:8080/api/v1/icmp/clients';

    fetch(apiURL)
        .then(response => {
            // Check if the response is successful
            if (!response.ok) {
                throw new Error(`HTTP error! Status: ${response.status}`);
            }
            return response.json(); // Parse the JSON in the response
        })
        .then(clients => {
            // Get the dropdown element
            const dropdown = document.getElementById('clientDropdown');

            // Clear existing options except the first placeholder
            dropdown.length = 1;

            // Populate the dropdown with client options
            clients.forEach(client => {
                let option = new Option(client, client); // new Option(text, value)
                dropdown.add(option);
            });
        })
        .catch(error => {
            console.error('Failed to fetch clients:', error);
        });
}


function refreshChart(){
    const startDateTime = document.getElementById('startDateTimePicker').value;
    const endDateTime = document.getElementById('endDateTimePicker').value;
    const selectedClient = document.getElementById('clientDropdown').value;
   
    const dataToSend = {
        startTime: toUtcIsoString(new Date(startDateTime)),
        endTime: toUtcIsoString(new Date(endDateTime)),
        client: selectedClient
    };


    const jsonData = JSON.stringify(dataToSend);

    const options = {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json'
        },
        body: jsonData
    };
    fetch("http://localhost:8080/api/v1/icmp/by-date", options)
        .then(response => {
            if (!response.ok) {
                throw new Error(`HTTP error! status: ${response.status}`);
            }
            return response.json();
        })
        .then(data => {
            plotGraph(data);
            plotPacketLossGraph(data);
        })
        .catch(error => {
            console.error('Error during fetch:', error);
        });
}

//blue- #5ccbfa  red #ea647d   gereen #6de38c yell #f2c57e


function plotGraph(data) {
    let timestamps = data.map(entry => {

        // Parse the original timestamp as a Date object
        let date = new Date(entry.timestamp);

        // Extract parts of the date
        let month = date.getMonth() + 1; // getMonth() returns month from 0-11
        let day = date.getDate();
        let hours = date.getHours();
        let minutes = ('0' + date.getMinutes()).slice(-2); // Ensure two digits
        let seconds = ('0' + date.getSeconds()).slice(-2); // Ensure two digits

        // Format the month and day
        month = ('0' + month).slice(-2); // Ensure two digits
        day = ('0' + day).slice(-2); // Ensure two digits

        // Construct the new format "MM/DD HH:MM:SS"
        let newFormat = `${month}/${day} ${hours}:${minutes}:${seconds}`;
        return newFormat;

    });
    let minRtt = data.map(entry => entry.minRtt);
    let maxRtt = data.map(entry => entry.maxRtt);
    let avgRtt = data.map(entry => entry.avgRtt);
    // Create RTT time series graph using Chart.js
    var ctx = document.getElementById('rttTimeSeries').getContext('2d');
    if (chartInstanceRtt) {
        chartInstanceRtt.destroy();
    }
    chartInstanceRtt = new Chart(ctx, {
        type: 'line',
        data: {
            labels: timestamps,
            datasets: [{
                label: 'Min RTT',
                data: minRtt,
                borderColor: '#5ccbfa',
                borderWidth: 1,
                fill: false
            }, {
                label: 'Max RTT',
                data: maxRtt,
                borderColor: '#ea647d',
                borderWidth: 1,
                fill: false
            }, {
                label: 'Average RTT',
                data: avgRtt,
                borderColor: '#6de38c',
                borderWidth: 1,
                fill: false
            }]
        },
        options: {
            scales: {
                x: {

                    title: {
                        display: true,
                        text: 'Time'
                    }
                },
                y: {
					min:0,
					max:500,
                    title: {
                        display: true,
                        text: 'RTT (ms)'
                    }
                }
            }
        }
    });
}

function plotPacketLossGraph(data){
    let timestamps = data.map(entry => {

        // Parse the original timestamp as a Date object
        let date = new Date(entry.timestamp);

        // Extract parts of the date
        let month = date.getMonth() + 1; // getMonth() returns month from 0-11
        let day = date.getDate();
        let hours = date.getHours();
        let minutes = ('0' + date.getMinutes()).slice(-2); // Ensure two digits
        let seconds = ('0' + date.getSeconds()).slice(-2); // Ensure two digits

        // Format the month and day
        month = ('0' + month).slice(-2); // Ensure two digits
        day = ('0' + day).slice(-2); // Ensure two digits

        // Construct the new format "MM/DD HH:MM:SS"
        let newFormat = `${month}/${day} ${hours}:${minutes}:${seconds}`;
        return newFormat;

    });
    let packetLossPercentage = data.map(entry => entry.packetLossPercentage);
    let maxRtt = data.map(entry => entry.maxRtt);
    let avgRtt = data.map(entry => entry.avgRtt);
    // Create RTT time series graph using Chart.js
    var ctx = document.getElementById('packetLossOverTime').getContext('2d');
    if (chartInstancePacketLoss) {
        chartInstancePacketLoss.destroy();
    }
    chartInstancePacketLoss = new Chart(ctx, {
        type: 'line',
        data: {
            labels: timestamps,
            datasets: [{
                label: 'Packet Loss Percentage',
                data: packetLossPercentage,
                borderColor: '#f2c57e',
                borderWidth: 1,
                fill: false
            }]
        },
        options: {
            scales: {
                x: {

                    title: {
                        display: true,
                        text: 'Time'
                    }
                },
                y: {
                    title: {
                        display: true,
                        text: 'Packet Loss (%)'
                    }
                }
            }
        }
    });

}

// Function to format date as ISO string and append 'Z' to denote UTC
function toUtcIsoString(date) {
    return date.getUTCFullYear() +
        '-' + String(date.getUTCMonth() + 1).padStart(2, '0') + // getUTCMonth() returns 0-11
        '-' + String(date.getUTCDate()).padStart(2, '0') +
        'T' + String(date.getUTCHours()).padStart(2, '0') +
        ':' + String(date.getUTCMinutes()).padStart(2, '0') +
        ':' + String(date.getUTCSeconds()).padStart(2, '0') +
        '.' + String(date.getUTCMilliseconds()).padStart(3, '0') + 
        'Z';
}