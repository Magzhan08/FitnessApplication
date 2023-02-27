package com.makendzi.fitnessapp.fragments

import android.os.Bundle
import android.os.CountDownTimer
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.ActionBar
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.repeatOnLifecycle
import com.makendzi.fitnessapp.R
import com.makendzi.fitnessapp.adapters.ExerciseAdapter
import com.makendzi.fitnessapp.databinding.ExercisesListFragmentBinding
import com.makendzi.fitnessapp.databinding.WaitingFragmentBinding
import com.makendzi.fitnessapp.utils.FragmentManger
import com.makendzi.fitnessapp.utils.TimeUtils

const val COUNT_DOWN_TIME = 3000L
class WaitingFragment : Fragment() {

    private lateinit var binding: WaitingFragmentBinding
    private lateinit var timer: CountDownTimer
    private var actionBar: ActionBar? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = WaitingFragmentBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        actionBar = (activity as AppCompatActivity).supportActionBar
        actionBar?.title = getString(R.string.waiting)
        binding.progressBarTimer.max = COUNT_DOWN_TIME.toInt()
        startTimer()
    }

    private fun startTimer() = with(binding){
        timer = object : CountDownTimer(COUNT_DOWN_TIME,1){
            override fun onTick(restTime: Long) {
                tvTimer.text = TimeUtils.getTime(restTime)
                progressBarTimer.progress = restTime.toInt()
            }

            override fun onFinish() {
                FragmentManger.setFragment(ExerciseFragment.newInstance(),activity as AppCompatActivity)
            }

        }.start()
    }

    override fun onDetach() {
        super.onDetach()
        timer.cancel()
    }

    companion object {

        @JvmStatic
        fun newInstance() = WaitingFragment()
    }

}