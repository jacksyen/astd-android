package com.jacksyen.util;

/**
 * 聊天具体信息实体类
 * @author jacksyen
 *
 */
public class ChatLog {
	
	private String playerId;
	
	private String name;
	
	private String content;
	
	private String time;
	
	private int mark;
	
	public ChatLog(){}
	
	public ChatLog(String playerId,String name,String content,String time,int mark){
		this.playerId = playerId;
		this.name = name;
		this.content = content;
		this.time = time;
		this.mark = mark;
	}

	public String getPlayerId() {
		return playerId;
	}

	public void setPlayerId(String playerId) {
		this.playerId = playerId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getTime() {
		return time;
	}

	public void setTime(String time) {
		this.time = time;
	}

	public int getMark() {
		return mark;
	}

	public void setMark(int mark) {
		this.mark = mark;
	}

}
