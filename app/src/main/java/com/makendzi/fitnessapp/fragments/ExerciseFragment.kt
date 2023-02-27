package com.makendzi.fitnessapp.fragments

import android.os.Bundle
import android.os.CountDownTimer
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.ActionBar
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.activityViewModels
import com.makendzi.fitnessapp.R
import com.makendzi.fitnessapp.adapters.ExerciseModel
import com.makendzi.fitnessapp.databinding.ExerciseBinding
import com.makendzi.fitnessapp.utils.FragmentManger
import com.makendzi.fitnessapp.utils.MainViewModel
import com.makendzi.fitnessapp.utils.TimeUtils
import pl.droidsonroids.gif.GifDrawable

class ExerciseFragment : Fragment() {

    private var timer: CountDownTimer? = null
    private var exerciseCounter = 0
    private lateinit var binding: ExerciseBinding
    private var exList: ArrayList<ExerciseModel>? = null
    private val model : MainViewModel by activityViewModels()
    private var actionBar: ActionBar? = null
    private var currentDayForCheck = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        binding = ExerciseBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        currentDayForCheck = model.currentDay
        exerciseCounter = model.getExerciseCount()
        actionBar = (activity as AppCompatActivity).supportActionBar
        model.mutableListOfExercise.observe(viewLifecycleOwner){
            exList = it
            nextExercise()
        }
        binding.buttonNext.setOnClickListener {
            nextExercise()
        }
    }

    private fun nextExercise(){
        if(exerciseCounter < exList?.size!!){
            val exercise = exList?.get(exerciseCounter) ?: return
            exerciseCounter++
            showExercis(exercise)
            setExerciseType(exercise)
            showNextExercise()
        }else{
            exerciseCounter++
            FragmentManger.setFragment(DayFinishFragment.newInstance(), activity as AppCompatActivity)
        }
    }

    private fun showExercis(exerciseModel: ExerciseModel) = with(binding){
        imageMain.setImageDrawable(GifDrawable(root.context.assets,exerciseModel.img_ex))
        tvNameOfEx.text = exerciseModel.name_ex
        var title = "$exerciseCounter / ${exList?.size}"
        actionBar?.title = title
    }

    private fun setExerciseType(exerciseModel: ExerciseModel){
//        timer?.cancel()
        if (exerciseModel.time_ex.startsWith("x")){
            binding.tvTime.text = exerciseModel.time_ex
        } else{
            startTimer(exerciseModel)
        }
    }

    private fun showNextExercise() = with(binding){
        if(exerciseCounter < exList?.size!!){
            val exercise = exList?.get(exerciseCounter) ?: return
            imageNextEx.setImageDrawable(GifDrawable(root.context.assets,exercise.img_ex))
            setTimeType(exercise)
        }else{
            imageNextEx.setImageDrawable(GifDrawable(root.context.assets,"done_dif.gif"))
            tvNextNameEx.text = getString(R.string.done)
        }
    }

    private fun setTimeType(exerciseModel: ExerciseModel){
        if (exerciseModel.time_ex.startsWith("x")){
            binding.tvNextNameEx.text = exerciseModel.name_ex + ": " + exerciseModel.time_ex
        } else{
            val name = exerciseModel.name_ex + ": ${TimeUtils.getTime(exerciseModel.time_ex.toLong() * 1000)}"
            binding.tvNextNameEx.text = name
        }
    }


    private fun startTimer(exerciseModel: ExerciseModel) = with(binding){
        progBar.max = exerciseModel.time_ex.toInt() * 1000
        timer?.cancel()
        timer = object : CountDownTimer(exerciseModel.time_ex.toLong() * 1000, 1){
            override fun onTick(time: Long) {
                tvTime.text = TimeUtils.getTime(time)
                progBar.progress = time.toInt()
            }

            override fun onFinish() {
                nextExercise()
            }

        }.start()

    }

    override fun onDetach() {
        super.onDetach()
        model.savePrefData(currentDayForCheck.toString(),exerciseCounter - 1 )
        timer?.cancel()
    }

    companion object {

        @JvmStatic
        fun newInstance() = ExerciseFragment()
    }
}