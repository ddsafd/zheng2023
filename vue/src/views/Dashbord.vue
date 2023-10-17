<template>
  <div>
    <!--    <el-row :gutter="10" style="margin-bottom: 60px">-->
    <!--      <el-col :span="6">-->
    <!--        <el-card style="color: #409EFF">-->
    <!--          <div><i class="el-icon-user-solid" /> 用户数量</div>-->
    <!--          <div style="padding: 10px 0; text-align: center; font-weight: bold">-->
    <!--            {{ counts.userCount}}-->
    <!--          </div>-->
    <!--        </el-card>-->
    <!--      </el-col>-->
    <!--      <el-col :span="6">-->
    <!--        <el-card style="color: #F56C6C">-->
    <!--          <div><i class="el-icon-money" /> 歌曲数量</div>-->
    <!--          <div style="padding: 10px 0; text-align: center; font-weight: bold">-->
    <!--            {{ counts.musicCount}}-->

    <!--          </div>-->
    <!--        </el-card>-->
    <!--      </el-col>-->
    <!--      <el-col :span="6">-->
    <!--        <el-card style="color: #67C23A">-->
    <!--          <div><i class="el-icon-bank-card" /> 歌手数量</div>-->
    <!--          <div style="padding: 10px 0; text-align: center; font-weight: bold">-->
    <!--            {{ counts.singerCount}}-->

    <!--          </div>-->
    <!--        </el-card>-->
    <!--      </el-col>-->
    <!--      <el-col :span="6">-->
    <!--        <el-card style="color: #E6A23C">-->
    <!--          <div><i class="el-icon-s-shop" /> 歌曲种类数量</div>-->
    <!--          <div style="padding: 10px 0; text-align: center; font-weight: bold">-->
    <!--            {{ counts.typeCount}}-->

    <!--          </div>-->
    <!--        </el-card>-->
    <!--      </el-col>-->
    <!--    </el-row>-->

    <el-row :gutter="10" style="margin-bottom: 10px">
      <el-col :span="24">
        <el-card style="color: #409EFF">
          <div style="padding: 10px 0; text-align: center; font-weight: bold">
            <div id="pie" style=" height: 400px"></div>
          </div>
        </el-card>
      </el-col>
    </el-row>


  </div>
</template>

<script>
import * as echarts from 'echarts'

export default {
  name: "Home",
  data() {
    return {
      activeNames: ['0'],
      user: localStorage.getItem("user") ? JSON.parse(localStorage.getItem("user")) : {},
      tableData: [],
      myChartData: [
        // {name: "收入", value: 10000}, {name: "支出", value: 3000}
      ],
      dataId: '',
      pieChart: null, // 饼图
      counts: []
    }
  },
  mounted() {  // 页面元素渲染之后再触发

    this.pieChart = echarts.init(
        document.getElementById('pie')
    );

    this.request.get("/echarts/likes").then(res => {
      this.myChartData = res.data
      this.pieEchart(this.myChartData);
    })

    this.request.get("/echarts/count").then(res => {
      this.counts = res.data
    })

  },
  methods: {
    pieEchart(data) {
      // 饼图
      this.pieChart.setOption(
          {
            title: {
              text: '文章浏览量统计',
              left: 'center'
            },
            tooltip: {
              trigger: 'item'
            },
            legend: {
              orient: 'vertical',
              left: 'right'
            },
            series: [
              {
                type: 'pie',
                radius: '50%',
                data: data,
                emphasis: {
                  itemStyle: {
                    shadowBlur: 10,
                    shadowOffsetX: 0,
                    shadowColor: 'rgba(0, 0, 0, 0.5)'
                  }
                }
              }
            ]
          }
      );
    }
  }

}
</script>

<style scoped>

</style>
