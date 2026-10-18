package com.automation.utils;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

/**
 * ConfigReader - Reads configuration values from config.properties file.
 * This class follows the Singleton-like pattern for loading properties once.
 */
public class ConfigReader {

    // Properties object to hold all key-value pairs from the config file
    private static Properties properties;

    // Static block runs once when the class is first loaded
    static {
        try {
            // Create a new Properties object
            properties = new Properties();

            // Path to the config.properties file (relative to project root when running from IDE/Maven)
            String configPath = "src/test/resources/config.properties";

            // Open the file as input stream
            FileInputStream fis = new FileInputStream(configPath);

            // Load all properties from the file into the Properties object
            properties.load(fis);

            // Close the stream to free resources
            fis.close();
        } catch (IOException e) {
            // If file is missing or unreadable, throw a runtime exception with clear message
            throw new RuntimeException("Failed to load config.properties file: " + e.getMessage());
        }
    }

    /**
     * Returns the value for a given key from config.properties
     * @param key the property key (e.g. "browser", "baseUrl")
     * @return the corresponding value as String
     */
    public static String getProperty(String key) {
        // Retrieve value; returns null if key does not exist
        String value = properties.getProperty(key);

        // Optional safety: throw exception if key is missing (helps catch typos early)
        if (value == null) {
            throw new RuntimeException("Property key '" + key + "' not found in config.properties");
        }
        return value;
    }
}
