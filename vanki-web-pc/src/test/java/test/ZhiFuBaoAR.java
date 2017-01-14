package test;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class ZhiFuBaoAR {
    private String parentPath = "/Users/vanki/Downloads/zhifubao";
    private Graphics2D g = null;

    private static void readPixel(BufferedImage img, int x, int y, int[] pixels) {
        int xStart = x - 1;
        int yStart = y - 1;
        int current = 0;
        for (int i = xStart; i < 3 + xStart; i++)
            for (int j = yStart; j < 3 + yStart; j++) {
                int tx = i;
                if (tx < 0) {
                    tx = -tx;

                } else if (tx >= img.getWidth()) {
                    tx = x;
                }
                int ty = j;
                if (ty < 0) {
                    ty = -ty;
                } else if (ty >= img.getHeight()) {
                    ty = y;
                }
                pixels[current++] = img.getRGB(tx, ty);

            }
    }

    private static void fillMatrix(int[][] matrix, int[] values) {
        int filled = 0;
        for (int i = 0; i < matrix.length; i++) {
            int[] x = matrix[i];
            for (int j = 0; j < x.length; j++) {
                x[j] = values[filled++];
            }
        }
    }

    private static int avgMatrix(int[][] matrix) {
        int r = 0;
        int g = 0;
        int b = 0;
        for (int i = 0; i < matrix.length; i++) {
            int[] x = matrix[i];
            for (int j = 0; j < x.length; j++) {
                if (j == 1) {
                    continue;
                }
                Color c = new Color(x[j]);
                r += c.getRed();
                g += c.getGreen();
                b += c.getBlue();
            }
        }
        return new Color(r / 8, g / 8, b / 8).getRGB();

    }

    public static void main(String[] args) {
        ZhiFuBaoAR ar = new ZhiFuBaoAR();
        ar.start(null);
    }

    /**
     * 导入本地图片到缓冲区
     */
    public BufferedImage loadImageLocal(String imgName) {
        try {
            return ImageIO.read(new File(imgName));
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
        return null;
    }

    /**
     * 生成新图片到本地
     */
    public void writeImageLocal(String newImage, BufferedImage img) {
        if (newImage != null && img != null) {
            try {
                File outputfile = new File(newImage);
                ImageIO.write(img, "jpg", outputfile);
            } catch (IOException e) {
                System.out.println(e.getMessage());
            }
        }
    }

    public BufferedImage merge(BufferedImage b, BufferedImage d) {

        try {
            int w = b.getWidth();
            int h = b.getHeight();

            AlphaComposite ac = AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.5F);

            g = d.createGraphics();
            g.setComposite(ac);
            g.drawImage(b, 0, 6, w, h, null);
            g.dispose();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

        return d;
    }

    public int clamp(int value) {
        return value > 255 ? 255 :(value < 0 ? 0 : value);
    }

    public String start(File fileParent) {
        try {
            if (fileParent == null) fileParent = new File(parentPath);

            if (fileParent.isFile()) fileParent = fileParent.getParentFile();

            File[] fs = fileParent.listFiles();
            if (fs == null) return "ok";

            for (File file : fs) {
                if (file.isDirectory()) {
                    start(file);
                    continue;
                }
                if (file.getName().startsWith(".")) continue;

                String targetPath = file.getAbsolutePath().replace(parentPath, parentPath + "_target");

                File fileTarget = new File(targetPath);
                if (!fileTarget.getParentFile().exists()) {
                    fileTarget.getParentFile().mkdirs();
                }

                ZhiFuBaoAR tt = new ZhiFuBaoAR();
                BufferedImage a = tt.loadImageLocal(file.getAbsolutePath());
                BufferedImage b = a;
                BufferedImage merge = tt.merge(a, b);

                int width = merge.getWidth();
                int height = merge.getHeight();
                // 高斯模糊
                int[][] martrix = new int[3][3];
                int[] values = new int[9];
                for (int i = 0; i < width; i++)
                    for (int j = 0; j < height; j++) {
                        readPixel(merge, i, j, values);
                        fillMatrix(martrix, values);
                        merge.setRGB(i, j, avgMatrix(martrix));
                    }

                tt.writeImageLocal(targetPath, merge);
            }
            return "ok";
        } catch (Exception e) {
            e.printStackTrace();
            return "fail";
        }
    }
} 