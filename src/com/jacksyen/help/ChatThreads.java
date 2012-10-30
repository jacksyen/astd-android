package com.jacksyen.help;

import java.io.IOException;
import java.util.List;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpGet;

import com.jacksyen.util.Chat;
import com.jacksyen.util.ChatLog;

/**
 * 聊天线程
 * @author jacksyen
 *
 */
public class ChatThreads extends Thread{
	
//	private HttpClient httpclient = null;
//	
//	public ChatThread(){
//		
//	}
//	
//	public ChatThread(HttpClient httpclient){
//		this.httpclient = httpclient;
//	}
	
	private void printLog(List<ChatLog> logList){
		 if(logList!=null && logList.size()>0){
        	for(ChatLog log : logList){
        		System.out.println(log.getName()+":"+log.getContent());
        	}
	      }
	}

	@Override
	public void run() {
		System.out.println("======= 聊天线程启动 =========");
		while(true){
			try {
				String chatUrl = "http://s280.as.yaowan.com/root/chat.action?1338995477861";
				HttpGet mainGet = new HttpGet(chatUrl);
		        HttpResponse gameRes = HttpClientStatic.httpclient.execute(mainGet);
		        System.out.println("chat response statuscode:"+gameRes.getStatusLine().getStatusCode());
				String chatResponse = ResponseHelp.getZLibResponseStr(gameRes.getEntity().getContent());
		        System.out.println("chat response:"+chatResponse);
//		        mainGet.releaseConnection();
		        Chat chat = HttpXmlParser.GetChat(chatResponse);
		        if(chat.getChats()==null){
		        	System.out.println("聊天消息为空");
		        }else{
			        printLog(chat.getChats().getNationChatLog());
			        printLog(chat.getChats().getLegionChatLog());
			        printLog(chat.getChats().getOtherChatLog());
		        }
		        
		        Thread.sleep(12*1000);
			} catch (ClientProtocolException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			
		}
	}

}
