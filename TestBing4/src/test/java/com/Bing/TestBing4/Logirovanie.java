package com.Bing.TestBing4;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.events.AbstractWebDriverEventListener;
import org.openqa.selenium.support.events.WebDriverEventListener;
import org.testng.Reporter;

public class Logirovanie extends AbstractWebDriverEventListener{
	public void RecordsLogs(String Message) {
		Reporter.log(Message + "<br>");
	}

	@Override
	public void afterChangeValueOf(WebElement arg0, WebDriver arg1, CharSequence[] arg2) {
		if (arg2.length == 0)
			RecordsLogs("Был очищен данный элемент " + arg0.getTagName());
		else
			RecordsLogs("В данный элемент  " + arg0.getTagName() + " был введен следующий запрос " + arg2.toString());

	}

	@Override
	public void afterClickOn(WebElement arg0, WebDriver arg1) {
		RecordsLogs("Клик по элементу прошол успешно " + arg0);

	}

	@Override
	public void afterFindBy(By arg0, WebElement arg1, WebDriver arg2) {
		RecordsLogs("Поиск элемента выполнен успешно " + arg0);

	}

	@Override
	public void afterNavigateBack(WebDriver arg0) {
		RecordsLogs("Произошел переход назад");

	}

	@Override
	public void afterNavigateTo(String arg0, WebDriver arg1) {
		RecordsLogs("После перехода на страницу сайта " + arg0 + " заголовок страницы имеет следующие название "
				+ arg1.getTitle());

	}

	@Override
	public void afterScript(String arg0, WebDriver arg1) {
		RecordsLogs("Был выполнен успешно следующий скрипт " + arg0);

	}

	@Override
	public void beforeChangeValueOf(WebElement arg0, WebDriver arg1, CharSequence[] arg2) {
		if (arg2.length == 0)
			RecordsLogs("Следующий элемент будет очищен " + arg0.getTagName());
		else
			RecordsLogs("В данный элемент  " + arg0.getTagName() + " будет введен следующий запрос " + arg2.toString());

	}

	@Override
	public void beforeClickOn(WebElement arg0, WebDriver arg1) {
		RecordsLogs("На следующий элемент будет произведен клик " + arg0.toString());
	}

	@Override
	public void beforeFindBy(By arg0, WebElement arg1, WebDriver arg2) {
		RecordsLogs("Ищем следующий элемент " + arg0.toString());
	}

	@Override
	public void beforeNavigateBack(WebDriver arg0) {
		RecordsLogs("Перед переходом на предыдущую страницу");
	}

	@Override
	public void beforeNavigateTo(String arg0, WebDriver arg1) {
		RecordsLogs("Перед переходом на страницу с данным адресом " + arg0);

	}

	@Override
	public void beforeScript(String arg0, WebDriver arg1) {
		RecordsLogs("Будет выполнен следующий скрипт " + arg0);

	}
}
