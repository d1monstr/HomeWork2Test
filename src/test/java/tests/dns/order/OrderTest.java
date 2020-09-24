package ru.appline.tests.sberbank.order;

import org.junit.*;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;
import ru.appline.tests.sberbank.base.BaseTests;

public class OrderTest extends BaseTests {

    @Test
    public void test(){

        // выбрать меню "Карты"
        String cardsMenuXPath = "//label[text() = 'Карты']";
        WebElement cardsMenuButton = driver.findElement(By.xpath(cardsMenuXPath));
        cardsMenuButton.click();

        // выбрать пункт подменю - "Дебетовые карты"
        String debetCardXPath = "//li/a[text() = 'Дебетовые карты']";
        WebElement debetCardButton = driver.findElement(By.xpath(debetCardXPath));
        debetCardButton.click();

        // проверка открытия страницы "Дебетовые карты"
        String pageTitleXPath = "//h2[contains(@class, 'kit-heading')]";
        WebElement pageTitle = driver.findElement(By.xpath(pageTitleXPath));
        waitUtilElementToBeVisible(pageTitle);
        Assert.assertEquals("Заголовок отсутствует/не соответствует требуемому",
                "Дебетовые карты", pageTitle.getText());

        // нажать кнопку "Молодежная"
        String youthCardXPath = "//label[contains(text(), 'Молодежная')]";
        WebElement youthCardButton = driver.findElement(By.xpath(youthCardXPath));
        waitUtilElementToBeClickable(youthCardButton);
        youthCardButton.click();

        // нажать кнопку "Заказать онлайн" у карты "Молодежная"
        String orderXPath = "//h3[contains(text(), 'Молодежная карта')]/preceding::b[contains(text(), 'Заказать онлайн')]";
        WebElement orderButton = driver.findElement(By.xpath(orderXPath));
        waitUtilElementToBeClickable(orderButton);
        orderButton.click();

        // проверка открытия страницы "Молодежная карта"
        String pageTitle1XPath = "//h1[contains(@class, 'kitt-heading')]";
        WebElement pageTitle1 = driver.findElement(By.xpath(pageTitle1XPath));
        waitUtilElementToBeVisible(pageTitle1);
        Assert.assertEquals("Заголовок отсутствует/не соответствует требуемому",
                "Молодёжная карта", pageTitle1.getText());

        // закрываем куки
        WebElement closeCookies = driver.findElement(By.xpath("//button[text()='Закрыть']"));
        closeCookies.click();

        // заполнить поля данными
        fillInputField(driver.findElement(By.xpath("//input[@data-name = 'lastName']")), "Иванов");
        fillInputField(driver.findElement(By.xpath("//input[@data-name = 'firstName']")), "Иван");
        fillInputField(driver.findElement(By.xpath("//input[@data-name = 'middleName']")), "Иванович");
        fillInputField(driver.findElement(By.xpath("//input[@data-name = 'cardName']")), "IVAN IVANOV");
        fillInputField(driver.findElement(By.xpath("//input[@data-name = 'birthDate']")), "15.06.1996");
        fillInputField(driver.findElement(By.xpath("//input[@data-name = 'email']")), "qwerty@mail.ru");

        WebElement phoneField = driver.findElement(By.xpath("//input[@data-name = 'phone']"));
        scrollToElementJs(phoneField);
        waitUtilElementToBeClickable(phoneField);
        try {
            Thread.sleep(500);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        phoneField.click();
        phoneField.sendKeys("9999999999");
        Assert.assertEquals("Поле было заполнено некорректно",
                "+7 (999) 999-99-99", phoneField.getAttribute("value"));

        // нажать кнопку "Далее"
        String furtherXPath = "//span[contains(text(), 'Далее')]/..";
        WebElement furtherButton = driver.findElement(By.xpath(furtherXPath));
        scrollToElementJs(furtherButton);
        waitUtilElementToBeClickable(furtherButton);
        JavascriptExecutor executor = (JavascriptExecutor)driver;
        executor.executeScript("arguments[0].click();", furtherButton);

        // проверка написей "Обязательное поле" у незаполненных полей
        checkErrorMessageAtField(driver.findElement(By.xpath("//input[@data-name = 'series']")));
        checkErrorMessageAtField(driver.findElement(By.xpath("//input[@data-name = 'number']")));

        WebElement issueDateError = driver.findElement(By.xpath("//input[@data-name = 'issueDate']/../../..//div[@class = 'odcui-error__text']"));
        Assert.assertEquals("Проверка ошибки у поля не была пройдена",
                "Обязательное поле", issueDateError.getText());

        checkErrorMessageAtField(driver.findElement(By.xpath("//div[text() = 'Я соглашаюсь на']/button")));
    }
}
