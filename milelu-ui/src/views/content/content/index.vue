<template>
  <div class="app-container">
    <el-row :gutter="20">
      <el-col :span="4" :xs="24">
        <div class="head-container">
          <el-input
            placeholder="请输入栏目名称"
            clearable
            v-model="filterText"
            size="small"
            prefix-icon="el-icon-search"
            style="margin-bottom: 20px"
          />
        </div>
        <div class="head-container" id="dataPage">
          <!--@node-contextmenu="rightClick"-->
          <el-tree ref="tree" :data="treeData" @node-contextmenu="rightClick" :props="defaultProps" highlight-current
                   @node-click="treeClick" :filter-node-method="filterNode">
            <span class="custom-tree-node" slot-scope="{ node, data }">
              <div class="custom-tree-node-wrapper">
                <span class="custom-tree-node-label">
                 {{ node.label }}
                </span>
              </div>
            </span>
          </el-tree>
        </div>
        <div v-show="menuVisible" @mouseleave="menuVisible=!menuVisible">
          <ul id="menu">
            <li tabindex="-1" class="menu__item" id="menu-4994-1-0" v-for="(row,index) in publishModels">
              <el-link icon="el-icon-plus" :disabled="!item.attributes.allowContribute" @click="() => createCate(row)"
                       type="primary" :underline="false">创建内容({{row.value}})
              </el-link>
            </li>
          </ul>
        </div>
      </el-col>
      <el-col :span="20" :xs="24">
        <el-row :gutter="10" class="mb8">
          <el-col :span="1.5">
            <el-button
              icon="el-icon-bell"
              size="mini"
              @click="publish('1')"
              :disabled="multiple"
            >批量发布
            </el-button>
          </el-col>
          <el-col :span="1.5">
            <el-button
              icon="el-icon-refresh-left"
              size="mini"
              @click="publish('0')"
              :disabled="multiple"
            >批量撤销发布
            </el-button>
          </el-col>
          <el-col :span="1.5">
            <el-button
              icon="el-icon-d-arrow-right"
              size="mini"
              @click="batchGenCid"
            >批量生成
            </el-button>
          </el-col>
          <el-col :span="1.5">
            <el-button
              icon="el-icon-timer"
              size="mini"
              @click="timingPub"
            >定时发布
            </el-button>
          </el-col>
        </el-row>

        <el-table border v-loading="loading" :data="tableData" @selection-change="handleSelectionChange">
          <el-table-column type="selection" width="50" align="center"/>
          <el-table-column label="标题" prop="title" width="400">
            <template slot-scope="scope">
              <div class="demo-fit list-content-cover">
                <div class="block" v-for="(fit,index) in scope.row.cover" :key="index">
                  <el-avatar shape="square" fit="contain" :size="35" :src="fit.url"></el-avatar>
                </div>
              </div>
              <div class="list-content-title">
                <a><span v-html="scope.row.title.substring(0,15)"></span></a><br>
                <a slot="description" v-if="scope.row.description"><span
                  v-html="scope.row.description.substring(0,22)"></span></a>
              </div>
            </template>
          </el-table-column>
          <el-table-column label="分类" align="center" prop="categoryName" width="150" :show-overflow-tooltip="true"/>
          <el-table-column label="模型" align="center" prop="modelName" width="150" :show-overflow-tooltip="true"/>
          <el-table-column label="转载" align="center" prop="copied" :show-overflow-tooltip="true">
            <template slot-scope="scope">
              <el-tag type="warning" v-if="scope.row.copied">转载</el-tag>
              <el-tag type="success" v-else>原创</el-tag>
            </template>
          </el-table-column>
          <el-table-column label="状态" align="center" width="150" :show-overflow-tooltip="true" prop="status">
            <template slot-scope="scope">
              <el-tag v-if="scope.row.status === '0' ">草稿</el-tag>
              <el-tag type="info" v-if="scope.row.status === '1' ">已发布</el-tag>
              <el-tag type="danger" v-if="scope.row.status === '2' ">已删除</el-tag>
            </template>
          </el-table-column>
          <el-table-column label="置顶级别" align="center" width="100" prop="sort"/>
          <el-table-column label="创建时间" align="center" width="150" :show-overflow-tooltip="true" prop="createTime">
            <template slot-scope="scope">
              <span>{{ parseTime(scope.row.createTime) }}</span>
            </template>
          </el-table-column>
          <el-table-column label="发布日期" align="center" prop="publishDate" width="160">
            <template slot-scope="scope">
              <span>{{ parseTime(scope.row.publishDate) }}</span>
            </template>
          </el-table-column>
          <el-table-column label="是否定时" :show-overflow-tooltip="true" align="center" prop="jobDate">
            <template slot-scope="scope">
              <span>{{ parseTime(scope.row.jobDate) }}</span>
            </template>
          </el-table-column>
          <el-table-column
            fixed="right"
            label="操作"
            align="center"
            width="160"
            class-name="small-padding fixed-width"
          >
            <template slot-scope="scope">
              <el-button
                size="mini"
                type="text"
                icon="el-icon-edit"
                @click="handleEdit(scope.row)"
              >编辑
              </el-button>
              <el-button
                size="mini"
                type="text"
                icon="el-icon-caret-top"
                @click="handleTop(scope.row)"
              >置顶
              </el-button>
              <el-button
                v-if="scope.row.userId !== 1"
                size="mini"
                type="text"
                icon="el-icon-delete"
                @click="handleDelete(scope.row)"
              >删除
              </el-button>
            </template>
          </el-table-column>
        </el-table>
      </el-col>
    </el-row>
    <handle ref="handleForm" @ok="handleOk"></handle>
    <top ref="top"  @ok="handleOk"></top>
    <el-dialog
      title="定时发布"
      :visible.sync="openTimePublish"
      width="30%">
      <el-row>
        <div class="block">
          <span class="demonstration">生成时间---:</span>
          <el-date-picker
            v-model="date"
            type="datetime"
            placeholder="选择日期时间">
          </el-date-picker>
        </div>
      </el-row>
      <span slot="footer" class="dialog-footer">
        <el-button plain size="mini">取 消</el-button>
        <el-button plain size="mini" @click="handlePublish">确 定</el-button>
      </span>
    </el-dialog>
  </div>
