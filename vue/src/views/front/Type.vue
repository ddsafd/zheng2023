<template>
  <div style="width:1100px;margin: 10px auto">
    <div style="margin: 10px 0">
      <el-row :gutter="10" style="margin: 10px 0">
        <el-col v-for="item in types" :key="item.id" :span="6" style="margin-bottom: 10px">
          <div style="border: 1px solid #ccc; padding-bottom: 10px; border-radius: 10px; overflow: hidden">
            <img :src="item.img" alt="" style="width: 100%;height: 270px; cursor: pointer">
            <div style="padding: 5px; font-size: 18px;font-weight: bold; cursor: pointer;color: #494949;"
                 @click="$router.push('/front/questions?type=' + item.name)">
              {{ item.name }}
            </div>
          </div>
        </el-col>
      </el-row>
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
    }
  },
  created() {
    this.load()
  },
  methods: {
    load() {
      this.request.get("/type").then(res => {
        this.types = res.data
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
  }
}
</script>

<style>
.fontCss {
  font-size: 13px;
  color: #494949;
}
</style>
