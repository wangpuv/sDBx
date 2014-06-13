package org.blue.sdbx.xsd.aa.handler;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created with IntelliJ IDEA.
 * User: wangpu
 * Date: 13-4-15
 * Time: 下午4:35
 */
public class AaHandler {
    private static Logger logger = LoggerFactory.getLogger(AaHandler.class);

    public String getMainAcctRoleListInfo(String id) {
        logger.debug("Class[AaHandler] Method[getMainAcctRoleListInfo] invoke. param is : " + id);
        return "test1Role,test2Role";
    }
}
