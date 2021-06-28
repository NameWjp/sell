import { getOrganTree } from '@/api/organ';
import { flattenTree } from '@/utils/tree';

const state = {
  organizationTree: [{ name: '测试组织机构', id: 0 }],
  organizationMap: { 0: { name: '测试组织机构', id: 0 } },
};

const mutations = {
  SET_ORGAN_TREE(state, tree) {
    state.organizationTree = tree;
  },
  SET_ORGAN_MAP(state, list) {
    const map = {};
    list.forEach(item => {
      map[item.id] = item;
    });
    state.organizationMap = map;
  },
};

const actions = {
  async loadOrganizationTree({ state, commit }, refresh) {
    if (!state.organizationTree.length || refresh) {
      const { data: tree } = await getOrganTree();
      commit('SET_ORGAN_MAP', flattenTree(tree));
      commit('SET_ORGAN_TREE', tree);
    }
    return state.organizationTree;
  },
};

export default {
  namespaced: true,
  state,
  mutations,
  actions,
};
