package com.zoufanqi.utils.imagecode;

import sun.misc.BASE64Encoder;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Random;

/**
 * Created by vanki on 2017/4/5.
 */
public class ImageCodeUtil {
    protected static final Random RANDOM = new Random();
    // 字体样式, 加粗, 斜体...
    private static final int[] FONT_STYLE_ARR = {Font.BOLD, Font.ITALIC + Font.BOLD, Font.PLAIN + Font.ITALIC + Font.BOLD};
    private static final String DEFAULT_STR = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz1234567890";
    private static final String FORMAT_NAME = "PNG";
    // 背景色范围
    private static final int[] RGB_BACKGROUND = new int[]{200, 250};
    private static final int[] RGB_DISTURB = new int[]{1, 255};
    private static final int[] RGB_STRING = new int[]{1, 100};
    // 字体
    private static String[] FONT_NAME_ARR = new String[]{"Default"};
    // 验证码
    private String code = null;
    // 图片的宽度。
    private int width = 140;
    // 图片的高度。
    private int height = 40;
    // 验证码字符个数
    private int codeCount = 4;
    // 验证码干扰线数
    private int lineCount = 20;
    // 验证码图片Buffer
    private BufferedImage buffImg = null;

    public ImageCodeUtil() {
        createImage();
    }

    public ImageCodeUtil(String code) {
        this.code = code;
        if (code != null) this.codeCount = code.length();
        createImage();
    }

    public ImageCodeUtil(int width, int height) {
        this.width = width;
        this.height = height;
        createImage();
    }

    public ImageCodeUtil(int width, int height, String code) {
        this.width = width;
        this.height = height;
        this.code = code;
        if (code != null) this.codeCount = code.length();
        createImage();
    }

    public ImageCodeUtil(int width, int height, String code, int lineCode) {
        this.width = width;
        this.height = height;
        this.code = code;
        this.lineCount = lineCode;
        createImage();
    }

    public ImageCodeUtil(int width, int height, int codeCount) {
        this.width = width;
        this.height = height;
        this.codeCount = codeCount;
        createImage();
    }

    public ImageCodeUtil(int width, int height, int codeCount, int lineCount) {
        this.width = width;
        this.height = height;
        this.codeCount = codeCount;
        this.lineCount = lineCount;
        createImage();
    }

    public static void main(String[] args) throws IOException {
        new ImageCodeUtil("12+343").write("/data/aa.jpeg");
    }

    protected static int getScopeNum(int min, int max) {
        return RANDOM.nextInt(max) % (max - min + 1) + min;
    }

    protected static char getRandomChar(String str) {
        int index = RANDOM.nextInt(str.length());
        return str.charAt(index);
    }

    // 得到随机字符
    protected String drawString() {
        this.code = "";
        int len = DEFAULT_STR.length() - 1;
        double r;
        for (int i = 0; i < this.codeCount; i++) {
            r = (Math.random()) * len;
            this.code = this.code + DEFAULT_STR.charAt((int) r);
        }
        return this.code;
    }

    public void write(String outImgPath) throws IOException {
        ImageIO.write(buffImg, FORMAT_NAME, new File(outImgPath));
    }

    public void write(OutputStream os) throws IOException {
        try {
            ImageIO.write(buffImg, FORMAT_NAME, os);
        } catch (IOException e) {
            throw e;
        } finally {
            if (os != null) os.close();
        }
    }

    /**
     * 图片字节转base64字符, html解码: <img src="data:image/png;base64,这里放BASE64字符"/>
     *
     * @return
     *
     * @throws IOException
     */
    public String writeBASE64() throws IOException {
        ByteArrayOutputStream os = null;
        try {
            os = new ByteArrayOutputStream(2048);
            ImageIO.write(buffImg, FORMAT_NAME, os);
            return new BASE64Encoder().encode(os.toByteArray());
        } catch (IOException e) {
            throw e;
        } finally {
            if (os != null) os.close();
        }
    }

    public BufferedImage getBuffImg() {
        return buffImg;
    }

    public String getCode() {
        return code;
    }

    // 生成图片
    private void createImage() {
        if (this.code == null) {
            this.code = drawString();// 得到随机字符
            this.codeCount = this.code.length();
        }

        int fontWidth = width / codeCount;// 字体的宽度
        int fontHeight = height - 2;// 字体的高度
        int codeY = height - 6;

        // 图像buffer
        buffImg = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
        Graphics g = buffImg.getGraphics();
        //Graphics2D g = buffImg.createGraphics();
        // 设置背景色
        g.setColor(getRandColor(RGB_BACKGROUND[0], RGB_BACKGROUND[1]));
        g.fillRect(0, 0, width, height);

        // 设置字体
        Font font = getFont(fontHeight);
        g.setFont(font);

        // 设置干扰线
        for (int i = 0; i < lineCount; i++) {
            int xs = RANDOM.nextInt(width);
            int ys = RANDOM.nextInt(height);
            int xe = xs + RANDOM.nextInt(width);
            int ye = ys + RANDOM.nextInt(height);
            g.setColor(getRandColor(RGB_DISTURB[0], RGB_DISTURB[1]));
            g.drawLine(xs, ys, xe, ye);
        }

        // 添加噪点
        float yawpRate = 0.01f;// 噪声率
        int area = (int) (yawpRate * width * height);
        for (int i = 0; i < area; i++) {
            int x = RANDOM.nextInt(width);
            int y = RANDOM.nextInt(height);

            buffImg.setRGB(x, y, RANDOM.nextInt(255));
        }

        for (int i = 0; i < this.codeCount; i++) {
            String strRand = this.code.substring(i, i + 1);
            g.setColor(getRandColor(RGB_STRING[0], RGB_STRING[1]));
            // g.drawString(a,x,y);
            // a为要画出来的东西，x和y表示要画的东西最左侧字符的基线位于此图形上下文坐标系的 (x, y) 位置处
            g.drawString(strRand, i * fontWidth + 1, codeY);
        }
    }

    // 得到随机颜色
    private Color getRandColor(int fc, int bc) {// 给定范围获得随机颜色
        if (fc > 255) fc = 255;
        if (bc > 255) bc = 255;
        int r = getScopeNum(fc, bc);
        int g = getScopeNum(fc, bc);
        int b = getScopeNum(fc, bc);
        return new Color(r, g, b);
    }

    /**
     * 产生随机字体
     */
    private Font getFont(int fontSize) {
        initFontNameArr();
        int indexName = RANDOM.nextInt(FONT_NAME_ARR.length);
        int indexStyle = RANDOM.nextInt(FONT_STYLE_ARR.length);
        return new Font(FONT_NAME_ARR[indexName], FONT_STYLE_ARR[indexStyle], fontSize);
    }

    private void initFontNameArr() {
        if (FONT_NAME_ARR != null) return;
        FONT_NAME_ARR = GraphicsEnvironment.getLocalGraphicsEnvironment().getAvailableFontFamilyNames();
    }
}