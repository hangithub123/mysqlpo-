package mybatis.utils;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import mybatis.entity.DataBase;

/**
 * @className：LinkDatabase.java
 * @Title: LinkDatabase
 * @Description: TODO(连接数据库返回Connection)
 * @author: ludaqing
 * @date: 2018年5月8日下午2:25:58
 */
public class LinkDatabaseUtil {
    
    /**
     * 获取数据库连接
     * @param dataBase
     * @return Connection
     */
    public static Connection getConnection(DataBase dataBase) {
        String url = dataBase.getDatabasePath();
        String user = dataBase.getAccount();
        String pass = dataBase.getPassword();
        Connection conn = null;
        try {
            Class.forName("com.mysql.jdbc.Driver");
            conn = DriverManager.getConnection(url, user, pass);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return conn;
    }

}
