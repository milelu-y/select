<template>
  <div>
    <el-scrollbar style="height: 300px">
    <el-form ref="elForm" :model="formData" :rules="rules" size="medium" label-width="100px">
      <el-row v-for="(model,index) in models" :key="index">
        <el-col :span="7">
          <el-form-item label="模型名称" prop="name">
              <el-checkbox  :key="index" @change="checkModel($event,model)" v-model="model.check">
                {{ model.name }}
              </el-checkbox>
          </el-form-item>
        </el-col>
        <tree-temp-select :ref="model.id" :model="model" @select="changeTemplate"></tree-temp-select>
      </el-row>
    </el-form>
    </el-scrollbar>
  </div>
</template>
<script>
import TreeTempSelect from "@/views/category/category/TreeTempSelect";
import {listModel,listModels} from "@/api/model/model";
import {saveRelation} from "@/api/category/categoryModelRelation";
import _ from 'lodash'
export default {
  inheritAttrs: false,
  components: {TreeTempSelect},
  props: [],
  data() {
    return {
      formData: {
        name: [1],
      },
      rules: {
        name: [{
          required: true,
          type: 'array',
          message: '请至少选择一个name',
          trigger: 'change'
        }],
      },
      nameOptions: [{
        "label": "选项一",
        "value": 1
      },{
        "label": "选项二",
        "value": 2
      }],
      categoryId:null,
      models:[]
    }
  },
  computed: {},
  watch: {},
  created() {
  },
  mounted() {
  },
  methods: {
    listModels () {
      listModels({ categoryId: this.categoryId }).then(response => {
        if (response.code === 200) {
          this.models = response.data
          console.log("models",this.models)
        }
      })
    },
    setCategoryId(categoryId){
      this.categoryId = categoryId
      this.listModels()
    },
    changeTemplate(id,value){
      if (this.models.length > 0) {
        for (let i = 0; i < this.models.length; i++) {
          const model = this.models[i]
          if (model.id === id) {
            model.templatePath = value.relativePath
          }
        }
      }
    },
    onOpen() {
    },
    onClose() {
      this.$refs['elForm'].resetFields()
    },
    close() {
      this.$emit('update:visible', false)
    },
    handleSubmit(){
      const models = this.getCheckModelData();
      if (models.length > 0) {
        console.log(models)
        saveRelation(models).then(response => {
          console.log("嗷嗷",response)
          if (response.code === 200) {
            this.$emit('ok')
            this.$message.success('~保存成功~')
          } else {
            this.$message.error(response.msg)
          }
        })
      } else {
        this.$message.error('模型不能为空')
      }
    },
    handelConfirm() {
      this.$refs['elForm'].validate(valid => {
        if (!valid) return
        this.close()
      })
    },
    getCheckModelData() {
      const models = []
      if (this.models.length > 0) {
        for (let i = 0; i < this.models.length; i++) {
          const model = this.models[i]
          if (model.check) {
            models.push({
              modelId: model.id,
              categoryId: this.categoryId,
              templatePath: model.templatePath
            })
          }
        }
      }
      return models
    },
    checkModel(e, model){
      // console.log(e)
      // console.log(model)
      // if (!e){
      //   model.md5Key=undefined;
      //   model.templatePath="";
      // }
    }
  }
}

</script>
<style>
</style>
