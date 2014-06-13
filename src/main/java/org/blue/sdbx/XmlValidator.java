package org.blue.sdbx;

import org.xml.sax.SAXException;

import java.io.IOException;

/**
 * Created with IntelliJ IDEA.
 * User: WangPu
 * Date: 13-4-11
 * Time: 上午9:54
 *
 * XML验证接口
 */
public interface XmlValidator {

    /**
     * 根据XML Schema文件验证XML文件是否正确
     *
     * @param xmlPath XML文件绝对路径
     * @param schemaPath XSD文件的绝对路径
     * @return 是否正确
     */
    boolean validatingXml(String xmlPath, String schemaPath) throws SAXException, IOException;
}