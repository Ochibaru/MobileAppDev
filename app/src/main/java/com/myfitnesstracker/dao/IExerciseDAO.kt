/*
curl -H "Content-Type: application/json" -H "x-app-id: <your app id>" -H "x-app-key: <your app key>" https://trackapi.nutritionix.com/v2/search/instant?query=apple
 */

public interface IExerciseDAO {

    @GET("/v2/natural/exercise/")
    fun getExercise(@Query("query") exercise:String) : Call<ArrayList<ExerciseDTO>>
}
