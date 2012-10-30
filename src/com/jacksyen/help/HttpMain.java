package com.jacksyen.help;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpHost;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.NameValuePair;
import org.apache.http.client.CookieStore;
import org.apache.http.client.HttpClient;
import org.apache.http.client.RedirectHandler;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.params.ClientPNames;
import org.apache.http.client.params.CookiePolicy;
import org.apache.http.client.params.HttpClientParams;
import org.apache.http.cookie.Cookie;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;
import org.apache.http.protocol.HTTP;
import com.jacksyen.util.GetPlayerInfoByUserId;
import com.jacksyen.util.PlayerInfo;


public class HttpMain {
	
//	private  HttpClient httpclient = null;
//	
//	public HttpClient getHttpclient() {
//		return httpclient;
//	}
//
//	public void setHttpclient(HttpClient httpclient) {
//		this.httpclient = httpclient;
//	}
//
//	public HttpMain(){
//		 //  创建  HttpParams  以用来设置  HTTP  参数（这一部分不是必需的）   
//        HttpParams params =  new  BasicHttpParams();   
//
//         //  设置连接超时和  Socket  超时，以及  Socket  缓存大小   
//        HttpConnectionParams.setConnectionTimeout(params,  50  *  1000 );   
//        HttpConnectionParams.setSoTimeout(params,  50  *  1000 );   
//        HttpConnectionParams.setSocketBufferSize(params,  8192 );   
//
//         //  设置重定向，缺省为  true   
//        HttpClientParams.setRedirecting(params,  false );   
//        //  设置  user agent   
////        HttpProtocolParams.setUserAgent(params, userAgent); 
//        
//		
//		httpclient = new DefaultHttpClient(params);
////		httpclient.getParams().setParameter(ClientPNames.COOKIE_POLICY, CookiePolicy.BROWSER_COMPATIBILITY);
//		
//	}
	
//	public HttpMain(DefaultHttpClient httpclient){
//		httpclient = new DefaultHttpClient();
//		httpclient.getParams().setParameter(ClientPNames.COOKIE_POLICY, CookiePolicy.BROWSER_COMPATIBILITY);
//	}
	
	
	/**
	 * 登录事件
	 * @return
	 * @throws Exception
	 */
	public boolean onLogin(String name,String pwd) throws Exception{
//		DefaultHttpClient httpclient = new DefaultHttpClient();
//		httpclient.getParams().setParameter(ClientPNames.COOKIE_POLICY, CookiePolicy.BROWSER_COMPATIBILITY);
//		HttpHost host = new HttpHost("http://as.yaowan.com");
		
		HttpPost login = new HttpPost("http://as.yaowan.com/?m=user&action=loginform&subdomain=as");
		//建立HttpPost对象
		List<NameValuePair> params=new ArrayList<NameValuePair>();
		//建立一个NameValuePair数组，用于存储欲传送的参数
		params.add(new BasicNameValuePair("username",name));
		params.add(new BasicNameValuePair("password", pwd));
		login.setEntity(new UrlEncodedFormEntity(params,HTTP.UTF_8));
		//设置编码
		HttpResponse response=HttpClientStatic.httpclient.execute(login);
		
		//login.releaseConnection();
		
		int statuscode = response.getStatusLine().getStatusCode();
		System.out.println("response:"+statuscode);
		if(statuscode==HttpStatus.SC_OK){
			String loginResponse = ResponseHelp.getResponseStr(login.getEntity().getContent());
			System.out.println("login response:"+loginResponse);
			return false;
		}
		if ((statuscode == HttpStatus.SC_MOVED_TEMPORARILY) ||
	            (statuscode == HttpStatus.SC_MOVED_PERMANENTLY) ||
	            (statuscode == HttpStatus.SC_SEE_OTHER) ||
	            (statuscode == HttpStatus.SC_TEMPORARY_REDIRECT)){
			String location = response.getFirstHeader("location").getValue();
			System.out.println("location:"+location);
			HttpGet loginRedirect = new HttpGet(location);
			HttpResponse redirectRes = HttpClientStatic.httpclient.execute(loginRedirect);
			InputStream in = redirectRes.getEntity().getContent();
			String loginResponse = ResponseHelp.getResponseStr(in);
			System.out.println("login redirect response:"+loginResponse);
//			loginRedirect.releaseConnection();
			return true;
		}
		return false;
	}
	
