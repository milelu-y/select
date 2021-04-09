import request from '@/utils/request'

export function genCategory(data) {
  return request({
    url: '/category/category/genCategory',
    method: 'put',
    data: data
  })
}


export function updateCategoryAttribute(data) {
  return request({
    url: '/category/category/updateCategoryAttribute',
    method: 'put',
    data: data
  })
}


export function getSeo(query) {
  return request({
    url: '/category/category/getSeo',
    method: 'get',
    params: query
  })
}


export function getById(id) {
  return request({
    url: '/category/category/getDetail/' + id,
    method: 'get'
  })
}

//查询分类树
export function treeCategory(){
  return request({
    url: '/category/category/tree',
    method: 'get',
  })
}
// 查询分类列表
export function listCategory(query) {
  return request({
    url: '/category/category/list',
    method: 'get',
    params: query
  })
}

// 查询分类详细
export function getCategory(id) {
  return request({
    url: '/category/category/' + id,
    method: 'get'
  })
}

// 新增分类
export function addCategory(data) {
  return request({
    url: '/category/category',
    method: 'post',
    data: data
  })
}

// 修改分类
export function updateCategory(data) {
  return request({
    url: '/category/category',
    method: 'put',
    data: data
  })
}

// 删除分类
export function delCategory(id) {
  return request({
    url: '/category/category/' + id,
    method: 'delete'
  })
}

export function delById(id) {
  return request({
    url: '/category/category/delById/' + id,
    method: 'delete'
  })
}

// 导出分类
export function exportCategory(query) {
  return request({
    url: '/category/category/export',
    method: 'get',
    params: query
  })
}

// 加载路径规则
export function loadPathRule() {
  return request({
    url: '/category/category/loadPathRule',
    method: 'get',
  })
}

// 加载模板
export function loadTemplate(){
  return request({
    url: '/category/category/loadTemplate',
    method: 'get',
  })
}

