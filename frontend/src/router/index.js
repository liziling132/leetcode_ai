import { createRouter, createWebHistory } from 'vue-router';
import { useAuthStore } from '@/stores/auth';
const routes = [
    { path: '/', redirect: '/problems' },
    { path: '/login', component: () => import('@/views/LoginView.vue') },
    { path: '/problems', component: () => import('@/views/ProblemsView.vue') },
    { path: '/problems/:id', component: () => import('@/views/ProblemDetailView.vue') },
    { path: '/submissions', component: () => import('@/views/SubmissionsView.vue') },
    { path: '/wrong-questions', component: () => import('@/views/WrongQuestionsView.vue') },
    { path: '/recommendations', component: () => import('@/views/RecommendationsView.vue') },
    { path: '/recommendation-records', component: () => import('@/views/RecommendationRecordsView.vue') },
    { path: '/favorites', component: () => import('@/views/FavoritesView.vue') },
    { path: '/stats', component: () => import('@/views/StatsView.vue') },
    { path: '/profile', component: () => import('@/views/ProfileView.vue') },
    { path: '/admin/users', component: () => import('@/views/admin/AdminUsersView.vue') },
    { path: '/admin/problems', component: () => import('@/views/admin/AdminProblemsView.vue') },
    { path: '/admin/tags', component: () => import('@/views/admin/AdminTagsView.vue') },
    { path: '/admin/monitor', component: () => import('@/views/admin/AdminMonitorView.vue') }
];
const router = createRouter({
    history: createWebHistory(),
    routes
});
router.beforeEach((to) => {
    const auth = useAuthStore();
    if (to.path !== '/login' && !auth.token) {
        return '/login';
    }
    if (to.path.startsWith('/admin') && auth.role !== 'ADMIN') {
        return '/problems';
    }
});
export default router;
