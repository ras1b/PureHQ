package me.ras1b.PureHQ.Crates;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.inventory.ItemStack;

public class PureHQInventoryListener implements Listener{
	
	@EventHandler
	public void playerTakeitemEvent(InventoryClickEvent event) {
		if (event.getInventory().getName().contains("�3 crate")){
			event.setCancelled(true);
		}
	}
	
	@EventHandler
	public void playerCloseInventory(InventoryCloseEvent event){
		if (event.getInventory().getName().contains(" �3crate reward")){
			ItemStack[] isList = event.getInventory().getContents();
			if (isList != null && isList.length != 0){
				for (ItemStack is : isList){
					event.getPlayer().getInventory().setItem(event.getPlayer().getInventory().firstEmpty(), is);
				}
			}
		}
	}
}
