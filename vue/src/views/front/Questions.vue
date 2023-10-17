<template>
  <div style="margin-top: 15px">
    <el-card>
      <el-table :data="tableData">
        <el-table-column label="序号" prop="number" width="140">
          <template slot-scope='scope'>
            <span>{{ (pageNum - 1) * pageSize + (scope.$index + 1) }}</span>
          </template>
        </el-table-column>
        <el-table-column label="试题名称" prop="name"></el-table-column>
        <el-table-column label="试题描述" prop="content"></el-table-column>
        <el-table-column label="试题种类" prop="type"></el-table-column>
      </el-table>
      <div style="padding: 10px 0;display:flex">
        <el-pagination
            :current-page="pageNum"
            :page-size="pageSize"
            :page-sizes="[2, 5, 10, 20]"
            :total="total"
            layout="total, sizes, prev, pager, next"
            @size-change="handleSizeChange"
            @current-change="handleCurrentChange">
        </el-pagination>
        <el-button v-if="tableData.length > 0" style="margin-left: 760px"
                   type="primary"
                   @click="$router.push('/front/questionsDetail?id=' + tableData[0].id + '&indexs=' + 0)">开始练习
        </el-button>
      </div>
    </el-card>
  </div>
</template>

<script>
export default {
  name: "MyCollect",
  data() {
    return {
      tableData: [],
      total: 0,
      pageNum: 1,
      pageSize: 10,
      user: localStorage.getItem("user") ? JSON.parse(localStorage.getItem("user")) : {},
      type: this.$route.query.type,
      chapterLabel: this.$route.query.chapterLabel,
    }
  },
  created() {
    this.load()
  },
  methods: {
    load() {
      this.request.get("/questions/type", {
        params: {
          type: this.type,
          pageNum: this.pageNum,
          pageSize: this.pageSize,
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
.headerBg {
  background: #eee !important;
}
</style>
