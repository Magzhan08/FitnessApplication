package com.makendzi.fitnessapp.utils

import android.content.SharedPreferences
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.makendzi.fitnessapp.adapters.ExerciseModel

class MainViewModel : ViewModel() {
    val  mutableListOfExercise = MutableLiveData<ArrayList<ExerciseModel>>()
    var pref: SharedPreferences? = null
    var currentDay = 0

    fun savePrefData(key: String,value: Int){
        pref?.edit()?.putInt(key,value)?.apply()
    }

    fun getExerciseCount(): Int{
        return pref?.getInt(currentDay.toString(),0) ?: 0
    }

}