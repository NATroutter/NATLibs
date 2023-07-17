package fi.natroutter.natlibs.objects;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.bukkit.enchantments.Enchantment;

@Getter @Setter
@AllArgsConstructor
public class Enchant {

    private Enchantment enchant;
    private Integer level;

}
