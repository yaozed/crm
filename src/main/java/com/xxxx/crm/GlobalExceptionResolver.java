package com.xxxx.crm;
import com.alibaba.fastjson.JSON;
import com.xxxx.base.ResultInfo;
import com.xxxx.crm.exceptions.AuthException;
import com.xxxx.crm.exceptions.ParamsException;
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
 *
 * 全局异常统一处理
 */
@Component
public class GlobalExceptionResolver implements HandlerExceptionResolver {
    /**
     * 1返回视图
     * 2返回json数据
     * 如何判断方法的返回值
     * 方法上是否声明@Responsebody注解
     * 如果未声明，返回视图
     * 声明了，则返回数据
     * @param request 请求对象
     * @param response 响应对象
     * @param handler the executed handler, or {@code null} if none chosen at the
     * time of the exception (for example, if multipart resolution failed)
     * @param ex the exception that got thrown during handler execution
     * @return
     */
    @Override
    public ModelAndView resolveException(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) {
ModelAndView modelAndView = new ModelAndView("error");
modelAndView.addObject("code",500);
modelAndView.addObject("msg","系统异常请重试");
//判断HandlerMethod
        if(handler instanceof HandlerMethod){
            HandlerMethod handlerMethod = (HandlerMethod) handler;
            ResponseBody responseBody = handlerMethod.getMethod().getDeclaredAnnotation(ResponseBody.class);
      //判断ResponseBody对象是否为空
            //是空为视图，否则是数据
            if(responseBody==null){
                //方法返回视图
                if(ex instanceof ParamsException){
                    ParamsException p = (ParamsException) ex;
                    modelAndView.addObject("code",p.getCode());
                    modelAndView.addObject("msg",p.getMsg());
                }else if(ex instanceof AuthException){
                    AuthException a = (AuthException) ex;
                    modelAndView.addObject("code",a.getCode());
                    modelAndView.addObject("msg",a.getMsg());
                }
                return modelAndView;
            }else{
                //返回数据
                ResultInfo resultInfo = new ResultInfo();
                resultInfo.setCode(500);
                resultInfo.setMsg("异常请重试!");
                //判断异常类型是否为自定义异常
                if(ex instanceof ParamsException){
                    ParamsException p = (ParamsException) ex;
                    resultInfo.setCode(p.getCode());
                    resultInfo.setMsg(p.getMsg());
                }else if(ex instanceof AuthException){
                    AuthException a = (AuthException) ex;
                    resultInfo.setCode(a.getCode());
                    resultInfo.setMsg(a.getMsg());
                }
                //设置响应类型和编码格式
                response.setContentType("application/json;charset=UTF-8");
                PrintWriter out = null;
                try{
                    out = response.getWriter();
                    //将对象转换成json格式字符串
                    String json = JSON.toJSONString(resultInfo);
                    //输出数据
                    out.write(json);
                }catch (IOException e) {
                      e.printStackTrace();
                }finally {
                    //如果对象不为空，则关闭
                    if(out != null){
out.close();
                    }
                }
                return null;
            }
        }
return modelAndView;
    }
    }

