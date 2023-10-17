<template>
  <el-card style="width: 1200px; margin: 10px auto">
    <el-form label-width="80px" size="small">
      <el-upload
          :action="'http://localhost:9090/file/upload'"
          :on-success="handleAvatarSuccess"
          :show-file-list="false"
          class="avatar-uploader"
      >
        <img v-if="form.avatarUrl" :src="form.avatarUrl" class="avatar">
        <i v-else class="el-icon-plus avatar-uploader-icon"></i>
      </el-upload>

      <el-form-item label="用户名">
        <!--        <el-tooltip content="" placement="top">-->
        <el-input v-model="form.username" autocomplete="off" disabled></el-input>
        <!--        </el-tooltip>-->
      </el-form-item>
      <el-form-item label="昵称">
        <el-input v-model="form.nickname" autocomplete="off"></el-input>
      </el-form-item>
      <el-form-item label="邮箱">
        <el-tooltip content="请输入电子邮件以找回密码" placement="top">
          <el-input v-model="form.email" autocomplete="off"></el-input>
        </el-tooltip>
      </el-form-item>
      <el-form-item label="电话">
        <el-tooltip content="请输入电话号码以找回密码" placement="top">
          <el-input v-model="form.phone" autocomplete="off"></el-input>
        </el-tooltip>
      </el-form-item>
      <el-form-item label="地址">
        <el-input v-model="form.address" autocomplete="off" type="textarea"></el-input>
      </el-form-item>
      <el-form-item>
        <el-button type="primary" @click="save">确 定</el-button>
      </el-form-item>
    </el-form>
  </el-card>
</template>

<script>

export default {
  name: "Person",
  data() {
    return {
      form: {},
      user: localStorage.getItem("user") ? JSON.parse(localStorage.getItem("user")) : {}
    }
  },
  created() {
    this.getUser().then(res => {
      console.log(res)
      this.form = res
    })
  },
  methods: {
    async getUser() {
      return (await this.request.get("/user/username/" + this.user.username)).data
    },
    save() {
      this.request.post("/user", this.form).then(res => {
        if (res.code === '200') {
          this.$message.success("保存成功")

          // 触发父级更新User的方法
          this.$emit("refreshUser")

          // 更新浏览器存储的用户信息
          this.getUser().then(res => {
            res.token = JSON.parse(localStorage.getItem("user")).token
            localStorage.setItem("user", JSON.stringify(res))
          })

        } else {
          this.$message.error("保存失败")
        }
      })
    },
    handleAvatarSuccess(res) {
      this.form.avatarUrl = res
    }
  }
}
</script>

<style scoped>
.avatar-uploader {
  text-align: center;
  padding-bottom: 0px;
  width: 190px;
  height: 190px;
  margin: 0 auto;
  /* 垂直居中元素 */
  display: flex;
  justify-content: center;
  align-items: center;
  margin-bottom: 20px;
  border: 1px dashed #d9d9d9;
  border-radius: 10px;
  transition: all 0.3s;
}

.avatar-uploader:hover {
  transform: translateY(-2px);
  box-shadow: 0 10px 20px rgba(0, 0, 0, 0.1);
}

.avatar-uploader-icon {
  font-size: 28px;
  color: #8c939d;
  width: 138px;
  height: 138px;
  line-height: 138px;
  text-align: center;
  transition: color 0.3s;
}

.avatar-uploader-icon:hover {
  color: #409EFF;
}

.avatar {
  width: 164px;
  height: 164px;
  display: block;
  border-radius: 10px;
}

.el-form-item {
  margin-bottom: 25px;
}

.el-button {
  background-color: #409EFF;
  border: none;
  transition: background-color 0.3s;
}

.el-button:hover {
  background-color: #307CDC;
}

.el-input, .el-textarea {
  border-radius: 5px;
  border: 1px solid #dcdcdc;
  transition: border 0.3s;
}

.el-input:focus, .el-textarea:focus {
  border-color: #409EFF;
}

.el-card {
  border-radius: 10px;
  box-shadow: 0 2px 12px rgba(0, 0, 0, 0.1);
  overflow: hidden;
}
</style>

