package pageObject.Android;


import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;

import ecommerceUtils.AndroidGestures;
import ecommerceUtils.UtilsFunction;
import io.appium.java_client.AppiumDriver;
import io.appium.java_client.pagefactory.AndroidFindBy;
import io.appium.java_client.pagefactory.AppiumFieldDecorator;

public class CartPage {
	AppiumDriver driver;
	public CartPage(AppiumDriver driver)
	{
		this.driver=driver;
		PageFactory.initElements(new AppiumFieldDecorator(driver), this);
	}
	
	@AndroidFindBy(id="com.androidsample.generalstore:id/toolbar_title")
	public WebElement toolBar;

	@AndroidFindBy(id="com.androidsample.generalstore:id/productName")
	public WebElement product_in_cart;
	
	@AndroidFindBy(id="com.androidsample.generalstore:id/productPrice")
	public List<WebElement> productPriceInCart;
	
    @AndroidFindBy(id="com.androidsample.generalstore:id/totalAmountLbl")
    public WebElement totalPriceInCartLabel; 
	
    @AndroidFindBy(xpath ="//android.widget.LinearLayout/android.widget.CheckBox")
    public WebElement notificationCheckBox;
    
    @AndroidFindBy(id="com.androidsample.generalstore:id/termsButton")
    public WebElement termsAndConditionBtn;
	
    @AndroidFindBy(id="com.androidsample.generalstore:id/alertTitle")
    public WebElement alertTitle;
    
    @AndroidFindBy(id="android:id/button1")
    public WebElement closeButton;
    
	public float getTotalPriceOfProductsInCart()
	{
		float sum = 0.0f;

		for (WebElement element : productPriceInCart) {
			String price = element.getAttribute("text"); 
			UtilsFunction utils = new UtilsFunction(driver);
			float price_in_cart = utils.formatStringPrice(price);
			sum = sum + price_in_cart;

		}
		return sum;
	}
	
	public float getTotalPriceInCartLabel()
	{
		String totalPriceInCartLabel_Value = totalPriceInCartLabel.getText();//getAttribute("text");
		UtilsFunction utils = new UtilsFunction(driver);
		float totalPriceLabel = utils.formatStringPrice(totalPriceInCartLabel_Value);
		return totalPriceLabel;
		
	}
	
	
	public void confirmCheckOut()
	{
		float sum = getTotalPriceOfProductsInCart();
		float totalPriceLabel = getTotalPriceInCartLabel();
		if(sum==totalPriceLabel)
		{
			notificationCheckBox.click();
			
		}
		AndroidGestures gesture = new AndroidGestures(driver);
		gesture.longClick(termsAndConditionBtn);		
	}
}
