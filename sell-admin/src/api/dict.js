import { post, get } from '@/utils/request';
import { obj2Pagination, pagination2Query } from '@/utils/pagination';

export async function getDictList(pagination, params) {
  const { data } = await get('/dict/list', {
    params: pagination2Query(pagination, params),
  });
  return obj2Pagination(data);
}

export function addDict(data) {
  return post('/dict/create', data);
}

export function editDict(data) {
  return post(`/dict/update/${data.id}`, data);
}

export function deleteDict(data) {
  return post('/dict/delete', data);
}

export function getSubTree() {
  return get('/dict/getSubTree');
}

export function getDictInfo({ id }) {
  return get(`/dict/getInfo/${id}`);
}

export function getAllDictList(params) {
  return get('/dict/allList', {
    params,
  });
}
