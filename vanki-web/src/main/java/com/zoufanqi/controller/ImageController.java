package com.zoufanqi.controller;

import com.zoufanqi.consts.ConstDB;
import com.zoufanqi.consts.ConstService;
import com.zoufanqi.entity.Picture;
import com.zoufanqi.result.ResultBuilder;
import com.zoufanqi.result.ResultJson;
import com.zoufanqi.service.PictureService;
import com.zoufanqi.status.EnumStatusCode;
import com.zoufanqi.utils.*;
import org.apache.commons.io.FileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

/**
 * 图片上传
 */
@Controller
@RequestMapping("/image")
public class ImageController extends BaseController {
    private static final Logger LOG = LoggerFactory.getLogger(ImageController.class);
    private static final int UPLOAD_MAX_NUM = 20;
    private static final List<String> allowTypeList = new ArrayList<>();
    private static String imageBasePath = ConfigUtil.get("image.base.path");

    static {
        allowTypeList.add("jpg");
        allowTypeList.add("jpeg");
        allowTypeList.add("png");
        allowTypeList.add("gif");
    }

    @Autowired
    private PictureService pictureService;

    /**
     * 图片相对路径父文件夹
     *
     * @return
     */
    private static final String getSubDir() {
        Calendar calendar = Calendar.getInstance();

        StringBuilder subDir = new StringBuilder();
        subDir.append(calendar.get(Calendar.YEAR));
        subDir.append(File.separatorChar);
        subDir.append(calendar.get(Calendar.MONTH) + 1);
        subDir.append(File.separatorChar);
        subDir.append(calendar.get(Calendar.DAY_OF_MONTH));

        return subDir.toString();
    }

    private static boolean resizeImg(int useType, int width, File imageFile) {
        int maxWidth;

        switch (useType) {
            case ConstDB.Picture.USE_TYPE_NOTE:
                maxWidth = 800;
                break;
            case ConstDB.Picture.USE_TYPE_AVATAR:
                maxWidth = 300;
                break;
            default:
                maxWidth = 500;
        }

        if (width <= maxWidth) return false;

        /**
         * TODO: 缩放图片，有时间把这个操作加到消息队列里去
         */
        String absPath = null;
        try {
            ImageMagickUtil.resizeWidth(maxWidth, (absPath = imageFile.getAbsolutePath()), absPath);
            LOG.info("缩放图片：{}", imageFile.getAbsolutePath());
            return true;
        } catch (Exception e) {
            LOG.error("缩放图片失败：{}, {}", absPath, ExceptionUtil.getExceptionAllMsg(e));
            return false;
        }
    }

    @ResponseBody
    @RequestMapping(value = "/uploadMulti.json", method = RequestMethod.POST)
    public ResultJson uploadMulti(@RequestParam MultipartFile[] images, Integer useType) throws Exception {
        int uploadNum;
        if (images == null || (uploadNum = images.length) == 0)
            return ResultBuilder.buildError(EnumStatusCode.IMAGE_EMPTY);
        if (uploadNum > UPLOAD_MAX_NUM) return ResultBuilder.buildError(EnumStatusCode.IMAGE_OVER_MAX_NUM);
        if (useType == null) useType = ConstDB.Picture.USE_TYPE_NOTE;

        Long userId = this.getUserId();

        File baseDir = new File(imageBasePath);
        if (!baseDir.exists()) {
            baseDir.mkdirs();
            FileUtil.setFilePermission(imageBasePath, 755);
        }

        List<Picture> picList = new ArrayList<>(uploadNum);

        try {
            for (MultipartFile image : images) {
                String imageType = FileTypeUtil.getFileTypeByInputStream(image.getInputStream());
                if (StringUtil.isEmpty(imageType) || !allowTypeList.contains(imageType)) continue;

                final String uuid = UUIDUtil.getUUID();
                String imageRelationPath = getSubDir();
                File imageParentDir = new File(imageBasePath, imageRelationPath);

                if (!imageParentDir.exists()) { // 生成父文件夹
                    imageParentDir.mkdirs();
                    FileUtil.setFilePermission(imageParentDir.getAbsolutePath(), 755);
                }
                imageRelationPath = imageRelationPath + File.separatorChar + uuid + "." + imageType;

                File imageFile = new File(baseDir, imageRelationPath);

                BufferedImage bi = ImageIO.read(image.getInputStream());
                int width = bi.getWidth();
                int height = bi.getHeight();

                FileUtils.copyInputStreamToFile(image.getInputStream(), imageFile);
                image.getInputStream().close();
                FileUtil.setFilePermission(imageFile, 644);

                boolean isResize = resizeImg(useType, width, imageFile);    // 图片缩放
                if (isResize) {
                    bi = ImageIO.read(imageFile);
                    width = bi.getWidth();
                    height = bi.getHeight();
                }
                imageRelationPath = imageRelationPath.replace("\\", "/");

                Picture pic = new Picture();
                pic.setUuid(uuid);
                pic.setWidth(width);
                pic.setHeight(height);
                pic.setPath(imageRelationPath);
                pic.setSize(imageFile.length());
                pic.setType(imageType);
                pic.setUseType(useType);
                pic.setName(image.getOriginalFilename().substring(0, image.getOriginalFilename().lastIndexOf(".")));
                pic.setUserId(userId);

                int status = this.pictureService.add(pic);
                if (status > 0) {
                    LOG.info("上传图片，路径：{}", imageRelationPath);
                    pic.setPath(ConstService.imageDomain + pic.getPath());
                    picList.add(pic);
                }
            }
            return ResultBuilder.build(picList);
        } catch (Exception e) {
            throw e;
        } finally {
            for (MultipartFile image : images) {
                if (image.getInputStream() != null) {
                    image.getInputStream().close();
                }
            }
        }
    }
}
