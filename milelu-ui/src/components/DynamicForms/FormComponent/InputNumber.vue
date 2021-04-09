<template>
  <div>
    <el-col :xl="{ span: 12 }" :lg="{ span: 12 }" :md="{ span: 12 }">
      <el-form-item :label="data.fieldAliase+':'" :prop="data.fieldCode"
                    :rules="[
                    {required: data.isRequired, message: '请输入'+data.fieldAliase, trigger: 'blur'}
                  ]" label-width="65px">
        <el-input-number :max="max" style="width: 100%" v-model="form[data.fieldCode]" :placeholder="data.description"/>
      </el-form-item>
    </el-col>
  </div>
</template>

<script>
export default {
  name: "InputNumber",
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
  watch:{
    data(newData,oldData){
      this.$set(this.form,newData.fieldCode,newData.defaultValue)
    }
  },
  data() {
    return {
      rules: {},
      max: 99999
    }
  },
  created() {
    //处理默认值
    this.$set(this.form,this.data.fieldCode,this.data.defaultValue)
  }
}
</script>

<style scoped>

</style>
