<template>
  <div class="container">
    <h1>{{ currentYear }}년 월별 매출</h1>
    <div>
      <canvas id="myChart" height="400" width="800"></canvas>
    </div>
    <h1>연도별 매출</h1>
    <div>
      <canvas id="myChart2" height="400" width="800"></canvas>
    </div>

  </div>

</template>

<script setup>
import {onMounted, ref} from 'vue';
import Chart from 'chart.js/auto';
import axios from "axios"; // Make sure to use the auto version of Chart.js

const currentYear = ref(new Date().getFullYear());
// const labels = [
//   '1월',
//   '2월',
//   '3월',
//   '4월',
//   '5월',
//   '6월',
//   '7월',
//   '8월',
//   '9월',
//   '10월',
//   '11월',
//   '12월',
// ];
const labels1 = ref([])
const labels2 = ref([])
const dataset1 = ref([]);
const dataset2 = ref([]);

const getYearlySalesAmount = () => {
  axios.post("/api/payment/getYearlySalesAmount")
      .then((response) => {
        const data = response.data
        console.log(response.data)
        data.forEach((row) => {
          labels2.value.push(`${row.year}`);
          dataset2.value.push(row.yearlyRevenue);
        })
        const dataForChart = {
          labels: labels2.value,
          datasets: [
            {
              label: '연매출',
              backgroundColor: 'rgb(255, 180, 1)',
              borderColor: 'rgb(110, 100, 150)',
              data: dataset2.value,
            },
          ]
        }
        const config = {
          type: 'bar',
          data: dataForChart,
          options: {},
        };
        const myChart = new Chart(document.getElementById('myChart2'), config);
      })
      .catch(error => {
        console.error('Error fetching data:', error);
      })
}

const getMonthlySalesAmount = () => {
  console.log(currentYear.value)
  axios.post("/api/payment/getMonthlySalesAmount", {
    year : currentYear.value,
  })
      .then((response) => {
        const data = response.data;
        console.log(response.data)
        data.forEach((row) => {
          labels1.value.push(`${row.year}-${row.month}`);
          dataset1.value.push(row.monthlyRevenue);
        })
        // 가져온 데이터를 차트에 반영하는 작업
        const dataForChart = {
          labels: labels1.value,
          datasets: [
            {
              label: '매출',
              backgroundColor: 'rgb(255, 99, 132)',
              borderColor: 'rgb(255, 99, 132)',
              data: dataset1.value,
            },
            // 데이터셋 2와 3도 필요에 따라 추가 작업
          ],
        };
        const config = {
          type: 'bar',
          data: dataForChart,
          options: {},
        };

        const myChart = new Chart(document.getElementById('myChart'), config);
      })

      .catch(error => {
        console.error('Error fetching data:', error);
      });
}


// const data = {
//   labels: labels,
//   datasets: [
//     {
//       label: 'My First dataset',
//       backgroundColor: 'rgb(255, 99, 132)',
//       borderColor: 'rgb(255, 99, 132)',
//       data: [0, 10, 5, 2, 20, 30, 45],
//     },
// {
//   label: 'My Second dataset',
//   backgroundColor: 'rgb(120, 150, 190)',
//   borderColor: 'rgb(10, 40, 80)',
//   data: [10, 25, 15, 20, 40, 12, 40],
// },
// {
//   label: 'My Third dataset',
//   backgroundColor: 'rgb(100, 200, 150)',
//   borderColor: 'rgb(90, 190, 140)',
//   data: [30, 15, 25, 10, 30, 10, 15],
// }
//   ]
// };

// const config = {
//   type: 'line',
//   data: data,
//   options: {}
// };


onMounted(() => {
  getMonthlySalesAmount()
  getYearlySalesAmount()
  // const myChart = new Chart(
  //     document.getElementById('myChart'),
  //     config
  // );
  // const myChart2 = new Chart(
  //     document.getElementById('myChart2'),
  //     config
  // );

});
</script>

<style>
canvas {
  max-width: 1000px;
  margin: 20px auto;
}
</style>
