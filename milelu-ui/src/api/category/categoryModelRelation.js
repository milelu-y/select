import request from '@/utils/request'

// 新增分类选择模型时关系 该模型值 tk_model  不是 分类模型
export function saveRelation(data) {
  return request({
    url: '/model/relation/saveBatch',
    method: 'post',
    data: data
  })
}

