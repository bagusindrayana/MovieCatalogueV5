package com.bagus.moviecataloguev5

import android.content.Context
import android.content.Intent
import android.database.Cursor
import android.graphics.Bitmap
import android.os.Binder
import android.widget.RemoteViews
import android.widget.RemoteViewsService
import androidx.core.os.bundleOf
import com.bagus.moviecataloguev5.db.DatabaseContract
import com.bagus.moviecataloguev5.helper.MappingHelper
import com.bagus.moviecataloguev5.model.favorite.Favorite
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.bumptech.glide.request.target.AppWidgetTarget

internal class StackRemoteViewsFactory(private val mContext: Context) : RemoteViewsService.RemoteViewsFactory {
    private var data = ArrayList<Favorite>()
    private var cursor : Cursor? = null

    fun getMovie(){

        if(cursor != null){
            cursor?.close()
        }
        val identityToken = Binder.clearCallingIdentity()

        cursor = mContext.contentResolver?.query(DatabaseContract.FavoriteColumns.CONTENT_URI, null, "MOVIE", null, null)

        Binder.restoreCallingIdentity(identityToken)

        data = MappingHelper.mapCursorToArrayList(cursor)


    }

    override fun onCreate() {
        //getMovie()
    }

    override fun onDestroy() {

    }


    override fun onDataSetChanged() {
        getMovie()
    }


    override fun getCount(): Int = data.size

    override fun getViewAt(position: Int): RemoteViews {
        val rv = RemoteViews(mContext.packageName, R.layout.my_favorite_widget)
        val awt: AppWidgetTarget = object : AppWidgetTarget(mContext.applicationContext, R.id.imageView, rv, getItemId(position).toInt()) {
             override fun onResourceReady(
                resource: Bitmap,
                transition: com.bumptech.glide.request.transition.Transition<in Bitmap>?
            ) {
                super.onResourceReady(resource, transition)
            }
        }

        var options = RequestOptions().placeholder(R.mipmap.ic_launcher_round).error(R.mipmap.ic_launcher_round)

        Glide.with(mContext.applicationContext).asBitmap().load("https://image.tmdb.org/t/p/w342/"+data[position].poster).apply(options).into(awt)



        val extras = bundleOf(
            MyFavoriteWidget.EXTRA_ITEM to position
        )
        val fillInIntent = Intent()
        fillInIntent.putExtras(extras)
        rv.setOnClickFillInIntent(R.id.imageView, fillInIntent)
        return rv
    }

    override fun getLoadingView(): RemoteViews? = null

    override fun getViewTypeCount(): Int = 1

    override fun getItemId(i: Int): Long = 0

    override fun hasStableIds(): Boolean = false
}