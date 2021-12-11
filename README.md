![banner](https://cdn.nat.gs/img/NATLibs_Banner.png)

<div align="center">
<h1 style="margin: 0px;font-weight: 700;font-family:-apple-system,BlinkMacSystemFont,Segoe UI,Helvetica,Arial,sans-serif,Apple Color Emoji,Segoe UI Emoji">NATLibs</h1>

![licence](https://img.shields.io/badge/License-MIT-brightgreen)

![Jenkins](https://img.shields.io/jenkins/build?jobUrl=https%3A%2F%2Fhub.nat.gs%2Fjenkins%2Fjob%2Fnatlibs%2520-%2520Dev%2F&label=Last%20dev%20Build&style=flat-square)

![Sonatype Nexus (Repository)](https://img.shields.io/nexus/natlibs/net.natroutter/NATLibs?label=nexus%20-%20dev&server=https%3A%2F%2Fhub.nat.gs%2Fnexus%2F&style=flat-square)

NATLibs is simple and powerful spigot plugin library that provides  
easier and faster way to do things in code i have made this library mainly  
for my personal use but if you want to use it feel free to do so

</div>


## Documentation
Documentation: [here](https://hub.nat.gs/javadoc/NATLibs/latest.php)  
Old version documentation can be found in [ProjectHub](https://hub.nat.gs/) for limited time

##Getting Started
````java
@Override
public class ExamplePlugin extends JavaPlugin {
    
    @Override
    public void onEnable() {
        //This is needed for being available to use some features
        NATLibs lib = new NATLibs(this);
    }
    
}
````

## Api
###Maven Repository:
````xml
<repository>
    <id>natlibs</id>
    <url>https://hub.nat.gs/nexus/repository/natlibs/</url>
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
    <version>3.2.4</version> <!-- Check if there are newer versions available-->
    <executions>
        <execution>
            <phase>package</phase>
            <goals>
                <goal>shade</goal>
            </goals>
            <configuration>
                <createDependencyReducedPom>false</createDependencyReducedPom>
                <relocations> <!-- This is the must have part that you need (START) -->
                    <relocation>
                        <pattern>net.natroutter.natlibs</pattern>
                        <shadedPattern>${project.groupId}.natlibs</shadedPattern>
                    </relocation>
                </relocations> <!-- This is the must have part that you need (END) -->
            </configuration>
        </execution>
    </executions>
</plugin>
`````