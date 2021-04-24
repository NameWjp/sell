<template>
<div class="goods">
	<div class="menu-wrap" ref="menuWrap">
		<ul>
			<li v-for="(item, index) in goods" :key="index" class="menu-item" :class="{current: currentIndex === index}" @click="selectMenu(index, $event)">
				<span class="text border-1px">
					<span v-show="item.type>0" class="icon" :class="classMap[item.type]"></span>{{item.name}}
				</span>
			</li>
		</ul>
	</div>
	<div class="foods-wrap" ref="foodsWrap">
		<ul>
			<li v-for="(item, index) in goods" class="food-list food-list-hook" :key="index">
				<h1 class="title">{{item.name}}</h1>
				<ul>
					<li @click="selectFood(food, $event)" v-for="(food, index) in item.foods" :key="index" class="food-item border-1px">
						<div class="icon">
							<img width="57" height="57" :src="food.icon" >
						</div>
						<div class="content">
							<h2 class="name">{{food.name}}</h2>
							<p class="desc">{{food.description}}</p>
							<div class="extra">
								<span class="count">月售{{food.sellCount}}份</span><span>好评率{{food.rating}}</span>
							</div>
							<div class="price">
								<span class="now">￥{{food.price}}</span><span class="old" v-show="food.oldPrice">￥{{food.oldPrice}}</span>
							</div>
							<div class="cart-control-wrap">
								<cart-control :food="food" @cart-add="_drop"></cart-control>
							</div>
						</div>
					</li>
				</ul>
			</li>
		</ul>
	</div>
	<shop-cart :deliveryPrice="seller.deliveryPrice" :minPrice="seller.minPrice" :selectFoods="selectFoods" ref="shopCart"></shop-cart>
	<food :food="selectedFood" ref="food" @cart-add="_drop"></food>
</div>
</template>

<script>
//使用了老师开发的better-scroll插件，具体见github
import BScroll from 'better-scroll'
import shopCart from '../shopCart/shopCart'
import cartControl from '../cartControl/cartControl'
import food from '../food/food'

export default {
	data () {
		return {
			goods: [],
			listHeight: [],
			scrollY: 0,
			selectedFood: {},
			classMap: ['decrease','discount','special','invoice','guarantee']
		}
	},
	components: {
		shopCart,
		cartControl,
		food
	},
	props: {
		seller: {
			type: Object
		}
	},
	created () {
		this.$ajax.get('/api/goods')
		.then((res) => {
			this.goods = res.data;
			//vue更新数据的时候是异步的，实际在vue中的$nextTick执行DOM的更新
			//必须在DOM更新后初始better-scroll,故要调用vue的$nextTick方法
			//vue官网有详细的例子
			this.$nextTick(() => {
				this._initScroll();
				this._goodsHeight();
			})
		})
		.catch(() => {
			console.log('errer');
		})
	},
	computed: {
		currentIndex () {
			for (let i = 0; i < this.listHeight.length; i++) {
				let height1 = this.listHeight[i];
				let height2 = this.listHeight[i + 1];
				if (!height2 || (this.scrollY >= height1 && this.scrollY < height2)) {
					return i;
				}
			}
			return 0;
		},
		selectFoods () {
			let foods = [];
			this.goods.forEach((good) => {
				good.foods.forEach((food) => {
					if (food.count) {
						foods.push(food);
					}
				});
			});
			return foods;
		}
	},
	methods: {
		selectMenu (index, event) {
			//better-scroll 默认会阻止浏览器的原生 click 事件。当设置为 true，better-scroll 会派发一个 click 事件（所以在PC端会触发两次事件），我们会给派发的 event 参数加一个私有属性 _constructed，值为 true（这里过滤掉PC端派发的事件）
			if(!event._constructed) {
				return;
			}
			//better-scroll插件存在1px偏差的问题，这里减一解决这个问题
			let scrollY = -this.listHeight[index] - 1;
			this.foodsScroll.scrollTo(0, scrollY, 300);
		},
		selectFood (food, event) {
			if(!event._constructed) {
				return;
			}
			this.selectedFood = food;
			this.$refs.food.show();
		},
		//定义滑动插件的方法
		//ref为DOM引用的钩子，refs保存着引用（具体查看官网）
		_initScroll () {
			this.meunScroll = new BScroll(this.$refs.menuWrap, {
				click: true
			});

			this.foodsScroll = new BScroll(this.$refs.foodsWrap, {
				probeType: 3,
				click: true
			});

			this.foodsScroll.on('scroll', (pos) => {
				this.scrollY = Math.abs(Math.round(pos.y))
			});
		},
		_goodsHeight () {
			let foodList = this.$refs.foodsWrap.getElementsByClassName('food-list-hook');
			let height = 0;
			this.listHeight.push(height);
			for (let i=0; i<foodList.length; i++) {
				let item = foodList[i];
				height += item.clientHeight;
				this.listHeight.push(height);
			}
		},
		_drop (target) {
			//体验优化，异步执行下落动画
			this.$nextTick(() => {
				this.$refs.shopCart.drop(target);
			})
		}
	}
}
</script>

<style lang="stylus" rel="stylesheet/stylus">
@import '../../common/stylus/mixin'

.goods
	display: flex
	position: absolute
	top: 174px
	bottom: 46px
	width: 100%
	overflow: hidden
	.menu-wrap
		flex: 0 0 80px
		width: 80px
		background: #f3f5f7
		.menu-item
			display: table
			width: 56px
			padding: 0 12px
			height: 54px
			line-height: 14px
			&.current
				position: relative
				z-index: 10
				margin-top: -1px
				background-color: #fff
				.text
					font-weight: 700
					border-none()
			.text
				display: table-cell
				vertical-align: middle
				width: 56px
				font-size: 12px
				border-1px(rgba(7, 17, 27, 0.2))
				.icon
					display:inline-block
					vertical-align: top
					width: 12px
					height: 12px
					margin-right: 2px
					background-size: 12px 12px
					background-repeat:no-repeat
					&.decrease
						bg-image('decrease_3')
					&.discount
						bg-image('discount_3')
					&.guarantee
						bg-image('guarantee_3')
					&.invoice
						bg-image('invoice_3')
					&.special
						bg-image('special_3')
	.foods-wrap
		flex: 1
		.title
			padding-left: 14px
			height: 26px
			line-height: 26px
			border-left: 2px solid #d9dde1
			font-size: 12px
			color: rgb(147, 153, 159)
			background-color: #f3f5f7
		.food-item
			display: flex
			margin: 18px
			padding-bottom: 18px
			border-1px(rgba(7, 17, 27, 0.1))
			&:last-child
				border-none()
				margin-bottom: 0
			.icon
				flex: 0 0 57px
				margin-right: 10px
			.content
				flex: 1
				.name
					margin: 2px 0 8px 0
					height: 14px
					line-height: 14px
					font-size: 14px
					color: rgb(7, 17, 27)
				.desc, .extra
					line-height: 10px
					font-size: 10px
					color: rgb(147, 153, 159)
				.desc
					margin-bottom: 8px
					line-height: 12px
				.extra
					.count
						margin-right: 12px
				.price
					font-weight: 700
					line-height: 24px
					.now
						margin-right: 8px
						font-size: 14px
						color: rgb(240, 20, 20)
					.old
						text-decoration: line-through
						font-size: 10px
						color: rgb(147, 153, 159)
				.cart-control-wrap
					position: absolute
					right: 0
					bottom: 0
</style>
