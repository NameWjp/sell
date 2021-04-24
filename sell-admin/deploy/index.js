const Client = require('ssh2-sftp-client');
const print = require('./plugin/print');
const getNodeParams = require('./plugin/nodeParams');
const allConfig = require('./config');
const { fileOrDirForEach } = require('./plugin/fs');

const LOCAL_PATH = './dist/';
const REMOTE_PATH = '/usr/local/nginx/html/vueAdmin/';

const config =
  process.env.DEPLOY_ENV === 'prod' ? allConfig.prod :
  process.env.DEPLOY_ENV === 'test' ? allConfig.test :
  {};

async function deploy() {
  const { host, username, password } = config || getNodeParams();

  if (!host || !username || !password) {
    print.error('Upload Dist to SFTP Missing Parameters');
    return false;
  }

  if (!/\/usr\/local\/nginx\/html/.test(REMOTE_PATH)) {
    print.error('REMOTE_PATH is illegal');
    return false;
  }

  const sftp = new Client();

  try {
    await sftp.connect({ host, username, password });

    print.tip('SFTP connected!');

    const fileType = await sftp.exists(REMOTE_PATH);

    if (fileType) {
      if (fileType === 'd') {
        await sftp.rmdir(REMOTE_PATH, true);
      } else {
        await sftp.delete(REMOTE_PATH);
      }
    }

    await sftp.mkdir(REMOTE_PATH, true);

    print.tip('SFTP start upload');

    const basePath = LOCAL_PATH.slice(0, LOCAL_PATH.length - 1);

    await fileOrDirForEach(basePath, async ({ src, isFile }) => {
      const destPath = src.replace(LOCAL_PATH, REMOTE_PATH);

      if (isFile) {
        print.tip('upload：' + destPath);

        await sftp.put(src, destPath, {
          mode: 0o644
        });
      } else {
        await sftp.mkdir(destPath);
      }
    });

    print.yellow('-------------------------------------')
    print.success('    Automatic Deployment Success!    ')
    print.yellow('-------------------------------------')
  } catch(e) {
    print.error('SFTP upload error');
  }

  await sftp.end();
}

deploy();
