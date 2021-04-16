package net.natroutter.natlibs.objects;

import java.net.InetSocketAddress;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.DyeColor;
import org.bukkit.Effect;
import org.bukkit.EntityEffect;
import org.bukkit.FluidCollisionMode;
import org.bukkit.GameMode;
import org.bukkit.Instrument;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.Note;
import org.bukkit.Particle;
import org.bukkit.Server;
import org.bukkit.Sound;
import org.bukkit.SoundCategory;
import org.bukkit.Statistic;
import org.bukkit.WeatherType;
import org.bukkit.World;
import org.bukkit.advancement.Advancement;
import org.bukkit.advancement.AdvancementProgress;
import org.bukkit.attribute.Attribute;
import org.bukkit.attribute.AttributeInstance;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.block.PistonMoveReaction;
import org.bukkit.block.data.BlockData;
import org.bukkit.command.CommandSender;
import org.bukkit.conversations.Conversation;
import org.bukkit.conversations.ConversationAbandonedEvent;
import org.bukkit.entity.*;
import org.bukkit.entity.memory.MemoryKey;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.player.PlayerTeleportEvent.TeleportCause;
import org.bukkit.inventory.EntityEquipment;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryView;
import org.bukkit.inventory.InventoryView.Property;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.MainHand;
import org.bukkit.inventory.Merchant;
import org.bukkit.inventory.PlayerInventory;
import org.bukkit.map.MapView;
import org.bukkit.metadata.MetadataValue;
import org.bukkit.permissions.Permission;
import org.bukkit.permissions.PermissionAttachment;
import org.bukkit.permissions.PermissionAttachmentInfo;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.plugin.Plugin;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scoreboard.Scoreboard;
import org.bukkit.util.BoundingBox;
import org.bukkit.util.RayTraceResult;
import org.bukkit.util.Vector;

@SuppressWarnings("deprecation")
public class BasePlayer implements Player {

	private static HashMap<Player, BasePlayer> players = new HashMap<>();
	
	private final Player p;
	
	private BasePlayer(Player p) {
        this.p = p;
        players.put(p, this);
        
    }
	
	public static BasePlayer from(Entity p) {
        if (p == null) { return null; }
        return players.containsKey(p) ? players.get(p) : new BasePlayer((Player)p);

    }
    public static BasePlayer from(Player p) {
        if (p == null) { return null; }
        return players.containsKey(p) ? players.get(p) : new BasePlayer(p);

    }
    public static BasePlayer from(CommandSender sender) {
        if (sender == null) { return null; }
        return players.containsKey(sender) ? players.get(sender) : new BasePlayer((Player) sender);
    }
	public static BasePlayer from(AnimalTamer tamer) {
		if (tamer == null) { return null; }
		return players.containsKey(tamer) ? players.get(tamer) : new BasePlayer((Player) tamer);
	}
	
    
    public static List<BasePlayer> getOnlinePlayers() {
    	List<BasePlayer> players = new ArrayList<BasePlayer>();
    	Bukkit.getOnlinePlayers().forEach((p) -> {
    		players.add(BasePlayer.from(p));
    	});
    	return players;
    }
    
	@Override
	public void closeInventory() {
		p.closeInventory();
		
	}

	@Override
	public boolean discoverRecipe( NamespacedKey arg0) {
		return p.discoverRecipe(arg0);
	}

	@Override
	public int discoverRecipes( Collection<NamespacedKey> arg0) {
		return p.discoverRecipes(arg0);
	}

	@Override
	public float getAttackCooldown() {
		return p.getAttackCooldown();
	}

	@Override
	public  Location getBedLocation() {
		return p.getBedLocation();
	}

	@Override
	public int getCooldown( Material arg0) {
		return p.getCooldown(arg0);
	}

	@Override
	public  Inventory getEnderChest() {
		return p.getEnderChest();
	}

	@Override
	public int getExpToLevel() {
		return p.getExpToLevel();
	}

	@Override
	public  GameMode getGameMode() {
		return p.getGameMode();
	}

	@Override
	public  PlayerInventory getInventory() {
		return p.getInventory();
	}

	@Override
	public BaseItem getItemInHand() {
		return BaseItem.from(getItemInHand());
	}

	@Override
	public BaseItem getItemOnCursor() {
		return BaseItem.from(getItemInHand());
	}
	
	@Override
	public  MainHand getMainHand() {
		return p.getMainHand();
	}

	@Override
	public  String getName() {
		return p.getName();
	}

	@Override
	public  InventoryView getOpenInventory() {
		return p. getOpenInventory();
	}

	@Override
	public Entity getShoulderEntityLeft() {
		return p.getShoulderEntityLeft();
	}

	@Override
	public Entity getShoulderEntityRight() {
		return p.getShoulderEntityRight();
	}

	@Override
	public int getSleepTicks() {
		return p.getSleepTicks();
	}

	@Override
	public boolean hasCooldown( Material arg0) {
		return p.hasCooldown(arg0);
	}

	@Override
	public boolean isBlocking() {
		return p.isBlocking();
	}

	@Override
	public boolean isHandRaised() {
		return p.isHandRaised();
	}

	@Override
	public InventoryView openEnchanting(Location arg0, boolean arg1) {
		return p.openEnchanting(arg0, arg1);
	}

	@Override
	public InventoryView openInventory( Inventory arg0) {
		return p.openInventory(arg0);
	}

	@Override
	public void openInventory( InventoryView arg0) {
		p.openInventory(arg0);
	}

	@Override
	public InventoryView openMerchant( Villager arg0, boolean arg1) {
		return p.openMerchant(arg0, arg1);
	}

