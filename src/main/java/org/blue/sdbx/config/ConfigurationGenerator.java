package org.blue.sdbx.config;

import org.blue.sdbx.XmlErrorHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * User: wangpu
 * Date: 13-4-16
 * Time: 上午10:05
 * <p/>
 * 配置文件解析类
 * 负责解析配置文件为java对象
 */
public class ConfigurationGenerator {
    private static Logger logger = LoggerFactory.getLogger(ConfigurationGenerator.class);

    private static final String SCHEMA_LANGUAGE_ATTRIBUTE = "http://java.sun.com/xml/jaxp/properties/schemaLanguage";
    private static final String XSD_SCHEMA_LANGUAGE = "http://www.w3.org/2001/XMLSchema";

    private String uri;

    public ConfigurationGenerator(String uri) {
        this.uri = uri;
    }

    public Configuration generateConfiguration()
            throws ParserConfigurationException, IOException, SAXException {
        Configuration configuration = new Configuration();
        logger.debug("parse xml file from path : " + uri);
        // 读取XML
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        //设置使用schema来验证xml文件
        factory.setAttribute(SCHEMA_LANGUAGE_ATTRIBUTE, XSD_SCHEMA_LANGUAGE);
        // 设置是否先验证XML
        factory.setValidating(true);
        factory.setNamespaceAware(true);
        logger.debug("set validating is : " + factory.isValidating()
                + ", and namespaceAware is : " + factory.isNamespaceAware());
        DocumentBuilder builder = factory.newDocumentBuilder();
        XmlErrorHandler xmlErrorHandler = new XmlErrorHandler();
        builder.setErrorHandler(xmlErrorHandler);
        Document document = builder.parse(uri);
        if (!xmlErrorHandler.isXmlValidated()) {
            throw new RuntimeException("XML validating error!");
        }
        // 获取root
        Element root = document.getDocumentElement();
        logger.debug("get root element is : " + root.getTagName());
        configuration.setName(root.getAttribute("name"));
        // 获取唯一的db元素
        Element dbElement = (Element) root.getElementsByTagName("db").item(0);
        configuration.setDb(generateDb(dbElement));
        return configuration;
    }

    private Configuration.Db generateDb(Element dbElement) {
        logger.debug("get from db element!");
        Configuration.Db db = new Configuration.Db();
        db.setType(dbElement.getAttribute("type"));
        db.setSql(dbElement.getAttribute("sql"));
        db.setMapper(generateMapper((Element) dbElement.getElementsByTagName("mapper").item(0)));
        return db;
    }

    private Configuration.Db.Mapper generateMapper(Element mapperElement) {
        logger.debug("get from mapper element!");
        Configuration.Db.Mapper mapper = new Configuration.Db.Mapper();
        mapper.setNamespace(mapperElement.getAttribute("namespace"));
        mapper.setElementMap(generateElementMap(mapperElement.getElementsByTagName("element")));
        return mapper;
    }

    private Map<String, Configuration.Db.Mapper.Element> generateElementMap(NodeList elementList) {
        logger.debug("get from element element!");
        Map<String, Configuration.Db.Mapper.Element> elementMap = new HashMap<String, Configuration.Db.Mapper.Element>();
        for (int i = 0; i < elementList.getLength(); i++) {
            Element e = (Element) elementList.item(i);
            Configuration.Db.Mapper.Element element = new Configuration.Db.Mapper.Element();
            element.setName(e.getAttribute("name"));
            if (e.getElementsByTagName("column").getLength() > 0) {
                element.setColumn(e.getElementsByTagName("column").item(0).getTextContent().trim());
            } else {
                element.setHandler(e.getElementsByTagName("handler").item(0).getTextContent().trim());
                element.setMethod(generateMethod((Element) e.getElementsByTagName("method").item(0)));
            }
            elementMap.put(element.getName(), element);
        }
        return elementMap;
    }

    private Configuration.Db.Mapper.Element.Method generateMethod(Element methodElement) {
        logger.debug("get from method element!");
        Configuration.Db.Mapper.Element.Method method = new Configuration.Db.Mapper.Element.Method();
        method.setName(methodElement.getElementsByTagName("name").item(0).getTextContent().trim());
        Element params = (Element) methodElement.getElementsByTagName("params").item(0);
        method.setParams(generateParamList(params.getElementsByTagName("param")));
        return method;
    }

    private List<Configuration.Db.Mapper.Element.Method.Param> generateParamList(NodeList paramNodeList) {
        logger.debug("get from param element!");
        List<Configuration.Db.Mapper.Element.Method.Param> paramList = new ArrayList<Configuration.Db.Mapper.Element.Method.Param>();
        for (int i = 0; i < paramNodeList.getLength(); i++) {
            Element paramElement = (Element) paramNodeList.item(i);
            Configuration.Db.Mapper.Element.Method.Param param = new Configuration.Db.Mapper.Element.Method.Param();
            param.setValue(paramElement.getElementsByTagName("value").item(0).getTextContent().trim());
            paramList.add(param);
        }
        return paramList;
    }
}
