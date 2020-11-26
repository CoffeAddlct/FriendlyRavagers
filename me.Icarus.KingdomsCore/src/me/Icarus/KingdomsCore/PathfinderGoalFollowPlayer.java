package me.Icarus.KingdomsCore;

import java.util.EnumSet;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import net.minecraft.server.v1_16_R3.EntityCreature;
import net.minecraft.server.v1_16_R3.EntityInsentient;
import net.minecraft.server.v1_16_R3.EntityLiving;
import net.minecraft.server.v1_16_R3.PathfinderGoal;
import net.minecraft.server.v1_16_R3.RandomPositionGenerator;
import net.minecraft.server.v1_16_R3.Vec3D;

//TODO Check if the Entity is in the same world as the player

public class PathfinderGoalFollowPlayer extends PathfinderGoal {

    private final EntityInsentient entity; //a
    private EntityLiving ownerPlayer; //b
    private final double speed;  //f
    private final float distanceSquared; //g
    private double x;
    private double y;
    private double z;

    public PathfinderGoalFollowPlayer(EntityInsentient entity,double speed, float distance) {
        this.entity = entity;
        this.speed = speed;
        this.distanceSquared = distance;
        this.a(EnumSet.of(Type.MOVE,Type.LOOK,Type.TARGET));
        this.ownerPlayer=entity.getGoalTarget();

    }
//starts pathfinding
    //Owner is alive, not null and distant x blocks from the entity
    @Override
    public boolean a() {
    	this.ownerPlayer= this.entity.getGoalTarget();
    	if(this.ownerPlayer==null)
    		return false;
    	else if (!this.entity.isAlive())
    		return false;
    	//Calculates distance betwen ownerplayer and entity
    	else if(!((this.ownerPlayer.h(this.entity)) < (double)(this.distanceSquared*this.distanceSquared))) {
    		//you should
    		entity.setPosition(ownerPlayer.locX(),ownerPlayer.locY(), ownerPlayer.locZ());
    		return false;
    	}
    	else {
    		Vec3D vec3d= RandomPositionGenerator.a((EntityCreature)this.entity, 16, 7,this.ownerPlayer.getPositionVector());
    				if(vec3d==null)
    					return false;
    		this.x=this.ownerPlayer.locX();
    		this.y=this.ownerPlayer.locY();
    		this.z=this.ownerPlayer.locZ();
    		return true;
    	}
    		
    }
    
    //tick()
    @Override
    public void c() {
    	if(!this.entity.hasSinglePlayerPassenger()) 
    	this.entity.getNavigation().a(this.x,this.y,this.z, this.speed);
    }
    
    //runs after c, did the navigation reach the Owner? If ==true c runs again, if ==false d runs instead
    @Override
    public boolean b() {
    	return(!this.entity.getNavigation().m() && (this.ownerPlayer.h(this.entity) < (double) (this.distanceSquared*this.distanceSquared)));
    	
    }
    
    @Override
    public void d() {
    	this.ownerPlayer=null;
    } 
 
}