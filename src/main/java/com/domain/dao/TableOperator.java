package com.domain.dao;

/**
 * Created by sunxin(hysx8@sina.com) on 2017/6/9.
 */
import org.apache.log4j.Logger;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;

import javax.sql.DataSource;

public class TableOperator {
    private DataSource dataSource;

    private static Logger logger = Logger.getLogger("log");

    public void setDataSource(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    private static final int COUNT = 800;

    public TableOperator() {

    }

    public void tearDown() throws Exception {
        try {
            dropTable();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void insert() throws Exception {

        StringBuffer ddl = new StringBuffer();
        ddl.append("INSERT INTO t_big (");
        for (int i = 0; i < COUNT; ++i) {
            if (i != 0) {
                ddl.append(", ");
            }
            ddl.append("F" + i);
        }
        ddl.append(") VALUES (");
        for (int i = 0; i < COUNT; ++i) {
            if (i != 0) {
                ddl.append(", ");
            }
            ddl.append("?");
        }
        ddl.append(")");

        Connection conn = dataSource.getConnection();

//        System.out.println(ddl.toString());

        PreparedStatement stmt = conn.prepareStatement(ddl.toString());

        for (int i = 0; i < COUNT; ++i) {
            stmt.setInt(i + 1, i);
        }
        stmt.execute();
        stmt.close();

        conn.close();
    }

    private void dropTable() throws SQLException {

        Connection conn = dataSource.getConnection();

        Statement stmt = conn.createStatement();
        stmt.execute("DROP TABLE t_big");
        stmt.close();

        conn.close();
    }

    public void createTable() throws SQLException {
        StringBuffer ddl = new StringBuffer();
        ddl.append("CREATE TABLE t_big (FID INT AUTO_INCREMENT PRIMARY KEY ");
        for (int i = 0; i < COUNT; ++i) {
            ddl.append(", ");
            ddl.append("F" + i);
            ddl.append(" BIGINT NULL");
        }
        ddl.append(")");

        Connection conn = dataSource.getConnection();

        Statement stmt = conn.createStatement();
        stmt.execute(ddl.toString());
        stmt.close();

        conn.close();
    }

    /**
     * 同步到DB
     */
    public void insertDomain(String domain){
        String sql = splitDomain(domain);
        try {
            Connection conn = dataSource.getConnection();
            Statement stmt = conn.createStatement();
            stmt.execute(sql);
            stmt.close();
            conn.close();
        }
        catch (Throwable e){
            e.printStackTrace();
            logger.warn(e.getMessage(), e);
        }
    }


    public static String splitDomain(String domain) {
        String[] array = domain.split(",");
        StringBuilder sql = new StringBuilder("INSERT INTO domain (domain_name , CREATE_time) VALUES ");
        for (int i = 0; i < array.length; i++) {
            sql.append("('" + array[i] + "' , SYSDATE()),");
        }
        return sql.toString().substring(0, sql.length()-1);
    }
}