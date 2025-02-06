package fi.natroutter.natlibs.objects;

import fi.natroutter.natlibs.configuration.IConfig;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor @Getter @Setter
public class Complete{

    private String arg;
    private String permission;

    public Complete(IConfig arg, IConfig perm) {
        this.arg = arg.asString();
        this.permission = perm.asString();
    }

}
