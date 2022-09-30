package net.natroutter.natlibs.objects;

import java.util.UUID;

public class PlayerInfo {

    private String name;
    private UUID id;

    public PlayerInfo(){}
    public PlayerInfo(String name, UUID id) {
        this.name = name;
        this.id= id;
    }

    public String getName() {
        return name;
    }
    public UUID getUUID() {
        return id;
    }

}
