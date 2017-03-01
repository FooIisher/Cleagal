package com.ynkj.common.util;

import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileOutputStream;
import java.util.ResourceBundle;

import javax.imageio.ImageIO;

import com.sun.image.codec.jpeg.JPEGCodec;
import com.sun.image.codec.jpeg.JPEGImageEncoder;
/**
 * 图片转换工具
 * @author xuweiming
 *
 */
public class GraphicsConvert {

	public final static String typea = ResourceBundle.getBundle("system").getString("picconvert.a");
	public final static String typeb = ResourceBundle.getBundle("system").getString("picconvert.b");
	public final static String typec = ResourceBundle.getBundle("system").getString("picconvert.c");
	public final static String typed = ResourceBundle.getBundle("system").getString("picconvert.d");
	public final static String typee = ResourceBundle.getBundle("system").getString("picconvert.e");
	// 图片宽和高的最大尺寸
	public static final int IMAGEMAXBIG = 2000;
	// 图片宽和高的最小尺寸
	public static final int IMAGEMINBIG = 10;
	// 按原图大小生成新图
	public static final int CREATENEWIMAGETYPE_0 = 0;
	// 按指定的大小生成新图
	public static final int CREATENEWIMAGETYPE_1 = 1;
	// 按原图宽高比例生成新图-按指定的宽度
	public static final int CREATENEWIMAGETYPE_2 = 2;
	// 按原图宽高比例生成新图-按指定的高度
	public static final int CREATENEWIMAGETYPE_3 = 3;
	// 按原图宽高比例生成新图-按指定的宽和高中较大的尺寸
	public static final int CREATENEWIMAGETYPE_4 = 4;
	// 按原图宽高比例生成新图-按指定的宽和高中较小的尺寸
	public static final int CREATENEWIMAGETYPE_5 = 5;

	
	public static void main(String[] args) {
		String fname = "/Users/xuweiming/Pictures/mycartoon.jpg";
		File f = new File("/Users/xuweiming/Pictures/mycartoon.jpg");
		try {
			GraphicsConvert.covertpic(fname);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public static boolean covertpic(String filepath){
		try {
			String[] arr = GraphicsConvert.typea.split(",");
			GraphicsConvert.createNewImage(new File(filepath), GraphicsConvert.CREATENEWIMAGETYPE_1, Integer.valueOf(arr[0]), Integer.valueOf(arr[1]));
			arr = GraphicsConvert.typeb.split(",");
			GraphicsConvert.createNewImage(new File(filepath), GraphicsConvert.CREATENEWIMAGETYPE_1, Integer.valueOf(arr[0]), Integer.valueOf(arr[1]));
			arr = GraphicsConvert.typec.split(",");
			GraphicsConvert.createNewImage(new File(filepath), GraphicsConvert.CREATENEWIMAGETYPE_1, Integer.valueOf(arr[0]), Integer.valueOf(arr[1]));
			arr = GraphicsConvert.typed.split(",");
			GraphicsConvert.createNewImage(new File(filepath), GraphicsConvert.CREATENEWIMAGETYPE_1, Integer.valueOf(arr[0]), Integer.valueOf(arr[1]));
			arr = GraphicsConvert.typee.split(",");
			GraphicsConvert.createNewImage(new File(filepath), GraphicsConvert.CREATENEWIMAGETYPE_1, Integer.valueOf(arr[0]), Integer.valueOf(arr[1]));
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}
	/**
	 * 
	 * @param _file
	 *            原图片
	 * @param createType
	 *            处理类型
	 * @param newW
	 *            新宽度
	 * @param newH
	 *            新高度
	 * @return
	 * @throws Exception
	 */
	public static String createNewImage(File _file, int createType, int newW,
			int newH) throws Exception {
		if (_file == null)
			return null;
		String fileName = _file.getPath();
		if (fileName == null || "".equals(fileName)
				|| fileName.lastIndexOf(".") == -1)
			return null;
		String newFileName = "_"+newW+"_"+newH;
		/*
		 * else newFileName = "_" + newFileName;
		 */

		String outFileName = fileName.substring(0, fileName.lastIndexOf("."))
				+ newFileName
				+ fileName.substring(fileName.lastIndexOf("."), fileName
						.length());
		String fileExtName = fileName.substring(
				(fileName.lastIndexOf(".") + 1), fileName.length());
		if (newW < IMAGEMINBIG)
			newW = IMAGEMINBIG;
		else if (newW > IMAGEMAXBIG)
			newW = IMAGEMAXBIG;

		if (newH < IMAGEMINBIG)
			newH = IMAGEMINBIG;
		else if (newH > IMAGEMAXBIG)
			newH = IMAGEMAXBIG;

		// 得到原图信息
		if (!_file.exists() || !_file.isAbsolute() || !_file.isFile()
				|| !checkImageFile(fileExtName))
			return null;
		if ((new File(outFileName)).exists()) {
			System.out.println("file [" + outFileName + "] already exists");
			throw new Exception();
		}
		Image src = ImageIO.read(_file);
		int w = src.getWidth(null);
		int h = src.getHeight(null);

		// 确定目标图片的大小
		int nw = w;
		int nh = h;
		if (createType == CREATENEWIMAGETYPE_0)
			;
		else if (createType == CREATENEWIMAGETYPE_1) {
			nw = newW;
			nh = newH;
		} else if (createType == CREATENEWIMAGETYPE_2) {
			nw = newW;
			nh = (int) ((double) h / (double) w * nw);
		} else if (createType == CREATENEWIMAGETYPE_3) {
			nh = newH;
			nw = (int) ((double) w / (double) h * nh);
		} else if (createType == CREATENEWIMAGETYPE_4) {
			if ((double) w / (double) h >= (double) newW / (double) newH) {
				nh = newH;
				nw = (int) ((double) w / (double) h * nh);
			} else {
				nw = newW;
				nh = (int) ((double) h / (double) w * nw);
			}
		} else if (createType == CREATENEWIMAGETYPE_5) {
			if ((double) w / (double) h <= (double) newW / (double) newH) {
				nh = newH;
				nw = (int) ((double) w / (double) h * nh);
			} else {
				nw = newW;
				nh = (int) ((double) h / (double) w * nw);
			}
		}

		// 构造目标图片
		BufferedImage tag = new BufferedImage(nw, nh,
				BufferedImage.TYPE_INT_RGB);

		// 得到目标图片输出流
		FileOutputStream out = new FileOutputStream(outFileName);

		// 根据需求画出目标图片 方式1
		tag.getGraphics().drawImage(src, 0, 0, nw, nh, null);

		// 将画好的目标图输出到输出流 方式1
		JPEGImageEncoder encoder = JPEGCodec.createJPEGEncoder(out);
		encoder.encode(tag);
		out.close();
		return outFileName;
	}

	public static boolean checkImageFile(String extName) {

		if ("jpg".equalsIgnoreCase(extName))
			return true;
		if ("gif".equalsIgnoreCase(extName))
			return true;
		if ("bmp".equalsIgnoreCase(extName))
			return true;
		if ("jpeg".equalsIgnoreCase(extName))
			return true;
		if ("png".equalsIgnoreCase(extName))
			return true;
		return false;
	}

}

