import '../public/ueditor/ueditor.config.js'
import '../public/ueditor/ueditor.all.min.js'
import '../public/ueditor/lang/zh-cn/zh-cn.js'
//import '../public/ueditor/ueditor.parse.min.js'
import Vue from 'vue'
import App from './App.vue'



Vue.config.productionTip = false

new Vue({
  render: h => h(App),
}).$mount('#app')
