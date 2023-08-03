package ecommerceUtils;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;

public class ExtentReportNG {
	
	static ExtentReports extentReport;
	static ExtentSparkReporter sparkReporter;
	
	public static ExtentReports configExtentReport()
	{
		//Set the configurations for the report
		System.out.println("The directory is :"+System.getProperty("user.dir"));
		String reportPath = System.getProperty("user.dir")+"\\target\\index.html";
		sparkReporter = new ExtentSparkReporter(reportPath);
		sparkReporter.config().setDocumentTitle("Test result");
		sparkReporter.config().setReportName("Appium Android results");
		
		extentReport = new ExtentReports();
		extentReport.attachReporter(sparkReporter);
		extentReport.setSystemInfo("Test Engineer", "Sathya");
		return extentReport;
	}

}
