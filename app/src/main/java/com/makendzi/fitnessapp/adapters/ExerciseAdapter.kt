package com.makendzi.fitnessapp.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.makendzi.fitnessapp.R
import com.makendzi.fitnessapp.databinding.ExerciseListItemBinding
import pl.droidsonroids.gif.GifDrawable

class ExerciseAdapter : ListAdapter<ExerciseModel,ExerciseAdapter.ExerxiseViewHolder>(Comparator()) {


    class ExerxiseViewHolder(view: View): RecyclerView.ViewHolder(view){
        private var binding = ExerciseListItemBinding.bind(view)

        fun setDataToList(exerciseModel: ExerciseModel) = with(binding){
            tvNameEx.text = exerciseModel.name_ex
            tvCount.text = exerciseModel.time_ex
            checkBox.isChecked = exerciseModel.isDone
            imageEx.setImageDrawable(GifDrawable(root.context.assets,exerciseModel.img_ex))
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ExerxiseViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.exercise_list_item,parent,false)
        return ExerxiseViewHolder(view)
    }

    override fun onBindViewHolder(holder: ExerxiseViewHolder, position: Int) {
        holder.setDataToList(getItem(position))
    }

    class Comparator: DiffUtil.ItemCallback<ExerciseModel>() {
        override fun areContentsTheSame(oldItem: ExerciseModel, newItem: ExerciseModel): Boolean {
            return oldItem == newItem
        }

        override fun areItemsTheSame(oldItem: ExerciseModel, newItem: ExerciseModel): Boolean {
            return oldItem == newItem
        }
    }
}