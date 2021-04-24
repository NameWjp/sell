import { post, get } from '@/utils/request';

export function login(data) {
  return post('/login/index', data);
}

export function getUserInfo() {
  return get('/login/currentUserInfo');
}

export function refreshToken() {
  return post('/login/refreshToken');
}

export function logout() {
  return post('/login/logout');
}
