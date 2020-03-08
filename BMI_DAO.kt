interface BMI_DAO {
    val _height: Double
    val _weight: Double
    val _bmi: Double

    public open fun getBMI(height: Double, weight: Double): Double.Companion {
        val _height =  height
        val _weight = weight

        val _bmi = _height / _weight

        return Double
    }
}