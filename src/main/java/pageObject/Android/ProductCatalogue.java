package pageObject.Android;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;

import ecommerceUtils.AndroidGestures;
import io.appium.java_client.AppiumDriver;
import io.appium.java_client.pagefactory.AndroidFindBy;
import io.appium.java_client.pagefactory.AppiumFieldDecorator;



public class ProductCatalogue {

	AppiumDriver driver;
	
	
	public ProductCatalogue(AppiumDriver driver)
	{
		this.driver=driver;
		PageFactory.initElements(new AppiumFieldDecorator(driver), this);
	}
	
	
	@AndroidFindBy(id="com.androidsample.generalstore:id/productName")
	List<WebElement> products;
	
	@AndroidFindBy(xpath="//android.widget.TextView[@resource-id='com.androidsample.generalstore:id/productAddCart']")
	List<WebElement> addProductToCartlnk;
		
	@AndroidFindBy(xpath="//android.widget.ImageButton[@resource-id='com.androidsample.generalstore:id/appbar_btn_cart']")
	public WebElement shoppingCartBtn;
	
	@AndroidFindBy(id="com.androidsample.generalstore:id/toolbar_title")
	public WebElement toolBarTitle;
		
	public void addItemToCartByName(String productname)
	{
		AndroidGestures gesture = new AndroidGestures(driver);
		gesture.scrollByElement(productname); // "Air Jordan 9 Retro"
		int product_size = products.size();

		for (int i = 0; i < product_size; i++) {
			String product_name = products.get(i).getText();

			if (product_name.equals("Air Jordan 9 Retro")) {
				addProductToCartlnk.get(i).click();
				break;
			}
		}

	}
	
	public void addMultipleItemToCart(int numberofItemToAdd)
	{
		int count=0;
	
		for(WebElement element : addProductToCartlnk)
		{
			element.click();
			count++;
			
			if(count==numberofItemToAdd)
			{
				break;
			}
			
		}
	}
	
	
	public CartPage clickShoppingCartButton() {
		shoppingCartBtn.click();
		CartPage cart = new CartPage(driver);
		return cart;
	}
	
}
