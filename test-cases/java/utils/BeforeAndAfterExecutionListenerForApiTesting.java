package utils;

import helpers.EnvHelper;
import helpers.LoggerHelper;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.mime.MultipartEntityBuilder;
import org.apache.http.entity.mime.content.FileBody;
import org.apache.http.impl.client.HttpClientBuilder;
import org.json.simple.JSONObject;
import org.testng.IExecutionListener;

import java.io.File;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class BeforeAndAfterExecutionListenerForApiTesting implements IExecutionListener {
/*
    @Override
    public void onExecutionFinish()
    {
        if(Env.get("global.zephyr.synchronize") == "true")
        {
            // Upload test to Zephyr
            File dirFile = new File("./test-output/xml/");

            ZipFiles zipFiles = new ZipFiles();
            zipFiles.zipDirectory(dirFile, "./test-output/xml/all.zip");

            File dirZip = new File("./test-output/xml/all.zip");

            DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
            LocalDateTime now = LocalDateTime.now();

            JSONObject testCycle = new JSONObject();
            testCycle.put("name", Env.get("global.zephyr.name") + " run executed on " + dtf.format(now) + " [ENV: " + Env.get("environment") + "]");
            testCycle.put("folderId", Env.get("global.zephyr.testCycleFolder"));

            HttpEntity entity = MultipartEntityBuilder.create()
                    .addPart("file", new FileBody(dirZip))
                    .addTextBody("testCycle", testCycle.toJSONString(), ContentType.parse("application/json"))
                    .build();

            HttpPost request = new HttpPost(Env.get("global.zephyr.url") + "/automations/executions/junit?projectKey=ECP");
            request.addHeader("Authorization", "Bearer " + Env.get("global.zephyr.access_token"));
            request.setEntity(entity);

            HttpClient client = HttpClientBuilder.create().build();
            try {
                HttpResponse response = client.execute(request);
                LoggerHelper.logInfo(response.toString());
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }
*/
}

