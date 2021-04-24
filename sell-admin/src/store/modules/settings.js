import defaultSettings from '@/settings';
import variables from '@/styles/element-variables.scss';
import { getTheme } from '@/utils/storage';

const { fixedHeader, sidebarLogo, tagsView } = defaultSettings;

const state = {
  theme: getTheme().color || variables.theme,
  fixedHeader,
  sidebarLogo,
  tagsView,
};

const mutations = {
  CHANGE_SETTING: (state, { key, value }) => {
    if (state.hasOwnProperty(key)) {
      state[key] = value;
    }
  },
};

const actions = {
  changeSetting({ commit }, data) {
    commit('CHANGE_SETTING', data);
  },
};

export default {
  namespaced: true,
  state,
  mutations,
  actions,
};
