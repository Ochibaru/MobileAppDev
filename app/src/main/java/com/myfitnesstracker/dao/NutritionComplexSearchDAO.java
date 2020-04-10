package com.myfitnesstracker.dao;

import androidx.lifecycle.MutableLiveData;

import com.myfitnesstracker.dto.NutritionSearchResultDTO;
import org.json.JSONException;
import org.json.JSONObject;
import java.util.ArrayList;

public class NutritionComplexSearchDAO implements INutritionComplexSearchDAO {

    private NetworkDAO networkDAO;

    public ArrayList<NutritionSearchResultDTO> fetchNutritionComplexSearch(String searchedItem) throws Exception{
        ArrayList<NutritionSearchResultDTO> searchResults = new ArrayList<NutritionSearchResultDTO>();
        String rawJson = networkDAO.request("https://api.spoonacular.com/recipes/complexSearch?apiKey=1e607600ad2648b0bd74de02247c86ba&number=1&query=" + searchedItem);
        return parsedNutritionSearchResults(searchResults, rawJson);
    }

    private ArrayList<NutritionSearchResultDTO> parsedNutritionSearchResults(ArrayList<NutritionSearchResultDTO> searchResults, String rawJson) throws JSONException {

        // Creating DTO object
        NutritionSearchResultDTO complexResults = new NutritionSearchResultDTO();

        // Parsing data from JSON query from API
        JSONObject obj = new JSONObject(rawJson);
        JSONObject movies = obj.getJSONObject("results");
        String id = movies.getString("id");
        String title = movies.getString("title");
        String image = movies.getString("image");
        String imageType = movies.getString("imagetype");

        // Populating DTO with data
        complexResults.setId(id);
        complexResults.setImage(image);
        complexResults.setImageType(imageType);
        complexResults.setTitle(title);

        // Adding DTO to a list and returning results
        searchResults.add(complexResults);

        return searchResults;
    }
}
