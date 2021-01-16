package com.example.boni_football_score_viewer

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.content.Context
import android.database.sqlite.SQLiteConstraintException
import android.graphics.Color
import android.graphics.Typeface
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.content.ContextCompat
import android.support.v4.widget.SwipeRefreshLayout
import android.view.*
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.Toast
import com.bumptech.glide.Glide
import com.example.boni_football_score_viewer.db.Response
import com.example.boni_football_score_viewer.model.Events
import com.example.boni_football_score_viewer.model.TeamModel
import com.example.boni_football_score_viewer.db.database
import com.example.boni_football_score_viewer.model.DetailMatchViewModel
import com.example.boni_football_score_viewer.model.DetailMatchViewModelFactory
import dagger.android.support.AndroidSupportInjection
import kotlinx.coroutines.experimental.android.UI
import kotlinx.coroutines.experimental.launch
import org.jetbrains.anko.*
import org.jetbrains.anko.db.classParser
import org.jetbrains.anko.db.insert
import org.jetbrains.anko.db.delete
import org.jetbrains.anko.db.select
import org.jetbrains.anko.design.snackbar
import org.jetbrains.anko.support.v4.UI
import timber.log.Timber
import javax.inject.Inject


const val DETAIL_EVENT = "detail_event"

class DetailMatch : Fragment() {

    companion object {
        fun newInstance(): DetailMatch = DetailMatch()
    }

    @Inject
    lateinit var detailMatchViewModelFactory: DetailMatchViewModelFactory

    var events: Events? = null
    var listImage : MutableList<String?> = ArrayList()
    lateinit var imageViewHome : ImageView
    lateinit var imageViewAway : ImageView
    lateinit var swipeRefreshLayout: SwipeRefreshLayout
    var menuItem : Menu? = null
    var isFavorite : Boolean = false

