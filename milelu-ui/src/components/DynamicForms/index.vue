<template>
  <div>
    <el-row v-if="dynamicFields.length === 0">
      <p style="text-align: center;">~暂无设计字段~</p>
    </el-row>
    <el-form :model="form" ref="form" label-width="1000px">
      <el-row :gutter="12" v-if="textFields.length>0 || numberFields.length>0|| dateFields.length>0 " >
        <input-text v-for="(field, index) in textFields"  :form.sync="form" :data.sync="field" :key="field.fieldCode+index"></input-text>
        <input-number v-for="(field, index) in numberFields" :ref="field.fieldCode" :form.sync="form" :data.sync="field" :key="field.fieldCode+index" />
        <input-date v-for="(field, index) in dateFields" :ref="field.fieldCode" :form.sync="form" :data.sync="field" :key="field.fieldCode+index" />
      </el-row>

      <el-row v-if="switchFields.length>0">
        <input-switch v-for="(field, index) in switchFields" :ref="field.fieldCode" :form="form" :data="field" :key="field.fieldCode+index" />
      </el-row>

      <el-row v-if="pictureFields.length>0">
        <input-picture v-for="(field, index) in pictureFields" :ref="field.fieldCode" :form="form" :data="field" :key="field.fieldCode+index" />
      </el-row>

      <el-row v-if="textareaFields.length>0">
        <input-textarea v-for="(field, index) in textareaFields" :ref="field.fieldCode" :form="form" :data="field" :key="field.fieldCode+index" />
      </el-row>
      <el-row v-if="editorFields.length>0">
        <input-tinymce
          v-for="(field, index) in editorFields"
          :ref="field.fieldCode"
          :form="form"
          :data="field"
          :key="field.fieldCode+index" />
      </el-row>
    </el-form>
  </div>
</template>

