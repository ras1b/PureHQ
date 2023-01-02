package me.ras1b.PureHQ.Staff;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.entity.Player;



public class PureHQTeleport {
	@SuppressWarnings("deprecation")
	public static boolean tphere(Player player, String[] args){
		if (!player.isOp()){
			player.sendMessage(ChatColor.RED + "You don't have permission to perform this command");
			return true;
		}
		if (args.length == 1){
			String playername = args[0];
			for (Player all : Bukkit.getServer().getOnlinePlayers()) {
			      if (all.getName().toString().equals(playername)) {
			    	  Location l = player.getLocation();
			    	  all.teleport(l);
			    	  player.sendMessage(ChatColor.GREEN + "Successfully teleported "+ ChatColor.RED + all.getName() + ChatColor.GREEN + " to you!");
			    	  return true;
			      }
			}
			player.sendMessage(ChatColor.RED + "Invalid player name");
		}else{
			player.sendMessage(ChatColor.RED + "Usage: tphere name");
			return true;
		}
		return true;
	}
	
	@SuppressWarnings("deprecation")
	public static boolean tpall(Player player, String[] args){
		if (!player.isOp()){
			player.sendMessage(ChatColor.RED + "You don't have permission to perform this command");
			return true;
		}
		if (args.length > 0){
			player.sendMessage(ChatColor.RED + "Usage: tpall");
		}else{
			Location l = player.getLocation();
			for (Player all : Bukkit.getServer().getOnlinePlayers()) {
				all.teleport(l);
				
			}
			player.sendMessage(ChatColor.GREEN + "Successfully teleported everyone!");
			return true;
		}
		player.sendMessage(ChatColor.RED + "Invalid action");
		return true;
	}
	
	@SuppressWarnings("deprecation")
	public static boolean tp(Player player, String[] args){
		if (!player.isOp()){
			player.sendMessage(ChatColor.RED + "You don't have permission to perform this command");
			return true;
		}
		if (args.length == 1){
			for (Player all : Bukkit.getServer().getOnlinePlayers()) {
			    if (all.getName() == args[0]){
			    	Location l = all.getLocation();
			    	player.teleport(l);
			    	
				    	player.sendMessage(ChatColor.GREEN + "Successfully teleported to "+ ChatColor.RED + all.getName() + ChatColor.GREEN + "!");
				    	return true;
			    }
			}
			player.sendMessage(ChatColor.RED + "Invalid player name");
			return true;
		}else if (args.length == 2){
			if (args[0].equals(args[1])){
				player.sendMessage(ChatColor.RED + "Cannot teleport to the same player!");
				return true;
			}
			Player p = null;
			Player p2 = null;
			for (Player all : Bukkit.getServer().getOnlinePlayers()) {
			    if (all.getName().equals(args[0])){
			    	p = all;
			    	break;
			    }
			}
			for (Player all : Bukkit.getServer().getOnlinePlayers()) {
			    if (all.getName().equals(args[1])){
			    	p2 = all;
			    	break;
			    }
			}
			if (p != null && p2 != null){
				Location l = p2.getLocation();
				p.teleport(l);
				
		    	player.sendMessage(ChatColor.GREEN + "Successfully teleported " + ChatColor.RED + p.getName() + ChatColor.GREEN + " to " + ChatColor.RED + p2.getName());
		    	return true;
			}else{
				player.sendMessage(ChatColor.RED + "Error! Usage: tp playername playername");
			}
		}
		else{
			player.sendMessage(ChatColor.RED + "Usage: tp playername playername");
		}
		return true;
	}
	
	
	
	
}
