/**
 * 生成随机数字或字母串，以图像方式显示，用于人工识别，使程序很难识别。 
 *  减小系统被程序自动攻击的可能性。
 *  生成的图形颜色由红、黑、蓝、紫4中随机组合而成，数字或字母垂直方向位置在
 *  一定范围内也是随机的，减少被程序自动识别的几率。
 *  由于数字的0，1，2易和字母的o，l,z混淆，使人眼难以识别，因此不生成数字
 *  和字母的混合串。
 *  生成的串字母统一用小写，串的最大长度为16。
 *
 * @作者:黄勇$
 *
 */
package com.ynkj.common.util;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Random;

import javax.imageio.ImageIO;

import org.apache.commons.lang.RandomStringUtils;

public class RandomGraphic {
	// 字符的高度和宽度，单位为像素
	private int wordHeight = 8;
	private int wordWidth = 15;
	// 字符大小
	private int fontSize = 18;
	// 最大字符串个数
	private static final int MAX_CHARCOUNT = 16;

	// 垂直方向起始位置
	private final int initypos = 5;

	// 要生成的字符个数，由工厂方法得到
	private int charCount = 4;
	private int lineCount = 0;

	// 颜色数组，绘制字串时随机选择一个
	// private static final Color[] CHAR_COLOR =
	// {Color.RED,Color.BLUE,Color.GREEN,Color.MAGENTA};
	private static final Color[] CHAR_COLOR = { Color.BLUE };

	// 随机数生成器
	private Random r = new Random();

	/**
	 * 生成图像的格式常量，JPEG格式,生成为文件时扩展名为.jpg； 输出到页面时需要设置MIME type 为image/jpeg
	 */
	public static String GRAPHIC_JPEG = "JPEG";
	/**
	 * 生成图像的格式常量，PNG格式,生成为文件时扩展名为.png； 输出到页面时需要设置MIME type 为image/png
	 */
	public static String GRAPHIC_PNG = "PNG";

	// 用工厂方法创建对象
	protected RandomGraphic(int charCount) {
		this.charCount = charCount;
	}

	// 用工厂方法创建对象
	protected RandomGraphic(int charCount, int lineCount) {
		this.charCount = charCount;
		this.lineCount = lineCount;
	}

	/**
	 * 创建对象的工厂方法
	 * 
	 * @param charCount
	 *            要生成的字符个数，个数在1到16之间
	 * 
	 *            Return 返回RandomGraphic对象实例
	 * @throws Exception
	 *             参数charCount错误时抛出
	 */
	public static RandomGraphic createInstance(int charCount) throws Exception {
		if (charCount < 1 || charCount > MAX_CHARCOUNT) {
			throw new Exception("Invalid parameter charCount,charCount should between in 1 and 16");
		}
		return new RandomGraphic(charCount);
	}

	/**
	 * 创建对象的工厂方法
	 * 
	 * @param charCount
	 *            要生成的字符个数，个数在1到16之间
	 * 
	 *            Return 返回RandomGraphic对象实例
	 * @throws Exception
	 *             参数charCount错误时抛出
	 */
	public static RandomGraphic createInstance(int charCount, int lineCount) throws Exception {
		return new RandomGraphic(charCount, lineCount);
	}

	/**
	 * 随机生成一个数字串，并以图像方式绘制，绘制结果输出到流out中
	 * 
	 * @param graphicFormat
	 *            设置生成的图像格式，值为GRAPHIC_JPEG或GRAPHIC_PNG
	 * @param out
	 *            图像结果输出流
	 * @return 随机生成的串的值
	 * @throws IOException
	 */
	public String drawNumber(String graphicFormat, OutputStream out) throws IOException {
		// 随机生成的串的值
		String charValue = "";
		charValue = randNumber();
		return draw(charValue, graphicFormat, out);
	}

	/**
	 * 随机生成一个字母串，并以图像方式绘制，绘制结果输出到流out中
	 * 
	 * @param graphicFormat
	 *            设置生成的图像格式，值为GRAPHIC_JPEG或GRAPHIC_PNG
	 * @param out
	 *            图像结果输出流
	 * @return 随机生成的串的值
	 * @throws IOException
	 */
	public String drawAlpha(String graphicFormat, OutputStream out) throws IOException {
		// 随机生成的串的值
		String charValue = "";
		charValue = randAlpha();
		return draw(charValue, graphicFormat, out);
	}

	/**
	 * 随机生成一个字母数字混合串，并以图像方式绘制，绘制结果输出到流out中
	 * 
	 * @param graphicFormat
	 * @param out
	 * @return
	 * @throws IOException
	 */
	public String drawAlphaNumber(String graphicFormat, OutputStream out) throws IOException {
		// 随机生成的串的值
		String charValue = "";
		charValue = randAlphaNumber();
		return draw(charValue, graphicFormat, out);
	}

	/**
	 * 重载drawAlphaNumber方法，可以过滤过指定的字串<br>
	 * 随机生成一个字母数字混合串，并以图像方式绘制，绘制结果输出到流out中
	 * 
	 * @author fanzhen 2011-8-14 可以过滤掉指定的字串，
	 * @param graphicFormat
	 * @param out
	 * @param filter
	 *            [] 需要过滤掉的字串
	 * @return
	 * @throws IOException
	 */
	public String drawAlphaNumber(String graphicFormat, OutputStream out, String filter) throws IOException {
		// 随机生成的串的值
		String charValue = "";
		charValue = randAlphaNumber(filter);
		return draw(charValue, graphicFormat, out);
	}

