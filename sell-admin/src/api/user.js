import { post, get } from '@/utils/request';
import { obj2Pagination, pagination2Query } from '@/utils/pagination';

export function addUser(data) {
  return post('/user/create', data);
}

export function editUser(data) {
  return post(`/user/update/${data.id}`, data);
}

export function deleteUser(data) {
  return post('/user/delete', data);
}

export function getUserInfoById({ id }) {
  return get(`/user/getInfo/${id}`);
}

export async function getUserList(pagination, params) {
  const { data } = await get('/user/list', {
    params: pagination2Query(pagination, params),
  });
  return obj2Pagination(data);
}

export function updatePassword(data) {
  return post('/user/updatePassword', data);
}
