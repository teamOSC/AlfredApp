// Set new default font family and font color to mimic Bootstrap's default styling
Chart.defaults.global.defaultFontFamily = '-apple-system,system-ui,BlinkMacSystemFont,"Segoe UI",Roboto,"Helvetica Neue",Arial,sans-serif';
Chart.defaults.global.defaultFontColor = '#292b2c';

// Area Chart Example
var ctx = document.getElementById("myAreaChart");
var myLineChart;

function createLineChart(line) {
myLineChart = new Chart(ctx, {
  type: 'line',
  data: {
    labels: ["Oct 10", "Oct 11", "Oct 12", "Oct 13", "Oct 14", "Oct 15", "Oct 16", "Oct 17", "Oct 18", "Oct 19", "Oct 20"],
    datasets: [{
      label: "Sessions",
      lineTension: 0.3,
      backgroundColor: "rgba(2,117,216,0.2)",
      borderColor: "rgba(2,117,216,1)",
      pointRadius: 5,
      pointBackgroundColor: "rgba(2,117,216,1)",
      pointBorderColor: "rgba(255,255,255,0.8)",
      pointHoverRadius: 5,
      pointHoverBackgroundColor: "rgba(2,117,216,1)",
      pointHitRadius: 50,
      pointBorderWidth: 2,
      data: line,
    }],
  },
  options: {
    scales: {
      xAxes: [{
        time: {
          unit: 'date'
        },
        gridLines: {
          display: false
        },
        ticks: {
          maxTicksLimit: 7
        }
      }],
      yAxes: [{
        ticks: {
          min: 0,
          max: 11,
          maxTicksLimit: 5
        },
        gridLines: {
          color: "rgba(0, 0, 0, .125)",
        }
      }],
    },
    legend: {
      display: false
    }
  }
});
}

createLineChart([ 8.66318983,  8.39645176,  8.6944384 ,  8.67149976,  8.65095544, 9.97396413,  9.43572099,  9.06250715,  9.66042876, 10.09016662]);


function refreshChart(context) {
  // get the ctx element
  var elem = ctx.parentElement;
  elem.innerHTML = "";

  elem.innerHTML = '<canvas id="myAreaChart" width="100%" height="30"></canvas>';

  ctx = document.getElementById("myAreaChart");
  delete myLineChart;


  var name = document.getElementById("nameChart");

  if (context == "overall") {
    name.innerHTML = "Overall Everyday Scores";
    createLineChart([9.19728062, 8.41570802, 8.91774234, 9.64839941, 9.94116089,
       9.8633095 , 8.72755655, 8.6279186 , 9.20544532, 8.99797807]);
  } else if (context == 'face') {
    name.innerHTML = "Average score for Face";
    createLineChart([4.37421608, 4.88356956, 4.52110972, 4.20960003, 5.37441199,
       4.76075943, 4.38122099, 4.37614689, 4.87397038, 5.47046168]);
  } else if (context == 'body') {
    name.innerHTML = "Average score for Body actions";
    createLineChart([3.72633207, 3.19348581, 3.98299872, 4.71714673, 4.87405699,
       4.56995598, 4.35047965, 3.38338372, 3.82366876, 4.52839804]);
  } else if (context == 'draw') {
    name.innerHTML = "Average score from drawing tests";
    createLineChart([8.39095139, 8.2709611 , 7.31955306, 8.42209253, 7.99085297,
       8.47436895, 8.66243979, 6.6488216 , 5.17972495, 8.85810855]);
  }

  updateImages(context);

}


function updateImages(context) {
  if (context == "body") {
      document.getElementById("imageTable").innerHTML = `<tr>
        <td><img src="./img/data_body_1.png" width=240 height=300></td>
        <td><img src="./img/data_body_2.png" width=240 height=300></td>
        <td><img src="./img/data_body_3.png" width=240 height=300></td>
        <td><img src="./img/data_body_4.png" width=240 height=300></td>
        <td><img src="./img/data_body_5.png" width=240 height=300></td>
      </tr>`
  } else if (context == "draw") {
    document.getElementById("imageTable").innerHTML = `<tr>
        <td><img src="./img/data_draw_1.png" width=240 height=300></td>
        <td><img src="./img/data_draw_2.png" width=240 height=300></td>
        <td><img src="./img/data_draw_3.png" width=240 height=300></td>
        <td><img src="./img/data_draw_4.png" width=240 height=300></td>
        <td><img src="./img/data_draw_5.png" width=240 height=300></td>
      </tr>`
  } else {
    document.getElementById("imageTable").innerHTML = "";
  }
}