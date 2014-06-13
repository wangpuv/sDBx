package org.blue.sdbx.db;

import java.sql.SQLException;

/**
 * Created with IntelliJ IDEA.
 * User: wangpu
 * Date: 13-4-15
 * Time: 上午11:44
 *
 * Transaction接口定义
 */
public interface Transaction {

    void commit() throws SQLException;

    void rollback() throws SQLException;
}
