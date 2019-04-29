package com.cognizant.cognizantits.engine.cli;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import org.apache.tools.ant.taskdefs.Sleep;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;

public class WebServicePost {
	public static String line = null;

	public static void main(String[] args) {
		

		String ceritificateAccept="overridelink";
		String CorpCustAddBtn="CorpCustAdd";
		String XmlHttpRouteBtn="//td[text()='XML/HTTP Route']/input";
		String submitBtn="//input[@type='submit']";
		String webServiceMessageBox="//textarea[@name='ipXml']";


		 /*System.setProperty("webdriver.ie.driver", "H://cognizant-intelligent-test-scripter-1.2//lib//Drivers//IEDriverServer.exe");
         //Initialize InternetExplorerDriver Instance.
         WebDriver driver = new InternetExplorerDriver();*/


		System.setProperty("webdriver.chrome.driver", "H://cognizant-intelligent-test-scripter-1.2//lib//Drivers//chromedriver.exe");
		//Initialize InternetExplorerDriver Instance.
		WebDriver driver = new ChromeDriver();

		driver.get("https://tst-wbcore-web.ic.ing.net:8443/fiuicif/services/index_CIF.html");
		driver.manage().window().maximize();
		System.out.println("Url launched");
		//clickByID(driver, ceritificateAccept);
		clickByLinkText(driver, CorpCustAddBtn);
		clickByXpath(driver, XmlHttpRouteBtn);
		clickByXpath(driver, submitBtn);

		String reqMessage=messagePost();
		sendRequestMessage(driver, webServiceMessageBox,reqMessage);



	}



	public static  void clickByID(WebDriver webdriver,String webElement)
	{
		webdriver.findElement(By.id(webElement)).click();
	}

	public static  void clickByLinkText(WebDriver webdriver,String webElement)
	{
		webdriver.findElement(By.partialLinkText(webElement)).click();
	}

	public static  void clickByXpath(WebDriver webdriver,String webElement)
	{
		webdriver.findElement(By.xpath(webElement)).click();
	}





	public static String messagePost()
	{
		StringBuilder requestMsg;
		FileReader fr;
		String fileName = "H://CIF_File//GRID.txt";

		Map<String,String> val=new HashMap<String,String>();
		val.put("$CIF_ID$", generateRandomNumber(4,"INGBANK",""));



		File file = new File(fileName);		
		try {
			fr = new FileReader(file);
			BufferedReader br = new BufferedReader(fr);		
			try {
				while((line = br.readLine()) != null){				
					for (Map.Entry<String,String> entry : val.entrySet()) 
					{					
						if(line.contains(entry.getKey()))					
						{
							line=line.replace(entry.getKey(),entry.getValue());

						}
					}	
					System.out.println(line);

				}
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}


		return line;		  


	}

	public static String generateRandomNumber(int length, String prefix,
			String suffix) {
		StringBuilder randomNumberStringBuilder = new StringBuilder("");
		for (int i = 0; i < length; i++)
		{
			//System.out.println("randomNumberStringBuilder::"+randomNumberStringBuilder);
			randomNumberStringBuilder.append(Long.toString(
					(long) (Math.random() * 1000)).substring(0, 1));
		}
		return prefix + randomNumberStringBuilder + suffix;
	}

	
	public static void sendRequestMessage(WebDriver driver, String webServiceMessageBox,String message) {

		Set<String> handles = driver.getWindowHandles();

		for (String handle : driver.getWindowHandles()) {

			driver.switchTo().window(handle);}
		System.out.println("window switched");

		WebElement reqMsg= driver.findElement(By.xpath(webServiceMessageBox));
		reqMsg.clear();
		System.out.println("message cleared");
		reqMsg.sendKeys(message);
		
		/*
		JavascriptExecutor js = (JavascriptExecutor) driver;
		js.executeScript(""
				+ "function getElementByXpath(path)"
				+ " {"
				+ "return document.evaluate(path, document, null, XPathResult.FIRST_ORDERED_NODE_TYPE, null).singleNodeValue;"
				+ "}"
				+ "document.getElementByXpath('//textarea[@name='ipXml']').value='test');");
		
		
		js.executeScript("document.getElementByXpath('some id').value='someValue';");*/
		
		
		System.out.println("task done");


	}








}
