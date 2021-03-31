package com.bignerdranch.android.geoquiz

import android.util.Log
import androidx.lifecycle.ViewModel

private const val TAG = "QuizViewModel"

//Using a ViewModel to preserve states across device configuration changes for MainActivity
class QuizViewModel: ViewModel() {

    private val questionBank = listOf(
        Question(R.string.question_australia, true),
        Question(R.string.question_oceans, true),
        Question(R.string.question_mideast, false),
        Question(R.string.question_africa, false),
        Question(R.string.question_americas, true),
        Question(R.string.question_asia, true)
    )

    var currentIndex = 0 //Not private because it has to be accessed in MainActivity explicitly
    var isCheater = false

    init {
        Log.d(TAG, "QuizViewModel created")
    }

    override fun onCleared() {
        super.onCleared()
        Log.d(TAG, "QuizViewModel destroyed")
    }

    val currentAnswer: Boolean
        get() = questionBank[currentIndex].answer

    val currentQuestion : Int
        get() = questionBank[currentIndex].questionResId

    fun moveToNext(){
        currentIndex = (currentIndex + 1) % questionBank.size
    }
}