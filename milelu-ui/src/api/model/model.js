import request from '@/utils/request'

export function getById(id) {
  return request({
    url: '/model/model/getDetail/' + id,
    method: 'get'
  })
}


export function loadModel() {
  return request({
    url: '/model/model/loadModel',
    method: 'get',
  })
}


export function loadTemplate() {
  return request({
    url: '/model/model/loadTemplate',
    method: 'get',
  })
}


// 查询模型列表
export function listModel(query) {
  return request({
    url: '/model/model/list',
    method: 'get',
    params: query
  })
}
export function listModels(query) {
  return request({
    url: '/model/model/lists',
    method: 'get',
    params: query
  })
}

export function listPublishModel(query) {
  return request({
    url: '/model/model/listPublishModel',
    method: 'get',
    params: query
  })
}

// 查询模型详细
export function getModel(id) {
  return request({
    url: '/model/model/' + id,
    method: 'get'
  })
}

// 新增模型
export function addModel(data) {
  return request({
    url: '/model/model',
    method: 'post',
    data: data
  })
}

// 修改模型
export function updateModel(data) {
  return request({
    url: '/model/model',
    method: 'put',
    data: data
  })
}

// 删除模型
export function delModel(id) {
  return request({
    url: '/model/model/' + id,
    method: 'delete'
  })
}

// 导出模型
export function exportModel(query) {
  return request({
    url: '/model/model/export',
    method: 'get',
    params: query
  })
}

