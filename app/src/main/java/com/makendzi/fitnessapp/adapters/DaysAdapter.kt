package com.makendzi.fitnessapp.adapters

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.makendzi.fitnessapp.R
import com.makendzi.fitnessapp.databinding.DaysListItemBinding

class DaysAdapter(var listener: Listener) : ListAdapter<DayModel,DaysAdapter.DaysViewHolder>(Comparator()) {


    class DaysViewHolder(view: View) : RecyclerView.ViewHolder(view) {

        private var binding = DaysListItemBinding.bind(view)

        @SuppressLint("SetTextI18n")
        fun setData(day: DayModel, listener: Listener) = with(binding) {
            tvName.text = root.context.getString(R.string.day) + " ${adapterPosition + 1}"
            var exCounter =
                day.exercises.split(",").size.toString() + " " + root.context.getString(R.string.exercises)
            tvExerciseCounter.text = exCounter
            checkBox2.isChecked = day.isDone
            itemView.setOnClickListener {
                listener.onClick(day.copy(dayNumber = adapterPosition + 1))
            }
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DaysViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.days_list_item,parent,false)
        return DaysViewHolder(view)
    }

    override fun onBindViewHolder(holder: DaysViewHolder, position: Int) {
        holder.setData(getItem(position),listener)
    }

    class Comparator : DiffUtil.ItemCallback<DayModel>(){
        override fun areItemsTheSame(oldItem: DayModel, newItem: DayModel): Boolean {
            return oldItem == newItem
        }

        override fun areContentsTheSame(oldItem: DayModel, newItem: DayModel): Boolean {
            return oldItem == newItem
        }

    }

    interface Listener{
        fun onClick(day: DayModel)
    }

}