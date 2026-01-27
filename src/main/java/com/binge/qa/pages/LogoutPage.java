package com.binge.qa.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import java.util.List;

public class LogoutPage extends BasePage {

    public LogoutPage(WebDriver driver){
        super(driver);
    }

    @FindBy(xpath = "//h3[text()='Settings']")
    private List<WebElement> settingPage;

    @FindBy(css=".account-dropdown")
    private WebElement profileMenu;

    @FindBy(xpath = "//div[text()='Settings']")
    private WebElement settingButton;

    @FindBy(xpath = "//p[text()='Logout']")
    private WebElement logoutCta;

    @FindBy(css=".modal-body")
    private WebElement logoutDrawer;

    private By logoutClickLocator = By.xpath("//button[text()='Log Out']");

    public void logOutClick() throws InterruptedException {
        Thread.sleep(5000);
        if(settingPage.isEmpty()){
            waitForVisibility(profileMenu);
            actionClick(profileMenu);

            waitForVisibility(settingButton);
            jsClick(settingButton);
        }



        waitForClickability(logoutCta);
        jsClick(logoutCta);

        waitForVisibility(logoutDrawer);

        WebElement logoutClick = logoutDrawer.findElement(logoutClickLocator);
        jsClick(logoutClick);


    }

}
