package com.bagus.moviecataloguev5


import android.app.SearchManager
import android.content.Context
import android.content.Intent
import android.database.ContentObserver
import android.os.Bundle
import android.os.Handler
import android.os.HandlerThread
import android.provider.Settings
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.widget.SearchView
import androidx.appcompat.app.AppCompatActivity
import androidx.viewpager.widget.ViewPager
import androidx.viewpager.widget.ViewPager.OnPageChangeListener
import com.bagus.moviecataloguev5.db.DatabaseContract.FavoriteColumns.Companion.CONTENT_URI
import com.bagus.moviecataloguev5.db.DatabaseHelper
import com.google.android.material.bottomnavigation.BottomNavigationView



class MainActivity : AppCompatActivity() {
    private lateinit var dataBaseHelper: DatabaseHelper
    var prevMenuItem: MenuItem? = null
    lateinit var navView: BottomNavigationView
    lateinit var viewPager : ViewPager


    init {
        dataBaseHelper = DatabaseHelper(this)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        navView = findViewById(R.id.nav_view)
        viewPager = findViewById(R.id.viewPager)

        val adapter = MainPageAdapter(supportFragmentManager)
        viewPager.setAdapter(adapter)



        navView.setOnNavigationItemSelectedListener({ item ->
            when (item.itemId) {
                R.id.navigation_movie -> viewPager.currentItem = 0
                R.id.navigation_tv -> viewPager.currentItem = 1
                R.id.navigation_favorite -> viewPager.currentItem = 2
            }
            false
        })

        viewPager.addOnPageChangeListener(object : OnPageChangeListener {
            override fun onPageScrolled(
                position: Int,
                positionOffset: Float,
                positionOffsetPixels: Int
            ) {
            }

            override fun onPageSelected(position: Int) {
                if (prevMenuItem != null) prevMenuItem!!.setChecked(false) else navView.getMenu().getItem(
                    0
                ).setChecked(false)
                navView.getMenu().getItem(position).setChecked(true)
                prevMenuItem = navView.getMenu().getItem(position)
            }

            override fun onPageScrollStateChanged(state: Int) {}
        })

        val handlerThread = HandlerThread("DataObserver")
        handlerThread.start()
        val handler = Handler(handlerThread.looper)
        val myObserver = object : ContentObserver(handler) {
            override fun onChange(self: Boolean) {
//                loadNotesAsync()
            }
        }

        contentResolver.registerContentObserver(CONTENT_URI, true, myObserver)


    }


    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.main_menu, menu)

        val searchManager = getSystemService(Context.SEARCH_SERVICE) as SearchManager
        val searchView = menu.findItem(R.id.search).actionView as SearchView

        searchView.setSearchableInfo(searchManager.getSearchableInfo(componentName))
        searchView.queryHint = resources.getString(R.string.search_title)
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {

            override fun onQueryTextSubmit(query: String): Boolean {
                if(viewPager.currentItem == 0){
                    val searchIntent = Intent(this@MainActivity, SearchMovieActivity::class.java)
                    searchIntent.putExtra(SearchMovieActivity.QUERY, query)
                    startActivity(searchIntent)
                    return true
                } else if(viewPager.currentItem == 1){
                    val searchIntent = Intent(this@MainActivity, SearchTvActivity::class.java)
                    searchIntent.putExtra(SearchMovieActivity.QUERY, query)
                    startActivity(searchIntent)
                    return true
                } else {
                    return false
                }

            }


            override fun onQueryTextChange(newText: String): Boolean {
                return false
            }
        })
        return super.onCreateOptionsMenu(menu)
    }


    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == R.id.action_change_language) {
            val mIntent = Intent(Settings.ACTION_LOCALE_SETTINGS)
            startActivity(mIntent)
        }

        if (item.itemId == R.id.action_change_settings) {
            val mIntent = Intent(this@MainActivity,
                SettingNotifications::class.java)
            startActivity(mIntent)
        }

        return super.onOptionsItemSelected(item)
    }



}
