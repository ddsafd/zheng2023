<template>
  <el-card style="margin-top: 15px">
    <el-form label-width="100px" size="small" style="width: 90%">
      <el-form-item label="类别" prop="blogType">
        <template>
          <el-select v-model="form.blogType" placeholder="请选择">
            <el-option v-for="item in types" :key="item.name" :label="item.name" :value="item.name"></el-option>
          </el-select>
        </template>
      </el-form-item>
      <el-form-item label="论坛名称">
        <el-input v-model="form.name" autocomplete="off" placeholder="请输入论坛名称"></el-input>
      </el-form-item>
      <el-form-item label="封面">
        <el-upload

            :action="'http://localhost:9090/file/upload'"
            :on-success="handleImgUploadSuccess"
            :show-file-list="false"
            class="avatar-uploader"
        >
          <img v-if="form.img" :src="form.img" class="avatar">
          <i v-else class="el-icon-plus avatar-uploader-icon" style="border: 1px dashed #d9d9d9"></i>
        </el-upload>
      </el-form-item>

      <el-form-item label="论坛内容">
        <div id="richText"></div>
      </el-form-item>

    </el-form>
    <div style="margin-top: 10px;margin-left: 988px">
      <el-button size="small" type="success" @click="submit">提交</el-button>
    </div>
  </el-card>
</template>

<script>
import E from "wangeditor"

let editor;

export default {
  name: "AddBlog",
  data() {
    return {
      form: {
        img: ""
      },
      user: localStorage.getItem("user") ? JSON.parse(localStorage.getItem("user")) : {},
      types: []
    }
  },
  created() {
    // 初始化富文本编辑器
    this.handleAdd()

    this.request.get("/blogType").then(res => {
      this.types = res.data
    })
  },
  methods: {
    submit() {
      if (this.user.username == null) {
        this.$message({
  message: '请登录',
  type: 'warning',
  duration: 500  // 设置为1.5秒
});

        return;
      }
      let data = {
        name: this.form.name,
        content: editor.txt.html(),
        userId: this.user.id,
        img: this.form.img,
        blogType: this.form.blogType
      }
      console.log(data);
      if (data.blogType === null || data.blogType === '' || data.blogType === 'undefined') {
        this.$message.error("请选择博客类别")
        return;
      }
      this.request.post("/blog", data).then(res => {
        if (res.code === '200') {
          this.$message.success("发表成功")
          this.form = {}
          editor.txt.html('')
          this.load()
        } else if (res.code === '201') {
          this.$message.error(res.msg)
        } else {
          this.$message.error("发表失败")
        }
      })
    },
    handleAdd() {
      editor = ''
      this.$nextTick(() => {
        if (!editor) {
          editor = new E("#richText")
          // 配置 server 接口地址
          editor.config.uploadImgServer = 'http://localhost:9090/file/uploadImg'
          editor.config.uploadFileName = 'file'
          editor.create()
        }
        editor.txt.html('')
      })
    },
    handleImgUploadSuccess(res) {
      this.form.img = res
    },
  }
}
</script>

<style scoped>
.avatar-uploader {
  padding-bottom: 10px;
}

.avatar-uploader .el-upload {
  border: 1px dashed #d9d9d9;
  border-radius: 6px;
  cursor: pointer;
  position: relative;
  overflow: hidden;
}

.avatar-uploader .el-upload:hover {
  border-color: #409EFF;
}

.avatar-uploader-icon {
  font-size: 28px;
  color: #8c939d;
  width: 138px;
  height: 138px;
  line-height: 138px;
}

.avatar {
  width: 138px;
  height: 138px;
  display: block;
}
</style>

