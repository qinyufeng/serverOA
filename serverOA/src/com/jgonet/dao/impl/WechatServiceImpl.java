package com.jgonet.dao.impl;

import java.util.Date;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Repository;

import com.jgonet.dao.WechatService;
import com.jgonet.pojo.TextMessage;
import com.jgonet.util.MessageUtil;
@Repository("wechatService")
public class WechatServiceImpl implements WechatService{
	
	private final static Logger LOGGER = Logger.getLogger(WechatServiceImpl.class);
	
	@Override
	public String weixinPost(HttpServletRequest request) {
		String respMessage = null;
        try {

            // xml请求解析
            Map<String, String> requestMap = MessageUtil.xmlToMap(request);
            System.out.println("=====>"+requestMap);
            // 发送方帐号（open_id）
            String fromUserName = requestMap.get("FromUserName");
            // 公众帐号
            String toUserName = requestMap.get("ToUserName");
            // 消息类型
            String msgType = requestMap.get("MsgType");
            // 消息内容
            String content = requestMap.get("Content");
            
            LOGGER.info("FromUserName is:" + fromUserName + ", ToUserName is:" + toUserName + ", MsgType is:" + msgType);

            // 文本消息
            if (msgType.equals(MessageUtil.REQ_MESSAGE_TYPE_TEXT)) {
                //这里根据关键字执行相应的逻辑，只有你想不到的，没有做不到的
                if(content.equals("xxx")){
                    
                }
                
                //自动回复
                TextMessage text = new TextMessage();
                text.setContent("the text is" + content);
                text.setToUserName(fromUserName);
                text.setFromUserName(toUserName);
                text.setCreateTime(new Date().getTime() + "");
                text.setMsgType(msgType);
                
                respMessage = MessageUtil.textMessageToXml(text);
               
            } 
            // 事件推送
            else if (msgType.equals(MessageUtil.REQ_MESSAGE_TYPE_EVENT)) {
                String eventType = requestMap.get("Event");// 事件类型
                // 订阅
                if (eventType.equals(MessageUtil.EVENT_TYPE_SUBSCRIBE)) {
                    
                    TextMessage text = new TextMessage();
                    text.setContent("欢迎关注，xxx");
                    text.setToUserName(fromUserName);
                    text.setFromUserName(toUserName);
                    text.setCreateTime(new Date().getTime() + "");
                    text.setMsgType(MessageUtil.RESP_MESSAGE_TYPE_TEXT);
                    
                    respMessage = MessageUtil.textMessageToXml(text);
                } 
                // TODO 取消订阅后用户再收不到公众号发送的消息，因此不需要回复消息
                else if (eventType.equals(MessageUtil.EVENT_TYPE_UNSUBSCRIBE)) {// 取消订阅
                    
                    
                } 
                // 自定义菜单点击事件
                else if (eventType.equals(MessageUtil.EVENT_TYPE_CLICK)) {
                    String eventKey = requestMap.get("EventKey");// 事件KEY值，与创建自定义菜单时指定的KEY值对应
                    if (eventKey.equals("customer_telephone")) {
                        TextMessage text = new TextMessage();
                        text.setContent("0755-86671980");
                        text.setToUserName(fromUserName);
                        text.setFromUserName(toUserName);
                        text.setCreateTime(new Date().getTime() + "");
                        text.setMsgType(MessageUtil.RESP_MESSAGE_TYPE_TEXT);
                        
                        respMessage = MessageUtil.textMessageToXml(text);
                    }
                }
            }
        }
        catch (Exception e) {
            LOGGER.error("error......");
        }
        return respMessage;
	}

}
