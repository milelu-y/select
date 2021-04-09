<template>
  <div style="position:relative;">
    <div class="button-box" v-drag draggable="false">
      <div class="dragball">
        <div class="container">
          <div class="row">
            <div class="col-md-12"> <!-- <div class="btn-bg-img" @dblclick="openBox"></div> -->
              <div class="navbar">主菜单
                <ul class="menu">
                  <li style="margin-left: -14px;top: -80px;z-index: -9999;" @click="notifyHome"><a href="#" class="fa fa-facebook">生成首页</a></li>
                  <li style="margin-left: 8px" @click="notifyWholeSite"><a href="#" class="fa fa-google-plus">生成全站</a></li>
                  <li style="margin-left: -35px" @click="notifyCategory"><a href="#" class="fa fa-google-plus">生成栏目</a></li>
<!--                  <li style="margin-left: -35px" ><a href="#" class="fa fa-twitter">清除缓存</a></li>-->
                  <li style="margin-left: -45px;top: -189px;"><a href="#" class="fa fa-linkedin">清除缓存</a></li>

                </ul>
              </div>
            </div>
          </div>
        </div>
      </div>

      <div class="font-box">{{ text }}</div>
    </div>
  </div>
</template>

<script>

export default {
  components: {},
  props: ['dragball'],
  data () {
    return {
      text: '',
      isOpen: false,
      isMove: false
    }
  },
  methods: {
    notifyHome () {
      this.$store.dispatch('notifyHome')
    },
    notifyWholeSite () {
       this.$store.dispatch('notifyWholeSite')
    },
    notifyCategory () {
       this.$store.dispatch('notifyWholeSite')
    },
    openBox () {
      console.log('双击')
    },
    mousedowm (e) { // 鼠标按下时的鼠标所在的X，Y坐标
      this.mouseDownX = e.pageX
      this.mouseDownY = e.pageY
      // 初始位置的X，Y 坐标
      // this.initX = obj.offsetLeft;
      // this.initY = obj.offsetTop;
      console.log('e', e)
      // 表示鼠标已按下
      this.flag = true
    },
    mousemove (e) {
      if (this.flag) {
        console.log('e :', e)
      }
    }
  },
  directives: {
    drag (el) {
      const oDiv = el // 当前元素
      // let self = this // 上下文
      // 禁止选择网页上的文字
      document.onselectstart = function () {
        return false
      }
      oDiv.onmousedown = function (e) {
        // 鼠标按下，计算当前元素距离可视区的距离
        const disX = e.clientX - oDiv.offsetLeft
        const disY = e.clientY - oDiv.offsetTop
        document.onmousemove = function (e) {
          // 通过事件委托，计算移动的距离
          const l = e.clientX - disX
          const t = e.clientY - disY
          // 移动当前元素
          oDiv.style.left = l + 'px'
          oDiv.style.top = t + 'px'
        }
        document.onmouseup = function (e) {
          document.onmousemove = null
          document.onmouseup = null
        }
        // return false不加的话可能导致黏连，就是拖到一个地方时div粘在鼠标上不下来，相当于onmouseup失效
        return false
      }
    }
  }
}
</script>

<style scoped>
.button-box {
  width: 200px;
  border-radius: 50%;
  position: fixed;
  bottom: 80px;
  right: 50px;
  padding-left: 0px;
  padding-top: 0px;
  cursor: pointer;
  opacity: 0.7;
  z-index: 888;
}
.btn-bg-img {
  width: 50px;
  height: 50px;
  /* background-image: url('/apps.svg'); */
  background-size: cover;
}
.button-box:hover {
  color: white;
  opacity: 1;
}

.font-box {
  width: 80px;
  color: #3193ef;
  text-align: center;
}

.dragball {
    padding: 0em 0;
    transform: translate3d(0, 0, 0);
}
.navbar {
    width: 50px;
    height: 50px;
    line-height: 50px;
    border-radius: 50%;
    background: #d0d0d0;
    margin: 70px auto;
    position: relative;
    cursor: pointer;
    text-align: center;
    font-size: 0.75em;
    /* background-image: url('/apps.svg'); */
    font-weight: bold;
    color: #383838;
    transition: 0.24s 0.2s;
}
.navbar:hover {
    background: orangered;
    /* background-image: url('/apps.svg'); */
}

.navbar .menu {
    list-style: none;
    padding: 0;
    margin: 0;
    position: absolute;
    top: -28px;
    left: -28px;
    border: 50px solid transparent;
    cursor: default;
    border-radius: 50%;
    transform: scale(0);
    transition: transform 1.4s 0.07s;
    z-index: -1;
}
.navbar:hover .menu {
    transition: transform 0.8s 0.08s, z-index 0s 0.5s;
    transform: scale(1);
    color: #383838;
    z-index: 1;
}
.navbar .menu li {
    position: absolute;
    top: -80px;
    left: -150px;
    transform-origin: 100px 100px;
    transition: all 0.5s 0.5s;
}
.navbar:hover .menu li {
    transition: all 0.6s;
}
.navbar .menu li a {
	transition:all .4s ease 0s;
    width: 50px;
    height: 50px;
    line-height: 45px;
    border-radius: 50%;
    background: grey;
    position: absolute;
    font-size: 60%;
    color: white;
    transition: 0.6s;
    text-decoration: none;
}
.navbar .menu li a:hover {
    background-color: black;
    color: darkgoldenrod;
}
.navbar:hover .menu li:nth-child(1) {
    transition-delay: 0.02s;
    transform: rotate(85deg);
}
.navbar:hover .menu li:nth-child(1) a {
    transition-delay: 0.04s;
    transform: rotate(635deg);
}
.navbar:hover .menu li:nth-child(2) {
    transition-delay: 0.04s;
    transform: rotate(125deg);
}
.navbar:hover .menu li:nth-child(2) a {
    transition-delay: 0.08s;
    transform: rotate(595deg);
}
.navbar:hover .menu li:nth-child(3) {
    transition-delay: 0.06s;
    transform: rotate(165deg);
}
.navbar:hover .menu li:nth-child(3) a {
    transition-delay: 0.12s;
    transform: rotate(555deg);
}
.navbar:hover .menu li:nth-child(4) {
    transition-delay: 0.08s;
    transform: rotate(205deg);
}
.navbar:hover .menu li:nth-child(4) a {
    transition-delay: 0.16s;
    transform: rotate(515deg);
}
.navbar:hover .menu li:nth-child(5) {
    transition-delay: 0.1s;
    transform: rotate(245deg);
}
.navbar:hover .menu li:nth-child(5) a {
    transition-delay: 0.2s;
    transform: rotate(475deg);
}
.navbar:hover .menu li:nth-child(6) {
    transition-delay: 0.12s;
    transform: rotate(285deg);
}
.navbar:hover .menu li:nth-child(6) a {
    transition-delay: 0.24s;
    transform: rotate(435deg);
}
.navbar:hover .menu li:nth-child(7) {
    transition-delay: 0.14s;
    transform: rotate(325deg);
}
.navbar:hover .menu li:nth-child(7) a {
    transition-delay: 0.28s;
    transform: rotate(395deg);
}
.navbar:hover .menu li:nth-child(8) {
    transition-delay: 0.16s;
    transform: rotate(365deg);
}
.navbar:hover .menu li:nth-child(8) a {
    transition-delay: 0.32s;
    transform: rotate(355deg);
}
.navbar:hover .menu li:nth-child(9) {
    transition-delay: 0.18s;
    transform: rotate(405deg);
}
.navbar:hover .menu li:nth-child(9) a {
    transition-delay: 0.36s;
    transform: rotate(315deg);
}
</style>
