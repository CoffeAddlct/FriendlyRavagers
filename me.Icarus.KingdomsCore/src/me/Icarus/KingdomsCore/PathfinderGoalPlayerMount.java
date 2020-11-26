package me.Icarus.KingdomsCore;

import java.util.EnumSet;

import org.bukkit.util.Vector;

import net.minecraft.server.v1_16_R3.EntityCreature;
import net.minecraft.server.v1_16_R3.EntityInsentient;
import net.minecraft.server.v1_16_R3.EntityLiving;
import net.minecraft.server.v1_16_R3.PathfinderGoal;
import net.minecraft.server.v1_16_R3.RandomPositionGenerator;
import net.minecraft.server.v1_16_R3.Vec3D;
import net.minecraft.server.v1_16_R3.PathfinderGoal.Type;

public class PathfinderGoalPlayerMount extends PathfinderGoal {

	    private final EntityInsentient entity; //a
	    private EntityLiving ownerPlayer; //b
	    private final double speed;  //f
	    private double x;
	    private double y;
	    private double z;
	    
	    public PathfinderGoalPlayerMount(EntityInsentient ridedEntity, double speed) {
	    	this.entity=ridedEntity;
	    	this.speed=speed;
			this.ownerPlayer=this.entity.getGoalTarget();
		}

	//starts pathfinding if true
	    //Owner is alive, not null and distant x blocks from the entity
	    @Override
	    public boolean a() {
	    	if(this.entity.isAlive() && this.entity.isNoAI()==false && this.entity.hasSinglePlayerPassenger()) {
	    		return true;
	    	}
	    	return false;

	    		
	    }
	    
	    //tick()
	    @Override
	    public void c() {
	    	if(this.entity.hasSinglePlayerPassenger()) {
	    		  this.entity.lastYaw = (this.entity.yaw = ownerPlayer.yaw);
	    	        this.entity.pitch = ownerPlayer.pitch * 0.5F;
	    	        Vector vector= new Vector(ownerPlayer.getLookDirection().getX(),ownerPlayer.getLookDirection().getY(),ownerPlayer.getLookDirection().getZ());
	    	        this.entity.getBukkitEntity().setVelocity(vector);
	    	        this.entity.velocityChanged=true;
	    	}
	    		
	    		
	    	//this.entity.getNavigation().a(this.x,this.y,this.z, this.speed);
	    	//this.entity.getBukkitEntity().setVelocity());
	    }
	    
	    //runs after c, did the navigation reach the Owner? If ==true c runs again, if ==false d runs instead
	    @Override
	    public boolean b() {
	    	return(!this.entity.hasSinglePlayerPassenger() && ownerPlayer==null);
	    	
	    }
	    
	    @Override
	    public void d() {
	    } 
	 
	}
