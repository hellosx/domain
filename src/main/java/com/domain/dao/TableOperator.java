package com.domain.dao;

/**
 * Created by sunxin(hysx8@sina.com) on 2017/6/9.
 */

import org.apache.log4j.Logger;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.Statement;

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
        Statement stmt = null;
        Connection conn = null;
        try {
            conn = dataSource.getConnection();
            stmt = conn.createStatement();
            stmt.execute(sql);

        }
        catch (Throwable e){
            e.printStackTrace();
            logger.warn(e.getMessage(), e);
        }
        finally {
            try {
                if (conn != null &&stmt != null){
                    stmt.close();
                    conn.close();
                }
            }
            catch (Throwable e){
                logger.error(e.getMessage(), e);
            }
        }
    }


    private static String createDomainSql(String domain) {
        String[] array = domain.split(",");
        StringBuilder sql = new StringBuilder("INSERT INTO domain (domain_name, domain_length, CREATE_time) VALUES ");
        for (int i = 0; i < array.length; i++) {
            sql.append("('" + array[i] + "'," + String.valueOf(array[i].length()) + ", SYSDATE()),");
        }
        return sql.toString().substring(0, sql.length()-1);
    }

}
