package com.jacksyen.astd;

import java.io.IOException;
import java.util.List;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpGet;

import com.jacksyen.help.HttpClientStatic;
import com.jacksyen.help.HttpXmlParser;
import com.jacksyen.help.ResponseHelp;
import com.jacksyen.util.Chat;
import com.jacksyen.util.ChatLog;
import com.jacksyen.util.Chats;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.TextView;

/**
 * 聊天挂机窗口
 * @author 		jacksyen
 * @created 	2012.06.07
 *
 */
public class ChatActivity extends CommonActivity{
	
	// 用户名
	TextView tvUserName;
	// 等级
	TextView tvUserLv;

	// 聊天消息信息
	EditText txtChatInfo;
	
	// 开启聊天线程
	ChatT chat;


	@Override
	/**
	 * 返回事件
	 */
	public void onBackPressed() {
		Intent i= new Intent(Intent.ACTION_MAIN);
		i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
		i.addCategory(Intent.CATEGORY_HOME);
		startActivity(i);
	}

	@Override
	/**
	 * 创建菜单
	 */
	public boolean onCreateOptionsMenu(Menu menu) {
		menu.add(Menu.NONE, Menu.FIRST + 1, 1, "退出").setIcon(android.R.drawable.ic_menu_close_clear_cancel);
		menu.add(Menu.NONE, Menu.FIRST + 2, 2, "帮助").setIcon(android.R.drawable.ic_menu_help);
		return true;
	}

	public void close() {
		Intent intent = new Intent();
		intent.setAction("ExitApp");
		this.sendBroadcast(intent);
		super.finish();
	}
	
	
	
	@Override
	protected void onDestroy() {
		super.onDestroy();
		System.exit(0);
	}

	@Override
	/**
	 * 为菜单项注册事件
	 */
	public boolean onOptionsItemSelected(MenuItem item) {
		switch(item.getItemId()){
		case Menu.FIRST+1:
			close();
			// 退出事件
//			Deaboway appState = (Deaboway)getApplicationContext();  
//			appState.finishAll();
			// 关闭聊天线程
//			chat.cancel(true);
//			getApplicationContext().sendBroadcast(new Intent(GlobalVarable.EXIT_ACTION));
//			System.exit(0);
//			Intent intent = new Intent();  
//			intent.setAction(GlobalVarable.EXIT_ACTION); // 退出动作  
//			this.sendBroadcast(intent);// 发送广播
			//super.finish();
//			System.exit(0);
			break;
		case Menu.FIRST+2:
			break;
		}
		return false;
	}


	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
//		Deaboway appState = (Deaboway)this.getApplication();  
//	    appState.addActivity(this); 
		setContentView(R.layout.chat);
		
		txtChatInfo = (EditText)findViewById(R.id.txtChatInfo);
		tvUserName = (TextView)findViewById(R.id.txtUserName);
		tvUserLv = (TextView)findViewById(R.id.txtUserLv);
		
		Intent i = getIntent();
		
		// 开启聊天线程
		chat = new ChatT(i.getStringExtra("serverId"));
		chat.execute(12*1000);
//		ChatThread chat = new ChatThread();
//        chat.start();
	}
	
	
	class ChatT extends AsyncTask<Integer, Chats, String>{
		
//		private void printLog(List<ChatLog> logList){
//			if(logList!=null && logList.size()>0){
//				for(ChatLog log : logList){	
//					String temp = txtChatInfo.getText().toString();
//					System.out.println("temp:"+temp);
//					txtChatInfo.setText(temp + log.getName()+":"+log.getContent() +"\n");
////					txtChatInfo.setSelection(txtChatInfo.length());
//					System.out.println(log.getName()+":"+log.getContent());
//				}	
//		    }
//		}
		private String serverId;
		
		public ChatT(){
			
		}
		public ChatT(String serverId){
			this.serverId = serverId;
		}
		
		@Override
		protected void onPreExecute() {
			super.onPreExecute();
		}
		
		@Override
		protected String doInBackground(Integer... params) {
			while(true){
				try {
					String chatUrl = "http://s" + this.serverId + ".as.yaowan.com/root/chat.action?1338995477861";
					HttpGet mainGet = new HttpGet(chatUrl);
			        HttpResponse gameRes = HttpClientStatic.httpclient.execute(mainGet);
			        System.out.println("chat response statuscode:"+gameRes.getStatusLine().getStatusCode());
					String chatResponse = ResponseHelp.getZLibResponseStr(gameRes.getEntity().getContent());
			        System.out.println("chat response:"+chatResponse);
//			        mainGet.releaseConnection();
			        Chat chat = HttpXmlParser.GetChat(chatResponse);
			        // chat为null时表示账号在别处登陆
			        if(chat==null){
			        	return "quit";
			        }
			        if(chat.getChats()==null){
			        	System.out.println("聊天消息为空");
			        }else{
			        	publishProgress(chat.getChats());
			        }
			        Thread.sleep(params[0]);
				} catch (ClientProtocolException e) {
					e.printStackTrace();
				} catch (IOException e) {
					e.printStackTrace();
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		}

		@Override
		protected void onProgressUpdate(Chats... values) {
			List<ChatLog> logList = values[0].getNationChatLog();
	        if(logList!=null && logList.size()>0){
				for(ChatLog log : logList){
					String temp = txtChatInfo.getText().toString();
					if(temp.length()>20*10){
						temp = temp.substring(50,temp.length());
					}
					txtChatInfo.setText(temp + log.getName()+":"+log.getContent() +"\n");
					txtChatInfo.setSelection(txtChatInfo.length());
				}	
		    }
	        logList = values[0].getLegionChatLog();
	        if(logList!=null && logList.size()>0){
	        	txtChatInfo.setTextColor(0xff9999CC);
				for(ChatLog log : logList){
					String temp = txtChatInfo.getText().toString();
					if(temp.length()>20*10){
						temp = temp.substring(50,temp.length());
					}
					txtChatInfo.setText(temp + log.getName()+":"+log.getContent() +"\n");
					txtChatInfo.setSelection(txtChatInfo.length());
				}	
		    }
	        logList = values[0].getOtherChatLog();
	        if(logList!=null && logList.size()>0){
				for(ChatLog log : logList){
					String temp = txtChatInfo.getText().toString();
					if(temp.length()>20*10){
						temp = temp.substring(50,temp.length());
					}
					txtChatInfo.setText(temp + log.getName()+":"+log.getContent() +"\n");
					txtChatInfo.setSelection(txtChatInfo.length());
				}	
		    }
			super.onProgressUpdate(values);
		}
		
		@Override
		protected void onPostExecute(String result) {
			if(result.equals("quit")){
				// 设置全局变量，表示退出登陆
//				GlobalVarable.IS_ENTERGAME = false;
				// 退出程序
				AlertDialog.Builder dialog = new AlertDialog.Builder(ChatActivity.this);
				dialog.setTitle("尼玛提示").setIcon(android.R.drawable.ic_dialog_info).setMessage("嘿嘿，你的帐号已经在别处登录鸟~").setPositiveButton(
						"退出", new DialogInterface.OnClickListener() {
							
							@Override
							public void onClick(DialogInterface dialog, int which) {
								close();
							}
						}).setNegativeButton("返回", 
						new DialogInterface.OnClickListener() {

							@Override
							public void onClick(DialogInterface arg0,
									int arg1) {
								//取消弹出框
								System.exit(0);
							}
				}).create().show();
				
			}
			super.onPostExecute(result);
		}
	}

}
