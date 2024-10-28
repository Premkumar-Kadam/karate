package com.ama.karate.configuration;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.sql.DatabaseMetaData;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;


@Component
public class DatabaseValidator {

    @Autowired JdbcTemplate jdbcTemplate;
    private final String filePath = "src/main/java/com/ama/karate/migration/createTable.txt";
    public DatabaseValidator(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public void validateDatabase() throws SQLException, IOException {
        Map<String, Map<String, String>> expectedTables = loadExpectedSchema();
        DatabaseMetaData metaData = jdbcTemplate.getDataSource().getConnection().getMetaData();

        for (String tableName : expectedTables.keySet()) {
            if (!tableExists(metaData, tableName)) {
                throw new RuntimeException("Table missing: " + tableName);
            }

            Map<String, String> expectedColumns = expectedTables.get(tableName);
            for (String columnName : expectedColumns.keySet()) {
                if(columnName.startsWith("UNIQUE")){
                    continue;
                }else if (!columnExists(metaData, tableName, columnName)) {
                    throw new RuntimeException("Column missing: " + columnName + " in table " + tableName);
                }
            }
        }
    }

    private Map<String, Map<String, String>> loadExpectedSchema() throws IOException {
        Map<String, Map<String, String>> tableMap = new HashMap<>();

        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line;
            String currentTable = null;
            Map<String, String> columns = null;

            while ((line = reader.readLine()) != null) {
                line = line.trim();

                if (line.startsWith("CREATE TABLE")) {
                    // Extract table name
                    currentTable = line.split(" ")[2];
                    columns = new HashMap<>();
                } else if (line.startsWith(")")) {
                    // End of the current table definition
                    if (currentTable != null && columns != null) {
                        tableMap.put(currentTable, columns);
                    }
                } else if (currentTable != null && columns != null && !line.isEmpty()) {
                    // Extract column name and type
                    String[] parts = line.split(" ");
                    String columnName = parts[0];
                    String columnType = parts[1];
                    columns.put(columnName, columnType);
                }
            }
        }

        return tableMap;
    }

    private boolean tableExists(DatabaseMetaData metaData, String tableName) throws SQLException {
        try (ResultSet tables = metaData.getTables(null, "public", tableName, null)) { 
            return tables.next();
        }
    }
    
    private boolean columnExists(DatabaseMetaData metaData, String tableName, String columnName) throws SQLException {
        try (ResultSet columns = metaData.getColumns(null, "public", tableName, columnName)) { 
            return columns.next();
        }
    }
}
