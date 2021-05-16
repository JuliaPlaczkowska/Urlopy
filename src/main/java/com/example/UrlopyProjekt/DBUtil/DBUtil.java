package com.example.UrlopyProjekt.DBUtil;


import com.example.UrlopyProjekt.Urlop;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.List;

public abstract class DBUtil {

    public abstract List<Urlop> getUrlopy(String stan, String zaakceptowane) throws Exception;

    protected static void close(Connection conn, Statement statement, ResultSet resultSet) {

        try {

            if (resultSet != null)
                resultSet.close();

            if (statement != null)
                statement.close();

            if (conn != null)
                conn.close();

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

}

