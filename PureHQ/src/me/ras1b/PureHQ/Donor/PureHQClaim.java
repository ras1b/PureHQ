package me.ras1b.PureHQ.Donor;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import me.ras1b.PureHQ.PureHQMain;
import me.ras1b.PureHQ.UsefulMethods.PureHQFileManage;
import me.ras1b.PureHQ.UsefulMethods.PureHQStrings;
import net.md_5.bungee.api.ChatColor;

public class PureHQClaim {
	public static boolean claimMain(Player player, String[] args){
		if (args.length == 0){
			return claim(player,args);
		}else if (args.length == 4 && (args[0].equalsIgnoreCase("addkeys")|| args[0].equalsIgnoreCase("addkey"))){
			return claimAddKeys(player, args);
		}else if (args.length == 4 && (args[0].equalsIgnoreCase("removekeys")|| args[0].equalsIgnoreCase("removekey"))){
			return claimRemoveKeys(player, args);
		}else if (args.length == 3 && (args[0].equalsIgnoreCase("addlives")|| args[0].equalsIgnoreCase("addlife"))){
			return claimAddLives(player, args);
		}else if (args.length == 3 && (args[0].equalsIgnoreCase("removelives")|| args[0].equalsIgnoreCase("removelife"))){
			return claimRemoveLives(player, args);
			
		}else{
			return true;
		}
		
	}
	
	public static boolean claim(Player player, String[] args){
		String playerName = player.getName();
		String rank = PureHQMain.playerData.getString("Name." + playerName + ".rank");
		String rankAvailable = PureHQMain.ranks.getString(rank);
		if (rankAvailable == null){
			player.sendMessage(PureHQStrings.RANK_DOESNT_EXIST);
			return true;
		}
		boolean claimedAlready = PureHQMain.playerData.getBoolean("Name." + playerName + ".claimed");
		
		if (!claimedAlready){
			List<String> commands = PureHQMain.ranks.getStringList(rank + ".commands");
			if (commands == null){
				player.sendMessage(ChatColor.RED + "This rank does not have claims!");
				return true;
			}else{
				for (String s : commands){
					String s2 = s.replace("{player}", player.getName());
					Bukkit.getServer().dispatchCommand(Bukkit.getConsoleSender(), s2);
				}
				PureHQMain.playerData.set("Name." + playerName + ".claimed", true);
				PureHQFileManage.saveYamls();
				Bukkit.broadcastMessage(ChatColor.GRAY + "[Claim] " + ChatColor.RED + playerName + ChatColor.YELLOW + " has claimed their keys and lives!");
			}
		}else{
			player.sendMessage(ChatColor.RED + "You already claimed!");
		}
		return true;
	}
	
	public static boolean claimAddLives(Player player, String[] args){
		String rank = args[1];
		String amount = args[2];
		if (!amount.matches("[0-9]+")){
			player.sendMessage(ChatColor.RED + "Amount should be a number!");
		}
		String rankAvailable = PureHQMain.ranks.getString(rank);
		List<String> commands = PureHQMain.ranks.getStringList(rank + ".commands");
		String command = "lives add {player} "+ amount;
		if (rankAvailable == null){
			player.sendMessage(PureHQStrings.RANK_DOESNT_EXIST);
			return true;
		}
		if (commands == null){
			commands = new ArrayList<String>();
			commands.add(command);
			player.sendMessage(ChatColor.GREEN + "Added " + ChatColor.GRAY + amount + " lives" + ChatColor.GREEN +  " to " + ChatColor.DARK_GRAY + rank);
		}else{
			commands.add(command);
			player.sendMessage(ChatColor.GREEN + "Added " + ChatColor.GRAY + amount + " lives" + ChatColor.GREEN +  " to " + ChatColor.DARK_GRAY + rank); 
		}
		PureHQMain.ranks.set(rank + ".commands", commands);
		PureHQFileManage.saveYamls();
		return true;
	}
	

	public static boolean claimRemoveLives(Player player, String[] args){
		String rank = args[1];
		
		String amount = args[2];
		if (!amount.matches("[0-9]+")){
			player.sendMessage(ChatColor.RED + "Amount should be a number!");
		}
		String rankAvailable = PureHQMain.ranks.getString(rank);
		List<String> commands = PureHQMain.ranks.getStringList(rank + ".commands");
		String command = "lives add {player} "+ amount;
		if (rankAvailable == null){
			player.sendMessage(PureHQStrings.RANK_DOESNT_EXIST);
			return true;
		}
		if (commands != null && commands.contains(command)){
			commands.remove(command);
			player.sendMessage(ChatColor.GREEN + "Removed " + ChatColor.GRAY + amount + " lives" + ChatColor.GREEN +  " from " + ChatColor.DARK_GRAY + rank); 
		}else{
			player.sendMessage(ChatColor.RED + "This rank does not have this command attached!");
		}
		PureHQMain.ranks.set(rank + ".commands", commands);
		PureHQFileManage.saveYamls();
		return true;
	}
	
	
	public static boolean claimAddKeys(Player player, String[] args){
		String rank = args[1];
		String keyType = args[2];
		String amount = args[3];
		if (!amount.matches("[0-9]+")){
			player.sendMessage(ChatColor.RED + "Amount should be a number!");
		}
		String rankAvailable = PureHQMain.ranks.getString(rank);
		List<String> commands = PureHQMain.ranks.getStringList(rank + ".commands");
		String command = "key give {player} " + keyType + " " + amount;
		if (rankAvailable == null){
			player.sendMessage(PureHQStrings.RANK_DOESNT_EXIST);
			return true;
		}
		if (commands == null){
			commands = new ArrayList<String>();
			commands.add(command);
			player.sendMessage(ChatColor.GREEN + "Added " + ChatColor.GRAY + amount + " " + keyType + " key(s)" + ChatColor.GREEN +  " to " + ChatColor.DARK_GRAY + rank);
		}else{
			commands.add(command);
			player.sendMessage(ChatColor.GREEN + "Added " + ChatColor.GRAY + amount + " " + keyType + " key(s)" + ChatColor.GREEN +  " to " + ChatColor.DARK_GRAY + rank); 
		}
		PureHQMain.ranks.set(rank + ".commands", commands);
		PureHQFileManage.saveYamls();
		return true;
	}
	
	public static boolean claimRemoveKeys(Player player, String[] args){
		String rank = args[1];
		String keyType = args[2];
		String amount = args[3];
		if (!amount.matches("[0-9]+")){
			player.sendMessage(ChatColor.RED + "Amount should be a number!");
		}
		String rankAvailable = PureHQMain.ranks.getString(rank);
		List<String> commands = PureHQMain.ranks.getStringList(rank + ".commands");
		String command = "key give {player} " + keyType + " " + amount;
		if (rankAvailable == null){
			player.sendMessage(PureHQStrings.RANK_DOESNT_EXIST);
			return true;
		}
		if (commands != null && commands.contains(command)){
			commands.remove(command);
			player.sendMessage(ChatColor.GREEN + "Removed " + ChatColor.GRAY + amount + " " + keyType + " key(s)" + ChatColor.GREEN +  " from " + ChatColor.DARK_GRAY + rank); 
		}else{
			player.sendMessage(ChatColor.RED + "This rank does not have this command attached!");
		}
		PureHQMain.ranks.set(rank + ".commands", commands);
		PureHQFileManage.saveYamls();
		return true;
	}
}