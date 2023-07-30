package fi.natroutter.natlibs.objects;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor @Getter @Setter
public class MongoConfig {

    private String database;
    private String username;
    private String password;
    private String host;
    private int port;

    public String getURI() {
        return "mongodb://"+username+":"+password+"@"+host+":"+port+"/";
    }

}
