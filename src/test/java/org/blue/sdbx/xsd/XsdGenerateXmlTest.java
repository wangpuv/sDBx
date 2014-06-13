package org.blue.sdbx.xsd;

import org.apache.commons.io.FileUtils;
import org.junit.Test;

import javax.xml.bind.JAXBException;
import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.XMLGregorianCalendar;
import java.io.*;
import java.math.BigInteger;
import java.util.Date;
import java.util.GregorianCalendar;

/**
 * Created with IntelliJ IDEA.
 * User: WangPu
 * Date: 13-4-12
 * Time: 下午3:48
 */
public class XsdGenerateXmlTest {
    @Test
    public void aaGenerate() throws IOException, JAXBException {
        // 创建aa对象工厂
        org.blue.sdbx.xsd.aa.ObjectFactory objectFactory = new org.blue.sdbx.xsd.aa.ObjectFactory();
        // 创建sdbx
        org.blue.sdbx.xsd.aa.Sdbx sdbx = objectFactory.createSdbx();
        sdbx.setType("AA");
        sdbx.setProvince(BigInteger.valueOf(100));
        sdbx.setCreatetime(convertToXMLGregorianCalendar(new Date()));
        sdbx.setSum(BigInteger.valueOf(1));
        sdbx.setBegintime(convertToXMLGregorianCalendar(new Date()));
        sdbx.setEndtime(convertToXMLGregorianCalendar(new Date()));
        // 创建data
        org.blue.sdbx.xsd.aa.Sdbx.Data data = objectFactory.createSdbxData();
        org.blue.sdbx.xsd.aa.Sdbx.Data.Rcd rcd = objectFactory.createSdbxDataRcd();
        rcd.setSeq(BigInteger.valueOf(1));
        rcd.setMainacctid("12345");
        rcd.setLoginname("test");
        rcd.setUsername("test");
        rcd.setValid(BigInteger.valueOf(1));
        rcd.setLockstatus(BigInteger.valueOf(0));

        data.getRcd().add(rcd);
        sdbx.setData(data);

        File aaXml = new File(getClass().getResource("/").getFile() + "/xml/aa_test.xml");
        FileUtils.touch(aaXml);

        JAXBUtils.marshal("org.blue.sdbx.xsd.aa", sdbx, new FileOutputStream(aaXml));
    }

    @Test
    public void xmlToObject() throws FileNotFoundException, JAXBException {
        File aaXml = new File(getClass().getResource("/").getFile() + "/xml/aa_test.xml");
        org.blue.sdbx.xsd.aa.Sdbx sdbx = (org.blue.sdbx.xsd.aa.Sdbx)
                JAXBUtils.unMarshal("org.blue.sdbx.xsd.aa", new FileInputStream(aaXml));
        for (org.blue.sdbx.xsd.aa.Sdbx.Data.Rcd rcd : sdbx.getData().getRcd()) {
            System.out.println(rcd.getSeq());
        }
    }

    private XMLGregorianCalendar convertToXMLGregorianCalendar(Date date) {
        GregorianCalendar cal = new GregorianCalendar();
        cal.setTime(date);
        XMLGregorianCalendar gc = null;
        try {
            gc = DatatypeFactory.newInstance().newXMLGregorianCalendar(cal);
        } catch (Exception e) {
            // todo
            e.printStackTrace();
        }
        return gc;
    }
}
