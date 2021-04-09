<template>
  <div>
    <el-dialog
      :title="title"
      :visible.sync="dialogVisible"
      width="70%"
      append-to-body
      @close="close"
      >

      <el-form :model="form" ref="Form" label-width="200px" class="demo-ruleForm">
        <dynamic-forms ref="dynamicForms" :form="form" @submit="submit"></dynamic-forms>
      </el-form>

      <span slot="footer" class="dialog-footer">
    <el-button @click="handleCancel">取 消</el-button>
    <el-button type="primary" @click="handleSubmit">确 定</el-button>
      </span>
    </el-dialog>
  </div>
</template>

<script>
import DynamicForms from '@/components/DynamicForms/index'
export default {
  name: "ModelDynamicForm",
  components:{
    DynamicForms
  },
  props: {
    width: {
      type: String,
      default: '70%',
      required: false
    },
    title: {
      type: String,
      default: '表单管理',
      required: false
    }
  },
  data(){
    return{
      form:{},
      dialogVisible:false
    }
  },
  methods:{
    initForm (models) {
      console.log("models",models)
      const _this = this
      _this.dialogVisible=true;
      if (models) {
        setTimeout(() => {
          _this.$refs.dynamicForms.setFields(models)
        }, 300)
      }
      setTimeout(() => {
      }, 300)
    },
    submit (data) {
      this.$emit('submit', data)
    },
    handleSubmit(){
      this.$refs.dynamicForms.submitFrom()
    },
    /**
     * 清理
     * @param e
     */
    handleCancel (e) {
      const _this = this
      this.$emit('beforeClose')
      setTimeout(() => {
        _this.dialogVisible = false
      }, 150)
      this.$emit('close')
    },
    close(){
      this.$emit('beforeClose')
    },
    cleanForm () {
      this.$refs.dynamicForms.resetFieldsClear(true)
    }
  }
}
</script>

<style scoped>

</style>
