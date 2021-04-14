package net.natroutter.natlibs.handlers.Database;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import net.natroutter.natlibs.handlers.Database.enums.DatabaseDriver;
import net.natroutter.natlibs.handlers.Database.enums.FieldType;
import net.natroutter.natlibs.handlers.Database.objects.*;
import net.natroutter.natlibs.utilities.libs.RawFileManager;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.plugin.java.JavaPlugin;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class DatabaseTable {

    private JavaPlugin pl;
    private ConsoleCommandSender console;

    private String tableName;
    private DatabaseCredentials cred;
    private DatabaseDriver driver;
    private List<TableField> tableFields;

    private List<String> fieldNames = new ArrayList<>();

    private HikariConfig hikConfig;
    private HikariDataSource hikData;
    boolean Valid = false;

    public DatabaseTable(JavaPlugin pl, String tableName, DatabaseCredentials cred, DatabaseDriver driver, TableField... fields) {
        this.pl = pl;
        this.console = pl.getServer().getConsoleSender();
        this.tableName = tableName;
        this.cred = cred;
        this.driver = driver;
        this.tableFields = Arrays.asList(fields);

        //Construct table creation query and construct field name list for future use
        ArrayList<String> queryParts = new ArrayList<>();
        for (TableField field : fields) {
            queryParts.add(field.getName()+" "+field.getType()+" NOT NULL");
            fieldNames.add(field.getName());
        }

        String queryF = String.join(", ", queryParts);
        String tableQuery = "CREATE TABLE IF NOT EXISTS "+tableName+" ("+queryF+");";

        //console.sendMessage("§4Query: §c"+ tableQuery);

        //execute table create query!
        try {
            hikConfig = new HikariConfig();

            if (driver.equals(DatabaseDriver.SQLITE)) {
                RawFileManager rfm = new RawFileManager(pl, cred.getName()+".db");

                hikConfig.setPoolName("SQLiteConnectionPool");
                hikConfig.setDriverClassName("org.sqlite.JDBC");
                hikConfig.setJdbcUrl("jdbc:sqlite:" + rfm.getFile());

            } else if (driver.equals(DatabaseDriver.MYSQL)) {

                if (cred.getHost() == null || cred.getUser() == null) {
                    console.sendMessage("§4["+pl.getName()+"][DatabaseHandler] §cInvalid Database credentials you are missing host or user");
                    return;
                }

                String port = cred.getPort().toString();
                if (cred.getPort() == null) {
                    port = "3306";
                }

                hikConfig.setJdbcUrl("jdbc:mysql://"+cred.getHost()+":"+port+"/" + cred.getName());
                hikConfig.setUsername(cred.getUser());
                if (cred.getPass() != null) {
                    hikConfig.setPassword(cred.getPass());
                }
                hikConfig.addDataSourceProperty("cachePrepStmts", "true");
                hikConfig.addDataSourceProperty("prepStmtCacheSize", "250");
                hikConfig.addDataSourceProperty("prepStmtCacheSqlLimit", "2048");

            }

            hikData = new HikariDataSource(hikConfig);

            Connection con = hikData.getConnection();
            PreparedStatement stmt = con.prepareStatement(tableQuery);
            stmt.execute();
            stmt.close();
            con.close();
            Valid = true;
        } catch (SQLException e) {
            console.sendMessage("§4["+pl.getName()+"][DatabaseHandler] §cDatabase table creation failed!");
            e.printStackTrace();
        }
    }

    public boolean insert(InsertField... data) {
        if (!Valid) {console.sendMessage("§4["+pl.getName()+"][DatabaseHandler] §cDatabase is not valid!"); return false;}

        if (data.length != fieldNames.size()) {
            console.sendMessage("§4["+pl.getName()+"][DatabaseHandler] §cField count does not match inserting amount");
            return false;
        }

        String fields = String.join(", ", fieldNames);

        ArrayList<String> QuestionMarks = new ArrayList<>();
        for (int i = 0; i < fieldNames.size(); i++) {
            QuestionMarks.add("?");
        }
        String QuestionArgs = String.join(", ", QuestionMarks);


        Connection con = null;
        PreparedStatement stmt = null;
        try {
            String sql = "INSERT INTO "+tableName+" ("+fields+") VALUES ("+QuestionArgs+");";
            con = hikData.getConnection();
            stmt = con.prepareStatement(sql);


            for (int i = 0; i < data.length; i++) {
                InsertField field = data[i];
                int index = i + 1;

                if (field.getType().equals(FieldType.TEXT)) {
                    stmt.setString(index, field.getData().toString());

                } else if (field.getType().equals(FieldType.INTEGER)) {
                    stmt.setInt(index, Integer.parseInt(field.getData().toString()));

                } else if (field.getType().equals(FieldType.BOOLEAN)) {
                    stmt.setBoolean(index, Boolean.parseBoolean(field.getData().toString()));

                }
            }

            stmt.execute();
            con.close();
            return true;
        } catch (Exception e) {
            console.sendMessage("§4["+pl.getName()+"][DatabaseHandler] §cFailed to insert field to database!");
            e.printStackTrace();
        } finally {
            try {
                if (stmt != null) {stmt.close();}
                if (con != null) {con.close();}
            } catch (Exception ignored) {}
        }
        return false;
    }

    public ArrayList<TableRow> selectAll() {
        if (!Valid) {console.sendMessage("§4["+pl.getName()+"][DatabaseHandler] §cDatabase is not valid!"); return null;}

        ArrayList<TableRow> rows = new ArrayList<>();

        Connection con = null;
        PreparedStatement stmt = null;

        try {
            String sql = "SELECT * FROM "+tableName+";";
            con = hikData.getConnection();
            stmt = con.prepareStatement(sql);
            ResultSet result = stmt.executeQuery();

            while (result.next()) {
                TableRow row = new TableRow();
                for (TableField field : tableFields) {
                    DataField getF = null;

                    if (field.getType().equals(FieldType.TEXT)) {
                        getF = new DataField(field.getName(), result.getString(field.getName()), field.getType());

                    } else if (field.getType().equals(FieldType.INTEGER)) {
                        getF = new DataField(field.getName(), result.getInt(field.getName()), field.getType());

                    } else if (field.getType().equals(FieldType.BOOLEAN)) {
                        getF = new DataField(field.getName(), result.getBoolean(field.getName()), field.getType());

                    }

                    if (row != null) {
                        row.add(getF);
                    }
                }
                rows.add(row);
            }
            stmt.close();
            con.close();
            return rows;
        } catch (Exception e) {
            console.sendMessage("§4["+pl.getName()+"][DatabaseHandler] §cFailed to select data from database!");
            e.printStackTrace();
        } finally {
            try {
                if (stmt != null) {stmt.close();}
                if (con != null) {con.close();}
            } catch (Exception ignored) {}
        }
        return null;

    }

    public TableRow selectWhere(DataField selectField) {
        if (!Valid) {console.sendMessage("§4["+pl.getName()+"][DatabaseHandler] §cDatabase is not valid!"); return null;}

        Connection con = null;
        PreparedStatement stmt = null;

        try {
            String sql = "SELECT * FROM "+tableName+" WHERE "+selectField.getName()+"=?;";
            con = hikData.getConnection();
            stmt = con.prepareStatement(sql);

            if (selectField.getType().equals(FieldType.TEXT)) {
                stmt.setString(1, selectField.getData().toString());

            } else if (selectField.getType().equals(FieldType.INTEGER)) {
                stmt.setInt(1, Integer.parseInt(selectField.getData().toString()));

            } else if (selectField.getType().equals(FieldType.BOOLEAN)) {
                stmt.setBoolean(1, Boolean.parseBoolean(selectField.getData().toString()));

            }

            ResultSet result = stmt.executeQuery();

            //if (!result.next()) { return null; }

            TableRow row = new TableRow();
            for (TableField field : tableFields) {
                DataField getF = null;

                if (field.getType().equals(FieldType.TEXT)) {
                    getF = new DataField(field.getName(), result.getString(field.getName()), field.getType());

                } else if (field.getType().equals(FieldType.INTEGER)) {
                    getF = new DataField(field.getName(), result.getInt(field.getName()), field.getType());

                } else if (field.getType().equals(FieldType.BOOLEAN)) {
                    getF = new DataField(field.getName(), result.getBoolean(field.getName()), field.getType());

                }

                if (row != null) {
                    row.add(getF);
                }
            }

            stmt.close();
            con.close();
            return row;
        } catch (Exception e) {
            console.sendMessage("§4["+pl.getName()+"][DatabaseHandler] §cFailed to select data from database!");
            e.printStackTrace();
        } finally {
            try {
                if (stmt != null) {stmt.close();}
                if (con != null) {con.close();}
            } catch (Exception ignored) {}
        }
        return null;

    }

    public boolean updateWhere(DataField selectField, String columnName, Object newdata) {
        if (!Valid) {console.sendMessage("§4["+pl.getName()+"][DatabaseHandler] §cDatabase is not valid!"); return false;}

        Connection con = null;
        PreparedStatement stmt = null;
        try {
            String sql = "UPDATE "+tableName+" SET "+columnName+"=? WHERE "+ selectField.getName() + "=?;";
            con = hikData.getConnection();
            stmt = con.prepareStatement(sql);

            if (selectField.getType().equals(FieldType.TEXT)) {
                stmt.setString(1, newdata.toString());
                stmt.setString(2, selectField.getData().toString());

            } else if (selectField.getType().equals(FieldType.INTEGER)) {
                stmt.setInt(1, Integer.parseInt(newdata.toString()));
                stmt.setInt(2, Integer.parseInt(selectField.getData().toString()));

            } else if (selectField.getType().equals(FieldType.BOOLEAN)) {
                stmt.setBoolean(1, Boolean.parseBoolean(newdata.toString()));
                stmt.setBoolean(2, Boolean.parseBoolean(selectField.getData().toString()));

            }
            stmt.execute();
            stmt.close();
            con.close();
            return true;
        } catch (Exception e) {
            console.sendMessage("§4["+pl.getName()+"][DatabaseHandler] §cFailed to select data from database!");
            e.printStackTrace();
        } finally {
            try {
                if (stmt != null) {stmt.close();}
                if (con != null) {con.close();}
            } catch (Exception ignored) {}
        }
        return false;
    }

    public boolean deleteWhere(DataField selectField) {
        if (!Valid) {console.sendMessage("§4["+pl.getName()+"][DatabaseHandler] §cDatabase is not valid!"); return false;}

        Connection con = null;
        PreparedStatement stmt = null;
        try {
            String sql = "DELETE FROM "+tableName+" WHERE "+ selectField.getName() + "=?;";
            con = hikData.getConnection();
            stmt = con.prepareStatement(sql);

            if (selectField.getType().equals(FieldType.TEXT)) {
                stmt.setString(1, selectField.getData().toString());

            } else if (selectField.getType().equals(FieldType.INTEGER)) {
                stmt.setInt(1, Integer.parseInt(selectField.getData().toString()));

            } else if (selectField.getType().equals(FieldType.BOOLEAN)) {
                stmt.setBoolean(1, Boolean.parseBoolean(selectField.getData().toString()));

            }
            stmt.execute();
            stmt.close();
            con.close();
            return true;
        } catch (Exception e) {
            console.sendMessage("§4["+pl.getName()+"][DatabaseHandler] §cFailed to select data from database!");
            e.printStackTrace();
        } finally {
            try {
                if (stmt != null) {stmt.close();}
                if (con != null) {con.close();}
            } catch (Exception ignored) {}
        }
        return false;
    }



    public String getName() { return tableName; }
}
