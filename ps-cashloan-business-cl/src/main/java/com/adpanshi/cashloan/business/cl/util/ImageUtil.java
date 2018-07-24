package com.adpanshi.cashloan.business.cl.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Random;

public class ImageUtil {
	private final static Logger logger = LoggerFactory.getLogger(ImageUtil.class);
	// 验证码字符集
	private static final char[] CHARS = { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e',
			'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm', 'n', 'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z',
			'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M', 'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U',
			'V', 'W', 'X', 'Y', 'Z' };
	// 字符数量
	private static final int SIZE = 4;
	// 干扰线数量
	private static final int LINES = 5;
	// 宽度
	//private static final int WIDTH = 78;
	// 高度
	//private static final int HEIGHT = 28;
	// 字体大小
	private static final int FONT_SIZE = 32;

	/**
	 * 生成随机验证码及图片 Object[0]：验证码字符串； Object[1]：验证码图片。
	 */
	public static String createImage(int with, int height, OutputStream os) {
		StringBuffer sb = new StringBuffer();
		// 1.创建空白图片
		BufferedImage image = new BufferedImage(with, height, BufferedImage.TYPE_INT_RGB);
		// 2.获取图片画笔
		Graphics graphic = image.getGraphics();
		// 3.设置背景颜色
		graphic.setColor(Color.getColor("backgroud", 15921906));
		// 4.绘制矩形背景
		graphic.fillRect(0, 0, with, height);
		// 5.画随机字符
		Random ran = new Random();
		for (int i = 0; i < SIZE; i++) {
			// 取随机字符索引
			int n = ran.nextInt(CHARS.length);
			// 设置随机颜色
			graphic.setColor(getRandomColor());
			// 设置字体大小
			graphic.setFont(new Font("Times New Roman", Font.BOLD + Font.ITALIC, FONT_SIZE));
			// 画字符
			graphic.drawString(CHARS[n] + "", i * with / SIZE, height * 2 / 3);
			// 记录字符
			sb.append(CHARS[n]);
		}
		// 6.画干扰线
		for (int i = 0; i < LINES; i++) {
			// 设置随机颜色
			graphic.setColor(getRandomColor());
			// 随机画线
			graphic.drawLine(ran.nextInt(with), ran.nextInt(height), ran.nextInt(with), ran.nextInt(height));
		}
		try {
			ImageIO.write(image, "png", os);
			os.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return sb.toString();
	}

	/**
	 * 随机取色
	 */
	public static Color getRandomColor() {
		Random ran = new Random();
		Color color = new Color(ran.nextInt(256), ran.nextInt(256), ran.nextInt(256));
		return color;
	}

	/**
	 * 图片模糊化处理（增加马赛克）
	 * @param file 需要处理的图片文件
	 * @param size 马赛克的颗粒度大小
	 */
	public static BufferedImage mosaic(File file, int size) {
		BufferedImage bi;
		try {
			bi = ImageIO.read(file);// 读取该图片
		} catch (IOException e) {
			logger.info("文件不是图片格式", e);
			return null;
		}
		int imageWidth = bi.getWidth();
		int imageHeight = bi.getHeight();
		BufferedImage spinImage = new BufferedImage(imageWidth, imageHeight, bi.TYPE_INT_RGB);
		//马赛克格尺寸太大或太小
		if (imageWidth < size || imageHeight < size) {
			logger.info("马赛克格尺寸太大");
			return null;
		}
		if (size <= 0) {
			logger.info("马赛克格尺寸太小");
			return null;
		}

		int xcount; // 方向绘制个数
		int ycount; // y方向绘制个数
		if (imageWidth % size == 0) {
			xcount = imageWidth / size;
		} else {
			xcount = imageWidth / size + 1;
		}
		if (imageHeight % size == 0) {
			ycount = imageHeight / size;
		} else {
			ycount = imageHeight / size + 1;
		}
		int x = 0;   //坐标
		int y = 0;
		// 绘制马赛克(绘制矩形并填充颜色)
		Graphics2D gs = spinImage.createGraphics();
		for (int i = 0; i < xcount; i++) {
			for (int j = 0; j < ycount; j++) {
				//马赛克矩形格大小
				int mwidth = size;
				int mheight = size;
				if (i == xcount - 1) {   //横向最后一个比較特殊，可能不够一个size
					mwidth = imageWidth - x;
				}
				if (j == ycount - 1) {  //同理
					mheight = imageHeight - y;
				}
				//矩形颜色取中心像素点RGB值
				int centerX = x;
				int centerY = y;
				if (mwidth % 2 == 0) {
					centerX += mwidth / 2;
				} else {
					centerX += (mwidth - 1) / 2;
				}
				if (mheight % 2 == 0) {
					centerY += mheight / 2;
				} else {
					centerY += (mheight - 1) / 2;
				}
				Color color = new Color(bi.getRGB(centerX, centerY));
				gs.setColor(color);
				gs.fillRect(x, y, mwidth, mheight);
				y = y + size;// 计算下一个矩形的y坐标
			}
			y = 0;// 还原y坐标
			x = x + size;// 计算x坐标
		}
		gs.dispose(); // 释放对象
		return spinImage;
	}

	public static void main(String[] args) throws IOException {
		OutputStream os = new FileOutputStream("d:/1.png");
		createImage(78,28, os);
	}
}
