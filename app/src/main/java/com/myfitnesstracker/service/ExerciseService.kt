class ExerciseService {

    internal fun fetchExercise(exercise: String) : MutableLiveData<ArrayList<ExerciseDTO>> {
        var _exercises = MutableLiveData<ArrayList<ExerciseDTO>>()
        val service = RetrofitClientInstance.retrofitInstance?.create(IExerciseDAO::class.java)
        val call = service?.getExercise()
        call?.enqueue(object : Callback<ArrayList<ExerciseDTO>> {

            /**
             * Invoked when a network exception occurred talking to the server or when an unexpected
             * exception occurred creating the request or processing the response.
             */
            override fun onFailure(call: Call<ArrayList<ExerciseDTO>>, t: Throwable) {
                // Need to complete failure response
            }

            /**
             * Invoked for a received HTTP response.
             *
             *
             * Note: An HTTP response may still indicate an application-level failure such as a 404 or 500.
             * Call [Response.isSuccessful] to determine if the response indicates success.
             */
            override fun onResponse(
                call: Call<ArrayList<ExerciseDTO>>,
                response: Response<ArrayList<ExerciseDTO>>
            )
            {
                _exercises.value = response.body()
            }
        })
        return _exercises
    }
}

    /*
    private IExerciseDAO exerciseDAO;
    ExerciseService() {
        val retrofit: etrofit = Builder()
            .baseUrl("https://trackapi.nutritionix.com/v2/natural/exercise/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        nutritionDAO = retrofit.create(INutritionDAO.class);
    }

    List<String> getTopContributors(String userName) throws IOException {
        List<ExerciseDTO> exercise = nutritionDAO
    }

     */
}