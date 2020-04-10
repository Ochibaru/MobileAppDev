package com.myfitnesstracker.dao;

import com.myfitnesstracker.dto.NutritionSearchResultDTO;

import java.util.ArrayList;

public interface INutritionComplexSearchDAO {
    ArrayList<NutritionSearchResultDTO> fetchNutritionComplexSearch(String searchedItem) throws Exception;
}
