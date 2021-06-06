import { post, get } from '@/utils/request';
import { obj2Pagination, pagination2Query } from '@/utils/pagination';

export function addRole(data) {
  return post('/role/create', data);
}

export function editRole(data) {
  return post(`/role/update/${data.id}`, data);
}

export function deleteRole(data) {
  return post('/role/delete', data);
}

export function getRoleInfoById({ id }) {
  return get(`/role/getInfo/${id}`);
}

export async function getRoleList(pagination, params) {
  const { data } = await get('/role/list', {
    params: pagination2Query(pagination, params),
  });
  return obj2Pagination(data);
}
