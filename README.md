![banner](https://cdn.nat.gs/img/NATLibs_Banner.png)

<div align="center">
<h1 style="margin: 0px;font-weight: 700;font-family:-apple-system,BlinkMacSystemFont,Segoe UI,Helvetica,Arial,sans-serif,Apple Color Emoji,Segoe UI Emoji">NATLibs</h1>

![GitHub](https://img.shields.io/github/license/NATroutter/NATLibs?style=for-the-badge)

![Jenkins](https://img.shields.io/jenkins/build?jobUrl=https%3A%2F%2Fhub.nat.gs%2Fjenkins%2Fjob%2FNATLibs%2F&style=for-the-badge)
![Sonatype Nexus (Releases)](https://img.shields.io/nexus/r/net.natroutter/NATLibs?server=https%3A%2F%2Fhub.nat.gs%2Fnexus%2F&style=for-the-badge)

NATLibs is simple and powerful spigot plugin library that provides  
easier and faster way to do things in code i have made this library mainly  
for my personal use but if you want to use it feel free to do so

</div>


## Documentation
Documentation: [here](https://hub.nat.gs/javadoc/NATLibs/latest.php)  
Old version documentation can be found in [ProjectHub](https://hub.nat.gs/) for limited time

##Getting Started

````java
import net.natroutter.natlibs.NATLibs;

@Override
public class ExamplePlugin extends JavaPlugin implements NATLibs {

    @Override
    public void onEnable() {
        //This is needed for being available to use some features
        registerLibrary(this);
        
        //This is only needed if you want to use bungeecord functionality
        createBungeecordHandler(this);
    }

}
````

## Api
###Maven Repository:
````xml
<repository>
    <id>NAT-Software</id>
    <url>https://hub.nat.gs/nexus/repository/NAT-Software/</url>
</repository>
````

###Maven Dependency:
````xml
<dependency>
    <groupId>net.natroutter</groupId>
    <artifactId>NATLibs</artifactId>
    <version>{VERSION}</version>
</dependency>
````

###Needed maven plugins with configuration
`````xml
<plugin>
    <groupId>org.apache.maven.plugins</groupId>
    <artifactId>maven-shade-plugin</artifactId>
    <version>3.2.4</version>
    <executions>
        <execution>
            <phase>package</phase>
            <goals>
                <goal>shade</goal>
            </goals>
            <configuration>
                <minimizeJar>true</minimizeJar>
                <createDependencyReducedPom>false</createDependencyReducedPom>
                <relocations>
                    <relocation>
                        <pattern>net.natroutter.natlibs</pattern>
                        <shadedPattern>${project.groupId}.natlibs</shadedPattern>
                    </relocation>
                </relocations>
            </configuration>
        </execution>
    </executions>
</plugin>
`````