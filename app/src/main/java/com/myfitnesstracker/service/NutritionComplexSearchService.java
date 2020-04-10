package com.myfitnesstracker.service;

import com.myfitnesstracker.dao.INutritionComplexSearchDAO;
import com.myfitnesstracker.dto.NutritionSearchResultDTO;
import java.util.ArrayList;


public class NutritionComplexSearchService {

    private INutritionComplexSearchDAO nutritionSearchResultDAO;

    public ArrayList<NutritionSearchResultDTO> fetchComplexSearchResults(String searchedItem) throws Exception{
        ArrayList<NutritionSearchResultDTO> complexResults;
        complexResults = nutritionSearchResultDAO.fetchNutritionComplexSearch(searchedItem);

        try{
            if (complexResults != null){
                return complexResults;
            }
        } catch (NullPointerException e) {
            // Need to make a good exception
        }
        return null;
    }
}
