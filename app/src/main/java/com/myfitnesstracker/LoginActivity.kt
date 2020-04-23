package com.myfitnesstracker

import android.animation.Animator
import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.os.CountDownTimer
import android.util.Log
import android.util.Patterns
import android.view.View
import android.view.View.VISIBLE
import android.view.Window
import android.view.WindowManager
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.afollestad.materialdialogs.MaterialDialog
import com.afollestad.materialdialogs.input.input
import com.google.android.gms.tasks.OnCompleteListener
import com.google.android.material.textfield.TextInputEditText
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.android.synthetic.main.login_activity.*
import kotlinx.coroutines.*
import java.util.regex.Pattern


class LoginActivity : AppCompatActivity() {

    private val AUTH_REQUEST_CODE = 2002
    private var user : FirebaseUser? = null
    private lateinit var auth: FirebaseAuth
    private lateinit var email: TextInputEditText
    private lateinit var password: TextInputEditText

    private val PASSWORD_PATTERN: Pattern = Pattern.compile(
        "^" +  "(?=.*[0-9])" +         //at least 1 digit
                "(?=.*[a-z])" +         //at least 1 lower case letter
                "(?=.*[A-Z])" +         //at least 1 upper case letter
                "(?=.*[a-zA-Z])" +  //any letter
                "(?=.*[@#$%^&+=!])" +  //at least 1 special character
                "(?=\\S+$)" +  //no white spaces
                ".{4,}" +  //at least 8 characters
                "$"
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        auth = FirebaseAuth.getInstance()
        requestWindowFeature(Window.FEATURE_NO_TITLE)
        window.setFlags(
            WindowManager.LayoutParams.FLAG_FULLSCREEN,
            WindowManager.LayoutParams.FLAG_FULLSCREEN)
        setContentView(R.layout.login_activity)
        object : CountDownTimer(5000, 1000) {
            override fun onFinish() {
                viewFlame.visibility = View.GONE
                loadingProgressBar.visibility = View.GONE
                rootView.setBackgroundColor(ContextCompat.getColor(this@LoginActivity, R.color.grayish))
                iconFlame.setImageResource(R.drawable.ic_flame_colored)
                startAnimation()
            }
            override fun onTick(p0: Long) {}
        }.start()

        email = findViewById(R.id.txtEmail)
        password = findViewById(R.id.txtPassword)

        btnLogin.setOnClickListener {
            val email: String = email.text.toString()
            val password: String = password.text.toString()
            logon(email, password)

        }
        btnSignUp.setOnClickListener {
            val email: String = email.text.toString()
            val password: String = password.text.toString()
            if (confirmInput(email, password)){
                createUser(email, password)
            }
        }
        btnForgotPassword.setOnClickListener {
            resetPassword()
        }

    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == Activity.RESULT_OK) {
            if (requestCode == AUTH_REQUEST_CODE){
                user = FirebaseAuth.getInstance().currentUser
            }
        }
    }

    private fun logon(email:String, password:String){
        auth.signInWithEmailAndPassword(email, password).addOnCompleteListener(this, OnCompleteListener { task ->
            if(task.isSuccessful) {
                Toast.makeText(this, "Successfully Logged In", Toast.LENGTH_LONG).show()
                val intent = Intent(this, MainActivity::class.java)
                startActivity(intent)
                finish()
            }else {
                Toast.makeText(this, "Login Failed", Toast.LENGTH_LONG).show()
            }
        })
    }
    private fun createUser(email:String, password:String) {
        auth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(this, OnCompleteListener{ task ->
            if(task.isSuccessful){
                val created = hashMapOf(
                    "user" to "created successfully"
                )
                val firestore: FirebaseFirestore = FirebaseFirestore.getInstance()
                val setBMI = firestore.collection("Login").document(email).collection("BMI").document().set(created)
                setBMI.addOnSuccessListener {
                    Log.d("Firebase", "Calculation Succeeded")
                    val setExercise = firestore.collection("Login").document(email).collection("Exercises").document().set(created)
                    setExercise.addOnSuccessListener {
                        val setFood = firestore.collection("Login").document(email).collection("Food").document().set(created)
                        setFood.addOnSuccessListener {
                            Toast.makeText(this, "Successfully Registered", Toast.LENGTH_LONG).show()
                            val intent = Intent(this, MainActivity::class.java)
                            startActivity(intent)
                            finish()
                        }
                    }
                }
            }else {
                Toast.makeText(this, "Registration Failed", Toast.LENGTH_LONG).show()
            }
        })
    }
    private fun resetPassword(){
        MaterialDialog(this).show {
            input(hint = "Enter email") { _, text ->
                val inputText = text.toString()
                FirebaseAuth.getInstance().sendPasswordResetEmail(inputText)
                    .addOnCompleteListener { task ->
                        if (task.isSuccessful) {
                            Log.d("PasswordRequest", "Email sent.")
                        }
                    }
            }
            positiveButton(R.string.send)
        }
    }

    private fun startAnimation() {
        iconFlame.animate().apply {
            x(50f)
            y(100f)
            duration = 1000
        }.setListener(object : Animator.AnimatorListener {
            override fun onAnimationRepeat(p0: Animator?) {}
            override fun onAnimationEnd(p0: Animator?) {viewAfterAnimation.visibility = VISIBLE}
            override fun onAnimationCancel(p0: Animator?) {}
            override fun onAnimationStart(p0: Animator?) {}
        })
    }


    // Firestore was having issues allowing user to be created, added valid email and password requirements
    private fun validateEmail(email: String): Boolean {
        return if (email.isEmpty()) {
            Toast.makeText(this, "Email can't be empty", Toast.LENGTH_LONG).show()
            false
        } else if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            Toast.makeText(this, "Please enter a valid email address", Toast.LENGTH_LONG).show()
            false
        } else {
            true
        }
    }
    private fun validatePassword(password: String): Boolean {
        return if (password.isEmpty()) {
            Toast.makeText(this, "Password can't be empty", Toast.LENGTH_LONG).show()
            false
        } else if (!PASSWORD_PATTERN.matcher(password).matches()) {
            Toast.makeText(this, "Password too weak, must contain 1 digit, 1 upper, 1 lower and be at least 8 characters", Toast.LENGTH_LONG).show()
            false
        } else {
            true
        }
    }
    fun confirmInput(email: String, password: String): Boolean {
        if (!validateEmail(email) or !validatePassword(password)) {
            return false
        } else {
            var input = "Email: " + email
            input += "\n"
            input += "Password: " + password
            Toast.makeText(this, input, Toast.LENGTH_SHORT).show()
            return true
        }
    }


}
//firestore.collection("Login").document(email).collection("BMI").document(formatted)
//firestore.collection("Login").document(userEntry).collection("Exercise").document(formatted).set(exerciseResponse)
//firestore.collection("Login").document(userEntry).collection("Food").document("Date").collection(formatted).document(foodItem).set(nutrition)

