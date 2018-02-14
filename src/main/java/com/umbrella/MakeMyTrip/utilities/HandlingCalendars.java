package com.umbrella.MakeMyTrip.utilities;

/*this class handles calendar for makemyTrip project
http://makeseleniumeasy.com/2017/09/18/how-to-handle-different-types-of-calendars-in-selenium-part-1/
	There are so many types of calendar but there are something common across all types. We must know those common points about calend
	Maximum calendars are a table whh consists of rows and tables. Generally week will be created with “tr” and dates will “td”.
	“Class” attribute for “td” tag gives more information about if date is past, current , weekend or upcoming. Refer below image, You will see the class name is different.
	Red marked column is past date.
	Yellow marked column is current date.
	Green marked column is week-end.
	Blue marked column is future date.
	Every td will have attributes which is “data-month” and “data-year” which gives information about month and year to which td belongs. For example: “data-month” as 2 means day belongs to March month.
	HANDLING MAKEMYTRIP’S CALENDAR:
	Logic:
	We will take Year, Month and day from user.
	First we will get year in first month(left hand side). If year is not matching with desired year, we will click on next till we get desired year. For example: If I need to select 2018, and current year is 2017, we will keep clicking of next button till we get year as 2018.
	Once we get desired year, we need to get desired month. We will retrieve month value from left most month and if month is not matching with desired month, we will keep clicking on Next button till we get desired month. For example: If current month is September and desired month is November. We will click on Next button till we get month November in left most month.
	The major step is finding valid dates of desired month. We should filter past dates and dates of other months. We should also filter empty date columns.
	Java has a number for each month of year which is equal to actual position of month-1. For example: Java month number for March will be 3-1=2.
	Using java month number, we can filter dates from other months.
*/

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class HandlingCalendars {

	public static void selectDate(WebElement calendar, String year, String monthName, String day, WebDriver driver)
			throws ParseException {
		// Clicking on calendar to open calendar widget
		calendar.click();

		// Retrieving current year value
		String currentYear = driver
				.findElement(By.xpath("//div[@class='ui-datepicker-title']/span[@class='ui-datepicker-year']"))
				.getText();

		// Click on Next arrow till we get desired year
		if (!currentYear.equals(year)) {
			do {
				driver.findElement(By.xpath("(//span[text()='Next'])[1]")).click();
			} while (!driver
					.findElement(By.xpath("//div[@class='ui-datepicker-title']/span[@class='ui-datepicker-year']"))
					.getText().equals(year));

		}

		// Select desired month after selecting desired year

		String currentMonth = driver
				.findElement(By.xpath("(//div[@class='ui-datepicker-title']/span[@class='ui-datepicker-month'])[1]"))
				.getText();
		if (!currentMonth.equalsIgnoreCase(monthName)) {
			do {
				driver.findElement(By.xpath("(//span[text()='Next'])[1]")).click();
			} while (!driver
					.findElement(
							By.xpath("(//div[@class='ui-datepicker-title']/span[@class='ui-datepicker-month'])[1]"))
					.getText().trim().equalsIgnoreCase(monthName));

		}
		// get java month number for desired month

		int javaMonthInt = HandlingCalendars.getMonthJavaInt(monthName);

		// Find dates of desired month only

		List<WebElement> allDateOfDesiredMonth = driver.findElements(By.xpath(
				"//div[@class='ui-datepicker-group ui-datepicker-group-first']//table/tbody[1]//td[(@class=' ' or @class=' ui-datepicker-week-end ' ) and @data-month='"
						+ javaMonthInt + "']"));
		for (WebElement d : allDateOfDesiredMonth) {
			if (d.getText().trim().equals(day)) {
				d.click();
				break;
			}
		}
	}

	// Code to get java month number
	public static int getMonthJavaInt(String monthName) throws ParseException {

		Date date = new SimpleDateFormat("MMMM").parse(monthName);
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		return cal.get(Calendar.MONTH);
	}

}
