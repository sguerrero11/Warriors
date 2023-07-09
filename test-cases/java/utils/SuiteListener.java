package utils;

import designpattern.pom.BasePage;
import helpers.BrowserDriverHelper;
import org.testng.ISuite;
import org.testng.ISuiteListener;
import org.testng.ITestContext;
import org.testng.ITestListener;

public class SuiteListener extends BasePage implements ISuiteListener {

    @Override
    public void onStart(ISuite suite) {
    }
}
