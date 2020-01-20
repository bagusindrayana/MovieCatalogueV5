package com.bagus.moviecataloguev4.ui.tv

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider

import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout

import com.bagus.moviecataloguev4.R
import com.bagus.moviecataloguev4.model.tv.TvGenres
import com.bagus.moviecataloguev4.ui.tv.category.CardViewTvCategorysAdapter
import kotlinx.android.synthetic.main.fragment_tv.*


class TvFragment : Fragment() {
    var mProgressBar: ProgressBar? = null
    private var rvTvs: RecyclerView? = null
    lateinit var tvViewModel: TvViewModel
    var swipeLayout: SwipeRefreshLayout? = null



    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_tv, container, false)
        super.onViewCreated(view, savedInstanceState)
        getActivity()?.setTitle(R.string.title_movie);
        mProgressBar = view.findViewById(R.id.progressBar)
        rvTvs = view.findViewById(R.id.rv_tvs)
        rvTvs?.setHasFixedSize(true)
        rvTvs?.layoutManager = LinearLayoutManager(this.activity)

        tvViewModel = ViewModelProvider(this).get(TvViewModel::class.java)
        if (savedInstanceState == null) {
            tvViewModel.getGenre()
        }
        showRecyclerCardView()
        swipeLayout = view.findViewById(R.id.swipe_container) as? SwipeRefreshLayout
        swipeLayout?.setOnRefreshListener {
            tvViewModel.getGenre()
            swipeLayout?.setRefreshing(false);
        }

        return view
    }


    fun showRecyclerCardView() {
        if(::tvViewModel.isInitialized) {
            showLoading(true)
            tvViewModel.getCategorys().observe(this, Observer { items ->
                if (items != null) {

                    rvTvs!!.adapter = CardViewTvCategorysAdapter(
                        items
                    )

                }
            })
        }
    }

    private fun showLoading(state: Boolean) {
        if (state) {
            mProgressBar?.visibility  = View.VISIBLE
        } else {
            mProgressBar?.visibility = View.GONE
        }
    }





}