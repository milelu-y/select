import request from '@/utils/request'

export function jobPublish(param) {
  return request({
    url: '/job/jobPublish',
    method: 'put',
    data: param
  })
}

export function topIt(param) {
  return request({
    url: '/content/manage/top',
    method: 'put',
    data: param
  })
}

export function reStaticBatchGenCid(data) {
  return request({
    url: '/content/manage/reStaticBatchGenCid',
    method: 'post',
    data: data
  })
}

export function getDetail(query) {
  return request({
    url: '/content/manage/getDetail',
    method: 'get',
    params: query
  })
}


export function getFormDesign(query) {
  return request({
    url: '/content/manage/getFormDesign',
    method: 'get',
    params: query
  })
}

// 查询内容列表
export function listManage(query) {
  return request({
    url: '/content/manage/list',
    method: 'get',
    params: query
  })
}

// 查询内容列表
export function listManages(query) {
  return request({
    url: '/content/manage/lists',
    headers:{'showLoading': true},//显示加载特效
    method: 'get',
    params: query
  })
}

export function publish(param) {
  return request({
    url: '/content/manage/publish',
    method: 'put',
    data: param
  })
}

//查询分类树
export function treeCategory() {
  return request({
    url: '/content/manage/treeCategory',
    method: 'get',
  })
}

// 查询内容详细
export function getManage(id) {
  return request({
    url: '/content/manage/' + id,
    method: 'get'
  })
}

// 新增内容
export function addManage(data) {
  return request({
    url: '/content/manage',
    method: 'post',
    data: data
  })
}

// 修改内容
export function updateManage(data) {
  return request({
    url: '/content/manage',
    method: 'put',
    data: data
  })
}

// 删除内容
export function delManage(id) {
  return request({
    url: '/content/manage/' + id,
    method: 'delete'
  })
}

// 导出内容
export function exportManage(query) {
  return request({
    url: '/content/manage/export',
    method: 'get',
    params: query
  })
}
