package fi.natroutter.natlibs.config;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
public enum CfgType {

    CONFIG("config.yml"),
    LANG("lang.yml");

    @Getter
    private String name;

}
