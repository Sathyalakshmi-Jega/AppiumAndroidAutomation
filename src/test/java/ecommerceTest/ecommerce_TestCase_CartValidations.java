package ecommerceTest;

import java.net.MalformedURLException;
import java.util.HashMap;

import org.testng.Assert;
import org.testng.annotations.Test;

import ecommerceUtils.UtilsFunction;
import pageObject.Android.CartPage;
import pageObject.Android.FormPage;
import pageObject.Android.ProductCatalogue;

public class ecommerce_TestCase_CartValidations extends BaseTest{

	@Test(dataProvider="getData", groups = {"smoke"})
	public void verifyProductInCart(HashMap<String,String> input) throws MalformedURLException
	{
	    logger.info("**************Test successful addition of product*******************");
		String product_to_add = "Air Jordan 9 Retro";
		FormPage fillForm = new FormPage(rDriver);
		fillForm.completeFormSubmission(input.get("name"), input.get("country"), input.get("gender"));

		ProductCatalogue product = new ProductCatalogue(rDriver);
		product.addItemToCartByName(product_to_add);
		

		CartPage cart = product.clickShoppingCartButton();

		// Why we need wait is since both product and product_in_cart both have same id
		// and even before cart page can load this validation gets asserted in
		// the product page itself then the assertion would pass but its not correct way
		// to validate so added a webDriverwait to check for the cart page title and then assertion
		// is done text is attribute and cart is value
		 UtilsFunction utils = new UtilsFunction(rDriver);
		 utils.explicitWaitByAttribute(rDriver,cart.toolBar, 5);
																							

		 String product_in_cart = cart.product_in_cart.getText();
		 Assert.assertEquals(product_in_cart, product_to_add);
		 
		 
	}
	
	@Test(dataProvider="getData",groups= {"smoke"})
	public void verifytotalPriceCalculation(HashMap<String,String> input) throws MalformedURLException {

	    logger.info("**************Test price verification for product in cart*******************");
		
		FormPage fillForm = new FormPage(rDriver);
		fillForm.completeFormSubmission(input.get("name"), input.get("country"), input.get("gender"));
		
	    ProductCatalogue product = new ProductCatalogue(rDriver);
	    product.addMultipleItemToCart(3);
		
		product.shoppingCartBtn.click();
		CartPage cart = new CartPage(rDriver);
		
		UtilsFunction utils = new UtilsFunction(rDriver);
		utils.explicitWaitByAttribute(rDriver, cart.toolBar, 5);
		
	    cart.confirmCheckOut();
		String termsTitle = cart.alertTitle.getText();
		Assert.assertEquals(termsTitle, "Terms Of Conditions");
		cart.closeButton.click();
		

	}
}
