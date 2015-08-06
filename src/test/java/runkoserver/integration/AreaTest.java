/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package runkoserver.integration;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.boot.test.WebIntegrationTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import runkoserver.Application;
import runkoserver.domain.Area;
import runkoserver.domain.Content;
import static runkoserver.libraries.Attributes.*;
import static runkoserver.libraries.Links.*;
import static runkoserver.libraries.Messages.*;
import runkoserver.service.ContentAreaService;

/**
 * Integration tests for area-usage.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@WebIntegrationTest(value = "server.port=9000")
@SeleniumTest(baseUrl = "http://localhost:9000")

public class AreaTest {
    
    @Autowired
    private WebDriver driver;
    
    @Autowired
    private ContentAreaService contentAreaService;
    
    @Before
    public void userIsLoggedIn() {
        driver.get(LINK_LOCALHOST + LINK_LOGIN);

        WebElement username = driver.findElement(By.name(ATTRIBUTE_USERNAME));
        WebElement password = driver.findElement(By.name(ATTRIBUTE_PASSWORD));

        username.sendKeys(LOGIN_TEST);
        password.sendKeys(PASSWORD_TEST);
        password.submit();
    }
    
    private Area createNewArea(String areaName) {
        driver.get(LINK_LOCALHOST + LINK_AREA_INDEX + LINK_AREA_FORM);
        
        WebElement name = driver.findElement(By.name(ATTRIBUTE_NAME));
        
        String theName = areaName;
        name.sendKeys(theName);
        
        name.submit();
        
        return contentAreaService.findAreaByName(theName);
    }
    
    private Content createNewSimpleContent(String contentName, String tArea, String area) {
        driver.get(LINK_LOCALHOST + LINK_CONTENT + LINK_CONTENT_SIMPLEFORM);
        
        WebElement name = driver.findElement(By.name(ATTRIBUTE_NAME));
        WebElement textArea = driver.findElement(By.name(ATTRIBUTE_TEXTAREA));
        
        int areaId = area.getId();
        
        String theName = contentName;
        name.sendKeys(theName);
        String text = tArea;
        textArea.sendKeys(text);
        String areaName = area;
        
        textArea.submit();
        
        return contentAreaService.findContentByName(theName);
    }
    
    @Test
    public void areaCanBeCreatedWithValidInformation() {
        String areaName = "Orange is new black!";
        
        createNewArea(areaName);
    }
    
    @Test
    public void areaCannotBeCreatedWithInvalidInformation() {
        String areaName = "av";
        
        createNewArea(areaName);
    }
    
    @Test
    public void createdAreaCanBeFound() {
        String areaName = "Elämä on!";        
        
        Area area = createNewArea(areaName);
        
        assertTrue(area != null);
    }
    
    @Test
    public void createdAreaContainsAllGivenInformation() {
        String areaName = "Tämä on testi.";
        
        Area area = createNewArea(areaName);
        
        driver.get(LINK_LOCALHOST + LINK_AREA_INDEX + "/" + area.getId());
        
        assertTrue(driver.getPageSource().contains(areaName));
    }
    
    @Test
    public void contentCanBeCreatedInNewArea(){
        String areaName = "Uusi alue";
        String contentName = "Uusi sisältö";
        String contentText = "Lisätään sisältöä.";
        
        Area area = createNewArea(areaName);
        Content content = createNewSimpleContent(contentName, contentText, areaName);
        
        
        
    }
}
