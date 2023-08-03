package ecommerceTest;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.URL;
import java.time.Duration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.DataProvider;

import ecommerceUtils.UtilsFunction;
import io.appium.java_client.AppiumDriver;
import io.appium.java_client.android.Activity;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.StartsActivity;
import io.appium.java_client.ios.IOSDriver;
import io.appium.java_client.remote.MobileCapabilityType;
import io.appium.java_client.service.local.AppiumDriverLocalService;
import io.appium.java_client.service.local.AppiumServiceBuilder;


public class BaseTest {

	private static final String String = null;

	public AppiumDriver rDriver;
	
	AppiumDriverLocalService service;
	String app;
	String service_path;
	public static Logger logger;
	Properties configProp;
	
	@BeforeMethod(alwaysRun=true)
	public void configureSetUp() throws IOException
	{
		configProp = new Properties();
		FileInputStream reader  = new FileInputStream("Config.Properties");
		configProp.load(reader);
		
		
		String mobilePlatForm = configProp.getProperty("mobilePlatForm");
		String ipAddress = configProp.getProperty("ipAddress");
		String port = configProp.getProperty("port");
	    service_path = configProp.getProperty("servicePath");
	    app =configProp.getProperty("app");
		
		logger = Logger.getLogger(BaseTest.class.getName());
		PropertyConfigurator.configure("log4j.properties");
		
		
		logger.info("*******************Starting the Appium server******************");
		
		service = UtilsFunction.startAppiumServer( service_path, ipAddress, port);
		
		//URL url = new URL("http://127.0.0.1:4723");
		
		//Setting Common desired Capabilities
		DesiredCapabilities options =  new DesiredCapabilities();
    	options.setCapability(MobileCapabilityType.APP,app );
    	options.setCapability(MobileCapabilityType.PLATFORM_NAME, "Android");
    	options.setCapability(MobileCapabilityType.AUTOMATION_NAME,"UIAutomator2");
		if (mobilePlatForm.equalsIgnoreCase("Android Virtual")) {
			logger.info("*******************Launching Android vitual device******************");
			options.setCapability(MobileCapabilityType.DEVICE_NAME, "Android Emulator");
			
		//	rDriver = new AndroidDriver(url, options); this works too
			rDriver = new AndroidDriver(service.getUrl(), options);
			
		} else if (mobilePlatForm.equalsIgnoreCase("Android Real")) {
			logger.info("*******************Launching Android Real device******************");
			options.setCapability(MobileCapabilityType.DEVICE_NAME, "Android");
			options.setCapability(MobileCapabilityType.PLATFORM_VERSION, "12");
			
			rDriver = new AndroidDriver(service.getUrl(), options);
		
		}else if(mobilePlatForm.equalsIgnoreCase("iOS virtual"))
		{
			logger.info("*******************Launching iOS vitual device******************");
			rDriver = new IOSDriver(service.getUrl(),options);
			
		}else if(mobilePlatForm.equalsIgnoreCase("iOS real"))
		{
			logger.info("*******************Launching iOS Real device******************");
			rDriver = new IOSDriver(service.getUrl(),options);
			
		}else
		{
			logger.info("*******************No device set up******************");
		}
		rDriver.manage().timeouts().implicitlyWait(Duration.ofSeconds(5));
		//return rDriver;
		
        
	}
   
	
	//Activity was not working because of permission denied for the android intent was the error
	//Hence I made the configure method to start the server and install app again 
	//@BeforeMethod
	public void setAppFormPage()
	{
		String appPackage = "com.androidsample.generalstore";
		String appActivity ="com.androidsample.generalstore.MainActivity";
		
		((StartsActivity) rDriver).startActivity(new Activity(appPackage,appActivity));
		
		/*
		 * JavascriptExecutor js = (JavascriptExecutor)rDriver;
		 * js.executeScript("mobile: startActivity", "{appPackage: '" + appPackage +
		 * "', appActivity: '" + appActivity + "'}");
		 */
		
		
		/*
		 * Activity activity = new Activity(appPackage, appActivity);
		 * ((StartsActivity)rDriver).startActivity(activity);
		 */
		 
	}
	
	
	@DataProvider
	public Object[][] getData() throws IOException
	{
		String jsonFilePath ="C:\\Users\\Jega\\eclipse-workspace\\AppiumMobileAutomationProject\\ecommerce_Data.json";
		
		List<HashMap<String, String>> dataList = UtilsFunction.getJsonData(jsonFilePath);
		Object data[][] = new Object[dataList.size()][1];
		for(int i=0;i<dataList.size();i++)
		{
			HashMap<String, String> dataMap = dataList.get(i);
			data[i] = new Object[]{dataMap};
		}
		return data;
	}
	@AfterMethod(alwaysRun=true)
	public void tearDown()
	{
		logger.info("********************Stopping the appium server*******************");
		service.start();
		
		logger.info("********************Stopping the driver *******************");
		rDriver.quit();
		
	}
	
}
