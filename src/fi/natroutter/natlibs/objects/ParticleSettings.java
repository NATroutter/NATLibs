package fi.natroutter.natlibs.objects;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.bukkit.Location;
import org.bukkit.Particle;

@Getter
@Setter
@AllArgsConstructor
public class ParticleSettings {

	private Particle particle;
	private int count;
	private double OffsetX;
	private double OffsetY;
	private double OffsetZ;
	private double Speed;
	private Particle.DustOptions dustOptions;

	public ParticleSettings(Particle particle, int count, double OffsetX, double OffsetY, double OffsetZ, double Speed) {
		this.particle = particle;
		this.count = count;
		this.OffsetX = OffsetX;
		this.OffsetY = OffsetY;
		this.OffsetZ = OffsetZ;
		this.Speed = Speed;
	}

	public ParticleSettings(ParticleSettings settings, Particle particle) {
		this.particle = particle;
		this.count = settings.getCount();
		this.OffsetX = settings.getOffsetX();
		this.OffsetY = settings.getOffsetY();
		this.OffsetZ = settings.getOffsetZ();
		this.Speed = settings.getSpeed();
		this.dustOptions = settings.getDustOptions();
	}
	
	public ParticleSettings(ParticleSettings settings) {
		this.particle = settings.getParticle();
		this.count = settings.getCount();
		this.OffsetX = settings.getOffsetX();
		this.OffsetY = settings.getOffsetY();
		this.OffsetZ = settings.getOffsetZ();
		this.Speed = settings.getSpeed();
		this.dustOptions = settings.getDustOptions();
	}

}
