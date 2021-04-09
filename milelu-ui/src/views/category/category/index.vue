<template>
  <div class="app-container">
    <el-row :gutter="20">
      <!--部门数据-->
      <el-col :span="4" :xs="24">
        <el-tree
          :data="treeData"
          :props="defaultProps"
          @node-click="treeClick"
          :highlight-current="true"
          :expand-on-click-node="false"
          default-expand-all
        >
           <span class="custom-tree-node" slot-scope="{ node, data }">
              <div class="custom-tree-node-wrapper">
                <span class="custom-tree-node-label">
                 {{ node.label }}
                </span>
              </div>
            </span>
        </el-tree>
      </el-col>
      <el-col :span="20" :xs="24">
        <el-row :gutter="10" class="mb8">
          <el-col :span="1.5">
            <el-button
              plain
              size="mini"
              :disabled="single"
              @click="handleAddWin"
            >
              创建子栏目
            </el-button>
          </el-col>
          <el-col :span="1.5">
            <el-button
              plain
              size="mini"
              :disabled="single"
              @click="handleDelete(item)"
            >
              删除栏目
            </el-button>
          </el-col>
          <right-toolbar @queryTable="getList"></right-toolbar>
        </el-row>

        <el-table ref="table" border v-loading="loading" :data="tableData">
          <el-table-column type="selection" width="50" align="center"/>
          <el-table-column label="栏目名称" align="center" prop="name" :show-overflow-tooltip="true"/>
          <el-table-column label="父栏目" align="center" prop="parentName" :show-overflow-tooltip="true" />
          <el-table-column label="栏目编码" align="center" prop="code" :show-overflow-tooltip="true"/>
          <el-table-column label="排序" align="center" prop="sort" :show-overflow-tooltip="true"/>
          <el-table-column label="栏目模型" align="center" :formatter="modelFormatter" prop="categoryModelId" width="120"/>
          <el-table-column label="前台显示" :formatter="hiddenFormatter" align="center" prop="hidden" width="120"/>
          <el-table-column label="允许投稿" align="center" :formatter="allowContributeFormatter" prop="allowContribute"
                           width="120"/>
          <el-table-column label="创建时间" align="center" prop="createTime" width="160">
            <template slot-scope="scope">
              <span>{{ parseTime(scope.row.createTime) }}</span>
            </template>
          </el-table-column>
          <el-table-column
            label="操作"
            align="center"
            width="160"
            class-name="small-padding fixed-width"
          >
            <template slot-scope="scope">
              <el-button
                v-if="!scope.row.onlyUrl"
                size="mini"
                type="text"
                icon="el-icon-edit"
                @click="handleStatic(scope.row)"
              >静态化
              </el-button>
              <el-button
                size="mini"
                type="text"
                icon="el-icon-edit"
                @click="handleEditor(scope.row)"
              >修改
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

        <pagination
          v-show="total>0"
          :total="total"
          :page.sync="queryParams.pageNum"
          :limit.sync="queryParams.pageSize"
          @pagination="getList"
        />
      </el-col>
    </el-row>

    <!-- 添加或修改参数配置对话框 -->
    <el-dialog :title="title" :visible.sync="open" width="1000px" @open="openCallback" append-to-body>
      <el-tabs type="border-card" v-model="active">
        <el-tab-pane label="基本配置" name="baseConfig" :disabled="tabActiveKey!=='1'">
          <base-config ref="baseConfig" @ok="handleBaseConfigOk"></base-config>
        </el-tab-pane>
        <el-tab-pane label="模型选择" name="model" :disabled="tabActiveKey!=='2'">
          <model-select ref="modelSelect" @ok="handleModelSelectOK"></model-select>
        </el-tab-pane>
        <el-tab-pane label="SEO 设置" name="SEO" :disabled="tabActiveKey!=='3'">
          <SEO ref="seo" @ok="handleSeoOK"></SEO>
        </el-tab-pane>
      </el-tabs>
      <div slot="footer" class="dialog-footer">
        <el-button @click="handleSubmit">下一步</el-button>
        <el-button @click="handleCancel">取 消</el-button>
      </div>
    </el-dialog>
  </div>
</template>

