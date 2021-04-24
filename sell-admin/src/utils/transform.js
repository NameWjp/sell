export function toNumber(v) {
  if (typeof v === 'number') {
    return v;
  }

  if (typeof v === 'string') {
    if (v === '') {
      return undefined;
    }
    if (v.trim() === '') {
      return false;
    }
    if (v.slice(-1) === '.') {
      return false;
    }
  }

  return Number(v);
}

export function toInteger(v) {
  return parseInt(toNumber(v), 10);
}

export function toFloat(v, num = 2) {
  return toNumber(Number(v).toFixed(num));
}

export function toOnlyInteger(v) {
  const num = toNumber(v);
  const int = toInteger(v);
  if ((num || num === 0) && num === int) {
    return int;
  }
  return false;
}
