package com.bignerdranch.android.geoquiz

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.TextView

private const val TAG = "CheatActivity"
private const val ANSWER_EXTRA = "com.bignerdranch.android.geoquiz.ANSWER_TRUE"
private const val CHEATED_EXTRA = "com.bignerdranch.android.geoquiz.HAS_CHEATED"
private var currentAnswer: Boolean = false

class CheatActivity : AppCompatActivity() {

    private lateinit var showAnswerButton: Button
    private lateinit var answerTextView : TextView

    companion object {
        //To create an intent for sending information from the parent activity
        fun createIntent(packageContext: Context, answer: Boolean) = Intent(packageContext, CheatActivity::class.java).apply {
            putExtra(ANSWER_EXTRA, answer)
        }

        fun wasAnswerShown(intent: Intent) = intent.getBooleanExtra(CHEATED_EXTRA, false)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_cheat)

        //'intent' keyword retuns the intent instance that started this activity
        currentAnswer = intent.getBooleanExtra(ANSWER_EXTRA, false)     //Getting a value from parent activity through 'intent'
        //Log.d(TAG, "currentAnswer: $currentAnswer")

        showAnswerButton = findViewById(R.id.show_answer_button)
        answerTextView = findViewById(R.id.answer_text_view)

        showAnswerButton.setOnClickListener {
            val answerTextId = when{
                currentAnswer -> R.string.true_button
                else -> R.string.false_button
            }
            answerTextView.setText(answerTextId)
            setAnswerShownResult(true)
        }
    }

    fun setAnswerShownResult(isAnswerShown: Boolean){
        //Log.d(TAG, "Is answer shown? $isAnswerShown")
        setResult(RESULT_OK, Intent().apply { putExtra(CHEATED_EXTRA, isAnswerShown) })
    }
}