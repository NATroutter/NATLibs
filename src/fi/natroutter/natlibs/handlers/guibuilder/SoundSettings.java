package fi.natroutter.natlibs.handlers.guibuilder;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.bukkit.Sound;
import org.bukkit.SoundCategory;

@Getter @Setter
public class SoundSettings {

    private boolean EnumSound;
    private String strSound;
    private Sound sound;
    private float volume;
    private float pitch;
    private SoundCategory category;

    public SoundSettings(Sound sound, float volume, float pitch) {
        this.EnumSound = true;
        this.sound = sound;
        this.volume = volume;
        this.pitch = pitch;
        this.category = SoundCategory.MASTER;
    }

    public SoundSettings(String sound, float volume, float pitch) {
        this.EnumSound = false;
        this.strSound = sound;
        this.volume = volume;
        this.pitch = pitch;
        this.category = SoundCategory.MASTER;
    }

    public SoundSettings(Sound sound, SoundCategory category, float volume, float pitch) {
        this.EnumSound = true;
        this.sound = sound;
        this.volume = volume;
        this.pitch = pitch;
        this.category = category;
    }

    public SoundSettings(String sound, SoundCategory category, float volume, float pitch) {
        this.EnumSound = false;
        this.strSound = sound;
        this.volume = volume;
        this.pitch = pitch;
        this.category = category;
    }

}
