package com.zoufanqi.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class ConfigUtil {
    private static final Logger LOG = LoggerFactory.getLogger(ConfigUtil.class);
    private static final Properties props = new Properties();

    static {
        InputStream is = null;
        try {
            is = ConfigUtil.class.getClassLoader().getResourceAsStream("config.properties");
            props.load(is);
        } catch (IOException e) {
            LOG.error(ExceptionUtil.getExceptionAllMsg(e));
        } finally {
            try {
                if (is != null) is.close();
            } catch (IOException e) {
                LOG.error(ExceptionUtil.getExceptionAllMsg(e));
            }
        }
    }

    public static String get(String key) {
        return props.getProperty(key);
    }

}
