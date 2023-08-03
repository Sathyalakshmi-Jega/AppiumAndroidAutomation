package ecommerceUtils;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.remote.RemoteWebElement;

import com.google.common.collect.ImmutableMap;

import io.appium.java_client.AppiumBy;
import io.appium.java_client.AppiumDriver;

public class AndroidGestures {
	public AppiumDriver driver;
	
	
	public AndroidGestures(AppiumDriver driver)
	{
		this.driver= driver;
	}
	public void scrollByElement(String text) {
		
		WebElement elementToChoose = driver.findElement(AppiumBy.androidUIAutomator("new UiScrollable(new UiSelector()).scrollIntoView(text(\""+text+"\"));"));
		elementToChoose.click();
		
	}
	public void scrollToEnd() {
	 JavascriptExecutor js = (JavascriptExecutor)driver;
	 boolean canScrollMore;
	 do {
	     canScrollMore = (Boolean)js.executeScript("mobile:scrollGesture",ImmutableMap.of("left",100,"top",100,"width",200,"height",200,"direction","down","percent",3.0));
	 }
	 while(canScrollMore);
	}

	public void longClick(WebElement element)
	{
		JavascriptExecutor js = (JavascriptExecutor)driver;
		js.executeScript("mobile:longClickGesture", ImmutableMap.of("elementId",((RemoteWebElement)element).getId(),"duration",2000));
	}
}
