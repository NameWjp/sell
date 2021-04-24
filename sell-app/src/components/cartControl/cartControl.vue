<template>
<div class="cart-control">
	<transition name="move">
		<div class="cart-decrease icon-remove_circle_outline" v-show="food.count > 0" @click.stop="decreaseCart"></div>
	</transition>
	<div class="cart-count" v-show="food.count > 0">{{food.count}}</div>
	<div class="cart-add icon-add_circle" @click.stop="addCart"></div>
</div>
</template>

<script>
export default {
	props: {
		food: {
			type: Object
		}
	},
	methods: {
		addCart (event) {
			if (!event._constructed) {
				return;
			}
			if (!this.food.count) {
				this.$set(this.food, 'count', 1)
			} else {
				this.food.count++;
			}
			this.$emit('cart-add', event.target)
		},
		decreaseCart (event) {
			if (!event._constructed) {
				return;
			}
			if (this.food.count) {
				this.food.count--;
			}
		}
	}
}
</script>

<style lang="stylus" rel="stylesheel/stylus">


.cart-control
	font-size: 0
	.cart-decrease
		display: inline-block
		padding: 6px
		line-height: 24px
		font-size: 24px
		color: rgb(0, 160, 220)
		&.move-enter-active, &.move-leave-active
			transition: all 0.4s linear
		&.move-enter, &.move-leave-to
			opacity: 0
			transform: translate3d(24px, 0, 0) rotate(180deg)
	.cart-count
		display: inline-block
		vertical-align: top
		width: 12px
		padding-top: 7px
		line-height: 24px
		text-align: center
		font-size: 10px
		color: rgb(147, 153, 159)
	.cart-add
		display: inline-block
		padding: 6px
		line-height: 24px
		font-size: 24px
		color: rgb(0, 160, 220)
</style>