</template>

<script>
  import handle from './handle'
  import {listManages, treeCategory, publish, reStaticBatchGenCid, jobPublish, delManage} from "@/api/content/content"
  import {listPublishModel} from '@/api/model/model'
  import Handle from "./handle";
  import DotDropdown from "./DotDropdown";
  import {delUser} from "@/api/system/user";
  import Top from "@/views/content/content/top";

  export default {
    name: "index",
    components: {Top, Handle, DotDropdown},
    comments: {handle},
    data() {
      return {
        ids: [],
        openTimePublish: false,
        menuVisible: false,
        nodeData: {},
        treeData: [],
        date: '',
        loading: false,
        tableData: [],
        single: true,
        // 非多个禁用
        multiple: true,
        item: {},
        tmDisplay: null,
        rightMenu: null,
        dropdownVisible: false,
        queryParam: {
          categoryId: null
        },
        filterText: '',
        defaultProps: {
          children: "children",
          label: "title"
        },
        sysDropMenuEvents: [{label: '新增资源', funcName: 'addNode'}],

        publishModels: []
      }
    },
    watch: {
      filterText(val) {
        this.$refs.tree.filter(val);
      }
    },
    created() {
      this.initTreeCategory()
    },
    methods: {
      filterNode(value, data) {
        if (!value) return true;
        return data.title.indexOf(value) !== -1;
      },
      listContentManage() {
        listManages(this.queryParam).then(response => {
          response.rows.forEach(function (item) {
            if (item.coverStr && item.coverStr != undefined) {
              item.cover = JSON.parse(item.coverStr);
            }
          })
          this.tableData = response.rows
        })
      },
      initTreeCategory() {
        treeCategory().then(response => {
          if (response.code === 200) {
            this.treeData = [response.data]
          }
        })
      },
      listPublishModel(categoryId) {
        listPublishModel({
          categoryId: categoryId
        }).then(response => {
          this.publishModels = response.data
          console.log(response.data)
          if (this.publishModels.length === 0) {
            if (categoryId !== '0') {
              this.$message.error('该栏目没有选择模型')
            }
          }
        })
      },
      treeClick(e) {
        this.queryParam.categoryId = e.id
        this.listContentManage();
      },

      rightClick(MouseEvent, data) {
        this.item = data
        this.listPublishModel(data.id);
        this.menuVisible = true;
        let menu = document.querySelector("#menu");
        menu.style.cssText = "position: fixed; left: "
          + (MouseEvent.clientX - 10) + 'px' + "; top: " + (MouseEvent.clientY - 25)
          + 'px; z-index: 999; cursor:pointer;';
      },
      createCate(model) {
        const categoryId = this.item.id
        this.$refs.handleForm.add(model, categoryId)
      },
      handleAdd(model) {
        const categoryId = this.item.id
        this.$refs.handleForm.add(model, categoryId)
      },
      handleEdit(record) {
        this.$refs.handleForm.edit(record)
      },
      /** 删除按钮操作 */
      handleDelete(row) {
        const that = this;
        const ids = row.id || this.ids;
        this.$confirm('是否确认删除标题为"' + row.title + '"的内容项?', "警告", {
          confirmButtonText: "确定",
          cancelButtonText: "取消",
          type: "warning"
        }).then(function () {
          delManage(ids).then(response => {
            that.listContentManage();
          })
        })
      },
      /**
       * 发布撤销
       * @param status
       */
      publish(status) {
        const that = this
        this.$confirm('确定要执行该操作吗?', '操作提醒', {
          confirmButtonText: '确定',
          cancelButtonText: '取消',
          type: 'warning'
        }).then(() => {
          publish({ids: that.ids, status: status}).then(response => {
            if (response.code === 200) {
              this.msgSuccess("~操作成功~")
              this.listContentManage();
            } else {
              this.msgError(response.msg)
            }
          })
        })
      },
      /**生成**/
      batchGenCid() {
        const _this = this
        if (this.multiple) {
          this.$message.error('请选择要操作的记录')
          return
        }
        this.$confirm('确定要重新生成静态页面?', '操作提醒', {
          confirmButtonText: '确定',
          cancelButtonText: '取消',
          type: 'warning'
        }).then(() => {
          reStaticBatchGenCid(_this.ids).then(response => {
            if (response.code === 200) {
              this.msgSuccess("~操作成功~")
            } else {
              this.msgError(response.msg)
            }
          })
        })
      },
      timingPub() {
        this.openTimePublish = true;
      },
      handlePublish() {
        const param = {
          ids: this.ids,
          date: this.date
        }
        jobPublish(param).then(response => {
          this.$message.success("~操作成功~")
          this.handleCancel()
        })
      },
      handleTop(row){
        this.$refs.top.top(row)
      },
      handleSelectionChange(selection) {
        this.ids = selection.map(item => item.id);
        this.single = selection.length != 1;
        this.multiple = !selection.length;
      },
      handleCancel() {
        this.openTimePublish = false
        this.date = ''
      },
      handleOk() {
        this.listContentManage()
      }
    },

  }
