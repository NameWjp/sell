<template>
  <div>
    <el-popover
      ref="popover"
      v-model="showPopover"
      placement="bottom-start"
      trigger="click"
      :disabled="disabled || readonly"
    >
      <organ-tree-query
        :highlight-current="false"
        :width="`${popoverWidth}px`"
        :max-height="maxHeight"
        @click="handleClickTree"
        @updateList="handleUpdateList"
      />
      <el-input
        ref="input"
        slot="reference"
        :disabled="disabled"
        :value="treeNode.name"
        :readonly="true"
        :placeholder="placeholder"
        @click.native="handleInputClick"
        @mouseenter.native="isHover = true"
        @mouseleave.native="isHover = false"
      >
        <span
          slot="suffix"
          :class="['el-input__icon', iconClass ]"
          @click="handleIconClick"
        />
      </el-input>
    </el-popover>
  </div>
</template>

<script>
import OrganTreeQuery from '@/views/common/OrganTreeQuery';
import { getTreeNode } from '@/utils/tree';

export default {
  props: {
    maxHeight: {
      type: String,
      default: '400px',
    },
    disabled: {
      type: Boolean,
      default: false,
    },
    placeholder: {
      type: String,
    },
    readonly: {
      type: Boolean,
      default: false,
    },
    value: [Number, String],
  },
  data() {
    return {
      showPopover: false,
      isHover: false,
      popoverWidth: 200,
      treeList: [],
    };
  },
  computed: {
    iconClass() {
      if (this.showPopover) {
        return 'el-icon-arrow-up';
      }
      if (this.isHover && this.treeNode.name && !this.disabled && !this.readonly) {
        return 'el-icon-circle-close';
      }
      return 'el-icon-arrow-down';
    },
    treeNode() {
      const treeNodeByInt = getTreeNode(this.treeList, this.value, 'id', 'children');
      // 浏览器刷新后，query中参数会被转成字符，兼容处理
      const treeNodeByStr = getTreeNode(this.treeList, Number(this.value), 'id', 'children');
      return treeNodeByInt || treeNodeByStr || {};
    },
  },
  methods: {
    getCurrentNode() {
      return this.treeNode;
    },
    handleUpdateList(list) {
      this.treeList = list;
    },
    handleIconClick(e) {
      if (!this.showPopover && this.treeNode.name) {
        e.stopPropagation();
        this.$emit('input', null);
      }
    },
    handleInputClick() {
      const state = !this.showPopover;
      if (state) {
        this.popoverWidth = this.$refs.input.$el.offsetWidth - 24;
      }
    },
    handleClickTree({ id, name }) {
      this.showPopover = false;
      this.$emit('input', id);
    },
  },
  components: {
    OrganTreeQuery,
  },
};
</script>

<style scoped lang="scss">
/deep/ .el-input__inner, .el-input__icon{
  cursor: pointer;
}
/deep/ .el-input__validateIcon {
  display: none;
}
</style>
