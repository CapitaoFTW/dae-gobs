export default {
	// Global page headers: https://go.nuxtjs.dev/config-head
	head: {
		title: 'Gestão de ocorrências de bens segurados',
		htmlAttrs: {
			lang: 'pt'
		},
		meta: [
			{charset: 'utf-8'},
			{name: 'viewport', content: 'width=device-width, initial-scale=1'}
			//{name: 'format-detection', content: 'telephone=no'}
		],
		link: [
			{
				rel: 'icon',
				type: 'image/x-icon',
				href: '/favicon.ico'
			}
		]
	},

	// Global CSS: https://go.nuxtjs.dev/config-css
	css: [],

	// Plugins to run before rendering page: https://go.nuxtjs.dev/config-plugins
	plugins: [],

	// Auto import components: https://go.nuxtjs.dev/config-components
	components: true,

	// Modules for dev and build (recommended): https://go.nuxtjs.dev/config-modules
	buildModules: [],

	// Modules: https://go.nuxtjs.dev/config-modules
	modules: [
		// https://go.nuxtjs.dev/bootstrap
		'bootstrap-vue/nuxt',
		// https://go.nuxtjs.dev/axios
		'@nuxtjs/axios',
		'@nuxtjs/auth'
	],

	bootstrapVue: {
		icons: true
	},

	// Axios module configuration: https://go.nuxtjs.dev/config-axios
	axios: {
		credentials: true,
		proxy: true,
		// Workaround to avoid enforcing hard-coded localhost:3000: https://github.com/nuxt-community/axios-module/issues/308
		baseURL: '/'
	},

	proxy: {
		'/api/': {
			target: 'http://localhost:8080/gobs/api/',
			pathRewrite: {
				'^/api/': ''
			}
		}
	},

	// Build Configuration: https://go.nuxtjs.dev/config-build
	build: {},

	server: {
		host: '0.0.0.0'
	},

	auth: {
		redirect: {
			login: '/auth/login',
			logout: '/',
			home: '/'
		},
		watchLoggedIn: true,
		strategies: {
			client: {
				_scheme: 'local',
				endpoints: {
					login: {
						url: '/api/auth/login-client',
						method: 'post'
					},
					logout: false,
					user: {
						url: '/api/auth/self-client',
						method: 'get'
					}
				}
			},
			employee: {
				_scheme: 'local',
				endpoints: {
					login: {
						url: '/api/auth/login-employee',
						method: 'post'
					},
					logout: false,
					user: {
						url: '/api/auth/self-employee',
						method: 'get'
					}
				}
			}
		}
	},

	router: {
		middleware: [
			'auth'
		]
	}
}
