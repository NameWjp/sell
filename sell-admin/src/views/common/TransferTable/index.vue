<template>
  <div class="transfer-table">
    <div class="left-area">
      <el-table
        ref="leftTable"
        row-key="id"
        :data="leftList"
        :max-height="maxHeight"
        style="width: 100%"
        @selection-change="handleLeftSelect"
      >
        <el-table-column v-if="!disabled" type="selection" width="55" />
        <slot />
      </el-table>
    </div>
    <div class="control">
      <el-button type="primary" icon="el-icon-arrow-left" :disabled="!rightSelected.length || disabled" @click="moveToLeft" />
      <el-button type="primary" icon="el-icon-arrow-right" :disabled="!leftSelected.length || disabled" @click="moveToRight" />
    </div>
    <div class="right-area">
      <el-table
        ref="rightTable"
        row-key="id"
        :data="rightList"
        :max-height="maxHeight"
        style="width: 100%"
        @selection-change="handleRightSelect"
      >
        <el-table-column v-if="!disabled" type="selection" width="55" />
        <slot />
      </el-table>
    </div>
  </div>
</template>

<script>
import { isEqual } from 'lodash-es';
import Sortable from 'sortablejs';

export default {
  name: 'TransferTable',
  props: {
    sortable: {
      type: Boolean,
      default: false,
    },
    disabled: {
      type: Boolean,
      default: false,
    },
    value: {
      type: Array,
      default: () => [],
    },
    listRequest: {
      type: Function,
    },
    maxHeight: [Number, String],
  },
  data() {
    return {
      originList: [],
      leftList: [],
      leftSelected: [],
      rightList: [],
      rightSelected: [],
    };
  },
  computed: {
    _ElFormItem() {
      let parent = this.$parent;
      while (parent) {
        if (parent.$options.componentName !== 'ElFormItem') {
          parent = parent.$parent;
        } else {
          return parent;
        }
      }
      return false;
    },
  },
  async mounted() {
    await this.queryList();
    this.refreshList();
    if (!this.disabled && this.sortable) {
      this.initSortable();
    }
  },
  methods: {
    initSortable() {
      const wrapEl = this.$refs.rightTable.$el;
      const tableEl = wrapEl.querySelector('.el-table__body-wrapper tbody');
      Sortable.create(tableEl, {
        animation: 150,
        ghostClass: 'blue-background-class',
        onEnd: ({ oldIndex, newIndex }) => {
          const list = this.rightList.slice();
          const [item] = list.splice(oldIndex, 1);
          list.splice(newIndex, 0, item);
          this.rightList = list;
          this.changeValue();
        },
      });
    },
    handleLeftSelect(rows) {
      this.leftSelected = rows.map(i => i.id);
    },
    handleRightSelect(rows) {
      this.rightSelected = rows.map(i => i.id);
    },
    changeValue() {
      const rightListIds = this.rightList.map(item => item.id);
      this.$emit('input', rightListIds);
      if (this._ElFormItem) {
        this._ElFormItem.$emit('el.form.change', rightListIds);
      }
    },
    moveToLeft() {
      const rightList = this.rightList.filter(item => !this.rightSelected.includes(item.id));
      const addLeftItems = this.rightList.filter(item => this.rightSelected.includes(item.id));
      const leftSelectedItems = this.leftList.filter(item => this.leftSelected.includes(item.id));
      this.rightList = rightList;
      this.leftList = this.leftList.concat(addLeftItems);
      this.rightSelected = [];
      this.$nextTick(() => {
        if (leftSelectedItems.length) {
          leftSelectedItems.forEach((row) => {
            this.$refs.leftTable.toggleRowSelection(row);
          });
        }
      });
      this.changeValue();
    },
    moveToRight() {
      const leftList = this.leftList.filter(item => !this.leftSelected.includes(item.id));
      const addRightItems = this.leftList.filter(item => this.leftSelected.includes(item.id));
      const rightSelectedItems = this.rightList.filter(item => this.rightSelected.includes(item.id));
      this.leftList = leftList;
      this.rightList = this.rightList.concat(addRightItems);
      this.leftSelected = [];
      this.$nextTick(() => {
        if (rightSelectedItems.length) {
          rightSelectedItems.forEach((row) => {
            this.$refs.rightTable.toggleRowSelection(row);
          });
        }
      });
      this.changeValue();
    },
    async queryList() {
      if (this.listRequest) {
        const { data: list } = await this.listRequest();
        this.originList = list;
      } else {
        this.originList = [];
      }
    },
    refreshList() {
      if (this.value && this.value.length && this.originList && this.originList.length) {
        this.leftList = this.originList.filter((item) => !this.value.includes(item.id));
        this.rightList = this.value.map(id => this.originList.find(item => item.id === id));
      } else {
        this.leftList = this.originList;
        this.rightList = [];
      }
      this.rightSelected = [];
      this.leftSelected = [];
    },
  },
  components: {},
  watch: {
    async listRequest() {
      await this.queryList();
      this.refreshList();
    },
    value() {
      const currentIds = this.rightList.map(_ => _.id);
      if (!isEqual(currentIds, this.value)) {
        this.refreshList();
      }
    },
  },
};
</script>

<style scoped lang="scss">
.transfer-table {
  display: flex;
  .left-area, .right-area {
    flex: 1;
    overflow: hidden;
  }
  .control {
    white-space: nowrap;
    display: flex;
    padding: 0 5%;
    align-items: center;
  }
  /deep/ .blue-background-class {
    background-color: #C8EBFB;
  }
}
</style>
