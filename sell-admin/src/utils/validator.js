/**
 * 用于判断是否是外部链接
 * @param {string} path
 * @returns {Boolean}
 */
export function isExternal(path) {
  return /^(https?:|mailto:|tel:)/.test(path);
}

// 用户名效验
export function validateUsername(rule, value, callback) {
  const fregex = /^[.@A-Za-z0-9_-]{1,16}$/;
  const chinese = /^[\u4E00-\u9FA5]/;
  if (value && chinese.test(value)) {
    return callback('不允许是中文');
  }
  if (value && !fregex.test(value)) {
    return callback('长度在 1 到 16 个字符');
  }
  return callback();
}

// 手机号效验
export function validatePhone(rule, value, callback) {
  const fregex = /^[1][3,4,5,7,8,9][0-9]{9}$/;
  if (value && !fregex.test(value)) {
    return callback('手机号输入有误');
  }
  return callback();
}

// 默认姓名
export function validate(rule, value, callback) {
  const fregex = /^[\u4E00-\u9FA5A-Za-z]{2,50}$/;
  if (value && !fregex.test(value)) {
    return callback('名称输入有误');
  }
  return callback();
}

// 身份证效验
export function validatorID(rule, value, callback) {
  if (value && !/^[1-9]\d{5}(18|19|20)\d{2}((0[1-9])|(1[0-2]))(([0-2][1-9])|10|20|30|31)\d{3}[0-9Xx]$/.test(value)) {
    return callback('身份证号输入有误');
  }
  return callback();
}

// 不能为负数
export function validatePositive(rule, value, callback) {
  if (value < 0) {
    return callback('值不能为负数');
  }
  return callback();
}

// 不能为0
export function validateNotZero(rule, value, callback) {
  if (value === 0) {
    return callback('值不能为零');
  }
  return callback();
}
