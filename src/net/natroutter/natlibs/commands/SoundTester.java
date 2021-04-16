package net.natroutter.natlibs.commands;

import java.text.DecimalFormat;
import java.util.HashMap;
import java.util.UUID;
import java.util.ArrayList;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemFlag;

import net.natroutter.natlibs.NATLibs;
import net.natroutter.natlibs.handlers.gui.GUIItem;
import net.natroutter.natlibs.handlers.gui.GUIWindow;
import net.natroutter.natlibs.handlers.gui.GUIWindow.Rows;
import net.natroutter.natlibs.objects.BaseItem;
import net.natroutter.natlibs.objects.BasePlayer;
import net.natroutter.natlibs.utilities.SkullCreator;

public class SoundTester extends Command {

	String prefix = "§4§lNATLibs §8§l§ ";
	
	public SoundTester() {
		super("");
		this.setPermission("natlibs.soundtester");
		this.setPermissionMessage(prefix + "§7You don't have permissions to use this command!");
	}
	
	//Create Skulls
	SkullCreator skull = new SkullCreator();
	
	BaseItem ArrowUp = skull.Create("§c§lUp", "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvMTRhNTY2N2VmNzI4NWM5MjI1ZmMyNjdkNDUxMTdlYWI1NDc4Yzc4NmJkNWFmMGExOTljMjlhMmMxNGMxZiJ9fX0=");
	BaseItem ArrowDown = skull.Create("§c§lDown", "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvZDFiNjJkYjVjMGEzZmExZWY0NDFiZjcwNDRmNTExYmU1OGJlZGY5YjY3MzE4NTNlNTBjZTkwY2Q0NGZiNjkifX19");
	BaseItem ArrowLeft = skull.Create("§c§lPrevious", "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvNTQyZmRlOGI4MmU4YzFiOGMyMmIyMjY3OTk4M2ZlMzVjYjc2YTc5Nzc4NDI5YmRhZGFiYzM5N2ZkMTUwNjEifX19");
	BaseItem ArrowRight = skull.Create("§c§lNext", "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvNDA2MjYyYWYxZDVmNDE0YzU5NzA1NWMyMmUzOWNjZTE0OGU1ZWRiZWM0NTU1OWEyZDZiODhjOGQ2N2I5MmVhNiJ9fX0=");
	
	BaseItem AmbientSounds = skull.Create("§c§lAmbient Sounds", "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvZmY5YmY1MzgzZmNlNTQzZTRhYTg4MzVhZjhlY2FlZDY3MDI5ZmQwYWFiYjAzYWMwZmY1YTJlZGVjZWUyY2U0ZCJ9fX0=");
	BaseItem BlockSounds = skull.Create("§c§lBlock Sounds", "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvODQ0OWI5MzE4ZTMzMTU4ZTY0YTQ2YWIwZGUxMjFjM2Q0MDAwMGUzMzMyYzE1NzQ5MzJiM2M4NDlkOGZhMGRjMiJ9fX0=");
	BaseItem EntitySounds = skull.Create("§c§lEntity Sounds", "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvNDE2NDVkZmQ3N2QwOTkyMzEwN2IzNDk2ZTk0ZWViNWMzMDMyOWY5N2VmYzk2ZWQ3NmUyMjZlOTgyMjQifX19");
	BaseItem ItemSounds = skull.Create("§c§lItem Sounds", "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvMmVlNGE1Y2Q0ZWU2ZTk4OWE2M2RjNDFjNGI0MGQ4M2YwZDU4NTk4ZTdlY2RmMmM5NGRmZWVjMGFkYTAyZWM5MyJ9fX0=");
	BaseItem MiscSounds = skull.Create("§c§lMisc Sounds", "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvNGNlZWI3N2Q0ZDI1NzI0YTljYWYyYzdjZGYyZDg4Mzk5YjE0MTdjNmI5ZmY1MjEzNjU5YjY1M2JlNDM3NmUzIn19fQ==");
	
	BaseItem PitchDisplay = skull.Create("§7§lPitch: 1.0", "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvMjdiOTk1ZjU5NDVjYmY0ZGUyOWY3OGEwY2QyYWNkNDRjYzI4ODczM2Y0ZGU1MTJmZTcyY2E0Y2IxOTgzYzQyNCJ9fX0=");
	
	public enum SoundCategory {
		Ambient,Block,Entity,Item,Misc;
		
	    public SoundCategory next() {
	    	return values()[(this.ordinal()+1) % values().length];
	    }
	    public SoundCategory previous() {
	    	return values()[ordinal() > 0 ? ordinal() - 1 : (values().length - 1)];
	    }
	}
	
	HashMap<UUID, SoundCategory> Category = new HashMap<UUID, SoundCategory>();
	HashMap<UUID, Float> Pitch = new HashMap<UUID, Float>();
	HashMap<UUID, GUIWindow> GUI = new HashMap<UUID, GUIWindow>();
	HashMap<UUID, Sound> LastSound = new HashMap<UUID, Sound>();
	
