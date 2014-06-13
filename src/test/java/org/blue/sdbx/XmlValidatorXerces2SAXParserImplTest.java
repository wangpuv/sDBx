package org.blue.sdbx;

import org.junit.Before;
import org.junit.Test;
import org.xml.sax.SAXException;

import java.io.IOException;
import java.net.URL;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

/**
 * Created with IntelliJ IDEA.
 * User: WangPu
 * Date: 13-4-11
 * Time: 上午10:32
 */
public class XmlValidatorXerces2SAXParserImplTest {

    private XmlValidator xmlValidator;
    private String xmlRightPath;
    private String xmlErrorPath;
    private String schemaPath;

    @Before
    public void setUp() {
        this.xmlValidator = new XmlValidatorXerces2SAXParserImpl();
        this.xmlRightPath = "/xml/AA_Right.xml";
        this.xmlErrorPath = "/xml/AA_Error.xml";
        this.schemaPath = "/xsd/sdbx_aa.xsd";
    }

    @Test
    public void validatingXmlTest_Right() throws SAXException, IOException {
        URL xmlUrl = getClass().getResource(this.xmlRightPath);
        // 验证xml文件存在
        assertNotNull(xmlUrl);

        URL schemaUrl = getClass().getResource(this.schemaPath);
        // 验证schema文件存在
        assertNotNull(schemaUrl);

        boolean result = xmlValidator.validatingXml(xmlUrl.getFile(), schemaUrl.getFile());
        assertEquals(true, result);
    }

    @Test
    public void validatingXmlTest_Error() throws SAXException, IOException {
        URL xmlUrl = getClass().getResource(this.xmlErrorPath);
        // 验证xml文件存在
        assertNotNull(xmlUrl);

        URL schemaUrl = getClass().getResource(this.schemaPath);
        // 验证schema文件存在
        assertNotNull(schemaUrl);

        boolean result = xmlValidator.validatingXml(xmlUrl.getFile(), schemaUrl.getFile());
        assertEquals(false, result);
    }
}
