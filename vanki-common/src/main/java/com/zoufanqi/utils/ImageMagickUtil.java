package com.zoufanqi.utils;

import org.im4java.core.ConvertCmd;
import org.im4java.core.IMOperation;

import java.io.FileInputStream;
import java.io.IOException;

/**
 * 图片缩放、裁切工具类
 *
 * @author vanki
 * @project_name lofficiel-rabbitmq 2015年12月14日 上午9:45:24
 */
public class ImageMagickUtil {

    /**
     * 获得图片文件大小
     *
     * @param imagePath 文件路径
     *
     * @return 文件大小
     */
    public static int getSize(String imagePath) {
        int size = 0;
        FileInputStream inputStream = null;
        try {
            inputStream = new FileInputStream(imagePath);
            size = inputStream.available();
            inputStream.close();
            inputStream = null;
        } catch (Exception e) {
            size = 0;
        } finally {
            // 可能异常为关闭输入流,所以需要关闭输入流
            if (null != inputStream) {
                try {
                    inputStream.close();
                } catch (IOException e) {
                }
                inputStream = null;
            }
        }
        return size;
    }

    /**
     * 根据坐标裁剪图片
     *
     * @param srcPath  要裁剪图片的路径
     * @param destPath 裁剪图片后的路径
     * @param x_start  起始横坐标
     * @param y_start  起始纵坐标
     * @param x_end    结束横坐标
     * @param y_end    结束纵坐标
     */

    public static void cutImage(String srcPath, String destPath, int x_start,
                                int y_start, int x_end, int y_end) throws Exception {
        int width = x_end - x_start;
        int height = y_end - y_start;
        IMOperation op = new IMOperation();
        op.addImage(srcPath);
        /**
         * width： 裁剪的宽度 height： 裁剪的高度 x： 裁剪的横坐标 y： 裁剪的挫坐标
         */
        op.crop(width, height, x_start, y_start);
        op.addImage(destPath);
        ConvertCmd convert = new ConvertCmd();
        convert.run(op);
    }

    /**
     * 根据尺寸等比缩放图片
     *
     * @param width    缩放后的图片宽度
     * @param height   缩放后的图片高度
     * @param srcPath  源图片路径
     * @param destPath 缩放后图片的路径
     */
    public static void resize(int width, int height, String srcPath,
                              String destPath) throws Exception {
        IMOperation op = new IMOperation();
        op.addImage(srcPath);
        op.resize(width, height);
        op.addImage(destPath);
        ConvertCmd convert = new ConvertCmd();
        convert.run(op);
    }

    public static void main(String[] args) throws Exception {
        // /Users/vanki/Documents/company/lofficiel/data/1111.jpg
        //resize_width(253, "/Users/vanki/Documents/company/lofficiel/data/16.jpg", "/Users/vanki/Documents/company/lofficiel/data/16_1.jpg");
    }

    /**
     * 根据宽度缩放图片
     *
     * @param width    缩放后的图片宽度
     * @param srcPath  源图片路径
     * @param destPath 缩放后图片的路径
     */
    public static void resizeWidth(int width, String srcPath, String destPath)
            throws Exception {
        IMOperation op = new IMOperation();
        op.addImage(srcPath);
        op.resize(width, null);
        op.addImage(destPath);
        ConvertCmd convert = new ConvertCmd();
        convert.run(op);
    }

    /**
     * 根据高度缩放图片
     *
     * @param height   缩放后的图片高度
     * @param srcPath  源图片路径
     * @param destPath 缩放后图片的路径
     */
    public static void resizeHeight(int height, String srcPath, String destPath)
            throws Exception {
        IMOperation op = new IMOperation();
        op.addImage(srcPath);
        op.resize(null, height);
        op.addImage(destPath);
        ConvertCmd convert = new ConvertCmd();
        convert.run(op);
    }

    /**
     * 给图片加水印
     *
     * @param srcPath
     *            源图片路径
     */
    /*
	 * public static void addImgText(String srcPath, String destPath) throws
	 * Exception { IMOperation op = new IMOperation();
	 * op.font("宋体").gravity("southeast").pointsize(18).fill("#BCBFC8")
	 * .draw("text 5,5 我是水印"); op.addImage(); op.addImage(); ConvertCmd convert
	 * = new ConvertCmd(); convert.run(op, srcPath, destPath); }
	 */
}
