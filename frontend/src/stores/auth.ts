import { defineStore } from 'pinia'

function parseRole(token: string): string {
  try {
    const payload = token.split('.')[1]
    if (!payload) return 'USER'
    const json = JSON.parse(atob(payload.replace(/-/g, '+').replace(/_/g, '/')))
    return (json.role || 'USER').toString().toUpperCase()
  } catch {
    return 'USER'
  }
}

export const useAuthStore = defineStore('auth', {
  state: () => ({
    token: localStorage.getItem('token') || '',
    role: localStorage.getItem('role') || 'USER',
    avatarUrl: localStorage.getItem('avatarUrl') || ''
  }),
  actions: {
    setToken(token: string) {
      this.token = token
      this.role = parseRole(token)
      localStorage.setItem('token', token)
      localStorage.setItem('role', this.role)
    },
    logout() {
      this.token = ''
      this.role = 'USER'
      this.avatarUrl = ''
      localStorage.removeItem('token')
      localStorage.removeItem('role')
      localStorage.removeItem('avatarUrl')
    },
    setAvatarUrl(url: string) {
      this.avatarUrl = url || ''
      localStorage.setItem('avatarUrl', this.avatarUrl)
    }
  }
})
