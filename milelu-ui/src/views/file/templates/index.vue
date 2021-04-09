<template>
  <div class="app-container">

    <el-row :gutter="20">

      <!--部门数据-->
      <el-col :span="6" :xs="24">
        <div class="header-container">
          <el-button
            plain
            icon="el-icon-plus"
            size="mini"
            :disabled="fileItem.isLeaf"
            @click="addTemplate"
          >新增模板
          </el-button>
        </div>
        <div class="head-container padding">
          <My-tree
            :data.sync="data"
            v-if="data.length"
            :fileDrop="fileDrop"
            :folderDrop="folderDrop"
            :getContent="getContent"
            @deleteFile="deleteFile"
            :addTemplate="addTemplate"
            :updateMetaData="updateMetaData"
            :generateTemplate="generateTemplate"
            @nodeClick="nodeClick"
          ></My-tree>
        </div>
      </el-col>
      <!--模板-->
      <el-col :span="18" :xs="24">
        <el-row :gutter="10" class="mb8">
          <el-col :span="1.5">
            <el-button
              plain
              icon="el-icon-upload2"
              size="mini"
              @click="openUploadTemplate"
            >导入
            </el-button>
          </el-col>
        </el-row>
        <div class="codeTemp" v-show="fileItem.isLeaf">
          <codemirror ref="codemirror" @ctrls="ctrls"></codemirror>
        </div>
      </el-col>
    </el-row>
    <template-form ref="template" :saveAddTemplate="saveAddTemplate"></template-form>
    <el-dialog title="导入模板" :visible.sync="open" width="400px">
      <el-upload
        class="upload-demo"
        drag
        action="#"
        :http-request="httpRequest"
        multiple>
        <i class="el-icon-upload"></i>
        <div class="el-upload__text">将文件拖到此处，或<em>点击上传</em></div>
        <div class="el-upload__tip" slot="tip">只能上传html文件</div>
      </el-upload>
    </el-dialog>
  </div>
</template>

