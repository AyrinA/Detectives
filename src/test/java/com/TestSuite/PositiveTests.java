package com.TestSuite;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.junit.Assert;
import org.junit.Test;
import org.junit.jupiter.api.Tag;

import java.util.HashSet;
import java.util.Set;

import static org.junit.Assert.*;
import static org.junit.Assert.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;


    @Tag("Positive")
    public class PositiveTests {

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
        public void positiveTestMainId() {
            String json = getJsonResponse();
            int MainIdToStart = 0;
            int MainIdToEnd = 10;

            JSONArray detectives = JsonUtils.getJSONArray(json, "detectives");
            for (int i = 0; i < detectives.length(); i++) {
                int mainId = JsonUtils.getInt(detectives.getJSONObject(i), "MainId");
                Assert.assertTrue(mainId >= MainIdToStart && mainId <= MainIdToEnd);
            }
        }

        @Test
        public void positiveTestDetectivesArray() {
            String json = getJsonResponse();
            int min = 1;
            int max = 3;
            int DetectivesNumber = JsonUtils.getArrayLength(json, "detectives");
            assertTrue(DetectivesNumber >= min && DetectivesNumber <= max);
        }

        @Test
        public void positiveTestCategoryID() {
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
                        assertTrue(categoryID == 1 || categoryID == 2);
                    }
                }
            } catch (JSONException exception) {
                exception.printStackTrace();
            }
        }

        @Test
        public void positiveTestExtraIsNull() {
            String json = getJsonResponse();

            try {
                JSONObject jsonObject = new JSONObject(json);
                JSONArray detectives = jsonObject.getJSONArray("detectives");

                for (int i = 0; i < detectives.length(); i++) {
                    JSONArray categories = detectives.getJSONObject(i).getJSONArray("categories");
                    for (int j = 0; j < categories.length(); j++) {
                        int categoryId = categories.getJSONObject(j).getInt("CategoryID");
                        if (categoryId == 2) {
                            assertTrue(categories.getJSONObject(j).isNull("extra"));
                        }
                    }
                }
            } catch (JSONException exception) {
                exception.printStackTrace();
            }
        }

        @Test
        public void positiveTestExtraArray() {
            String json = getJsonResponse();

            try {
                JSONObject jsonObject = new JSONObject(json);
                JSONArray detectives = jsonObject.getJSONArray("detectives");

                for (int i = 0; i < detectives.length(); i++) {
                    JSONArray categories = detectives.getJSONObject(i).getJSONArray("categories");
                    for (int j = 0; j < categories.length(); j++) {
                        int categoryId = categories.getJSONObject(j).getInt("CategoryID");
                        if (categoryId == 1) {
                            JSONObject extra = categories.getJSONObject(j).optJSONObject("extra");
                            JSONArray extraArray = extra != null ? extra.getJSONArray("extraArray") : null;
                            assertNotNull(extraArray);
                        }
                    }
                }
            } catch (JSONException exception) {
                exception.printStackTrace();
            }
        }
    }