	@Override
	public InventoryView openMerchant( Merchant arg0, boolean arg1) {
		return p.openMerchant(arg0, arg1);
	}

	@Override
	public InventoryView openWorkbench(Location arg0, boolean arg1) {
		return p.openWorkbench(arg0, arg1);
	}

	@Override
	public void setCooldown( Material arg0, int arg1) {
		p.setCooldown(arg0, arg1);
		
	}

	@Override
	public void setGameMode( GameMode arg0) {
		p.setGameMode(arg0);
		
	}

	@Override
	public void setItemInHand(ItemStack arg0) {
		p.setItemInHand(arg0);
		
	}

	@Override
	public void setItemOnCursor(ItemStack arg0) {
		p.setItemOnCursor(arg0);
		
	}

	@Override
	public void setShoulderEntityLeft(Entity arg0) {
		p.setShoulderEntityLeft(arg0);
		
	}

	@Override
	public void setShoulderEntityRight(Entity arg0) {
		p.setShoulderEntityRight(arg0);
		
	}

	@Override
	public boolean setWindowProperty( Property arg0, int arg1) {
		return p.setWindowProperty(arg0, arg1);
	}

	@Override
	public boolean sleep( Location arg0, boolean arg1) {
		return p.sleep(arg0, arg1);
	}

	@Override
	public boolean undiscoverRecipe( NamespacedKey arg0) {
		return p.undiscoverRecipe(arg0);
	}

	@Override
	public int undiscoverRecipes( Collection<NamespacedKey> arg0) {
		return p.undiscoverRecipes(arg0);
	}

	@Override
	public void wakeup(boolean arg0) {
		p.wakeup(arg0);
		
	}

	@Override
	public boolean addPotionEffect( PotionEffect arg0) {
		return p.addPotionEffect(arg0);
	}

	@Override
	public boolean addPotionEffect( PotionEffect arg0, boolean arg1) {
		return p.addPotionEffect(arg0, arg1);
	}

	@Override
	public boolean addPotionEffects( Collection<PotionEffect> arg0) {
		return p.addPotionEffects(arg0);
	}

	@Override
	public void attack( Entity arg0) {
		p.attack(arg0);
		
	}

	@Override
	public Collection<PotionEffect> getActivePotionEffects() {
		return p.getActivePotionEffects();
	}

	@Override
	public boolean getCanPickupItems() {
		return p.getCanPickupItems();
	}

	@Override
	public Set<UUID> getCollidableExemptions() {
		return p.getCollidableExemptions();
	}

	@Override
	public EntityEquipment getEquipment() {
		return p.getEquipment();
	}

	@Override
	public double getEyeHeight() {
		return p.getEyeHeight();
	}

	@Override
	public double getEyeHeight(boolean arg0) {
		return p.getEyeHeight();
	}

	@Override
	public  Location getEyeLocation() {
		return p.getEyeLocation();
	}

	@Override
	public Player getKiller() {
		return p.getKiller();
	}

	@Override
	public double getLastDamage() {
		return p.getLastDamage();
	}

	@Override
	public  List<Block> getLastTwoTargetBlocks(Set<Material> arg0, int arg1) {
		return p.getLastTwoTargetBlocks(arg0, arg1);
	}

	@Override
	public  Entity getLeashHolder() throws IllegalStateException {
		return p.getLeashHolder();
	}

	@Override
	public  List<Block> getLineOfSight(Set<Material> arg0, int arg1) {
		return p.getLineOfSight(arg0, arg1);
	}

	@Override
	public int getMaximumAir() {
		return p.getMaximumAir();
	}

	@Override
	public int getMaximumNoDamageTicks() {
		return p.getMaximumNoDamageTicks();
	}

	@Override
	public <T> T getMemory( MemoryKey<T> arg0) {
		return p.getMemory(arg0);
	}

	@Override
	public int getNoDamageTicks() {
		return p.getNoDamageTicks();
	}

	@Override
	public PotionEffect getPotionEffect( PotionEffectType arg0) {
		return p.getPotionEffect(arg0);
	}

	@Override
	public int getRemainingAir() {
		return p.getRemainingAir();
	}

	@Override
	public boolean getRemoveWhenFarAway() {
		return p.getRemoveWhenFarAway();
	}

	@Override
	public  Block getTargetBlock(Set<Material> arg0, int arg1) {
		return p.getTargetBlock(arg0, arg1);
	}

	@Override
	public Block getTargetBlockExact(int arg0) {
		return p.getTargetBlockExact(arg0);
	}

	@Override
	public Block getTargetBlockExact(int arg0,  FluidCollisionMode arg1) {
		return p.getTargetBlockExact(arg0, arg1);
	}

	@Override
	public boolean hasAI() {
		return p.hasAI();
	}

	@Override
	public boolean hasLineOfSight( Entity arg0) {
		return p.hasLineOfSight(arg0);
	}

	@Override
	public boolean hasPotionEffect( PotionEffectType arg0) {
		return p.hasPotionEffect(arg0);
	}

	@Override
	public boolean isCollidable() {
		return p.isCollidable();
	}

	@Override
	public boolean isGliding() {
		return p.isGliding();
	}

	@Override
	public boolean isLeashed() {
		return p.isLeashed();
	}

	@Override
	public boolean isRiptiding() {
		return p.isRiptiding();
	}

	@Override
	public boolean isSleeping() {
		return p.isSleeping();
	}

	@Override
	public boolean isSwimming() {
		return p.isSwimming();
	}

	@Override
	public RayTraceResult rayTraceBlocks(double arg0) {
		return p.rayTraceBlocks(arg0);
	}

