package com.jgonet.util;

import java.util.Properties;

import org.apache.log4j.Logger;

import com.xwtech.xwecp.service.logic.LIException;
import com.xwtech.xwecp.service.logic.client_impl.common.IRegionQueryService;
import com.xwtech.xwecp.service.logic.client_impl.common.ITransactBusinessService;
import com.xwtech.xwecp.service.logic.client_impl.common.IUserMarketingSerivce;
import com.xwtech.xwecp.service.logic.client_impl.common.IUserSmsVerifyService;
import com.xwtech.xwecp.service.logic.client_impl.common.impl.RegionQueryServiceImpl;
import com.xwtech.xwecp.service.logic.client_impl.common.impl.TransactBusinessServiceClientImpl;
import com.xwtech.xwecp.service.logic.client_impl.common.impl.UserMarketingSerivceImpl;
import com.xwtech.xwecp.service.logic.client_impl.common.impl.UserSmsVerifyServiceImpl;
import com.xwtech.xwecp.service.logic.pojo.DEL010001Result;
import com.xwtech.xwecp.service.logic.pojo.DEL710009Result;
import com.xwtech.xwecp.service.logic.pojo.DEL720019Result;
import com.xwtech.xwecp.service.logic.pojo.QRY040004Result;

public class InvokeEcpUtil {
	private final static Logger log = Logger.getLogger(InvokeEcpUtil.class);
	/**
	 * 业务办理接口
	 * @param mobile 手机号码
	 * @param code 业务代码
	 * @param areacode 区域代码
	 * @return
	 */
	public static DEL010001Result invokeTransactBusiness(String mobile, String code, String areacode)throws Exception{
	    ITransactBusinessService transactBusinessService = new TransactBusinessServiceClientImpl();
	    Long lon = System.currentTimeMillis();
	    DEL010001Result del010001Result = transactBusinessService.transactBusiness(mobile, code, 1, 0, "", "", areacode, "");
	    lon = System.currentTimeMillis()-lon;
	    log.info("The life time of the call interface:invokeTransactBusiness"+lon);
	    
	    return del010001Result;
	  }
	/**
	 * 查询归属地信息
	 * @param mobile 手机号码
	 * @return
	 * @throws Exception
	 */
	public static String phoneNumAttribution(String mobile)throws Exception{
		IRegionQueryService rqs = new RegionQueryServiceImpl();
		Long lon = System.currentTimeMillis();
		QRY040004Result re = rqs.handleBiz(mobile, "");
		lon = System.currentTimeMillis()-lon;
		log.info("The life time of the call interface: phoneNumAttribution"+lon);
		
		if (re != null && "0".equals(re.getResultCode())) {
			return re.getRegionName();
		}
		log.error("Call interface return values 查询归属地信息:"+re.getErrorMessage()+"phoneNum="+mobile);
		return null;
		
		
	}
	/**
	 * 发送手机验证码
	 * @param mobile 手机号码
	 * @return
	 * @throws Exception
	 */
	public static DEL720019Result sendSmsVerify(String mobile)throws Exception{
		IUserSmsVerifyService  userSmsVerifyService= new UserSmsVerifyServiceImpl();
		
		Long lon = System.currentTimeMillis();
		DEL720019Result re = userSmsVerifyService.verifySmsPwd(mobile, "0","");
		lon = System.currentTimeMillis()-lon;
		log.info("The life time of the call interface: sendSmsVerify"+lon);
		
		return re;
		
		
	}
	
	/**
	 * 验证手机验证码
	 * @param mobile
	 * @param checkCode
	 * @return
	 * @throws Exception
	 */
	public static DEL720019Result checkSmsVerify(String mobile,String checkCode)throws Exception{
		IUserSmsVerifyService  userSmsVerifyService= new UserSmsVerifyServiceImpl();
		
		Long lon = System.currentTimeMillis();
		DEL720019Result re = userSmsVerifyService.verifySmsPwd(mobile, "1",checkCode);
		lon = System.currentTimeMillis()-lon;
		log.info("The life time of the call interface: phoneNumAttribution"+lon);
		
		return re;
		
		
	}
	/**
	 *送流量接口
	 * @param mobile 手机号码
	 * @param serviceCode 业务代码
	 * @return
	 * @throws Exception
	 */
	public static DEL710009Result invokeS4035IntCfm(String mobile,String serviceCode)throws Exception{
		IUserMarketingSerivce userMarketingSerivce = new UserMarketingSerivceImpl();
		
		Long lon = System.currentTimeMillis();
		DEL710009Result  re=userMarketingSerivce.s4035IntCfm(mobile, "", serviceCode.substring(0, 4), serviceCode);
		lon = System.currentTimeMillis()-lon;
		log.info("The life time of the call interface: invokeS4035IntCfm"+lon);
		
		return re;
		
		
	}
	/**
	 * 调用ecp，失败则重新调用，最多三次
	 */
	public static boolean invokeAEC0Ecp(String f_phone,String code){
		IUserMarketingSerivce iUserMarketingSerivce = new UserMarketingSerivceImpl();
		DEL710009Result s4035IntCfmRet=null;
		for (int i = 0; i < 3; i++) {
			try {
				s4035IntCfmRet = iUserMarketingSerivce.s4035IntCfm(f_phone,"","AEC0",code);
				if("0".equals(s4035IntCfmRet.getResultCode())){
					return true;
			    }
				System.out.println("ssss="+code+s4035IntCfmRet.getErrorMessage());
			} catch (LIException e) {
				
			}
		}
		return false;
	}
	public static void main(String[] args) {
		//设置ECP
		Properties props = new Properties();
		props.put("client.channel", "scmcc_channel");
		props.put("platform.url", "http://183.221.33.188:33000/scmcc_ecp/xwecp.do");
		props.put("platform.user", "scmcc");
		props.put("platform.password", "scmcc");

		try {
			InvokeEcpUtil.invokeTransactBusiness("18881850395", "WYTHLLB", "14");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
//		System.out.println("AEC09008".substring(0, 4));
	}
}
