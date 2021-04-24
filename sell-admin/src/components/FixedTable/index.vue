<template>
  <div class="fixed-table" :style="{ height }">
    <div class="fixed-table-head">
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
    <div v-show="data.length" ref="container" class="fixed-table-body">
      <div ref="tbBody">
        <ul v-for="(item, index) in data" :key="index" class="row">
          <li
            v-for="(column, inx) in sortColumns"
            :key="inx"
            class="item"
            :style="{
              flex: column.width ? `0 0 ${column.width}` : '1',
              cursor: needRowClick ? 'pointer' : 'auto'
            }"
            @click="handleRowClick(index, inx, item)"
          >
            <slot :name="column.prop" v-bind="{ column, row: item, $index: index }">
              {{ item[column.prop] || '未知数据' }}
            </slot>
          </li>
        </ul>
      </div>
    </div>
    <div v-show="!data.length" class="no-data">
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
// data 插槽名称 `${prop}`
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
    height: {
      type: String,
      default: '300px',
    },
    needRowClick: {
      type: Boolean,
      default: false,
    },
  },
  mounted() {
    this.$refs.container.addEventListener('scroll', this.handleScroll);
  },
  beforeDestroy() {
    this.$refs.container.removeEventListener('scroll', this.handleScroll);
  },
  data() {
    this.firstUpperCase = firstUpperCase;
    return {};
  },
  computed: {
    sortColumns() {
      const notSort = this.columns.filter(item => item.sort === undefined);
      const hasSort = this.columns.filter(item => item.sort !== undefined);
      return hasSort.sort((a, b) => a.sort - b.sort).concat(notSort);
    },
  },
  methods: {
    handleRowClick(rowIndex, columnIndex, row) {
      if (this.needRowClick) {
        this.$emit('row-click', {
          rowIndex, columnIndex, row,
        });
      }
    },
    handleScroll() {
      const container = this.$refs.container;
      const scrollTop = container.scrollTop;
      const scrollHeight = container.scrollHeight;
      const offsetHeight = container.offsetHeight;
      if (scrollTop + offsetHeight + 10 > scrollHeight) {
        this.$emit('scrollBottom');
      }
    },
  },
};
</script>
<style lang="scss" scoped>
.fixed-table {
  box-sizing: border-box;
  position: relative;
  padding: 0 10px;
  font-size: 14px;
  .fixed-table-head {
    display: flex;
    height: 28px;
    line-height: 28px;
    color: #fff;
    background: #3F5077;
    .item {
      text-align: center;
    }
    .item + .item {
      margin-left: 8px;
    }
  }
  .fixed-table-body {
    height: calc(100% - 28px);
    overflow-y: auto;
    &::-webkit-scrollbar {
      z-index: 11;
      width: 6px;
      height: 6px;
    }
    &::-webkit-scrollbar-track,
    &::-webkit-scrollbar-corner,
    &::-webkit-scrollbar-track-piece {
      background: #fff;
      border-radius: 5px;
    }
    &::-webkit-scrollbar-thumb {
      border-radius: 5px;
      width: 6px;
      height: 6px;
      background: #a9b1c0;
    }
    .row {
      display: flex;
      align-items: center;
      padding: 7px 0;
      color: #fff;
      margin: 0;
      & + .row {
        border-top: 1px dotted #435174;
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
