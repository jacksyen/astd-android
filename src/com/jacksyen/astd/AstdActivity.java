package com.jacksyen.astd;


import com.jacksyen.help.HttpMain;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;


public class AstdActivity extends CommonActivity {
	
	// 登录按钮
	Button loginBtn;
	// 用户名
	EditText txtUserName;
	// 密码
	EditText txtPwd;
	// 服务器ID
	EditText txtServerId;
	// 是否记住密码
	CheckBox cbIsRemember;
	
	// 等待提示框
	ProgressDialog proDialog;
	
	// 开始登录
	OnClickListener onLogin = null;
	
	// 存储对象
	SharedPreferences preferences;  
    SharedPreferences.Editor editor;  
	
    
	/** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
//    	// 如果已经进入游戏了，直接跳转至聊天窗口
//    	if(GlobalVarable.IS_ENTERGAME){
//    		Intent intent=new Intent();
//			intent.setClass(AstdActivity.this, ChatActivity.class);
//			AstdActivity.this.startActivity(intent);
//    		return;
//    	}
        super.onCreate(savedInstanceState);
//        Deaboway appState = (Deaboway)this.getApplication();  
//	    appState.addActivity(this); 
        
        
        
        onLogin = new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// 获得用户名和密码
				String userName = txtUserName.getText().toString();
				String pwd = txtPwd.getText().toString();
				String serverId = txtServerId.getText().toString();
				if(userName==null || userName.equals("") || userName.length()==0){
					AlertDialog.Builder dialog = new AlertDialog.Builder(AstdActivity.this);
					dialog.setTitle("尼玛提示").setIcon(android.R.drawable.ic_dialog_info).setMessage("用户名不能为空").setNegativeButton("确定", 
							new DialogInterface.OnClickListener() {

								@Override
								public void onClick(DialogInterface arg0,
										int arg1) {
									//取消弹出框
								}
					}).create().show();
					return;
				}
				if(pwd==null || pwd.equals("") || pwd.length()==0){
					AlertDialog.Builder dialog = new AlertDialog.Builder(AstdActivity.this);
					dialog.setTitle("尼玛提示").setIcon(android.R.drawable.ic_dialog_info).setMessage("密码不能为空").setNegativeButton("确定", 
							new DialogInterface.OnClickListener() {

								@Override
								public void onClick(DialogInterface arg0,
										int arg1) {
									//取消弹出框
								}
					}).create().show();
					return;
				}
				if(serverId==null || serverId.equals("") || serverId.length()==0){
					AlertDialog.Builder dialog = new AlertDialog.Builder(AstdActivity.this);
					dialog.setTitle("尼玛提示").setIcon(android.R.drawable.ic_dialog_info).setMessage("服务器不能为空").setNegativeButton("确定", 
							new DialogInterface.OnClickListener() {

								@Override
								public void onClick(DialogInterface arg0,
										int arg1) {
									//取消弹出框
								}
					}).create().show();
					return;
				}
				proDialog = ProgressDialog.show(AstdActivity.this, "尼玛提示" , " Loading. Please wait ... ", true); 
				LoadTask loadTask = new LoadTask(userName, pwd, serverId);  
				loadTask.execute();  
			}
		};
		
		setContentView(R.layout.main);
        txtUserName = (EditText)findViewById(R.id.txtUserName);
		txtPwd = (EditText)findViewById(R.id.txtPwd);
		txtServerId = (EditText)findViewById(R.id.txtServerId);
		cbIsRemember = (CheckBox)findViewById(R.id.cbIsRemember);
        // 初始化登录监听器
        loginBtn = (Button)findViewById(R.id.btnLogin);
        loginBtn.setOnClickListener(onLogin);
        
        // 初始化已存储的用户名和密码信息
        // 获取只能被本应用程序读、写的SharedPreferences对象  
        preferences = getSharedPreferences("11",MODE_WORLD_READABLE); 
        String preName = preferences.getString("username", null);
        String prePwd  = preferences.getString("password", null);
        String preServerId = preferences.getString("serverId", null);
        
        System.out.println("preName:"+preName);
        System.out.println("prePwd:"+prePwd);
        
        if(preName!=null){
        	txtUserName.setText(preName);
        }
        if(prePwd!=null){
        	txtPwd.setText(prePwd);
        }
        if(preServerId!=null){
        	txtServerId.setText(preServerId);
        }
    }


    /**
     * 加载框
     * @author jacksyen
     *
     */
    class LoadTask extends AsyncTask<Integer, String, String>{
    	
    	private String userName;
    	private String userPwd;
    	private String serverId;
    	
    	LoadTask(){}
    	
    	LoadTask(String userName, String userPwd, String serverId){
    		this.userName = userName;
    		this.userPwd  = userPwd;
    		this.serverId = serverId;
    	}
    	
    	@Override
		protected void onPreExecute() {
			super.onPreExecute();
		}

		@Override
		protected String doInBackground(Integer... arg0) {
			HttpMain http = new HttpMain();
			publishProgress("开始登录...."); 
			try {
				// 登录
				boolean result = http.onLogin(this.userName,this.userPwd);
				if(!result){
					proDialog.cancel();
					AlertDialog.Builder dialog = new AlertDialog.Builder(AstdActivity.this);
					dialog.setTitle("尼玛提示").setIcon(android.R.drawable.ic_dialog_info).setMessage("登陆失败").setNegativeButton("确定", 
							new DialogInterface.OnClickListener() {

								@Override
								public void onClick(DialogInterface arg0,
										int arg1) {
									//取消弹出框
								}
					}).create().show();
				}
				// 存储用户信息
				boolean isCheck = cbIsRemember.isChecked();
				if(isCheck){
					//获得修改器  
			        editor = preferences.edit();  
			        editor.putString("username", this.userName);
			        editor.putString("password", this.userPwd);
			        editor.putString("serverId", this.serverId);
			        editor.commit();
				}
				// 设置全局变量，表示已经登陆
//				GlobalVarable.IS_ENTERGAME = true;
				publishProgress("登录成功，进入游戏中...."); 
				// 进入游戏
				http.initGame(this.serverId);
			} catch (Exception e) {
				e.printStackTrace();
			}
			return "";
		}
    	
		@Override
		protected void onProgressUpdate(String... values) {
			proDialog.setMessage(values[0]);
			super.onProgressUpdate(values);
		}
		
		@Override  
        protected void onPostExecute(String result) {  
			proDialog.cancel();
			Intent intent=new Intent();
			intent.putExtra("serverId", this.serverId);
			intent.setClass(AstdActivity.this, ChatActivity.class);
			AstdActivity.this.startActivity(intent);
            super.onPostExecute(result);  
        }  
    }
}