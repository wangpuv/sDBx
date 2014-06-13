package org.blue.sdbx.xsd;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

/**
 * Created with IntelliJ IDEA.
 * User: WangPu
 * Date: 13-4-12
 * Time: 下午4:21
 */
public class JAXBUtils {
    private static Logger logger = LoggerFactory.getLogger(JAXBUtils.class);

    private JAXBUtils() {
        throw new UnsupportedOperationException();
    }

    /**
     * 将XML反序列化为对象
     *
     * @param contextPath 指定对应对象的包路径（必须含有ObjectFactory）
     * @param xmlStream   XML流文件
     * @return Object 对象
     * @throws JAXBException
     */
    public static Object unMarshal(String contextPath, InputStream xmlStream) throws JAXBException {
        JAXBContext jc = JAXBContext.newInstance(contextPath);
        Unmarshaller u = jc.createUnmarshaller();

        return u.unmarshal(xmlStream);
    }

    /**
     * 将对象序列化为XML文件（编码为UTF-8）
     *
     * @param contextPath 指定对应对象的包路径（必须含有ObjectFactory）
     * @param obj         对象
     * @param stream      XML流文件
     * @throws JAXBException
     */
    public static void marshal(String contextPath, Object obj, OutputStream stream) throws JAXBException, IOException {
        logger.debug("jaxb marshal java object to xml ...");
        JAXBContext jc = JAXBContext.newInstance(contextPath);
        Marshaller m = jc.createMarshaller();
        m.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
        m.setProperty(Marshaller.JAXB_ENCODING, "UTF-8");
        logger.debug("jaxb marshal use encode is : " + m.getProperty(Marshaller.JAXB_ENCODING));

        // 为了去掉jaxb自动生成的头部“standalone”，自定义了头部
        m.setProperty(Marshaller.JAXB_FRAGMENT, Boolean.TRUE);
        stream.write("<?xml version=\"1.0\" encoding=\"UTF-8\"?>".getBytes("UTF-8"));

        /*m.setProperty(CharacterEscapeHandler.class.getName(),
                new CharacterEscapeHandler() {
                    @Override
                    public void escape(char[] ac, int i, int j, boolean flag,
                                       Writer writer) throws IOException {
                        writer.write(ac, i, j);
                    }
                });*/

        m.marshal(obj, stream);
    }
}
