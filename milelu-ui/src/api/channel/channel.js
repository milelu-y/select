import request from '@/utils/request'

export function notifyHomePage() {
  return request({
    url: '/channelNotify/notifyHomePage',
    method: 'put',
  })
}

export function notifyWholeSite() {
  return request({
    url: '/channelNotify/notifyWholeSite',
    method: 'put',
  })
}
export function notifyCategory() {
  return request({
    url: '/channelNotify/notifyCategory',
    method: 'put',
  })
}
