package com.wangjp.sell.controller;

import com.wangjp.sell.enums.ResultEnum;
import com.wangjp.sell.exception.SellException;
import com.wangjp.sell.utils.FileUploadUtil;
import com.wangjp.sell.utils.ResultVOUtil;
import com.wangjp.sell.vo.ResultVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * @author wangjp
 * @email 943375372@qq.com
 * @date 2021/10/20 14:34
 * @detail
 */
@Slf4j
@Api(tags = "公共方法")
@RestController
@RequestMapping("/common")
public class CommonController {

    @Value("${upload.image.path}")
    public String imageRootPath;

    @ApiOperation("多张图片上传")
    @PostMapping("/uploadImages")
    public ResultVO<String> uploadImage(@RequestParam(value = "file") MultipartFile[] images) {
        if (images.length == 0) {
            throw new SellException(ResultEnum.COMMON_IMAGE_NOT_FIND);
        }

        List<String> paths = FileUploadUtil.uploadImages(images, imageRootPath);

        return ResultVOUtil.success(String.join(",", paths));
    }
}
