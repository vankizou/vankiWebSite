package com.zoufanqi.imagepress;

import com.sun.image.codec.jpeg.JPEGCodec;
import com.sun.image.codec.jpeg.JPEGImageEncoder;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;

/**
 * java图片处理
 *
 * Created by vanki on 16/12/21.
 */
public class ImageUtil {
    /**
     * setting
     */
    private static final String W = "w";
    private static final String H = "h";
    private static final String N = "n";

    public static void main(String[] args) {
        File fileParent = new File("/Users/vanki/Downloads/drift");
        startResize(fileParent);
    }

    public static void startResize(File fileParent) {
        try {
            String path = fileParent.getPath();
            path = path.replace("\\", File.separator);
            path = path.replace("/", File.separator);
            String parentPath = path.substring(path.lastIndexOf(File.separator) + 1, path.length());

            File[] fs = fileParent.listFiles();
            if (fs == null) return;

            for (File file : fs) {
                if (file.isDirectory()) {
                    startResize(file);
                    continue;
                }
                if (file.getName().startsWith(".")) continue;
                BufferedImage bi = ImageIO.read(new FileInputStream(file));
                int width = bi.getWidth();
                int height = bi.getHeight();

                String resizePath = file.getAbsolutePath().replace(parentPath, parentPath + "_target");

                File fileResize = new File(resizePath);
                if (!fileResize.getParentFile().exists()) {
                    fileResize.getParentFile().mkdirs();
                }

                System.out.println(resizePic(file.getAbsoluteFile(), fileResize, true, null, null));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // 图片处理
    public static String resizePic(File src, File dest, boolean isProportion, Integer width, Integer height) {
        try {
            //获得源文件
            if (!src.exists()) {
                return "源图片不存在";
            }
            Image img = ImageIO.read(src);

            int srcWidth;
            int srcHeight;
            // 判断图片格式是否正确
            if ((srcWidth = img.getWidth(null)) == -1 || (srcHeight = img.getHeight(null)) != -1) return "不是图片格式";

            int newWidth = srcWidth;
            int newHeight = srcHeight;
            // 判断是否是等比缩放
            if (!isProportion) {
                width = width == null ? 0 : width;
                height = height == null ? 0 : height;
                PictureRatio ratio = getRatioPicWH(srcWidth, srcHeight, width, height, N);
            }
            BufferedImage tag = new BufferedImage(newWidth, newHeight, BufferedImage.TYPE_INT_RGB);

            /*
             * Image.SCALE_SMOOTH 的缩略算法 生成缩略图片的平滑度的
             * 优先级比速度高 生成的图片质量比较好 但速度慢
             */
            tag.getGraphics().drawImage(img.getScaledInstance(newWidth, newHeight, Image.SCALE_SMOOTH), 0, 0, null);
            FileOutputStream out = new FileOutputStream(dest);
            // JPEGImageEncoder可适用于其他图片类型的转换
            JPEGImageEncoder encoder = JPEGCodec.createJPEGEncoder(out);
            encoder.encode(tag);
            out.close();
        } catch (Exception ex) {
            return "失败!";
        }
        return "成功!";
    }

    /**
     * 根据平台缩放规则算出缩放比例后的值
     *
     * @param srcW
     * @param srcH
     * @param settingW
     * @param settingH
     * @param fix
     *
     * @return
     */
    private static PictureRatio getRatioPicWH(int srcW, int srcH, int settingW, int settingH, String fix) {
        PictureRatio data = new PictureRatio();
        fix = fix == null ? N : fix;


        boolean isGetSrc = false;

        /**
         * 原图比缩放图还小
         */
        if (W.equals(fix)) {
            if (srcW <= settingW) isGetSrc = true;
        } else if (H.equals(fix)) {
            if (srcH <= settingH) isGetSrc = true;
        } else {
            if (srcW <= settingW && srcH <= settingH) isGetSrc = true;
        }
        if (isGetSrc) {
            data.setRatioWidth(srcW);
            data.setRatioHeight(srcH);
            return data;
        }

        /**
         * 计算比例宽高
         */
        if (W.equals(fix)) {
            data.setRatioWidth(settingW);
            data.setRatioHeight(getRatioVal(srcW, srcH, settingW, false));
        } else if (H.equals(fix)) {
            data.setRatioWidth(getRatioVal(srcW, srcH, settingH, true));
            data.setRatioHeight(settingH);
        } else {
            int ratioW = getRatioVal(srcW, srcH, settingH, true);
            if (settingW == ratioW) {
                data.setRatioWidth(settingW);
                data.setRatioHeight(settingH);
            } else if (settingW > ratioW) {
                return getSizeByWidth(srcW, srcH, settingW);
            } else {
                return getSizeByHeight(srcW, srcH, settingH);
            }
        }
        return data;
    }

    public static PictureRatio getSizeByWidth(Integer srcW, Integer srcH, Integer settingW) {
        if (srcW == null || srcH == null || settingW == null) return new PictureRatio();
        return getRatioPicWH(srcW, srcH, settingW, 0, W);
    }

    public static PictureRatio getSizeByHeight(Integer srcW, Integer srcH, Integer settingH) {
        if (srcW == null || srcH == null || settingH == null) return new PictureRatio();
        return getRatioPicWH(srcW, srcH, 0, settingH, H);
    }

    public static Integer getRatioVal(int xSrc, int ySrc, int xxOrYy, boolean... returnValIsXx) {
        if (xSrc <= 0 || ySrc <= 0 || xxOrYy <= 0) return null;

        boolean isXx = returnValIsXx == null || returnValIsXx.length == 0 ? true : returnValIsXx[0];

        Double returnXxOrYy;
        if (isXx) {   // 求等比宽
            returnXxOrYy = ((double) xSrc / ySrc) * xxOrYy;
        } else {    // 求等比高
            returnXxOrYy = ((double) ySrc * xxOrYy) / xSrc;
        }
        return returnXxOrYy == null ? null : returnXxOrYy.intValue();
    }
}
