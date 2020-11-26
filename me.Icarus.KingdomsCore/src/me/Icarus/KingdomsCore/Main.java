package me.Icarus.KingdomsCore;

import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.craftbukkit.v1_16_R3.CraftWorld;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import me.Icarus.KingdomsCore.RavagerMount;
import net.minecraft.server.v1_16_R3.ChatModifier;
import net.minecraft.server.v1_16_R3.IChatBaseComponent;
import net.minecraft.server.v1_16_R3.IChatMutableComponent;
import net.minecraft.server.v1_16_R3.WorldServer;

public class Main extends JavaPlugin {
	WorldServer worldserv;

	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		if(sender instanceof Player) {
			Player p= (Player) sender;
			if(label.equalsIgnoreCase("raid")) {
				//DEBUG
				worldserv= ((CraftWorld)p.getWorld()).getHandle();
				RavagerMount rvMount= new RavagerMount(p,worldserv,p.getLocation());
				worldserv.addEntity(rvMount);				
			}
			return true;
		}else{
			return false;
		}
	}

	@Override
	public void onDisable() {
		//save the list of ravagers and locations. onLoad, teleport them.
		// TODO Auto-generated method stub
	}

	@Override
	public void onEnable() {
		getServer().getConsoleSender().sendMessage(ChatColor.GREEN +" "+ ChatColor.BOLD + " " + "Enabling Kingdoms");
		getServer().getPluginManager().registerEvents(new RavagerClassManager(), this);
		// TODO Auto-generated method stub
	}

}
