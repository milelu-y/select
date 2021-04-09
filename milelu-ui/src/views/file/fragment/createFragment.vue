<template>
  <div>
    <el-dialog v-bind="$attrs" v-on="$listeners" :visible.sync="visible" @open="onOpen" @close="onClose" :title="title">
      <el-form ref="elForm" :model="formData" :rules="rules" size="medium" label-width="100px">
        <el-row :gutter="15">
          <el-col :span="12">
            <el-form-item label="片段别名" prop="alias">
              <el-input v-model="formData.alias" placeholder="用于标记片段名称便于维护和设计" clearable
                        :style="{width: '100%'}"></el-input>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="片段编码" prop="code">
              <el-input v-model="formData.code" placeholder="编码必须为字母切不可修改" clearable :style="{width: '100%'}">
              </el-input>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="数据条数" prop="size">
              <el-input-number v-model="formData.size" placeholder="数据条数" step-strictly>
              </el-input-number>
            </el-form-item>
          </el-col>
        </el-row>
      </el-form>
      <div slot="footer">
        <el-button @click="close">取消</el-button>
        <el-button type="primary" @click="handleConfirm">确定</el-button>
      </div>
    </el-dialog>
  </div>
</template>
<script>
import { addModel } from '@/api/file/fragment'
export default {
  name: "createFragment",
  inheritAttrs: false,
  components: {},
  props: [],
  data() {
    return {
      formData: {
        alias: undefined,
        code: undefined,
        size: 1,
      },
      title: '',
      visible: false,
      fileItem: null,
      rules: {
        alias: [{
          required: true,
          message: '用于标记片段名称便于维护和设计',
          trigger: 'blur'
        }],
        code: [{
          required: true,
          message: '编码必须为字母切不可修改',
          trigger: 'blur'
        }],
        size: [{
          required: true,
          message: '数据条数',
          trigger: 'blur'
        }],
      },
    }
  },
  computed: {},
  watch: {},
  created() {
  },
  mounted() {
  },
  methods: {
    /**创建页面片段*/
    createFile(fileItem) {
      this.fileItem = fileItem
      this.popUps("新建页面片段");
    },
    popUps(title) {
      this.title = title;
      this.visible = true;
      // this.$refs['elForm'].resetFields()
    },
    onOpen() {

    },
    onClose() {
      this.$refs['elForm'].resetFields()
      this.$emit("loadTree")
    },
    close() {
      this.$emit('update:visible', false)
    },
    handleConfirm() {
      this.$refs['elForm'].validate(valid => {
        if (!valid) return
        this.formData.path = this.fileItem.path;
        this.formData.relativePath = this.fileItem.relativePath;
        if (this.formData.path===undefined || this.formData.path===null ||
          this.formData.relativePath===undefined || this.formData.relativePath===null)
        {
          this.msgInfo("请选择目录!!!");
          return;
        }
        addModel(this.formData).then(response=>{
            if (response.code===200){
              this.visible=false;
              this.msgSuccess("~创建成功~");
            }else {
              this.msgError(response.msg);
            }
        })
        this.close()
      });
    },
  }
}

</script>
<style>
</style>
