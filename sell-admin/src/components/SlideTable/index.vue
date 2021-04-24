<template>
  <div class="slide-table" :style="{ height }">
    <div class="slide-table-head" :style="{ color: titleColor, backgroundColor: titleBackgroundColor }">
      <span
        v-for="(column, index) in sortColumns"
        :key="index"
        class="item"
        :style="{flex: column.width ? `0 0 ${column.width}` : '1'}"
      >
        <slot :name="`title${firstUpperCase(column.prop)}`">
          {{ column.label || '未知标题' }}
        </slot>
      </span>
    </div>
    <div v-show="tableData.length" ref="container" class="slide-table-body" :style="{ color: bodyColor, backgroundColor: bodyBackgroundColor }">
      <div ref="tbBody">
        <ul v-for="(item, index) in tableData" :key="index" class="row" :style="{ borderTopColor: rowBorderColor }">
          <li
            v-for="(column, inx) in sortColumns"
            :key="inx"
            class="item"
            :style="{flex: column.width ? `0 0 ${column.width}` : '1'}"
          >
            <slot :name="column.prop" v-bind="{ column, row: item, $index: index }">
              {{ item[column.prop] || '未知数据' }}
            </slot>
          </li>
        </ul>
      </div>
    </div>
    <div v-show="!tableData.length" class="no-data">
      暂无数据
    </div>
  </div>
</template>

<script>
// column支持属性 {
//   width?: 宽度，
//   sort?: 排序，
//   label：名称，
//   prop：名称对应prop
// }
// title 插槽名称 `title${firstUpperCase(prop)}`
// body 插槽名称 `${prop}`
import { firstUpperCase } from '@/utils/util';

export default {
  props: {
    data: {
      type: Array,
      default: () => ([]),
    },
    columns: {
      type: Array,
      required: true,
    },
    interval: {
      type: Number,
      default: 2,
    },
    transition: {
      type: Number,
      default: 1,
    },
    height: {
      type: String,
      default: '300px',
    },
    titleColor: {
      type: String,
      default: '#c3c91b',
    },
    titleBackgroundColor: {
      type: String,
      default: '#86b0ed',
    },
    bodyColor: {
      type: String,
      default: '#fff',
    },
    bodyBackgroundColor: {
      type: String,
      default: 'transparent',
    },
    rowBorderColor: {
      type: String,
      default: '#435174',
    },
  },
  data() {
    this.firstUpperCase = firstUpperCase;

    return {
      timer: null,
      tableData: [],
      scrollElment: null,
    };
  },
  computed: {
    sortColumns() {
      const notSort = this.columns.filter(item => item.sort === undefined);
      const hasSort = this.columns.filter(item => item.sort !== undefined);
      return hasSort.sort((a, b) => a.sort - b.sort).concat(notSort);
    },
  },
  mounted() {
    this.init();

    window.addEventListener('resize', this.refresh);
  },
  beforeDestroy() {
    this.destroyed();

    window.removeEventListener('resize', this.refresh);
  },
  methods: {
    init() {
      // 初始化数据
      this.tableData = this.data.slice();

      this.$nextTick(() => {
        if (!this.needSlide() || this.interval < this.transition) {
          return;
        }

        // 监听动画事件
        this.scrollElment = this.$refs.tbBody;
        this.scrollElment.addEventListener('transitionend', this.resetTbBody);

        // 开始轮播
        this.timer = setInterval(() => {
          this.start();
        }, this.interval * 1000);
      });
    },
    destroyed() {
      clearInterval(this.timer);
      if (this.scrollElment) {
        this.scrollElment.removeEventListener('transitionend', this.resetTbBody);
        this.scrollElment.style.marginTop = '0';
        this.scrollElment = null;
      }
    },
    refresh() {
      this.destroyed();
      this.init();
    },
    needSlide() {
      const containerHeight = this.$refs.container.offsetHeight;
      const contentHeight = this.$refs.tbBody.offsetHeight;
      return containerHeight < contentHeight;
    },
    start() {
      const offsetHeight = this.scrollElment.children[0].getBoundingClientRect().height;
      this.scrollElment.style.transition = `all ${this.transition}s`;
      this.scrollElment.style.marginTop = `${-offsetHeight}px`;
    },
    resetTbBody() {
      const head = this.tableData.shift();
      this.tableData.push(head);
      this.scrollElment.style.transition = 'none';
      this.scrollElment.style.marginTop = '0';
    },
  },
  watch: {
    data() {
      this.refresh();
    },
    columns() {
      this.refresh();
    },
  },
};
</script>
<style lang="scss" scoped>
.slide-table {
  box-sizing: border-box;
  position: relative;
  padding: 0 10px;
  font-size: 14px;
  .slide-table-head {
    display: flex;
    height: 28px;
    line-height: 28px;
    .item {
      text-align: center;
    }
    .item + .item {
      margin-left: 8px;
    }
  }
  .slide-table-body {
    height: calc(100% - 28px);
    overflow-y: hidden;
    transition: all .5s;
    .row {
      display: flex;
      align-items: center;
      padding: 7px 0;
      margin: 0;
      & + .row {
        border-top-width: 1px;
        border-top-style: dotted;
      }
      .item {
        text-align: center;
      }
      .item + .item {
        margin-left: 8px;
      }
    }
  }
  .no-data {
    position: absolute;
    top: 50%;
    left: 0;
    right: 0;
    width: 100%;
    transform: translateY(-50%);
    text-align: center;
  }
}
</style>
