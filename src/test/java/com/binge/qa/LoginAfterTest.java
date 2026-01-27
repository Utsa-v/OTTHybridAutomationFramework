package com.binge.qa;

import com.binge.qa.base.BaseTest;
import com.binge.qa.environment.EnvReader;
import com.binge.qa.pages.AfterLoginPage;
import com.binge.qa.pages.SearchPage;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;


public class LoginAfterTest extends BaseTest {
    AfterLoginPage ap;
    SearchPage sp;

    @BeforeMethod
    public void webAppLaunch() {
        ap = new AfterLoginPage(driver);
        sp = new SearchPage(driver);
        fixedRMNLoginSetup(EnvReader.getRMN(),EnvReader.getOTP());
        accountSectionVisibility();
    }

    @AfterMethod
    public void tearDown() throws InterruptedException {
        logout();
        driver.quit();

    }

    @Test
    public void profileSetting() {
        ap.clickProfile();
        ap.settingClick();
        Assert.assertEquals(ap.setting(), "Settings");
    }

    @Test
    public void profileName()  {
        ap.updateProfileName();
        Assert.assertTrue(ap.profileUpdateToast());
    }

    @Test
    public void addAndRemoveToBingeList() throws InterruptedException {
        Thread.sleep(7000);
        sp.searchIconClick();
        sp.searchBarKeys();
        sp.searchResult();
        ap.removeBingeList();
        Assert.assertTrue(ap.bingeLisToast());
    }

    @Test
    public void playback() throws InterruptedException {
        Thread.sleep(7000);
        sp.searchIconClick();
        sp.searchBarDiffKeys();
        sp.searchResultSingle();
        ap.playCTAClick();

        System.out.println("Watch time before seek: "+ ap.beforeWatchTime());
        ap.forwardClick();
        System.out.println("Watch time after seek: "+ap.afterWatchTime());

        Assert.assertNotEquals(ap.beforeWatchTime(), ap.afterWatchTime());
        driver.navigate().back();
    }
}