	@Override
	public RayTraceResult rayTraceBlocks(double arg0,  FluidCollisionMode arg1) {
		return p.rayTraceBlocks(arg0, arg1);
	}

	@Override
	public void removePotionEffect( PotionEffectType arg0) {
		p.removePotionEffect(arg0);
		
	}

	@Override
	public void setAI(boolean arg0) {
		p.setAI(arg0);
		
	}

	@Override
	public void setCanPickupItems(boolean arg0) {
		p.setCanPickupItems(arg0);
		
	}

	@Override
	public void setCollidable(boolean arg0) {
		p.setCollidable(arg0);
		
	}

	@Override
	public void setGliding(boolean arg0) {
		p.setGliding(arg0);
		
	}

	@Override
	public void setLastDamage(double arg0) {
		p.setLastDamage(arg0);
		
	}

	@Override
	public boolean setLeashHolder(Entity arg0) {
		return p.setLeashHolder(arg0);
	}

	@Override
	public void setMaximumAir(int arg0) {
		p.setMaximumAir(arg0);
		
	}

	@Override
	public void setMaximumNoDamageTicks(int arg0) {
		p.setMaximumNoDamageTicks(arg0);
		
	}

	@Override
	public <T> void setMemory( MemoryKey<T> arg0,T arg1) {
		p.setMemory(arg0, arg1);
		
	}

	@Override
	public void setNoDamageTicks(int arg0) {
		p.setNoDamageTicks(arg0);
		
	}

	@Override
	public void setRemainingAir(int arg0) {
		p.setRemainingAir(arg0);
		
	}

	@Override
	public void setRemoveWhenFarAway(boolean arg0) {
		p.setRemoveWhenFarAway(arg0);
		
	}

	@Override
	public void setSwimming(boolean arg0) {
		p.setSwimming(arg0);
		
	}

	@Override
	public void swingMainHand() {
		p.swingMainHand();
		
	}

	@Override
	public void swingOffHand() {
		p.swingOffHand();
		
	}

	@Override
	public AttributeInstance getAttribute( Attribute arg0) {
		return p.getAttribute(arg0);
	}

	@Override
	public void damage(double arg0) {
		p.damage(arg0);
		
	}

	@Override
	public void damage(double arg0,Entity arg1) {
		p.damage(arg0, arg1);
		
	}

	@Override
	public double getAbsorptionAmount() {
		return p.getAbsorptionAmount();
	}

	@Override
	public double getHealth() {
		return p.getHealth();
	}

	@Override
	public double getMaxHealth() {
		return p.getMaxHealth();
	}

	@Override
	public void resetMaxHealth() {
		p.resetMaxHealth();
		
	}

	@Override
	public void setAbsorptionAmount(double arg0) {
		p.setAbsorptionAmount(arg0);
		
	}

	@Override
	public void setHealth(double arg0) {
		p.setHealth(arg0);
		
	}

	@Override
	public void setMaxHealth(double arg0) {
		p.setMaxHealth(arg0);
		
	}

	@Override
	public boolean addPassenger( Entity arg0) {
		return p.addPassenger(arg0);
	}

	@Override
	public boolean addScoreboardTag( String arg0) {
		return p.addScoreboardTag(arg0);
	}

	@Override
	public boolean eject() {
		return p.eject();
	}

	@Override
	public BoundingBox getBoundingBox() {
		return p.getBoundingBox();
	}

	@Override
	public int getEntityId() {
		return p.getEntityId();
	}

	@Override
	public BlockFace getFacing() {
		return p.getFacing();
	}

	@Override
	public float getFallDistance() {
		return p.getFallDistance();
	}

	@Override
	public int getFireTicks() {
		return p.getFireTicks();
	}

	@Override
	public double getHeight() {
		return p.getHeight();
	}

	@Override
	public EntityDamageEvent getLastDamageCause() {
		return p.getLastDamageCause();
	}

	@Override
	public  Location getLocation() {
		return p.getLocation();
	}

	@Override
	public Location getLocation(Location arg0) {
		return p.getLocation(arg0);
	}

	@Override
	public int getMaxFireTicks() {
		return p.getMaxFireTicks();
	}

	@Override
	public  List<Entity> getNearbyEntities(double arg0, double arg1, double arg2) {
		return p.getNearbyEntities(arg0, arg1, arg2);
	}

	@Override
	public Entity getPassenger() {
		return p.getPassenger();
	}

	@Override
	public  List<Entity> getPassengers() {
		return p.getPassengers();
	}

	@Override
	public  PistonMoveReaction getPistonMoveReaction() {
		return p.getPistonMoveReaction();
	}

	@Override
	public int getPortalCooldown() {
		return p.getPortalCooldown();
	}

	@Override
	public  Pose getPose() {
		return p.getPose();
	}

	@Override
	public  Set<String> getScoreboardTags() {
		return p.getScoreboardTags();
	}

	@Override
	public Server getServer() {
		return p.getServer();
	}

	@Override
	public int getTicksLived() {
		return p.getTicksLived();
	}

	@Override
	public  EntityType getType() {
		return p.getType();
	}

	@Override
	public  UUID getUniqueId() {
		return p.getUniqueId();
	}

	@Override
	public Entity getVehicle() {
		return p.getVehicle();
	}

	@Override
	public  Vector getVelocity() {
		return p.getVelocity();
	}

	@Override
	public double getWidth() {
		return p.getWidth();
	}

	@Override
	public  World getWorld() {
		return p.getWorld();
	}

	@Override
	public boolean hasGravity() {
		return p.hasGravity();
	}

