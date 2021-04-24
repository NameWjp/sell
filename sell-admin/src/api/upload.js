import { customUpload } from '@/utils/upload';

export function customUploadImg(obj) {
  return customUpload({ ...obj, action: '/upload/uploadImages' });
}

export function customUploadFile(obj) {
  return customUpload({ ...obj, action: '/upload/uploadFiles' });
}
