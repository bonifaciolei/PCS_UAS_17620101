package com.example.boni_football_score_viewer

import android.graphics.Typeface
import android.support.v4.content.ContextCompat
import android.view.Gravity
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import com.example.boni_football_score_viewer.R
import org.jetbrains.anko.*
import org.jetbrains.anko.cardview.v7.cardView

class MainMatchInterface : AnkoComponent<ViewGroup> {

    override fun createView(ui: AnkoContext<ViewGroup>) : View = with(ui) {
        cardView {

            lparams(matchParent, dip(80)){margin = dip(10); bottomMargin = dip(0)}

            verticalLayout {
                padding = dip(10)
                lparams(matchParent, matchParent)
                orientation = LinearLayout.VERTICAL

                textView {
                    id = R.id.date_match_item
                    text = context.getString(R.string.date_example)
                    textColor = ContextCompat.getColor(context,R.color.colorPrimary)
                    gravity = Gravity.CENTER_HORIZONTAL
                }.setTypeface(textView().typeface, Typeface.BOLD)

                relativeLayout {

                    lparams(matchParent, wrapContent)

                    textView {
                        id = R.id.home_match_item
                        text = "Home Team"
                        gravity = Gravity.START
                        textSize = dip(6).toFloat()

                    }.lparams(){rightMargin = dip(10); leftOf(R.id.home_score_item)}

                    textView {
                        id = R.id.home_score_item
                        text = "3"
                        setTypeface(this.typeface, Typeface.BOLD)
                        textSize = dip(6).toFloat()
                        textColor = ContextCompat.getColor(context,R.color.colorPrimary)
                    }.lparams(){rightMargin = dip(10); leftOf(R.id.tv_vs_item)}

                    textView {
                        id = R.id.tv_vs_item
                        text = "vs"
                        gravity = Gravity.CENTER_HORIZONTAL
                    }.lparams(){centerHorizontally()}

                    textView {
                        id = R.id.away_score_item
                        text = "3"
                        gravity = Gravity.RIGHT
                        setTypeface(this.typeface, Typeface.BOLD)
                        textSize = dip(6).toFloat()
                        textColor = ContextCompat.getColor(context,R.color.colorPrimary)
                    }.lparams(){leftMargin = dip(10); rightOf(R.id.tv_vs_item)}

                    textView {
                        id = R.id.away_match_item
                        text = "Away Team"
                        textSize = dip(6).toFloat()

                    }.lparams(){leftMargin = dip(10);
                        rightOf(R.id.away_score_item)
                    }

                }
            }
        }
    }

}