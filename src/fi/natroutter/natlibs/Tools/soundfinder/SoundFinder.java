package fi.natroutter.natlibs.Tools.soundfinder;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.checkerframework.checker.units.qual.Current;
import org.jetbrains.annotations.NotNull;

public class SoundFinder extends Command {
    protected SoundFinder(@NotNull String name) {
        super(name);
    }

    @Override
    public boolean execute(@NotNull CommandSender sender, @NotNull String commandLabel, @NotNull String[] args) {
        return false;
    }

    // kun tää on valmis niin git commit and push
//
//    gui mis on äänet eri gatecorioissa ja ja sit niitä voi selata läpi alas bapit että voi play eri pitch
//
//                                                                              [pitch up]
//                     [Previous]   [current sound info] [Next sound]
//                                                                            [Current_pitch]
//                    [play Pitch 0.5] [play Pitch 1] [play Pitch 2]
//                                                                             [pitch down]


    //joka äänelle oma gatecoria ja sit yks mis on kaikki maholliset
    //jotenkin maholliseks myös saada resourcepack custom soundit!
}
