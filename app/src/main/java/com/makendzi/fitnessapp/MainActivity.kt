package com.makendzi.fitnessapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import com.makendzi.fitnessapp.fragments.DaysFragment
import com.makendzi.fitnessapp.utils.FragmentManger
import com.makendzi.fitnessapp.utils.MainViewModel

class MainActivity : AppCompatActivity() {
    private val model: MainViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        model.pref = getSharedPreferences("mainMyTable", MODE_PRIVATE)
        FragmentManger.setFragment(DaysFragment.newInstance(), this)
    }

    override fun onBackPressed() {
        if (FragmentManger.currentFragment is DaysFragment) super.onBackPressed()
        else FragmentManger.setFragment(DaysFragment.newInstance(), this)
    }
}