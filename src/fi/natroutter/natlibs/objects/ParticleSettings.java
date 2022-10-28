package fi.natroutter.natlibs.objects;

import org.bukkit.Location;
import org.bukkit.Particle;

public class ParticleSettings {

	private Particle particle;
	private Location loc;
	private int count;
	private double OffsetX;
	private double OffsetY;
	private double OffsetZ;
	private double Speed;
	
	public ParticleSettings(Particle particle, Location loc, ParticleSettings settings) {
		this.particle = particle;
		this.loc = loc;
		this.count = settings.getCount();
		this.OffsetX = settings.getOffsetX();
		this.OffsetY = settings.getOffsetY();
		this.OffsetZ = settings.getOffsetZ();
		this.Speed = settings.getSpeed();
	}
	
	public ParticleSettings(ParticleSettings settings) {
		this.particle = settings.getParticle();
		this.loc = settings.getLoc();
		this.count = settings.getCount();
		this.OffsetX = settings.getOffsetX();
		this.OffsetY = settings.getOffsetY();
		this.OffsetZ = settings.getOffsetZ();
		this.Speed = settings.getSpeed();
	}
	
	public ParticleSettings(int count, double OffsetX, double OffsetY, double OffsetZ, double Speed) {
		this.count = count;
		this.OffsetX = OffsetX;
		this.OffsetY = OffsetY;
		this.OffsetZ = OffsetZ;
		this.Speed = Speed;
	}
	
	public ParticleSettings(Particle particle, Location loc, int count, double OffsetX, double OffsetY, double OffsetZ, double Speed) {
		this.particle = particle;
		this.loc = loc;
		this.count = count;
		this.OffsetX = OffsetX;
		this.OffsetY = OffsetY;
		this.OffsetZ = OffsetZ;
		this.Speed = Speed;
	}
	
	
	//Getters
	public Particle getParticle() {
		return particle;
	}
	
	public Location getLoc() {
		return loc;
	}
	
	public int getCount() {
		return count;
	}
	
	public double getOffsetX() {
		return OffsetX;
	}
	
	public double getOffsetY() {
		return OffsetY;
	}
	
	public double getOffsetZ() {
		return OffsetZ;
	}
	
	public double getSpeed() {
		return Speed;
	}
	
	
	
	//Setters
	public void setParticle(Particle particle) {
		this.particle = particle;
	}
	
	public void setLoc(Location loc) {
		this.loc = loc;
	}
	
	public void setCount(int count) {
		this.count = count;
	}
	
	public void setOffsetX(double offsetX) {
		OffsetX = offsetX;
	}
	
	public void setOffsetY(double offsetY) {
		OffsetY = offsetY;
	}
	
	public void setOffsetZ(double offsetZ) {
		OffsetZ = offsetZ;
	}
	
	public void setSpeed(double speed) {
		Speed = speed;
	}
	
	
	
}
