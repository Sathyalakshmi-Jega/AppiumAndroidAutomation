package ecommerceUtils;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.time.Duration;
import java.util.HashMap;
import java.util.List;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.service.local.AppiumDriverLocalService;
import io.appium.java_client.service.local.AppiumServiceBuilder;

public class UtilsFunction {
	
	AppiumDriver driver;
	public UtilsFunction(AppiumDriver driver)
	{
		this.driver=driver;
	}

	/**
	 * 
	 * @param driver
	 * @param element
	 * @param duration
	 */
public void explicitWaitByAttribute(AppiumDriver driver,WebElement element, int duration)
{
	WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(duration));
	wait.until(ExpectedConditions.attributeContains(element, "text", "Cart"));
}

/**
 * format String 
 * @param price
 * @return
 */
public float formatStringPrice(String price)
{
	 price = price.replaceAll("[^\\d.]", ""); // for removing non numeric character $ we can use replace all,also we can use substring(1)
	 float price_in_cart = Float.parseFloat(price);
  	 return price_in_cart;
}

/**
 * Function to convert json file to HashMap
 * @param jsonFilePath
 * @return
 * @throws IOException
 */
public static List<HashMap<String,String>> getJsonData(String jsonFilePath) throws IOException
{
	String jsonContent = FileUtils.readFileToString(new File(jsonFilePath),StandardCharsets.UTF_8);
	ObjectMapper mapper = new ObjectMapper();
	
	List<HashMap<String,String>> data = mapper.readValue(jsonContent, new TypeReference<List<HashMap<String,String>>>(){});
	
	return data;
	
}

public static AppiumDriverLocalService startAppiumServer(String service_path, String ipAddress, String port)
{
	AppiumDriverLocalService service = new AppiumServiceBuilder().withAppiumJS(new File(service_path)).withIPAddress(ipAddress)
			                       .usingPort(Integer.parseInt(port)).build();
	service.start();
	
	return service;
}

public static String getScreenShotPath(String testCaseName, AppiumDriver driver) throws IOException
{
	File source = driver.getScreenshotAs(OutputType.FILE);
	String filePath = System.getProperty("user.dir")+"\\target"+testCaseName+".png";
	FileUtils.copyFile(source ,new File(filePath));
	return filePath;
}
}