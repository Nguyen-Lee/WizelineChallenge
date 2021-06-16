package commonLibs;

import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;

public class ElementControl {
    public static void inputText(WebElement element, String value) {
        while(!element.getAttribute("value").equals("")){
            element.sendKeys(Keys.BACK_SPACE);
        }
        if (!value.isEmpty()) {
            element.sendKeys(value);
        }
    }
}
