package com.tiggs.core;

import org.json.JSONArray;
import org.json.JSONObject;

import java.sql.Timestamp;
import java.util.ArrayList;

import static com.tiggs.core.PropertyManager.PropertyMap;
import static com.tiggs.core.PropertyManager.CoreLogger;

public class JsonModifier {

    public String addTimestampToJson(String json) {
        JSONObject jsonObject = new JSONObject(json);
        Timestamp timestamp = new Timestamp(System.currentTimeMillis());
        jsonObject.put("timestamp", timestamp.toString());
        CoreLogger.info("Added timestamp to response");
        return jsonObject.toString();
    }

    public String addKeyAndValueToJson(String json, String key, String value) {
        JSONObject jsonObject = new JSONObject(json);
        jsonObject.put(key, value);
        CoreLogger.info("Added key: \"" + key + "\" and value: \"" + value + "\" to response");
        return jsonObject.toString();
    }

    public ArrayList<String> extractJsonArrayValues(String json) {
        ArrayList jsonArrayValueList = new ArrayList<String>();
        JSONObject jsonObject = new JSONObject(json);
        JSONObject desiredJsonObject = null;
        String rootObjectKey = "hits";
        String firstArrayKey = "hits";
        String objectInArrayKey = "_source";
        String desiredValueKey = "source";
        if (PropertyMap.get("ElsDefaultResponseStructure").toString().equals("false")) {
            rootObjectKey = PropertyMap.get("ElsRootObjectKey").toString();
            firstArrayKey = PropertyMap.get("ElsFirstArrayKey").toString();
            objectInArrayKey = PropertyMap.get("ElsObjectInArrayKey").toString();
            desiredValueKey = PropertyMap.get("ElsDesiredValueKey").toString();
        }
        try {
            CoreLogger.info("Extracting values for summary file");
            JSONObject rootObject = jsonObject.getJSONObject(rootObjectKey);   //hits
            JSONArray firstArray = rootObject.getJSONArray(firstArrayKey); // hits
            for (int i = 0, size = firstArray.length(); i < size; i++) {
                JSONObject objectInArray = firstArray.getJSONObject(i);
                desiredJsonObject = new JSONObject(objectInArray.get(objectInArrayKey).toString()); //_source
                if (!(jsonArrayValueList.contains(desiredJsonObject.getString(desiredValueKey)))) {
                    jsonArrayValueList.add(desiredJsonObject.getString(desiredValueKey)); //source
                }
            }
            CoreLogger.info(jsonArrayValueList.size() + " values extracted for summary file");
        } catch (Exception ex) {
            CoreLogger.error("Extracting values failed" + ex.getMessage());
        }
        return jsonArrayValueList;
    }
}


