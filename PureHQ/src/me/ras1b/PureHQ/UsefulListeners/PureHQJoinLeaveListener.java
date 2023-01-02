package me.ras1b.PureHQ.UsefulListeners;



import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

import me.ras1b.PureHQ.UsefulMethods.PureHQFileManage;
import me.ras1b.PureHQ.PureHQMain;

public class PureHQJoinLeaveListener implements Listener{
	
	@SuppressWarnings("unused")
	private PureHQMain plugin;

    public PureHQJoinLeaveListener(PureHQMain plugin) {
        this.plugin = plugin;
    }
	
	@SuppressWarnings("deprecation")
	@EventHandler
	public void playerJoinEvent(PlayerJoinEvent event) {
		event.setJoinMessage(null);
		if (event.getPlayer().isOp()){
			for (Player all : Bukkit.getServer().getOnlinePlayers()) {
			      if (all.isOp() && all.getName().toString() != event.getPlayer().getName().toString()) {
			    	  all.sendMessage(ChatColor.GREEN + "[Staff] " + ChatColor.RED + event.getPlayer().getName().toString() +ChatColor.GRAY + " joined the server");
			      }}
		}
		Object obj = PureHQMain.playerData.get("Name." + event.getPlayer().getName());
		if (obj == null){
			PureHQMain.playerData.set("Name." + event.getPlayer().getName() +".money", 200);
			PureHQMain.playerData.set("Name."+event.getPlayer().getName()+".rank", "default");
			PureHQMain.playerData.set("Name." + event.getPlayer().getName() +".lives", 1);
			PureHQMain.playerData.set("Name." + event.getPlayer().getName() +".claimed", false);
			PureHQFileManage.saveYamls();
		}
		
		
		
	}
	
	@SuppressWarnings("deprecation")
	@EventHandler
	public void playerQuitEvent(PlayerQuitEvent event) {
		event.setQuitMessage(null);
		if (event.getPlayer().isOp()){
			for (Player all : Bukkit.getServer().getOnlinePlayers()) {
			      if (all.isOp() && all.getName().toString() != event.getPlayer().getName().toString()) {
			    	  all.sendMessage(ChatColor.GREEN + "[Staff] " + ChatColor.RED + event.getPlayer().getName().toString() +ChatColor.GRAY + " left the server");
			      }}
		}
	}
}
