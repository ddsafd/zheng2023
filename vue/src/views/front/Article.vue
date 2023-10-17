<template>
  <div style="width:1100px;margin: 15px auto">
    <div style="margin: 10px 0">
      <div v-for="item in tableData" :key="item.id" style="margin: 10px 0">
        <el-card>
          <div style="display: flex">
            <div style="flex: 1">
              <div class="name"
                   style="font-size: 23px; font-weight: bold; cursor: pointer"
                   @click="$router.push('/front/articleDetail?id=' + item.id)">{{ item.name }}
              </div>
              <div style="margin: 10px 0; color: #666"></div>
              <div style="margin-top: 57px; color: #666">
                <span><i class="el-icon-user"></i> {{ item.user }}</span>
                <span style="margin-left: 10px"><i class="el-icon-time"></i> {{ item.time }}</span>
                <span style="margin-left: 10px"><i class="el-icon-view"></i> 收藏量&nbsp;{{ item.collectCount }}</span>
              </div>
            </div>
            <div style="width: 200px">
              <img :src="item.img" alt="" style="width: 200px; height: 110px">
            </div>
          </div>
        </el-card>
      </div>
    </div>
    <div v-if="tableData.length" style="padding: 10px 0">
      <el-pagination
          :current-page="pageNum"
          :page-size="pageSize"
          :page-sizes="[5, 10, 20]"
          :total="total"
          layout="total, prev, pager, next"
          @size-change="handleSizeChange"
          @current-change="handleCurrentChange">
      </el-pagination>
    </div>

  </div>
</template>

<script>
export default {
  name: "FrontHome",
  data() {
    return {
      pageNum: 1,
      pageSize: 8,
      total: 0,
      tableData: [],
      name: this.$route.query.name,
      form: {},
      user: localStorage.getItem("user") ? JSON.parse(localStorage.getItem("user")) : {},
    }
  },
  created() {
    this.load()
  },
  methods: {
    load() {
      console.log(this.type)
      this.request.get("/article/page", {
        params: {
          pageNum: this.pageNum,
          pageSize: this.pageSize,
          name: this.name,
        }
      }).then(res => {
        this.tableData = res.data.records
        this.total = res.data.total
      })
    },
    handleSizeChange(pageSize) {
      console.log(pageSize)
      this.pageSize = pageSize
      this.load()
    },
    handleCurrentChange(pageNum) {
      console.log(pageNum)
      this.pageNum = pageNum
      this.load()
    },
  }
}
</script>

<style>
.fontCss {
  font-size: 13px;
  color: #494949;
}

.el-card:hover {
  transform: scale(1.05); /* Scales the content 5% larger */
  transition: all 0.3s ease-in-out; /* Smooth transition effect */
  box-shadow: 0 4px 15px rgba(0, 0, 0, 0.1); /* Optional: Add a shadow effect for more depth */
}

/* Add this style for the image hover effect */
.el-card img:hover {
  transform: scale(1.1);
  transition: all 0.3s ease-in-out;
}
</style>
