<template>
  <div>
    <el-dialog v-bind="$attrs" v-on="$listeners" :visible.sync="open" width="1200px" @close="onClose" :title="title">
      <el-form ref="elForm" :model="formData" :rules="rules" size="medium" label-width="100px">
        <el-row :gutter="15">
          <el-col :span="12">
            <el-form-item label="路径" prop="path">
              <el-input v-model="formData.path" placeholder="请输入路径" clearable :style="{width: '100%'}">
              </el-input>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="别名" prop="alias">
              <el-input v-model="formData.alias" placeholder="请输入别名" clearable :style="{width: '100%'}">
              </el-input>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="发布路径" prop="publishPath">
              <el-input v-model="formData.publishPath" placeholder="请输入发布路径" clearable :style="{width: '100%'}">
              </el-input>
            </el-form-item>
          </el-col>
        </el-row>
      </el-form>
      <extend-field ref="extends"></extend-field>
      <div slot="footer">
        <el-button @click="close">取消</el-button>
        <el-button type="primary" @click="handleConfirm">确定</el-button>
      </div>
    </el-dialog>
  </div>
</template>
<script>
import {getSingleTemplateMetadataByPath} from '@/api/file/template';
import ExtendField from '@/components/ExtendField'
export default {
  inheritAttrs: false,
  components: {ExtendField},
  props: {
    saveAddTemplate: Function,
  },
  data() {
    return {
      formData: {
        path: "",
        alias: "",
        publishPath:"",
        extendList:[]
      },
      rules: {
        path: [{
          required: true,
          message: '请输入路径',
          trigger: 'blur'
        }],
        alias: [{
          required: true,
          message: '请输入别名',
          trigger: 'blur'
        }],
        publishPath: [{
          required: true,
          message: '请输入发布路径',
          trigger: 'blur'
        }],
      },
      open: false,
      title: ''
    }
  },
  methods: {
    onOpen(filePath,flag,isUp) {
      this.open = true
      this.title = flag
      this.formData.path=filePath
      if (isUp){
        this.getSingleTemplateMetadata(filePath);
      }
    },
    onClose() {
      this.$refs['elForm'].resetFields()
      this.$refs.extends.dataSource=[];
    },
    close() {
      this.open = false;
      this.$emit('update:visible', false)
    },
    handleConfirm() {
      this.formData.extendList=this.$refs.extends.dataSource
      this.$refs['elForm'].validate(valid => {
        if (!valid) return
          this.saveAddTemplate ? this.saveAddTemplate(this.formData) : this.saveTemplate(this.formData)
        this.close()
      })
    },
    saveTemplate(data){
    },

    /**
     * 根据路径获取单个模板
     */
    getSingleTemplateMetadata(path){
      getSingleTemplateMetadataByPath({path:path}).then(response=>{
        if (response.code===200){
          this.formData=response.data
          if (response.data.extendList==null){
            this.$refs.extends.dataSource=[]
          }else {
            this.$refs.extends.dataSource=response.data.extendList
          }
        }
      })
    }
  }
}

</script>
<style>
</style>
