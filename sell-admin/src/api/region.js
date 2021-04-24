import { post, get } from '@/utils/request';
import { obj2Pagination, pagination2Query } from '@/utils/pagination';

export async function getRegionList(pagination, query) {
  const { data } = await get('/region/list', {
    params: pagination2Query(pagination, query),
  });
  return obj2Pagination(data);
}
