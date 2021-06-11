package commonLibs;

import org.openqa.selenium.WebElement;

public class ElementControl {
    public static void inputText(WebElement element, String value) {
        element.clear();
        element.sendKeys(value);
    }
}
