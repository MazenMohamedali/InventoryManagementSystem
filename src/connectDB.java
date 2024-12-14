import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class connectDB {
    private static final String DBurl = "jdbc:sqlite:./databaseIMS.db";
    public static String getDburl() {
        return DBurl;
    }

    // empty String for hasnot where


    public static String[] generlizeSelect(String[] columns, String table, String hasWhere) {
        StringBuilder sqlQuary = new StringBuilder("SELECT ");// = "SELECT id,  FROM orders WHERE arrival_date LIKE ?";
        
        int len = columns.length;
        for(int i=0; i<len; i++) {
            sqlQuary.append(columns[i]);
            if(i != len-1)
                sqlQuary.append(", ");
        }

        sqlQuary.append(" FROM ").append(table);
        if(hasWhere != "") {
            sqlQuary.append(" WHERE ").append(hasWhere);
        }


        List<String> results = new ArrayList<>();


        try(Connection conn = DriverManager.getConnection(connectDB.getDburl());
        PreparedStatement preSatement = conn.prepareStatement(sqlQuary.toString())) {
            ResultSet resultSet = preSatement.executeQuery();

            int k=0;
            while (resultSet.next()) {
                StringBuilder row = new StringBuilder();
                for(int i=0; i<len; i++) {
                    row.append(resultSet.getString(columns[i]));
                    if(i != len-1)
                        row.append(", ");
                }
                results.add(row.toString());
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return new String[0];
        }
        return results.toArray(new String[0]);
    }


}