	@Override
	public boolean isCustomNameVisible() {
		return p.isCustomNameVisible();
	}

	@Override
	public boolean isDead() {
		return p.isDead();
	}

	@Override
	public boolean isEmpty() {
		return p.isEmpty();
	}

	@Override
	public boolean isGlowing() {
		return p.isGlowing();
	}

	@Override
	public boolean isInsideVehicle() {
		return p.isInsideVehicle();
	}

	@Override
	public boolean isInvulnerable() {
		return p.isInvulnerable();
	}

	@Override
	public boolean isOnGround() {
		return p.isOnGround();
	}

	@Override
	public boolean isPersistent() {
		return p.isPersistent();
	}

	@Override
	public boolean isSilent() {
		return p.isSilent();
	}

	@Override
	public boolean isValid() {
		return p.isValid();
	}

	@Override
	public boolean leaveVehicle() {
		return p.leaveVehicle();
	}

	@Override
	public void playEffect( EntityEffect arg0) {
		p.playEffect(arg0);
		
	}

	@Override
	public void remove() {
		p.remove();
		
	}

	@Override
	public boolean removePassenger( Entity arg0) {
		return p.removePassenger(arg0);
	}

	@Override
	public boolean removeScoreboardTag( String arg0) {
		return p.removeScoreboardTag(arg0);
	}

	@Override
	public void setCustomNameVisible(boolean arg0) {
		p.setCustomNameVisible(arg0);
		
	}

	@Override
	public void setFallDistance(float arg0) {
		p.setFallDistance(arg0);
		
	}

	@Override
	public void setFireTicks(int arg0) {
		p.setFireTicks(arg0);
		
	}

	@Override
	public void setGlowing(boolean arg0) {
		p.setGlowing(arg0);
		
	}

	@Override
	public void setGravity(boolean arg0) {
		p.setGravity(arg0);
		
	}

	@Override
	public void setInvulnerable(boolean arg0) {
		p.setInvulnerable(arg0);
		
	}

	@Override
	public void setLastDamageCause(EntityDamageEvent arg0) {
		p.setLastDamageCause(arg0);
		
	}

	@Override
	public boolean setPassenger( Entity arg0) {
		return p.setPassenger(arg0);
	}

	@Override
	public void setPersistent(boolean arg0) {
		p.setPersistent(arg0);
		
	}

	@Override
	public void setPortalCooldown(int arg0) {
		p.setPortalCooldown(arg0);
		
	}

	@Override
	public void setRotation(float arg0, float arg1) {
		p.setRotation(arg0, arg1);
		
	}

	@Override
	public void setSilent(boolean arg0) {
		p.setSilent(arg0);
		
	}

	@Override
	public void setTicksLived(int arg0) {
		p.setTicksLived(arg0);
		
	}

	@Override
	public void setVelocity( Vector arg0) {
		p.setVelocity(arg0);
		
	}

	@Override
	public boolean teleport( Location arg0) {
		return p.teleport(arg0);
	}

	@Override
	public boolean teleport( Entity arg0) {
		return p.teleport(arg0);
	}

	@Override
	public boolean teleport( Location arg0,  TeleportCause arg1) {
		return p.teleport(arg0);
	}

	@Override
	public boolean teleport( Entity arg0,  TeleportCause arg1) {
		return p.teleport(arg0);
	}

	@Override
	public  List<MetadataValue> getMetadata( String arg0) {
		return p.getMetadata(arg0);
	}

	@Override
	public boolean hasMetadata( String arg0) {
		return p.hasMetadata(arg0);
	}

	@Override
	public void removeMetadata( String arg0,  Plugin arg1) {
		p.removeMetadata(arg0, arg1);
		
	}

	@Override
	public void setMetadata( String arg0,  MetadataValue arg1) {
		p.setMetadata(arg0, arg1);
		
	}

	@Override
	public void sendMessage( String arg0) {
		p.sendMessage(arg0);
		
	}

	@Override
	public void sendMessage( String[] arg0) {
		p.sendMessage(arg0);
	}

	@Override
	public  PermissionAttachment addAttachment( Plugin arg0) {
		return p.addAttachment(arg0);
	}

	@Override
	public PermissionAttachment addAttachment( Plugin arg0, int arg1) {
		return p.addAttachment(arg0, arg1);
	}

	@Override
	public  PermissionAttachment addAttachment( Plugin arg0,  String arg1, boolean arg2) {
		return p.addAttachment(arg0, arg1, arg2);
	}

	@Override
	public PermissionAttachment addAttachment( Plugin arg0,  String arg1, boolean arg2,
			int arg3) {
		return p.addAttachment(arg0, arg1, arg2, arg3);
	}

	@Override
	public  Set<PermissionAttachmentInfo> getEffectivePermissions() {
		return p.getEffectivePermissions();
	}

	@Override
	public boolean hasPermission( String arg0) {
		return p.hasPermission(arg0);
	}

	@Override
	public boolean hasPermission( Permission arg0) {
		return p.hasPermission(arg0);
	}

	@Override
	public boolean isPermissionSet( String arg0) {
		return p.isPermissionSet(arg0);
	}

	@Override
	public boolean isPermissionSet( Permission arg0) {
		return p.isPermissionSet(arg0);
	}

	@Override
	public void recalculatePermissions() {
		p.recalculatePermissions();
		
	}

	@Override
	public void removeAttachment( PermissionAttachment arg0) {
		p.removeAttachment(arg0);
		
	}

	@Override
	public boolean isOp() {
		return p.isOp();
	}

	@Override
	public void setOp(boolean arg0) {
		p.setOp(arg0);
		
	}

