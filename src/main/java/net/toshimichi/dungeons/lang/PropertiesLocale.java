package net.toshimichi.dungeons.lang;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.Properties;

/**
 * 言語ファイルを管理します.
 */
public class PropertiesLocale implements Locale {

    private Properties properties = new Properties();

    public PropertiesLocale(InputStream in) throws IOException {
        try(InputStreamReader reader = new InputStreamReader(in, StandardCharsets.UTF_8)) {
            properties.load(reader);
        }
    }

    public PropertiesLocale(File f) throws IOException {
        this(new FileInputStream(f));
    }

    public String get(String key) {
        return properties.getProperty(key);
    }
}
