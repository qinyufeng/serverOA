package com.jgonet.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;

public class ReadUtil {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		
		File file = new File("C:/Users/ShiYing/Desktop/1.txt");
//		File out = new File("C:/Users/ShiYing/Desktop/2.txt");
		Reader  in = null;
//		Writer os = null;
        try {
            // 一次读一个字节
            in = new InputStreamReader(new FileInputStream(file));
//            os = new OutputStreamWriter(new FileOutputStream(out));
            int tempbyte;
            int i = 0;
            while ((tempbyte = in.read()) != -1) {
//               os.write(((char)tempbyte));
               if(((char)tempbyte) == ' '){
//            	   os.write("\n");
//            	   System.out.print(","+(++i));
//            	   System.out.println();
 //           	   System.out.println();
            	   continue;
               }else if(((char)tempbyte) == '\r'){
            	   continue;
               }
               else{
            	   System.out.print(((char)tempbyte));
               }
            }
            in.close();
        } catch (IOException e) {
            e.printStackTrace();
            return;
        }finally{
        	if (in != null) {
                try {
                    in.close();
                } catch (IOException e1) {
                }
            }
        }
	}

}