<script>
  import MyTree from "@/components/MyTree";
  import TemplateForm from "@/components/TemplateForm";
  import Codemirror from "@/components/Codemirror";
  import {
    listTemplateTree,
    getTemplateContentByPath,
    updateTemplateContent,
    deleteTemplate,
    addSaveTemplate,
    uploadSaveTemplate,
    templateFileUpload,
    generateTemplate
  } from '@/api/file/template';
  // 编辑的主题文件
  export default {
    name: "index",
    components: {MyTree, Codemirror, TemplateForm},
    data() {
      return {
        open: false,
        fileDrop: [
          {text: 'rm', value: '删除文件'},
          {text: 'upMeta', value: '修改元数据'},
          {text: 'gen', value: '生成模板'}
        ],
        folderDrop: [
          {text: 'add', value: '创建模板'},
          {text: 'rm', value: '删除文件夹'},
        ],
        data: [],
        id: 0,
        fileItem: {
          fileContent: '',
          filePath: null,
          isLeaf: false,
          fileKey: null
        },
        codeOptions: {
          lineNumbers: true,
          smartIndent: true,
          mode: 'text/html',
          foldGutter: true,
          gutters: ['CodeMirror-linenumbers', 'CodeMirror-foldgutter'],
          extraKeys: {'Ctrl': 'autocomplete'},
        }
      }
    },
    created() {
      this.getTemplatesTree();
    },
    methods: {
      //获取模板树
      getTemplatesTree() {
        listTemplateTree().then(response => {
          this.data = response.children;
        })
      },
      //获取模板内容
      getContent(data) {
        this.fileItem.fileKey = data.key
        getTemplateContentByPath({path: data.relativePath}).then(response => {
          if (response.code === 200) {
            this.fileItem.fileContent = response.msg
            this.$refs.codemirror.setCodeContent(this.fileItem.fileContent);
          }
          this.fileItem.filePath = data.relativePath
          this.fileItem.isLeaf = data.isLeaf
        });
      },
      nodeClick(data){
        this.fileItem=data
      },
      //删除文件夹或者文件
      deleteFile(data) {
        deleteTemplate({path: data.relativePath}).then(response => {
          if (response.code === 200) {
            this.msgSuccess("删除模板成功");
            this.getTemplatesTree();
          } else {
            this.msgError(response.msg)
          }
        })
      },
      /**
       * 修改模板文件内容
       */
      saveUpdateContent() {
        let path = this.fileItem.filePath;
        let content = encodeURIComponent(this.$refs.codemirror.getCodeContent());
        const param = {
          path: path,
          content: content
        }
        updateTemplateContent(param).then(response => {
          if (response.code === 200) {
            this.msgSuccess("~保存成功~");
          } else {
            this.msgError(response.msg)
          }
        })
      },
      ctrls(){
        this.saveUpdateContent();
      },
      /**
       * 新增模板
       */
      addTemplate() {
        this.id = 1
        this.$refs.template.onOpen(this.fileItem.filePath, "新增模板");
        this.fileItem.filePath = ""
      },
      /**
       * 保存新增模板
       * @param data
       */
      saveAddTemplate(data) {
        if (this.id === 1) {
          addSaveTemplate({path: data.path, alias: data.alias, publishPath: data.publishPath,extendList:data.extendList}).then(response => {
            if (response.code === 200) {
              this.msgSuccess("新增模板成功");
              this.getTemplatesTree();
            } else {
              this.msgError(response.msg)
            }
          })
        } else {
          uploadSaveTemplate({path: data.path, alias: data.alias, publishPath: data.publishPath,extendList:data.extendList}).then(response => {
            if (response.code === 200) {
              this.msgSuccess("修改模板元数据成功");
              this.getTemplatesTree();
            } else {
              this.msgError(response.msg)
            }
          })
        }

      },
      /**
       * 修改元数据
       */
      updateMetaData(data) {
        this.id = 0;
        this.$refs.template.onOpen(data.relativePath, '修改元数据', true);
      },
      generateTemplate(data){
        console.log(data)
        generateTemplate(data).then(response=>{
          console.log(response)
        })
      },
      /**
       * 打开模板上传窗口
       */
      openUploadTemplate() {
        this.open = true;
      },
      /**
       * 覆盖默认上传行为
       * @param file
       */
      httpRequest(file) {
        let formData = new FormData();
        formData.append("file", file.file);
        formData.append("path", this.fileItem.filePath);
        templateFileUpload(formData).then(response=>{
          if (response.code===200){
            this.msgSuccess("上传成功");
            this.getTemplatesTree();
          }else {
            this.msgError(response.msg);
          }
        })
      }
    }
  }
</script>

<style type="text/css">
  .el-tree{
    border: 1px dashed  #ccc!important;padding:14px;border-radius: 16px!important;    height: 800px !important;

  }
  .padding{
    padding-top: 10px;
  }

  .CodeMirror {
    height: 800px !important;
    width: 100% !important;
    /*background-color: rgb(222, 225, 218)*/
  }

  .codeTemp {
    border: 1px dashed #e8e8e8;
    border-radius: 6px
  }

  .el-tree {
    border: 1px dashed #ccc !important;
    padding: 14px;
    border-radius: 16px !important;
    height: 800px !important;
  }

  .padding {
    padding-top: 10px;
  }
  .codeTemp {
    border: 1px dashed #e8e8e8;
    border-radius: 6px;

  }

  ::-webkit-scrollbar-track-piece {
    background-color: #2B2B2B;
  }

  ::-webkit-scrollbar {
    width: 9px;
    height: 9px;
  }

  ::-webkit-scrollbar-thumb {
    background-color: #2B2B2B;
    background-clip: padding-box;
    min-height: 28px;
  }

  ::-webkit-scrollbar-thumb:hover {
    background-color: #2B2B2B;
  }
</style>
