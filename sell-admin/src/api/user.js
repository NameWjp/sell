import { post, get } from '@/utils/request';

export function editPassword(data) {
  return post(`/user/updatePassword/${data.id}`, data);
}
