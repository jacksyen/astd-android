package com.jacksyen.util;

import java.util.List;

/**
 * 聊天信息集合实体类
 * @author jacksyen
 *
 */
public class Chats {
	
	/**
	 * 国家消息集合
	 */
	private List<ChatLog> nationChatLog;
	
	/**
	 * 军团消息集合
	 */
	private List<ChatLog> legionChatLog;

	/**
	 * 其它消息集合(地区、附近、等等)
	 */
	private List<ChatLog> otherChatLog;
	
	public List<ChatLog> getNationChatLog() {
		return nationChatLog;
	}

	public void setNationChatLog(List<ChatLog> nationChatLog) {
		this.nationChatLog = nationChatLog;
	}

	public List<ChatLog> getLegionChatLog() {
		return legionChatLog;
	}

	public void setLegionChatLog(List<ChatLog> legionChatLog) {
		this.legionChatLog = legionChatLog;
	}

	public List<ChatLog> getOtherChatLog() {
		return otherChatLog;
	}

	public void setOtherChatLog(List<ChatLog> otherChatLog) {
		this.otherChatLog = otherChatLog;
	}
	
}
