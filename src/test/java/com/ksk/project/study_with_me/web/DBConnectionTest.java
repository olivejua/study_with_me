package com.ksk.project.study_with_me.web;

import org.junit.Test;

import java.sql.Connection;
import java.sql.DriverManager;

public class DBConnectionTest {
    private static final String driver = "com.mysql.cj.jdbc.Driver";
    private static final String url = "jdbc:mysql://localhost:3306/study_with_me?serverTimezone=UTC&characterEncoding=UTF-8";
    private static final String user = "root";
    private static final String pw = "11111111";

    @Test
    public void testConnection() throws Exception {
        Class.forName(driver);

        try(Connection con = DriverManager.getConnection(url, user, pw)) {
            System.out.println(con);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