</script>

<style type="less">
  .list-content-cover {
    width: 15%;
    float: left;
  }

  .list-content-title {
    width: 80%;
    float: left;
    margin-left: 9px;
    height: 38px;
    line-height: 19px;
  }

  ::-webkit-scrollbar-track-piece {
    background-color: whitesmoke !important;
  }

  ::-webkit-scrollbar {
    width: 9px;
    height: 9px;
  }

  ::-webkit-scrollbar-thumb {
    background-color: #e8e8e8 !important;
    background-clip: padding-box;
    min-height: 28px;
  }

  ::-webkit-scrollbar-thumb:hover {
    background-color: #e8e8e8 !important;
  }

  .el-table__fixed > > > ::before, .el-table__fixed-right > > > ::before {
    content: '';
    position: absolute;
    left: 0;
    bottom: 0;
    width: 100%;
    height: 1px;
    z-index: 4;
  }

  #dataPage {
    margin: 0 0;
    font-family: 'Microsoft Yahei', 'Avenir', Helvetica, Arial, sans-serif;
    -webkit-font-smoothing: antialiased;
    -moz-osx-font-smoothing: grayscale;
    text-align: center;
    color: #2c3e50;
    height: 100%;
  }

  html,
  body {
    height: 100%;
  }

  #dataPage {
    margin: 0 0;
    font-family: 'Microsoft Yahei', 'Avenir', Helvetica, Arial, sans-serif;
    -webkit-font-smoothing: antialiased;
    -moz-osx-font-smoothing: grayscale;
    text-align: center;
    color: #2c3e50;
    height: 100%;
  }

  #el-tree {
    user-select: none;
  }

  .right-menu {
    font-size: 14px;
    position: fixed;
    background: #fff;
    border: solid 1px rgba(0, 0, 0, .2);
    border-radius: 3px;
    z-index: 999;
    display: none;
  }

  .right-menu a {
    width: 150px;
    height: 28px;
    line-height: 28px;
    text-align: center;
    display: block;
    color: #1a1a1a;
  }

  .right-menu a:hover {
    background: #eee;
    color: #fff;
  }

  .right-menu {
    border: 1px solid #eee;
    box-shadow: 0 0.5em 1em 0 rgba(0, 0, 0, .1);
    border-radius: 1px;
  }

  a {
    text-decoration: none;
  }

  .right-menu a {
    padding: 2px;
  }

  .right-menu a:hover {
    background: #99A9BF;
  }

  .menu__item {
    width: 100%;
    display: block;
    line-height: 20px;
    text-align: center;
    margin-top: 10px;

  }

  ::v-deep el-tag el-tag--danger el-tag--medium el-tag--light:focus {
    outline: none;
  }
</style>
