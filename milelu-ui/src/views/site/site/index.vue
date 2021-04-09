<template>
  <div class="app-container">
    <el-form :model="queryParams" ref="queryForm" :inline="true" v-show="showSearch" label-width="68px">

      <el-form-item label="站点名" prop="name">
        <el-input
          v-model="queryParams.name"
          placeholder="请输入站点名"
          clearable
          size="small"
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>

      <el-form-item>
        <el-button type="cyan" icon="el-icon-search" size="mini" @click="handleQuery">搜索</el-button>
        <el-button icon="el-icon-refresh" size="mini" @click="resetQuery">重置</el-button>
      </el-form-item>
    </el-form>

    <el-row :gutter="10" class="mb8">
      <el-col :span="1.5">
        <el-button
          type="primary"
          icon="el-icon-plus"
          size="mini"
          @click="handleAdd"
          v-hasPermi="['site:site:add']"
        >新增</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="success"
          icon="el-icon-edit"
          size="mini"
          :disabled="single"
          @click="handleUpdate"
          v-hasPermi="['site:site:edit']"
        >修改</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="danger"
          icon="el-icon-delete"
          size="mini"
          :disabled="multiple"
          @click="handleDelete"
          v-hasPermi="['site:site:remove']"
        >删除</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="warning"
          icon="el-icon-download"
          size="mini"
          @click="handleExport"
          v-hasPermi="['site:site:export']"
        >导出</el-button>
      </el-col>
	  <right-toolbar :showSearch.sync="showSearch" @queryTable="getList"></right-toolbar>
    </el-row>

    <el-table border v-loading="loading"  :data="siteList" @selection-change="handleSelectionChange">
      <el-table-column type="selection" width="55" align="center" />
      <el-table-column label="站点名" align="center"  width="150" prop="name" />
      <el-table-column label="站点标题" :show-overflow-tooltip="true" align="center" prop="siteTitle" />
      <el-table-column label="关键字" :show-overflow-tooltip="true" align="center" width="200" prop="siteKeywords" />
      <el-table-column label="站点描述" :show-overflow-tooltip="true" align="center" width="200" prop="siteDesc" />
      <el-table-column label="站点logo" :show-overflow-tooltip="true" width="150" align="center" prop="siteLogo" />
      <el-table-column label="启用静态化" :show-overflow-tooltip="true" width="100" :formatter="staticFormat" align="center" prop="useStatic"></el-table-column>
      <el-table-column label="站点地址" :show-overflow-tooltip="true" align="center" width="200" prop="sitePath" />
      <el-table-column label="站点备案信息"  :show-overflow-tooltip="true" width="200" align="center" prop="siteicp" />
      <el-table-column label="站点版权" :show-overflow-tooltip="true" align="center" prop="copyright" />
      <el-table-column label="动态站点地址" :show-overflow-tooltip="true"  align="center"  width="200" prop="dynamicPath" />
      <el-table-column label="禁用" :formatter="disabledFormat" align="center" prop="disabled" ></el-table-column>
      <el-table-column label="操作" align="center" width="200" class-name="small-padding fixed-width">
        <template slot-scope="scope">
          <el-button
            icon="el-icon-plus"
            size="mini"
            type="text"
            @click="handleAdd"
            v-hasPermi="['site:site:add']"
          >新增</el-button>
          <el-button
            size="mini"
            type="text"
            icon="el-icon-edit"
            @click="handleUpdate(scope.row)"
            v-hasPermi="['site:site:edit']"
          >修改</el-button>
          <el-button
            size="mini"
            type="text"
            icon="el-icon-delete"
            @click="handleDelete(scope.row)"
            v-hasPermi="['site:site:remove']"
          >删除</el-button>
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

    <!-- 添加或修改站点对话框 -->
    <el-dialog :title="title" :visible.sync="open" width="500px" append-to-body>
      <el-form ref="form" :model="form" :rules="rules" label-width="80px">
        <el-form-item label="站点名" prop="name">
          <el-input v-model="form.name" placeholder="请输入站点名" />
        </el-form-item>
        <el-form-item label="站点标题" prop="siteTitle">
          <el-input v-model="form.siteTitle" placeholder="请输入站点标题" />
        </el-form-item>
        <el-form-item label="关键字" prop="siteKeywords">
          <el-input v-model="form.siteKeywords" placeholder="请输入关键字" />
        </el-form-item>
        <el-form-item label="站点描述" prop="siteDesc">
          <el-input v-model="form.siteDesc" placeholder="请输入站点描述" />
        </el-form-item>
        <el-form-item label="站点logo" prop="siteLogo">
          <el-input v-model="form.siteLogo" placeholder="请输入站点logo" />
        </el-form-item>
        <el-form-item label="启用静态化" prop="useStatic">
          <el-input v-model="form.useStatic" placeholder="请输入启用静态化" />
        </el-form-item>
        <el-form-item label="站点地址" prop="sitePath">
          <el-input v-model="form.sitePath" placeholder="请输入站点地址" />
        </el-form-item>
        <el-form-item label="站点备案信息" prop="siteicp">
          <el-input v-model="form.siteicp" placeholder="请输入站点备案信息" />
        </el-form-item>
        <el-form-item label="站点版权" prop="copyright">
          <el-input v-model="form.copyright" placeholder="请输入站点版权" />
        </el-form-item>
        <el-form-item label="启用服务器端包含" prop="useSsi">
          <el-input v-model="form.useSsi" placeholder="请输入启用服务器端包含" />
        </el-form-item>
        <el-form-item label="动态站点地址" prop="dynamicPath">
          <el-input v-model="form.dynamicPath" placeholder="请输入动态站点地址" />
        </el-form-item>
        <el-form-item label="禁用" prop="disabled">
          <el-input v-model="form.disabled" placeholder="请输入禁用" />
        </el-form-item>
        <el-form-item label="第三方评论代码" prop="commentCode">
          <el-input  v-model="form.commentCode" placeholder="请输入第三方评论代码" />
        </el-form-item>
        <el-form-item label="第三方分享代码" prop="shareCode">
          <el-input v-model="form.shareCode" placeholder="请输入第三方分享代码" />
        </el-form-item>
        <el-form-item label="第三方统计代码" prop="statisticalCode">
          <el-input v-model="form.statisticalCode" placeholder="请输入第三方统计代码" />
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button type="primary" @click="submitForm">确 定</el-button>
        <el-button @click="cancel">取 消</el-button>
      </div>
    </el-dialog>
  </div>