<script>
import {treeCategory, listCategory, genCategory, delById} from '@/api/category/category';
import BaseConfig from "@/views/category/category/BaseConfig";
import ModelSelect from "@/views/category/category/ModelSelect";
import SEO from "@/views/category/category/SEO";
export default {
  name: "User",
  components: {BaseConfig, ModelSelect,SEO},
  data() {
    return {
      // 遮罩层
      loading: true,
      // 选中数组
      ids: [],
      // 非单个禁用
      single: true,
      // 非多个禁用
      multiple: true,
      // 显示搜索条件
      showSearch: true,
      // 总条数
      total: 0,
      // 弹出层标题
      title: "",
      // 是否显示弹出层
      open: false,
      //默认激活tabs
      active: 'baseConfig',
      tabActiveKey: '1',
      item: {},
      // 表单参数
      form: {},
      // 树数据
      treeData: [],
      //表格数据
      tableData: [],
      defaultProps: {
        children: "children",
        label: "title"
      },
      // 查询参数
      queryParams: {
        id: '0',
      },
      // 表单校验
      rules: {},
      categoryId: null
    };
  },
  watch: {},
  created() {
    this.getTreeCategory();
    this.getList();
  },
  methods: {
    /**
     * 获取分类树
     */
    getTreeCategory() {
      treeCategory().then(response => {
        this.treeData = [response.data];
      })
    },
    /**
     * 获取列表
     */
    getList() {
      listCategory(this.queryParams).then(response => {
        this.tableData = response.rows
        this.loading = false
      })
    },

    /**
     * 树点击事件
     * @param data
     */
    treeClick(data) {
      this.queryParams.parentId = data.id
      this.single = false;
      this.getList();
      this.item = data;
    },
    //转换前台是否显示表格
    hiddenFormatter(row, column, cellValue, index) {
      return !row.hidden ? <el-tag type="info">是</el-tag> : <el-tag type="danger">否</el-tag>;
    },
    //转换模型表格
    modelFormatter(row) {
      return row.categoryModelId ? <el-tag type="info">是</el-tag> : <el-tag type="danger">否</el-tag>;
    },
    //展示是否投稿表格
    allowContributeFormatter(row) {
      return row.allowContribute ? <el-tag type="info">是</el-tag> : <el-tag type="danger">否</el-tag>;
    },
    //打开创建弹窗
    handleAddWin() {
      this.open = true;
      this.title = "创建栏目"
      this.setParent(this.item);
    },
    openCallback() {

    },
    /**
     * 设置父表单
     * @param item
     */
    setParent(item) {
      const _this = this
      setTimeout(function () {
        _this.$refs.baseConfig.setParent(item, 0)
      }, 350)
    },

    handleSubmit() {
      if (this.tabActiveKey === '1') {
        this.$refs.baseConfig.handleSubmit();
      } else if (this.tabActiveKey === '2') {
        this.$refs.modelSelect.handleSubmit();
      } else {
        this.$refs.seo.handleSubmit();
      }
    },
    handleCancel() {
      this.open = false
      this.categoryId = null
      // if (this.tabActiveKey === '1') {
        this.$refs.baseConfig.handleCancel()
      // } else if (this.tabActiveKey === '2') {
        this.$refs.baseConfig.handleCancel()
      // } else {
        this.$refs.baseConfig.handleCancel()
        this.$refs.seo.handleCancel();
      // }
      this.goToTab('baseConfig','1');
    },

    handleBaseConfigOk(id) {
      this.categoryId = id
      this.goToTab("model","2")
      const _this = this
      setTimeout(function () {
        _this.$refs.modelSelect.setCategoryId(id)
      }, 350)
      this.getList();
      this.getTreeCategory();
    },
    handleModelSelectOK(){
      this.goToTab("SEO","3");
      const _this = this
      setTimeout(function () {
        _this.$refs.seo.setCategoryId(_this.categoryId)
      }, 350)
    },
    handleSeoOK(id){
      this.goToTab('baseConfig','1');
      this.handleCancel()
    },
    goToTab(s,count) {
      this.active = s
      this.tabActiveKey=count
    },
    handleEditor(row) {
      const _this = this
      this.open = true;
      this.title = "修改栏目"
      this.setParent(this.item);
      setTimeout(function () {
        _this.$refs.baseConfig.edit(row)
      }, 350)
    },
    handleStatic(row){
      genCategory({ id: row.id }).then(response=>{
          console.log(response);
      })
    },
    handleDelete(row){
      console.log(row)
      this.$confirm('此操作将栏目删除, 是否继续?', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }).then(() => {
          delById(row.id).then(response=>{
            if (response.code===200){
              this.msgSuccess("~删除成功~")
              this.getList();
              this.getTreeCategory()
            }else {
              this.msgError(response.msg);
            }
          })
      }).catch(() => {
        this.$message({
          type: 'info',
          message: '已取消删除'
        });
      });
    }
  }
};
</script>
<style>
.el-tabs--border-card > .el-tabs__header {
  background-color: #F5F7FA;
  border-bottom: 1px solid #F5F7FA;
  margin: 0;
}
</style>
