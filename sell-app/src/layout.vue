<template>
<div class="app">
	<v-header :seller="seller"></v-header>
	<div class="tab border-1px">
		<router-link class="tab-item" :to="{path: 'goods'}" :seller="seller">商品</router-link>
		<router-link class="tab-item" :to="{path: 'ratings'}">评价</router-link>
		<router-link class="tab-item" :to="{path: 'seller'}">商家</router-link>
	</div>
	<keep-alive>
		<router-view :seller="seller"></router-view>
	</keep-alive>
</div>
</template>

<script>
import VHeader from './components/header/header'
import {urlParse} from './common/js/util'

export default {
	data () {
		return {
			seller: {
				id: (() => {
					let queryParam = urlParse();
					return queryParam.id;
				})()
			}
		}
	},
	components: {
		VHeader
	},
	created () {
		this.$ajax.get('/api/seller?id=' + this.seller.id)
		.then((res) => {
			this.seller = Object.assign({}, this.seller, res.data);
		})
		.catch(() => {
			console.log('error')
		})
	}
}
</script>

<style lang="stylus" rel="stylesheet/stylus">
@import "./common/stylus/mixin.styl"

.tab
	display: flex
	width: 100%
	height: 40px
	line-height: 40px
	border-1px(rgba(7,17,27,0.1))
	.tab-item
		flex: 1
		text-align: center
		font-size: 14px
		color: rgb(77, 85, 93)
		&.router-link-active
			color: rgb(240, 20, 20)
</style>
