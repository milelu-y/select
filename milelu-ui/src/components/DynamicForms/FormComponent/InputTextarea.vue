<template>
  <div>
    <el-col :xl="{ span: 24 }" :lg="{ span: 24 }" :md="{ span: 24 }">
      <el-divider dashed />
      <el-form-item :label="data.fieldAliase+':'" :prop="data.fieldCode"
                    :rules="[
                    {required: data.isRequired, message: '请输入'+data.fieldAliase, trigger: 'blur'},
                    data.maxlength !== 0?{ max: data.maxlength, message: data.fieldAliase + '过长' }:{}
                  ]" label-width="70px">
        <el-input type="textarea" v-model="form[data.fieldCode]" :placeholder="data.description"/>
      </el-form-item>
    </el-col>
  </div>
</template>

<script>
export default {
  name: "InputTextarea",
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
  created() {
    //处理默认值
    this.$set(this.form,this.data.fieldCode,this.data.defaultValue)
  }
}
</script>

<style scoped>

</style>
