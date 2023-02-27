package com.makendzi.fitnessapp.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.ActionBar
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.makendzi.fitnessapp.R
import com.makendzi.fitnessapp.databinding.DayFinishBinding
import com.makendzi.fitnessapp.utils.FragmentManger
import pl.droidsonroids.gif.GifDrawable


class DayFinishFragment : Fragment() {

    private lateinit var binding: DayFinishBinding
    private var actionBar: ActionBar? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DayFinishBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        actionBar = (activity as AppCompatActivity).supportActionBar
        actionBar?.title = getString(R.string.done)
        binding.imMain.setImageDrawable(GifDrawable((activity as AppCompatActivity).assets,"done_dif.gif"))
        binding.buttonDone.setOnClickListener {
            FragmentManger.setFragment(DaysFragment.newInstance(), activity as AppCompatActivity)
        }
    }

    companion object {

        @JvmStatic
        fun newInstance() = DayFinishFragment()
    }

}