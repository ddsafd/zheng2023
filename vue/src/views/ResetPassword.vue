<template>
  <div>
    <h3>重置密码</h3>
    <el-form :model="resetForm">
      <el-form-item label="新密码">
        <el-input v-model="resetForm.password" type="password"></el-input>
      </el-form-item>
      <el-form-item label="确认密码">
        <el-input v-model="resetForm.confirmPassword" type="password"></el-input>
      </el-form-item>
      <el-button @click="resetPassword">重置密码</el-button>
    </el-form>
  </div>
</template>

<script>
export default {
  data() {
    return {
      resetForm: {
        password: '',
        confirmPassword: ''
      },
      token: '' // 假设邮件中的链接附带了一个token作为查询参数，用于验证请求的合法性
    }
  },
  mounted() {
    // 从URL中获取token
    const urlParams = new URLSearchParams(window.location.search);
    this.token = urlParams.get('token');
  },
  methods: {
    resetPassword() {
      if (this.resetForm.password !== this.resetForm.confirmPassword) {
        this.$message.error("两次输入的密码不匹配");
        return;
      }
      this.request.post("/user/resetPassword", {
        token: this.token,
        newPassword: this.resetForm.password
      }).then(res => {
        if (res.code === '200') {
          this.$message.success("密码重置成功，请重新登录");
          this.$router.push("/login");
        } else {
          this.$message.error(res.msg);
        }
      });
    }
  }
}
</script>
