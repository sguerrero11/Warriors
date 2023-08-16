package WriteReadExamples;

import helpers.YMLHelper;
import org.testng.annotations.Test;


public class ReadFromYMLFiles {

    @Test
    public void getPropertyUsingSnake() {

//        String folderPath = System.getProperty("user.dir") + File.separator + "src" + File.separator + "main" + File.separator + "java" + File.separator + "config" + File.separator;
//        String filePath = folderPath + "config.yaml";

        try {
            // Load the YML reader object
            YMLHelper.setProperties();

            // Access the values directly from the nested LinkedHashMap using the helper method
            String databaseHost = YMLHelper.getYMLValue(YMLHelper.yamlData,"database.host");
            Integer databasePort = YMLHelper.getYMLValue(YMLHelper.yamlData,"database.port");
            String appVersion = YMLHelper.getYMLValue(YMLHelper.yamlData,"app.version");
            Boolean appDebug = YMLHelper.getYMLValue(YMLHelper.yamlData,"app.debug");
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
