<template>
  <div @click.stop>
    <el-tree
      :data="allData"
      default-expand-all
      :highlight-current="true"
      :render-content="render"
      :expand-on-click-node="false"
      @node-click="nodeClick"
    >
    </el-tree>
  </div>

</template>

<script>
import _ from 'lodash'
import {getTemplateContentByPath} from '@/api/file/template';

export default {
  name: "index",
  props: {
    data: {
      type: Array,
      default: () => []
    },
    fileDrop: Array,
    folderDrop: Array,
    getContent: Function,
    addTemplate: Function,
    updateMetaData: Function,
    generateTemplate:Function
  },
  components: {},
  data() {
    return {
      allData: [],
      open: false,
      title: '',
    }
  },
  watch: {
    data() {
      this.transFormData();
    }
  },
  mounted() {
    this.transFormData();
  },
  methods: {
    isLeaf(isLeaf) {
      return !isLeaf;
    },
    handleDeleteFile(data) {
      this.$confirm(`此操作将永久删除该文件${data.title}, 是否继续?`, '确认', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }).then(() => {
        this.$emit("deleteFile", data);
      }).catch(() => {
        this.$message({
          type: 'info',
          message: '已取消删除'
        });
      });
    },
    handleAddTemplate(data) {
      this.addTemplate ? this.addTemplate(data) : ""
    },
    handleUpdateMetadata(data) {
      this.updateMetaData ? this.updateMetaData(data) : '';
    },
    handleGenerate(data){
      this.generateTemplate ? this.generateTemplate(data) : '';
    },
    handleCommand(data, value) {
      if (value === "rm") {//删除文件or文件夹
        this.handleDeleteFile(data);
      } else if (value === "add") {//新增模板
        this.handleAddTemplate(data);
      } else if (value === 'upMeta') { //修改元数据
        this.handleUpdateMetadata(data);
      }else if (value==='gen'){
        this.handleGenerate(data);
      }
      //其他扩展
      this.$emit("events",data,value);
    },
    render(h, {node, data, store}) {
      let list = this.isLeaf(data.isLeaf) ? this.folderDrop : this.fileDrop;
      return (
        <div style={{width: '100%'}}>
          {
            this.isLeaf(data.isLeaf) ?
              node.expanded ?
                <i class="el-icon-folder-opened"></i> :
                <i class="el-icon-folder"></i> :
              <i class="el-icon-document"></i>
          }
          <span style="padding-left:5px;">{data.label}</span>
          <el-dropdown style="float: right" placement="bottom-start" trigger="click"
                       on-command={this.handleCommand.bind(this, data)}>
          <span class="el-dropdown-link">
            <i class="el-icon-arrow-down el-icon--right"></i>
          </span>
            <el-dropdown-menu slot="dropdown">
              {list.map(item => (
                <el-dropdown-item command={item.text}>{item.value}</el-dropdown-item>
              ))}
            </el-dropdown-menu>
          </el-dropdown>
        </div>
      )
    },
    content(data) {
      getTemplateContentByPath({path: data.relativePath}).then(response => {
        if (response.code === 200) {
          //通知父组件处理fileItem以及内容
          this.$emit("")
        }
      });
    },
    //点击节点事件
    nodeClick(data) {
      this.getContent ? this.getContent(data) : this.content(data);
      this.$emit("nodeClick", data)
    },
    transFormData() {
      //数据进行copy，防止再子组件中操作父数据
      let allDate = _.cloneDeep(this.data);
      this.allData = allDate;
    },
  }
}
</script>

<style scoped>
.el-tree {
  padding-top: 10px;
}

.el-dropdown {
  float: right;
}

</style>
