package com.tiggs.core;

import org.junit.jupiter.api.Test;
import java.util.HashMap;
import java.util.Map;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static com.tiggs.core.PropertyManager.PropertyMap;

public class PropertyManagerTest {

    public Map TestPropertyMap() {
        Map <String, String>propertyMap = new HashMap<String, String>();
        propertyMap.put("ElsUser","elastic");
        propertyMap.put("ElsPassword","DywG7Z9Xfp8jcT0HtvQV");
        propertyMap.put("ElsHost","localhost");
        propertyMap.put("ElsHostPort","9200");
        propertyMap.put("MongoURI","mongodb+srv");
        propertyMap.put("ElsIndex","ladoop");
        propertyMap.put("ElsFiledname","content");
        propertyMap.put("ElsFieldvalue","Zielsetzung");
        propertyMap.put("ElsSearchStartIndex","0");
        propertyMap.put("ElsSearchMaxResponseSize","10000");
        propertyMap.put("MongoDataBaseName","ElasticDB");
        propertyMap.put("MongoCollectionName","ElasticHadoopV4");
        propertyMap.put("ElsDefaultDocumentStructure","true");
        propertyMap.put("ElsRootObjectKey","null");
        propertyMap.put("ElsFirstArrayKey","null");
        propertyMap.put("ElsObjectInArrayKey","null");
        propertyMap.put("ElsDesiredValueKey","null");
        return propertyMap;
    }

    @Test
    public void loadPropertyFileTest_true() {
        PropertyManager propertyManager = new PropertyManager();
        propertyManager.loadPropertyFile("src/test/resources/ELCOMP.properties");
//        assertTrue(TestPropertyMap().equals(PropertyMap),"property file not loaded properly");
        assertEquals(true,TestPropertyMap().equals(PropertyMap));
    }

    @Test
    public void loadPropertyFileTest_false() {
        PropertyManager propertyManager = new PropertyManager();
        propertyManager.loadPropertyFile("src/test/resources/ELCOMP2.properties");
//        assertTrue(TestPropertyMap().equals(PropertyMap),"property file not loaded properly");
        assertEquals(false,TestPropertyMap().equals(PropertyMap));
    }
}
