<template>
  <div class="dashboard-container">
    <div class="dashboard-text">{{ timeFix() }}: {{ name }}</div>
  </div>
</template>

<script>
import { mapGetters } from 'vuex';
import { timeFix } from '@/utils/date';

export default {
  name: 'Dashboard',
  data() {
    this.timeFix = timeFix;
    return {};
  },
  computed: {
    ...mapGetters([
      'name',
      'roles',
    ]),
  },
};
</script>

<style lang="scss" scoped>
.dashboard {
  &-container {
    margin: 30px;
  }
  &-text {
    font-size: 30px;
    line-height: 46px;
  }
}
</style>
