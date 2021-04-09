<template>
  <div>
    <el-dialog title="置顶" :visible.sync="topOpen">
      <el-form ref="elForm" :model="record" size="medium" label-width="90px"
               label-position="left">
        <el-row :gutter="15">
          <el-form-item label="置顶级别" prop="sort">
            <el-input-number v-model="record.sort" placeholder="置顶级别" controls-position=right>
            </el-input-number>
          </el-form-item>
          <el-col :span="6">
            <el-form-item label="置顶" prop="istop">
              <el-switch v-model="record.istop"></el-switch>
            </el-form-item>
          </el-col>
          <el-col :span="6">
            <el-form-item label="推荐" prop="isrecomd">
              <el-switch v-model="record.isrecomd"></el-switch>
            </el-form-item>
          </el-col>
          <el-col :span="6">
            <el-form-item label="头条" prop="isheadline">
              <el-switch v-model="record.isheadline"></el-switch>
            </el-form-item>
          </el-col>
        </el-row>
      </el-form>
      <span slot="footer" class="dialog-footer">
    <el-button @click="topOpen=false">取 消</el-button>
    <el-button @click="submitForm">确 定</el-button>
      </span>
    </el-dialog>
  </div>
</template>

<script>
import {topIt} from '@/api/content/content'
export default {
  components: {},
  props: [],
  data() {
    return {
      topOpen: false,
      record: {
        istop: false,
        id: null,
        isrecomd: false,
        isheadline: false,
        sort: 1,
        topTags: []
      }
    }
  },
  methods: {
    top(row) {
      this.topOpen = true
      this.id = row.id
      this.record = {
        istop: row.istop,
        id: row.id,
        isrecomd: row.isrecomd,
        isheadline: row.isheadline,
        sort: row.sort
      }
    },
    submitForm() {
      console.log(this.record)
      topIt(this.record).then(response => {
        if (response.code === 200) {
          this.$emit('ok')
          this.resetForm()
          this.topOpen=false
          this.$message.success("~操作成功~")
        } else {
          this.$message.error(response.msg)
        }
      })
    },
    resetForm() {
      this.$refs['elForm'].resetFields()
    },
  }
}

</script>


<style scoped>

</style>
