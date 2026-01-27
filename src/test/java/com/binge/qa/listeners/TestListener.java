package com.binge.qa.listeners;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.binge.qa.base.BaseTest;
import com.binge.qa.reports.MyExtentReport;
import com.binge.qa.retry.RetryAnalyzer;
import com.binge.qa.utils.Utility.UtilityQA;
import org.openqa.selenium.WebDriver;
import org.testng.IAnnotationTransformer;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;
import org.testng.annotations.ITestAnnotation;

import java.lang.reflect.Constructor;
import java.lang.reflect.Method;


public class TestListener implements ITestListener, IAnnotationTransformer {

    // One ExtentReports instance for entire run
    private static ExtentReports extent =
            MyExtentReport.getExtentReport();

    // Thread-safe ExtentTest for parallel execution
    private static ThreadLocal<ExtentTest> test =
            new ThreadLocal<>();

    @Override
    public void onTestStart(ITestResult result) {
        ExtentTest extentTest =
                extent.createTest(result.getMethod().getMethodName());
        test.set(extentTest);
    }

    @Override
    public void onTestSuccess(ITestResult result) {
        test.get().pass("Test Passed");
    }

    @Override
    public void onTestFailure(ITestResult result) {

        test.get().fail(result.getThrowable());

        WebDriver driver = null;

        try {
            driver = ((BaseTest) result.getInstance()).getDriver();
        } catch (Exception e) {
        }

        if (driver != null) {
            String screenshotPath =
                    UtilityQA.getTheScreenShot(
                            driver,
                            result.getMethod().getMethodName()
                    );

            if (screenshotPath != null) {
                try {
                    test.get().addScreenCaptureFromPath(screenshotPath);
                } catch (Exception e) {
                }
            }
        }
    }

    @Override
    public void onTestSkipped(ITestResult result) {
        test.get().skip("Test Skipped");

        WebDriver driver =
                ((BaseTest) result.getInstance()).getDriver();
        String screenshotPath =
                UtilityQA.getTheScreenShot(
                        driver,
                        result.getMethod().getMethodName() + "_SKIPPED"
                );

        if (screenshotPath != null) {
            test.get().addScreenCaptureFromPath(screenshotPath);
        }
    }

    @Override
    public void onFinish(ITestContext context) {
        if (extent != null) {
            extent.flush();
        }
    }


    @Override
    public void transform(
            ITestAnnotation annotation,
            Class testClass,
            Constructor testConstructor,
            Method testMethod) {

        annotation.setRetryAnalyzer(RetryAnalyzer.class);
    }
}
