import { get } from '@/utils/request';

export function getOrganTree() {
  return get('/systemOrgan/orgTree');
}
