package org.blue.sdbx.db;

import org.apache.commons.dbcp.BasicDataSource;

import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

/**
 * Created with IntelliJ IDEA.
 * User: wangpu
 * Date: 13-4-15
 * Time: 上午11:48
 * <p/>
 * 数据连接工厂类
 */
public class ConnectionFactory {
    // 单例工厂类对象
    private static ConnectionManager connectionManager = null;

    /**
     * 私有构造器
     */
    private ConnectionFactory() {
    }

    /**
     * 创建连接管理器对象
     *
     * @return 连接管理器对象
     */
    public static ConnectionManager getConnectionManager() {
        if (connectionManager == null) {
            try {
                // todo jndi
                DataSource dataSource = (DataSource) new InitialContext().lookup("java:comp/env/jdbc/xxx");
                connectionManager = new ConnectionManager(dataSource);
            } catch (NamingException e) {
                e.printStackTrace();
            }
        }
        return connectionManager;
    }

    /**
     * 为测试初始化连接环境
     */
    public static void initializeForTesting(String driverClassName, String url, String username, String password) {
        if (connectionManager == null) {
            BasicDataSource dataSource = new BasicDataSource();
            dataSource.setDriverClassName(driverClassName);
            dataSource.setUrl(url);
            dataSource.setUsername(username);
            dataSource.setPassword(password);
            connectionManager = new ConnectionManager(dataSource);
        }
    }
}
