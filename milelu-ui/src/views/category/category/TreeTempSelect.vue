<template>
  <div>
    <el-col :span="14">
        <el-form-item label="内容模板" :wrapperCol="{ xs: { span: 24},sm: {span: 15}}">
          <tree-select
            style="width: 100%"
            :options="treeData"
            v-model="model.md5Key"
            :disable-branch-nodes="true"
            :z-index="5000"
            :show-count="true"
            @select="changeTemplate"
            placeholder="请选择模板">
          </tree-select>
        </el-form-item>

    </el-col>

    <!--    <el-col :xl="{span: 2}" :lg="{span: 8}" :md="{span: 12}">-->
    <!--      <el-form-item label="" :labelCol="{xs: {span: 24}}" :wrapperCol="wrapperCol">-->
    <!--        <span @click="loadTemplate"><a-icon type="reload" v-if="!reloading"/></span>-->
    <!--        <span><a-icon type="loading" v-if="reloading"/></span>-->
    <!--      </el-form-item>-->
    <!--    </el-col>-->
  </div>
</template>

<script>
  import TreeSelect from "@riophae/vue-treeselect";
  import "@riophae/vue-treeselect/dist/vue-treeselect.css";
  import {loadTemplate} from "@/api/category/category";

  export default {
    name: "TreeTempSelect",
    components: {TreeSelect},
    props: {
      model: {
        type: Object,
        required: true
      }
    },
    data() {
      return {
        // define the default value
        value: null,
        // define options

        treeData: [],
      }
    },
    created() {
      this.loadTemplate()
      console.log("this.model", this.model)
    },
    methods: {
      loadTemplate() {
        loadTemplate().then(response => {
          if (response.code === 200) {
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
      changeTemplate(value) {
        this.$set(this.model, 'md5Key', value.key)
        this.$emit('select', this.model.id, value)
      }
    }
  }
</script>

<style scoped>
</style>
