const getters = {
  sidebar: state => state.app.sidebar,
  token: state => state.user.token,
  visitedViews: state => state.tagsView.visitedViews,
  cachedViews: state => state.tagsView.cachedViews,
  name: state => state.user.name,
  roles: state => state.user.roles,
  permission_routes: state => state.permission.routes,
  menuTree: state => state.user.menuTree,
};
export default getters;