<script>
import InputText from "@/components/DynamicForms/FormComponent/InputText";
import InputNumber from "@/components/DynamicForms/FormComponent/InputNumber";
import InputDate from "@/components/DynamicForms/FormComponent/InputDate";
import InputSwitch from "@/components/DynamicForms/FormComponent/InputSwitch";
import InputTinymce from "@/components/DynamicForms/FormComponent/InputTinymce";
import InputTextarea from '@/components/DynamicForms/FormComponent/InputTextarea';
import InputPicture from '@/components/DynamicForms/FormComponent/InputPicture';
import _ from 'lodash'
export default {
  name: "index",
  components: {InputDate, InputText,InputNumber,InputSwitch,InputTinymce,InputTextarea,InputPicture},
  props:{
    form: {
      type: Object,
      required: true
    },
    initFromClear: {
      type: Boolean,
      required: false,
      default: true
    }
  },
  data() {
    return {
      dynamicFields: [],
      textFields: [],
      textSearchFields: [],
      textareaFields: [],
      numberFields: [],
      dateFields: [],
      fileFields: [],
      attachFields: [],
      pictureFields: [],
      radioFields: [],
      checkboxFields: [],
      tagsFields: [],
      switchFields: [],
      editorFields: [],
    }
  },
  methods: {
    setFields(dynamicFields) {
      if (dynamicFields && dynamicFields.length > 0) {
        this.initClear()
        this.dynamicFields = dynamicFields
        this.handFields(dynamicFields)
      }
    },
    initClear () {
      this.textFields = []
      this.textSearchFields = []
      this.numberFields = []
      this.dateFields = []
      this.switchFields = []
      this.textareaFields = []
      this.tagsFields = []
      this.pictureFields = []
      this.radioFields = []
      this.checkboxFields = []
      this.editorFields = []
      this.fileFields = []
      this.attachFields = []
      if (this.initFromClear) {
        this.$refs.form.resetFields()
      }
    },
    resetFieldsClear (resetForm) {
      this.textFields = []
      this.textSearchFields = []
      this.numberFields = []
      this.dateFields = []
      this.switchFields = []
      this.textareaFields = []
      this.tagsFields = []
      this.pictureFields = []
      this.radioFields = []
      this.checkboxFields = []
      this.editorFields = []
      this.fileFields = []
      this.attachFields = []
      if (resetForm) {
        this.$refs.form.resetFields()
      }
    },
    handFields(dynamicFields) {
      for (let i = 0; i < dynamicFields.length; i++) {
        const field = dynamicFields[i]
        if (field.inputType === 'text') {
          this.textFields.push(field)
        } else if (field.inputType === 'input-search') {
          this.textSearchFields.push(field)
        } else if (field.inputType === 'number') {
          this.numberFields.push(field)
        } else if (field.inputType === 'date') {
          this.dateFields.push(field)
        } else if (field.inputType === 'switch') {
          this.switchFields.push(field)
        } else if (field.inputType === 'textarea') {
          this.textareaFields.push(field)
        } else if (field.inputType === 'tags') {
          this.tagsFields.push(field)
        } else if (field.inputType === 'picture') {
          this.pictureFields.push(field)
        } else if (field.inputType === 'radio') {
          this.radioFields.push(field)
        } else if (field.inputType === 'checkbox') {
          this.checkboxFields.push(field)
        } else if (field.inputType === 'editor') {
          this.editorFields.push(field)
        } else if (field.inputType === 'file') {
          this.fileFields.push(field)
        } else if (field.inputType === 'attach') {
          this.attachFields.push(field)
        }
      }
      this.setDefaultVal()
    },
    setDefaultVal () {
      const _this = this
      // const timer = setInterval(() => {
      //   const attrs = Object.keys(_this.$refs)
      //   if (attrs.length > 0) {
      //     _this.loopSetFields()
      //     clearInterval(timer)
      //   }
      // }, 100)
      setTimeout(() => {
        _this.loopSetFields()
      }, 200)
    },
    submitFrom(){
      this.$refs.form.validate((valid) => {
        if (valid) {
          var cloneDeep = _.cloneDeep(this.form);
          this.loopFields(cloneDeep);
          this.$emit('submit',cloneDeep)
        } else {
          return false;
        }
      });
    },

    loopFields(values) {
      const fieldArr = [this.tagsFields, this.pictureFields, this.editorFields, this.fileFields, this.attachFields]
      for (let i = 0; i < fieldArr.length; i++) {
         this.getFieldsValue(values, fieldArr[i])
      }
    },
    loopSetFields () {
      const fieldArr = [this.tagsFields, this.pictureFields, this.editorFields, this.fileFields, this.attachFields]
      for (let i = 0; i < fieldArr.length; i++) {
        this.setFieldsValue(fieldArr[i])
      }
    },
    setFieldsValue (fields) {
      if (fields.length > 0) {
        for (let i = 0; i < fields.length; i++) {
          const field = fields[i]
          if (this.$refs.hasOwnProperty(field.fieldCode)) {
            this.$refs[field.fieldCode][0].setFieldValue(field.defaultValue)
          }
        }
      }
    },
    getFieldsValue (values, fields) {
      if (fields.length > 0) {
        for (let i = 0; i < fields.length; i++) {
          const field = fields[i]
          const value = this.$refs[field.fieldCode][0].getFieldValue()
          values[field.fieldCode] = value
        }
      }
    },
    /**
     * 清除字段
     */
    clearFields () {
      const fieldArr = [this.tagsFields, this.pictureFields, this.editorFields, this.fileFields, this.attachFields]
      for (let i = 0; i < fieldArr.length; i++) {
        this.setFieldsClear(fieldArr[i])
      }
    },
    setFieldsClear (fields) {
      if (fields.length > 0) {
        for (let i = 0; i < fields.length; i++) {
          const field = fields[i]
          this.$refs[field.fieldCode][0].fieldClear()
        }
      }
    }
  }
}
</script>

<style scoped>

</style>
