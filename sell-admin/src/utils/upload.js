import { post } from '@/utils/request';

export function customUpload(obj) {
  const {
    action, file, headers,
    onError, onProgress, onSuccess,
  } = obj;

  const formData = new FormData();
  formData.append('file', file, file.name);

  post(action, formData, {
    headers: {
      'Content-Type': 'multipart/form-data',
      ...headers,
    },
    onUploadProgress: (event) => {
      if (event.total > 0) {
        event.percent = Math.floor((event.loaded * 100) / event.total);
      }
      onProgress(event);
    },
  })
    .then((resp) => {
      if (resp.status === 200) {
        const { data: url } = resp;
        onSuccess({
          name: file.name,
          // eslint-disable-next-line no-undef
          url: `${PROXY_BASE_URL}/${url}`,
        });
      } else {
        onError({ event: new Error(resp.message), body: resp });
      }
    })
    .catch((error, resp) => onError({ event: error, body: resp }));
}
