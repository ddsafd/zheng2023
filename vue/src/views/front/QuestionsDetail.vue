<template>
  <div style="padding: 10px;width: 1200px;">
    <el-row :gutter="10">
      <el-col :span="20">
        <el-card>
          <div style="padding: 10px 0; border-bottom: 1px solid #ccc; color: #1E90FF; font-size: 18px">
            试题详情&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<span
              style="font-size: 13px;color:#222222">试题总数：{{ tableData.length }}</span>
            &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<span
              style="font-size: 13px;color: orangered">当前题数：{{ parseInt(indexs) + 1 }}</span>
          </div>
          <h2 style="margin: 15px 0">{{ questions.name }}</h2>
          <div style="margin-top: 15px" v-html="questions.content"></div>
        </el-card>

        <el-card style="margin-top: 10px">
          <el-radio v-if="answer === ''" v-model="answer" label="A">{{ questions.choiceA }}</el-radio>
          <el-radio v-if="answer != ''" v-model="answer" disabled label="A">{{ questions.choiceA }}</el-radio>
        </el-card>
        <el-card style="margin-top: 10px">
          <el-radio v-if="answer === ''" v-model="answer" label="B">{{ questions.choiceB }}</el-radio>
          <el-radio v-if="answer != ''" v-model="answer" disabled label="B">{{ questions.choiceB }}</el-radio>
        </el-card>
        <el-card style="margin-top: 10px">
          <el-radio v-if="answer === ''" v-model="answer" label="C">{{ questions.choiceC }}</el-radio>
          <el-radio v-if="answer != ''" v-model="answer" disabled label="C">{{ questions.choiceC }}</el-radio>
        </el-card>
        <el-card style="margin-top: 10px">
          <el-radio v-if="answer === ''" v-model="answer" label="D">{{ questions.choiceD }}</el-radio>
          <el-radio v-if="answer != ''" v-model="answer" disabled label="D">{{ questions.choiceD }}</el-radio>
        </el-card>

        <el-card v-if="answer != '' && questions.answer === answer" style="margin-top: 10px">
          <h4 style="color: #0f9876">结果：正确</h4>
        </el-card>
        <el-card v-if="answer != '' && questions.answer != answer" style="margin-top: 10px">
          <h4 style="color: orangered">结果：错误</h4>
        </el-card>

        <el-card v-if="answer != ''" style="margin: 10px 0">
          <div style="padding: 10px 0; border-bottom: 1px solid #ccc; color: #1E90FF; font-size: 18px">
            试题答案&解析
          </div>
          <h4 style="margin:10px 0;color: #0f9876">答案：{{ questions.answer }}</h4>
          <div style="margin-top: 15px">解析：{{ questions.explaination }}</div>
        </el-card>
      </el-col>
      <el-col :span="3">
        <el-card>
          <div>
            <el-button v-if="indexs != 0" type="primary" @click="prev">上一题</el-button>
          </div>
          <div style="margin-top:10px">
            <el-button v-if="indexs != tableData.length - 1" type="primary" @click="next">下一题</el-button>
          </div>
          <div style="margin-top:10px">
            <el-button v-if="indexs == tableData.length - 1" type="danger" @click="end">结束练习</el-button>
          </div>
        </el-card>
      </el-col>
    </el-row>
  </div>
</template>

<script>
export default {
  name: "BlogDetail",
  data() {
    return {
      id: this.$route.query.id,
      questions: {},
      user: localStorage.getItem("user") ? JSON.parse(localStorage.getItem("user")) : {},
      answer: '',
      tableData: [],
      total: 0,
      indexs: this.$route.query.indexs
    }
  },
  created() {
    this.load()
  },
  methods: {
    end() {
      this.$router.push('/front/questions?type=' + this.questions.type)
    },
    next() {
      this.indexs = parseInt(this.indexs) + 1
      this.$router.push("/front/questionsDetail?id=" + this.tableData[this.indexs].id + "&indexs=" + this.indexs)
      location.reload()
    },
    prev() {
      this.indexs = parseInt(this.indexs) - 1
      this.$router.push("/front/questionsDetail?id=" + this.tableData[this.indexs].id + "&indexs=" + this.indexs)
      location.reload()
    },
    load() {
      this.request.get("/questions/" + this.id).then(res => {
        this.questions = res.data

        this.request.get("/questions/type", {
          params: {
            type: this.questions.type,
            pageNum: 1,
            pageSize: 10000,
          }
        }).then(res => {
          this.tableData = res.data.records
          this.total = res.data.total
        })
      })
    },
  }
}
</script>

<style scoped>
.item1 {
  padding: 10px;
  color: #666;
}
</style>