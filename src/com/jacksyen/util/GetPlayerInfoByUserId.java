package com.jacksyen.util;

import java.util.List;

public class GetPlayerInfoByUserId {
	
	private int state;
	
	private String op;
	
	private List<PlayerInfo> playerInfoList;
	
	private String code;
	
	private int defaultPayOnOff;
	
	public GetPlayerInfoByUserId(){}
	
	public GetPlayerInfoByUserId(int state,String op,List<PlayerInfo> playerInfoLis,
			String code,int defaultPayOnOff){
		this.state = state;
		this.op = op;
		this.playerInfoList = playerInfoList;
		this.code = code;
		this.defaultPayOnOff = defaultPayOnOff;
	}

	public int getState() {
		return state;
	}

	public void setState(int state) {
		this.state = state;
	}

	public String getOp() {
		return op;
	}

	public void setOp(String op) {
		this.op = op;
	}

	public List<PlayerInfo> getPlayerInfoList() {
		return playerInfoList;
	}

	public void setPlayerInfoList(List<PlayerInfo> playerInfoList) {
		this.playerInfoList = playerInfoList;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public int getDefaultPayOnOff() {
		return defaultPayOnOff;
	}

	public void setDefaultPayOnOff(int defaultPayOnOff) {
		this.defaultPayOnOff = defaultPayOnOff;
	}

}
