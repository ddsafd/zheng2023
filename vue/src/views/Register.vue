<template>
  <div class="wrapper">
    <div style="height: 60px; line-height: 60px; font-size: 20px; padding-left: 50px; color: white;
      background-color: rgba(0,0,0,0.2)">诈骗预防宣传系统
    </div>
    <div
        style="margin: 150px auto; background-color: rgba(0,0,0,0.2); width: 350px; height: 370px; padding: 20px; border-radius: 10px">
      <div style="margin: 20px 0; text-align: center; font-size: 24px"><b>注 册</b></div>
      <el-form ref="userForm" :model="user" :rules="rules">
        <el-form-item prop="username">
          <el-input v-model="user.username" placeholder="请输入账号" prefix-icon="el-icon-user" size="medium"></el-input>
        </el-form-item>
        <el-form-item prop="password">
          <el-input v-model="user.password" placeholder="请输入密码" prefix-icon="el-icon-lock" show-password
                    size="medium"></el-input>
        </el-form-item>
        <el-form-item prop="confirmPassword">
          <el-input v-model="user.confirmPassword" placeholder="请确认密码" prefix-icon="el-icon-lock" show-password
                    size="medium"></el-input>
        </el-form-item>
        <el-form-item prop="sex">
          <template>
            <el-radio v-model="user.sex" label="男" style="color: #eeeeee">男</el-radio>
            <el-radio v-model="user.sex" label="女" style="color: #eeeeee">女</el-radio>
          </template>
        </el-form-item>
        <el-form-item style="margin: 5px 0; text-align: right">
          <el-button autocomplete="off" size="small" type="warning" @click="$router.push('/login')">返回登录</el-button>
          <el-button autocomplete="off" size="small" type="primary" @click="login">注册</el-button>
        </el-form-item>
      </el-form>
    </div>
  </div>
</template>

<script>
export default {
  name: "Login",
  data() {
    return {
      user: {},
      rules: {
        username: [
          {required: true, message: '请输入用户名', trigger: 'blur'},
          {min: 3, max: 10, message: '长度在 3 到 5 个字符', trigger: 'blur'}
        ],
        password: [
          {required: true, message: '请输入密码', trigger: 'blur'},
          {min: 1, max: 20, message: '长度在 1 到 20 个字符', trigger: 'blur'}
        ],
        confirmPassword: [
          {required: true, message: '请输入密码', trigger: 'blur'},
          {min: 1, max: 20, message: '长度在 1 到 20 个字符', trigger: 'blur'}
        ],
      }
    }
  },
  methods: {
    login() {
      this.$refs['userForm'].validate((valid) => {
        if (valid) {  // 表单校验合法
          if (this.user.password !== this.user.confirmPassword) {
            this.$message.error("两次输入的密码不一致")
            return false
          }
          this.request.post("/user/register", this.user).then(res => {
            if (res.code === '200') {
              this.$message.success("注册成功")
            } else {
              this.$message.error(res.msg)
            }
          })
        }
      });
    }
  }
}
</script>

<style>
.wrapper {
  height: 100vh;
  background-image: url("../assets/11111.jpg");
  background-size: cover;
  overflow: hidden;
}
</style>
