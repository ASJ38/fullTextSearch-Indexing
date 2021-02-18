package com.tiggs.core;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.HashMap;
import java.util.Properties;

public class PropertyManager {

    private Properties properties = null;
    public static HashMap PropertyMap = new HashMap<String, String>();
    public static final Logger CoreLogger = LogManager.getLogger(PropertyManager.class);

    public Properties getProperties() {
        return properties;
    }

    private void setPropertiesToHashMap(Properties properties) {
        for (final String key : properties.stringPropertyNames()) {
            PropertyMap.put(key, properties.getProperty(key));
        }
    }

    public void loadPropertyFile(String propertyFilePath) {
        try {
            properties = new Properties();
            CoreLogger.info("Loading property file from: " + propertyFilePath);
            properties.load(new FileInputStream(propertyFilePath));
            this.setPropertiesToHashMap(properties);
        } catch (FileNotFoundException ex) {
            CoreLogger.error("Property file not found@" + propertyFilePath + " " + ex.getMessage());
        } catch (IOException ex) {
            CoreLogger.error("Failed to load property file at " + propertyFilePath + " " + ex.getMessage());
        }
    }
}
