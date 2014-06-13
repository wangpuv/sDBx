package org.blue.sdbx;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.xml.sax.*;
import org.xml.sax.helpers.XMLReaderFactory;

import java.io.IOException;

/**
 * Created with IntelliJ IDEA.
 * User: WangPu
 * Date: 13-4-11
 * Time: 上午10:01
 * <p/>
 * XML验证接口Xerces2 SAXParser实现
 */
public class XmlValidatorXerces2SAXParserImpl implements XmlValidator {
    private static Logger logger = LoggerFactory.getLogger(XmlValidatorXerces2SAXParserImpl.class);

    /**
     * 根据XML Schema文件验证XML文件是否正确
     *
     * @param xmlPath    XML文件绝对路径
     * @param schemaPath XSD文件的绝对路径
     * @return 是否正确
     */
    @Override
    public boolean validatingXml(String xmlPath, String schemaPath) throws SAXException, IOException {
        XMLReader reader = XMLReaderFactory.createXMLReader("org.apache.xerces.parsers.SAXParser");
        reader.setFeature("http://xml.org/sax/features/validation", true);
        reader.setFeature("http://apache.org/xml/features/validation/schema", true);
        reader.setProperty("http://apache.org/xml/properties/schema/external-noNamespaceSchemaLocation", schemaPath);
        // 设置XML验证出错处理
        XmlErrorHandler handler = new XmlErrorHandler();
        reader.setContentHandler(handler);
        reader.setErrorHandler(handler);
        logger.debug("validating XML start...");
        // 解析XML
        reader.parse(xmlPath);
        logger.debug("validating XML over, result is : " + handler.isXmlValidated());
        return handler.isXmlValidated();
    }
}