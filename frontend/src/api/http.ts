import axios from 'axios'
import { useAuthStore } from '@/stores/auth'

export const http = axios.create({
  baseURL: '/api',
  timeout: 10000
})

http.interceptors.request.use((config) => {
  const auth = useAuthStore()
  if (auth.token) {
    config.headers.Authorization = `Bearer ${auth.token}`
  }
  return config
})

http.interceptors.response.use(
  (resp) => {
    const data = resp.data
    if (data?.code !== 0) {
      return Promise.reject(new Error(data?.message || 'request failed'))
    }
    return data.data
  },
  (error) => {
    const msg = error?.response?.data?.message || error?.message || 'request failed'
    return Promise.reject(new Error(msg))
  }
)
