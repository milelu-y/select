<template>
  <div>
    <div class="grid-content bg-purple">
      <p style="padding-left: 10px">
        扩展字段
        <el-icon class="el-icon-arrow-right"/>
      </p>
    </div>
    <div class="grid-content bg-purple" style="padding-left: 10px;padding-bottom: 5px">
      <el-button plain icon="plus" @click="handleAddExtendsField">添加字段</el-button>
      <span style="font-size:12px;color: red;">PS:内置字段(id,code,attach,sort,title,tags,copied,text,cover,data,author,editor,origin,description)不可用增加行</span>
    </div>
    <el-table
      :data="dataSource"
      style="width: 100%" border>
      <el-table-column prop="fieldCode" label="编码" width="150">
        <template slot-scope="scope">
          <el-input v-model="scope.row.fieldCode"></el-input>
        </template>
      </el-table-column>
      <el-table-column prop="inputType" label="字段类型" width="150">
        <template slot-scope="scope">
          <el-select placeholder="请选择" v-model="scope.row.inputType">
            <el-option label="输入框" value="text"/>
            <el-option label="数字" value="number"/>
            <el-option label="文件" value="file"/>
            <el-option label="图片" value="picture"/>
            <!--            <el-option label="视频" value="video"/>-->
            <el-option label="标签(暂未实现)" value="tag"/>
            <el-option label="区域框" value="textarea"/>
            <el-option label="MarkDown编辑器(暂未实现)" value="markDownEditor"/>
            <el-option label="富文本编辑器" value="editor"/>
            <el-option label="日期" value="date"/>
            <el-option label="开关" value="switch"/>
          </el-select>
        </template>
      </el-table-column>
      <el-table-column prop="fieldName" label="名称" width="150">
        <template slot-scope="scope">
          <el-input @input="handleChange(scope.row)" v-model="scope.row.fieldName"></el-input>
        </template>
      </el-table-column>
      <el-table-column
        prop="required"
        label="必填" width="65">
        <template slot-scope="scope">
          <el-switch
            v-model="scope.row.isRequired"
            active-color="#13ce66"
            inactive-color="#ff4949">
          </el-switch>
        </template>
      </el-table-column>
      <el-table-column
        prop="defaultValue"
        label="默认值" width="200">
        <template slot-scope="scope">
          <el-input v-model="scope.row.defaultValue"></el-input>
        </template>
      </el-table-column>
      <el-table-column
        prop="maxlength"
        label="最大长度" width="212">
        <template slot-scope="scope">
          <el-input-number v-model="scope.row.maxlength" :min="1" :max="100000" label="描述文字"></el-input-number>
        </template>
      </el-table-column>
      <el-table-column
        prop="description"
        label="描述" width="200">
        <template slot-scope="scope">
          <el-input
            v-model="scope.row.description"
            type="textarea"
            :autosize="{ minRows: 1, maxRows: 1}"
            placeholder="请输入内容">
          </el-input>
        </template>
      </el-table-column>
      <el-table-column
        prop="sort"
        label="排序" width="212">
        <template slot-scope="scope">
          <el-input-number v-model="scope.row.sort" :min="1" :max="100" label="描述文字"></el-input-number>
        </template>
      </el-table-column>
      <el-table-column
        prop="operating"
        label="操作">
        <template slot-scope="scope">
          <el-button
            size="mini"
            type="text"
            icon="el-icon-delete"
            @click="handleDeleteExtendsField(scope.row,scope.$index)"
          >删除
          </el-button>
        </template>
      </el-table-column>
    </el-table>
  </div>
</template>

<script>
export default {
  name: "index",
  data() {
    return {
      dataSource: []
    }
  },
  created() {
  },
  methods: {
    handleDeleteExtendsField(row, index) {
      this.dataSource.splice(index, 1);
    },
    handleAddExtendsField() {
      const that = this.dataSource
      that.push(this.buildData());
    },
    buildData() {
      return {
        fieldCode: '',
        fieldAliase: '',
        inputType: 'text',
        fieldName: '',
        required: false,
        isSearch: false,
        defaultValue: "",
        maxlength: 255,
        description: "",
        check: true,
        isExtend: true,
        sort: 1,
        id: Date.now()
      };
    },
    handleChange(e) {
      e.fieldAliase = e.fieldName
    }
  }
}
</script>

<style scoped>

</style>
