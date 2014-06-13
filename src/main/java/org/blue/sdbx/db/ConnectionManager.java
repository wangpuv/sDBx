package org.blue.sdbx.db;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * Created with IntelliJ IDEA.
 * User: wangpu
 * Date: 13-4-15
 * Time: 上午11:34
 *
 * 连接管理器类
 *
 * 主要具备如下功能：
 * 1.线程范围内的连接管理
 * 2.线程范围内的事务管理
 *
 * 对于连接管理，主要使用了Java的线程本地存储(ThreadLocal)，
 * 这样可以保证为每一个线程存储单个不同的连接对象
 */
public class ConnectionManager {
    private static Logger logger = LoggerFactory.getLogger(ConnectionManager.class);

    /**
     * 空的事务对象
     */
    private static final Transaction EMPTY_TRANSACTION = new Transaction() {
        public void rollback() throws SQLException {
        }

        public void commit() throws SQLException {
        }
    };

    /**
     * 负责提交和回滚的事务对象
     */
    private static final Transaction TRANSACTION = new Transaction() {
        public void rollback() throws SQLException {
            Connection connection = connectionHolder.get();
            if (connection != null) {
                if (!connection.getAutoCommit()) {
                    connection.rollback();
                }
                connection.close();
                connectionHolder.remove();
            }
        }

        public void commit() throws SQLException {
            Connection connection = connectionHolder.get();
            if (connection != null) {
                if (!connection.getAutoCommit()) {
                    connection.commit();
                }
                connection.close();
                connectionHolder.remove();
            }
        }
    };


    // 线程本地对象管理器
    private static final ThreadLocal<Connection> connectionHolder = new ThreadLocal<Connection>();

    // 数据源
    private DataSource dataSource;

    /**
     * 构造器
     *
     * @param dataSource 数据源对象
     */
    ConnectionManager(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    /**
     * 获取数据库连接
     *
     * @return 数据库连接
     * @throws SQLException
     */
    public Connection getConnection() throws SQLException {
        Connection connection = connectionHolder.get();
        if (connection == null) {
            connection = dataSource.getConnection();
            connectionHolder.set(connection);
        }

        logger.debug("get db connection!");
        return connection;
    }

    /**
     * 启动事务
     *
     * @return 事务管理对象
     * @throws SQLException
     */
    public Transaction beginTransaction() throws SQLException {
        Connection connection = getConnection();
        if (connection.getAutoCommit()) {
            connection.setAutoCommit(false);
        }

        logger.debug("begin jdbc transaction!");
        return TRANSACTION;
    }

    /**
     * 获取事务
     *
     * @return Transaction
     */
    public Transaction getTransaction() {
        return connectionHolder.get() == null ? EMPTY_TRANSACTION : TRANSACTION;
    }

    /**
     * 关闭数据库连接
     *
     * @throws SQLException
     */
    public void close() throws SQLException {
        Connection connection = connectionHolder.get();
        if (connection != null) {
            connection.close();
            connectionHolder.remove();
        }
        logger.debug("close db connection!");
    }

    /**
     * 释放资源
     *
     * @param rs  结果集对象
     * @param stm 命令集对象
     * @throws SQLException
     */
    public void release(ResultSet rs, Statement stm) throws SQLException {
        if (rs != null) {
            rs.close();
        }
        if (stm != null) {
            stm.close();
        }
        logger.debug("release ResultSet Statement!");
    }
}
