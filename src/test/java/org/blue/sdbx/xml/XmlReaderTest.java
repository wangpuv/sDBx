package org.blue.sdbx.xml;

import org.junit.Test;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.Attributes;
import org.xml.sax.EntityResolver;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import javax.xml.parsers.*;
import javax.xml.xpath.*;
import java.io.File;
import java.io.IOException;

/**
 * Created with IntelliJ IDEA.
 * User: wangpu
 * Date: 13-4-15
 * Time: 上午10:10
 * <p/>
 * XML解析读取测试
 */
public class XmlReaderTest {
    private static final String SCHEMA_LANGUAGE_ATTRIBUTE = "http://java.sun.com/xml/jaxp/properties/schemaLanguage";
    private static final String XSD_SCHEMA_LANGUAGE = "http://www.w3.org/2001/XMLSchema";

    /* 使用JAXB 读取XML为DOM对象 进行解析 */
    @Test
    public void jaxbReadXml() throws ParserConfigurationException, IOException, SAXException, XPathExpressionException {
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        //设置使用schema来验证xml文件
        factory.setAttribute(SCHEMA_LANGUAGE_ATTRIBUTE, XSD_SCHEMA_LANGUAGE);
        // 设置是否先验证XML
        //factory.setValidating(true);
        factory.setNamespaceAware(true);
        DocumentBuilder builder = factory.newDocumentBuilder();
        // 获取要验证XML的XSD
        //builder.setEntityResolver(new SchemaLoader());
        //读取并解析xml文件，创建Document对象
        Document doc = builder.parse(new File(getClass().getResource("/").getFile() + "/xml/SS.xml"));
        Element root = doc.getDocumentElement();
        NodeList nodes = root.getChildNodes();
        for (int i = 0; i < nodes.getLength(); i++) {
            Node node = nodes.item(i);
            if (node.getNodeType() == Node.ELEMENT_NODE) {
                System.out.println(node.getNodeName() + " --> " + node.getTextContent());
            }
        }

        // 使用XPath读取
        XPathFactory pathFactory = XPathFactory.newInstance();
        XPath xpath = pathFactory.newXPath();
        XPathExpression pathExpression = xpath.compile("//data/rcd[seq='1']/mainacctid/text()");
        Object result = pathExpression.evaluate(doc, XPathConstants.NODESET);
        NodeList xNodes = (NodeList) result;
        for (int i = 0; i < xNodes.getLength(); i++) {
            System.out.println(xNodes.item(i).getNodeValue());
        }
    }

    /* 在读取xml文件之前，可以setValidating方法来指定是否对xml进行验证，如果不满足格式的xml可以不执行读取操作。
       比较常用的xml的格式验证方法有两种：DTD和schema。默认情况下，jaxp会使用DTD来验证xml文档，
       如果需要使用schema来验证，
       那么需要这行语句：factory.setAttribute("http://java.sun.com/xml/jaxp/properties/schemaLanguage", "http://www.w3.org/2001/XMLSchema");
       现在存在这样一个问题，xml的验证文件如何读取？即程序是如何定位到DTD或者是schema文件呢？
       答案在EntityResolver。可以通过实现这个接口来指明如何加载验证文件。 */
    class SchemaLoader implements EntityResolver {
        @Override
        public InputSource resolveEntity(String publicId, String systemId) throws SAXException, IOException {
            if (systemId.endsWith(".xsd")) {
                // todo
                return new InputSource(SchemaLoader.class.getClassLoader().getResourceAsStream("xxxxx.xsd"));
            }
            return null;
        }

    }

    /* 目前使用SAX比较的多,与DOM主要区别是 SAX是一行一行读取XML文件进行分析,适合比较大文件,DOM是一次性读入内存,显然不能对付大文件 */
    @Test
    public void saxReadXml() throws ParserConfigurationException, SAXException, IOException {
        // 将我们的解析器对象化
        ConfigParser handler = new ConfigParser();

        // 获取SAX工厂对象
        SAXParserFactory factory = SAXParserFactory.newInstance();
        factory.setNamespaceAware(false);
        factory.setValidating(false);

        //获取SAX解析
        SAXParser parser = factory.newSAXParser();

        //将解析器和解析对象myenv.xml联系起来,开始解析
        parser.parse(getClass().getResource("/").getFile() + "/xml/SS.xml", handler);
    }

    class ConfigParser extends DefaultHandler {
        private String currentSet;
        private String currentName;
        private StringBuffer currentValue = new StringBuffer();

        // 定义开始解析元素的方法. 这里是将<xxx>中的名称xxx提取出来.
        public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {
            currentValue.delete(0, currentValue.length());
            this.currentName = qName;
        }

        // 这里是将<xxx></xxx>之间的值加入到currentValue
        public void characters(char[] ch, int start, int length) throws SAXException {
            currentValue.append(ch, start, length);
        }

        //在遇到</xxx>结束后,将之前的名称和值处理一下
        public void endElement(String uri, String localName, String qName) throws SAXException {
            System.out.println(qName.toLowerCase() + " --> " + currentValue.toString().trim());
        }
    }
}
