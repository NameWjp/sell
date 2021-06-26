/**
 * 获取树节点
 * @param {Array} tree 树结构
 * @param {*} value 要找的节点值
 * @param {String} keyName 要找的节点键名
 * @param {String} childrenKey 当前节点中子节点键名
 */
export function getTreeNode(tree, value, keyName, childrenKey) {
  const stack = [...tree];
  let tempNode;
  while (stack.length > 0) {
    tempNode = stack.pop();
    if (tempNode[keyName] === value) {
      return tempNode;
    }
    if (tempNode[childrenKey] && tempNode[childrenKey].length > 0) {
      stack.push(...tempNode[childrenKey]);
    }
  }
  return null;
}

/**
 * 获取展开所有树结构需要的keyName对应的值
 * @param {Array} tree
 * @param {String} keyName
 * @param {String} childrenKey
 */
export function getTreeExpandAllKeys(tree, keyName, childrenKey) {
  const keys = [];
  const stack = [...tree];
  let tempNode;
  while (stack.length > 0) {
    tempNode = stack.pop();
    if (tempNode[childrenKey] && tempNode[childrenKey].length > 0) {
      keys.push(tempNode[keyName]);
      stack.push(...tempNode[childrenKey]);
    }
  }
  return keys;
}

// 夷平树结构
export function flattenTree(tree, childrenKey = 'children') {
  const stack = [...tree];
  const res = [];
  let next;
  while (stack.length) {
    next = stack.shift();
    if (next[childrenKey] && next[childrenKey].length) {
      stack.unshift(...next[childrenKey]);
    }
    res.push(next);
  }
  return res;
}

/**
 * 根据叶子节点找出叶子节点的路径
 * @param {Array} tree
 * @param {*} value
 * @param {String} keyName
 * @param {String} childrenKey
 */
export function getTreeKeyPath(tree, value, keyName, childrenKey = 'children') {
  const path = [];

  const parse = (subTree) => {
    for (let i = 0; i < subTree.length; i += 1) {
      const node = subTree[i];
      path.push(node[keyName]);
      if (node[keyName] === value) {
        return true;
      }
      if (node[childrenKey] && node[childrenKey].length) {
        if (parse(node[childrenKey])) {
          return true;
        }
      }
      path.pop();
    }
    return false;
  };

  parse(tree);
  return path;
}

/**
 * 获取树的前几级结构
 * @export
 * @param {树节点} tree
 * @param {前几级} level
 */
export function getSubTree(tree, level = 2, childrenKey = 'children') {
  const result = JSON.parse(JSON.stringify(tree));

  function parse(subTree, index = 1) {
    subTree.forEach((e) => {
      if (index === level) {
        e[childrenKey] = [];
      } else {
        if (e[childrenKey] && e[childrenKey].length) {
          parse(e[childrenKey], index + 1);
        }
      }
    });
  }

  parse(result);
  return result;
}

// 列表结构转树结构
export function listToTree(source, pid = 0) {
  const result = [];
  let temp;

  source.forEach((e) => {
    if (e.parentId === pid) {
      temp = listToTree(source, e.id);
      if (temp.length > 0) {
        e.children = temp;
      }
      result.push(e);
    }
  });
  return result;
}

// 获取所有叶子节点
export function getLeafKeys(tree, keyName = 'id', childrenKey = 'children') {
  const result = [];
  const stack = [...tree];
  let tempNode;

  while (stack.length > 0) {
    tempNode = stack.pop();
    if (tempNode[childrenKey] && tempNode[childrenKey].length) {
      stack.push(...tempNode[childrenKey]);
    } else {
      result.push(tempNode[keyName]);
    }
  }

  return result;
}
