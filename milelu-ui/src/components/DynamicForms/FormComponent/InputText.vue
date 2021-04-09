<template>
  <div>
    <el-col :xl="{ span: 12 }" :lg="{ span: 12 }" :md="{ span: 12 }">
      <el-form-item :label="data.fieldAliase+':'" :prop="data.fieldCode"
                    :rules="[
                    {required: data.isRequired, message: '请输入'+data.fieldAliase, trigger: 'blur'},
                    data.maxlength !== 0?{ max: data.maxlength, message: data.fieldAliase + '过长' }:{}
                  ]" label-width="65px">
        <el-input v-model="form[data.fieldCode]"  :placeholder="data.description"/>
      </el-form-item>
    </el-col>
  </div>
</template>

<script>
import _ from 'lodash'

export default {
  name: "InputText",
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
    }
  },
  created() {
    console.log("123")
    this.$set(this.form,this.data.fieldCode,this.data.defaultValue)
  },
  methods: {
    init() {
      const data = this.data
    },
    change(v){
      this.$set(this.form,this.fData.fieldCode,v)
      console.log(this.form)
    }
  }
}
</script>

<style scoped>

</style>
