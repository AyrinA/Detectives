package com.TestSuite;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.junit.Assert;
import org.junit.Test;
import org.junit.jupiter.api.Tag;

import java.util.HashSet;
import java.util.Set;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;


@Tag("Negative")
public class NegativeTests {

    private String getJsonResponse() {
        String jsonFile = "{\"detectives\":[" +
                "{\"MainId\":1,\"firstName\":\"Sherlock\",\"lastName\":\"Homes\",\"violinPlayer\":true,\"categories\":[" +
                "{\"CategoryID\":1,\"CategoryName\":\"extras1\",\"extra\":{\"extraArray\":[{\"violin\":1},{\"cap\":2}]}}]}," +
                "{\"MainId\":3,\"firstName\":\"James\",\"lastName\":\"Watson\",\"violinPlayer\":false,\"categories\":[" +
                "{\"CategoryID\":2,\"CategoryName\":\"extras2\",\"extra\":null}]}]," +
                "\"success\":true}";
        return jsonFile;
    }

    @Test
    public void negativeTestMainId() {
        String json = getJsonResponse();
        int MainIdToStart = 10;
        int MainIdToEnd = 20;

        JSONArray detectives = JsonUtils.getJSONArray(json, "detectives");
        for (int i = 0; i < detectives.length(); i++) {
            int mainId = JsonUtils.getInt(detectives.getJSONObject(i), "MainId");
            Assert.assertFalse(mainId >= MainIdToStart && mainId <= MainIdToEnd);
        }
    }

    @Test
    public void negativeTestDetectivesArray() {
        String json = getJsonResponse();
        int min = 3;
        int max = 10;
        int DetectivesNumber = JsonUtils.getArrayLength(json, "detectives");
        assertFalse(DetectivesNumber >= min && DetectivesNumber <= max);
    }

    @Test
    public void negativeTestCategoryID() {
        String json = getJsonResponse();

        try {
            JSONObject jsonObject = new JSONObject(json);
            JSONArray detectives = jsonObject.getJSONArray("detectives");

            for (int i = 0; i < detectives.length(); i++) {
                JSONObject detective = detectives.getJSONObject(i);
                JSONArray categories = detective.getJSONArray("categories");

                for (int j = 0; j < categories.length(); j++) {
                    JSONObject category = categories.getJSONObject(j);
                    int categoryID = category.getInt("CategoryID");
                    assertFalse(categoryID >= 3);
                }
            }
        } catch (JSONException exception) {
            exception.printStackTrace();
        }
    }

    @Test
    public void negativeTestExtraArray() {
        String json = getJsonResponse();

        JSONArray detectives = JsonUtils.getJSONArray(json, "detectives");
        for (int i = 0; i < detectives.length(); i++) {
            JSONArray categories = detectives.getJSONObject(i).getJSONArray("categories");
            for (int j = 0; j < categories.length(); j++) {
                int categoryId = JsonUtils.getInt(categories.getJSONObject(j), "CategoryID");
                if (categoryId == 1) {
                    JSONObject extra = categories.getJSONObject(j).optJSONObject("extra");
                    assertFalse(extra.length() < 1);
                }
            }
        }
    }

    @Test
    public void positiveTestSuccessField() {
        String json = getJsonResponse();
        JSONArray detectives = JsonUtils.getJSONArray(json, "detectives");

        Set<String> firstName = new HashSet<>();

        for (int i = 0; i < detectives.length(); i++) {
            String name = JsonUtils.getString(detectives.getJSONObject(i), "firstName");
            firstName.add(name);
        }

        boolean success = JsonUtils.getBooleanSuccess(json, "success");
        assertEquals(firstName.contains("Sherlock"), success);
    }

    @Test
    public void negativeTestSuccessField() {
        String json = getJsonResponse();
        JSONArray detectives = JsonUtils.getJSONArray(json, "detectives");

        Set<String> firstName = new HashSet<>();

        for (int i = 0; i < detectives.length(); i++) {
            String name = JsonUtils.getString(detectives.getJSONObject(i), "firstName");
            firstName.add(name);
        }

        boolean success = JsonUtils.getBooleanSuccess(json, "success");
        assertFalse(!firstName.contains("Sherlock") && success);
    }
}
