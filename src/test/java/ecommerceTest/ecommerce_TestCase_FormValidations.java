package ecommerceTest;

import java.net.MalformedURLException;
import java.util.HashMap;

import org.testng.Assert;

import org.testng.annotations.Test;
import pageObject.Android.FormPage;
import pageObject.Android.ProductCatalogue;

public class ecommerce_TestCase_FormValidations extends BaseTest {
	
	@Test(dataProvider="getData")
	public void loginSucess(HashMap<String,String> input) throws MalformedURLException
	{
	    
	    logger.info("**************Test Filling form success*******************");
		FormPage fillForm = new FormPage(rDriver);
		fillForm.completeFormSubmission(input.get("name"), input.get("country"), input.get("gender"));
		
	    ProductCatalogue product = new ProductCatalogue(rDriver);
	    Assert.assertEquals(product.toolBarTitle.getText(),"Products");
		
	}
	
	
	@Test(dataProvider="getData")
	public void loginFailure(HashMap<String,String> input) throws MalformedURLException
	{
	    logger.info("**************Test Filling form failure*******************");
		FormPage fillForm = new FormPage(rDriver);
		fillForm.completeFormSubmission(input.get("name"), input.get("country"), input.get("gender"));
		
		
		//Toast Message refer the screenshot under check testing folder once
		String toast_msg = fillForm.getToastMessage();
		
		Assert.assertEquals(toast_msg, "Please enter your name");
		
	}
	
	

}
