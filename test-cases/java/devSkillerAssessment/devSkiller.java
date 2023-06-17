package devSkillerAssessment;

import org.openqa.selenium.*;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.By;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.Select;

import java.util.List;

public class devSkiller {

    private final WebDriver driver;

    public devSkiller(WebDriver driver) {
        this.driver = driver;
    }

    public String extractHeader() {
        return driver.findElement(By.xpath("//div[@class='container']/h1")).getText();
    }

    public String clickTheButtonAndExtractLink() {
        driver.findElement(By.xpath("//div[@class='container']/button")).click();

        return driver.findElement(By.xpath("//a[last()]")).getAttribute("href");
    }

    public String extractParagraph() {

        WebElement element=driver.findElement(By.xpath("//p"));
        ((JavascriptExecutor)driver).executeScript("arguments[0].setAttribute('style','visibility:visible;');",element);
        return driver.findElement(By.xpath("//p")).getText();
    }

    public String[] getDropdownValues() {

        WebElement selectElement = driver.findElement(By.id("dropdown-main"));
        Select select = new Select(selectElement);

        List<WebElement> selectedOptions = select.getAllSelectedOptions();
        String[] selectedValues = new String[selectedOptions.size()];

        for (int i = 0; i < selectedOptions.size(); i++) {
            selectedValues[i] = selectedOptions.get(i).getText();
        }

        return selectedValues;

    }

    public void setInput(String input) {
        List<WebElement> textBoxes = driver.findElements(By.className("text-box"));

        for (WebElement textBox: textBoxes) {
            textBox.sendKeys(input);
        }
    }
}


// cssSelector("#dropdown-main option:checked") // # is for IDs and "." is for classes

/*
.ejemplo h1 (ejemplo para traer el h1 que está adentro de cualquier elemento con la clase ejemplo)
#pepe (trae el elemento que tiene el id pepe)
#pepe .ejemplo (trae el elemento que tenga la clase ejemplo dentro del elemento con id pepe)


//a[last()]
//a[first()]
//a/parent::div

//a/following-sibling::div

//div/a VS //div//a
//*/ //h1

// el //* te toma todos los elementos sin importar el tipo
        /*
Select
 */