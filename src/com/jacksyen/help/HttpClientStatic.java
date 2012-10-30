package com.jacksyen.help;

import org.apache.http.client.HttpClient;
import org.apache.http.client.params.HttpClientParams;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;

public class HttpClientStatic {
	
	public static HttpClient httpclient;
	
	static {
	//  创建  HttpParams  以用来设置  HTTP  参数（这一部分不是必需的）   
        HttpParams params =  new  BasicHttpParams();   

         //  设置连接超时和  Socket  超时，以及  Socket  缓存大小   
        HttpConnectionParams.setConnectionTimeout(params,  50  *  1000 );   
        HttpConnectionParams.setSoTimeout(params,  50  *  1000 );   
        HttpConnectionParams.setSocketBufferSize(params,  8192 );   

         //  设置重定向，缺省为  true   
        HttpClientParams.setRedirecting(params,  false );   
        //  设置  user agent   
//        HttpProtocolParams.setUserAgent(params, userAgent); 
        
		
		httpclient = new DefaultHttpClient(params);
//		httpclient.getParams().setParameter(ClientPNames.COOKIE_POLICY, CookiePolicy.BROWSER_COMPATIBILITY);
	}

}
