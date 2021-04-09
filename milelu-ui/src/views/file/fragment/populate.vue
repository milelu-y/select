<template>
  <div class="source">
    <el-form :form="form">
      <el-col :xl="{span: 11}" :lg="{span: 8}" :md="{span: 12}" v-show="false">
        <el-form-item label="业务ID" prop="id">
          <el-input disabled v-model="form.id"/>
        </el-form-item>
      </el-col>

      <el-col :xl="{span: 11}" :lg="{span: 8}" :md="{span: 12}" v-show="false">
        <el-form-item label="模板ID" prop="fragmentModelId">
          <el-input disabled v-model="form.fragmentModelId"/>
        </el-form-item>
      </el-col>
      <el-collapse @change="changeCollapse" accordion>
        <el-collapse-item title="数据列表" name="1">
          <el-divider/>
          <el-table
            v-loading="loading"
            :data="tableData"
            style="width: 100%">
            <el-table-column
              prop="title"
              label="标题">
            </el-table-column>
            <el-table-column
              prop="date"
              label="创建日期">
            </el-table-column>
            <el-table-column>
              <template slot-scope="scope">
                <el-button
                  size="mini"
                  type="text"
                  icon="el-icon-edit"
                  @click="handleEdit(scope)"
                >编辑
                </el-button>
                <el-button
                  size="mini"
                  type="text"
                  icon="el-icon-delete"
                  @click="handleDelete(scope)"
                >删除
                </el-button>
              </template>
            </el-table-column>
          </el-table>
          <pagination
            v-show="total>0"
            :total="total"
            :page.sync="queryParam.pageNum"
            :limit.sync="queryParam.pageSize"
            @pagination="loadTableData"
          />
        </el-collapse-item>
        <el-collapse-item title="填充字段" name="2">
          <el-divider/>
          <dynamic-forms ref="dynamicForms" :form="form" @submit="submit"></dynamic-forms>
          <div class="dialog-footer" style="padding-top: 20px">
            <el-button plain size="mini" @click="handleSubmit">保存</el-button>
            <el-button plain size="mini">取消</el-button>
          </div>
        </el-collapse-item>
      </el-collapse>
    </el-form>
    <model-dynamic-form ref="modalDynamicForm" title="数据维护" @close="closeModalFrom"
                        @submit="submitModalFrom"></model-dynamic-form>
  </div>
</template>

<script>
import DynamicForms from '@/components/DynamicForms'
import ModelDynamicForm from '@/components/DynamicForms/ModelDynamicForm'

import {getFragmentForm} from '@/api/file/fragment'
import {addAttribute, getDesignAttrById, listAttribute} from "@/api/file/fragmentAttribute";
import {updateAttribute, delAttribute} from "../../../api/file/fragmentAttribute";
import {delFragmentModel} from "../../../api/file/fragment";

export default {
  name: "populate",
  components: {DynamicForms, ModelDynamicForm},
  data() {
    return {
      form: {},
      loading: true,
      activeName: '1',
      tableData: [],
      // 总条数
      total: 0,
      fileItem: null,
      fields: [],
      queryParam: {
        pageNum: 1,
        pageSize: 10,
        dto: {
          fragmentModelId: null
        }
      },
    }
  },
  methods: {
    init(fileItem) {
      this.fileItem = fileItem
      this.queryParam.dto.fragmentModelId = fileItem.fileInfo.fileName
      this.loadFragmentForm(fileItem.fileInfo.fileName)
    },
    loadFragmentForm(fileName) {
      getFragmentForm({fileName: fileName}).then(response => {
        if (response.code === 200) {
          this.fields = response.data.models;
        }
      })
    },
    changeCollapse(key) {
      const that = this
      if (key === '1') {
        //加载表格
        this.loadTableData();
      } else {
        setTimeout(function () {
          that.$refs.dynamicForms.setFields(that.fields)
        }, 500)
      }
    },
    /**
     * 加载表格数据
     */
    loadTableData() {
      listAttribute(Object.assign(this.queryParam.dto)).then(response => {
        this.tableData = response.rows;
        this.total = response.total;
        this.loading = false;
      })
    },
    /**
     * 编辑表格
     * @param values
     */
    handleEdit(record) {
      console.log(record)
      const id = record.row.id;
      getDesignAttrById(id).then(response => {
        const models = response.models
        //处理封面
        models.forEach(function (row, index) {
          if (row.fieldCode === 'cover') {
            row.defaultValue = JSON.parse(row.defaultValue);
          }
        })
        this.$refs.modalDynamicForm.initForm(models)
        this.$set(this.form, 'id', response.data.id);
        this.$set(this.form, 'fragmentModelId', response.data.fragmentModelId);
      })
    },
    /**
     * 删除列表片段
     * @param values
     */
    handleDelete(record) {
      const that = this
      this.$confirm('确定要删除吗?', '操作提醒', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }).then(() => {
        delAttribute(record.row.id).then(response => {
          if (response.code === 200) {
            that.loadTableData();
            that.msgSuccess("~操作成功~")
          } else {
            that.msgError(response.msg);
          }
        })
      }).catch(() => {
        this.$message({
          type: 'info',
          message: '已取消删除'
        });
      });
    },
    /**
     * 提交填充
     * @param values
     */
    submit(values) {
      values.params = Object.assign({}, values)
      values.fragmentModelId = this.fileItem.fileInfo.fileName
      if (values.cover) {
        var s = JSON.stringify(values.cover);
        values.cover = s;
      }
      const json = JSON.stringify(values)
      addAttribute(json).then(response => {
        if (response.code === 200) {
          this.msgSuccess("~保存成功~")
          this.initFields();
        } else {
          this.msgError(response.msg)
        }
      })
    },
    /**
     * 修改填充
     * @param data
     */
    submitModalFrom(data) {
      data.params = Object.assign({}, data)
      data.fragmentModelId = this.form.fragmentModelId
      data.id = this.form.id
      if (data.cover) {
        var cover = JSON.stringify(data.cover);
        data.cover = cover;
      }
      const jsonData = JSON.stringify(data)
      updateAttribute(jsonData).then(response => {
        if (response.code === 200) {
          //这里要刷新表格
          this.$refs.modalDynamicForm.handleCancel()
        }
      })

    },
    handleSubmit() {
      this.$refs.dynamicForms.submitFrom()
    },
    initFields() {
      this.$refs.dynamicForms.initClear()
      this.$refs.dynamicForms.clearFields()
    },
    closeModalFrom() {
      console.log("123")
      this.form.id = null
      this.form.fragmentModelId = null
      this.$refs.dynamicForms.initClear()
    }
  }
}
</script>

<style scoped>
.source {
  padding: 24px;
}
</style>
