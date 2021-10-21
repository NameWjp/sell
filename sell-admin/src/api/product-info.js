import { post, get } from '@/utils/request';
import { obj2Pagination, pagination2Query } from '@/utils/pagination';

export function addProductInfo(data) {
  return post('/productInfo/create', data);
}

export function editProductInfo(data) {
  return post(`/productInfo/update/${data.id}`, data);
}

export function deleteProductInfo(data) {
  return post('/productInfo/delete', data);
}

export function getProductInfoInfoById({ id }) {
  return get(`/productInfo/getInfo/${id}`);
}

export async function getProductInfoList(pagination, params) {
  const { data } = await get('/productInfo/list', {
    params: pagination2Query(pagination, params),
  });
  return obj2Pagination(data);
}

