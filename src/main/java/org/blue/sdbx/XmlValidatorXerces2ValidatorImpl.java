package org.blue.sdbx;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.xml.sax.SAXException;

import javax.xml.XMLConstants;
import javax.xml.transform.Source;
import javax.xml.transform.stream.StreamSource;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;
import javax.xml.validation.Validator;
import java.io.IOException;

/**
 * Created with IntelliJ IDEA.
 * User: WangPu
 * Date: 13-4-11
 * Time: 下午3:11
 * <p/>
 * XML验证接口Xerces2 Validator实现
 */
public class XmlValidatorXerces2ValidatorImpl implements XmlValidator {
    private static Logger logger = LoggerFactory.getLogger(XmlValidatorXerces2ValidatorImpl.class);

    /**
     * 根据XML Schema文件验证XML文件是否正确
     *
     * @param xmlPath    XML文件绝对路径
     * @param schemaPath XSD文件的绝对路径
     * @return 是否正确
     */
    @Override
    public boolean validatingXml(String xmlPath, String schemaPath) throws SAXException, IOException {
        // 获取XSD文件
        Source schemaSource = new StreamSource(schemaPath);
        // 获取XML文件
        Source xmlSource = new StreamSource(xmlPath);

        // 获取Schema工厂类
        SchemaFactory factory = SchemaFactory.newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI);
        // 实例化Schema对象
        Schema schema = factory.newSchema(schemaSource);

        // 获取验证器，验证器的XML Schema源就是之前创建的Schema
        Validator validator = schema.newValidator();
        // 设置XML验证出错处理
        XmlErrorHandler handler = new XmlErrorHandler();
        validator.setErrorHandler(handler);

        logger.debug("validating XML start...");
        // 执行验证
        validator.validate(xmlSource);
        logger.debug("validating XML over, result is : " + handler.isXmlValidated());
        return handler.isXmlValidated();
    }
}