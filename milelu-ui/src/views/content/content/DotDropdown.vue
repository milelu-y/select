<template>
  <el-dropdown trigger="hover" class="custom-tree-menu" size="small">
    <i class="el-icon-more rotate"/>
    <el-dropdown-menu slot="dropdown">
      <el-dropdown-item v-for='(item,index) in events' :key="index" :divided="index >0" @click.native="clickMenu(item)">
        {{item.value}}
      </el-dropdown-item>
    </el-dropdown-menu>
  </el-dropdown>
</template>
<script>
  export default {
    props: {
      events: {
        type: Array,
        default: function () {
          return [
            {
              code: '新建同级',
              value: 'addNode'
            },
            {
              code: '编辑',
              value: 'editNode'
            },
            {
              code: '删除',
              value: 'deleteNode'
            }
          ]
        }
      },
      // 注入数据
      data: {
        type: Object
      }
    },
    methods: {
      clickMenu(item) {
        this.$emit(item.value, this.data)
      }
    }
  }
</script>
<style>
  .rotate {
    cursor: pointer;
    margin-left: 100%;
  }
</style>
