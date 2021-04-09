<template>
  <div>
    <model-dynamic-form ref="modalDynamicForm" @beforeClose="beforeClose" width="60%" title="内容管理" @submit="submitModalFrom"></model-dynamic-form>
  </div>
</template>

<script>
import ModelDynamicForm from "../../../components/DynamicForms/ModelDynamicForm";
import {getFormDesign, addManage, getDetail,updateManage} from '@/api/content/content'

export default {
  name: "handle",
  components: {ModelDynamicForm},
  data() {
    return {
      modelId: null,
      categoryId: null,
      rowId: null,
      status: null,
    }
  },
  methods: {
    add(model, categoryId) {
      this.modelId = model.code
      this.categoryId = categoryId
      this.rowId = null
      this.getFormDesign(model.code)
    },
    edit(record) {
      console.log(record)
      this.categoryId = record.categoryId
      this.modelId = record.modelId
      this.rowId = record.id
      this.status = record.status
      getDetail({
        id: record.id
      }).then(response => {
        if (response.code === 200) {
          var models = response.data.models
          models.forEach(function (row, index) {
            if (row.fieldCode === 'cover') {
              if (row.defaultValue){
                row.defaultValue = JSON.parse(row.defaultValue);
              }
            }
          })
          this.$refs.modalDynamicForm.initForm(models)
        }
      })
    },
    getFormDesign(id) {
      getFormDesign({modelId: id}).then(response => {
        if (response.code === 200) {
          this.$refs.modalDynamicForm.initForm(response.data)
        }
      })
    },
    submitModalFrom(data) {
      data.params = Object.assign({}, data)
      data.categoryId = this.categoryId
      data.modelId = this.modelId
      data.status = this.status
      if (data.cover) {
        var cover = JSON.stringify(data.cover);
        data.cover = cover;
      }
      if (!this.rowId) {
        const json = JSON.stringify(data)
        addManage(json).then(response => {
          this.message(response)
        })
      } else {
        data.id = this.rowId
        const json = JSON.stringify(data)
        updateManage(json).then(response => {
          this.message(response)
        })
      }
    },
    message (response) {
      if (response.code === 200) {
        this.msgSuccess("~保存成功~")
        this.$emit('ok')
        this.handleCancel()
      } else {
        this.msgError(response.msg)
      }
    },
    beforeClose () {
      this.$refs.modalDynamicForm.cleanForm()
    },
    handleCancel () {
      this.$refs.modalDynamicForm.handleCancel()
    }
  }
}
</script>

<style scoped>

</style>
