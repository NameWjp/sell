import { post, get } from '@/utils/request';
import { obj2Pagination, pagination2Query } from '@/utils/pagination';

export function addCategory(data) {
  return post('/category/create', data);
}

export function editCategory(data) {
  return post(`/category/update/${data.id}`, data);
}

export function deleteCategory(data) {
  return post('/category/delete', data);
}

export function getCategoryInfoById({ id }) {
  return get(`/category/getInfo/${id}`);
}

export async function getCategoryList(pagination, params) {
  const { data } = await get('/category/list', {
    params: pagination2Query(pagination, params),
  });
  return obj2Pagination(data);
}

export function getAllCategoryList() {
  return get('/category/allList');
}
