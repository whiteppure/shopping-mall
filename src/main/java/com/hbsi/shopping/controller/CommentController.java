package com.hbsi.shopping.controller;

import io.swagger.annotations.Api;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import javax.servlet.http.HttpServletRequest;

@Slf4j
@Controller
@Api(tags = "页面controller")
public class CommentController {



    /**
     * 退出登录销毁session
     * @param request
     * @return
     */
    @GetMapping("/loginOut")
    public  String loginOut( HttpServletRequest request){
        try {
            request.getSession().invalidate();
            log.debug("用户退出成功,销毁session成功!");
            return "redirect:/shopping-mall/login";
        } catch (Exception e) {
            log.debug(e.getMessage(),"用户退出失败,销毁session失败!");
            return "/error/500";
        }

    }

}
