import Vue from 'vue'
import VueRouter from 'vue-router'
import store from "@/store";
import Message from '@/views/front/Message.vue'
Vue.use(VueRouter)

const routes = [
    {
        path: '/',
        redirect: '/front/home'
    },

    {
        path: '/login',
        name: 'Login',
        component: () => import('../views/Login.vue')
    },
    {
        path: '/register',
        name: 'Register',
        component: () => import('../views/Register.vue')
    },
    {
        path: '/404',
        name: '404',
        component: () => import('../views/404.vue')
    },
    {
        path: '/front',
        name: 'Front',
        component: () => import('../views/front/Front'),
        children: [
            {
                path: 'home',
                name: 'FrontHome',
                component: () => import('../views/front/Home.vue')
            },
            {
                path: 'password',
                name: 'Password',
                component: () => import('../views/front/Password.vue')
            },
            {
                path: 'person',
                name: 'Person',
                component: () => import('../views/front/Person.vue')
            },
            {
                path: 'myCollect',
                name: 'MyCollect',
                component: () => import('../views/front/MyCollect.vue')
            },
            {
                path: 'blog',
                name: 'Blog',
                component: () => import('../views/front/Blog.vue')
            },
            {
                path: 'blogDetail',
                name: 'BlogDetail',
                component: () => import('../views/front/BlogDetail.vue')
            },
            {
                path: 'addBlog',
                name: 'AddBlog',
                component: () => import('../views/front/AddBlog.vue')
            },
            {
                path: 'article',
                name: 'Article',
                component: () => import('../views/front/Article.vue')
            },
            {
                path: 'articleDetail',
                name: 'ArticleDetail',
                component: () => import('../views/front/ArticleDetail.vue')
            },
            {
                path: 'video',
                name: 'Video',
                component: () => import('../views/front/Video.vue')
            },
            {
                path: 'videoDetail',
                name: 'VideoDetail',
                component: () => import('../views/front/VideoDetail.vue')
            },
            {
                path: 'myVideoCollect',
                name: 'MyVideoCollect',
                component: () => import('../views/front/MyVideoCollect.vue')
            },
            {
                path: 'type',
                name: 'Type',
                component: () => import('../views/front/Type.vue')
            },
            {
                path: 'questions',
                name: 'Questions',
                component: () => import('../views/front/Questions.vue')
            },
            {
                path: 'questionsDetail',
                name: 'QuestionsDetail',
                component: () => import('../views/front/QuestionsDetail.vue')
            },
            {
                path: '/reset-password',
                name: 'ResetPassword',
                component: () => import('@/views/ResetPassword.vue')
            },
            {
                path: '/message',
                name: 'Message',
                component: Message
            }

        ]
    }
]

const router = new VueRouter({
    mode: 'history',
    routes
})

// 重置路由
export const resetRouter = () => {
    router.matcher = new VueRouter({
        mode: 'history',
        routes
    })
}

// 注意：刷新页面会导致页面路由重置
export const setRoutes = () => {
    const storeMenus = localStorage.getItem("menus");
    if (storeMenus) {
        // 拼装动态路由
        const manageRoute = {
            path: '/', name: 'Manage', component: () => import('../views/Manage.vue'), redirect: "/home", children: [
                {path: 'person', name: '个人信息', component: () => import('../views/Person.vue')},
                {path: 'password', name: '修改密码', component: () => import('../views/Password.vue')},
            ]
        }
        const menus = JSON.parse(storeMenus)
        menus.forEach(item => {
            if (item.path) {  // 当且仅当path不为空的时候才去设置路由
                let itemMenu = {
                    path: item.path.replace("/", ""),
                    name: item.name,
                    component: () => import('../views/' + item.pagePath + '.vue')
                }
                manageRoute.children.push(itemMenu)
            } else if (item.children.length) {
                item.children.forEach(item => {
                    if (item.path) {
                        let itemMenu = {
                            path: item.path.replace("/", ""),
                            name: item.name,
                            component: () => import('../views/' + item.pagePath + '.vue')
                        }
                        manageRoute.children.push(itemMenu)
                    }
                })
            }
        })

        // 获取当前的路由对象名称数组
        const currentRouteNames = router.getRoutes().map(v => v.name)
        if (!currentRouteNames.includes('Manage')) {
            // 动态添加到现在的路由对象中去
            router.addRoute(manageRoute)
        }
    }
}

// 每次刷新页面都要重新设置路由，否则路由就会被重置
setRoutes()

router.beforeEach((to, from, next) => {
    localStorage.setItem("currentPathName", to.name)  // 设置当前的路由名称
    store.commit("setPath")
    if (!to.matched.length) {
        const menus = localStorage.getItem("menus")
        if (!menus) {
            next("/login")
        } else {
            next("/404")
        }
    } else {
        next()
    }
})


export default router
