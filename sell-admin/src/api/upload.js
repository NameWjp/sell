import { customUpload } from '@/utils/upload';

export function customUploadImg(obj) {
  return customUpload({ ...obj, action: '/common/uploadImages' });
}
