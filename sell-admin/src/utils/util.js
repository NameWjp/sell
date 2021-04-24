export function getUniqueKey() {
  return Math.random().toString(36).substr(2, 9);
}

/**
 * 防抖函数
 * 注意，fn不要使用箭头函数
 * @param {Function} fn
 * @param {Number} delay
 */
export function debounce(fn, delay) {
  let timerId;
  return function(...args) {
    if (timerId) {
      clearTimeout(timerId);
    }
    timerId = setTimeout(() => {
      fn.call(this, ...args);
      timerId = null;
    }, delay);
  };
}

/**
 * 节流函数
 * 开始执行触发，离开后不触发
 * @param {Function} fn
 * @param {Number} delay
 */
export function throttle(fn, delay) {
  let previous = 0;

  return function(...args) {
    const now = +new Date();

    if (now - previous > delay) {
      fn.call(this, ...args);
      previous = now;
    }
  };
}

export function getQueryParams(query) {
  return query
    ? (/^[?#]/.test(query) ? query.slice(1) : query)
      .split('&')
      .reduce(
        (params, param) => {
          const [key, value] = param.split('=');
          params[key] = value ? decodeURIComponent(value.replace(/\+/g, ' ')) : '';
          return params;
        }, {}
      )
    : {};
}

export function firstUpperCase(str) {
  return str.replace(/\s/g, '').toLowerCase().replace(/^[a-z]/, s => s.toUpperCase());
}
