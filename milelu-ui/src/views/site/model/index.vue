<template>
  <div class="app-container">
    <el-form :model="queryParams" ref="queryForm" :inline="true" v-show="showSearch" label-width="80px">
      <el-form-item label="模型名称:" prop="name">
        <el-input
          v-model="queryParams.name"
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
        >创建模型
        </el-button>
      </el-col>
      <right-toolbar :showSearch.sync="showSearch" @queryTable="getList"></right-toolbar>
    </el-row>

    <el-table v-loading="loading" border :data="modelList" @selection-change="handleSelectionChange">
      <el-table-column type="selection" width="55" align="center"/>
      <el-table-column label="模型名称" align="center" prop="name"/>
      <el-table-column label="是否拥有图片" :formatter="pictureFormatter" align="center" prop="hasImages"/>
      <el-table-column label="是否拥有文件" :formatter="fileFormatter" align="center" prop="hasFiles"/>
      <el-table-column label="链接地址" :formatter="urlFormatter" align="center" prop="isUrl"/>
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
      <el-scrollbar style="height:500px;">
        <el-form ref="form" :model="form" :rules="rules" label-width="80px">
          <el-row>
            <el-col :span="12">
              <el-row :gutter="15">
                <el-col :span="24">
                  <el-form-item label="扩展名称" prop="name">
                    <el-input v-model="form.name" placeholder="请输入模型名称"/>
                  </el-form-item>
                </el-col>
              </el-row>
            </el-col>
            <el-col :span="12">
              <el-row :gutterr="15">
                <el-col :span="24">

                </el-col>
              </el-row>
            </el-col>
            <el-col :span="12">
              <el-row :gutterr="15">
                <el-col :span="24">
                  <el-form-item label="内容模板" prop="md5Key">
                    <tree-select v-model="form.md5Key"
                                 :options="treeData"
                                 :disable-branch-nodes="true"
                                 :show-count="true"
                                 @select="changeTemplate"
                                 placeholder="请选择模板"/>
                  </el-form-item>
                </el-col>
              </el-row>
            </el-col>
            <el-col :span="12">
              <el-row :gutterr="15">
                <el-col :span="24">
                  <el-form-item label="模板路径" prop="templatePath">
                    <el-input v-model="form.templatePath" disabled placeholder="模板路径"></el-input>
                  </el-form-item>
                </el-col>
              </el-row>
            </el-col>
          </el-row>
          <el-divider/>

          <p>
            内容系统字段
            <el-icon class="el-icon-arrow-right"></el-icon>
          </p>
          <el-row :gutter="15" v-for="(field,index) in fields" :key="index">
            <el-col :xl="{span: 4}" :lg="{span:4}" :md="{span: 4}">
              <el-form-item :label="field.fieldName+':'">
                <el-checkbox v-model="field.check" v-if="field.fieldCode!=='title'"></el-checkbox>
              </el-form-item>
            </el-col>
            <el-col :xl="{span: 6}" :lg="{span: 6}" :md="{span: 24}">
              <el-form-item>
                <p>{{ field.fieldName }}( {{ field.inputType }} )</p>
              </el-form-item>
            </el-col>
            <el-col :xl="{span: 5}" :lg="{span: 5}" :md="{span: 5}" v-if="field.check">
              <el-form-item label="必填">
                <el-switch v-model="field.isRequired"/>
              </el-form-item>
            </el-col>
            <el-col :xl="{span: 5}" :lg="{span: 8}" :md="{span: 24}" v-if="field.check">
              <el-form-item>
                <el-input v-model="field.fieldAliase"/>
              </el-form-item>
            </el-col>
          </el-row>
          <el-divider/>
          <extend-field ref="extends"></extend-field>
        </el-form>
      </el-scrollbar>
      <div slot="footer" class="dialog-footer">
        <el-button plain @click="submitForm">确 定</el-button>
        <el-button @click="cancel">取 消</el-button>
      </div>
    </el-dialog>
  </div>
</template>

