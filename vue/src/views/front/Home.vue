<template>
  <div class="home-wrapper">
    <!-- Header Banner -->
    <div class="header-banner">
      <el-carousel :interval="10000" height="450px">
        <el-carousel-item v-for="(item, index) in files" v-if="index < 5" :key="item.id">
          <el-image :src="item.url" fit="cover"></el-image>
        </el-carousel-item>
      </el-carousel>
      <div class="header-banner-text">
        <h1>诈骗预防宣传平台</h1>
        <p>为大学生提供最新、最实用的防诈骗资讯</p>
      </div>
    </div>

    <!-- Announcements & Guide -->
    <div class="content-section">
      <el-row gutter={20}>
        <!-- Announcements -->
        <el-col :span="12">
          <el-card class="notice-card" shadow="always">
            <h2 class="section-title">最新公告</h2>
            <div class="notice-container">
              <el-timeline slot="reference" reverse>
                <el-timeline-item v-for="item in notices" :key="item.id" :timestamp="item.time">
                  <el-popover :content="item.content" placement="right" trigger="hover" width="850">
                    <span slot="reference">{{ item.name }}</span>
                  </el-popover>
                </el-timeline-item>
              </el-timeline>
            </div>
          </el-card>
        </el-col>

        <!-- User Guide -->
        <el-col :span="12">
          <el-card class="guide-card" shadow="always">
            <h2 class="section-title">新手引导</h2>
            <p>1. 注册账号，加入我们的预防社区</p>
            <p>2. 阅读和分享最新的防诈骗资讯</p>
            <p>3. 在讨论区分享你的经验和疑问</p>
            <p>4. 常回来看看，我们经常有新内容！</p>
            <el-button type="primary">开始探索</el-button>
          </el-card>
        </el-col>
      </el-row>
    </div>

    <!-- Footer -->
    <footer>
      <div class="footer-content">
        <p>版权所有 © 2023, Your Company Name</p>
        <p>Email: example@email.com | 电话: +86 12345678</p>
      </div>
    </footer>
  </div>
</template>


<script>
export default {
  name: "FrontHome",
  data() {
    return {
      imgs: [
        require('../../assets/27a28806bdfe7819933acada9742a9fa.jpg'),
        require('../../assets/1.jpeg'),
      ],
      files: [],
      notices: [],
      active: '0',
      blogRecommends: [],
      pageNum: 1,
      pageSize: 5,
      total: 0,
      type: '',
      types: [],
      tableData: [],
      name: '',
      name2: '',
    }
  },
  created() {

    this.load()

    this.request.get("/notice").then(res => {
      this.notices = res.data.splice(0, 10)
    })


    this.request.get("/file/pageHome", {
      params: {
        pageNum: this.pageNum,
        pageSize: this.pageSize,
      }
    }).then(res => {
      this.files = res.data.records
      this.total = res.data.total

    })
    this.loadHotArticles();
    this.loadHotVideos();
  },
  methods: {
    handleClickType(tab, event) {
      this.type = this.types[tab.index - 1].name
      this.load();
    },
    load() {
      this.request.get("/blog/recommend").then(res => {
        this.tableData = res.data
      })

      this.request.get("/blogType").then(res => {
        this.types = res.data
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
.home-wrapper {
  background-color: #ffffff;
  padding: 20px;
}

.header-banner {
  position: relative;
}

.header-banner-text {
  position: absolute;
  top: 50%;
  left: 50%;
  transform: translate(-50%, -50%);
  color: white;
  text-align: center;
}

.section-title {
  font-size: 24px;
  margin-bottom: 20px;
  color: #333;
}

.notice-card, .guide-card {
  height: 400px;
  padding: 20px;
}

.notice-container {
  height: 270px;
  overflow-y: auto;
  padding-right: 10px;
}


footer {
  background-color: rgba(229, 229, 229, 0.2);
  color: #000000;
  padding: 20px 0;
  text-align: center;
}
</style>
