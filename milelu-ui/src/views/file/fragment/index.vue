<template>
  <div class="app-container">

    <el-row :gutter="20">
      <el-col :span="6" :xs="24">
        <div class="header-container">
          <el-button
            plain
            icon="el-icon-plus"
            size="mini"
            :disabled="fileItem.isLeaf"
            @click="addFragment"
          >创建片段
          </el-button>
        </div>
        <div class="head-container padding">
          <My-tree
            :data.sync="data"
            v-if="data.length"
            :fileDrop="fileDrop"
            :folderDrop="folderDrop"
            :getContent="getContent"
            @nodeClick="nodeClick"
            @deleteFile="deleteFile"
            @events="events"
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
            >导入
            </el-button>
          </el-col>
        </el-row>
        <div class="codeTemp" v-show="fileItem.isLeaf">
          <codemirror ref="codemirror" @ctrls="ctrls"></codemirror>
        </div>
      </el-col>
    </el-row>
    <create-fragment ref="createFragment" @loadTree="loadFiles"></create-fragment>
    <el-drawer
      :title="drawerTitle"
      :visible.sync="drawer"
      :show-close="false"
      :size="size"
      append-to-body
    >
      <el-divider></el-divider>
      <design-fragment ref="designFragment" @ok="oKCallback"></design-fragment>
    </el-drawer>

    <el-drawer
      :title="drawerTitle"
      :visible.sync="populateDataDrawer"
      :show-close="false"
      :size="size"
      append-to-body
    >
      <el-divider></el-divider>
      <populate ref="populate" @ok="oKCallback"></populate>
    </el-drawer>

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
import createFragment from "@/views/file/fragment/createFragment";
import Codemirror from '@/components/Codemirror'
import designFragment from "@/views/file/fragment/designFragment";
import populate from "@/views/file/fragment/populate";
import {loadTempTree, getContentByPath, setFragmentContent, delFragmentModel} from "@/api/file/fragment"

export default {
  name: "index",
  components: {MyTree, Codemirror, createFragment, designFragment, populate},
  data() {
    return {
      open: false,
      drawer: false,
      populateDataDrawer: false,//填充片段
      drawerTitle: '',
      size: '1100px',
      fileDrop: [
        {text: 'design', value: '设计片段'},
        {text: 'fill', value: '填充片段'},
        {text: 'delFragment', value: '删除文件'}
      ],
      folderDrop: [
        {text: 'add', value: '创建片段'},
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
    }
  },
  created() {
    this.loadFiles()
  },
  methods: {
    /**
     * 加载页面片段树
     **/
    loadFiles() {
      loadTempTree().then(response => {
        if (response.code === 200) {
          this.data = response.data
        }
      })
    },
    /**
     * 创建页面片段
     * */
    addFragment() {
      this.$refs.createFragment.createFile(this.fileItem)
    },
    /**
     * 树点击事件
     * */
    nodeClick(data) {
      this.fileItem = data
    },

    /**获取页面片段内容*/
    getContent(data) {
      if (data.isLeaf === false) {
        return;
      }
      getContentByPath({path: data.path}).then(response => {
        this.$refs.codemirror.codeContent = response;
      })
    },
    /**ctrl+s保存*/
    ctrls() {
      const relativePath = this.fileItem.path;
      const content = this.$refs.codemirror.codeContent
      const params = {
        path: relativePath,
        content: content
      }
      setFragmentContent(params).then(response => {
        if (response.code === 200) {
          this.msgSuccess("~保存成功~");
        } else {
          this.msgError(response.msg);
        }
      })
    },
    /**
     * 删除文件
     * */
    deleteFile(data) {
      delFragmentModel({path: data.relativePath}).then(response => {
        if (response.code === 200) {
          this.msgSuccess("~删除成功~")
          this.loadFiles();
        } else {
          this.msgError(response.msg);
        }
      })
    },

    /**
     * tree事件集
     */
    events(data, value) {
      if (value === 'design') {
        this.designFragmentFile(data);
      } else if (value === 'fill') {
        this.populateFragmentData(data);
      } else if (value==='add'){
        this.addFragment();
      }else {
        console.log("删除")
      }
    },
    /**片段设计*/
    designFragmentFile(data) {
      this.drawerTitle = "设计片段[" + data.title + "]";
      this.showChildrenDrawer(data)
    },
    populateFragmentData(data) {
      this.drawerTitle = "填充片段[" + data.title + "]";
      this.showChildrenPopulateDataDrawer(data)
    },
    /**
     *显示Drawer
     */
    showChildrenDrawer(fileItem) {
      const _this = this
      this.drawer = true
      setTimeout(() => {
        _this.$refs.designFragment.init(fileItem)
      }, 400)
    },

    /**
     * 设计片段回调
     **/
    oKCallback() {
      this.drawer = false;
      this.populateDataDrawer = false;
    },

    /**
     * 覆盖默认上传行为
     * @param file
     */
    httpRequest(file) {
      let formData = new FormData();
      formData.append("file", file.file);
      formData.append("path", this.fileItem.filePath);
      templateFileUpload(formData).then(response => {
        if (response.code === 200) {
          this.msgSuccess("上传成功");
          this.getTemplatesTree();
        } else {
          this.msgError(response.msg);
        }
      })
    },
    showChildrenPopulateDataDrawer(data) {
      const _this = this
      this.populateDataDrawer = true
      setTimeout(() => {
        _this.$refs.populate.init(data);
      }, 400)

    }
  }
}
</script>

<style>
.CodeMirror {
  height: 800px !important;
  width: 100% !important;
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

<style lang="scss" scoped>
/deep/ :focus {
  outline: 0;
}
</style>
