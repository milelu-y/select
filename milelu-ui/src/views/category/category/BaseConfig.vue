<template>
  <div>
    <el-row :gutter="15">
      <el-form ref="elForm" :model="formData" :rules="rules" size="medium" label-width="100px">
        <el-col :span="12">
          <el-row :gutter="15">
            <el-col :span="24">
              <el-form-item label="父级栏目" prop="parentName">
                <el-input v-model="formData.parentName" placeholder="请输入父栏目" :disabled='true' clearable
                          :style="{width: '100%'}"></el-input>
              </el-form-item>
            </el-col>
            <el-col :span="24">
              <el-form-item label="栏目名称" prop="name">
                <el-input v-model="formData.name" placeholder="请输入栏目名称" clearable :style="{width: '100%'}">
                </el-input>
              </el-form-item>
            </el-col>
            <el-col :span="24" v-if="!remoteUrlShow">
              <el-form-item label="路径规则" prop="pathRule">
                <el-select v-model="formData.pathRule" placeholder="请选择路径规则" clearable
                           :style="{width: '100%'}">
                  <el-option
                    v-for="(item,index) in pathRules"
                    :key="index"
                    :label="item.value"
                    :value="item.code">
                  </el-option>
                </el-select>
              </el-form-item>
            </el-col>
            <el-col :span="24" v-if="!remoteUrlShow">
              <el-form-item label="选择模板" prop="md5Key">
                <tree-select v-model="formData.md5Key" :options="treeData"
                             :disable-branch-nodes="true"
                             :show-count="true"
                             @select="changeTemplate"
                             placeholder="请选择模板"/>
              </el-form-item>
            </el-col>
            <el-col :span="24">
              <el-form-item label="栏目顺序" prop="sort">
                <el-input-number :style="{width: '100%'}" v-model="formData.sort" placeholder="栏目顺序" step-strictly
                                 controls-position=right></el-input-number>
              </el-form-item>
            </el-col>
          </el-row>
        </el-col>
        <el-col :span="12">
          <el-row :gutterr="15">
            <el-col :span="24">
              <el-form-item label="栏目模型" prop="categoryModelId">
                <el-select @change="changeCategoryModel" v-model="formData.categoryModelId" placeholder="请选择栏目模型"
                           clearable
                           :style="{width: '100%'}">
                  <el-option value="" label="不设置模型"></el-option>
                  <el-option v-for="(item, index) in models" :key="index" :label="item.categoryModelName"
                             :value="item.id"></el-option>
                </el-select>
              </el-form-item>
            </el-col>
            <el-col :span="24">
              <el-form-item label="栏目编码" prop="code">
                <el-input v-model="formData.code" @input="changeCode" placeholder="请输入栏目编码" clearable
                          :style="{width: '100%'}">
                </el-input>
              </el-form-item>
            </el-col>
            <el-col :span="24">
              <el-form-item label="栏目地址" prop="path" v-if="!remoteUrlShow">
                <el-input v-model="formData.path" placeholder="请输入栏目地址" readonly :disabled='true' clearable
                          :style="{width: '100%'}"></el-input>
              </el-form-item>
            </el-col>
            <el-col :xl="{ span: 12 }" :lg="{ span: 24 }" :md="{ span: 10 }" v-show="false">
              <el-form-item label="模板地址" prop="templatePath">
                <el-input v-model="formData.templatePath"/>
              </el-form-item>
            </el-col>
            <el-col :xl="{ span: 24 }" :lg="{ span: 24 }" :md="{ span: 24 }" v-show="!remoteUrlShow">
              <el-form-item label="分页条数" prop="pageSize">
                <el-input-number :style="{width:'100%'}" v-model="formData.pageSize" placeholder="分页条数" step-strictly
                                 controls-position=right></el-input-number>
              </el-form-item>
            </el-col>
            <el-col :span="24" v-show="!remoteUrlShow">
              <el-form-item label="生成页数" prop="topPages">
                <el-input-number :style="{width: '100%'}" v-model="formData.topPages" placeholder="生成页数"
                                 controls-position=right></el-input-number>
              </el-form-item>
            </el-col>
            <el-col :span="24" v-show="remoteUrlShow">
              <el-form-item label="跳转地址" prop="remoteUrl">
                <el-input v-model="formData.remoteUrl" placeholder="请输入跳转地址" clearable
                          :style="{width: '100%'}">
                  <template slot="prepend">http://</template>
                </el-input>
              </el-form-item>
            </el-col>
          </el-row>
        </el-col>
        <el-col :span="24">
          <el-row :gutter="15">
            <el-col :span="5">
              <el-form-item label="超链接" prop="onlyUrl">
                <el-switch v-model="formData.onlyUrl" @change="onlyUrlChange" active-color="#0DB11A"></el-switch>
              </el-form-item>
            </el-col>
            <el-col :span="5">
              <el-form-item label="隐藏" prop="hidden">
                <el-switch v-model="formData.hidden" active-color="#0DB11A"></el-switch>
              </el-form-item>
            </el-col>
            <el-col :span="5">
              <el-form-item label="单页" prop="singlePage">
                <el-switch v-model="formData.singlePage" active-color="#0DB11A"></el-switch>
              </el-form-item>
            </el-col>
            <el-col :span="5">
              <el-form-item label="投稿" prop="allowContribute">
                <el-switch v-model="formData.allowContribute" active-color="#0DB11A"></el-switch>
              </el-form-item>
            </el-col>
            <el-col :span="5" v-show="!remoteUrlShow">
              <el-form-item label="子内容" prop="containChild">
                <el-switch v-model="formData.containChild" active-color="#0DB11A"></el-switch>
              </el-form-item>
            </el-col>
          </el-row>
        </el-col>
        <el-col :span="24">
          <dynamic-forms v-if="dynamicVisible" ref="dynamicForms" :form="formData" :initFromClear="false"
                         @submit="submit"></dynamic-forms>
        </el-col>
      </el-form>
    </el-row>
  </div>
