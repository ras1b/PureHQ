package me.ras1b.PureHQ.UsefulListeners;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;

import me.ras1b.PureHQ.PureHQMain;

public class PureHQDeathListener implements Listener{

	@SuppressWarnings("unused")
	private PureHQMain plugin;

    public PureHQDeathListener(PureHQMain plugin) {
        this.plugin = plugin;
    }
	
	@EventHandler
	public void playerDeathEvent(PlayerDeathEvent event) {
		event.setDeathMessage(null);
	}
}
