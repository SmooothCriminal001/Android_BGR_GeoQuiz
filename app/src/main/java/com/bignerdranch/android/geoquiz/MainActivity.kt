package com.bignerdranch.android.geoquiz

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider

//A Tag to be used to easily identify 'MainActivity' related debug logs
//const means that this variable is compile-time immutable,
//only primitives are allowed as consts. The right hand side cannot be a function (that changes in runtime)
private const val TAG = "MainActivity"
private const val KEY_INDEX = "index"

class MainActivity : AppCompatActivity() {

    private lateinit var trueButton: Button
    private lateinit var falseButton: Button
    private lateinit var nextButton: Button
    private lateinit var questionTextView: TextView

    //Getting an instance of QuizViewModel lazily, the first time it is called
    //Note QuizViewModel instance keeps its state when a device configuration change occurs
    private val quizViewModel by lazy {
        //ViewModelProviders.of(this).get(QuizViewModel::class.java)        //This method appears to be deprecated
        ViewModelProvider(this).get(QuizViewModel::class.java)
    }

    /*
    Moved to QuizViewModel
    private val questionBank = listOf(
            Question(R.string.question_australia, true),
            Question(R.string.question_oceans, true),
            Question(R.string.question_mideast, false),
            Question(R.string.question_africa, false),
            Question(R.string.question_americas, true),
            Question(R.string.question_asia, true)
    )

    private var currentIndex = 0
     */

    //Called when an activity is first setup, app is clicked on
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.d(TAG, "onCreate(Bundle?) called")
        setContentView(R.layout.activity_main)

        //Getting value of currentIndex from savedInstanceState bundle. If the app was destroyed by OS earlier and opened now (not closed by user), the state will be maintained so
        quizViewModel.currentIndex = savedInstanceState?.getInt(KEY_INDEX, 0) ?: 0

        trueButton = findViewById(R.id.true_button)
        falseButton = findViewById(R.id.false_button)
        nextButton = findViewById(R.id.next_button)
        questionTextView = findViewById(R.id.question_text_view)

        setQuestion()

        trueButton.setOnClickListener {view: View ->
            //Toast.makeText(this, R.string.correct_toast, Toast.LENGTH_SHORT).show()
            checkAnswer(true)
        }

        falseButton.setOnClickListener {view: View ->
            //Toast.makeText(this, R.string.incorrect_toast, Toast.LENGTH_SHORT).show()
            checkAnswer(false)
        }

        nextButton.setOnClickListener {view: View ->
            quizViewModel.moveToNext()
            setQuestion()
        }
    }

    //On start of the activity, when it comes to split-screen/foreground
    override fun onStart() {
        super.onStart()
        Log.d(TAG, "onStart() called")
    }

    //When an activity gets to the foreground
    override fun onResume() {
        super.onResume()
        Log.d(TAG, "onResume() called")
    }

    //When an activity steps out of the strict foreground due to split screen, or a transparent overlay
    override fun onPause() {
        super.onPause()
        Log.d(TAG, "onPause() called")
    }

    //When an activity stops because of the user going to another app. Current app still lives in 'Recent apps'
    override fun onStop() {
        super.onStop()
        Log.d(TAG, "onStop() called")
    }

    //When the activity is destroyed. User clicks back button and gets out of app
    override fun onDestroy() {
        super.onDestroy()
        Log.d(TAG, "onDestroy() called")
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putInt(KEY_INDEX, quizViewModel.currentIndex)          //saving currentIndex in savedInstanceState. This will be called when the app is 'stopped'
    }

    private fun setQuestion(){
        //val questionResId = questionBank[currentIndex].questionResId
        val questionResId = quizViewModel.currentQuestion
        questionTextView.setText(questionResId)
    }

    private fun checkAnswer(userAnswer: Boolean){
        //val currentAnswer = questionBank[currentIndex].answer
        val currentAnswer = quizViewModel.currentAnswer

        val messageResId = if(userAnswer == currentAnswer){
            R.string.correct_toast
        }else{
            R.string.incorrect_toast
        }

        Toast.makeText(this, messageResId, Toast.LENGTH_SHORT).show()
    }
}