<template>
  <div class="app-container">
    <el-form :model="queryParams" ref="queryForm" :inline="true" v-show="showSearch" label-width="140px">
      <el-form-item label="分类模型扩展名称:" prop="categoryModelName">
        <el-input
          v-model="queryParams.categoryModelName"
          placeholder="请输入名称"
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
          plain
          icon="el-icon-plus"
          size="mini"
          @click="handleAdd"
          v-hasPermi="['category:model:add']"
        >新增
        </el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          plain
          icon="el-icon-edit"
          size="mini"
          :disabled="single"
          @click="handleUpdate"
          v-hasPermi="['category:model:edit']"
        >修改
        </el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          plain
          icon="el-icon-delete"
          size="mini"
          :disabled="multiple"
          @click="handleDelete"
          v-hasPermi="['category:model:remove']"
        >删除
        </el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          plain
          icon="el-icon-download"
          size="mini"
          @click="handleExport"
          v-hasPermi="['category:model:export']"
        >导出
        </el-button>
      </el-col>
      <right-toolbar :showSearch.sync="showSearch" @queryTable="getList"></right-toolbar>
    </el-row>

    <el-table v-loading="loading" :data="modelList" @selection-change="handleSelectionChange">
      <el-table-column type="selection" width="55" align="center"/>
      <el-table-column label="名称" align="center" prop="categoryModelName"/>
      <el-table-column label="排序" align="center" prop="sort"/>
      <el-table-column label="操作" align="center" class-name="small-padding fixed-width">
        <template slot-scope="scope">
          <el-button
            size="mini"
            type="text"
            icon="el-icon-edit"
            @click="handleUpdate(scope.row)"
            v-hasPermi="['category:model:edit']"
          >修改
          </el-button>
          <el-button
            size="mini"
            type="text"
            icon="el-icon-delete"
            @click="handleDelete(scope.row)"
            v-hasPermi="['category:model:remove']"
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

    <!-- 添加或修改分类模型对话框 -->
    <el-dialog :title="title" :visible.sync="open" width="1000px" append-to-body>
      <el-form ref="form" :model="form" :rules="rules" label-width="80px">
        <el-col :span="12">
          <el-row :gutter="15">
            <el-col :span="24">
              <el-form-item label="扩展名称" prop="categoryModelName">
                <el-input v-model="form.categoryModelName" placeholder="请输入分类模型扩展名称"/>
              </el-form-item>
            </el-col>
          </el-row>
        </el-col>
        <el-col :span="12">
          <el-row :gutterr="15">
            <el-col :span="24">
              <el-form-item label="排序" prop="sort">
                <el-input-number v-model="form.sort" placeholder="请输入排序" controls-position=right
                />
              </el-form-item>
            </el-col>
          </el-row>
        </el-col>
      </el-form>
      <extend-field ref="extendField"></extend-field>
      <div slot="footer" class="dialog-footer">
        <el-button type="primary" @click="submitForm">确 定</el-button>
        <el-button @click="cancel">取 消</el-button>
      </div>
    </el-dialog>
  </div>
</template>

<script>
import ExtendField from '@/components/ExtendField'
import {listModel, getModel, delModel, addModel, updateModel, exportModel} from "@/api/category/categoryModel";

export default {
  name: "Model",
  components: {ExtendField},
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
      // 分类模型表格数据
      modelList: [],
      // 弹出层标题
      title: "",
      // 是否显示弹出层
      open: false,
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        categoryModelName: null,
      },
      // 表单参数
      form: {
        sort: 1
      },
      // 表单校验
      rules: {
        categoryModelName: [
          {required: true, message: "扩展名称不能为空", trigger: "blur"}
        ],
      }
    };
  },
  created() {
    this.getList();
  },
  methods: {
    /** 查询分类模型列表 */
    getList() {
      this.loading = true;
      listModel(this.queryParams).then(response => {
        this.modelList = response.rows;
        this.total = response.total;
        this.loading = false;
      });
    },
    // 取消按钮
    cancel() {
      this.open = false;
      this.$refs.extendField.dataSource = [];
      this.reset();
    },
    // 表单重置
    reset() {
      this.form = {
        id: null,
        siteId: null,
        categoryModelName: null,
        extendFieldList: null,
        sort: null,
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
      this.single = selection.length !== 1
      this.multiple = !selection.length
    },
    /** 新增按钮操作 */
    handleAdd() {
      this.reset();
      this.open = true;
      this.title = "添加分类模型";
    },
    /** 修改按钮操作 */
    handleUpdate(row) {
      this.reset();
      const id = row.id || this.ids
      getModel(id).then(response => {
        this.form = response.data;
        console.log(response)
        this.setDataSource(JSON.parse(response.data.extendFieldList))
        this.open = true;
        this.title = "修改分类模型";
      });
    },

    setDataSource (extendFieldList) {
      var that=this
      if (extendFieldList) {
        setTimeout(function () {
          that.$refs.extendField.dataSource=extendFieldList
        },300)
      } else {
        this.$refs.extendField.dataSource=[]
      }
    },
    /** 提交按钮 */
    submitForm() {
      this.$refs["form"].validate(valid => {
        if (valid) {
          const fields = this.$refs.extendField.dataSource
          console.log(fields)
          if (fields.length === 0) {
            this.$message.error('您还没有添加字段,请先创建字段后保存!')
            return
          }
          this.form.extendFieldList = JSON.stringify(fields)
          if (this.form.id != null) {
            updateModel(this.form).then(response => {
              if (response.code===200) {
                this.msgSuccess("~保存成功~");
                this.open = false;
                this.getList();
              }else {
                this.msgError(response.msg);
              }
            });
          } else {
            addModel(this.form).then(response => {
              if (response.code===200){
                this.msgSuccess("~保存成功~");
                this.open = false;
                this.getList();
                this.cancel();
              }else {
                this.msgError(response.msg);
              }
            });
          }
        }
      });
    },
    /** 删除按钮操作 */
    handleDelete(row) {
      const ids = row.id || this.ids;
      this.$confirm('是否确认删除分类模型编号为"' + ids + '"的数据项?', "警告", {
        confirmButtonText: "确定",
        cancelButtonText: "取消",
        type: "warning"
      }).then(function () {
        return delModel(ids);
      }).then(() => {
        this.getList();
        this.msgSuccess("删除成功");
      })
    },
    /** 导出按钮操作 */
    handleExport() {
      const queryParams = this.queryParams;
      this.$confirm('是否确认导出所有分类模型数据项?', "警告", {
        confirmButtonText: "确定",
        cancelButtonText: "取消",
        type: "warning"
      }).then(function () {
        return exportModel(queryParams);
      }).then(response => {
        this.download(response.msg);
      })
    }
  }
};
</script>
