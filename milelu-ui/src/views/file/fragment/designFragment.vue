<template>
  <div>
    <el-form ref="elForm" :model="formData" :rules="rules" size="medium" label-width="100px">
      <el-row :gutter="15">
        <el-col :span="12">
          <el-form-item label="片段别名" prop="alias">
            <el-input v-model="formData.alias" placeholder="请输入片段别名" :maxlength="150" clearable
                      :style="{width: '100%'}"></el-input>
          </el-form-item>
        </el-col>
        <el-col :span="12">
          <el-form-item label="片段编码" prop="code">
            <el-input v-model="formData.code" placeholder="请输入片段编码" disabled :maxlength="99" clearable
                      :style="{width: '100%'}"></el-input>
          </el-form-item>
        </el-col>
        <el-col :span="12">
          <el-form-item label="数据条数" prop="size">
            <el-input-number v-model="formData.size" placeholder="数据条数"></el-input-number>
          </el-form-item>
        </el-col>
      </el-row>
    <el-divider></el-divider>
    <!--处理defaultFieldList-->
      <el-row :gutter="15" v-for="(field,index) in fields" :key="index">
        <el-col :xl="{span: 5}" :lg="{span: 8}" :md="{span: 24}">
          <el-form-item :label="field.fieldName+':'">
            <el-checkbox  v-model="field.check" v-if="field.fieldCode!=='title'"></el-checkbox>
          </el-form-item>
        </el-col>
        <el-col :xl="{span: 6}" :lg="{span: 6}" :md="{span: 24}">
          <el-form-item>
            <p>{{ field.fieldName }}( {{ field.inputType }} )</p>
          </el-form-item>
        </el-col>
        <el-col :xl="{span: 5}" :lg="{span: 8}" :md="{span: 24}" v-if="field.check">
          <el-form-item label="必填">
            <el-switch v-model="field.isRequired" />
          </el-form-item>
        </el-col>
        <el-col  :xl="{span: 5}" :lg="{span: 8}" :md="{span: 24}" v-if="field.check">
          <el-form-item>
            <el-input v-model="field.fieldAliase" />
          </el-form-item>
        </el-col>
      </el-row>
      <el-divider></el-divider>
      <el-row :gutter="15">
        <extend-field ref="extendField"></extend-field>
      </el-row>
      <el-divider></el-divider>
    </el-form>
        <div slot="footer">
          <el-button @click="close">取消</el-button>
          <el-button type="primary" @click="handleConfirm">确定</el-button>
        </div>
  </div>
</template>

<script>
import {getFragmentInfo} from "@/api/file/fragment"
import {updateModel} from "@/api/file/fragment"
import ExtendField from '@/components/ExtendField'
export default {
  name: "designFragment",
  inheritAttrs: false,
  components: {ExtendField},
  props: [],
  data() {
    return {
      formData: {
        alias: undefined,
        code: undefined,
        size: 1,
      },
      rules: {
        alias: [{
          required: true,
          message: '请输入片段别名',
          trigger: 'blur'
        }],
        code: [{
          required: true,
          message: '请输入片段编码',
          trigger: 'blur'
        }],
        size: [{
          required: true,
          message: '计数器',
          trigger: 'blur'
        }],
      },
      fileItem: null,
      fields:[],
    }
  },
  created() {
  },

  methods: {
    init(data) {
      this.fileItem = data
      this.loadFragmentInfo(data.fileInfo.fileName)
    },
    /**
     * 加载详情
     * @param fileName
     */
    loadFragmentInfo(fileName) {
      console.log(fileName);
      getFragmentInfo(fileName).then(response => {
        console.log(response);
        if (response.code === 200) {
          this.formData = response.data;
          //设置默认字段
          this.setFields(this.formData.defaultFieldList);
          //设置扩展字段
          this.setExtendFields(this.formData.extendFieldList);
        }
      })
    },
    /**
     * 设置默认字段
     */
    setFields(defaultFieldList){
      if (defaultFieldList){
        this.fields = JSON.parse(defaultFieldList)
      }
    },
    /**
     * 设置扩展字段
     * @param extendFieldList
     */
    setExtendFields (extendFieldList) {
      if (extendFieldList) {
        this.$refs.extendField.dataSource=JSON.parse(extendFieldList)
      } else {
        this.$refs.extendField.dataSource=[]
      }
    },

    buildData (values) {
      values.defaultFieldList = JSON.stringify(this.fields)
      values.extendFieldList = JSON.stringify(this.$refs.extendField.dataSource)
      values.templateId = this.templateId
    },
    onOpen() {
    },
    onClose() {
      this.$refs['elForm'].resetFields()
    },
    close() {
      this.setExtendFields(null)
      this.$emit('close')
    },
    handleConfirm() {
      this.$refs['elForm'].validate(valid => {
        if (!valid) return
        this.buildData(this.formData)
        updateModel(this.formData).then(response=>{
          if (response.code===200){
            this.msgSuccess("~保存成功~");
            this.$emit("ok");
            this.onClose();
            this.close();
          }else {
            this.msgError(response.msg);
          }
        })
        this.close()
      })
    },
  }
}
</script>

<style  lang="css" scoped>


</style>
