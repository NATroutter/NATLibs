![banner](https://cdn.nat.gs/img/NATLibs_Banner.png)

<div align="center">
<h1 style="margin: 0px;font-weight: 700;font-family:-apple-system,BlinkMacSystemFont,Segoe UI,Helvetica,Arial,sans-serif,Apple Color Emoji,Segoe UI Emoji">NATLibs</h1>

![licence](https://img.shields.io/badge/License-MIT-brightgreen)

![Jenkins](https://img.shields.io/jenkins/build?jobUrl=https%3A%2F%2Fhub.nat.gs%2Fjenkins%2Fjob%2FNATLibs%2520-%2520Dev%2F&label=Last%20dev%20Build&style=flat-square)
![Jenkins](https://img.shields.io/jenkins/build?jobUrl=https%3A%2F%2Fhub.nat.gs%2Fjenkins%2Fjob%2FNATLibs%2520-%2520stable%2F&label=Last%20stable%20Build&style=flat-square)

![Sonatype Nexus (Repository)](https://img.shields.io/nexus/NATLibs-dev/net.natroutter/NATLibs?label=nexus%20-%20dev&server=https%3A%2F%2Fhub.nat.gs%2Fnexus%2F&style=flat-square)
![Sonatype Nexus (Repository)](https://img.shields.io/nexus/NATLibs-stable/net.natroutter/NATLibs?label=nexus%20-%20stable&server=https%3A%2F%2Fhub.nat.gs%2Fnexus%2F&style=flat-square)

NATLibs is simple and powerful spigot plugin library that provides  
easier and faster way to do things in code i have made this library mainly  
for my personal use but if you want to use it feel free to use it

</div>

## Installation
When you are using this library you must have this library plugin in your plugin folder  

1. Download newest stable/dev version of this plugin from [Jenkins](https://hub.nat.gs/jenkins)  
2. Install that plugin to your server just drop it in your plugins fodler
3. Include maven repository and stable/dev dependency to your project
4. Start coding!

## Documentation
Documentation (stable): [here](https://hub.nat.gs/javadoc/NATLibs/latest.php?branch=stable)  
Documentation (dev): [here](https://hub.nat.gs/javadoc/NATLibs/latest.php?branch=dev)  
Old version documentation can be found in [ProjectHub](https://hub.nat.gs/) for limited time

## Api
Maven Repository (Stable):
````xml
<repository>
    <id>NATLibs-stable</id>
    <url>https://hub.nat.gs/nexus/repository/NATLibs-stable/</url>
</repository>
````
Maven Repository (Dev):
````xml
<repository>
    <id>NATLibs-dev</id>
    <url>https://hub.nat.gs/nexus/repository/NATLibs-dev/</url>
</repository>
````
Maven Dependency:
````xml
<dependency>
    <groupId>net.natroutter</groupId>
    <artifactId>NATLibs</artifactId>
    <version>{VERSION}</version>
</dependency>
````