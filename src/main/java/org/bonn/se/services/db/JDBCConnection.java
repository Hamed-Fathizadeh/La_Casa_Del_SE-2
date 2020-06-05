package org.bonn.se.services.db;

import org.bonn.se.services.db.exception.DatabaseException;
import org.bonn.se.services.util.Password;

import java.sql.*;
import java.util.Properties;

public class JDBCConnection {
    private static JDBCConnection connection = null;
    private Connection conn;
    private final String login = "tfelle2s";
    private final String password = Password.PASSWORD;

    public static JDBCConnection getInstance() throws DatabaseException {

        if (connection == null) {
            connection = new JDBCConnection();
        }
        return connection;
    }

    private JDBCConnection() throws DatabaseException {
        this.initConnection();
    }

    public void initConnection() throws DatabaseException {
        try {
            DriverManager.registerDriver(new org.postgresql.Driver());
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        this.openConnection();
    }

    public void openConnection() throws DatabaseException {

        try {

            Properties props = new Properties();
            props.setProperty("user", "tfelle2s");
            props.setProperty("password", Password.PASSWORD);


            String url = "jdbc:postgresql://dumbo.inf.h-brs.de/tfelle2s";
            this.conn = DriverManager.getConnection(url, props);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
            throw new DatabaseException("Fehler beim Zugriff auf die Datenbank! Sichere Verbindung vorhanden?");

        }
    }


    public Statement getStatement() throws DatabaseException {
        try {
            if(this.conn.isClosed()) {
                this.openConnection();
            }
            return this.conn.createStatement();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
            throw new DatabaseException("Fehler beim Zugriff auf die Datenbank! Sichere Verbindung vorhanden?");
        }
    }

    public PreparedStatement getPreparedStatement(String sql) throws DatabaseException {
        try {
            if(this.conn.isClosed()) {
                this.openConnection();
            }
            return this.conn.prepareStatement(sql);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
            throw new DatabaseException("Fehler beim Zugriff auf die Datenbank! Sichere Verbindung vorhanden?");
        }
    }

    public void closeConnection(){
        try {
            this.conn.close();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }
}
