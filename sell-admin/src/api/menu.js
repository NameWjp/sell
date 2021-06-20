import { post, get } from '@/utils/request';
import { obj2Pagination, pagination2Query } from '@/utils/pagination';

export async function getMenuList(pagination, params) {
  const { data } = await get('/menu/list', {
    params: pagination2Query(pagination, params),
  });
  return obj2Pagination(data);
}

export function addMenu(data) {
  return post('/menu/create', data);
}

export function editMenu(data) {
  return post(`/menu/update/${data.id}`, data);
}

export function deleteMenu(data) {
  return post('/menu/delete', data);
}

export function getMenuTree() {
  return get('/menu/getTree');
}

export function getMenuInfo({ id }) {
  return get(`/menu/getInfo/${id}`);
}
