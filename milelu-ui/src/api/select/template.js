import request from '@/utils/request'

// 查询选版模板列表
export function listTemplate(query) {
  return request({
    url: '/select/template/list',
    method: 'get',
    params: query
  })
}

// 查询选版模板详细
export function getTemplate(id) {
  return request({
    url: '/select/template/' + id,
    method: 'get'
  })
}

// 新增选版模板
export function addTemplate(data) {
  return request({
    url: '/select/template',
    method: 'post',
    data: data
  })
}

// 修改选版模板
export function updateTemplate(data) {
  return request({
    url: '/select/template',
    method: 'put',
    data: data
  })
}

// 删除选版模板
export function delTemplate(id) {
  return request({
    url: '/select/template/' + id,
    method: 'delete'
  })
}

// 导出选版模板
export function exportTemplate(query) {
  return request({
    url: '/select/template/export',
    method: 'get',
    params: query
  })
}

// 上传文件
export function uploadFiles(data) {
  return request({
    url: '/select/template/uploadFiles',
    method: 'post',
    data: data
  })
}
