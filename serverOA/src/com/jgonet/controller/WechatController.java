package com.jgonet.controller;
import java.io.IOException;
import java.io.PrintWriter;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.jgonet.dao.WechatService;
import com.jgonet.util.SignUtil;
@Controller
@RequestMapping("/wechat")
public class WechatController {
    private static final String DNBX_TOKEN="qinyufeng";//微信的token
    
    private static final Logger LOGGER = Logger.getLogger(WechatController.class);
    
    @Resource
    private WechatService wechatService;
    
    /**
     * 微信接入
     * @param wc
     * @return
     * @throws IOException 
     */
    @RequestMapping(value="/connect.do",method = {RequestMethod.GET, RequestMethod.POST})
    public void connectWeixin(HttpServletRequest request, HttpServletResponse response) throws IOException{
        // 将请求、响应的编码均设置为UTF-8（防止中文乱码）  
        request.setCharacterEncoding("UTF-8");  //微信服务器POST消息时用的是UTF-8编码，在接收时也要用同样的编码，否则中文会乱码；
        response.setCharacterEncoding("UTF-8"); //在响应消息（回复消息给用户）时，也将编码方式设置为UTF-8，原理同上；
        boolean isGet = request.getMethod().toLowerCase().equals("get"); 
      
        PrintWriter out = response.getWriter();
         
        try {
            if (isGet) {
                String signature = request.getParameter("signature");// 微信加密签名  
                String timestamp = request.getParameter("timestamp");// 时间戳  
                String nonce = request.getParameter("nonce");// 随机数  
                String echostr = request.getParameter("echostr");//随机字符串  
                
                // 通过检验signature对请求进行校验，若校验成功则原样返回echostr，表示接入成功，否则接入失败  
                if (SignUtil.checkSignature(DNBX_TOKEN, signature, timestamp, nonce)) {  
                    LOGGER.info("Connect the weixin server is successful.");
                    response.getWriter().write(echostr);  
                } else {  
                    LOGGER.error("Failed to verify the signature!"); 
                }
            }else{
                String respMessage = "异常消息！";
                
                try {
                    respMessage = wechatService.weixinPost(request);
                    out.write(respMessage);
                    LOGGER.info("The request completed successfully");
                    LOGGER.info("to weixin server "+respMessage);
                } catch (Exception e) {
                    LOGGER.error("Failed to convert the message from weixin!"); 
                }
                
            }
        } catch (Exception e) {
            LOGGER.error("Connect the weixin server is error.");
        }finally{
            out.close();
        }
    }
}

