package helpers;

import com.github.wnameless.json.flattener.JsonFlattener;
import designpattern.pom.BasePage;
import org.yaml.snakeyaml.Yaml;

import java.io.File;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.LinkedHashMap;
import java.util.Map;

public class YMLHelper {

    public static LinkedHashMap<String, Object> yamlData;
    // Define config file
    private static String folderPath = System.getProperty("user.dir") + File.separator + "src" + File.separator + "main" + File.separator + "java" + File.separator + "config" + File.separator;
    private static String filePath = folderPath + "config.yaml";

    public static void setProperties() {
        try {
            // Load the YAML file into a nested LinkedHashMap directly

            InputStream ymlInputStream = Files.newInputStream(Paths.get(filePath));

            Yaml yaml = new Yaml();
            yamlData = yaml.load(ymlInputStream);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public static <T> T getYMLValue(Map<String, Object> map, String key) {
        String[] keys = key.split("\\.");
        for (int i = 0; i < keys.length - 1; i++) {
            if (map == null) {
                return null;
            }
            map = (Map<String, Object>) map.get(keys[i]);
        }
        if (map == null) {
            return null;
        }
        return (T) map.get(keys[keys.length - 1]);
    }

    public void close(){

    }


}

