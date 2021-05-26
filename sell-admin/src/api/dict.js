import { post, get } from '@/utils/request';

export function getDictList(data) {
  return get('/dict/list', data);
}

export function addDict(data) {
  return post('/dict/create', data);
}

export function getSubTree() {
  return get('/dict/getSubTree');
}
