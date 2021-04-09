<template>
  <div>
    <codemirror
      ref="myCm"
      :options="codeOptions"
      v-model="codeContent"
      class="code"
      @beforeChange="beforeChange"
      @focus="focus"
      @cursorActivity="cursorActivity"
      @changes="changes"
    ></codemirror>
  </div>
</template>

<script>
import { codemirror } from 'vue-codemirror'
import 'codemirror/lib/codemirror.css' // css，必要
// 编辑的主题文件
import 'codemirror/theme/base16-light.css'
import 'codemirror/theme/eclipse.css'
import 'codemirror/theme/darcula.css'
import 'codemirror/addon/fold/foldgutter.css'
// 编辑器代码高亮css文件
import 'codemirror/addon/hint/show-hint.css'
require('codemirror/addon/fold/foldcode.js')
require('codemirror/addon/fold/foldgutter.js')
require('codemirror/addon/fold/brace-fold.js')
require('codemirror/addon/fold/xml-fold.js')
require('codemirror/addon/fold/indent-fold.js')
require('codemirror/addon/fold/markdown-fold.js')
require('codemirror/addon/fold/comment-fold.js')
require('codemirror/mode/htmlmixed/htmlmixed.js')
require('codemirror/addon/hint/html-hint.js')
export default {
  name: "index",
  components: {
    codemirror
  },
  data () {
    const _this = this
    return {
      mdl: {},
      theme: 'darcula',
      codeContent: '',
      codeOptions: {
        lineNumbers: true,
        smartIndent: true,
        mode: 'text/html',
        foldGutter: true,
        gutters: ['CodeMirror-linenumbers', 'CodeMirror-foldgutter'],
        extraKeys: { 'Tab': 'autocomplete',
          'Ctrl-S': function () {
            _this.$emit('ctrls')
          }
        },
        theme: 'darcula'// 编辑器主题
      }
    }
  },
  methods: {
    setCodeContent (content) {
      this.codeContent = content
    },
    getCodeContent (content) {
      return this.codeContent
    },
    beforeChange (e, data) {
      if (data.origin === '+input' && data.text && data.text.length > 0 && data.text[0] === '@') {
        this.$emit('tip')
        data.cancel()
      }
    },
    focus () {
      this.$emit('focus')
    },
    cursorActivity (e) {
      this.$emit('cursorActivity', e)
    },
    replaceSelection(text){
      if(text)
        this.$refs.myCm.codemirror.replaceSelection(text)
    },
    setFocus(){
      let codemirror = this.$refs.myCm.codemirror
      codemirror.focus();
    },
    changes (e, arrays) {

    },
    formatText (texts) {
      let text = ''
      if (texts && Array.isArray(texts)) {
        texts.forEach(function (val, index) {
          text += val
        })
      }
      return text
    },
    clear () {
      this.$refs.myCm.codemirror.setValue('')
    }
  }
}
</script>

<style scoped>

</style>
