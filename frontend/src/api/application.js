import request from '@/utils/request'

export function saveDraft(data) {
  return request.post('/application/save-draft', data)
}

export function submitApplication() {
  return request.post('/application/submit')
}

export function getMyApplication() {
  return request.get('/application/my')
}

export function getApplicationDetail(id) {
  return request.get(`/application/detail/${id}`)
}

export function getApplications(params) {
  return request.get('/admin/applications', { params })
}

export function getAdminApplicationDetail(id) {
  return request.get(`/admin/application/${id}`)
}

export function auditApplication(id, data) {
  return request.post(`/admin/application/${id}/audit`, data)
}

export function uploadSignature(file) {
  const formData = new FormData()
  formData.append('file', file)
  return request.post('/admin/upload/signature', formData, {
    headers: { 'Content-Type': 'multipart/form-data' }
  })
}
