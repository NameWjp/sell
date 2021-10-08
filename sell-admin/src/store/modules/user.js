import { login, getUserInfo, logout, refreshToken } from '@/api/auth';
import { setToken, clearToken } from '@/utils/request';

// 过滤按钮 type: 2为按钮 1为菜单
function filterMenuTree(menus) {
  const ret = [];

  menus.forEach((item) => {
    const menu = { ...item };
    const { children, type } = menu;
    if (type === 1) {
      if (children && children.length) {
        menu.children = filterMenuTree(menu.children);
      }
      ret.push(menu);
    }
  });

  return ret;
}

const state = {
  name: 'test',
  roles: [],
  userInfo: {},
  menuTree: [],
};

const mutations = {
  SET_NAME(state, name) {
    state.name = name;
  },
  SET_ROLES(state, roles) {
    state.roles = roles;
  },
  SET_USER_INFO(state, info) {
    state.userInfo = info;
  },
  SET_MENU_TREE(state, tree) {
    state.menuTree = tree;
  },
};

const actions = {
  async login({ commit }, userInfo) {
    const { username, password } = userInfo;
    try {
      const { data: { token: str, expiredDate } = {} } = await login({ username: username.trim(), password });
      const token = `Bearer ${str}`;
      if (token) {
        setToken(token, expiredDate);
        return token;
      }
      return false;
    } catch (e) {
      return false;
    }
  },

  async getInfo({ commit }) {
    const { data: userInfo } = await getUserInfo();
    if (userInfo) {
      const { username = '', privilegeList = [], privilegeTreeList = [] } = userInfo;
      const roles = (privilegeList || []).map(item => item.code);
      commit('SET_USER_INFO', userInfo);
      commit('SET_ROLES', roles);
      commit('SET_NAME', username);
      commit('SET_MENU_TREE', filterMenuTree(privilegeTreeList));
      return { roles };
    }
    return null;
  },

  async logout({ dispatch }) {
    await logout();
    await dispatch('resetToken');
  },

  async refreshToken() {
    const { data: { token: str, expiredDate } = {} } = await refreshToken();
    const token = `Bearer ${str}`;
    setToken(token, expiredDate);
  },

  resetToken({ commit }) {
    return new Promise((resolve) => {
      commit('SET_ROLES', []);
      clearToken();
      resolve();
    });
  },
};

export default {
  namespaced: true,
  state,
  mutations,
  actions,
};