</template>
<script>
import TreeSelect from "@riophae/vue-treeselect";
import {loadPathRule, loadTemplate, addCategory, getById, updateCategory} from '@/api/category/category'
import {listModel} from '@/api/category/categoryModel'
import DynamicForms from '@/components/DynamicForms';
import "@riophae/vue-treeselect/dist/vue-treeselect.css";
import _ from 'lodash'

export default {
  name: 'baseConfig',
  inheritAttrs: false,
  components: {TreeSelect, DynamicForms},
  props: [],
  data() {
    return {
      dynamicVisible: false,
      remoteUrlShow: false,
      pathRules: [],
      formData: {
        id: null,
        parentName: "",
        name: undefined,
        pathRule: undefined,
        md5Key: undefined,
        sort: 0,
        categoryModelId: "",
        code: undefined,
        path: undefined,
        pageSize: 1,
        topPages: 1,
        remoteUrl: undefined,
        onlyUrl: false,
        hidden: false,
        singlePage: false,
        allowContribute: false,
        containChild: false,
      },
      rules: {
        parentName: [],
        name: [{
          required: true,
          message: '请输入栏目名称',
          trigger: 'blur'
        }],
        pathRule: [{
          required: !this.remoteUrlShow,
          message: '请选择路径规则',
          trigger: 'change'
        }],
        sort: [{
          required: true,
          message: '请输入栏目顺序',
          trigger: 'blur'
        }],
        md5Key: [{
          required: !this.remoteUrlShow,
          message: '请选择模板文件',
          trigger: 'blur'
        }],
        code: [{
          required: true,
          message: '请输入栏目编码',
          trigger: 'blur'
        }],
        path: [{
          required: !this.remoteUrlShow,
          message: '请输入栏目地址',
          trigger: 'blur'
        }],
        pageSize: [{
          required: !this.remoteUrlShow,
          message: '分页条数',
          trigger: 'blur'
        }],
        topPages: [{
          required: !this.remoteUrlShow,
          message: '生成页数',
          trigger: 'blur'
        }],
        remoteUrl: [{
          required: this.remoteUrlShow,
          message: '请输入跳转地址',
          trigger: 'blur'
        }],
      },
      treeData: [],
      models: [],
      checkModel: null,
      categoryId: null
    }
  },
  computed: {},
  watch: {
    checkModel(newValue, oldValue) {
      if (newValue) {
        const extendFieldList = newValue.extendFieldList
        if (extendFieldList) {
          this.setModelFields(JSON.parse(extendFieldList))
        }
      } else {
        this.setModelFields([])
      }
    }
  },
  created() {
    this.loadPathRule();
    this.loadTemplate();
    this.loadModels();
  },
  methods: {
    onClose() {
      this.$refs['elForm'].resetFields()
    },
    close() {
      this.$emit('update:visible', false)
    },
    /**
     * 加载路由规则
     * @param check
     */
    loadPathRule() {
      loadPathRule().then(response => {
        if (response.code === 200) {
          this.pathRules = response.data
        }
      })
    },
    /**
     * 加载模板
     * @param check
     */
    loadTemplate() {
      loadTemplate().then(response => {
        if (response.code === 200) {
          let data = response.data.templateFiles
          this.filter(data)
          this.treeData = data
        }
      })
    },
    /**加载模型**/
    loadModels() {
      listModel({}).then(response => {
        if (response.code === 200) {
          this.models = response.rows
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
    onlyUrlChange(check) {
      this.remoteUrlShow = check
      const code = this.formData.code;
      console.log("onlyUrlChange",code)
      this.changeCode(code)
    },
    changeCode(e) {
      this.formData.path = '/' + e + '/index.html';
    },
    changeTemplate(v) {
      this.formData.templatePath = v.relativePath;
    },

    changeCategoryModel(value) {
      if (value) {
        if (this.models.length > 0) {
          for (let i = 0; i < this.models.length; i++) {
            if (value === this.models[i].id) {
              this.checkModel = Object.create(this.models[i])
            }
          }
        }
      } else {
        this.checkModel = null
      }
    },
    handleSubmit() {
      const categoryModelId = this.formData.categoryModelId
      if (categoryModelId) {
        this.$refs['elForm'].validate(valid => {
          if (!valid) return
          this.$refs.dynamicForms.submitFrom()
        })
      } else {
        this.submitForm()
      }
    },
    handleCancel() {
      this.onClose()
      this.categoryId = null
      this.formData.id = null
      this.remoteUrlShow = false
      this.clearDynamicForms(true)
    },
    clearDynamicForms(clearAll) {
      if (this.$refs.hasOwnProperty('dynamicForms') && this.$refs.dynamicForms) {
        if (clearAll) {
          this.$refs.dynamicForms.initClear()
        } else {
          this.$refs.dynamicForms.resetFieldsClear(false)
        }
      }
    },
    submitForm() {
      this.$refs['elForm'].validate(valid => {
        if (!valid) return
        this.formData.params = Object.assign({}, this.formData);
        if (this.categoryId === null) {
          addCategory(this.formData).then(response => {
            if (response.code === 200) {
              const id = response.msg
              this.categoryId = id
              this.formData.id = id
              this.msgSuccess("~保存成功~");
              this.$emit('ok', id)
            }
          })
        } else if (this.categoryId !== null) {
          updateCategory(this.formData).then(response => {
            this.$emit('ok', this.categoryId)
            this.msgSuccess("~保存成功~")
          })
        }
      })
    },
    submit(data) {
      data.params = Object.assign({}, data)
      const json = JSON.stringify(data)
      if (this.categoryId === null) {
        addCategory(json).then(response => {
          if (response.code === 200) {
            const id = response.msg;
            this.categoryId = id
            this.formData.id = id
            this.msgSuccess("~保存成功~");
            this.$emit('ok', id)
          } else {
            this.msgError(response.msg);
          }
        })
      } else {
        updateCategory(json).then(response => {
          this.$emit('ok', this.categoryId)
          this.msgSuccess("~保存成功~")
        })
      }
    },
    edit(row) {
      getById(row.id).then(response => {
        this.remoteUrlShow = response.category.onlyUrl === true;
        var ob = _.pick(response.category, 'id', 'parentId', 'parentName', 'categoryModelId',
          'name', 'code', 'pathRule', 'path', 'pageSize', 'sort', 'topPages', 'remoteUrl', 'onlyUrl', 'hidden', 'containChild', 'singlePage', 'allowContribute')
        const _this = this
        setTimeout(function () {
          _this.formData = ob;
          var data = _.pick(response, 'md5Key', 'templatePath')
          _this.formData.md5Key = data.md5Key
          _this.formData.templatePath = data.templatePath

        }, 200)
        this.categoryId = response.category.id
        this.changeCategoryModel(response.category.categoryModelId, null)
        this.setField(response)
      })
    },
    setField(response) {
      const _this = this
      setTimeout(function () {
        _this.clearDynamicForms(false)
        if (response.models) {
          _this.setModelFields(response.models)
        }
      }, 200)
    },
    setParent(item, flag) {
      this.onClose();
      this.formData.parentId = item.id;
      this.formData.parentName = item.title
    },
    setModelFields(items) {
      if (items && items.length > 0) {
        this.dynamicVisible = true
        const _this = this
        setTimeout(function () {
          _this.$refs.dynamicForms.setFields(items)
        }, 200)
      } else {
        this.$refs.dynamicForms.setFields([])
        this.dynamicVisible = false
      }
    },
  }
}

</script>
<style>
</style>