	@Override
	public String getCustomName() {
		return p.getCustomName();
	}

	@Override
	public void setCustomName(String arg0) {
		p.setCustomName(arg0);
	}

	@Override
	public  PersistentDataContainer getPersistentDataContainer() {
		return p.getPersistentDataContainer();
	}

	@Override
	public <T extends Projectile> T launchProjectile( Class<? extends T> arg0) {
		return p.launchProjectile(arg0);
	}

	@Override
	public <T extends Projectile> T launchProjectile( Class<? extends T> arg0,Vector arg1) {
		return p.launchProjectile(arg0, arg1);
	}

	@Override
	public void abandonConversation( Conversation arg0) {
		p.abandonConversation(arg0);
		
	}

	@Override
	public void abandonConversation( Conversation arg0,  ConversationAbandonedEvent arg1) {
		p.abandonConversation(arg0, arg1);
		
	}

	@Override
	public void acceptConversationInput( String arg0) {
		p.acceptConversationInput(arg0);
	}

	@Override
	public boolean beginConversation( Conversation arg0) {
		return p.beginConversation(arg0);
	}

	@Override
	public boolean isConversing() {
		return p.isConversing();
	}

	@Override
	public void decrementStatistic( Statistic arg0) throws IllegalArgumentException {
		p.decrementStatistic(arg0);
		
	}

	@Override
	public void decrementStatistic( Statistic arg0, int arg1) throws IllegalArgumentException {
		p.decrementStatistic(arg0, arg1);
		
	}

	@Override
	public void decrementStatistic( Statistic arg0,  Material arg1) throws IllegalArgumentException {
		p.decrementStatistic(arg0, arg1);
		
	}

	@Override
	public void decrementStatistic( Statistic arg0,  EntityType arg1) throws IllegalArgumentException {
		p.decrementStatistic(arg0, arg1);
		
	}

	@Override
	public void decrementStatistic( Statistic arg0,  Material arg1, int arg2)
			throws IllegalArgumentException {
		p.decrementStatistic(arg0, arg1, arg2);
		
	}

	@Override
	public void decrementStatistic( Statistic arg0,  EntityType arg1, int arg2) {
		p.decrementStatistic(arg0, arg1, arg2);
		
	}

	@Override
	public long getFirstPlayed() {
		return p.getFirstPlayed();
	}

	@Override
	public Player getPlayer() {
		return p.getPlayer();
	}

	@Override
	public int getStatistic( Statistic arg0) throws IllegalArgumentException {
		return p.getStatistic(arg0);
	}

	@Override
	public int getStatistic( Statistic arg0,  Material arg1) throws IllegalArgumentException {
		return p.getStatistic(arg0, arg1);
	}

	@Override
	public int getStatistic( Statistic arg0,  EntityType arg1) throws IllegalArgumentException {
		return p.getStatistic(arg0, arg1);
	}

	@Override
	public boolean hasPlayedBefore() {
		return p.hasPlayedBefore();
	}

	@Override
	public void incrementStatistic( Statistic arg0) throws IllegalArgumentException {
		p.incrementStatistic(arg0);
		
	}

	@Override
	public void incrementStatistic( Statistic arg0, int arg1) throws IllegalArgumentException {
		p.incrementStatistic(arg0, arg1);
		
	}

	@Override
	public void incrementStatistic( Statistic arg0,  Material arg1) throws IllegalArgumentException {
		p.incrementStatistic(arg0, arg1);
		
	}

	@Override
	public void incrementStatistic( Statistic arg0,  EntityType arg1) throws IllegalArgumentException {
		p.incrementStatistic(arg0, arg1);
		
	}

	@Override
	public void incrementStatistic( Statistic arg0,  Material arg1, int arg2)
			throws IllegalArgumentException {
		p.incrementStatistic(arg0, arg1, arg2);
		
	}

	@Override
	public void incrementStatistic( Statistic arg0,  EntityType arg1, int arg2)
			throws IllegalArgumentException {
		p.incrementStatistic(arg0, arg1, arg2);
	}

	@Override
	public boolean isBanned() {
		return p.isBanned();
	}

	@Override
	public boolean isOnline() {
		return p.isOnline();
	}

	@Override
	public boolean isWhitelisted() {
		return p.isWhitelisted();
	}

	@Override
	public void setStatistic( Statistic arg0, int arg1) throws IllegalArgumentException {
		p.setStatistic(arg0, arg1);
		
	}

	@Override
	public void setStatistic( Statistic arg0,  Material arg1, int arg2)
			throws IllegalArgumentException {
		p.setStatistic(arg0, arg1, arg2);
		
	}

	@Override
	public void setStatistic( Statistic arg0,  EntityType arg1, int arg2) {
		p.setStatistic(arg0, arg1, arg2);
		
	}

	@Override
	public void setWhitelisted(boolean arg0) {
		p.setWhitelisted(arg0);
		
	}

	@Override
	public  Map<String, Object> serialize() {
		return serialize();
	}

	@Override
	public  Set<String> getListeningPluginChannels() {
		return p.getListeningPluginChannels();
	}

	@Override
	public void sendPluginMessage( Plugin arg0,  String arg1,  byte[] arg2) {
		p.sendPluginMessage(arg0, arg1, arg2);
	}

	@Override
	public boolean canSee( Player arg0) {
		return p.canSee(arg0);
	}

	@Override
	public void chat( String arg0) {
		p.chat(arg0);
	}

	@Override
	public InetSocketAddress getAddress() {
		return p.getAddress();
	}

