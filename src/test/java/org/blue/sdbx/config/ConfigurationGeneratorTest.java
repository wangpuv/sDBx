package org.blue.sdbx.config;

import org.junit.Before;
import org.junit.Test;
import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.xpath.XPathExpressionException;
import java.io.IOException;

import static org.junit.Assert.assertEquals;

/**
 * Created with IntelliJ IDEA.
 * User: wangpu
 * Date: 13-4-16
 * Time: 上午10:28
 */
public class ConfigurationGeneratorTest {

    private ConfigurationGenerator parse;

    @Before
    public void setUp() throws ParserConfigurationException, IOException, SAXException {
        this.parse = new ConfigurationGenerator(getClass().getResource("/xmlMappingDb/aaXmlMapper.xml").getFile());
    }

    @Test
    public void generateConfiguration ()
            throws ParserConfigurationException, SAXException, XPathExpressionException, IOException {
        Configuration configuration = parse.generateConfiguration();
        assertEquals("aa", configuration.getName());
        assertEquals("column:main_acct_id", configuration.getDb().getMapper().getElementMap().get("rolelist").getMethod().getParams().get(0).getValue());
    }
}
