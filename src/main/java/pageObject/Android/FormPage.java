package pageObject.Android;

import org.openqa.selenium.WebElement;

import org.openqa.selenium.support.PageFactory;

import ecommerceUtils.AndroidGestures;
import io.appium.java_client.AppiumDriver;
import io.appium.java_client.HidesKeyboard;

import io.appium.java_client.pagefactory.AndroidFindBy;
import io.appium.java_client.pagefactory.AppiumFieldDecorator;


public class FormPage {

	public AppiumDriver driver;
	
	public FormPage(AppiumDriver driver)
	{
		this.driver = driver;
		//PageFactory.initElements(driver, this);
		PageFactory.initElements(new AppiumFieldDecorator(driver), this);
	}
	
	@AndroidFindBy(id="com.androidsample.generalstore:id/spinnerCountry")
	WebElement countryDropDown; 
	
	@AndroidFindBy(id="com.androidsample.generalstore:id/nameField")
	WebElement nameTxt;
	
	@AndroidFindBy(id="com.androidsample.generalstore:id/radioFemale")
	WebElement genderFemaleRadioBtn;
	
	@AndroidFindBy(id="com.androidsample.generalstore:id/radioMale")
	WebElement genderMaleRadioBtn;
	
	@AndroidFindBy(id="com.androidsample.generalstore:id/btnLetsShop")
	WebElement submitBtn;
	
	@AndroidFindBy(xpath="//android.widget.Toast[1]")
    public WebElement toastMessage;
	
	public void chooseCountry(String country)
	{
		countryDropDown.click();
		AndroidGestures gesture = new AndroidGestures(driver);
		gesture.scrollByElement(country);	
	}

	public void enterNameField(String textToType)
	{
		nameTxt.sendKeys(textToType);
	}
	
	public void genderSelection(String gender)
	{
		if(gender.equalsIgnoreCase("male"))
		{
			genderMaleRadioBtn.click();
		}else
		{
			genderFemaleRadioBtn.click();
		}
	}
	
	public void submitForm()
	{
		submitBtn.click();
	}
	
	public void completeFormSubmission(String name, String country, String gender)
	{
		chooseCountry(country);
		enterNameField(name);
	    ((HidesKeyboard) driver).hideKeyboard();
	    genderSelection(gender);
	    submitForm();
	}
	
	
	public String getToastMessage()
	{
		String toastMsg = toastMessage.getAttribute("text");
		return toastMsg;
	}
}