	@Override
	public AdvancementProgress getAdvancementProgress( Advancement arg0) {
		return p.getAdvancementProgress(arg0);
	}

	@Override
	public boolean getAllowFlight() {
		return p.getAllowFlight();
	}

	@Override
	public Location getBedSpawnLocation() {
		return p.getBedSpawnLocation();
	}

	@Override
	public int getClientViewDistance() {
		return p.getClientViewDistance();
	}

	@Override
	public  Location getCompassTarget() {
		return p.getCompassTarget();
	}

	@Override
	public  String getDisplayName() {
		return p.getDisplayName();
	}

	@Override
	public float getExhaustion() {
		return p.getExhaustion();
	}

	@Override
	public float getExp() {
		return p.getExp();
	}

	@Override
	public float getFlySpeed() {
		return p.getFlySpeed();
	}

	@Override
	public int getFoodLevel() {
		return p.getFoodLevel() ;
	}

	@Override
	public double getHealthScale() {
		return p.getHealthScale();
	}

	@Override
	public int getLevel() {
		return p.getLevel();
	}

	@Override
	public String getLocale() {
		return p.getLocale();
	}

	@Override
	public String getPlayerListFooter() {
		return p.getPlayerListFooter();
	}

	@Override
	public String getPlayerListHeader() {
		return p.getPlayerListHeader();
	}

	@Override
	public  String getPlayerListName() {
		return p.getPlayerListName();
	}

	@Override
	public long getPlayerTime() {
		return p.getPlayerTime();
	}

	@Override
	public long getPlayerTimeOffset() {
		return p.getPlayerTimeOffset();
	}

	@Override
	public WeatherType getPlayerWeather() {
		return p.getPlayerWeather();
	}

	@Override
	public float getSaturation() {
		return p.getSaturation();
	}

	@Override
	public  Scoreboard getScoreboard() {
		return p.getScoreboard();
	}

	@Override
	public Entity getSpectatorTarget() {
		return p.getSpectatorTarget();
	}

	@Override
	public int getTotalExperience() {
		return p.getTotalExperience();
	}

	@Override
	public float getWalkSpeed() {
		return p.getWalkSpeed();
	}

	@Override
	public void giveExp(int arg0) {
		p.giveExpLevels(arg0);
		
	}

	@Override
	public void giveExpLevels(int arg0) {
		p.giveExpLevels(arg0);
		
	}

	@Override
	public void hidePlayer( Player arg0) {
		p.hidePlayer(arg0);
		
	}

	@Override
	public void hidePlayer( Plugin arg0,  Player arg1) {
		p.hidePlayer(arg0, arg1);
		
	}

	@Override
	public boolean isFlying() {
		return p.isFlying();
	}

	@Override
	public boolean isHealthScaled() {
		return p.isHealthScaled();
	}

	@Override
	public boolean isPlayerTimeRelative() {
		return p.isPlayerTimeRelative();
	}

	@Override
	public boolean isSleepingIgnored() {
		return p.isSleepingIgnored();
	}

	@Override
	public boolean isSneaking() {
		return p.isSneaking();
	}

	@Override
	public boolean isSprinting() {
		return p.isSprinting();
	}

	@Override
	public void kickPlayer(String arg0) {
		p.kickPlayer(arg0);
		
	}

	@Override
	public void loadData() {
		p.loadData();
		
	}

	@Override
	public void openBook( ItemStack arg0) {
		p.openBook(arg0);
		
	}

	@Override
	public boolean performCommand( String arg0) {
		return p.performCommand(arg0);
	}

	@Override
	public void playEffect( Location arg0,  Effect arg1, int arg2) {
		p.playEffect(arg0, arg1, arg2);
		
	}

	@Override
	public <T> void playEffect( Location arg0,  Effect arg1,T arg2) {
		p.playEffect(arg0, arg1, arg2);
		
	}

	@Override
	public void playNote( Location arg0, byte arg1, byte arg2) {
		p.playNote(arg0, arg1, arg2);
		
	}

	@Override
	public void playNote( Location arg0,  Instrument arg1,  Note arg2) {
		p.playNote(arg0, arg1, arg2);
		
	}

	public void playSound(Sound sound, float volume, float pitch) {
		p.playSound(p.getLocation(), sound, volume, pitch);
		
	}
	
	@Override
	public void playSound( Location arg0,  Sound arg1, float arg2, float arg3) {
		p.playSound(arg0, arg1, arg2, arg3);
		
	}

	@Override
	public void playSound( Location arg0,  String arg1, float arg2, float arg3) {
		p.playSound(arg0, arg1, arg2, arg3);
		
	}

	@Override
	public void playSound( Location arg0,  Sound arg1,  SoundCategory arg2, float arg3,
			float arg4) {
		p.playSound(arg0, arg1, arg2, arg3, arg4);
		
	}

	@Override
	public void playSound( Location arg0,  String arg1,  SoundCategory arg2, float arg3,
			float arg4) {
		p.playSound(arg0, arg1, arg2, arg3, arg4);
		
	}

	@Override
	public void resetPlayerTime() {
		p.resetPlayerTime();
		
	}

	@Override
	public void resetPlayerWeather() {
		p.resetPlayerWeather();
		
	}

	@Override
	public void resetTitle() {
		p.resetTitle();
		
	}

	@Override
	public void saveData() {
		p.saveData();
		
	}

	@Override
	public void sendBlockChange( Location arg0,  BlockData arg1) {
		p.sendBlockChange(arg0, arg1);
		
	}

	@Override
	public void sendBlockChange( Location arg0,  Material arg1, byte arg2) {
		p.sendBlockChange(arg0, arg1, arg2);
		
	}

