package com.bagus.moviecataloguev5

import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.drawable.Drawable
import android.widget.RemoteViews
import android.widget.RemoteViewsService
import androidx.core.os.bundleOf
import com.bagus.moviecataloguev5.db.DatabaseContract
import com.bagus.moviecataloguev5.db.FavoriteProvider.Companion.rom
import com.bagus.moviecataloguev5.helper.MappingHelper
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.bumptech.glide.request.target.CustomTarget

internal class StackRemoteViewsFactory(private val mContext: Context) : RemoteViewsService.RemoteViewsFactory {
    private val mWidgetItems = ArrayList<Bitmap>()

    fun getMovie(){
        val cursor = mContext.contentResolver?.query(DatabaseContract.FavoriteColumns.CONTENT_URI, null, "MOVIE", null, null)

        val data = MappingHelper.mapCursorToArrayList(cursor)

        if (data != null) {
            for(l in data){
                val options = RequestOptions()
                    .centerCrop()
                    .placeholder(R.mipmap.ic_launcher_round)
                    .error(R.mipmap.ic_launcher_round)
                Glide.with(mContext)
                    .asBitmap()
                    .load("https://image.tmdb.org/t/p/w342/"+l.poster)
                    .apply(options)
                    .into(object : CustomTarget<Bitmap>(){
                        override fun onLoadCleared(placeholder: Drawable?) {

                        }

                        override fun onResourceReady(
                            resource: Bitmap,
                            transition: com.bumptech.glide.request.transition.Transition<in Bitmap>?
                        ) {
                            mWidgetItems.add(resource)

                        }

                    })
            }
        }
        rom?.close()
    }

    override fun onCreate() {

    }

    override fun onDestroy() {

    }


    override fun onDataSetChanged() {
        getMovie()
    }


    override fun getCount(): Int = mWidgetItems.size

    override fun getViewAt(position: Int): RemoteViews {
        val rv = RemoteViews(mContext.packageName, R.layout.widget_item)
        rv.setImageViewBitmap(R.id.imageView, mWidgetItems[position])

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