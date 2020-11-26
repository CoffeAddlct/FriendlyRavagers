package me.Icarus.KingdomsCore;

import java.util.Vector;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.data.BlockData;
import org.bukkit.entity.Player;

import com.google.common.base.CaseFormat;

public class pluginUtils {
	public pluginUtils() {
	}
	//for ravager teleportation issues
	static boolean  isThereEnoughSpace(Location c,int radius, World w) {
		 for (int x = c.getBlockX()-1; x <= c.getBlockX()-1 + radius; x++)
	        {
	            for (int z = c.getBlockZ()-1; z <= c.getBlockZ()-1 + radius; z++)
	            {
	                for (int y = c.getBlockY(); y <= c.getBlockY() + radius ; y++)
	                {
	                	switch (w.getBlockAt(x, y, z).getType()) {
						case AIR:
							
							break;
							
						case WATER:
							
							break;

						default:
							return false;
						}
	                	
	                	/*
	                  if(w.getBlockAt(x, y, z).getType()!=Material.AIR) {
	                	  return false;
	                  }
	                  */
	                }
	            }
	        }
		 return true;
	}

}
