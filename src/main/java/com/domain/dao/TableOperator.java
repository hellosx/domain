package com.domain.dao;

/**
 * Created by sunxin(hysx8@sina.com) on 2017/6/9.
 */

import com.domain.util.HttpUtil;
import org.apache.log4j.Logger;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import static com.domain.util.HttpUtil.extractMessageByRegular;

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
        String mapperSql = createDomainMapperSql(domain);
        Statement stmt = null;
        Connection conn = null;
        try {
            conn = dataSource.getConnection();
            stmt = conn.createStatement();
            stmt.execute(sql);
            stmt.execute(mapperSql);
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


    private String createDomainSql(String domain) {
        String[] array = domain.split(",");
        StringBuilder sql = new StringBuilder("INSERT INTO domain (domain_name, domain_length, CREATE_time) VALUES ");
        for (int i = 0; i < array.length; i++) {
            sql.append("('" + array[i] + "'," + String.valueOf(array[i].length()) + ", SYSDATE()),");
        }
        return sql.toString().substring(0, sql.length()-1);
    }

    private String createDomainMapperSql(String domain) {
        String[] array = domain.split(",");

        StringBuilder sql = new StringBuilder("INSERT INTO domain_mapper (domain_name, domain_message, CREATE_time) VALUES ");
        for (int i = 0; i < array.length; i++) {
            List list = getMapper(array[i]);
            if (list != null && list.size() > 0){
                for (int j = 0; j < list.size(); j++) {
                    sql.append("('" + array[i] + "','" + list.get(j) + "', SYSDATE()),");
                }
            }
        }
        return sql.toString().substring(0, sql.length()-1);

    }

    public List<String> getMapper(String domain) {
        String url = "https://gsp0.baidu.com/5a1Fazu8AA54nxGko9WTAnF6hhy/su?cb=jQuery11010828343924417585_1497418844492&wd=";
        String result = HttpUtil.get(url + domain, "GBK");
        List<String> list = new ArrayList<String>();
        try {
            String[] array = extractMessageByRegular(result).split(",");
            if (array.length > 0 && !"".equals(array[0])){
                for (int i = 0; i < array.length; i++) {
                    list.add(array[i].substring(1, array[i].length() - 1));
                }
            }
        }
        catch (Throwable e){
            logger.error(e.getMessage(), e);
        }
        return list;
    }
}
