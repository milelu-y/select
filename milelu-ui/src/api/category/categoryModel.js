import request from '@/utils/request'

// 查询分类模型列表
export function listModel(query) {
  return request({
    url: '/category/model/list',
    method: 'get',
    params: query
  })
}

// 查询分类模型详细
export function getModel(id) {
  return request({
    url: '/category/model/' + id,
    method: 'get'
  })
}

// 新增分类模型
export function addModel(data) {
  return request({
    url: '/category/model',
    method: 'post',
    data: data
  })
}

// 修改分类模型
export function updateModel(data) {
  return request({
    url: '/category/model',
    method: 'put',
    data: data
  })
}

// 删除分类模型
export function delModel(id) {
  return request({
    url: '/category/model/' + id,
    method: 'delete'
  })
}

// 导出分类模型
export function exportModel(query) {
  return request({
    url: '/category/model/export',
    method: 'get',
    params: query
  })
}
