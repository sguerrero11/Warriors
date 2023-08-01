package WriteReadExamples;

import designpattern.pom.BasePage;
import helpers.YMLHelper;
import org.testng.annotations.Test;
import org.yaml.snakeyaml.Yaml;

import java.io.File;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.LinkedHashMap;
import java.util.Map;

public class ReadFromYMLFiles {

    @Test
    public void getProperty() {

        String folderPath = System.getProperty("user.dir") + File.separator + "src" + File.separator + "main" + File.separator + "java" + File.separator + "config" + File.separator;
        String filePath = folderPath + "config.yaml";

        try {
            // Load the YML reader object
            YMLHelper yml = new YMLHelper(filePath) ;

            // Access the values directly from the nested LinkedHashMap using the helper method
            String databaseHost = YMLHelper.getYMLValue(yml.yamlData, "database.host");
            Integer databasePort = YMLHelper.getYMLValue(yml.yamlData, "database.port");
            String appVersion = YMLHelper.getYMLValue(yml.yamlData, "app.version");
            Boolean appDebug = YMLHelper.getYMLValue(yml.yamlData, "app.debug");
            // Print the values
            System.out.println("Database Host: " + databaseHost);
            System.out.println("Database Port: " + databasePort);
            System.out.println("App Version: " + appVersion);
            System.out.println("App Debug: " + appDebug);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
