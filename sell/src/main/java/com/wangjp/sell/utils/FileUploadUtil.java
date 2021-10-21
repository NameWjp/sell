package com.wangjp.sell.utils;

import com.wangjp.sell.constant.CommonConstant;
import com.wangjp.sell.enums.ResultEnum;
import com.wangjp.sell.exception.SellException;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.*;

/**
 * @author wangjp
 * @email 943375372@qq.com
 * @date 2021/10/20 14:53
 * @detail
 */
public class FileUploadUtil {

    private static final List<String> mImageTypes = new ArrayList<>();

    static {
        mImageTypes.add("jpg");
        mImageTypes.add("png");
        mImageTypes.add("gif");
        mImageTypes.add("tif");
        mImageTypes.add("bmp");
    }

    /**
     * 生成一个唯一的文件名称，防止文件覆盖
     * @param originName
     * @return
     */
    private static String makeFileName(String originName) {
        return UUID.randomUUID().toString() + "_" + originName;
    }

    /**
     * 根据文件名称和基础目录生成文件路径
     * @param imageName 文件名称
     * @param dirName 基础目录
     * @return
     */
    private static Map<String, String> makeDirPath(String imageName, String rootPath, String dirName) {
        int hashCode = imageName.hashCode();
        // 从低四位算出[0-15]中的一个数字
        int dir1 = hashCode & 0xf;
        // 从高四位算出[0-15]中的一个数字
        int dir2 = (hashCode & 0xf0) >> 4;

        // 构建目录
        String relativeDir = dirName + "/" + dir1 + "/" + dir2;
        String absoluteDir = rootPath + "/" + relativeDir;

        // 创建目录
        File file = new File(absoluteDir);
        if (!file.exists()) {
            file.mkdirs();
        }

        // 返回静态资源访问目录
        String[] rootDirList = rootPath.split("/");
        String rootDir = rootDirList[rootDirList.length - 1];
        String staticDir = rootDir + "/" + relativeDir;

        // 返回结果
        Map<String, String> result = new HashMap<>();
        result.put("absoluteDir", absoluteDir);
        result.put("staticDir", staticDir);
        return result;
    }

    /**
     * 根据文件名称判断是否是图片
     * @param originName
     * @return
     */
    private static Boolean isImage(String originName) {
        String extName = originName.substring(originName.lastIndexOf(".") + 1);
        extName = extName.toLowerCase();
        return mImageTypes.contains(extName);
    }

    /**
     * 上传单张图片
     * @param image
     * @return
     */
    public static String uploadImage(MultipartFile image, String rootPath) {
        try {
            String originName = image.getOriginalFilename();
            if (originName == null || !isImage(originName)) {
                throw new SellException(ResultEnum.COMMON_IMAGE_FORMAT_ERROR);
            }

            String imageName = makeFileName(originName);
            Map<String, String> imageDir = makeDirPath(imageName, rootPath, CommonConstant.IMAGE);
            File file = new File(imageDir.get("absoluteDir"), imageName);
            image.transferTo(file);
            return imageDir.get("staticDir") + "/" + imageName;
        } catch (IOException e) {
            throw new SellException(ResultEnum.COMMON_IMAGE_SAVE_ERROR);
        }
    }

    public static List<String> uploadImages(MultipartFile[] images, String rootPath) {
        List<String> result = new ArrayList<>();

        for (MultipartFile image : images) {
            result.add(uploadImage(image, rootPath));
        }

        return result;
    }
}
