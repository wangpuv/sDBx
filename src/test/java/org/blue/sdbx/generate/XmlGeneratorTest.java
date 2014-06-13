package org.blue.sdbx.generate;

import org.blue.sdbx.config.ConfigurationFactory;
import org.blue.sdbx.db.ConnectionFactory;
import org.junit.Before;
import org.junit.Test;
import org.xml.sax.SAXException;

import javax.xml.bind.JAXBException;
import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.parsers.ParserConfigurationException;
import java.io.File;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.sql.SQLException;

import static org.junit.Assert.assertTrue;

/**
 * Created with IntelliJ IDEA.
 * User: wangpu
 * Date: 13-4-17
 * Time: 上午10:58
 */
public class XmlGeneratorTest {
    private XmlGenerator generator;

    @Before
    public void setUp()
            throws ClassNotFoundException, NoSuchMethodException, InstantiationException, IllegalAccessException,
            IOException, SAXException, ParserConfigurationException {
        String driverClassName = "org.h2.Driver";
        String url = "jdbc:h2:tcp://localhost/~/sdbx";
        String username = "sa";
        String password = "";

        ConnectionFactory.initializeForTesting(driverClassName, url, username, password);

        this.generator = new XmlGenerator(getClass().getResource("/xml").getFile() + "/aa_generate.xml",
                ConfigurationFactory.getInstance().getConfiguration("aa"));

        /*this.generator = new XmlGenerator(getClass().getResource("/xml").getFile() + "/ss_generate.xml",
                ConfigurationFactory.getInstance().getConfiguration("ss"));*/
    }

    @Test
    public void generateXml()
            throws IOException, InstantiationException, InvocationTargetException, NoSuchMethodException,
            SQLException, JAXBException, IllegalAccessException, DatatypeConfigurationException, ClassNotFoundException {
        File aaXml = generator.generateXml();
        assertTrue(aaXml.exists());
    }
}