	@Override
	public boolean sendChunkChange( Location arg0, int arg1, int arg2, int arg3,  byte[] arg4) {
		return p.sendChunkChange(arg0, arg1, arg2, arg3, arg4);
	}

	@Override
	public void sendExperienceChange(float arg0) {
		p.sendExperienceChange(arg0);
		
	}

	@Override
	public void sendExperienceChange(float arg0, int arg1) {
		p.sendExperienceChange(arg0, arg1);
		
	}

	@Override
	public void sendMap( MapView arg0) {
		p.sendMap(arg0);
	}

	@Override
	public void sendRawMessage( String arg0) {
		p.sendRawMessage(arg0);
		
	}

	@Override
	public void sendSignChange( Location arg0,String[] arg1) throws IllegalArgumentException {
		p.sendSignChange(arg0, arg1);
	}

	@Override
	public void sendSignChange( Location arg0,String[] arg1,  DyeColor arg2)
			throws IllegalArgumentException {
		p.sendSignChange(arg0, arg1, arg2);
		
	}

	@Override
	public void sendTitle(String arg0,String arg1) {
		p.sendTitle(arg0, arg1);
		
	}

	@Override
	public void sendTitle(String arg0,String arg1, int arg2, int arg3, int arg4) {
		p.sendTitle(arg0, arg1, arg2, arg3, arg4);
		
	}

	@Override
	public void setAllowFlight(boolean arg0) {
		p.setAllowFlight(arg0);
		
	}

	@Override
	public void setBedSpawnLocation(Location arg0) {
		p.setBedSpawnLocation(arg0);
		
	}

	@Override
	public void setBedSpawnLocation(Location arg0, boolean arg1) {
		p.setBedSpawnLocation(arg0, arg1);
		
	}

	@Override
	public void setCompassTarget( Location arg0) {
		p.setCompassTarget(arg0);
		
	}

	@Override
	public void setDisplayName(String arg0) {
		p.setDisplayName(arg0);
		
	}

	@Override
	public void setExhaustion(float arg0) {
		p.setExhaustion(arg0);
		
	}

	@Override
	public void setExp(float arg0) {
		p.setExp(arg0);
		
	}

	@Override
	public void setFlySpeed(float arg0) throws IllegalArgumentException {
		p.setFlySpeed(arg0);
		
	}

	@Override
	public void setFlying(boolean arg0) {
		p.setFlying(arg0);
		
	}

	@Override
	public void setFoodLevel(int arg0) {
		p.setFoodLevel(arg0);
		
	}

	@Override
	public void setHealthScale(double arg0) throws IllegalArgumentException {
		p.setHealthScale(arg0);
		
	}

	@Override
	public void setHealthScaled(boolean arg0) {
		p.setHealthScaled(arg0);
		
	}

	@Override
	public void setLevel(int arg0) {
		p.setLevel(arg0);
		
	}

	@Override
	public void setPlayerListFooter(String arg0) {
		p.setPlayerListFooter(arg0);
		
	}

	@Override
	public void setPlayerListHeader(String arg0) {
		p.setPlayerListHeader(arg0);
		
	}

	@Override
	public void setPlayerListHeaderFooter(String arg0,String arg1) {
		p.setPlayerListHeaderFooter(arg0, arg1);
		
	}

	@Override
	public void setPlayerListName(String arg0) {
		p.setPlayerListName(arg0);
		
	}

	@Override
	public void setPlayerTime(long arg0, boolean arg1) {
		p.setPlayerTime(arg0, arg1);
	}

	@Override
	public void setPlayerWeather( WeatherType arg0) {
		p.setPlayerWeather(arg0);
	}

	@Override
	public void setResourcePack( String arg0) {
		p.setResourcePack(arg0);
	}

	@Override
	public void setResourcePack( String arg0,  byte[] arg1) {
		p.setResourcePack(arg0, arg1);
	}

	@Override
	public void setSaturation(float arg0) {
		p.setSaturation(arg0);
	}

	@Override
	public void setScoreboard( Scoreboard arg0) throws IllegalArgumentException, IllegalStateException {
		p.setScoreboard(arg0);
	}

	@Override
	public void setSleepingIgnored(boolean arg0) {
		p.setSleepingIgnored(arg0);
	}

	@Override
	public void setSneaking(boolean arg0) {
		p.setSneaking(arg0);
	}

	@Override
	public void setSpectatorTarget(Entity arg0) {
		p.setSpectatorTarget(arg0);
	}

	@Override
	public void setSprinting(boolean arg0) {
		p.setSprinting(arg0);
	}

	@Override
	public void setTexturePack( String arg0) {
		p.setTexturePack(arg0);
	}

	@Override
	public void setTotalExperience(int arg0) {
		p.setTotalExperience(arg0);
	}

	@Override
	public void setWalkSpeed(float arg0) throws IllegalArgumentException {
		p.setWalkSpeed(arg0);
	}

	@Override
	public void showPlayer( Player arg0) {
		p.showPlayer(arg0);
	}

	@Override
	public void showPlayer( Plugin arg0,  Player arg1) {
		p.showPlayer(arg0, arg1);
	}

	@Override
	public void spawnParticle( Particle arg0,  Location arg1, int arg2) {
		p.spawnParticle(arg0, arg1, arg2);
	}

	@Override
	public <T> void spawnParticle( Particle arg0,  Location arg1, int arg2,T arg3) {
		p.spawnParticle(arg0, arg1, arg2, arg3);
	}

	@Override
	public void spawnParticle( Particle arg0, double arg1, double arg2, double arg3, int arg4) {
		p.spawnParticle(arg0, arg1, arg2, arg3, arg4);
	}

