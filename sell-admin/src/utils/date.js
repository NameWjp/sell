export function dateFormat(fmt, date) {
  if (!date) {
    return '';
  }
  let d = date;
  if (typeof d === 'string' || typeof d === 'number') {
    d = new Date(d);
  }
  const o = {
    'M+': d.getMonth() + 1, // 月份
    'd+': d.getDate(), // 日
    'h+': d.getHours(), // 小时
    'm+': d.getMinutes(), // 分
    's+': d.getSeconds(), // 秒
    'q+': Math.floor((d.getMonth() + 3) / 3), // 季度
    S: d.getMilliseconds(), // 毫秒
  };
  let ret = fmt;
  if (/(y+)/.test(ret)) {
    ret = ret.replace(RegExp.$1, (`${d.getFullYear()}`).substr(4 - RegExp.$1.length));
  }
  Object.keys(o).forEach((k) => {
    if (new RegExp(`(${k})`).test(ret)) {
      ret = ret.replace(RegExp.$1, (RegExp.$1.length === 1) ? (o[k]) : ((`00${o[k]}`).substr((`${o[k]}`).length)));
    }
  });
  return ret;
}

export function formatDate(date) {
  return dateFormat('yyyy-MM-dd', date);
}

export function formatMonth(date) {
  return dateFormat('yyyy-MM', date);
}

export function formatDatetime(date) {
  return dateFormat('yyyy-MM-dd hh:mm:ss', date);
}

export function formatTime(date) {
  return dateFormat('hh:mm:ss', date);
}

// 获取当前月的第一天
export function getCurrentMonthFirst() {
  const date = new Date();
  return new Date(date.getFullYear(), date.getMonth(), 1);
}

// 获取当前月的最后一天
export function getCurrentMonthLast() {
  const date = new Date();
  return new Date(date.getFullYear(), date.getMonth() + 1, 1, 0, 0, -1);
}

// 获取当年第一天
export function getCurrentYearFirst() {
  const date = new Date();
  return new Date(date.getFullYear(), 0, 1);
}

// 获取当年最后一天
export function getCurrentYearLast() {
  const date = new Date();
  return new Date(date.getFullYear() + 1, 0, 0, 0, 0, -1);
}

// 获取当天开始
export function getCurrentDayStart() {
  const date = new Date();
  return new Date(date.getFullYear(), date.getMonth(), date.getDate());
}

// 获取当天结束
export function getCurrentDayEnd() {
  const date = new Date();
  return new Date(date.getFullYear(), date.getMonth(), date.getDate() + 1, 0, 0, -1);
}

// 获取昨天开始
export function getYesterdayDayStart() {
  const date = new Date();
  return new Date(date.getFullYear(), date.getMonth(), date.getDate() - 1);
}

// 获取昨天结束
export function getYesterdayDayEnd() {
  const date = new Date();
  return new Date(date.getFullYear(), date.getMonth(), date.getDate(), 0, 0, -1);
}

export function timeFix() {
  const time = new Date();
  const hour = time.getHours();
  return hour < 9 ? '早上好' : hour <= 11 ? '上午好' : hour <= 13 ? '中午好' : hour < 20 ? '下午好' : '晚上好';
}

export function timeSince(date) {
  let oldDate = date;
  if (typeof date === 'string' || typeof date === 'number') {
    oldDate = new Date(date);
  }

  const seconds = Math.floor((new Date() - oldDate.getTime()) / 1000);
  let interval = Math.floor(seconds / 31536000);

  if (interval >= 1) {
    return interval + ' 年前';
  }
  interval = Math.floor(seconds / 2592000);
  if (interval >= 1) {
    return interval + ' 月前';
  }
  interval = Math.floor(seconds / 86400);
  if (interval >= 1) {
    return interval + ' 天前';
  }
  interval = Math.floor(seconds / 3600);
  if (interval >= 1) {
    return interval + ' 小时前';
  }
  interval = Math.floor(seconds / 60);
  if (interval >= 1) {
    return interval + ' 分钟前';
  }
  return Math.floor(seconds) + ' 秒前';
}

export function getDiffDate(startDate, endDate) {
  if (!startDate || !endDate) return;

  const seconds = Math.floor((endDate.getTime() - startDate.getTime()) / 1000);
  let leftSeconds = seconds;

  const days = Math.floor(seconds / 86400);
  leftSeconds = leftSeconds % 86400;
  const hours = Math.floor(leftSeconds / 3600);
  leftSeconds = leftSeconds % 3600;
  const minutes = Math.floor(leftSeconds / 60);
  leftSeconds = leftSeconds % 60;

  return `${days}天${hours}小时${minutes}分钟${leftSeconds}秒`;
}
