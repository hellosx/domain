package com.domain.dao;

import com.domain.excepiton.DataBaseException;
import org.apache.ibatis.session.SqlSession;
import org.mybatis.spring.support.SqlSessionDaoSupport;

/**
 * Created by sunxin(hysx8@sina.com) on 2017/6/16.
 */
public class CommonDao extends SqlSessionDaoSupport {

    public int insert(final String sqlMapId, final Object object) {
        try {
            SqlSession session = getSqlSession();
            return session.insert(sqlMapId, object);
        } catch (Exception e) {
            logger.error("SQL执行出错: " + sqlMapId, e);
            throw new DataBaseException();
        }
    }
}
