package com.jgonet.util;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Random;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;

import com.xwtec.sso.utils.StringUtil;


/**
 *  @ClassName ImageCodeUtil
 *  @Description 图形验证码
 *
 *	@author xujiancheng@xwtec.cn 
 *  @date 2016年3月28日 下午4:41:47
 * 	@version 1.0.0
 */

public class ChecksumUtil {
	private static final Logger logger = Logger.getLogger(ChecksumUtil.class);
	
	/**
	 * 验证码加密串
	 */
	private static final String KEY = "socket@1024";
	private static final String CODE_KEY = "randImageCode";
	
    private static final String CONTENT_TYPE = "text/html; charset=gb2312";     
    //设置字母的大小,大小     
    private static Font mFont = new Font("Times New Roman", Font.PLAIN, 30); 
    private static Color getRandColor(int fc,int bc){     
        Random random = new Random();     
        if(fc>255) fc=255;     
        if(bc>255) bc=255;     
        int r=fc+random.nextInt(bc-fc);     
        int g=fc+random.nextInt(bc-fc);     
        int b=fc+random.nextInt(bc-fc);     
        return new Color(r,g,b);     
    }  
	/**
	 * 方法描述：验证码
	 * 
	 * @return String
	 * @time 2016年3月29日 上午12:05:01
	 * 
	 */
	private static String randomCode(){
		String code = "";
		Random random = new Random();
//		for(int i=0;i<4;i++){
//			code += random.nextInt(10);
//		}
//		return code;
		char[] codeSequence = { 'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J',         
	            'K', 'L', 'M', 'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W',         
	            'X', 'Y', 'Z', '0', '1', '2', '3', '4', '5', '6', '7', '8', '9' }; 
		for(int i=0; i<4; i++){
			code += String.valueOf(codeSequence[random.nextInt(codeSequence.length)]); 
		}
		return code;
//		return new Random().nextInt(9000)+1000 + "";
	}
	
	/**
	 * 生成校验码
	 * @param request
	 * @param response
	 * @throws IOException
	 * @throws Exception
	 */
	public static void responseCodeImage(HttpServletRequest request,
			HttpServletResponse response) throws IOException,Exception {
		String s = ChecksumUtil.randomCode();
		BufferedImage image = ChecksumUtil.getCodeImage(120, 40, s);
		HttpSession session = request.getSession(true);
		
		session.setAttribute(CODE_KEY, DesUtil.encrypt(s));
		ImageIO.write(image, "JPEG", response.getOutputStream());
	}
	/**
	 * 方法描述：校验验证码（session中的验证码校验）
	 * 
	 * @param request
	 * @param code
	 * @return boolean
	 * @time 2016年3月29日 上午9:54:17
	 * 
	 */
	public static boolean checkCode(HttpServletRequest request, String code)throws IOException,Exception{
		String validateC = (String) request.getSession().getAttribute(CODE_KEY);
		System.out.println("encoding code: " + validateC);
		validateC = DesUtil.decrypt(validateC);
		request.getSession().setAttribute(CODE_KEY, "");
		logger.info("code in session :" + validateC + ";code is " + code);
		System.out.println("code in session :" + validateC + ";code is " + code);
		if(!StringUtil.isNull(validateC)&&validateC.toLowerCase().equals(code.toLowerCase())){
			return true;
		}
		return false;
	}
	private static BufferedImage getCodeImage(int width, int height, String code){
		BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);     
        
        Graphics g = image.getGraphics();     
        Random random = new Random();     
        g.setColor(getRandColor(200,250));     
        g.fillRect(1, 1, width-1, height-1);     
        g.setColor(new Color(102,102,102));     
        g.drawRect(0, 0, width-1, height-1);     
        g.setFont(mFont);     
    
        g.setColor(getRandColor(160,200));     
    
        //画随机线     
        for (int i=0;i<155;i++)     
        {     
            int x = random.nextInt(width - 1);     
            int y = random.nextInt(height - 1);     
            int xl = random.nextInt(6) + 1;     
            int yl = random.nextInt(12) + 1;     
            g.drawLine(x,y,x + xl,y + yl);     
        }     
    
        //从另一方向画随机线     
        for (int i = 0;i < 70;i++)     
        {     
            int x = random.nextInt(width - 1);     
            int y = random.nextInt(height - 1);     
            int xl = random.nextInt(12) + 1;     
            int yl = random.nextInt(6) + 1;     
            g.drawLine(x,y,x - xl,y - yl);     
        }     
    
        //生成随机数,并将随机数字转换为字母     
        for (int i=0;i<code.length();i++)     
        {     
            char ctmp = code.charAt(i);     
            g.setColor(new Color(20+random.nextInt(110),20+random.nextInt(110),20+random.nextInt(110)));     
            g.drawString(String.valueOf(ctmp),20*i+20,30);     
        }
        g.dispose();
        return image;
	}
}
