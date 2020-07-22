package com.vcmanea.aboutme

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.inputmethod.InputMethodManager
import androidx.databinding.DataBindingUtil
import com.vcmanea.aboutme.databinding.ActivityMainBinding
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private var myName: MyName=MyName("Valentin Cornel")


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        binding=DataBindingUtil.setContentView(this,R.layout.activity_main)
        binding.myName=myName

        binding.doneButton.setOnClickListener {
            addNickname(it)
            // Hide the keyboard.
            val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            imm.hideSoftInputFromWindow(it.windowToken, 0)
        }
    }

    private fun addNickname(view: View) {
        binding.apply {
//            nickName_textview.text = nickName_editText.text
            myName?.nickName=nickNameEditText.text.toString()
            invalidateAll()
            //In order ro refresh the UI with new data, we ned invalidate all binding expressions so they can get recreated with the correct data
            //Invalidates all binding expressions and requests a new rebind to refresh UI.
            nickName_textview.visibility = View.VISIBLE
            view.visibility = View.GONE
            nickName_editText.visibility = View.GONE
        }
    }
}