<script>
import ExtendField from '@/components/ExtendField'
import {listModel, getModel, delModel, loadModel, addModel, getById, updateModel} from "@/api/model/model";
import TreeSelect from "@riophae/vue-treeselect";
import "@riophae/vue-treeselect/dist/vue-treeselect.css";
import {loadTemplate} from '@/api/category/category'
import _ from 'lodash'

export default {
  name: "Model",
  components: {ExtendField, TreeSelect},
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
      treeData: [],
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        categoryModelName: null,
      },
      // 表单参数
      form: {
        type: [],
        templatePath: ''
      },
      // 表单校验
      rules: {
        name: [
          {required: true, message: "模型名称不能为空", trigger: "blur"}
        ],
        templatePath: [
          {required: true, message: "模板路径不能为空", trigger: "blur"}
        ],
        md5Key: [{
          required: true,
          message: '请选择模板文件',
          trigger: 'blur'
        }],
      },
      fields: []
    };
  },
  created() {
    this.getList();
    this.loadTemplate();
    this.loadModelField();
  },
  methods: {
    loadTemplate() {
      loadTemplate().then(response => {
        if (response.code === 200) {
          console.log(response.data)
          let data = response.data.templateFiles
          this.filter(data)
          this.treeData = data
        }
      })
    },
    filter(templateFiles) {
      templateFiles.forEach((rows, index) => {
        rows.id = rows.key
        if (rows.children) {
          this.filter(rows.children)
        }
      })
    },

    loadModelField() {
      loadModel().then(response => {
        this.fields = response
      })
    },

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
      this.$refs.extends.dataSource = [];
      this.reset();
    },
    // 表单重置
    reset() {
      this.form = {};
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
      this.title = "创建模型";
    },
    /** 修改按钮操作 */
    handleUpdate(row) {
      this.reset();
      const id = row.id || this.ids
      getById(id).then(response => {
        this.title = "修改模型";
        console.log(response)
        this.open = true;
        this.form = (_.pick(response, 'id', 'name', 'templateId', 'templatePath', 'modelTemplateId'));
        if (this.treeData.length > 0) {
          this.form.md5Key = response.md5Key
        }
        if (response.extendFieldList) {
          var that = this
          setTimeout(function () {
            that.$refs.extends.dataSource = JSON.parse(response.extendFieldList);
          }, 200)
        }
        if (response.defaultFieldList) {
          this.fields = JSON.parse(response.defaultFieldList)
        }
      });
    },

    setDataSource(extendFieldList) {
      if (extendFieldList) {
        this.$refs.extends.column = extendFieldList
      } else {
        this.$refs.extends.column = []
      }
    },
    /** 提交按钮 */
    submitForm() {
      this.$refs["form"].validate(valid => {
        if (valid) {
          const param = this.buildData();
          if (this.form.id != null) {
            updateModel(param).then(response => {
              if (response.code === 200) {
                this.msgSuccess("~保存成功~")
                this.open = false;
                this.getList()
              } else {
                this.msgError(response.msg)
              }
            })
          } else {
            addModel(param).then(response => {
              if (response.code === 200) {
                this.open = false;
                this.msgSuccess("~保存成功~")
                this.getList();
              } else {
                this.msgError(response.msg)
              }
            })
          }
        }
      });
    },
    buildData() {
      const param = {
        id: this.form.id,
        name: this.form.name,
        templatePath: this.form.templatePath,
        templateId: this.form.templateId,
        modelTemplateId: this.form.modelTemplateId,
        isUrl: false,
        hasFiles: false,
        hasImages: false,
        defaultFieldList: JSON.stringify(this.fields),
        extendFieldList: JSON.stringify(this.$refs.extends.dataSource)
      }
      return param;
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
    changeTemplate(value) {
      this.$set(this.form,"templatePath",value.relativePath);
    },
    pictureFormatter(row) {
      return row.hasImages ? <el-tag type="info">是</el-tag> : <el-tag type="danger">否</el-tag>
    },
    fileFormatter(row) {
      return row.hasFiles ? <el-tag type="info">是</el-tag> : <el-tag type="danger">否</el-tag>
    },
    urlFormatter(row) {
      return row.isUrl ? <el-tag type="info">是</el-tag> : <el-tag type="danger">否</el-tag>
    }
  },
};
</script>
