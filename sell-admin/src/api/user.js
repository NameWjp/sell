import { post, get } from '@/utils/request';

export function addUser(data) {
  return post('/user/create', data);
}

export function deleteUser(data) {
  return post('/user/delete', data);
}
