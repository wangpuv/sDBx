package org.blue.sdbx.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * User: wangpu
 * Date: 13-4-16
 * Time: 上午9:15
 * <p/>
 * 配置工厂类
 * 根据不同配置类型产生不同配置对象
 */
public class ConfigurationFactory {
    private static Logger logger = LoggerFactory.getLogger(ConfigurationFactory.class);

    public static final String CONFIGURATION_FILE_PATH = "/xmlMappingDb/"; // 默认配置文件路径

    public static final String CONFIGURATION_FILE_NAME = "XmlMapper.xml"; // 默认配置文件后缀

    private static final Map<String, Configuration> configurationMap = new HashMap<String, Configuration>();

    private static ConfigurationFactory configurationFactory;

    private ConfigurationFactory() {
    }

    public static ConfigurationFactory getInstance() {
        logger.debug("get ConfigurationFactory instance!");
        if (configurationFactory == null) {
            configurationFactory = new ConfigurationFactory();
        }
        return configurationFactory;
    }

    public Configuration getConfiguration(String typeName)
            throws IOException, SAXException, ParserConfigurationException {
        logger.debug("get Configuration Object! typeName is : " + typeName + "!");
        Configuration configuration = configurationMap.get(typeName);
        if (configuration == null) {
            logger.debug(typeName + " Configuration Object not in map, must parse from xml!");
            String configurationFileName = typeName + CONFIGURATION_FILE_NAME;
            String uri = getClass().getResource(CONFIGURATION_FILE_PATH + configurationFileName).getFile();
            ConfigurationGenerator parse = new ConfigurationGenerator(uri);
            configuration = parse.generateConfiguration();
            logger.debug("Configuration Object put in map, typeName is : " + typeName + "!");
            configurationMap.put(typeName, configuration);
        }
        return configuration;
    }
}
