package com.jacksyen.util;

/**
 * 聊天封装实体类
 * @author jacksyen
 *
 */
public class Chat {
	
	private int state;
	
//	private Message message;
	
//	private PlayerUpdateInfo playerUpdateInfo;
	
	private Chats chats;

	public int getState() {
		return state;
	}

	public void setState(int state) {
		this.state = state;
	}

	public Chats getChats() {
		return chats;
	}

	public void setChats(Chats chats) {
		this.chats = chats;
	}
	
	

}
