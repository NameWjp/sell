<template>
  <section ref="appMain" class="app-main">
    <div v-if="isInit" class="container">
      <transition name="fade-transform" mode="out-in">
        <!-- 由于缓存页面数据会造成数据没有及时更新的问题，不需要时可注释掉 keep-alive  -->
        <keep-alive :include="cachedViews">
          <router-view :key="key" />
        </keep-alive>
      </transition>
    </div>
  </section>
</template>

<script>
import { mapState } from 'vuex';

export default {
  name: 'AppMain',
  computed: {
    ...mapState({
      organizationTree: state => state.organization.organizationTree,
    }),
    cachedViews() {
      return this.$store.state.tagsView.cachedViews;
    },
    key() {
      return this.$route.path;
    },
    // 确保系统需要的公共参数加载完毕
    isInit() {
      return this.organizationTree.length;
    },
  },
};
</script>

<style lang="scss" scoped>
.app-main {
  /* 50= navbar  50  */
  min-height: calc(100vh - 50px);
  width: 100%;
  position: relative;
  overflow: hidden;
  .container {
    padding: 20px;
  }
}

.fixed-header + .app-main {
  padding-top: 50px;
}

.hasTagsView {
  .app-main {
    /* 84 = navbar + tags-view = 50 + 34 */
    min-height: calc(100vh - 84px);
    box-sizing: content-box;
  }
  .fixed-header + .app-main {
    padding-top: 84px;
  }
}
</style>

<style lang="scss">
// fix css style bug in open el-dialog
// .el-popup-parent--hidden {
//   .fixed-header {
//     padding-right: 17px;
//   }
// }
</style>
