package fi.natroutter.natlibs.handlers.fancyfont.fonts;

import fi.natroutter.natlibs.handlers.fancyfont.FancyChar;

import java.util.Arrays;
import java.util.List;

public abstract class BaseFont {

    abstract FancyChar a();
    abstract FancyChar b();
    abstract FancyChar c();
    abstract FancyChar d();
    abstract FancyChar e();
    abstract FancyChar f();
    abstract FancyChar g();
    abstract FancyChar h();
    abstract FancyChar i();
    abstract FancyChar j();
    abstract FancyChar k();
    abstract FancyChar l();
    abstract FancyChar m();
    abstract FancyChar n();
    abstract FancyChar o();
    abstract FancyChar p();
    abstract FancyChar q();
    abstract FancyChar r();
    abstract FancyChar s();
    abstract FancyChar t();
    abstract FancyChar u();
    abstract FancyChar v();
    abstract FancyChar w();
    abstract FancyChar x();
    abstract FancyChar y();
    abstract FancyChar z();

    public List<FancyChar> getChars() {
        return Arrays.asList(
                a(),b(),c(),d(),e(),f(),g(),h(),i(),j(),k(),l(),m(),n(),o(),p(),q(),r(),s(),t(),u(),v(),w(),x(),y(),z()
        );
    }

    public FancyChar find(char og) {
        if (getChars() == null || getChars().isEmpty()) return null;
        for (FancyChar c : getChars()) {
            if (Character.toLowerCase(og) == Character.toLowerCase(c.getReference())) {
                return c;
            }
        }
        return null;
    }

}
