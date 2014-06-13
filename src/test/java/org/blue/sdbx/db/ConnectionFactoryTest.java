package org.blue.sdbx.db;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import static org.junit.Assert.assertNotNull;

/**
 * Created with IntelliJ IDEA.
 * User: wangpu
 * Date: 13-4-15
 * Time: 下午2:13
 */
public class ConnectionFactoryTest {

    private ConnectionManager connectionManager;

    @Before
    public void setup() {
        String driverClassName = "org.h2.Driver";
        String url = "jdbc:h2:tcp://localhost/~/sdbx";
        String username = "sa";
        String password = "";

        ConnectionFactory.initializeForTesting(driverClassName, url, username, password);
        this.connectionManager = ConnectionFactory.getConnectionManager();
    }

    @Test
    public void getTestDBConnection1() throws SQLException {
        final String sql = "select main_acct_id from sdbx_main_acct where main_acct_id = 12345";

        ResultSet rs = null;
        PreparedStatement stm = null;
        try {
            stm = connectionManager.getConnection().prepareStatement(sql);
            rs = stm.executeQuery();
            rs.next();

            assertNotNull(rs.getInt(1));
        } finally {
            connectionManager.release(rs, stm);
        }
    }

    @Test
    public void getTestDBConnection2() throws SQLException {
        final String sql = "select main_acct_id from sdbx_main_acct where main_acct_id = 12346";

        ResultSet rs = null;
        PreparedStatement stm = null;
        try {
            stm = connectionManager.getConnection().prepareStatement(sql);
            rs = stm.executeQuery();
            rs.next();

            assertNotNull(rs.getInt("main_acct_id"));
        } finally {
            connectionManager.release(rs, stm);
        }
    }

    @After
    public void tearDown() throws SQLException {
        connectionManager.close();
    }
}
