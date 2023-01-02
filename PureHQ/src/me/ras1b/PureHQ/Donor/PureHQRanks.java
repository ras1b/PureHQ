package me.ras1b.PureHQ.Donor;


import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

import me.ras1b.PureHQ.PureHQMain;
import me.ras1b.PureHQ.UsefulMethods.PureHQFileManage;



public class PureHQRanks {
	
	public static boolean ranks(Player player, String[] args){
		if (args.length == 0 || args.length == 1){
			return tellRank(player, args);
		}else if (!player.isOp()){
			player.sendMessage(ChatColor.RED+ "You don't have permission to perform this command");
			return true;
		}else if (args.length == 2 && args[0].equalsIgnoreCase("add")){
			return addRank(player, args);
		}else if (args.length == 2 && args[0].equalsIgnoreCase("delete")){
			return deleteRank(player, args);
		}else if (args.length == 3 && args[0].equalsIgnoreCase("set")){
			return set(player, args);
		}else if (args.length == 3 && args[0].equalsIgnoreCase("remove")){
			return removePlayer(player, args);
		}else{
			player.sendMessage(ChatColor.RED+ "Command not recognized!");
			return true;
		}
	}
	public static boolean tellRank(Player player, String[] args){
		if (args.length == 0){
			String playerName = player.getName();
			String rank = PureHQMain.playerData.getString("Name."+playerName + ".rank");
			player.sendMessage(ChatColor.GREEN + "Your rank is: " + ChatColor.YELLOW + rank);
		}else{
			String playerName = args[0];
			String rank = PureHQMain.playerData.getString("Name."+playerName + ".rank");
			if (rank != null){
				player.sendMessage(ChatColor.GREEN + playerName + "'s rank is: " + ChatColor.YELLOW + rank);
			}else{
				player.sendMessage(ChatColor.RED + "This player does not exist!");
			}
			
		}
		return true;
	}
	
	public static boolean addRank(Player player, String[] args){
		String rankName = args[1];
		String rankAvailable = PureHQMain.ranks.getString(rankName);
		
		if (rankAvailable == null){
			PureHQMain.ranks.set(rankName + ".name", rankName);
			PureHQFileManage.saveYamls();
			player.sendMessage(ChatColor.GREEN + "Successfully added the rank " + ChatColor.AQUA + rankName);
		}
		else{
			player.sendMessage(ChatColor.RED+ "This rank already exists!");
		}
		return true;
	}
	
	public static boolean deleteRank(Player player, String[] args){
		String rankName = args[1];
		String rankAvailable = PureHQMain.ranks.getString(rankName);
		if (rankAvailable == null){
			player.sendMessage(ChatColor.RED+ "This rank doesn't exists!");
			return true;
		}else{
			PureHQMain.ranks.set(rankName, null);
			PureHQFileManage.saveYamls();
			player.sendMessage(ChatColor.GREEN + "Successfully removed the rank " + ChatColor.AQUA + rankName);
		}
		return true;
	}
	
	@SuppressWarnings("deprecation")
	public static boolean set(Player player, String[] args){
		String rankName = args[2];
		String rankAvailable = PureHQMain.ranks.getString(rankName);
		String playerName = args[1];
		String rank = PureHQMain.playerData.getString("Name."+playerName + ".rank");
		if (rank.equals(rankName)){
			player.sendMessage(ChatColor.RED + "This player already has this rank!");
			return true;
		}
		boolean existance = false;
		for (Player all : Bukkit.getServer().getOnlinePlayers()) {
		      if (all.getName().equals(playerName)) {
		    	  existance = true;
		    	  break;
		      }
		}
		if (!existance){
			player.sendMessage(ChatColor.RED + "This player does not exist!");
		}else if (rankAvailable == null){
			player.sendMessage(ChatColor.RED + "This rank does not exist!");
		}else {
			PureHQMain.playerData.set("Name."+playerName+".rank", rankName);
			PureHQFileManage.saveYamls();
			player.sendMessage(ChatColor.GREEN + "Successfully added " + ChatColor.YELLOW + playerName + ChatColor.GREEN + " to the rank " + ChatColor.YELLOW + rankName);
		}
		
		return true;
	}
	public static boolean removePlayer(Player player, String[] args){
		String rankName = args[2];
		String playerName = args[1];
		String rank = PureHQMain.playerData.getString("Name."+playerName + ".rank");
		if (rank.equals(rankName)){
			PureHQMain.playerData.set("Name."+playerName + ".rank", "default");
			PureHQFileManage.saveYamls();
			player.sendMessage(ChatColor.YELLOW + playerName + ChatColor.GREEN + " was successfully removed from the rank " + ChatColor.YELLOW + rankName);
			return true;
		}else{
			player.sendMessage(ChatColor.RED + "This player doesn't have this rank!");
			return true;
		}
		
	}
}
