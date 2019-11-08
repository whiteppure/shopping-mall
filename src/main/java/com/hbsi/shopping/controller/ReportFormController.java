package com.hbsi.shopping.controller;

import com.hbsi.shopping.productinfo.entity.ProductInfo;
import com.hbsi.shopping.productinfo.service.IProductInfoService;
import com.hbsi.shopping.utils.ExcelUtil;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletResponse;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.util.List;

@Controller
@RequestMapping(value = "/excel/")
public class ReportFormController {

    private final IProductInfoService productInfoService;

    public ReportFormController(IProductInfoService productInfoService) {
        this.productInfoService = productInfoService;
    }


    /**
     * 导出报表
     *
     */
    @RequestMapping(value = "export")
    @ResponseBody
    public void export(HttpServletResponse response){
        //获取数据
        List<ProductInfo> infoList = productInfoService.list();

        //excel标题
        String[] title = {"编号", "名称", "库存", "价格", "类型"};

        //excel文件名
        String fileName = "商品信息表" + System.currentTimeMillis() + ".xls";

        //sheet名
        String sheetName = "商品信息表";

        //Excel内容
        String[][] content = new String[infoList.size()][];
        for (int i = 0; i < infoList.size(); i++) {
            content[i] = new String[title.length];
            ProductInfo productInfo = infoList.get(i);
            content[i][0] = productInfo.getProductNum();
            content[i][1] = productInfo.getProductName();
            content[i][2] = productInfo.getProductStock();
            content[i][3] = productInfo.getProductPrice() + "";
            content[i][4] = productInfo.getProductType();
        }

        //创建HSSFWorkbook
        HSSFWorkbook wb = ExcelUtil.getHSSFWorkbook(sheetName, title, content, null);

        //响应到客户端
        try {
            this.setResponseHeader(response, fileName);
            OutputStream os = response.getOutputStream();
            wb.write(os);
            os.flush();
            os.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //发送响应流方法
    private void setResponseHeader(HttpServletResponse response, String fileName) {
        try {
            try {
                fileName = new String(fileName.getBytes(), "ISO8859-1");
            } catch (UnsupportedEncodingException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
            response.setContentType("application/octet-stream;charset=ISO8859-1");
            response.setHeader("Content-Disposition", "attachment;filename=" + fileName);
            response.addHeader("Pargam", "no-cache");
            response.addHeader("Cache-Control", "no-cache");
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}