	@Override
	public <T> void spawnParticle( Particle arg0, double arg1, double arg2, double arg3, int arg4, T arg5) {
		p.spawnParticle(arg0, arg1, arg2, arg3, arg4, arg5);
	}

	@Override
	public void spawnParticle( Particle arg0,  Location arg1, int arg2, double arg3, double arg4,
			double arg5) {
		p.spawnParticle(arg0, arg1, arg2, arg3, arg4, arg5);
	}

	@Override
	public <T> void spawnParticle( Particle arg0,  Location arg1, int arg2, double arg3, double arg4,
			double arg5,T arg6) {
		p.spawnParticle(arg0, arg1, arg2, arg3, arg4, arg5, arg6);
	}

	@Override
	public void spawnParticle( Particle arg0,  Location arg1, int arg2, double arg3, double arg4,
			double arg5, double arg6) {
		p.spawnParticle(arg0, arg1, arg2, arg3, arg4, arg5, arg6);
	}

	@Override
	public void spawnParticle( Particle arg0, double arg1, double arg2, double arg3, int arg4, double arg5,
			double arg6, double arg7) {
		p.spawnParticle(arg0, arg1, arg2, arg3, arg4, arg5, arg6, arg7);
	}

	@Override
	public <T> void spawnParticle( Particle arg0,  Location arg1, int arg2, double arg3, double arg4,
			double arg5, double arg6,T arg7) {
		p.spawnParticle(arg0, arg1, arg2, arg3, arg4, arg5, arg6, arg7);
	}

	@Override
	public <T> void spawnParticle( Particle arg0, double arg1, double arg2, double arg3, int arg4, double arg5,
			double arg6, double arg7,T arg8) {
		p.spawnParticle(arg0, arg1, arg2, arg3, arg4, arg5, arg6, arg7, arg8);
	}

	@Override
	public void spawnParticle( Particle arg0, double arg1, double arg2, double arg3, int arg4, double arg5,
			double arg6, double arg7, double arg8) {
		p.spawnParticle(arg0, arg1, arg2, arg3, arg4, arg5, arg6, arg7, arg8);
	}

	@Override
	public <T> void spawnParticle( Particle arg0, double arg1, double arg2, double arg3, int arg4, double arg5,
			double arg6, double arg7, double arg8,T arg9) {
		p.spawnParticle(arg0, arg1, arg2, arg3, arg4, arg5, arg6, arg7, arg8, arg9);
		
	}

	@Override
	public  Spigot spigot() {
		return p.spigot();
	}

	@Override
	public void stopSound( Sound arg0) {
		p.stopSound(arg0);
		
	}

	@Override
	public void stopSound( String arg0) {
		p.stopSound(arg0);
	}

	@Override
	public void stopSound( Sound arg0,SoundCategory arg1) {
		p.stopSound(arg0, arg1);
	}

	@Override
	public void stopSound( String arg0,SoundCategory arg1) {
		p.stopSound(arg0, arg1);
	}

	@Override
	public void updateCommands() {
		p.updateInventory();
	}

	@Override
	public void updateInventory() {
		p.updateInventory();
	}

	@Override
	public boolean dropItem(boolean arg0) {
		return p.dropItem(arg0);
	}

	@Override
	public Set<NamespacedKey> getDiscoveredRecipes() {
		return p.getDiscoveredRecipes();
	}

	@Override
	public boolean hasDiscoveredRecipe(NamespacedKey arg0) {
		return p.hasDiscoveredRecipe(arg0);
	}

	@Override
	public int getArrowCooldown() {
		return p.getArrowCooldown();
	}

	@Override
	public int getArrowsInBody() {
		return p.getArrowsInBody();
	}

	@Override
	public EntityCategory getCategory() {
		return p.getCategory();
	}

	@Override
	public void setArrowCooldown(int arg0) {
		p.setArrowCooldown(arg0);
	}

	@Override
	public void setArrowsInBody(int arg0) {
		p.setArrowsInBody(arg0);
	}

	@Override
	public boolean isInvisible() {
		return p.isInvisible();
	}

	@Override
	public void setInvisible(boolean arg0) {
		p.setInvisible(arg0);
	}

	@Override
	public void sendMessage(UUID arg0, String arg1) {
		p.sendMessage(arg0, arg1);
	}

	@Override
	public void sendMessage(UUID arg0, String[] arg1) {
		p.sendMessage(arg0, arg1);
	}

	@Override
	public void sendRawMessage(UUID arg0, String arg1) {
		p.sendRawMessage(arg0, arg1);
	}

	@Override
	public boolean isInWater() {
		return p.isInWater();
	}

	@Override
	public long getLastPlayed() {
		return p.getLastPlayed();
	}

	@Override
	public int getSaturatedRegenRate() {
		return p.getSaturatedRegenRate();
	}

	@Override
	public int getStarvationRate() {
		return p.getStarvationRate();
	}

	@Override
	public int getUnsaturatedRegenRate() {
		return p.getUnsaturatedRegenRate();
	}

	@Override
	public void setSaturatedRegenRate(int arg0) {
		p.setSaturatedRegenRate(arg0);
	}

	@Override
	public void setStarvationRate(int arg0) {
		p.setStarvationRate(arg0);
	}

	@Override
	public void setUnsaturatedRegenRate(int arg0) {
		p.setUnsaturatedRegenRate(arg0);
	}

	@Override
	public void sendBlockDamage(Location arg0, float arg1) {
		sendBlockDamage(arg0, arg1);
	}

	
}
