<template>
  <div>
    <el-form :model="form" ref="form" :rules="rules" label-width="100px">

      <el-col :xl="{span: 20}" :lg="{span:20}" :md="{span: 24}" v-show="false">
        <el-form-item label="categoryId">
          <el-input v-model="form.categoryId"/>
        </el-form-item>
      </el-col>

      <el-col :xl="{span: 20}" :lg="{span:20}" :md="{span: 24}">
        <el-form-item label="标题:">
          <el-input v-model="form.title"/>
        </el-form-item>
      </el-col>

      <el-col :xl="{span: 20}" :lg="{span: 20}" :md="{span: 24}">
        <el-form-item label="关键字:">
          <el-input v-model="form.keywords"/>
        </el-form-item>
      </el-col>

      <el-col :xl="{span: 20}" :lg="{span: 20}" :md="{span: 24}">
        <el-form-item label="描述:">
          <el-input type="textarea" v-model="form.description"></el-input>
        </el-form-item>
      </el-col>

    </el-form>
  </div>
</template>

<script>
import {updateCategoryAttribute, getSeo} from '@/api/category/category'
import _ from 'lodash'
export default {
  name: "SEO",
  data() {
    return {
      form: {},
      categoryId: null,
      rules: {
        categoryId: [{required: true, message: '请输入categoryId！'}],
        title: [{required: false, min: 2, message: '请输入至少二个字符的规则！'}],
        keywords: [[{required: false, min: 2, message: '请输入至少二个字符的规则！'}]],
        description: [{required: false, max: 500, message: '最大500字符！'}]
      }
    }
  },
  methods: {
    setCategoryId(categoryId) {
      this.categoryId = categoryId
      this.form.categoryId = categoryId
      this.getSeoInfo()
    },
    getSeoInfo() {
      getSeo({categoryId: this.categoryId}).then(response => {
        if (response.data){
          this.form=_.pick(response.data,'title','description','keywords')
        }
      })
    },
    handleSubmit() {
      this.$refs.form.validate((valid) => {
        if (valid) {
          console.log(this.form);
          updateCategoryAttribute(this.form).then(response => {
            console.log(response);
            if (response.code === 200) {
              this.msgSuccess("~保存成功~");
              this.$emit('ok', this.categoryId)
            } else {
              this.msgError(response.msg);
            }
          })
        }
      })
    },
    handleCancel() {
      this.$refs.form.resetFields()
    },

  }
}
</script>

<style scoped>

</style>
