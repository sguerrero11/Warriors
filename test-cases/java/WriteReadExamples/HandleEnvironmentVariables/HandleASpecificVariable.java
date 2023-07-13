package WriteReadExamples.HandleEnvironmentVariables;

import org.testng.annotations.Test;

import java.util.Map;

public class HandleASpecificVariable {

    @Test(description = "Print all the environment variables from your SO")
    public void showEnvVariables() {
        Map<String, String> env = System.getenv();
        for (String envName : env.keySet()) {
            System.out.format("%s=%s%n",
                    envName,
                    env.get(envName));
        }
    }

    @Test(description = "Print specific environment variables from your SO")
    public void showSpecificVariable() {
        String[] vars = {
                "HOME_PATH",
                "HOMEPATH",
                "JAVA_HOME"
        };
        for (String env : vars) {
            String value = System.getenv(env);
            if (value != null) {
                System.out.format("%s = %s%n",
                        env, value);
            } else {
                System.out.format("%s is"
                        + " not assigned.%n", env);
            }
        }
    }

}
