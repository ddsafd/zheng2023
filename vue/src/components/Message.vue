<template>
  <div class="message-container">
    <h2>用户留言和咨询</h2>

    <!-- Display messages -->
    <ul class="message-list">
      <li v-for="message in messages" :key="message.id" class="message-item">
        <p><strong>{{ message.userId }}</strong>: {{ message.content }}</p>
        <p v-if="message.isAnswered" class="answer">回答: {{ message.answer }}</p>
      </li>
    </ul>

    <!-- Add a new message -->
    <div class="message-input">
      <textarea v-model="newMessage" placeholder="输入您的留言或咨询"></textarea>
      <button @click="addMessage">提交</button>
    </div>
  </div>
</template>

<script>
import axios from 'axios';

export default {
  name: "Message",
  data() {
    return {
      messages: [],
      newMessage: ''
    };
  },
  created() {
    this.fetchMessages();
  },
  methods: {
    fetchMessages() {
      axios.get('/api/message/getAll').then(response => {
        this.messages = response.data;
      });
    },
    addMessage() {
      if (this.newMessage.trim() === '') {
        alert('留言内容不能为空！');
        return;
      }

      const message = {
        userId: 1, // 这里只是一个示例，您可能需要从其他地方获取用户ID
        content: this.newMessage
      };

      axios.post('/api/message/add', message).then(() => {
        this.newMessage = '';
        this.fetchMessages();
      });
    }
  }
};
</script>

<style scoped>
.message-container {
  max-width: 800px;
  margin: 0 auto;
  padding: 20px;
  border: 1px solid #e5e5e5;
  border-radius: 5px;
  box-shadow: 0 2px 5px rgba(0, 0, 0, 0.1);
}

.message-list {
  list-style-type: none;
  padding: 0;
}

.message-item {
  padding: 10px;
  border-bottom: 1px solid #e5e5e5;
}

.message-item:last-child {
  border-bottom: none;
}

.answer {
  color: green;
}

.message-input {
  margin-top: 20px;
}

.message-input textarea {
  width: 100%;
  padding: 10px;
  border: 1px solid #e5e5e5;
  border-radius: 5px;
  resize: none;
}

.message-input button {
  margin-top: 10px;
  padding: 5px 15px;
  background-color: #007bff;
  color: #fff;
  border: none;
  border-radius: 5px;
  cursor: pointer;
  transition: background-color 0.3s;
}

.message-input button:hover {
  background-color: #0056b3;
}
</style>
