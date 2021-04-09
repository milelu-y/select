<template>
  <div >
    <tinymce-editor id="tinymce" :ref="data.fieldCode" :tinymceName="data.fieldAliase" :tinymceCode="data.fieldCode"></tinymce-editor>
  </div>
</template>

<script>
import TinymceEditor from '@/components/TinymceEditor'
export default {
  name: "InputTinymce",
  components:{TinymceEditor},
  props: {
    form: {
      type: Object,
      required: true
    },
    data: {
      type: Object,
      required: true
    }
  },
  data(){
    return{
      errmsg: null
    }
  },
  watch: {
    errmsg: {
      handler (newVal, oldVal) {
        if (newVal) {
          this.$message.error(newVal)
        }
      }
    }
  },
  created() {

  },
  methods:{
    getFieldValue () {
      const content = this.$refs[this.data.fieldCode].getContent()
      this.init(content)
      return content
    },
    init (content) {
      const data = this.data
      const fieldAliase = data.fieldAliase
      if (data.isRequired) {
        if (!content) {
          this.msgError(fieldAliase + '必填')
          throw new Error(fieldAliase + '必填!')
        }
      }
      if (data.maxlength) {
        if (content && content.length > data.maxlength) {
          this.msgError(fieldAliase + '长度不能大于' + data.maxlength)
          throw new Error(fieldAliase + '长度不能大于' + data.maxlength)
        }
      }
    },
    setFieldValue (value) {
      if (value) {
        this.$refs[this.data.fieldCode].setContent(value)
      }
    },
    fieldClear () {
      this.$refs[this.data.fieldCode].setContent('')
    },
    change (e) {
      this.errmsg = null
    }
  }
}
</script>

<style scoped>

</style>