	/**
	 * 以图像方式绘制字符串，绘制结果输出到流out中
	 * 
	 * @param charValue
	 *            要绘制的字符串
	 * @param graphicFormat
	 *            设置生成的图像格式，值为GRAPHIC_JPEG或GRAPHIC_PNG
	 * @param out
	 *            图像结果输出流
	 * @return 随机生成的串的值
	 * @throws IOException
	 */
	protected String draw(String charValue, String graphicFormat, OutputStream out) throws IOException {

		// 计算图像的宽度和高度
		int w = (charCount + 2) * wordWidth;
		int h = wordHeight * 3;

		// 创建内存图像区
		BufferedImage bi = new BufferedImage(w, h, BufferedImage.TYPE_3BYTE_BGR);
		Graphics2D g = bi.createGraphics();

		// 设置背景色
		Color backColor = Color.WHITE;
		g.setBackground(backColor);
		g.fillRect(0, 0, w, h);

		// 随机产生lineCount条干扰线，使图象中的认证码不易被其它程序探测到
		// 生成随机类
		Random random = new Random();
		for (int i = 0; i < lineCount; i++) {
			int x = random.nextInt(w);
			int y = random.nextInt(h);
			int xl = random.nextInt(3);
			int yl = random.nextInt(3);
			Color color = CHAR_COLOR[randomInt(0, CHAR_COLOR.length)];
			g.setColor(color);
			g.drawLine(x, y, x + xl, y + yl);
		}

		// 设置font
		g.setFont(new Font("Tahoma", Font.BOLD, fontSize));
		// 绘制charValue,每个字符颜色随机
		for (int i = 0; i < charCount; i++) {
			String c = charValue.substring(i, i + 1);
			Color color = CHAR_COLOR[randomInt(0, CHAR_COLOR.length)];
			g.setColor(color);
			int xpos = (i + 1) * wordWidth;
			// 垂直方向上随机
			int ypos = randomInt(initypos + wordHeight, initypos + wordHeight * 2);
			g.drawString(c, xpos, ypos);
		}

		g.dispose();
		bi.flush();
		// 输出到流
		ImageIO.write(bi, graphicFormat, out);

		return charValue;
	}

	protected String randNumber() {
		String charValue = "";
		// 生成随机数字串
		for (int i = 0; i < charCount; i++) {
			charValue += String.valueOf(randomInt(0, 10));
		}
		return charValue;
	}

	private String randAlpha() {
		String charValue = "";
		// 生成随机字母串
		for (int i = 0; i < charCount; i++) {
			char c = (char) (randomInt(0, 26) + 'A');
			charValue += String.valueOf(c);
		}
		return charValue;
	}

	/**
	 * 随机字母与数字组合
	 * 
	 * @return
	 */
	public String randAlphaNumber() {
		return RandomStringUtils.randomAlphanumeric(charCount).toUpperCase();
	}

	/**
	 * 重载randAlphaNumber方法，可以过滤过指定的字串<br>
	 * 随机字母与数字组合，当随机出来的字串含有filter中指定的，则重新随机生成新的，最多20次机会
	 * 
	 * @author fanzhen 2011-8-14 可以过滤掉指定的字串
	 * @return
	 */
	public String randAlphaNumber(String filter) {
		int iCount = 20;
		String temp = "";

		// 最多随机20次重新生成新的机会
		while (iCount > 0) {
			temp = RandomStringUtils.randomAlphanumeric(charCount).toUpperCase();

			boolean isInclude = false;
			for (int i = 0; i < temp.length(); i++) {
				if (filter.indexOf(temp.charAt(i)) != -1) {
					isInclude = true;
				}
			}
			iCount--;

			if (!isInclude) {
				break;
			}
		}
		return temp;
	}

	/**
	 * 返回[from,to)之间的一个随机整数
	 * 
	 * @param from
	 *            起始值
	 * @param to
	 *            结束值
	 * @return [from,to)之间的一个随机整数
	 */
	protected int randomInt(int from, int to) {
		// Random r = new Random();
		return from + r.nextInt(to - from);
	}

	/**
	 * 给定范围获得随机颜色
	 * 
	 * @param fc
	 * @param bc
	 * @return
	 */
	public Color getRandColor(int fc, int bc) {
		Random random = new Random();
		if (fc > 255) {
			fc = 255;
		}
		if (bc > 255) {
			bc = 255;
		}
		int r = fc + random.nextInt(bc - fc);
		int g = fc + random.nextInt(bc - fc);
		int b = fc + random.nextInt(bc - fc);
		return new Color(r, g, b);
	}

	/**
	 * @param args
	 * @throws Exception
	 */
	public static void main(String[] args) throws Exception {
		System.out.println(RandomGraphic.createInstance(4).drawAlpha(RandomGraphic.GRAPHIC_JPEG,
				new FileOutputStream("c:/myimg.png")));
	}
}