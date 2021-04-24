import { getDictList } from '@/api/dict';

const state = {
  dictList: [],
  dictMap: {},
};

const mutations = {
  SET_DICT_LIST(state, list) {
    state.dictList = list;
  },
  SET_DICT_MAP(state, list) {
    const map = {};

    list.forEach(item => {
      const { dictCode, dictName, dictValue } = item;

      if (!map[dictCode]) {
        map[dictCode] = {};
      }

      map[dictCode][dictValue] = dictName;
    });

    state.dictMap = map;
  },
};

const actions = {
  async loadDictList({ state, commit }, refresh) {
    if (!state.dictList.length || refresh) {
      const { data: list = [] } = await getDictList({ params: { type: 2 } });
      commit('SET_DICT_LIST', list);
      commit('SET_DICT_MAP', list);
    }
    return state.dictList;
  },
};

export default {
  namespaced: true,
  state,
  mutations,
  actions,
};
