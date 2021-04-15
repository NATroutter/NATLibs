package net.natroutter.natlibs.handlers.Database;

import net.natroutter.natlibs.handlers.Database.enums.DatabaseDriver;
import net.natroutter.natlibs.handlers.Database.objects.DatabaseCredentials;
import net.natroutter.natlibs.handlers.Database.objects.TableColumn;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.ArrayList;

public class Database {

    private JavaPlugin pl;
    private ConsoleCommandSender console;
    private DatabaseCredentials cred;
    private DatabaseDriver driver;

    private ArrayList<DatabaseTable> tables = new ArrayList<>();

    public Database(JavaPlugin pl, DatabaseCredentials cred, DatabaseDriver driver) {
        this.pl = pl;
        this.console = pl.getServer().getConsoleSender();
        this.cred = cred;
        this.driver = driver;
    }

    public DatabaseTable create(String tableName, TableColumn... fields) {
        DatabaseTable table = new DatabaseTable(pl, tableName, cred, driver, fields);
        tables.add(table);
        return table;
    }

    public DatabaseTable getTable(String tableName) {
        for (DatabaseTable table : tables) {
            if (table.getName().equals(tableName)) {
                return table;
            }
        }
        return null;
    }
}

