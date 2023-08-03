package ecommerceUtils;

import java.io.IOException;

import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;

import io.appium.java_client.AppiumDriver;

public class TestListener implements ITestListener{

	ExtentTest extentTest;
	ExtentReports extentReport = ExtentReportNG.configExtentReport();
	AppiumDriver driver;
	String screenShotPath;
	
	
	//result which is a parameter holds information about my @Test methods
	public void onTestStart(ITestResult result) {
		// TODO Auto-generated method stub
		extentTest = extentReport.createTest(result.getMethod().getMethodName());
	}

	public void onTestSuccess(ITestResult result) {
		// TODO Auto-generated method stub
		extentTest.log(Status.PASS, "Test Passed");
	}

	public void onTestFailure(ITestResult result) {
		// TODO Auto-generated method stub
		
		extentTest.fail(result.getThrowable());
		//so we need the driver of the test to be passed here. This Listener class is not extending any class to inherit driver
		//or it may be unnecessary so below code would retrieve the driver
		try {
			driver = (AppiumDriver)result.getTestClass().getRealClass().getField("rDriver").get(result.getInstance());// field name should be same as what we used in test Methods
		} catch (Exception e) {
			// TODO Auto-generated catch block
		}
		
		try {
			screenShotPath = UtilsFunction.getScreenShotPath(result.getMethod().getMethodName(), driver);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		extentTest.addScreenCaptureFromPath(screenShotPath);
		
	}

	public void onTestSkipped(ITestResult result) {
		// TODO Auto-generated method stub
		
	}

	public void onTestFailedButWithinSuccessPercentage(ITestResult result) {
		// TODO Auto-generated method stub
		
	}

	public void onStart(ITestContext context) {
		// TODO Auto-generated method stub
		
	}

	public void onFinish(ITestContext context) {
		// TODO Auto-generated method stub
		extentReport.flush();
	}

}
