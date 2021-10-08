import { post, get } from '@/utils/request';

export function login(data) {
  return post('/auth/login', data);
}

export function getUserInfo() {
  return get('/auth/currentUserInfo');
}

export function refreshToken() {
  return post('/auth/refreshToken');
}

export function logout() {
  return post('/auth/logout');
}
