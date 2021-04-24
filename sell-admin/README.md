# vue-element-admin

> 经过简化的 vue admin 管理后台。它只包含了 Element UI & axios & iconfont & permission control & lint，这些搭建后台必要的东西。

## 开发/构建环境

- node `10.15.3`
- npm `6.4.1`

## 构建代码

```bash
npm ci

npm run build
```

构建结果在 `./dist` 目录下

## 开始开发

复制 `.env` 文件为 `.env.local` 文件

在 `.env.local` 文件中修改后台接口地址

`PROXY_BASE_URL` 为 `项目接口前缀`

`API_PROXY` 为 `接口代理地址`

`DEV_PORT` 为 `所起服务端口`

```bash
npm install

npm run dev
```

## eslint修复

```bash
npm run lint -- --fix
````

## 自动部署
修改 deploy 文件夹下的 config.js 文件，配置服务器地址，修改 deploy 文件夹下的 index.js 文件中的远端上传地址（限制只能在 /usr/local/nginx/html/ 下上传）
```bash
npm run deploy
```

## 主要参考文档

- Vue [官方 https://cn.vuejs.org/v2/guide/](https://cn.vuejs.org/v2/guide/)
- Vue Router [官方 https://router.vuejs.org/zh/](https://router.vuejs.org/zh/)
- Vuex [官方 https://vuex.vuejs.org/zh/](https://vuex.vuejs.org/zh/)
- Element UI 库 [官方 https://element.eleme.io](https://element.eleme.io)
- vue-element-admin [github https://github.com/PanJiaChen/vue-element-admin](https://github.com/PanJiaChen/vue-element-admin)
- vue-admin-template [github https://github.com/PanJiaChen/vue-admin-template](https://github.com/PanJiaChen/vue-admin-template)
