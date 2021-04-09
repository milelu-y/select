<template>
  <div></div>
</template>

<script>
import SockJS from 'sockjs-client'
import Stomp from 'stompjs'

export default {
  name: "NotifySocket",
  data() {
    return {
      stompClient: {},
      visible: false,
      percent: 0,
      timer: null,
      token: ''
    }
  },
  created() {
    this.token = this.$store.getters.token
    this.initWebSocket()
  },
  methods: {
    initWebSocket() {
      this.connection()
    },
    connection() {
      // 更换that指针process.env.VUE_APP_BASE_API
      const that = this
      const socket = new SockJS('http://127.0.0.1:7777/stomp?token=' + this.token, {}, {timeout: 40000})
      this.stompClient = Stomp.over(socket)
      this.stompClient.hasDebug = null
      //建立连接，订阅主题
      this.stompClient.connect({}, (frame) => {
        this.stompClient.subscribe('/user/queue/msg', (val) => {
          const notifyMsg = JSON.parse(val.body)
          console.log("notifyMsg", notifyMsg)
          that.showNotifyMsg(notifyMsg)
        })
        this.stompClient.subscribe('/topic/sub', function (response) {
          console.log(response)
        })
        this.clearInter()
        that.changeConnec('Socket 连接成功')
      }, (err) => {
        //连接发生错误时的处理函数
        that.reconnect()
        that.changeConnec('Socket 连接失败')
        console.log(err)
      })
    },
    clearInter() {
      if (this.timer) {
        clearInterval(this.timer)
        this.timer = null
      }
    },
    reconnect() {
      const _this = this
      // 断开重连机制,尝试发送消息,捕获异常发生时重连
      if (!_this.timer) {
        _this.timer = setInterval(() => {
          console.log('~~失败重连~~')
          _this.connection()
        }, 25000)
      }
      // 需要有一个失败重连得到问题
    },
    changeConnec(msg) {
      this.$notify({
        title: 'Socket 状态',
        message: msg,
        position: 'bottom-right',
      });
    },
    showNotifyMsg(notifyMsg) {
      const _this = this
      this.$notify({
        title: notifyMsg.channerName + '静态化通知',
        dangerouslyUseHTMLString: true,
        message: <div>
          <el-progress status="success" percentage={notifyMsg.percent} size="small"/>
          <span>{notifyMsg.destPath} <br/> {notifyMsg.msg}</span>
        </div>,
        position: 'bottom-right',
      });
    },
  },

}
</script>

<style scoped>
.el-notification {
  border: 1px solid #ffffff;
}
</style>
