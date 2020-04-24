interface BMI_DAO {
    var _height: Double
    var _weight: Double
    var _bmi: Double

    fun getBMI(height: Double, weight: Double): Double.Companion {
        _height =  height
        _weight = weight

        _bmi = _height / _weight

        return Double
    }
}