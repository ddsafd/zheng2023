<template>
  <div style="margin-top: 15px">
    <el-card>
      <el-table :data="tableData">
        <el-table-column label="文章" prop="article.name">
          <template slot-scope="scope">
            <a @click="goToArticleDetail(scope.row.article.id)">{{ scope.row.article.name }}</a>
          </template>
        </el-table-column>

        <el-table-column label="封面">
          <template slot-scope="scope">
            <el-image :preview-src-list="[scope.row.article.img]" :src="scope.row.article.img"
                      style="width: 100px; height: 100px"></el-image>
          </template>
        </el-table-column>
        <el-table-column label="用户" prop="userName"></el-table-column>
        <el-table-column label="添加时间" prop="time"></el-table-column>
      </el-table>
      <div style="padding: 10px 0">
        <el-pagination
            :current-page="pageNum"
            :page-size="pageSize"
            :page-sizes="[2, 5, 10, 20]"
            :total="total"
            layout="total, sizes, prev, pager, next"
            @size-change="handleSizeChange"
            @current-change="handleCurrentChange">
        </el-pagination>
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

    }
  },
  created() {
    if (this.user.username == null) {
      this.$message.warning("请登录")
      return;
    }

    this.load()
  },
  methods: {
    load() {
      this.request.get("/collect/page", {
        params: {
          pageNum: this.pageNum,
          pageSize: this.pageSize,
        }
      }).then(res => {
        this.tableData = res.data.records
        this.total = res.data.total
      })
    },
    handleSizeChange(pageSize) {
      this.pageSize = pageSize
      this.load()
    },
    handleCurrentChange(pageNum) {
      this.pageNum = pageNum
      this.load()
    },
    goToArticleDetail(articleId) {
      this.$router.push({
        name: 'ArticleDetail',
        query: {
          id: articleId
        }
      });
    }
  }
}
</script>


<style>
.headerBg {
  background: #eee !important;
}
</style>
