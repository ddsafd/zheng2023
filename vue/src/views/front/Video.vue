<template>
  <div class="video-list-container">
    <div v-for="item in tableData" :key="item.id" class="video-card">
      <el-card>
        <div class="video-content">
          <!-- Thumbnail with play button overlay -->
          <div class="video-thumbnail">
            <img :src="item.img" alt="Video thumbnail">
            <div class="play-button" @click="$router.push('/front/videoDetail?id=' + item.id)"><i
                class="el-icon-play"></i></div>
          </div>
          <div class="video-info">
            <div class="video-title" @click="$router.push('/front/videoDetail?id=' + item.id)">{{ item.name }}</div>
            <div class="user-info"><i class="el-icon-user"></i> {{ item.user }}</div>
            <div class="video-time"><i class="el-icon-time"></i> {{ item.time }}</div>
            <div class="collect-count"><i class="el-icon-view"></i> 收藏量&nbsp;{{ item.collectCount }}</div>
          </div>
        </div>
      </el-card>
    </div>
    <div v-if="tableData.length" class="pagination-container">
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
      this.request.get("/video/page", {
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

.video-list-container {
  width: 1100px;
  margin: 15px auto;
  display: flex;
  flex-wrap: wrap;
  gap: 15px;
}

.video-card {
  width: calc(33.33% - 10px);
  margin-bottom: 20px;
}

.video-content {
  position: relative;
}

.video-thumbnail img {
  width: 100%;
  height: auto;
  cursor: pointer;
  transition: transform 0.3s;
}

.video-thumbnail:hover img {
  transform: scale(1.1);
}

.play-button {
  position: absolute;
  top: 50%;
  left: 50%;
  transform: translate(-50%, -50%);
  font-size: 24px;
  cursor: pointer;
  color: white;
}

.video-info {
  padding: 10px;
}

.video-title {
  font-size: 23px;
  font-weight: bold;
  cursor: pointer;
  margin-bottom: 5px;
}

.user-info, .video-time, .collect-count {
  font-size: 13px;
  color: #494949;
  margin-top: 5px;
}

.pagination-container {
  width: 100%;
  display: flex;
  justify-content: center;
  margin-top: 20px;
}

</style>
