package net.natroutter.natlibs.handlers;

import net.natroutter.natlibs.commands.SoundTester;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;

public class CommonHandler implements Listener {


    @EventHandler
    public void onQuit(PlayerQuitEvent e) {
        Player p = e.getPlayer();
        SoundTester.Category.remove(p.getUniqueId());
        SoundTester.LastSound.remove(p.getUniqueId());
        SoundTester.GUI.remove(p.getUniqueId());
        SoundTester.Pitch.remove(p.getUniqueId());
    }


}
