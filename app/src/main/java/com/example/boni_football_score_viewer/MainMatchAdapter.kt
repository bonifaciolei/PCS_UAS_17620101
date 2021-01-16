package com.example.boni_football_score_viewer

import android.support.v7.widget.RecyclerView
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.example.boni_football_score_viewer.model.Events
import com.example.boni_football_score_viewer.utils.extensions.DateTransformator
import org.jetbrains.anko.AnkoContext

class MainMatchAdapter
    (var list : MutableList<Events>, var menu : String, var listener : (Events) -> Unit) : RecyclerView.Adapter<MainMatchAdapter.PrevMatchAdapterViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PrevMatchAdapterViewHolder {
        return PrevMatchAdapterViewHolder(MainMatchInterface().createView(AnkoContext.create(parent.context,parent)))
    }

    override fun getItemCount(): Int = list.size


    override fun onBindViewHolder(holder: PrevMatchAdapterViewHolder, position: Int) {
        holder.bindItem(list[position],listener)
    }

    inner class PrevMatchAdapterViewHolder(var view : View) : RecyclerView.ViewHolder(view) {

        var dateText : TextView
        var homeMatch : TextView
        var homeScore : TextView
        var awayMatch : TextView
        var awayScore : TextView

        init {

            dateText = view.findViewById(R.id.date_match_item)
            homeMatch = view.findViewById(R.id.home_match_item)
            homeScore = view.findViewById(R.id.home_score_item)
            awayMatch = view.findViewById(R.id.away_match_item)
            awayScore = view.findViewById(R.id.away_score_item)

        }

        fun bindItem(itemsAny : Any, listener: (Events) -> Unit) {

            val items = itemsAny as Events
            dateText.text = items.dateEvent?.DateTransformator()
            homeMatch.text = items.strHomeTeam
            homeScore.text = items.intHomeScore
            awayMatch.text = items.strAwayTeam
            awayScore.text = items.intAwayScore
            view.setOnClickListener {
                listener(items)
            }


        }

    }
}