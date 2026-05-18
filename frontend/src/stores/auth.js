import { defineStore } from 'pinia';
function parseRole(token) {
    try {
        const payload = token.split('.')[1];
        if (!payload)
            return 'USER';
        const json = JSON.parse(atob(payload.replace(/-/g, '+').replace(/_/g, '/')));
        return (json.role || 'USER').toString().toUpperCase();
    }
    catch {
        return 'USER';
    }
}
export const useAuthStore = defineStore('auth', {
    state: () => ({
        token: localStorage.getItem('token') || '',
        role: localStorage.getItem('role') || 'USER'
    }),
    actions: {
        setToken(token) {
            this.token = token;
            this.role = parseRole(token);
            localStorage.setItem('token', token);
            localStorage.setItem('role', this.role);
        },
        logout() {
            this.token = '';
            this.role = 'USER';
            localStorage.removeItem('token');
            localStorage.removeItem('role');
        }
    }
});
