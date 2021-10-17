export function toNumber(v) {
  if (typeof v === 'number' || v === undefined || v === null) {
    return v;
  }
  if (v === '') {
    return undefined;
  }
  if (v && v.trim() === '') {
    return undefined;
  }
  return Number(v);
}

export function toInteger(v) {
  const number = toNumber(v);
  if (number) {
    return parseInt(number, 10);
  }
  return number;
}

export function toFloat(v, num = 2) {
  const number = toNumber(v);
  if (number) {
    return toNumber(number.toFixed(num));
  }
  return number;
}

export function toOnlyInteger(v) {
  const num = toNumber(v);
  const int = toInteger(v);
  if (num === int) {
    return int;
  }
  return false;
}
