import request from '@/utils/request'

// 查询选版-分类列表
export function listCategory(query) {
  return request({
    url: '/select/category/list',
    method: 'get',
    params: query
  })
}

// 查询选版-分类详细
export function getCategory(id) {
  return request({
    url: '/select/category/' + id,
    method: 'get'
  })
}

// 新增选版-分类
export function addCategory(data) {
  return request({
    url: '/select/category',
    method: 'post',
    data: data
  })
}

// 修改选版-分类
export function updateCategory(data) {
  return request({
    url: '/select/category',
    method: 'put',
    data: data
  })
}

// 删除选版-分类
export function delCategory(id) {
  return request({
    url: '/select/category/' + id,
    method: 'delete'
  })
}

// 导出选版-分类
export function exportCategory(query) {
  return request({
    url: '/select/category/export',
    method: 'get',
    params: query
  })
}