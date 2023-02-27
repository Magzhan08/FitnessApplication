package com.makendzi.fitnessapp.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.ActionBar
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.makendzi.fitnessapp.R
import com.makendzi.fitnessapp.adapters.DayModel
import com.makendzi.fitnessapp.adapters.DaysAdapter
import com.makendzi.fitnessapp.adapters.ExerciseAdapter
import com.makendzi.fitnessapp.databinding.ExercisesListFragmentBinding
import com.makendzi.fitnessapp.databinding.FragmentDaysBinding
import com.makendzi.fitnessapp.utils.FragmentManger
import com.makendzi.fitnessapp.utils.MainViewModel

class ExerciseListFragment : Fragment() {

    private lateinit var binding: ExercisesListFragmentBinding
    private val model : MainViewModel by activityViewModels()
    private lateinit var adapter: ExerciseAdapter
    private var actionBar: ActionBar? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        binding = ExercisesListFragmentBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        init()
        model.mutableListOfExercise.observe(viewLifecycleOwner){
            for (i in 0 until model.getExerciseCount()){
                it[i] = it[i].copy(isDone = true)
            }
            adapter.submitList(it)
        }
    }

    private fun init() = with(binding){
        actionBar = (activity as AppCompatActivity).supportActionBar
        actionBar?.title = getString(R.string.exercise)
        adapter = ExerciseAdapter()
        rcViewExercise.layoutManager = LinearLayoutManager(activity as AppCompatActivity)
        rcViewExercise.adapter = adapter
        buttonStart.setOnClickListener {
            FragmentManger.setFragment(WaitingFragment.newInstance(),activity as AppCompatActivity)
        }
    }

    companion object {

        @JvmStatic
        fun newInstance() = ExerciseListFragment()
    }
}