	/**
	 * 进入游戏事件
	 * @throws Exception
	 */
	public void initGame(String serverId)  throws Exception{
		System.out.println("=============== redirect game ===============");
		GetAllServer server = new GetAllServer();
		String serverStr = server.getServerStr(serverId);
		
		HttpGet game = new HttpGet("http://as.yaowan.com/Default.php?m=game&game_id=15&district_id=" + serverStr);
		HttpResponse gameRes = HttpClientStatic.httpclient.execute(game);
		System.out.println(gameRes.getStatusLine().getStatusCode());
		
		String startUrl = gameRes.getFirstHeader("location").getValue();
		HttpGet start = new HttpGet(startUrl);
		gameRes = HttpClientStatic.httpclient.execute(start);
		System.out.println(gameRes.getStatusLine().getStatusCode());
		
		String gurl = gameRes.getFirstHeader("location").getValue();
		HttpGet gm = new HttpGet(gurl);
		gameRes = HttpClientStatic.httpclient.execute(gm);
		System.out.println(gameRes.getStatusLine().getStatusCode());
		InputStream in = gameRes.getEntity().getContent();
		String gmResponse = ResponseHelp.getResponseStr(in);
		System.out.println("enter s" + serverId + ".as.yaowan.com response:"+gmResponse);
		
		String headerUrl = "http://s" + serverId + ".as.yaowan.com/";
		
		// ==================== Get serverinfo and playerinfo =================
		String timeurl = headerUrl + "root/server!getServerTime.action?1338955118375";
		HttpGet timeGet = new HttpGet(timeurl);
		gameRes = HttpClientStatic.httpclient.execute(timeGet);
//				timeGet.releaseConnection();
		System.out.println("getServerTime response statuscode :"+gameRes.getStatusLine().getStatusCode());
		String timeResponse = ResponseHelp.getZLibResponseStr(gameRes.getEntity().getContent());
        System.out.println("getServerTime response:"+timeResponse);
        
        String playerurl = headerUrl + "root/server!getPlayerInfoByUserId.action?1338983919148";
        HttpGet playerinfoGet = new HttpGet(playerurl);
		gameRes = HttpClientStatic.httpclient.execute(playerinfoGet);
//				playerinfoGet.releaseConnection();
		System.out.println("getPlayerInfoByUserId response statuscode :"+gameRes.getStatusLine().getStatusCode());
		String playerResponse = ResponseHelp.getZLibResponseStr(gameRes.getEntity().getContent());
        System.out.println("getPlayerInfoByUserId response:"+playerResponse);
        
        GetPlayerInfoByUserId getPlayer = HttpXmlParser.getPlayerInfoToXml(playerResponse);
        // ------------------------ Enter default pay account ------------------
        if(getPlayer.getPlayerInfoList()!=null && getPlayer.getPlayerInfoList().size()>1){
        	for(PlayerInfo info : getPlayer.getPlayerInfoList()){
        		if(info.getDefaultPayPlayer()==1){
        			HttpPost choseRole = new HttpPost(headerUrl + "root/server!chooseRole.action?1338983922991");
        			//建立HttpPost对象
        			List<NameValuePair> choseParams=new ArrayList<NameValuePair>();
        			//建立一个NameValuePair数组，用于存储欲传送的参数
        			choseParams.add(new BasicNameValuePair("code",getPlayer.getCode()));
        			choseParams.add(new BasicNameValuePair("playerId",info.getPlayerId()));
        			choseRole.setEntity(new UrlEncodedFormEntity(choseParams,HTTP.UTF_8));
        			//设置编码
        			HttpResponse choseRes=HttpClientStatic.httpclient.execute(choseRole);
//        			choseRole.releaseConnection();
        			System.out.println("choseRole response statuscode:"+choseRes.getStatusLine().getStatusCode());
        			String choseResponse = ResponseHelp.getZIPResponseStr(choseRole.getEntity().getContent());
        	        System.out.println("choseRole response:"+choseResponse);
        		}
        	}
			 playerurl = headerUrl + "root/server!getPlayerInfoByUserId.action?1338983923097";
			 HttpGet playerinfoGet2 = new HttpGet(playerurl);
			 gameRes = HttpClientStatic.httpclient.execute(playerinfoGet2);
			System.out.println("getPlayerInfoByUserId2 response statuscode :"+gameRes.getStatusLine().getStatusCode());
			playerResponse = ResponseHelp.getZLibResponseStr(gameRes.getEntity().getContent());
			 System.out.println("getPlayerInfoByUserId2 response:"+playerResponse);
//			 playerinfoGet2.releaseConnection();
        }
        
        String extraUrl = headerUrl + "root/server!getExtraInfo.action?1338983923306";
        HttpGet extraGet = new HttpGet(extraUrl);
        gameRes = HttpClientStatic.httpclient.execute(extraGet);
        System.out.println("getExtraInfo response statuscode :"+gameRes.getStatusLine().getStatusCode());
		playerResponse = ResponseHelp.getZLibResponseStr(gameRes.getEntity().getContent());
        System.out.println("getExtraInfo response:"+playerResponse);
//        extraGet.releaseConnection();
        
        String sessionUrl = headerUrl + "root/server!getSessionId.action?1338983923984";
        HttpGet sessionGet = new HttpGet(sessionUrl);
        gameRes = HttpClientStatic.httpclient.execute(sessionGet);
        System.out.println("getSessionId response statuscode :"+gameRes.getStatusLine().getStatusCode());
		playerResponse = ResponseHelp.getZLibResponseStr(gameRes.getEntity().getContent());
        System.out.println("getSessionId response:"+playerResponse);
//        sessionGet.releaseConnection();
        
        String mainUrl = headerUrl + "root/mainCity.action?1338983923992";
        HttpGet mainGet = new HttpGet(mainUrl);
        gameRes = HttpClientStatic.httpclient.execute(mainGet);
        System.out.println("mainCity response statuscode :"+gameRes.getStatusLine().getStatusCode());
		playerResponse = ResponseHelp.getZLibResponseStr(gameRes.getEntity().getContent());
        System.out.println("mainCity response:"+playerResponse);
//        mainGet.releaseConnection();
        
        
        
	}
}
