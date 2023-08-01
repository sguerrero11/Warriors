package config;

import com.github.wnameless.json.flattener.JsonFlattener;
import helpers.LoggerHelper;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import java.nio.file.Files;
import java.nio.file.Paths;

@SuppressWarnings("unchecked")
public abstract class Env extends LoggerHelper {
    public static JSONObject properties = null;
    public static String environment = null;

    public static void setProperties() {
        try {
            JSONObject tempBaseProperties;
            JSONObject tempEnvProperties;
            String projectPath = System.getProperty("user.dir");

            // You get baseJson (env_base.json) with flattened format in JSONObject
            String baseEnvJson = new String(Files.readAllBytes(Paths.get(projectPath + "/src/main/java/config/env/env_base.json")));
            String baseFlattenJson = JsonFlattener.flatten(baseEnvJson);
            JSONParser parser = new JSONParser();
            tempBaseProperties = (JSONObject) parser.parse(baseFlattenJson);
            environment = (tempBaseProperties.containsKey("environment")) ? tempBaseProperties.get("environment").toString() : "";

            // You get the localJson ([environment]_env.json) with flattened format in JSONObject
            String localEnvJson = new String(Files.readAllBytes(Paths.get(projectPath + "/src/main/java/config/env/env_" + environment + ".json")));
            String localFlattenJson = JsonFlattener.flatten(localEnvJson);
            parser = new JSONParser();
            tempEnvProperties = (JSONObject) parser.parse(localFlattenJson);

            // You merge both JSONObjects in another final that will be assigned to global
            tempBaseProperties.putAll(tempEnvProperties);
            properties = tempBaseProperties;
            /*String environmentMailtrapInboxId = "services.mailtrap.inbox." + get("environment") + "Id";
            set("services.mailtrap.inbox.id", has(environmentMailtrapInboxId) ? get(environmentMailtrapInboxId) : get("services.mailtrap.inbox.defaultId"));*/
            logInfo("[Env/setProperties] Configuration Properties successfully loaded.");
        } catch (Exception e) {
            logError(e.getMessage());
            logError(e.getCause() != null ? e.getCause().toString() : "[Env] ERROR_INFO: Non Cause");
        }
    }

    /**
     * Get specific property based on received key.
     *
     * @param propertyKey [String]
     * @return [String] Value
     */
    public static String get(String propertyKey) {
        try {
            if (System.getenv(propertyKey.replace(".", "_").replace("-", "_")) != null) {
                return System.getenv(propertyKey.replace(".", "_").replace("-", "_"));
            }
            if (properties.containsKey(propertyKey)) {
                return properties.get(propertyKey).toString();
            } else {
                logWarning("[Env/post] Key: " + propertyKey + " not found");
                return "";
            }
        } catch (NullPointerException e) {
            logError(e.getMessage());
            logError(e.getCause() != null ? e.getCause().toString() : "[Env] ERROR_INFO: Non Cause");
            return "";
        }
    }

    public static Boolean has(String propertyKey) {
        return properties.containsKey(propertyKey);
    }

    /***
     * Add new property to config.properties file.
     * @param propertyKey [Strong]
     * @param propertyValue [String]
     */
    public static void set(String propertyKey, String propertyValue) {
        try {
            properties.replace(propertyKey, propertyValue);
        } catch (Exception e) {
            LoggerHelper.logError("[Env/set]: Property: " + propertyKey + " could not be set.");
            LoggerHelper.logError(e.getMessage());
            LoggerHelper.logError(e.getCause() != null ? e.getCause().toString() : "[Env] ERROR_INFO: Non Cause");
        }
    }

    /***
     * Verify if properties are loaded.
     * @return [Boolean]
     */
    public static boolean arePropertiesLoaded() {
        return properties != null;
    }
}