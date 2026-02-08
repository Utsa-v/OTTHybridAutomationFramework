package com.binge.qa.pages;

import org.openqa.selenium.*;
import org.openqa.selenium.support.FindBy;

import java.util.List;


import static com.binge.qa.utils.Utility.UtilityQA.getRandomString;

public class AfterLoginPage extends BasePage {
    public AfterLoginPage(WebDriver driver){
        super(driver);
    }


    @FindBy (css=".logged-in-user-img")
    private WebElement accountSetting;

    @FindBy (xpath = "//div[text()='Settings']")
    private WebElement settingElement;

    @FindBy (xpath = "//h3[text()=\"Settings\"]")
    private WebElement settingPage;

    @FindBy (xpath = "//input[@placeholder='Enter Name Here']")
    private WebElement nameInput;

    @FindBy (xpath = "//span[text()='Save Changes']")
    private WebElement saveCTA;

    @FindBy(xpath = "//div[contains(@class,'Toastify__toast-body')]//div[contains(text(),'Binge List')]")
    private WebElement toastBingeListElement;

    @FindBy(css=".play-btn")
    private WebElement playCTAElement;

    @FindBy(css=".endTime")
    private WebElement endTimeElement;

    @FindBy(xpath = "//span[text()='My Binge List']/preceding-sibling::i")
    private WebElement removeIcons;

    @FindBy(xpath = "//span[text()='My Binge List']/preceding-sibling::img")
    private WebElement addBingeListElement;

    @FindBy (xpath = "//p[text()=\"Manage Devices\"]")
    private WebElement manageDeviceSection;

    @FindBy(css=".select-content")
    private List<WebElement> manageDevicesList;

    @FindBy(css=".active")
    private WebElement manageDeviceAfterClick;

    @FindBy(xpath = "//span[text()=\"Remove Device\"]")
    private WebElement  removeDevice;

    private By forwardCTALocator = By.cssSelector(".icon-forward");

    private By profileUpdateToast = By.xpath("//div[contains(@class,'Toastify__toast-body')]/div[contains(text(),'Subscriber Details Updated')]");

    //private By addBingeListLocator = By.xpath("//span[text()='My Binge List']/preceding-sibling::img");

    private By bingeListToastLocator = By.xpath("//div[contains(@class,'Toastify__toast-body')]//div[contains(text(),'Binge List')]");

    public void clickProfile(){
        //waitForVisibility(accountSetting);
        //action.moveToElement(accountSetting).click().perform();
        waitForClickability(accountSetting);
        jsClick(accountSetting);
    }

    public void settingClick(){
        waitForVisibility(settingElement);
        jsClick(settingElement);
    }

    public String setting(){
        waitForVisibility(settingPage);
        String settingText = settingPage.getText().trim();
        return settingText;
    }

    public void nameInputClick(){

        String randomName = "Test_" + getRandomString(5);
        waitForClickability(nameInput);
        jsClick(nameInput);
        nameInput.clear();
        nameInput.sendKeys(randomName);
    }

    public void saveCTAClick(){
        waitForClickability(saveCTA);
        jsClick(saveCTA);
    }


    public void updateProfileName(){
        clickProfile();
        settingClick();
        nameInputClick();
        saveCTAClick();
    }

    public List <WebElement> listOfDevices(){
        waitForVisibilityOfAll(manageDevicesList);
        return  manageDevicesList;
    }

    public int deviceCount(){
        return listOfDevices().size();
    }

    public int manageDevices(){
        clickProfile();
        settingClick();
        waitForClickability(manageDeviceSection);
        manageDeviceSection.click();

        if(listOfDevices().isEmpty() ){
          return 0;
        }
        waitForClickability(listOfDevices().getFirst());
        jsClick(listOfDevices().getFirst());
        waitForVisibility(manageDeviceAfterClick);
        waitForClickability(removeDevice);
        jsClick(removeDevice);

        return listOfDevices().size();
    }

    public boolean profileUpdateToast(){
        waitforVisibilityBy(profileUpdateToast );
        boolean toastMessage = driver.getPageSource().contains("Subscriber Details Updated Successfully.");
        return  toastMessage;
    }

    public void removeBingeList() {
        waitForVisibility(removeIcons);
        if (removeIcons.isDisplayed()) {
            jsClick(removeIcons);

        }
        waitForVisibility(addBingeListElement
        );
        jsClick(addBingeListElement);

    }


    public boolean bingeLisToast(){
        try {
            return fluentWait(d -> !d.findElements(bingeListToastLocator).isEmpty(),
                    5,
                    300 );
        }
        catch (TimeoutException e) {
            return false;
        }
    }



    public void playCTAClick(){
        waitForVisibility(playCTAElement);
        waitForClickability(playCTAElement);
        playCTAElement.click();
    }

    public String beforeWatchTime(){
        waitForVisibility(endTimeElement);
        String timeBefore = endTimeElement.getText();
        return timeBefore;
    }

    public void forwardClick() throws InterruptedException {
        Thread.sleep(1000);
        //Wait for the player control before click
         waitForPresence(forwardCTALocator);
         WebElement forward = waitForClickabilityBy(forwardCTALocator);
         forward.click();
    }

    public String afterWatchTime() throws InterruptedException {
        Thread.sleep(1000);
        waitForVisibility(endTimeElement);
        String timeAfter = endTimeElement.getText();
        return timeAfter;
    }


}
