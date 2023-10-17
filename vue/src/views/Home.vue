<template>
  <div style="color: #666;font-size: 14px;">
    <div style="padding-bottom: 20px">
      <b>您好！{{ user.nickname }}</b>
    </div>
    <el-card>
      欢迎使用本系统
    </el-card>

    <el-card style="width: 100%;  margin: 20px 0">
      <h2 style="margin: 20px 0">公告列表</h2>
      <div>
        <el-timeline slot="reference" reverse>
          <el-timeline-item v-for="item in tableData" :key="item.id" :timestamp="item.time">
            <el-popover
                :content="item.content"
                placement="right"
                trigger="hover"
                width="200">
              <span slot="reference">{{ item.name }}</span>
            </el-popover>
          </el-timeline-item>
        </el-timeline>
      </div>
    </el-card>

  </div>
</template>

<script>
export default {
  name: "Home",
  data() {
    return {
      activeNames: ['0'],
      tableData: [],
      user: localStorage.getItem("user") ? JSON.parse(localStorage.getItem("user")) : {},
    }
  },
  created() {
    this.request.get("/notice", {}).then(res => {
      this.tableData = res.data.splice(0, 5)
      this.total = res.data.total
    })
  }
}
</script>
