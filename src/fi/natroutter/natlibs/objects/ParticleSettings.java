package fi.natroutter.natlibs.objects;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.bukkit.Color;
import org.bukkit.Material;
import org.bukkit.Particle;
import org.bukkit.inventory.ItemStack;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ParticleSettings {

	private Particle particle = Particle.FLAME;
	private int count = 1;
	private double offsetX = 0;
	private double offsetY = 0;
	private double offsetZ = 0;
	private double speed  = 0;
	private Particle.DustOptions dustOptions = new Particle.DustOptions(Color.fromRGB(255,255,255), 1);
	private Particle.DustTransition dustTransition = new Particle.DustTransition(Color.fromRGB(255,255,255), Color.fromRGB(0,0,0), 1);
	private ItemStack item = new ItemStack(Material.REDSTONE_BLOCK);
	private int note = 6;
	private Color spellColor = Color.fromRGB(255,0,0);

	public ParticleSettings(Particle particle, int count, double OffsetX, double OffsetY, double OffsetZ, double Speed) {
		this.particle = particle;
		this.count = count;
		this.offsetX = OffsetX;
		this.offsetY = OffsetY;
		this.offsetZ = OffsetZ;
		this.speed = Speed;
	}
	public ParticleSettings(Particle particle, int count, double OffsetX, double OffsetY, double OffsetZ, double Speed, Particle.DustOptions dustOptions) {
		this.particle = particle;
		this.count = count;
		this.offsetX = OffsetX;
		this.offsetY = OffsetY;
		this.offsetZ = OffsetZ;
		this.speed = Speed;
		this.dustOptions = dustOptions;
	}

	public ParticleSettings(ParticleSettings settings, Particle particle) {
		this.particle = particle;
		this.count = settings.getCount();
		this.offsetX = settings.getOffsetX();
		this.offsetY = settings.getOffsetY();
		this.offsetZ = settings.getOffsetZ();
		this.speed = settings.getSpeed();
		this.dustOptions = settings.getDustOptions();
		this.dustTransition = settings.getDustTransition();
		this.item = settings.getItem();
		this.note = settings.getNote();
		this.spellColor = settings.getSpellColor();
	}
	
	public ParticleSettings(ParticleSettings settings) {
		this.particle = settings.getParticle();
		this.count = settings.getCount();
		this.offsetX = settings.getOffsetX();
		this.offsetY = settings.getOffsetY();
		this.offsetZ = settings.getOffsetZ();
		this.speed = settings.getSpeed();
		this.dustOptions = settings.getDustOptions();
		this.dustTransition = settings.getDustTransition();
		this.item = settings.getItem();
		this.note = settings.getNote();
		this.spellColor = settings.getSpellColor();

	}

}