</template>

<script>
import { listSite, getSite, delSite, addSite, updateSite, exportSite } from "@/api/site/site";

export default {
  name: "Site",
  components: {
  },
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
      // 站点表格数据
      siteList: [],
      // 弹出层标题
      title: "",
      // 是否显示弹出层
      open: false,
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        parentId: null,
        name: null,
        siteTitle: null,
        siteKeywords: null,
        siteDesc: null,
        siteLogo: null,
        useStatic: null,
        sitePath: null,
        siteicp: null,
        copyright: null,
        useSsi: null,
        dynamicPath: null,
        disabled: null,
        commentCode: null,
        shareCode: null,
        statisticalCode: null
      },
      // 表单参数
      form: {},
      // 表单校验
      rules: {
        name: [
          { required: true, message: "站点名不能为空", trigger: "blur" }
        ],
        useStatic: [
          { required: true, message: "启用静态化不能为空", trigger: "blur" }
        ],
        sitePath: [
          { required: true, message: "站点地址不能为空", trigger: "blur" }
        ],
        useSsi: [
          { required: true, message: "启用服务器端包含不能为空", trigger: "blur" }
        ],
        dynamicPath: [
          { required: true, message: "动态站点地址不能为空", trigger: "blur" }
        ],
        disabled: [
          { required: true, message: "禁用不能为空", trigger: "blur" }
        ],
      },
      //是否
      yesNoOptions:[]
    };
  },
  created() {
    this.getList();
    this.getDicts("number_yes_no").then(response => {
      this.yesNoOptions = response.data;
      console.log(this.yesNoOptions)
    });
  },
  methods: {
    /** 查询站点列表 */
    getList() {
      this.loading = true;
      listSite(this.queryParams).then(response => {
        this.siteList = response.rows;
        this.total = response.total;
        this.loading = false;
      });
    },
    /** 转换静态化表格字段 */
    staticFormat(row, column){
      return this.selectDictLabel(this.yesNoOptions, row.useStatic);
    },
    /** 转换静态化表格字段 */
    disabledFormat(row, column){
      return this.selectDictLabel(this.yesNoOptions, row.disabled);
    },
    // 取消按钮
    cancel() {
      this.open = false;
      this.reset();
    },
    // 表单重置
    reset() {
      this.form = {
        id: null,
        parentId: null,
        name: null,
        siteTitle: null,
        siteKeywords: null,
        siteDesc: null,
        siteLogo: null,
        useStatic: null,
        sitePath: null,
        siteicp: null,
        copyright: null,
        useSsi: null,
        dynamicPath: null,
        disabled: null,
        commentCode: null,
        shareCode: null,
        statisticalCode: null
      };
      this.resetForm("form");
    },
    /** 搜索按钮操作 */
    handleQuery() {
      this.queryParams.pageNum = 1;
      this.getList();
    },
    /** 重置按钮操作 */
    resetQuery() {
      this.resetForm("queryForm");
      this.handleQuery();
    },
    // 多选框选中数据
    handleSelectionChange(selection) {
      this.ids = selection.map(item => item.id)
      this.single = selection.length!==1
      this.multiple = !selection.length
    },
    /** 新增按钮操作 */
    handleAdd() {
      this.reset();
      this.open = true;
      this.title = "添加站点";
    },
    /** 修改按钮操作 */
    handleUpdate(row) {
      this.reset();
      const id = row.id || this.ids
      getSite(id).then(response => {
        this.form = response.data;
        this.open = true;
        this.title = "修改站点";
      });
    },
    /** 提交按钮 */
    submitForm() {
      this.$refs["form"].validate(valid => {
        if (valid) {
          if (this.form.id != null) {
            updateSite(this.form).then(response => {
              this.msgSuccess("修改成功");
              this.open = false;
              this.getList();
            });
          } else {
            addSite(this.form).then(response => {
              this.msgSuccess("新增成功");
              this.open = false;
              this.getList();
            });
          }
        }
      });
    },
    /** 删除按钮操作 */
    handleDelete(row) {
      const ids = row.id || this.ids;
      this.$confirm('是否确认删除站点编号为"' + ids + '"的数据项?', "警告", {
          confirmButtonText: "确定",
          cancelButtonText: "取消",
          type: "warning"
        }).then(function() {
          return delSite(ids);
        }).then(() => {
          this.getList();
          this.msgSuccess("删除成功");
        })
    },
    /** 导出按钮操作 */
    handleExport() {
      const queryParams = this.queryParams;
      this.$confirm('是否确认导出所有站点数据项?', "警告", {
          confirmButtonText: "确定",
          cancelButtonText: "取消",
          type: "warning"
        }).then(function() {
          return exportSite(queryParams);
        }).then(response => {
          this.download(response.msg);
        })
    }
  }
};
</script>
