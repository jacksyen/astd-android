package com.jacksyen.help;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class GetAllServer {
	
	private static Properties properties;
	
	static{
		properties = new Properties();
		try {
			InputStream in =  GetAllServer.class.getResourceAsStream("/assets/server.properties");
			properties.load(in);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	/**
	 * 获取登录服务器ID
	 * @param serverId
	 * @return
	 */
	public String getServerStr(String serverId){
		String result = properties.getProperty(serverId);
		if(result==null || result.equals("") || result.length()==0){
			//TODO 读取在线服务器列表
			return null;
		}
		return result;
	}

}
