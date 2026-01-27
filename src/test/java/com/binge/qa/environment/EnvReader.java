package com.binge.qa.environment;

public class EnvReader {
    public static String getRMN() {
       return System.getenv("OTT_RMN").split(",")[1];
    }

    public static String getOTP() {
        return System.getenv("OTT_OTP").split(",")[1];
    }


    public static Object[][] getAllCredentials() {
        String[] rmns = System.getenv("OTT_RMN").split(",");
        String[] otps = System.getenv("OTT_OTP").split(",");

        Object[][] data = new Object[rmns.length][2];
        for (int i = 0; i < rmns.length; i++) {
            data[i][0] = rmns[i];
            data[i][1] = otps[i];
        }
        return data;
    }
}
