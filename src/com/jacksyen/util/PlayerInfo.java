package com.jacksyen.util;

/**
 * 玩家信息实体类
 * @author jacksyen
 *
 */
public class PlayerInfo {
	
	private String playerId;
	
	private String playerName;
	
	private String playerLevel;
	
	private int vipLv;
	
	private int defaultPayPlayer;
	
	private String lastLoginTime;
	
	private String pic;
	
	private int returnReward;
	
	public PlayerInfo(){}
	
	public PlayerInfo(String playerId,String playerName,String playerLevel,int vipLv,int defaultPayPlayer,String lastLoginTime,
			String pic,int returnReward){
		this.playerId = playerId;
		this.playerName = playerName;
		this.playerLevel = playerLevel;
		this.vipLv = vipLv;
		this.defaultPayPlayer = defaultPayPlayer;
		this.lastLoginTime = lastLoginTime;
		this.pic = pic;
		this.returnReward = returnReward;
	}

	public String getPlayerId() {
		return playerId;
	}

	public void setPlayerId(String playerId) {
		this.playerId = playerId;
	}

	public String getPlayerName() {
		return playerName;
	}

	public void setPlayerName(String playerName) {
		this.playerName = playerName;
	}

	public String getPlayerLevel() {
		return playerLevel;
	}

	public void setPlayerLevel(String playerLevel) {
		this.playerLevel = playerLevel;
	}

	public int getVipLv() {
		return vipLv;
	}

	public void setVipLv(int vipLv) {
		this.vipLv = vipLv;
	}

	public int getDefaultPayPlayer() {
		return defaultPayPlayer;
	}

	public void setDefaultPayPlayer(int defaultPayPlayer) {
		this.defaultPayPlayer = defaultPayPlayer;
	}

	public String getLastLoginTime() {
		return lastLoginTime;
	}

	public void setLastLoginTime(String lastLoginTime) {
		this.lastLoginTime = lastLoginTime;
	}

	public String getPic() {
		return pic;
	}

	public void setPic(String pic) {
		this.pic = pic;
	}

	public int getReturnReward() {
		return returnReward;
	}

	public void setReturnReward(int returnReward) {
		this.returnReward = returnReward;
	}

}
