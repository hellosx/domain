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

    /**
     * 同步到DB
     */
    public void insertDomain(String domain){
        String sql = createDomainSql(domain);
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


    public static String createDomainSql(String domain) {
        String[] array = domain.split(",");
        StringBuilder sql = new StringBuilder("INSERT INTO domain (domain_name, domain_length, CREATE_time) VALUES ");
        for (int i = 0; i < array.length; i++) {
            sql.append("('" + array[i] + "'," + String.valueOf(array[i].length()) + ", SYSDATE()),");
        }
        return sql.toString().substring(0, sql.length()-1);
    }

}
