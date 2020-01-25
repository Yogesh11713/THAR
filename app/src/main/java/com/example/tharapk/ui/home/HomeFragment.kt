package com.example.tharapk.ui.home

import android.app.ActivityOptions
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.annotation.RequiresApi
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager.widget.ViewPager
import com.example.tharapk.ui.event.EventDetailActivity
import com.example.tharapk.R
import com.example.tharapk.adapters.EventAdapter
import com.example.tharapk.adapters.EventItemClickListener
import com.example.tharapk.adapters.SliderPagerAdapter
import com.example.tharapk.models.Event
import com.example.tharapk.models.Slide
import com.google.android.material.tabs.TabLayout

import java.util.*

class HomeFragment : Fragment(), EventItemClickListener {

    private lateinit var homeViewModel: HomeViewModel


    private var lstSlides: MutableList<Slide>? = null
    private var sliderpager: ViewPager? = null
    private var indicator: TabLayout? = null
    private var MoviesRV: RecyclerView? = null


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        homeViewModel =
            ViewModelProviders.of(this).get(HomeViewModel::class.java)
        val root = inflater.inflate(R.layout.fragment_home, container, false)

        sliderpager = root.findViewById(R.id.slider_pager)
        indicator = root.findViewById(R.id.indicator)
        MoviesRV = root.findViewById(R.id.Rv_events)

        setupSlider()

        return root
    }

   private fun setupSlider(){

        // ITEMS IN THE SLIDES
        lstSlides = ArrayList()
        (lstSlides as ArrayList<Slide>).add(Slide(R.drawable.thar_logo, "Slide Title \nmore text here"))
        (lstSlides as ArrayList<Slide>).add(Slide(R.drawable.login_background, "Slide Title \nmore text here"))

        val adapter = SliderPagerAdapter(context, lstSlides)
        sliderpager!!.adapter = adapter

        // SETUP TIMER FOR SLIDER
        val timer = Timer()
        timer.scheduleAtFixedRate(SliderTimer(), 4000, 6000)
        indicator!!.setupWithViewPager(sliderpager, true)

        // EVENT RECYCLER VIEW SETUP
        val lstMovies = ArrayList<Event>()
        lstMovies.add(
            Event(
                "Moana",
                R.drawable.login_background,
                R.drawable.thar_logo
            )
        )
       lstMovies.add(
           Event(
               "Moana",
               R.drawable.login_background,
               R.drawable.thar_logo
           )
       )
       lstMovies.add(
           Event(
               "Moana",
               R.drawable.login_background,
               R.drawable.thar_logo
           )
       )
       lstMovies.add(
           Event(
               "Moana",
               R.drawable.login_background,
               R.drawable.thar_logo
           )
       )


        val movieAdapter = EventAdapter(context, lstMovies,this)
        MoviesRV!!.adapter = movieAdapter
        MoviesRV!!.layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)

    }

    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
    override fun onMovieClick(movie: Event, movieImageView: ImageView) {
        // here we send movie information to detail activity
        // also we ll create the transition animation between the two activity

        val intent = Intent(context, EventDetailActivity::class.java)
        // send movie information to detailActivity
        intent.putExtra("title", movie.title)
        intent.putExtra("imgURL", movie.thumbnail)
        intent.putExtra("imgCover",movie.thumbnail)

        // lets create the animation
        val options = ActivityOptions.makeSceneTransitionAnimation(
            activity,
            movieImageView, "sharedName"
        )

        startActivity(intent, options.toBundle())


        // i l make a simple test to see if the click works

        //Toast.makeText(this, "item clicked : " + movie.title, Toast.LENGTH_LONG).show()
        // it works great

    }


    internal inner class SliderTimer : TimerTask() {


        override fun run() {

            activity?.runOnUiThread(Runnable {
                if (sliderpager!!.currentItem  < lstSlides!!.size  - 1) {
                    sliderpager!!.currentItem = sliderpager!!.currentItem + 1
                } else
                sliderpager!!.currentItem = 0
            })

        }
    }

}