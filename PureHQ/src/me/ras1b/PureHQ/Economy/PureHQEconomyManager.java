package me.ras1b.PureHQ.Economy;


import org.bukkit.entity.Player;

import me.ras1b.PureHQ.PureHQMain;
import me.ras1b.PureHQ.UsefulMethods.PureHQFileManage;

public class PureHQEconomyManager {

	@SuppressWarnings("unused")
	private PureHQMain plugin;
	public static int playerMoney = 10000;
    public PureHQEconomyManager(PureHQMain plugin) {
        this.plugin = plugin;
    }

    
	public static int checkMoney(Player player){
		int money = PureHQMain.playerData.getInt("Name." + player.getName() +".money");
		return money;
	}
	
	public static void addMoney(String playername, int money){
		int moneyBanked = PureHQMain.playerData.getInt("Name." + playername +".money");
		moneyBanked = moneyBanked + money;
		PureHQMain.playerData.set("Name." + playername + ".money", moneyBanked);
		PureHQFileManage.saveYamls();
	}
	
	public static boolean removeMoney(String playername, int money){
		int moneyBanked = PureHQMain.playerData.getInt("Name." + playername +".money");
		moneyBanked = moneyBanked - money;
		if (moneyBanked >=0){
			PureHQMain.playerData.set("Name." + playername + ".money", moneyBanked);
			PureHQFileManage.saveYamls();
			return true;
		}else{
			return false;
		}
	}
}
