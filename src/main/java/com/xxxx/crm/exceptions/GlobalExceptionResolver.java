package com.xxxx.crm.exceptions;

import com.alibaba.fastjson.JSON;
import com.xxxx.base.ResultInfo;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * @Auther:姚泽栋
 * @Date:2023/1/6
 */
@Component
public class GlobalExceptionResolver implements HandlerExceptionResolver {
    @Override
    public ModelAndView resolveException(HttpServletRequest request, HttpServletResponse response,
                                         Object handler, Exception e) {
        // 判断是否是未登录异常
        if (e instanceof NoLoginException) {
            // 重定向到登录页面
            ModelAndView mv = new ModelAndView();
            mv.setViewName("redirect:/index");
            return mv;
        }
        /**
         * 方法返回值判断：
         * 如果方法被@ResponseBody注解修饰，则返回Json格式内容，否则显示错误视图
         */
        ModelAndView mv = new ModelAndView();
        mv.setViewName("error");
        mv.addObject("code", 400);
        mv.addObject("msg", "系统异常，请稍后再试...");
        // 判断方法类型
        if (handler instanceof HandlerMethod) {
            HandlerMethod hd = (HandlerMethod) handler;
            // 获取注解
            ResponseBody responseBody = hd.getMethod().getDeclaredAnnotation(ResponseBody.class);
            if (responseBody != null) {
                // 表示没有@ResponseBody注解
                if (e instanceof ParamsException) {
                    ParamsException p = (ParamsException) e;
                    mv.addObject("code", p.getCode());
                    mv.addObject("msg", p.getMsg());
                } else if (e instanceof AuthException) {
                    AuthException a = (AuthException) e;
                    mv.addObject("code", a.getCode());
                    mv.addObject("msg", a.getMsg());
                }
                return mv;
            } else {
                // 表示有@ResponseBody注解
                ResultInfo resultInfo = new ResultInfo();
                resultInfo.setCode(300);
                resultInfo.setMsg("系统异常，请稍后再试...");
                if (e instanceof ParamsException) {
                    ParamsException p = (ParamsException) e;
                    resultInfo.setCode(p.getCode());
                    resultInfo.setMsg(p.getMsg());
                } else if (e instanceof AuthException) {
                    AuthException a = (AuthException) e;
                    resultInfo.setCode(a.getCode());
                    resultInfo.setMsg(a.getMsg());
                }
                response.setContentType("application/json;charset=utf-8");
                PrintWriter pw = null;
                try {
                    pw = response.getWriter();
                    pw.write(JSON.toJSONString(resultInfo));
                    pw.flush();
                } catch (IOException ex) {
                    throw new RuntimeException(ex);
                } finally {
                    if (pw != null) {
                        pw.close();
                    }
                }
                return null;
            }
        } else {
            return mv;
        }
    }
}
