package com.jacksyen.help;

import java.io.IOException;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.List;

import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.JDOMException;
import org.jdom2.Namespace;
import org.jdom2.input.SAXBuilder;
import org.xml.sax.InputSource;

import com.jacksyen.util.Chat;
import com.jacksyen.util.ChatLog;
import com.jacksyen.util.Chats;
import com.jacksyen.util.GetPlayerInfoByUserId;
import com.jacksyen.util.PlayerInfo;

/**
 * Respons XML字符串解析
 * @author jacksyen
 *
 */
public class HttpXmlParser {
	
	private static List<ChatLog> getChatLog(List<Element> chatLogList){
		List<ChatLog> clList = new ArrayList<ChatLog>();
		for(Element cll : chatLogList){
			if(cll.getName().equals("chatlog")){
				ChatLog chatLog = new ChatLog();
				List<Element> chatL = cll.getChildren();
				for(Element cl : chatL){
					if(cl.getName().equals("playerid")){
						chatLog.setPlayerId(cl.getValue());
					}else if(cl.getName().equals("name")){
						chatLog.setName(cl.getValue());
					}else if(cl.getName().equals("content")){
						chatLog.setContent(cl.getValue());
					}else if(cl.getName().equals("time")){
						chatLog.setTime(cl.getValue());
					}else if(cl.getName().equals("mark")){
						int mark = Integer.parseInt(cl.getValue());
						chatLog.setMark(mark);
					}
				}
				clList.add(chatLog);
			}
		}
		return clList;
	}
	
	
	public static Chat GetChat(String xmlStr){
		Chat chat = new Chat();
		if(xmlStr==null || xmlStr.equals("") || xmlStr.length()==0){
			return chat;
		}
		StringReader read = new StringReader(xmlStr);
		// 创建新的输入源SAX 解析器将使用 InputSource 对象来确定如何读取 XML 输入
		InputSource source = new InputSource(read);
		// 创建一个新的SAXBuilder
		SAXBuilder sb = new SAXBuilder();
		try {
			// 通过输入源构造一个Document
			Document doc = sb.build(source);
			// 取的根元素
			Element root = doc.getRootElement();
			System.out.println(root.getName());// 输出根元素的名称（测试）
			// 获得XML中的命名空间（XML中未定义可不写）
			Namespace ns = root.getNamespace();
			// 得到根元素所有子元素的集合
			List<Element> list = root.getChildren();
			for(Element el : list){
				if (el.getName().equals("state")) {
					int state = Integer.parseInt(el.getValue());
					System.out.println("state:" + state);
					if(state==2){
						return null;
					}
					chat.setState(state);
				}else if(el.getName().equals("message")){
					// TODO
				}else if(el.getName().equals("playerupdateinfo")){
					// TODO
				}else if(el.getName().equals("chats")){
					Chats chats = new Chats();
					List<Element> chatsEl = el.getChildren();
					for(Element csl : chatsEl){
						if(csl.getName().equals("nationchatlog")){
							List<ChatLog> clList = getChatLog(csl.getChildren());
							chats.setNationChatLog(clList);
						}else if(csl.getName().equals("legionchatlog")){
							List<ChatLog> clList = getChatLog(csl.getChildren());
							chats.setLegionChatLog(clList);
						}else{
							List<ChatLog> clList = getChatLog(csl.getChildren());
							chats.setOtherChatLog(clList);
						}
					}
					chat.setChats(chats);
				}
			}
			
		} catch (JDOMException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return chat;
	}

	/**
	 * 登录解析GetPlayerInfoByUserId信息
	 * @param 	xmlStr
	 * @return
	 */
	public static GetPlayerInfoByUserId getPlayerInfoToXml(String xmlStr) {
		GetPlayerInfoByUserId getPlayer = new GetPlayerInfoByUserId();
		if(xmlStr==null || xmlStr.equals("") || xmlStr.length()==0){
			return getPlayer;
		}
		
		StringReader read = new StringReader(xmlStr);
		// 创建新的输入源SAX 解析器将使用 InputSource 对象来确定如何读取 XML 输入
		InputSource source = new InputSource(read);
		// 创建一个新的SAXBuilder
		SAXBuilder sb = new SAXBuilder();
		try {
			// 通过输入源构造一个Document
			Document doc = sb.build(source);
			// 取的根元素
			Element root = doc.getRootElement();
			System.out.println(root.getName());// 输出根元素的名称（测试）
			// 获得XML中的命名空间（XML中未定义可不写）
			Namespace ns = root.getNamespace();
			// 得到根元素所有子元素的集合
			List<Element> list = root.getChildren();
			List<PlayerInfo> playerList = new ArrayList<PlayerInfo>();
			for (Element element : list) {
				if (element.getName().equals("state")) {
					int state = Integer.parseInt(element.getValue());
					System.out.println("state:" + state);
					getPlayer.setState(state);
				} else if (element.getName().equals("op")) {
					String op = element.getValue();
					System.out.println("op:" + op);
					getPlayer.setOp(op);
				} else if (element.getName().equals("player")) {
					PlayerInfo playerInfo = new PlayerInfo();
					String playerId = element.getChildText("playerid");
					playerInfo.setPlayerId(playerId);
					String playerName = element.getChildText("playername");
					playerInfo.setPlayerName(playerName);
					String playerLevel = element.getChildText("playerlevel");
					playerInfo.setPlayerLevel(playerLevel);
					if(element.getChildText("viplv")!=null){
						int vipLv = Integer.parseInt(element.getChildText("viplv"));
						playerInfo.setVipLv(vipLv);
					}
					if(element.getChildText("defaultpayplayer")!=null){
						int defaultPayPlayer = Integer.parseInt(element
								.getChildText("defaultpayplayer"));
						playerInfo.setDefaultPayPlayer(defaultPayPlayer);
					}
					String lastLoginTime = element
							.getChildText("lastlogintime");
					playerInfo.setLastLoginTime(lastLoginTime);
					String pic = element.getChildText("pic");
					playerInfo.setPic(pic);
					if(element.getChildText("returnreward")!=null){
						int returnReward = Integer.parseInt(element
								.getChildText("returnreward"));
						playerInfo.setReturnReward(returnReward);
					}
					playerList.add(playerInfo);
				} else if (element.getName().equals("code")) {
					String code = element.getValue();
					System.out.println("code:" + code);
					getPlayer.setCode(code);
				} else if (element.getName().equals("defaultpayonoff")) {
					int defaultPayOnOff = Integer.parseInt(element.getValue());
					System.out.println("defaultPayOnOff:" + defaultPayOnOff);
					getPlayer.setDefaultPayOnOff(defaultPayOnOff);
				}
			}
			getPlayer.setPlayerInfoList(playerList);
		} catch (JDOMException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return getPlayer;
	}

}