	ArrayList<Sound> AmbientSoundList = new ArrayList<Sound>();
	ArrayList<Sound> BlockSoundList = new ArrayList<Sound>();
	ArrayList<Sound> EntitySoundList = new ArrayList<Sound>();
	ArrayList<Sound> ItemSoundList = new ArrayList<Sound>();
	ArrayList<Sound> MiscSoundList = new ArrayList<Sound>();
	
	public void ConstructSoundLists() {
		for (Sound sound : Sound.values()) {
			if (sound.name().startsWith("AMBIENT_")) {
				AmbientSoundList.add(sound);
				
			} else if (sound.name().startsWith("BLOCK_")) {
				BlockSoundList.add(sound);
				
			} else if (sound.name().startsWith("ENTITY_")) {
				EntitySoundList.add(sound);
				
			} else if (sound.name().startsWith("ITEM_")) {
				ItemSoundList.add(sound);
				
			} else {
				MiscSoundList.add(sound);
				
			}
		}
	}
	
	public Sound getLastSound(BasePlayer p) {
		if (!LastSound.containsKey(p.getUniqueId())) {
			Sound s = getSoundList(getCategory(p)).get(0);
			LastSound.put(p.getUniqueId(), s);
		}
		return LastSound.get(p.getUniqueId());
	}
	
	public Integer getLastSoundIndex(BasePlayer p) {
		int index = 0;
		ArrayList<Sound> list = getSoundList(getCategory(p));
		for (int i = 0; i < list.size(); i++) {
			Sound sound = list.get(i);
			if (sound.equals(getLastSound(p))) {
				index = i;
				break;
			}
		}
		return index;
	}
	
	public void resetLastSound(BasePlayer p) {
		p.stopSound(getLastSound(p));
		LastSound.remove(p.getUniqueId());
	}
	
	public ArrayList<Sound> getSoundList(SoundCategory cat) {
		switch(cat) {
			case Ambient: return AmbientSoundList;
			case Block: return BlockSoundList;
			case Entity: return EntitySoundList;
			case Misc: return MiscSoundList;
			case Item: return ItemSoundList;
			
			default: return AmbientSoundList;
		}
	}
	
	public GUIWindow getGUI(BasePlayer p) {
		if (!GUI.containsKey(p.getUniqueId())) {
			GUI.put(p.getUniqueId(), new GUIWindow("§4§lSoundTester", Rows.row3, true, false, false));
		}
		return GUI.get(p.getUniqueId());
	}
	
	public BaseItem iconItem(Material mat, String name) {
		BaseItem item = new BaseItem(mat);
		item.setDisplayName(name);
		item.addItemFlags(ItemFlag.values());
		return item;
	}
	
	
	public void PreviousSound(BasePlayer p) {
		if (!Category.containsKey(p.getUniqueId())) {
			Category.put(p.getUniqueId(), SoundCategory.Ambient);
		}
		SoundCategory cat = Category.get(p.getUniqueId());
		float pitch = getPitch(p);
		
		int nextIndex = getLastSoundIndex(p) - 1;
		ArrayList<Sound> list = getSoundList(cat);
		
		if (nextIndex <= 0) {
			nextIndex = list.size();
		}
		Sound nextSound = list.get(nextIndex);
		PlaySound(p, nextSound, pitch);
	}
	
	public void NextSound(BasePlayer p) {
		if (!Category.containsKey(p.getUniqueId())) {
			Category.put(p.getUniqueId(), SoundCategory.Ambient);
		}
		SoundCategory cat = Category.get(p.getUniqueId());
		float pitch = getPitch(p);
		
		int nextIndex = getLastSoundIndex(p) + 1;
		ArrayList<Sound> list = getSoundList(cat);
		
		if (nextIndex >= list.size()) {
			nextIndex = 0;
		}
		Sound nextSound = list.get(nextIndex);
		PlaySound(p, nextSound, pitch);
	}
	
	public void PlaySound(BasePlayer p, Sound sound, Float pitch) {
		Sound last = getLastSound(p);
		p.stopSound(last);
		
		p.playSound(p.getLocation(), sound, 1, pitch);
		
		LastSound.put(p.getUniqueId(), sound);
		updateInv(p);
	}
	
	public void PitchUp(BasePlayer p) {
		if (!Pitch.containsKey(p.getUniqueId())) {
			Pitch.put(p.getUniqueId(), 1.0f);
		}
		Float pitch = Pitch.get(p.getUniqueId());
		Float newPitch = pitch + 0.1f;
		
		if (newPitch >= 2.0f) {
			newPitch = 2.0f;
		}
		
		Pitch.put(p.getUniqueId(), newPitch);
		updateInv(p);
	}
	
