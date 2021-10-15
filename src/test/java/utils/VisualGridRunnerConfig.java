package utils;

import com.applitools.eyes.AccessibilityGuidelinesVersion;
import com.applitools.eyes.AccessibilityLevel;
import com.applitools.eyes.AccessibilitySettings;
import com.applitools.eyes.EyesRunner;
import com.applitools.eyes.selenium.BrowserType;
import com.applitools.eyes.selenium.ClassicRunner;
import com.applitools.eyes.selenium.Configuration;
import com.applitools.eyes.selenium.Eyes;
import com.applitools.eyes.selenium.StitchMode;
import com.applitools.eyes.visualgrid.model.DeviceName;
import com.applitools.eyes.visualgrid.model.ScreenOrientation;
import com.applitools.eyes.visualgrid.services.RunnerOptions;
import com.applitools.eyes.visualgrid.services.VisualGridRunner;

public class VisualGridRunnerConfig {

    public static Eyes getEyes(String appName) {
        //Applitools Eyes setup
        EyesRunner runner = new VisualGridRunner(new RunnerOptions().testConcurrency(10));
        Configuration config = getGrid(appName, "BookStore Tests");

        /* ... other configuration */;
        Eyes eyes = new Eyes(runner);

        eyes.setConfiguration(config);

        eyes.setApiKey("SBRUfnBHX106AFTU77H99Ffy7LxvpTmDaRVWMLjeZlIIZQ110");
        eyes.setForceFullPageScreenshot(true);
        eyes.setStitchMode(StitchMode.CSS);
        return eyes;
    }

    public static Configuration getGrid(String appName, String testName){
        Configuration vgConfig = new Configuration();
        vgConfig.setAppName(appName);
        vgConfig.setTestName(testName);

        vgConfig
                .setAccessibilityValidation(
                        new AccessibilitySettings(AccessibilityLevel.AAA,
                                AccessibilityGuidelinesVersion.WCAG_2_1));

        //Browsers
        vgConfig.addBrowser(800,  600, BrowserType.FIREFOX);
        vgConfig.addBrowser(800,  600, BrowserType.CHROME);
        vgConfig.addBrowser(1200, 800, BrowserType.FIREFOX);
        vgConfig.addBrowser(1200, 800, BrowserType.CHROME);
        vgConfig.addBrowser(1200, 800, BrowserType.IE_11);
        vgConfig.addBrowser(1200, 800, BrowserType.SAFARI);

        //Devices
        vgConfig.addDeviceEmulation(DeviceName.iPhone_X);
        vgConfig.addDeviceEmulation(DeviceName.iPhone_X, ScreenOrientation.LANDSCAPE);
        vgConfig.addDeviceEmulation(DeviceName.Galaxy_S5);
        vgConfig.addDeviceEmulation(DeviceName.Galaxy_S5, ScreenOrientation.LANDSCAPE);
        vgConfig.addDeviceEmulation(DeviceName.iPad_Mini, ScreenOrientation.LANDSCAPE);

        return vgConfig;
    }
}
