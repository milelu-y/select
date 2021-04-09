import request from '@/utils/request'

export function generateTemplate(data) {
  return request({
    url: '/file/template/generateTemplate',
    method: 'post',
    data: data
  })
}


// 查询模板文件树
export function listTemplateTree() {
  return request({
    url: '/file/template/getTemplateTree',
    method: 'get',
  })
}

//根据路径获取模板内容
export function getTemplateContentByPath(data) {
  return request({
    url: '/file/template/getTemplateContentByPath',
    method: 'get',
    params:data
  })
}

// 修改模板内容
export function updateTemplateContent(data) {
  return request({
    url: '/file/template/',
    method: 'put',
    data: data
  })
}

//删除模板
export function deleteTemplate(path) {
  return request({
    url: '/file/template/deleteTemplate/',
    method: 'delete',
    params: path
  })
}

// 新增模板
export function addSaveTemplate(data) {
  return request({
    url: '/file/template/',
    method: 'post',
    data: data
  })
}

// 修改模板元数据
export function uploadSaveTemplate(data) {
  return request({
    url: '/file/template/uploadSaveTemplate',
    method: 'put',
    data: data
  })
}

// 新增模板
export function addSaveRootPathTemplate() {
  return request({
    url: '/file/template/rootPath',
    method: 'post',
  })
}


//根据路径获取模板内容
export function getSingleTemplateMetadataByPath(data) {
  return request({
    url: '/file/template/getSingleTemplateMetadataByPath',
    method: 'get',
    params:data
  })
}

//上传模板文件
export function templateFileUpload(file) {
  return request({
    url: '/file/template/upload',
    method: 'post',
    data: file
  })
}
