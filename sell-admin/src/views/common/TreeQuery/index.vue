<template>
  <div class="wrap" :style="{width: width}">
    <el-input
      v-model="filterText"
      class="search"
      clearable
      placeholder="输入关键字进行过滤"
    />
    <div :style="{maxHeight: maxHeight}" class="treelist-panel">
      <el-tree
        ref="tree"
        class="org-tree"
        :default-expand-all="defaultExpandAll"
        :default-expanded-keys="expandedKey"
        :filter-node-method="filterNode"
        :data="treeList"
        :props="defaultProps"
        :highlight-current="highlightCurrent"
        node-key="id"
        :expand-on-click-node="false"
        @node-click="handleTreeSelect"
      />
    </div>
  </div>
</template>

<script>
import { getSubTree, flattenTree } from '@/utils/tree';
import { debounce } from '@/utils/util';

export default {
  props: {
    treeRequest: {
      type: Function,
    },
    highlightCurrent: {
      type: Boolean,
      default: true,
    },
    maxHeight: {
      type: String,
      default: 'none',
    },
    width: {
      type: String,
      default: 'auto',
    },
    defaultExpandAll: {
      type: Boolean,
      default: false,
    },
    defaultExpandedKeys: {
      type: Array,
      default: () => [],
    },
    defaultExpandeLevel: {
      type: Number,
      default: 3,
    },
  },
  data() {
    return {
      treeList: [],
      filterText: '',
      preClickNodeId: null,
      expandedKey: this.defaultExpandedKeys,
      defaultProps: {
        children: 'children',
        label: 'name',
      },
    };
  },
  async created() {
    await this.initTree();
    if (this.defaultExpandeLevel) {
      this.initExpanded();
    }
  },
  methods: {
    async initTree() {
      if (this.treeRequest) {
        const { data: treeList } = await this.treeRequest();
        this.treeList = treeList;
      }
    },
    initExpanded() {
      const keys = flattenTree(getSubTree(this.treeList, this.defaultExpandeLevel)).map(item => item.id);
      this.expandedKey = keys;
    },
    async refreshList() {
      this.clearSelectNode();
      this.initTree();
    },
    handleTreeSelect(row) {
      if (this.highlightCurrent) {
        if (row.id === this.preClickNodeId) {
          this.$refs.tree.setCurrentKey(null);
          this.preClickNodeId = null;
          this.$emit('click', {});
        } else {
          this.preClickNodeId = row.id;
          this.$emit('click', row);
        }
      } else {
        this.$emit('click', row);
      }
    },
    clearSelectNode() {
      this.preClickNodeId = null;
      if (this.$refs.tree.getCurrentKey()) {
        this.$refs.tree.setCurrentKey(null);
      }
      this.$emit('click', {});
    },
    filterNode(key, node) {
      if (!key) return true;
      return node.name.indexOf(key) !== -1;
    },
    filterTree: debounce(function(text) {
      this.$refs.tree.filter(text);
    }, 500),
  },
  components: {},
  watch: {
    filterText(val) {
      this.filterTree(val);
    },
    treeList: {
      handler(list) {
        this.$emit('updateList', list);
      },
      immediate: true,
    },
    async treeRequest() {
      await this.initTree();
      if (this.defaultExpandeLevel) {
        this.initExpanded();
      }
    },
  },
};
</script>

<style scoped lang="scss">
.wrap {
  .search {
    display: block;
    margin-bottom: 10px;
  }
  .treelist-panel {
    width: 100%;
    overflow: auto;
    .org-tree {
      min-width: 100%;
      display: inline-block;
    }
  }
}
.treelist-panel::-webkit-scrollbar {
  z-index: 11;
  width: 6px;
  height: 6px;
}
.treelist-panel::-webkit-scrollbar-track,
.treelist-panel::-webkit-scrollbar-corner {
  background: #fff;
}
.treelist-panel::-webkit-scrollbar-thumb {
  border-radius: 5px;
  width: 6px;
  height: 6px;
  background: #b4bccc;
}
.treelist-panel::-webkit-scrollbar-track-piece {
  background: #fff;
  width: 6px;
  height: 6px;
}
</style>
