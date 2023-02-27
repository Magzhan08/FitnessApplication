package com.makendzi.fitnessapp.fragments

import android.os.Bundle
import android.view.*
import androidx.appcompat.app.ActionBar
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.makendzi.fitnessapp.R
import com.makendzi.fitnessapp.adapters.DayModel
import com.makendzi.fitnessapp.adapters.DaysAdapter
import com.makendzi.fitnessapp.adapters.ExerciseModel
import com.makendzi.fitnessapp.databinding.FragmentDaysBinding
import com.makendzi.fitnessapp.utils.DialogManager
import com.makendzi.fitnessapp.utils.FragmentManger
import com.makendzi.fitnessapp.utils.MainViewModel

class DaysFragment : Fragment(), DaysAdapter.Listener {

    private lateinit var adapter: DaysAdapter
    private lateinit var binding: FragmentDaysBinding
    private val model: MainViewModel by activityViewModels()
    private var actionBar: ActionBar? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentDaysBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        model.currentDay = 0
        initRcView()
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        return inflater.inflate(R.menu.main_menu, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == R.id.reset) {
            DialogManager.showDialog(
                activity as AppCompatActivity,
                R.string.reset_days_message,
                object : DialogManager.Listener {
                    override fun onClick() {
                        model.pref?.edit()?.clear()?.apply()
                        adapter.submitList(fillDaysArray())
                    }

                }
            )

        }
        return super.onOptionsItemSelected(item)
    }

    private fun initRcView() = with(binding) {
        adapter = DaysAdapter(this@DaysFragment)
        actionBar = (activity as AppCompatActivity).supportActionBar
        actionBar?.title = getString(R.string.days)
        rcViewDays.layoutManager = LinearLayoutManager(activity as AppCompatActivity)
        rcViewDays.adapter = adapter
        adapter.submitList(fillDaysArray())
    }

    private fun fillDaysArray(): ArrayList<DayModel> {
        var doneDaysCounter = 0
        val tempArray = ArrayList<DayModel>()
        resources.getStringArray(R.array.day_exercises).forEach {
            model.currentDay++
            val exCountOfDay = it.split(",").size
            tempArray.add(DayModel(it, 0, model.getExerciseCount() == exCountOfDay))
        }
        binding.progressBar.max = tempArray.size
        tempArray.forEach {
            if (it.isDone) doneDaysCounter++
        }
        updateRestDaysUI(tempArray.size - doneDaysCounter, tempArray.size)
        return tempArray
    }

    private fun updateRestDaysUI(restDays: Int, allDaysCount: Int) = with(binding) {
        val tvRest = "$restDays " + getString(R.string.rest_days)
        tvRestDays.text = tvRest
        progressBar.progress = allDaysCount - restDays
    }

    private fun fillExerciseList(day: DayModel) {
        val tempList = ArrayList<ExerciseModel>()
        day.exercises.split(",").forEach {
            val exerciseList = resources.getStringArray(R.array.exercise)
            val exercises = exerciseList[it.toInt()].split("|")
            tempList.add(ExerciseModel(exercises[0], exercises[1], false, exercises[2]))
        }
        model.mutableListOfExercise.value = tempList

    }

    companion object {

        @JvmStatic
        fun newInstance() = DaysFragment()
    }

    override fun onClick(day: DayModel) {
        if (!day.isDone) {
            fillExerciseList(day)
            model.currentDay = day.dayNumber
            FragmentManger.setFragment(
                ExerciseListFragment.newInstance(),
                activity as AppCompatActivity
            )
        } else {

            DialogManager.showDialog(
                activity as AppCompatActivity,
                R.string.reset_day_message,
                object : DialogManager.Listener {
                    override fun onClick() {
                        model.savePrefData(day.dayNumber.toString(), 0)
                        fillExerciseList(day)
                        model.currentDay = day.dayNumber
                        FragmentManger.setFragment(
                            ExerciseListFragment.newInstance(),
                            activity as AppCompatActivity
                        )
                    }

                }
            )
        }
    }
}