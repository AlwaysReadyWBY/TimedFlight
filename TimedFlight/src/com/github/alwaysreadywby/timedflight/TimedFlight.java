package com.github.alwaysreadywby.timedflight;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerChangedWorldEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.metadata.MetadataValue;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;

public class TimedFlight extends JavaPlugin implements Listener {
	
	public static final long TICK_SECOND=20;
	public static final long TICK_MINUTE=TICK_SECOND*60;
	public static final long TICK_HOUR=TICK_MINUTE*60;
	public static final long TICK_DAY=TICK_HOUR*24;
	public static final long TICK_MONTH=TICK_DAY*30;
	public static final long TICK_YEAR=TICK_DAY*365;

	private static TimedFlight plug;
	
	public static TimedFlight getPlug() {
		return plug;
	}
	
	public static long getTimeFor(String t) {
		char a=t.charAt(t.length()-1);
		long time=-1;
		try {
			time=Long.parseLong(t.substring(0, t.length()-1));
		}catch (NumberFormatException e) {
			MsgMgr.inform(Config.getLang("msg.invalid-time-format"));
		}
		switch(a) {
		case 't':
			break;
		case 's':
			time=time*TimedFlight.TICK_SECOND;
			break;
		case 'm':
			time=time*TimedFlight.TICK_MINUTE;
			break;
		case 'h':
			time=time*TimedFlight.TICK_HOUR;
			break;
		case 'd':
			time=time*TimedFlight.TICK_DAY;
			break;
		case 'M':
			time=time*TimedFlight.TICK_MONTH;
			break;
		case 'y':
			time=time*TimedFlight.TICK_YEAR;
			break;
		default:
			MsgMgr.inform(Config.getLang("msg.invalid-time-format"));
			break;
		}
		return time;
	}
	
	@Override
	public void onEnable() {
	    getServer().getPluginManager().registerEvents(this, this);
	}
	
	@Override
	public void onLoad() {
		plug=this;
		init();
	}

	@Override
	public void onDisable() {
		Config.saveAll();
	}
	
	private void init() {
		// TODO Auto-generated method stub
		Config.init();
	}
	
	public void onReload() {
		// TODO Auto-generated method stub
		Config.saveAll();
		init();
	}
	
	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
	    CmdMgr.onCommandReceive(sender, command, label, args);
		return false;
	}
	
	@EventHandler(priority=EventPriority.LOWEST, ignoreCancelled=true)
	public void onPlayerMove(PlayerMoveEvent e) {
		Player p=e.getPlayer();
		if(p.getAllowFlight() && !p.hasPermission("timedflight.bypass") && p.getGameMode().getValue()!=1) {
			long currentTime=p.getWorld().getFullTime();
			if(p.hasMetadata("timedflight.lastchecked")) {
				if(!Config.reduceTickRemaining(p.getName(), currentTime-p.getMetadata("timedflight.lastchecked").get(0).asLong())) {
					p.setAllowFlight(false);
					p.setFallDistance(-255);
					MsgMgr.sendMsg(p, Config.getLang("msg.time.insufficient"));
				}
				p.setMetadata("timedflight.lastchecked", new LastCheckedMeta(p.getWorld().getFullTime()));
			}
		}
	}
	
	public void onPlayerWorldChange(PlayerChangedWorldEvent e) {
		Player p=e.getPlayer();
		if(p.getAllowFlight() && !p.hasPermission("timedflight.bypass") && p.getGameMode().getValue()!=1) {
			p.setMetadata("timedflight.lastchecked", new LastCheckedMeta(p.getWorld().getFullTime()));
		}
	}
}
