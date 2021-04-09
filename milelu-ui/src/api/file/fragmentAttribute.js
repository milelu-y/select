import request from '@/utils/request'

// 查询页面片段数据列表
export function listAttribute(query) {
  return request({
    url: '/fragment/attribute/list',
    method: 'get',
    params: query
  })
}
export function listAttributeTable(query) {
  return request({
    url: '/fragment/attribute/listTable',
    method: 'get',
    params: query
  })
}

// 查询页面片段数据详细
export function getAttribute(id) {
  return request({
    url: '/fragment/attribute/' + id,
    method: 'get'
  })
}

// 查询页面片段数据详细
export function getDesignAttrById(id) {
  return request({
    url: '/fragment/attribute/getDesignAttr/' + id,
    method: 'get'
  })
}

// 新增页面片段数据
export function addAttribute(data) {
  return request({
    url: '/fragment/attribute',
    method: 'post',
    data: data
  })
}

// 修改页面片段数据
export function updateAttribute(data) {
  return request({
    url: '/fragment/attribute',
    method: 'put',
    data: data
  })
}

// 删除页面片段数据
export function delAttribute(id) {
  return request({
    url: '/fragment/attribute/' + id,
    method: 'delete'
  })
}

// 导出页面片段数据
export function exportAttribute(query) {
  return request({
    url: '/fragment/attribute/export',
    method: 'get',
    params: query
  })
}
