import {notifyHomePage, notifyWholeSite} from '@/api/channel/channel.js'


const notifyIt = {
  mutations: {},
  actions: {
    // 通知生成首页
    notifyHome({
                 commit
               }, response) {
      return new Promise((resolve, reject) => {
        notifyHomePage().then(response => {
        })
      })
    },
    notifyWholeSite({
                      commit
                    }, response) {
      return new Promise((resolve, reject) => {
        notifyWholeSite().then(response => {
        })
      })
    },
    notifyCategory({
                      commit
                    }, response) {
      return new Promise((resolve, reject) => {
        notifyCategory().then(response => {
        })
      })
    },
    flushDB({
              commit
            }, response) {
      return new Promise((resolve, reject) => {
        // flushdb().then(response => {
        //   // if (response.code === 0) {
        //   //    _this.$message.info('~请稍后~')
        //   // } else {
        //   //    _this.$message.info('操作通知异常')
        //   // }
        // })
      })
    }


  }
}

export default notifyIt
