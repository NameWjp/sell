<template>
  <el-row class="wrap">
    <el-col :span="7">
      <el-select :disabled="disabled" filterable :value="value.provinceId" style="width: 100%;" placeholder="请选择省" @change="provinceChange">
        <el-option
          v-for="item in provinceList"
          :key="item.id"
          :label="item.name"
          :value="item.id"
        />
      </el-select>
    </el-col>
    <el-col :span="7" :offset="1">
      <el-select :disabled="disabled" filterable :value="value.cityId" style="width: 100%;" placeholder="请选择市" @change="cityChange">
        <el-option
          v-for="item in cityList"
          :key="item.id"
          :label="item.name"
          :value="item.id"
        />
      </el-select>
    </el-col>
    <el-col :span="7" :offset="1">
      <el-select :disabled="disabled" filterable :value="value.areaId" style="width: 100%;" placeholder="请选择区" @change="areaChange">
        <el-option
          v-for="item in areaList"
          :key="item.id"
          :label="item.name"
          :value="item.id"
        />
      </el-select>
    </el-col>
  </el-row>
</template>

<script>
import { getRegionList } from '@/api/region';

export default {
  props: {
    disabled: {
      type: Boolean,
      default: false,
    },
    value: {
      type: Object,
      default: () => ({}),
    },
  },
  model: {
    props: 'value',
    event: 'change',
  },
  data() {
    return {
      provinceList: [],
      cityList: [],
      areaList: [],
    };
  },
  mounted() {
    this.getProvinceList();
    this.getCityList();
    this.getAreaList();
  },
  methods: {
    provinceChange(provinceId) {
      this.value.provinceId = provinceId;
      this.value.cityId = '';
      this.value.areaId = '';
      this.cityList = [];
      this.areaList = [];
      this.$emit('change', { ...this.value });
    },
    cityChange(cityId) {
      this.value.cityId = cityId;
      this.value.areaId = '';
      this.areaList = [];
      this.$emit('change', { ...this.value });
    },
    areaChange(areaId) {
      this.value.areaId = areaId;
      this.$emit('change', { ...this.value });
    },
    async getProvinceList() {
      const { list } = await getRegionList({ pageSize: 0 }, { level: 1 });
      this.provinceList = list;
    },
    async getCityList() {
      if (this.value.provinceId) {
        const { list } = await getRegionList({ pageSize: 0 }, {
          level: 2,
          parentId: this.value.provinceId,
        });
        this.cityList = list;
      }
    },
    async getAreaList() {
      if (this.value.cityId) {
        const { list } = await getRegionList({ pageSize: 0 }, {
          level: 3,
          parentId: this.value.cityId,
        });
        this.areaList = list;
      }
    },
  },
  watch: {
    'value.provinceId'() {
      this.getCityList();
    },
    'value.cityId'() {
      this.getAreaList();
    },
  },
};
</script>

<style scoped lang="scss">
.wrap {
  overflow: hidden;
}
</style>
