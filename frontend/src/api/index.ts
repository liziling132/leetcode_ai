import { http } from './http'

export const api = {
  register: (payload: any) => http.post('/auth/register', payload),
  login: (payload: any) => http.post('/auth/login', payload),
  adminLogin: (payload: any) => http.post('/admin/auth/login', payload),

  problems: (params: any) => http.get('/problems', { params }),
  problemDetail: (id: number) => http.get(`/problems/${id}`),

  favorite: (id: number) => http.post(`/problems/${id}/favorite`),
  unfavorite: (id: number) => http.delete(`/problems/${id}/favorite`),
  favorites: (params: any) => http.get('/users/me/favorites', { params }),

  createSubmission: (payload: any) => http.post('/submissions', payload),
  runTest: (payload: any) => http.post('/submissions/run-test', payload),
  submissions: (params: any) => http.get('/submissions', { params }),
  submissionDetail: (id: number) => http.get(`/submissions/${id}`),
  submissionCaseResults: (id: number, params: any) => http.get(`/submissions/${id}/case-results`, { params }),
  submissionVersions: (problemId: number, params: any) => http.get(`/submissions/problem/${problemId}/versions`, { params }),
  explain: (id: number) => http.get(`/submissions/${id}/ai-explain`),

  wrongQuestions: (params: any) => http.get('/wrong-questions', { params }),
  recommendations: (params: any) => http.get('/recommendations', { params }),
  recommendRecords: (params: any) => http.get('/recommendations/records', { params }),

  stats: () => http.get('/users/me/stats'),
  weakPoints: (params: any) => http.get('/users/me/weak-points', { params }),
  learningAdvice: () => http.get('/users/me/learning-advice'),
  profile: () => http.get('/users/me/profile'),
  updateProfile: (payload: any) => http.put('/users/me/profile', payload),
  updatePassword: (payload: any) => http.put('/users/me/password', payload),

  adminUsers: (params: any) => http.get('/admin/users', { params }),
  adminUpdateUserStatus: (id: number, status: number) => http.put(`/admin/users/${id}/status`, { status }),

  adminProblems: (params: any) => http.get('/admin/problems', { params }),
  adminProblemDetail: (id: number) => http.get(`/admin/problems/${id}`),
  adminCreateProblem: (payload: any) => http.post('/admin/problems', payload),
  adminUpdateProblem: (id: number, payload: any) => http.put(`/admin/problems/${id}`, payload),
  adminDeleteProblem: (id: number) => http.delete(`/admin/problems/${id}`),
  adminReplaceTestcases: (id: number, payload: any) => http.put(`/admin/problems/${id}/testcases`, payload),

  adminTags: (params: any) => http.get('/admin/tags', { params }),
  adminCreateTag: (payload: any) => http.post('/admin/tags', payload),
  adminUpdateTag: (id: number, payload: any) => http.put(`/admin/tags/${id}`, payload),
  adminDeleteTag: (id: number, force = false) => http.delete(`/admin/tags/${id}?force=${force}`),

  adminResources: (params: any) => http.get('/admin/resources', { params }),
  adminResourceDetail: (id: number) => http.get(`/admin/resources/${id}`),
  adminCreateResource: (payload: any) => http.post('/admin/resources', payload),
  adminUpdateResource: (id: number, payload: any) => http.put(`/admin/resources/${id}`, payload),
  adminDeleteResource: (id: number) => http.delete(`/admin/resources/${id}`),

  adminMonitorOverview: () => http.get('/admin/monitor/overview')
}
