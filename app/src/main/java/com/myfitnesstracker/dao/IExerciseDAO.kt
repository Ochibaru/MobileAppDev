import com.myfitnesstracker.dto.ExerciseRequest
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.Headers
import retrofit2.http.POST

/*
 "Content-Type: application/json" -H "x-app-id: <your app id>" -H "x-app-key: <your app key>" https://trackapi.nutritionix.com/v2/search/instant?query=apple
 */

interface IExerciseDAO {

    @Headers("x-app-id: b36b7e87", "x-app-key: c487e33d8a8f2e583faee78a5e672004", "x-remote-user-id: 0", "Content-Type: application/json")
    @POST("/v2/natural/exercise/")
    fun getExercise(@Body body: ExerciseRequest) : Call<Any>
}