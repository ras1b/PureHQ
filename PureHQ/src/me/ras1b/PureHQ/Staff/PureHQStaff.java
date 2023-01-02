package me.ras1b.PureHQ.Staff;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import me.ras1b.PureHQ.PureHQMain;


public class PureHQStaff {

	
	
	public static List<String> StaffChatMembers = new ArrayList<String>();
	public static boolean staffchat(CommandSender sender, String[] args, PureHQMain plugin){
		if (!sender.isOp()){
			sender.sendMessage(ChatColor.RED + "You don't have permission to perform this command!");
		}
		else{
			if (args.length > 1 || args.length == 0){
				sender.sendMessage(ChatColor.RED + "Only one argument allowed!");
			}
			else{
				if (args[0].equalsIgnoreCase("on")){
					sender.sendMessage(ChatColor.AQUA + "You are now in staff chat");
					plugin.getConfig().set("Name.Staffchat." + sender.getName().toString(),true);
					plugin.saveConfig();
					plugin.reloadConfig();
					
				}
				else if (args[0].equalsIgnoreCase("off")){
					sender.sendMessage(ChatColor.AQUA + "You are now out of staff chat");
					plugin.getConfig().set("Name.Staffchat." + sender.getName().toString(),false);
					plugin.saveConfig();
					plugin.reloadConfig();
				}
			}
			
		}
		
		return true;
	}
	
	@SuppressWarnings("deprecation")
	public static boolean heal(Player player, String[] args){
		if (args.length == 0){
			player.setHealth(20);
			player.sendMessage(ChatColor.GREEN + "You have been healed!");
			return true;
		}else if (args.length == 1){
			String playername = args[0];
			for (Player all : Bukkit.getServer().getOnlinePlayers()) {
			      if (all.getName().equals(playername)) {
			    	  all.setHealth(20);
			    	  player.sendMessage(ChatColor.RED + all.getName() + ChatColor.GREEN + " has been healed!");
			      }}
			return true;
		}else{
			player.sendMessage(ChatColor.RED + "Only one argument allowed!");
			return true;
		}
	}
	@SuppressWarnings("deprecation")
	public static boolean feed(Player player, String[] args){
		if (args.length == 0){
			player.setFoodLevel(20);
			player.setSaturation(5);
			player.sendMessage(ChatColor.GREEN + "You have been fed!");
			return true;
		}else if (args.length == 1){
			String playername = args[0];
			for (Player all : Bukkit.getServer().getOnlinePlayers()) {
			      if (all.getName().equals(playername)) {
			    	  all.setFoodLevel(20);
			    	  all.setSaturation(5);
			    	  player.sendMessage(ChatColor.RED + all.getName() + ChatColor.GREEN + " has been fed!");
			      }}
			return true;
		}else{
			player.sendMessage(ChatColor.RED + "Only one argument allowed!");
			return true;
		}
	}
	
	@SuppressWarnings("deprecation")
	public static boolean clearinventory(Player player, String[] args){
		if (!player.isOp()){
			player.sendMessage(ChatColor.RED + "You do not have permission to perform this command");
			return true;
		}
		if (args.length > 1){
			player.sendMessage(ChatColor.RED + "Usage: clearinventory name");
		}else if (args.length == 0){
			player.getInventory().clear();
			player.updateInventory();
			player.sendMessage(ChatColor.GREEN + "Successfully cleared your inventory");
		}else{
			String playername = args[0];
			for (Player all : Bukkit.getServer().getOnlinePlayers()) {
			      if (all.getName().toString().equals(playername)) {
			    	  all.getInventory().clear();
			    	  all.updateInventory();
			    	  player.sendMessage(ChatColor.GREEN + "Successfully clear the inventory of " + ChatColor.RED + playername);
			      }      
			}
		}
		
		
		return true;
	}
}
