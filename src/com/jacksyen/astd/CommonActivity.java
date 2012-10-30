package com.jacksyen.astd;

import com.jacksyen.help.GlobalVarable;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;

public class CommonActivity extends Activity{
	
	//广播的内部类，当收到关闭事件时，调用finish方法结束activity  
    private BroadcastReceiver broadcastReceiver = new BroadcastReceiver() {  
        @Override  
        public void onReceive(Context context, Intent intent) {  
//        	if(GlobalVarable.EXIT_ACTION.equals(intent.getAction())){
    		finish();
//        	}
        }
    }; 
    
    @Override  
    public void onResume() {  
        super.onResume();  
        //在当前的activity中注册广播  
        IntentFilter filter = new IntentFilter();  
        filter.addAction("ExitApp");  
        this.registerReceiver(this.broadcastReceiver, filter);  
    }  

}
