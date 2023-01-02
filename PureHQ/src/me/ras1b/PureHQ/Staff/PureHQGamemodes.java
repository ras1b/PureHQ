package me.ras1b.PureHQ.Staff;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.GameMode;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class PureHQGamemodes {
	
	//Change to gamemode 0 (survival)
		@SuppressWarnings("deprecation")
		public static boolean gms(CommandSender sender){
			if (!sender.isOp()){
				sender.sendMessage(ChatColor.RED+ "You don't have permission to perform this command");
			}
			else{
				for (Player all : Bukkit.getServer().getOnlinePlayers()) {
				      if (all.isOp() && all.getName().toString() != sender.getName().toString()) {
				    	  all.sendMessage(ChatColor.RED + sender.getName().toString() +ChatColor.GRAY + " changed to gamemode " + ChatColor.GREEN+ "survival");
				      }}
				((Player) sender).setGameMode(GameMode.SURVIVAL);
			sender.sendMessage(ChatColor.GRAY + "Your gamemode has been updated to "+ ChatColor.GREEN +"survival");
			}
			return true;
		}
	
	//Change to gamemode 1 (creative)
	@SuppressWarnings("deprecation")
	public static boolean gmc(CommandSender sender){
		if (!sender.isOp()){
			sender.sendMessage(ChatColor.RED+ "You don't have permissionn to perform this command");
		}
		else{
			for (Player all : Bukkit.getServer().getOnlinePlayers()) {
			      if (all.isOp() && all.getName().toString() != sender.getName().toString()) {
			    	  all.sendMessage(ChatColor.RED + sender.getName().toString() +ChatColor.GRAY + " changed to gamemode " + ChatColor.GREEN+ "creative");
			      }}
			((Player) sender).setGameMode(GameMode.CREATIVE);
			sender.sendMessage(ChatColor.GRAY + "Your gamemode has been updated to "+ ChatColor.GREEN +"creative");
	
		}
		return true;

	}
	
	
	//Change to gamemode 2 (adventure)
	@SuppressWarnings("deprecation")
	public static boolean gma(CommandSender sender){
		if (!sender.isOp()){
			sender.sendMessage(ChatColor.RED+ "You don't have permissionn to perform this command");
		}
		else{
			for (Player all : Bukkit.getServer().getOnlinePlayers()) {
			      if (all.isOp() && all.getName().toString() != sender.getName().toString()) {
			    	  all.sendMessage(ChatColor.RED + sender.getName().toString() +ChatColor.GRAY + " changed to gamemode " + ChatColor.GREEN+ "adventure");
			      }}
		((Player) sender).setGameMode(GameMode.ADVENTURE);
		sender.sendMessage(ChatColor.GRAY + "Your gamemode has been updated to "+ ChatColor.GREEN +"adventure");
		
		}
		return true;
	}
	
	//Change gamemode to 0,1,2 (survival, creative, adventure)
	@SuppressWarnings("deprecation")
	public static boolean gm(CommandSender sender, String[] args){
		if (!sender.isOp()){
			sender.sendMessage(ChatColor.RED+ "You don't have permissionn to perform this command");
		}
		else{
			
		
		if (args.length > 1 || args.length == 0){
			sender.sendMessage(ChatColor.RED + "Only one argument allowed!");
		}
		else{
			if (Integer.parseInt(args[0]) == 1){
				for (Player all : Bukkit.getServer().getOnlinePlayers()) {
				      if (all.isOp() && all.getName().toString() != sender.getName().toString()) {
				    	  all.sendMessage(ChatColor.RED + sender.getName().toString() +ChatColor.GRAY + " changed to gamemode " + ChatColor.GREEN+ "creative");
				      }}
				((Player) sender).setGameMode(GameMode.CREATIVE);
				sender.sendMessage(ChatColor.GRAY + "Your gamemode has been updated to "+ ChatColor.GREEN +"creative");
			}
			else if (Integer.parseInt(args[0]) == 0){
				for (Player all : Bukkit.getServer().getOnlinePlayers()) {
				      if (all.isOp() && all.getName().toString() != sender.getName().toString()) {
				    	  all.sendMessage(ChatColor.RED + sender.getName().toString() +ChatColor.GRAY + " changed to gamemode " + ChatColor.GREEN+ "survival");
				      }}
				((Player) sender).setGameMode(GameMode.SURVIVAL);
				sender.sendMessage(ChatColor.GRAY + "Your gamemode has been updated to "+ ChatColor.GREEN +"survival");
			}
			else if (Integer.parseInt(args[0]) == 2){
				for (Player all : Bukkit.getServer().getOnlinePlayers()) {
				      if (all.isOp() && all.getName().toString() != sender.getName().toString()) {
				    	  all.sendMessage(ChatColor.RED + sender.getName().toString() +ChatColor.GRAY + " changed to gamemode " + ChatColor.GREEN+ "adventure");
				      }}
				((Player) sender).setGameMode(GameMode.ADVENTURE);
				sender.sendMessage(ChatColor.GRAY + "Your gamemode has been updated to "+ ChatColor.GREEN +"adventure");
			}
			else{
				sender.sendMessage(ChatColor.RED + "Only arguments [0,1,2] are allowed!");
			}
		}
		}
		return true;
	}
	
	
	//endclass
}
