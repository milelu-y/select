<template>
  <div>
    <el-divider/>
    <el-col :xl="{ span: 4 }" :lg="{ span: 12 }" :md="{ span: 12 }">
      <div>
        <input type="file" ref="file" webkitdirectory name="file" @change.stop="changeData"/>
        <p v-if="fileList" v-for="(item,index) in fileList" :key="index">
          {{item.name}}
        </p>
        <span slot="footer" class="el-dialog__footer">
          <el-button>取消</el-button>
          <el-button @click="uploadRequest">确定</el-button>
        </span>
      </div>
    </el-col>
  </div>
</template>

<script>
  import {uploadFile, deleteFile} from "@/api/upload/upload";
  import _ from 'lodash'

  export default {
    name: "InputFile",
    props: {
      form: {
        type: Object,
        required: true
      }
    },
    data() {
      return {
        dialogImageUrl: '',
        dialogVisible: false,
        disabled: false,
        fileList: [],
        hideUploadEdit:false
      };
    },
    watch: {},
    methods: {
      handleRemove(file,fileList) {
        if (file && !file.error && file.response && file.response.data.id) {
          deleteFile(file.response.data.id).then(response => {
            if (response.code === 200) {
              this.msgSuccess("~删除成功~")
              console.log('delete file success')
              this.fileList = []
            }
          })
        }
      },
      handleDownload(file) {
        if (file && !file.error && file.response && file.response.res.fileUid) {
          deleteFile({
            fileUid: file.response.res.fileUid
          }).then(response => {
            if (response.code === 0) {
              console.log('delete file success')
            }
          })
        }
      },
      /**
       * 覆盖默认上传
       */
      uploadRequest(file) {
        let formData = new FormData();
        for (let i = 0; i < this.fileList.length; i++){
          formData.append("file",this.fileList[i])
        }
        uploadFile(formData).then(response => {
          if (response.code === 200) {
            file.onSuccess(response)
            this.msgSuccess("~上传成功~");
          } else {
            this.msgError(response.msg)
          }
        })
      },
      /**
       * 上传成功回调
       */
      uploadSuccess(file, response) {
        this.fileList.push(response);
      },
      getFieldValue() {
        return this.filterField()
      },
      filterField() {
        const fields = []
        if (this.fileList.length > 0) {
          const field = this.fileList[0]
          const fileObj = field.response.data
          fields.push({
            uid: fileObj.fileUid,
            fileUid: fileObj.fileUid,
            name: field.name,
            id: fileObj.id,
            status: 'done',
            url: fileObj.url
          })
        }
        return fields
      },
      setFieldValue(value) {
        if (value) {
          this.beforeSetVal(value)
        }
      },
      fieldClear() {
        this.fileList = []
      },
      beforeSetVal(value) {
        if (value && value instanceof Array) {
          const fileList = []
          for (let i = 0; i < value.length; i++) {
            const val = value[i]
            val.response = {
              code: 0,
              msg: '操作成功!',
              data: Object.assign({}, val)
            }
            fileList.push(val)
            console.log(val.url)
          }
          this.fileList = fileList
        }
      },
      changeData(){
        console.log(this.$refs.file.files)
        this.fileList = this.$refs.file.files
        console.log(this.fileList)
      }
    },

  }
</script>

<style scoped>
  .hide >>> .el-upload--picture-card {
    display: none;
  }
</style>
