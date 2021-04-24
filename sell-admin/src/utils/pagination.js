export function obj2Pagination(obj = {}) {
  const {
    list = [], pageSize = 20, total = 0, pageNum = 1,
  } = obj;
  return {
    list, pageSize, total, currentPage: pageNum,
  };
}

export function pagination2Query(pagination = {}, otherQuery = {}) {
  const { currentPage = 1, pageSize = 20 } = pagination;
  return { pageNum: currentPage, pageSize, ...otherQuery };
}
