import { TOKEN_KEY, TOKEN_EXPIRED_DATE, THEME_KEY, THEME_STYLE_KEY } from '@/constant';

export function getToken() {
  return localStorage.getItem(TOKEN_KEY);
}

export function setLocalToken(token, expiredDate) {
  localStorage.setItem(TOKEN_KEY, token);
  localStorage.setItem(TOKEN_EXPIRED_DATE, expiredDate);
}

export function removeLocalToken() {
  localStorage.removeItem(TOKEN_KEY);
  localStorage.removeItem(TOKEN_EXPIRED_DATE);
}

export function hasUser() {
  const savedToken = localStorage.getItem(TOKEN_KEY);
  const savedExpiredAt = localStorage.getItem(TOKEN_EXPIRED_DATE);
  return savedToken && savedExpiredAt > (new Date());
}

export function getTheme() {
  return {
    color: localStorage.getItem(THEME_KEY),
    style: localStorage.getItem(THEME_STYLE_KEY),
  };
}

export function setTheme(color, style) {
  localStorage.setItem(THEME_KEY, color);
  localStorage.setItem(THEME_STYLE_KEY, style);
}

export function removeTheme() {
  localStorage.removeItem(THEME_KEY);
  localStorage.removeItem(THEME_STYLE_KEY);
}
