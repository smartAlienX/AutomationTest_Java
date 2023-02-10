package com.smart.bddtest.core.element;

import lombok.Data;
import org.openqa.selenium.By;

@Data
abstract public class ElementLocationInfo implements Cloneable {

    private String aosXPatch;

    private String iosXPatch;

    @Override
    public ElementLocationInfo clone() {
        try {
            return (ElementLocationInfo) super.clone();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

//    public By getLocationBy() {
//        return By.xpath();
//    }
}
