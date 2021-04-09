<template>
  <div>
    <el-divider dashed />
  <el-col :xl="{ span: 12 }" :lg="{ span: 12 }" :md="{ span: 12 }">
    <el-form-item :label="data.fieldAliase+':'" :prop="data.fieldCode" label-width="65px">
      <el-switch v-model="form[data.fieldCode]" :placeholder="data.description"/>
    </el-form-item>
  </el-col>
  </div>
</template>

<script>
  export default {
    name: "InputSwitch",
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
