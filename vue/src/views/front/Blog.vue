<template>
  <div style="width:1100px;margin: 15px auto">
    <el-row>
      <el-col :span="24">
        <div style="margin-top: 20px;">
          <el-tabs v-model="type" tab-position="top" @tab-click="handleClickType">
            <el-tab-pane disabled>
              <span slot="label" style="font-size: 15px;color: #8c939d">分类</span>
            </el-tab-pane>
            <el-tab-pane v-for="item in types" :label="item.name" :name="item.name"></el-tab-pane>
          </el-tabs>
        </div>
      </el-col>
    </el-row>

    <div style="margin: 10px 0">
      <div v-for="item in tableData" :key="item.id" style="margin: 10px 0">
        <el-card>
          <div style="display: flex">
            <div style="flex: 1">
              <div class="name"
                   style="font-size: 23px; font-weight: bold; cursor: pointer"
                   @click="$router.push('/front/blogDetail?id=' + item.id)">{{ item.name }}
              </div>
              <div style="margin: 10px 0; color: #666"></div>
              <div style="margin-top: 57px; color: #666">
                <span><i class="el-icon-user"></i> {{ item.user }}</span>
                <span style="margin-left: 10px"><i class="el-icon-time"></i> {{ item.time }}</span>
                <span style="margin-left: 10px"><i class="el-icon-s-promotion"></i> <el-tag style="font-size: 15px"
                                                                                            type="success">{{
                    item.blogType
                  }}</el-tag></span>
                <span style="margin-left: 10px"><i class="el-icon-view"></i> 浏览量&nbsp;{{ item.pageviews }}</span>
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
      type: '',
      types: [],
      name2: '',
      recommends: []
    }
  },
  created() {
    this.load()
  },
  methods: {
    handleClickType(tab, event) {
      this.type = this.types[tab.index - 1].name
      this.load();
    },
    load() {
      console.log(this.type)
      this.request.get("/blog/page/type", {
        params: {
          pageNum: this.pageNum,
          pageSize: this.pageSize,
          name: this.name,
          type: this.type
        }
      }).then(res => {
        this.tableData = res.data.records
        this.total = res.data.total
      })

      this.request.get("/blogType").then(res => {
        this.types = res.data
      })

      this.request.get("/blog/recommend").then(res => {
        this.recommends = res.data
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
</style>
