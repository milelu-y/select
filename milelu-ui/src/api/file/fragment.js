import request from '@/utils/request'
import da from "element-ui/src/locale/lang/da";


// 查询页面片段文件模型列表
export function loadTempTree(query) {
  return request({
    url: '/fragment/model/listTree',
    method: 'get',
    params: query
  })
}


// 查询页面片段文件模型列表
export function listModel(query) {
  return request({
    url: '/fragment/model/list',
    method: 'get',
    params: query
  })
}

// 查询页面片段文件模型详细
export function getModel(id) {
  return request({
    url: '/fragment/model/' + id,
    method: 'get'
  })
}

// 新增页面片段文件模型
export function addModel(data) {
  return request({
    url: '/fragment/model',
    method: 'post',
    data: data
  })
}

// 修改页面片段文件模型
export function updateModel(data) {
  return request({
    url: '/fragment/model',
    method: 'put',
    data: data
  })
}

// 删除页面片段文件模型
export function delFragmentModel(path) {
  return request({
    url: '/fragment/model/delFragmentModel/',
    method: 'delete',
    params: path
  })
}


// 导出页面片段文件模型
export function exportModel(query) {
  return request({
    url: '/fragment/model/export',
    method: 'get',
    params: query
  })
}

//查询页面片段内容
export function getContentByPath(data){
  return request({
    url: '/fragment/model/getFragmentContent',
    method: 'get',
    // headers:{'showLoading': true},//显示加载特效
    params:data
  })
}

//设置片段内容
export function setFragmentContent(data){
  return request({
    url: '/fragment/model/setFragmentContent',
    method: 'put',
    data:data
  })
}

//获取页面片段信息
export function getFragmentInfo(data){
  return request({
    url: '/fragment/model/getFragmentInfo/'+data,
    method: 'get',
  })
}

//获取片段表单
export function getFragmentForm(data){
  return request({
    url: '/fragment/model/getFragmentForm',
    method: 'get',
    params: data
  })
}
