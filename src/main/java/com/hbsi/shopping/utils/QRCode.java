package com.hbsi.shopping.utils;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;

import javax.imageio.ImageIO;

import com.baomidou.mybatisplus.core.toolkit.ObjectUtils;
import com.hbsi.shopping.exception.BaseException;
import com.hbsi.shopping.exception.ExceptionEnum;
import com.swetake.util.Qrcode;
import lombok.extern.slf4j.Slf4j;

/**
 * 以QRCode的形式
 *
 * @author lenovo
 */
@Slf4j
public class QRCode {

    //当前项目的路径
    private static  String current;
    //图片名称
    private static  String newImgName;
    //存放商品二维码图片 路径
    private static  String productCodePath;
    private static  String tempPath ;
    private static  String path;


    static {
        //获取当前项目路径 H:\codeFiles\javaCode\eclipse-workspace\shopping-mall
        current = System.getProperty("user.dir");
        //拼接字符串
        tempPath = ".src.main.resources.static.image.productCode";
        // . -> \
        path = tempPath.replace(".", "\\");
        //存放商品二维码图片 路径
        productCodePath = current+path;
    }


    public static void main(String[] args) throws Exception {
        createProductCode("https://baidu.com", "商品id");
    }

    /**
     * 创建商品的二维码
     * 默认大小 10
     * 图片格式png
     *
     * @param context <p>图片内容</p>
     * @throws Exception
     */
    public static String  createProductCode(String context,String imgName) throws Exception {
        if (ObjectUtils.isEmpty(context)){
            log.debug("创建二维码失败");
            throw new BaseException(ExceptionEnum.PRODUCT_INFO_CREATE_CODE_FILED,"context参数错误");
        }
        if (ObjectUtils.isEmpty(imgName)){
            log.debug("创建二维码失败");
            throw new BaseException(ExceptionEnum.PRODUCT_INFO_CREATE_CODE_FILED,"imgName参数错误");
        }
        boolean flag = getPassword(context, imgName, "png", 10);
        if (!flag){
            log.debug("生成商品二维码失败");
            throw new BaseException(ExceptionEnum.PRODUCT_INFO_CREATE_CODE_FILED);
        }
        return  "/static/image/productCode/"+imgName+".png";
    }


    /**
     * 存放二维码
     *
     * @param context 加密的内容
     * @param imgType 图片保存的形式 png?  jpg? gif?
     * @param size    图片大小
     */
    private static boolean  getPassword(String context,String  imgName ,String imgType, int size) throws Exception {
        newImgName =  "/"+imgName+".png";
        System.out.println(productCodePath);
        //如果该文件夹不存在则创建一个该文件夹
        File file = new File(productCodePath);
        if (!file.exists()){
            file.mkdirs();
            log.debug("创建文件夹"+file.getName()+"成功");
        }
        //创建内存中一张图片
        BufferedImage bufImg = createPassword(context, imgType, size);
        //创建图片
        return ImageIO.write(bufImg, imgType, new File(productCodePath+newImgName));

    }


    private static BufferedImage createPassword(String context, String imgType, int size) throws Exception {
        BufferedImage buffer = null;
        Qrcode qrCodeHander = new Qrcode();
        qrCodeHander.setQrcodeErrorCorrect('M');
        qrCodeHander.setQrcodeEncodeMode('B');
        qrCodeHander.setQrcodeVersion(size);
        byte[] contextBytes = context.getBytes("UTF-8");
        boolean[][] codeOut = qrCodeHander.calQrcode(contextBytes);
        int imgSize = 67 + 12 * (size - 1);
        buffer = new BufferedImage(imgSize, imgSize, 1);
        Graphics2D gs = buffer.createGraphics();
        gs.setColor(Color.BLACK);
        gs.setBackground(Color.white);
        gs.clearRect(0, 0, imgSize, imgSize);
        int pixoff = 2;

        for(int i = 0; i < codeOut.length; ++i) {
            for(int j = 0; j < codeOut.length; ++j) {
                if (codeOut[i][j]) {
                    gs.fillRect(j * 3 + pixoff, i * 3 + pixoff, 3, 3);
                }
            }
        }

        return buffer;
    }



}
