package me.Icarus.KingdomsCore;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.apache.logging.log4j.core.layout.YamlLayout;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.craftbukkit.v1_16_R3.CraftWorld;
import org.bukkit.craftbukkit.v1_16_R3.entity.CraftEntity;
import org.bukkit.craftbukkit.v1_16_R3.entity.CraftPlayer;
import org.bukkit.craftbukkit.v1_16_R3.entity.CraftRavager;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Ravager;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.event.entity.EntityTeleportEvent;
import org.bukkit.event.entity.EntityTargetEvent.TargetReason;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import net.minecraft.server.v1_16_R3.EntityInsentient;
import net.minecraft.server.v1_16_R3.EntityLiving;
import net.minecraft.server.v1_16_R3.EntityRavager;
import net.minecraft.server.v1_16_R3.WorldServer;
import me.Icarus.KingdomsCore.pluginUtils;

public class RavagerClassManager implements Listener{
	private List<RavagerMount> ravagersList;
	
	public RavagerClassManager() {
		ravagersList= new ArrayList<>();
	}
	
	@EventHandler
	public void onrightclickevent(PlayerInteractEntityEvent e) {
	        if(e.getRightClicked() instanceof Ravager){
	        	Entity bukkitRavager = e.getRightClicked();
	        	EntityRavager nmsRavager = ((CraftRavager)bukkitRavager).getHandle();
	        	if(nmsRavager instanceof RavagerMount) {
	        		e.getPlayer().sendMessage(ChatColor.RED + "This Ravager is already tamed!");
	        	}else{
	        		WorldServer worldserv= ((CraftWorld)e.getPlayer().getWorld()).getHandle();
	        		RavagerMount rvMount= new RavagerMount(e.getPlayer(), worldserv, nmsRavager.getBukkitEntity().getLocation());
	        		worldserv.addEntity(rvMount);
	        		e.getRightClicked().teleport(e.getPlayer().getLocation().subtract(0, 100, 0));
	        		rvMount.getBukkitEntity().setCustomName(ChatColor.GOLD +" " + ChatColor.UNDERLINE+ " " + e.getPlayer().getName() + ChatColor.RESET +"'s Ravager");
	        		e.getPlayer().sendMessage(ChatColor.GREEN + "You successfully tamed this Ravager!");
	        		ravagersList.add(rvMount);
	        	}
	        	
	        }
		}
	
	@EventHandler
	public void onPlayerLeave(PlayerQuitEvent pqe) {
		//if(!ravagersList.isEmpty())
		for(RavagerMount rvMount : ravagersList)
			if(rvMount.getOwnerUUID()==pqe.getPlayer().getUniqueId()) {
				rvMount.setGoalTarget(null);
				if(rvMount.getGoalTarget()==null)
					Bukkit.getServer().getConsoleSender().sendMessage(ChatColor.RED +" "+ ChatColor.BOLD + " " + "The Ravager don't have a target anymore..");
				
			}
	}
	
	@EventHandler
	public void onPlayerJoin(PlayerJoinEvent pje) {
		//if(!ravagersList.isEmpty())
		for(RavagerMount rvMount : ravagersList) {
			//TODO Check if the corrispoinding owner UUID of the Ravager equals to the player that has joined.
			//if(rvMount.getOwnerUUID()==pje.getPlayer().getUniqueId()) {
				rvMount.setGoalTarget((EntityLiving)((CraftPlayer)pje.getPlayer()).getHandle(),TargetReason.CUSTOM, true);
				if(rvMount.getGoalTarget()!=null)
					Bukkit.getServer().getConsoleSender().sendMessage(ChatColor.GREEN +" "+ ChatColor.BOLD + " " + "They are now following you. At least, they should");
			}
	}
		
	@EventHandler
	public void onEntityDeath(EntityDeathEvent ede) {
		 if(ede.getEntity() instanceof Ravager){
	        	Entity bukkitRavager = ede.getEntity();
	        	EntityRavager nmsRavager = ((CraftRavager)bukkitRavager).getHandle();
	        	if(nmsRavager instanceof RavagerMount)
	        		if(ravagersList.contains(nmsRavager))
	        			ravagersList.remove(nmsRavager);
	        		Bukkit.broadcastMessage(ChatColor.AQUA + "A ravager has died!");
	        	
	}
		
	}
	
	@EventHandler
	public void onAsyncPlayerChat(AsyncPlayerChatEvent apce) {
		if(apce.getMessage().equals("mappa"))
			for(RavagerMount rvMount : ravagersList)
					 apce.getPlayer().sendMessage("Debug..." + rvMount.getName());
		if(apce.getMessage().equals("near"))
			if(pluginUtils.isThereEnoughSpace(apce.getPlayer().getLocation(), 2, apce.getPlayer().getWorld())) {
				apce.getPlayer().sendMessage("You have enough space!");
			}else {
				apce.getPlayer().sendMessage("You don't have enough space!");
			}
			
		
	
  }
	
	@EventHandler
	public void onEntityTeleportEvent(EntityTeleportEvent ete) {
		 if(ete.getEntity() instanceof Ravager){
	        	Entity bukkitRavager = ete.getEntity();
	        	EntityRavager nmsRavager = ((CraftRavager)bukkitRavager).getHandle();
	        	if(nmsRavager instanceof RavagerMount) {
	        		RavagerMount mount= (RavagerMount)nmsRavager;
	        		Bukkit.broadcastMessage("A ravager has teleported to you");
	        	}
	        	
	        		
		 }
	}

	public List<RavagerMount> getRavagersList() {
		return ravagersList;
	}

	public void setRavagersList(List<RavagerMount> ravagersList) {
		this.ravagersList = ravagersList;
	}
	
	
	
	
	
	
}


	
	
	