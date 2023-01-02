package me.ras1b.PureHQ.Staff;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;

import me.ras1b.PureHQ.PureHQMain;

public class PureHQChatListener implements Listener{

	private PureHQMain plugin;

    public PureHQChatListener(PureHQMain plugin) {
        this.plugin = plugin;
    }
	
	
	
	@SuppressWarnings("deprecation")
	@EventHandler
	public void playerChatEvent(AsyncPlayerChatEvent event) {
			String playerName = event.getPlayer().getName();
			boolean x = this.plugin.getConfig().getBoolean("Name.Staffchat." +playerName);
			if (x == true){
				String chat = event.getMessage();
				StringBuilder sb = new StringBuilder();
				sb.append(ChatColor.GREEN + "[StaffChat] " + playerName + " > ");
				sb.append(ChatColor.WHITE + chat);
				for (Player all : Bukkit.getServer().getOnlinePlayers()) {
					if (all.isOp()) {
					all.sendMessage(sb.toString());
					}
				}
				event.setCancelled(true);
			}
	}
	
}
