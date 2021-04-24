import { post, get } from '@/utils/request';

export function getDictList(data) {
  return get('/dict/list', data);
}
