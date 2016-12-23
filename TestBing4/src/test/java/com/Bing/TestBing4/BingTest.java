package com.Bing.TestBing4;

import org.testng.annotations.Test;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Parameters;
import org.testng.annotations.BeforeTest;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.events.EventFiringWebDriver;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.Reporter;
import org.testng.annotations.AfterTest;

public class BingTest {
	EventFiringWebDriver EventDriver;
	Logirovanie Logs;
	int Img;

	@Test()
	public void Test1() {

		EventDriver.get("http://www.bing.com/");
		EventDriver.findElement(By.xpath("//a[@id='scpl1']")).click();
		Logs.RecordsLogs("Проверяем заголовок у страницы");

		boolean Title = (new WebDriverWait(EventDriver, 10))
				.until(ExpectedConditions.titleContains("Лента изображений Bing"));
		Logs.RecordsLogs("Заголовок на странице " + Title);

		JavascriptExecutor Js = (JavascriptExecutor) EventDriver;
		List<WebElement> ListImg = new ArrayList<WebElement>();

		for (int i = 0; i < 2; i++) {
			int BeforeSizeList = ListImg.size();
			Js.executeScript("window.scrollTo(0, document.body.scrollHeight)");

			ListImg.addAll(new WebDriverWait(EventDriver, 10).until(ExpectedConditions.visibilityOfAllElements(
					EventDriver.findElements(By.xpath("//div[@id='recContainer']//li[@data-idx]")))));

			int AfterSizeList = ListImg.size() - BeforeSizeList;
			Assert.assertTrue(AfterSizeList > BeforeSizeList, "Изображения не подгружаются");
			Logs.RecordsLogs("Подгруженные изображения " + AfterSizeList);
		}
	}

	@Test(dataProvider = "Select", dependsOnMethods = "Test1")

	public void Test2(String Select) {
		System.out.println(Select + "\n");
		WebElement FieldSelect = (new WebDriverWait(EventDriver, 10))
				.until(ExpectedConditions.visibilityOf(EventDriver.findElement(By.xpath("//input[@id='sb_form_q']"))));
		FieldSelect.clear();
		FieldSelect.sendKeys(Select);
		(new WebDriverWait(EventDriver, 10)).until(
				ExpectedConditions.elementToBeClickable(EventDriver.findElement(By.xpath("//input[@id='sb_form_go']"))))
				.click();
		List<WebElement> ImgPass = ((new WebDriverWait(EventDriver, 10))
				.until(ExpectedConditions.presenceOfAllElementsLocatedBy(By.xpath("//div[@class='dg_u']"))));
		Logs.RecordsLogs("Количество изображений по запросу " + Select + ": " + ImgPass.size());

		WebElement FirstElem = ((new WebDriverWait(EventDriver, 10)).until(ExpectedConditions
				.visibilityOf(EventDriver.findElement(By.xpath("//div[@test-fb='true']/div/div[1]")))));

		Actions Action = new Actions(EventDriver);
		Action.moveToElement(FirstElem).build().perform();
		Logs.RecordsLogs("Курсор на первое изображение передвинут");

		WebElement ImgBig = (new WebDriverWait(EventDriver, 10)).until(
				ExpectedConditions.elementToBeClickable(By.xpath("//div[@class='irhc']//img[@role='presentation']")));
		Assert.assertTrue(ImgBig.isDisplayed(), "Большое изображение не отображается");
		Logs.RecordsLogs("Увеличенное изображение отображается");

		WebElement ImgBigSea = (new WebDriverWait(EventDriver, 10))
				.until(ExpectedConditions.elementToBeClickable(By.xpath("//img[@class='ovrf ovrfIconMS']")));
		ImgBigSea.click();

		WebElement AddCol = (new WebDriverWait(EventDriver, 10)).until(ExpectedConditions.elementToBeClickable(
				By.xpath("//span[@class='irhcsb']/span[@class='favC']")));
		Assert.assertTrue(AddCol.isDisplayed(), "Кнопка не отображается");
		Logs.RecordsLogs("Отображение кнопки Добавить в коллекцию " + AddCol.isDisplayed());

		WebElement Mess = (new WebDriverWait(EventDriver, 10))
				.until(ExpectedConditions.elementToBeClickable(By.xpath("//img[@class='ovrf ovrfIconFA']")));
		Assert.assertTrue(Mess.isDisplayed(), "Кнопка не отображается");
		Logs.RecordsLogs("Отображение кнопки Сообщить о нарушении " + Mess.isDisplayed());

		WebElement ImgSelect = (new WebDriverWait(EventDriver, 15)).until(
				ExpectedConditions.presenceOfElementLocated(By.xpath("//img[@class='mainImage accessible nofocus']")));
		Logs.RecordsLogs("Отображение изображения в режиме слайд шоу" + ImgSelect.isDisplayed());

		List<WebElement> ImgVisib = (new WebDriverWait(EventDriver, 15))
				.until(ExpectedConditions.visibilityOfAllElements(EventDriver
						.findElements(By.xpath("//ul[@id='mmComponent_images_4_1_1_list']/li[@data-row='0']"))));
		Logs.RecordsLogs("Количество видимых изображений: " + ImgVisib.size());

		WebElement ImgS = (new WebDriverWait(EventDriver, 15))
				.until(ExpectedConditions.elementToBeClickable(By.xpath("//div[@id='mmComponent_images_4_1_1_exp']")));
		ImgS.click();
		List<WebElement> ImgAll = (new WebDriverWait(EventDriver, 15)).until(ExpectedConditions.visibilityOfAllElements(
				EventDriver.findElements(By.xpath("//ul[@id='mmComponent_images_4_1_1_list']/li[@data-row]"))));
		int Size = ImgAll.size() - ImgVisib.size();
		Logs.RecordsLogs("Количество подгруженных изображений: " + Size);
		Logs.RecordsLogs("Сравнение количества подгруженных изображений с минимумом подгрузки");
		Assert.assertTrue(Size > Img, "Подгрузилось изображений меньше минимума");
		Logs.RecordsLogs("Подгрузилось изображений больше минимума");
		EventDriver.navigate().back();

	}

	@Test()
	@Parameters({ "Img" })
	public void Test3(String ImgStr) {
		Img = Integer.parseInt(ImgStr);
	}

	@DataProvider
	public Object[][] Select() {
		List<String> StrList = new ArrayList<String>();
		Scanner In = null;
		try {
			In = new Scanner(new File("select" + File.separator + "select.txt"));
			while (In.hasNext())
				StrList.add(In.nextLine());

		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}

		Object[][] Str = new String[StrList.size()][1];

		for (int i = 0; i < StrList.size(); i++) {
			for (int j = 0; j < 1; j++) {
				Str[i][j] = StrList.get(i);
			}
		}
		return Str;
	}

	@BeforeTest
	public void beforeTest() {
		System.setProperty("webdriver.gecko.driver", "driver" + File.separator + "geckodriver.exe");
		Reporter.setEscapeHtml(false);
		EventDriver = new EventFiringWebDriver(new FirefoxDriver());
		EventDriver.manage().window().maximize();
		Logs = new Logirovanie();
		EventDriver.register(Logs);
	}

	@AfterTest
	public void afterTest() {
		EventDriver.quit(); 
	}

}
