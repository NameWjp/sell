import { post, get } from '@/utils/request';
import { obj2Pagination, pagination2Query } from '@/utils/pagination';

export async function getOrganList(pagination, params) {
  const { data } = await get('/organ/list', {
    params: pagination2Query(pagination, params),
  });
  return obj2Pagination(data);
}

export function addOrgan(data) {
  return post('/organ/create', data);
}

export function editOrgan(data) {
  return post(`/organ/update/${data.id}`, data);
}

export function deleteOrgan(data) {
  return post('/organ/delete', data);
}

export function getOrganTree() {
  return get('/organ/getTree');
}

export function getOrganInfo({ id }) {
  return get(`/organ/getInfo/${id}`);
}