    private lateinit var detailMatchViewModel: DetailMatchViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)

        val bundle = arguments
        when {
            (bundle != null) -> events = bundle.getParcelable<Events>(DETAIL_EVENT)
             else -> return
        }
    }

    private fun execute(response : Response) = when (response) {
        is Response.Loading -> renderLoadingState()
        is Response.Success -> renderDataState(response.data as TeamModel)
        is Response.Error -> renderErrorState()
    }

    private fun renderLoadingState() {
    }

    private fun renderDataState(footballEvent : TeamModel?) {
        listImage.add(footballEvent?.teams?.get(0)?.strTeamBadge)

        Glide.with(this).load(listImage[0]).into(imageViewHome)

        if (listImage.size>1){
            Glide.with(this).load(listImage[1]).into(imageViewAway)
            events?.strTeamHomeBadge = listImage[0].orEmpty()
            events?.strTeamAwayBadge = listImage[1].orEmpty()
        }


    }

    override fun onCreateOptionsMenu(menu: Menu?, inflater: MenuInflater?) {
        inflater?.inflate(R.menu.menu_favorite, menu)
        menuItem = menu
        setFavorite()
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        when(item?.itemId){
            R.id.favorites_detail -> {
                if (isFavorite) removeToFavorite() else addToFavorite()
                isFavorite = !isFavorite
                setFavorite()
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onAttach(context: Context?) {
        AndroidSupportInjection.inject(this)
        super.onAttach(context)
    }

    override fun onDestroy() {
        super.onDestroy()
        listImage.clear()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        listImage.clear()
    }

    override fun onDetach() {
        super.onDetach()
        listImage.clear()
    }

    private fun renderErrorState() {
        Timber.e("Error")
        Toast.makeText(context, "Error", Toast.LENGTH_SHORT).show()
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        detailMatchViewModel = ViewModelProviders.of(this, detailMatchViewModelFactory).get(
            DetailMatchViewModel::class.java)

        detailMatchViewModel.response().observe(this, Observer { response -> execute(response!!) })

        launch(UI) {
            detailMatchViewModel.loadImage(events?.strHomeTeam)
            detailMatchViewModel.loadImage(events?.strAwayTeam)
        }



        favoriteState()

        return UI {

                scrollView {
                    verticalLayout {
                        orientation = LinearLayout.VERTICAL
                        lparams(matchParent, matchParent)

                        view {
                            backgroundColor = Color.LTGRAY
                        }.lparams(matchParent, dip(1)){margin = dip(10)}

                        textView {
                            text = "Score View";
                            gravity = Gravity.CENTER_HORIZONTAL
                        }.setTypeface(textView().typeface, Typeface.BOLD)

                        relativeLayout {
                            lparams(matchParent, dip(100))

                            linearLayout {
                                id = R.id.home_match_item
                                orientation = LinearLayout.VERTICAL
                                gravity = Gravity.CENTER

                                imageViewHome =imageView(R.drawable.avd_show_password) {
                                }.lparams(dip(50), dip(50))

                                textView {
                                    id = R.id.home_match_item
                                    text = events?.strHomeTeam
                                    textColor = ContextCompat.getColor(context, R.color.colorPrimary)
                                    textSize = dip(7).toFloat()
                                    gravity = Gravity.CENTER
                                }

                            }.lparams(dip(150), wrapContent){leftOf(R.id.home_score_item);leftMargin = dip(10)}

                            textView {
                                id = R.id.home_score_item
                                text = events?.intHomeScore
                                textColor = ContextCompat.getColor(context, R.color.colorPrimary)
                                textSize = dip(10).toFloat()

                            }.lparams(){leftOf(R.id.tv_vs_item);leftMargin = dip(10); topMargin = dip(15)}

                            textView {
                                id = R.id.tv_vs_item
                                text = "vs"
                                gravity = Gravity.CENTER_HORIZONTAL
                                setTypeface(this.typeface, Typeface.BOLD)

                            }.lparams(){centerHorizontally(); margin = dip(15)}

                            textView {
                                id = R.id.away_score_item
                                text = events?.intAwayScore
                                textColor = ContextCompat.getColor(context, R.color.colorPrimary)
                                textSize = dip(10).toFloat()
                            }.lparams(){rightOf(R.id.tv_vs_item);rightMargin = dip(10); topMargin = dip(15)}

                            linearLayout {
                                id = R.id.away_match_item
                                orientation = LinearLayout.VERTICAL
                                gravity = Gravity.CENTER

                                imageViewAway = imageView(R.drawable.avd_show_password) {
                                }.lparams(dip(50), dip(50))

                                textView {
                                    id = R.id.away_match_item
                                    text = events?.strAwayTeam
                                    textColor = ContextCompat.getColor(context, R.color.colorPrimary)
                                    textSize = dip(7).toFloat()
                                    gravity = Gravity.CENTER
                                }

                            }.lparams(dip(150), wrapContent){rightOf(R.id.away_score_item);rightMargin = dip(10)}
                        }

                        view {
                            backgroundColor = Color.LTGRAY
                        }.lparams(matchParent, dip(1)){margin = dip(10)}

                        textView {
                            text = "Match Detail";
                            gravity = Gravity.CENTER_HORIZONTAL
                        }.setTypeface(textView().typeface, Typeface.BOLD)

                        linearLayout {
                            orientation = LinearLayout.HORIZONTAL
                            gravity = Gravity.CENTER_HORIZONTAL
                            weightSum = 5f

                            textView {
                                text = "Stadium";
                                textColor = ContextCompat.getColor(context, R.color.colorPrimary)
                                gravity = Gravity.LEFT
                                setTypeface(this.typeface, Typeface.BOLD)
                            }.lparams(){weight = 1f}

                            textView {
                                id = R.id.stadium
                                text = events?.strVenue
                                gravity = Gravity.END
                                gravity = Gravity.RIGHT
                            }.lparams(dip(100), wrapContent){weight = 2f}

                        }.lparams(matchParent, wrapContent){margin = dip(10)}

                        linearLayout {
                            orientation = LinearLayout.HORIZONTAL
                            gravity = Gravity.CENTER_HORIZONTAL
                            weightSum = 5f

                            textView {
                                text = "Country";
                                textColor = ContextCompat.getColor(context, R.color.colorPrimary)
                                gravity = Gravity.LEFT
                                setTypeface(this.typeface, Typeface.BOLD)
                            }.lparams(){weight = 1f}

                            textView {
                                id = R.id.stadium_country
                                text = events?.strCountry
                                gravity = Gravity.END
                                gravity = Gravity.RIGHT
                            }.lparams(dip(100), wrapContent){weight = 2f}

                        }.lparams(matchParent, wrapContent){margin = dip(10)}

                        linearLayout {
                            orientation = LinearLayout.HORIZONTAL
                            gravity = Gravity.CENTER_HORIZONTAL
                            weightSum = 5f

                            textView {
                                text = "Match Date";
                                textColor = ContextCompat.getColor(context, R.color.colorPrimary)
                                gravity = Gravity.LEFT
                                setTypeface(this.typeface, Typeface.BOLD)
                            }.lparams(){weight = 1f}

                            textView {
                                id = R.id.date_match_item
                                text = events?.dateEvent
                                gravity = Gravity.END
                                gravity = Gravity.RIGHT
                            }.lparams(dip(100), wrapContent){weight = 2f}

                        }.lparams(matchParent, wrapContent){margin = dip(10)}

                    }
                }
        }.view
    }

    private fun favoriteState(){
        context?.database?.use {
            val result = select(Events.TABLE_SAVED_MATCH)
                    .whereArgs("(ID_EVENT = {event_id})",
                            "event_id" to "${events?.idEvent}" )
            val favorite = result.parseList(classParser<Events>())
            if (!favorite.isEmpty()) isFavorite = true
        }
    }

    private fun setFavorite(){
        when{
            isFavorite -> {
                menuItem?.getItem(0)?.icon = ContextCompat.getDrawable(context!!, R.drawable.ic_added_to_favorites)
            } else -> {
            menuItem?.getItem(0)?.icon = ContextCompat.getDrawable(context!!, R.drawable.ic_add_to_favorites)
        }
        }
    }

    private fun addToFavorite(){
        try {
            context?.database?.use {
                insert(
                        Events.TABLE_SAVED_MATCH,
                        Events.ID_EVENT to events?.idEvent,
                        Events.HOME_TEAM to events?.strHomeTeam,
                        Events.AWAY_TEAM to events?.strAwayTeam,
                        Events.HOME_SCORE to events?.intHomeScore,
                        Events.AWAY_SCORE to events?.intAwayScore,
                        Events.DATE_EVENT to events?.dateEvent,
                        Events.HOME_BADGE to events?.strTeamHomeBadge,
                        Events.AWAY_BADGE to events?.strTeamAwayBadge,
                        Events.STADIUM to events?.strVenue,
                        Events.STADIUM_COUNTRY to events?.strCountry
                )
            }
            Toast.makeText(context, "Match Saved!", Toast.LENGTH_SHORT).show()
        } catch (e : SQLiteConstraintException){
            snackbar(swipeRefreshLayout, e.localizedMessage).show()
        }
    }

    private fun removeToFavorite(){
        try {
            context?.database?.use {
                delete(
                        Events.TABLE_SAVED_MATCH, "(ID_EVENT = {event_id})",
                        "event_id" to "${events?.idEvent}")
            }
            Toast.makeText(context, "Match Removed!", Toast.LENGTH_SHORT).show()
        } catch (e : SQLiteConstraintException){
            snackbar(swipeRefreshLayout, e.localizedMessage).show()
        }
    }

}

