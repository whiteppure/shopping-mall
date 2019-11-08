package com.hbsi.shopping.utils;


import com.baomidou.mybatisplus.core.toolkit.ObjectUtils;
import com.hbsi.shopping.exception.BaseException;
import com.hbsi.shopping.exception.ExceptionEnum;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

@Slf4j
public class ImageUtils {

    private static final String currentPath;

    private static  String productImgPath;

    static {
        //获取当前路径
        currentPath = System.getProperty("user.dir");
        //路径拼接
        final String path = "\\src\\main\\resources\\static\\image\\productImage\\";
        //存放商品图片的路径
        productImgPath = currentPath + path;
    }


    /**
     * 单图片上传
     *
     * @param upload  图片
     * @param ImgName 图片名称
     * @return 图片路径
     */
    public static String upLoadFile(MultipartFile upload, String ImgName) {

        //判断文件夹是否存在,不存在则创建
        File file = new File(productImgPath);
        if (!file.exists()) file.mkdirs();

        //获取原始图片的扩展名
        String originalFileName = upload.getOriginalFilename();
        //文件名称
        String newFileName = ImgName + originalFileName;
        //文件的路径
        String newFilePath = productImgPath + newFileName;
        try {
            //写入本地
            transferToImg(upload, newFilePath);
            //返回路径 写在数据库里的相对路径
            return "/static/image/productImage/" + newFileName;
        } catch (Exception e) {
            //处理异常
            log.debug("上传用户图片异常");
            throw new BaseException(ExceptionEnum.PRODUCT_INFO_UPLOAD_IMG_FILED, "上传图片失败,请重新上传");
        }

    }

    //指定路径删除图片
    public static boolean deleteImg(String path) {
        File file = new File(currentPath + "\\src\\main\\resources" + path.trim());
        if (!file.exists()){
            log.debug("路径不存在");
            return false;
        }else{
            log.debug("路径存在");
            return  file.delete();
        }
    }

    public static void transferToImg(MultipartFile file, String path) throws IOException {
        file.transferTo(new File(path));//将传来的文件写入新建的文件
    }


    //多图片上传

    /**
     *问题: 删除一张图片上传一张图片 操作多次时有路径问题 待解决
     *
     *
     * @param files      文件
     * @return 文件路径
     */
    public static String[] upLoadFiles(MultipartFile[] files) {
        if (ObjectUtils.isEmpty(files))
            throw new RuntimeException("多文件上传,参数为空");
        String[] pathos = new String[5];

        final String productNo =  ""+Long.parseLong(new SimpleDateFormat("yyyyMMddHHmmss").format(new Date()))*1000;

        //判断文件夹是否存在,不存在则创建
        //  \src\main\resources\static\image\productImage\20190917111225000\20190917111225000_1.png
        productImgPath = productImgPath + productNo + "\\";
        File file = new File(productImgPath);
        if (!file.exists()) file.mkdirs();

        for (int i = 0, filesLength = files.length; i < filesLength; i++) {
            MultipartFile multipartFile = files[i];
            String fileName = productNo + "_" + i + "_" + multipartFile.getOriginalFilename();
            String newFilePath = productImgPath + fileName; //新文件的路径
            try {
                transferToImg(multipartFile, newFilePath);//将传来的文件写入新建的文件
                pathos[i] =  "/static/image/productImage/" +productNo+ "/" + fileName;
            } catch (IOException e) {
                log.error(e.getMessage(), "文件上传异常");
                throw new BaseException(ExceptionEnum.PRODUCT_INFO_UPLOAD_IMG_FILED, "多文件(图片)上传异常");
            }
        }
        return pathos;
    }


    public static void main(String[] args) {
        System.out.println(productImgPath);
    }

}
