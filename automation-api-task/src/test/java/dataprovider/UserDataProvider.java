package dataprovider;

import org.testng.annotations.DataProvider;

public class UserDataProvider {

    // DataProvider method to provide user data
    @DataProvider(name = "userData")
    public Object[][] userData() {
        return new Object[][] {
                {10, "mohameddhateem", "mohamed", "hatem", "hatem@email.com", "12345", "12345", 1}

        };
    }
}



