package com.binge.qa.retry;

import org.testng.IRetryAnalyzer;
import org.testng.ITestResult;

public class RetryAnalyzer implements IRetryAnalyzer {

    private int retryCount = 0;
    private static final int MAX_RETRY = 1; // retry ONLY ONCE

    @Override
    public boolean retry(ITestResult result) {

        if (retryCount < MAX_RETRY) {
            retryCount++;
            return true;   // re-run test
        }
        return false;      // stop retrying
    }
}

