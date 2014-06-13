package org.blue.sdbx;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.xml.sax.SAXParseException;
import org.xml.sax.ext.DefaultHandler2;

/**
 * Created with IntelliJ IDEA.
 * User: WangPu
 * Date: 13-4-11
 * Time: 下午3:38
 * <p/>
 * XML验证出错处理
 * 返回XML验证是否出错，如果出错打印错误日志，但是不抛出SAXException异常
 * 如果需要错误异常，请判断xmlValidated自行处理
 */
public class XmlErrorHandler extends DefaultHandler2 {
    private static Logger logger = LoggerFactory.getLogger(XmlErrorHandler.class);

    private boolean xmlValidated = true;

    public boolean isXmlValidated() {
        return xmlValidated;
    }

    public void setXmlValidated(boolean xmlValidated) {
        this.xmlValidated = xmlValidated;
    }

    @Override
    public void warning(SAXParseException e) {
        logger.error("验证XML中发现错误，相关信息如下：\n" + getMessage("Warning", e));
        setXmlValidated(false);
    }

    @Override
    public void error(SAXParseException e) {
        logger.error("验证XML中发现错误，相关信息如下：\n" + getMessage("Error", e));
        setXmlValidated(false);
    }

    private String getMessage(String level, SAXParseException e) {
        return "解析级别 " + level + "\t" + "出错行:" + e.getLineNumber() + "\t" + "文件目录:" + e.getSystemId() + "\n"
                + "出错消息:" + e.getMessage();
    }
}