	public void PitchDown(BasePlayer p) {
		if (!Pitch.containsKey(p.getUniqueId())) {
			Pitch.put(p.getUniqueId(), 1.0f);
		}
		Float pitch = Pitch.get(p.getUniqueId());
		float newPitch = pitch - 0.1f;
		
		if (newPitch <= 0.1f) {
			newPitch = 0.1f;
		}
		
		Pitch.put(p.getUniqueId(), newPitch);
		updateInv(p);
	}
	
	public Float getPitch(BasePlayer p) {
		if (!Pitch.containsKey(p.getUniqueId())) {
			Pitch.put(p.getUniqueId(), 1.0f);
		}
		DecimalFormat df = new DecimalFormat("#.#");
		float pitch = Pitch.get(p.getUniqueId());
		
		return Float.parseFloat(df.format(pitch));
		
	}
	
	public void NextCategory(BasePlayer p) {
		if (!Category.containsKey(p.getUniqueId())) {
			Category.put(p.getUniqueId(), SoundCategory.Ambient);
		}
		SoundCategory cat = Category.get(p.getUniqueId());
		Category.put(p.getUniqueId(), cat.next());
		resetLastSound(p);
		updateInv(p);
	}
	
	public void PreviousCategory(BasePlayer p) {
		if (!Category.containsKey(p.getUniqueId())) {
			Category.put(p.getUniqueId(), SoundCategory.Ambient);
		}
		SoundCategory cat = Category.get(p.getUniqueId());
		Category.put(p.getUniqueId(), cat.previous());
		resetLastSound(p);
		updateInv(p);
	}
	
	public SoundCategory getCategory(BasePlayer p) {
		if (!Category.containsKey(p.getUniqueId())) {
			Category.put(p.getUniqueId(), SoundCategory.Ambient);
		}
		return Category.get(p.getUniqueId());
	}
	
	public BaseItem getCategoryItem(BasePlayer p) {
		if (!Category.containsKey(p.getUniqueId())) {
			Category.put(p.getUniqueId(), SoundCategory.Ambient);
		}
		SoundCategory cat = Category.get(p.getUniqueId());
		
		switch(cat) {
			case Ambient:
				return AmbientSounds;
			case Block:
				return BlockSounds;
			case Entity:
				return EntitySounds;
			case Item:
				return ItemSounds;
			case Misc:
				return MiscSounds;
		}
		return AmbientSounds;
	}
	
	
	public void updateInv(BasePlayer p) {
		CreateGUI(p);
	}
	
	public void show(BasePlayer p) {
		ConstructSoundLists();
		p.closeInventory();
		CreateGUI(p).show(p, true);
	}
	
	public GUIWindow CreateGUI(BasePlayer p) {
		GUIWindow gui = getGUI(p);
		
		//Sounds controls
		gui.setItem(new GUIItem(ArrowLeft, (e)->{
			PreviousSound(p);
		}), Rows.row2, 2);
		
		gui.setItem(new GUIItem(getCategoryItem(p).setLore("§7Sound: §c" + getLastSound(p)), (e)->{
			PlaySound(p, getLastSound(p), getPitch(p));
		}), Rows.row2, 3);
		
		gui.setItem(new GUIItem(ArrowRight, (e)->{
			NextSound(p);
		}), Rows.row2, 4);
		
		
		//pitch controls
		gui.setItem(new GUIItem(ArrowUp.clone().setDisplayName("§c§lIncrease Pitch §8(" + getPitch(p) + ")"), (e)->{
			PitchUp(p);
		}), Rows.row1, 8);
		
		gui.setItem(new GUIItem(PitchDisplay.setDisplayName("§7§lPitch: §c§l" + getPitch(p)), (e)->{
		}), Rows.row2, 8);
		
		gui.setItem(new GUIItem(ArrowDown.clone().setDisplayName("§c§lDecrease Pitch §8(" + getPitch(p) + ")"), (e)->{
			PitchDown(p);
		}), Rows.row3, 8);
		
		
		//category controls
		gui.setItem(new GUIItem(ArrowUp, (e)->{
			NextCategory(p);
		}), Rows.row1, 7);
		
		gui.setItem(new GUIItem(getCategoryItem(p).setDisplayName("§7§lCategory: §c§l" + getCategory(p).name()), (e)->{
		}), Rows.row2, 7);
		
		gui.setItem(new GUIItem(ArrowDown, (e)->{
			PreviousCategory(p);
		}), Rows.row3, 7);
		
		return gui;
	}

	@Override
	public boolean execute(CommandSender sender, String label, String[] args) {
		if (!(sender instanceof Player)) {
			sender.sendMessage(prefix + "§7This command can only be used ingame!");
			return false;
		}
		BasePlayer p = BasePlayer.from(sender);
		if (args.length == 0) {
			show(p);
		} else {
			sender.sendMessage(prefix + "§7Too many arguments!");
		}
		return false;
	}

	
	
	
}
