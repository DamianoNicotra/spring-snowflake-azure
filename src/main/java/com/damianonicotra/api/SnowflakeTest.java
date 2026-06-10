package com.damianonicotra.api;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;

public class SnowflakeTest {
    public static void main(String[] args) {
        String url = "jdbc:snowflake://hjdlbku-cv00477.snowflakecomputing.com/?db=SPRING_SNOWFLAKE_DB&schema=PUBLIC&warehouse=TINY_WAREHOUSE&role=TERRAFORM_ROLE";
        String user = "terraform_user";
        String password = "TerraformSnowflake2026!";
        
        System.out.println("URL: " + url);
        
        try {
            Class.forName("net.snowflake.client.jdbc.SnowflakeDriver");
            Connection conn = DriverManager.getConnection(url, user, password);
            System.out.println("Connessione riuscita!");
            
            // Stampa il ruolo corrente
            Statement roleStmt = conn.createStatement();
            var rs = roleStmt.executeQuery("SELECT CURRENT_ROLE()");
            while (rs.next()) {
                System.out.println("Ruolo corrente: " + rs.getString(1));
            }
            
            // Inserimento con PARSE_JSON
            Statement stmt = conn.createStatement();
            String insertSql = "INSERT INTO SPRING_SNOWFLAKE_DB.PUBLIC.API_LOGS (ENDPOINT, REQUEST_DATA, RESPONSE_DATA, STATUS_CODE) " +
                               "SELECT '/test-jdbc', PARSE_JSON('{}'), PARSE_JSON('{}'), 200";
            System.out.println("Query: " + insertSql);
            int result = stmt.executeUpdate(insertSql);
            System.out.println("Inserimento riuscito! Righe: " + result);
            
            stmt.close();
            conn.close();
        } catch (Exception e) {
            System.out.println("ERRORE: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
