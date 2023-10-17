<template>
  <div class="wrapper">
    <div style="height: 60px; line-height: 60px; font-size: 20px; padding-left: 50px; color: white;
      background-color: rgba(0,0,0,0.2)">诈骗预防宣传系统
    </div>
    <div
        style="margin: 150px auto; background-color: rgba(0,0,0,0.2); width: 350px; height: 300px; padding: 20px; border-radius: 10px">
      <div style="margin: 20px 0; text-align: center; font-size: 24px"><b>登 录</b></div>
      <el-form ref="userForm" :model="user" :rules="rules">
        <el-form-item prop="username">
          <el-input v-model="user.username" prefix-icon="el-icon-user" size="medium"></el-input>
        </el-form-item>
        <el-form-item prop="password">
          <el-input v-model="user.password" prefix-icon="el-icon-lock" show-password size="medium"></el-input>
        </el-form-item><!--这里解开注释**/-->
        <el-form-item>
          <div style="display: flex">
            <el-input v-model="code" size="mid" style="width: 200px"></el-input>
            <span style="cursor: pointer; flex: 1;" @click="refreshCode">
                      <Identify :identifyCode="identifyCode"></Identify>
                   </span>
          </div>
        </el-form-item>

        <el-form-item style="margin: 10px 0; text-align: right">
          <el-button autocomplete="off" size="small" type="warning" @click="$router.push('/register')">前往注册</el-button>
          <el-button autocomplete="off" size="small" type="primary" @click="login">登录</el-button>

        </el-form-item>
        <el-form-item style="margin: 10px 0; text-align: right">
          <el-button autocomplete="off" size="mid" type="text" @click="handlePass">找回密码</el-button>
        </el-form-item>
      </el-form>
    </div>


    <el-dialog :visible.sync="dialogFormVisible" title="找回密码" width="30%">
      <el-form label-width="100px" size="small">
        <el-form-item label="用户名">
          <el-input v-model="pass.username" autocomplete="off"></el-input>
        </el-form-item>
        <el-form-item label="邮  箱">
          <el-input v-model="pass.email" autocomplete="off"></el-input>
        </el-form-item>
        <el-form-item label="手机号">
          <el-input v-model="pass.phone" autocomplete="off"></el-input>
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button @click="dialogFormVisible = false">取 消</el-button>
        <el-button type="primary" @click="passwordBack">重置密码</el-button>
      </div>
    </el-dialog>
  </div>
</template>

<script>
import {resetRouter, setRoutes} from "@/router";
import Identify from "@/components/Identify";

export default {
  name: "Login",
  data() {
    return {
      user: {},
      pass: {},
      code: '',
      dialogFormVisible: false,
      // 图片验证码
      identifyCode: '',
      // 验证码规则
      identifyCodes: '3456789ABCDEFGHGKMNPQRSTUVWXY',
      rules: {
        username: [
          {required: true, message: '请输入用户名', trigger: 'blur'},
          {min: 3, max: 10, message: '长度在 3 到 5 个字符', trigger: 'blur'}
        ],
        password: [
          {required: true, message: '请输入密码', trigger: 'blur'},
          {min: 1, max: 20, message: '长度在 1 到 20 个字符', trigger: 'blur'}
        ],
      }
    }
  },
  components: {Identify},
  mounted() {
    // 重置路由
    resetRouter()
    this.refreshCode()
  },
  methods: {
    login() {
      if (this.code.toLowerCase() !== this.identifyCode.toLowerCase()) {
        this.$message({
          type: "error",
          message: "验证码错误"
        });
        this.refreshCode();
        return;
      }
      this.$refs['userForm'].validate((valid) => {
        if (valid) {  // 表单校验合法
          this.request.post("/user/login", this.user).then(res => {
            if (res.code === '200') {
              localStorage.setItem("user", JSON.stringify(res.data))  // 存储用户信息到浏览器
              localStorage.setItem("menus", JSON.stringify(res.data.menus))  // 存储用户信息到浏览器

              // 动态设置当前用户的路由
              setRoutes()
              if (res.data.role === 'ROLE_USER') {
                this.$router.push("/front/article")
                this.$message.success("登录成功")
              } else {
                this.$router.push("/")
                this.$message.success("登录成功")
              }
            } else {
              this.$message.error(res.msg)
            }
          })
        }
      });
    },
    // 切换验证码
    refreshCode() {
      this.identifyCode = ''
      this.makeCode(this.identifyCodes, 4)
    },
    // 生成随机验证码
    makeCode(o, l) {
      for (let i = 0; i < l; i++) {
        this.identifyCode += this.identifyCodes[Math.floor(Math.random() * (this.identifyCodes.length))]
      }
    },
    handlePass() {
      this.dialogFormVisible = true
      this.pass = {}
    },
    passwordBack() {
      this.request.put("/user/reset", this.pass).then(res => {
        if (res.code === '200') {
          this.$message.success("重置密码成功，新密码为：123456，请尽快修改密码")
          this.dialogFormVisible = false
        } else {
          this.$message.error(res.msg)
        }
      })
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
