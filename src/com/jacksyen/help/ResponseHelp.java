package com.jacksyen.help;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.zip.GZIPInputStream;
import java.util.zip.InflaterInputStream;

/**
 * 返回信息帮助类 
 * @author   jacksyen
 * @created  2012.06.06
 *
 */
public class ResponseHelp {

	/**
	 * 读取1024个字节以内的GZIP流数据，返回字符串
	 * @param 	stream
	 * @return
	 */
	public static String getGZIPResponseStr(InputStream stream){
		StringBuffer sb = new StringBuffer();
		 try {
			GZIPInputStream gis = new GZIPInputStream(stream);
			BufferedReader bin = new BufferedReader(new InputStreamReader(gis,
					"UTF-8"));
			String line;
			while ((line = bin.readLine()) != null) {
				sb.append(line);
			}
			bin.close();
		} catch (IOException e) {
			e.printStackTrace();
		}  
		return sb.toString();
	}
	
	public static String getZLibResponseStr(InputStream stream){
		InflaterInputStream iis = new InflaterInputStream(stream);  
        ByteArrayOutputStream o = new ByteArrayOutputStream(1024);  
        try {  
            int i = 1024;  
            byte[] buf = new byte[i];  
  
            while ((i = iis.read(buf, 0, i)) > 0) {  
                o.write(buf, 0, i);  
            }  
  
        } catch (IOException e) {  
            e.printStackTrace();  
        }  
        String result = o.toString();
        if(result!=null){
        	result = result.substring(result.indexOf("<?xml"));
        }
		return result;
	}
	
	public static String getZIPResponseStr(InputStream stream){
		ByteArrayOutputStream o = new ByteArrayOutputStream(1024);  
        try {  
            int i = 1024;  
            byte[] buf = new byte[i];  
  
            while ((i = stream.read(buf, 0, i)) > 0) {  
                o.write(buf, 0, i);  
            }  
  
        } catch (IOException e) {  
            e.printStackTrace();  
        }  
        return o.toString();
	}
	
	
	/**
	 * 读取text/html格式输出流数据，返回字符串
	 * @param stream
	 * @return
	 */
	public static String getResponseStr(InputStream stream){
		StringBuffer sb = new StringBuffer();
		try{
			BufferedReader bin = new BufferedReader(new InputStreamReader(stream,"UTF-8"));
			String line;
			while ((line = bin.readLine()) != null) {
				sb.append(line);
			}
			bin.close();
		}catch(IOException e){
			
		}
		return sb.toString();
	}
	
}
