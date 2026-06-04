import request from '@/utils/request'

export function login(data) {
  return request.post('/auth/login', data)
}

export function sendCode(phone) {
  return request.post('/auth/send-code', { phone })
}

export function getUserInfo() {
  return request.get('/auth/me')
}
