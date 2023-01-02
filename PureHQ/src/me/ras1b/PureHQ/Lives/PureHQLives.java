package me.ras1b.PureHQ.Lives;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import me.ras1b.PureHQ.UsefulMethods.PureHQFileManage;
import me.ras1b.PureHQ.PureHQMain;

public class PureHQLives {

	public static boolean livesMain(CommandSender player, String[] args){
		if (player instanceof Player){
			if (!player.isOp()){
				player.sendMessage(ChatColor.RED+ "You don't have permission to perform this command");
				return true;
			}else if (args.length == 2){
				if (args[0].equalsIgnoreCase("revive") ) return PureHQLives.revive((Player)player,args);
			}else if (args.length == 3){
				if (args[0].equalsIgnoreCase("addlives")|| args[0].equalsIgnoreCase("add")) return PureHQLives.addLives((Player)player,args);
				if (args[0].equalsIgnoreCase("removelives")|| args[0].equalsIgnoreCase("remove")) return PureHQLives.removeLives((Player)player,args);
			}else{
				player.sendMessage(ChatColor.RED + "Command not recognized!");
			}
		}else{
			Player player2 = Bukkit.getPlayer(args[1]);
			if (player2 != null){
				if (args[0].equalsIgnoreCase("addlives")|| args[0].equalsIgnoreCase("add")) return PureHQLives.addLives(player2,args);
				if (args[0].equalsIgnoreCase("removelives")|| args[0].equalsIgnoreCase("remove")) return PureHQLives.removeLives(player2,args);
			}
		}
		return true;
	}
	
	@SuppressWarnings("deprecation")
	public static boolean revive(Player player, String[] args){
		OfflinePlayer[] playerToRevive = Bukkit.getOfflinePlayers();
		for (OfflinePlayer p : playerToRevive){
			if (p.getName().equals(args[1])){
				p.setBanned(false);
				player.sendMessage(ChatColor.GREEN + "Successfully revived " + ChatColor.GRAY + args[1]);
				return true;
			}
		}
		player.sendMessage(ChatColor.RED + "Player is not banned or not recognized!");
		return true;
	}
	
	public static boolean addLives(Player player, String[] args){
		String amount = args[2];
		if (!amount.matches("[0-9]+")){
			player.sendMessage(ChatColor.RED + "Amount must be a number!");
			return true;
		}
		String playername = args[1];
		int lives = PureHQMain.playerData.getInt("Name." + playername + ".lives");
		lives = lives + Integer.parseInt(amount);
		player.sendMessage(ChatColor.GREEN + "Successfully added " + ChatColor.RED + amount + ChatColor.GREEN + " lives to " + ChatColor.RED + playername);
		PureHQMain.playerData.set("Name." + playername + ".lives", lives);
		PureHQFileManage.saveYamls();
		return true;
	}
	
	public static boolean removeLives(Player player, String[] args){
		String amount = args[2];
		if (!amount.matches("[0-9]+")){
			player.sendMessage(ChatColor.RED + "Amount must be a number!");
			return true;
		}
		String playername = args[1];
		int lives = PureHQMain.playerData.getInt("Name." + playername + ".lives");
		lives = lives - Integer.parseInt(amount);
		if (lives < 1){
			player.sendMessage(ChatColor.RED + "Remaining lives should be 1 or more!");	
		}else{
			PureHQMain.playerData.set("Name." + playername + ".lives", lives);
			PureHQFileManage.saveYamls();
			player.sendMessage(ChatColor.GREEN + "Successfully removed " + ChatColor.GRAY + amount + " lives" + ChatColor.GREEN + " from " + ChatColor.GRAY + playername);
		}
		return true;
	}
}
