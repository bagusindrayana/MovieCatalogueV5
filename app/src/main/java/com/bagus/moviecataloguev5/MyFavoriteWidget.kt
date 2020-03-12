package com.bagus.moviecataloguev5

import android.app.PendingIntent
import android.appwidget.AppWidgetManager
import android.appwidget.AppWidgetProvider
import android.content.Context
import android.content.Intent
import android.database.Cursor
import android.graphics.Bitmap
import android.os.Binder
import android.widget.RemoteViews
import android.widget.Toast
import androidx.core.net.toUri
import com.bagus.moviecataloguev5.db.DatabaseContract
import com.bagus.moviecataloguev5.helper.MappingHelper
import com.bagus.moviecataloguev5.model.favorite.Favorite
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.bumptech.glide.request.target.AppWidgetTarget


class MyFavoriteWidget : AppWidgetProvider() {

    companion object {
        private var data = ArrayList<Favorite>()
        private var cursor : Cursor? = null
        private const val TOAST_ACTION = "com.bagus.moviecataloguev5.TOAST_ACTION"
        const val EXTRA_ITEM = "com.bagus.moviecataloguev5.EXTRA_ITEM"

        private fun updateAppWidget(context: Context, appWidgetManager: AppWidgetManager, appWidgetId: Int) {
            val intent = Intent(context, StackWidgetService::class.java)
            intent.putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, appWidgetId)
            intent.data = intent.toUri(Intent.URI_INTENT_SCHEME).toUri()

            val views = RemoteViews(context.packageName, R.layout.my_favorite_widget)
            views.setRemoteAdapter(R.id.stack_view, intent)
            views.setEmptyView(R.id.stack_view, R.id.empty_view)

            val toastIntent = Intent(context, MyFavoriteWidget::class.java)
            toastIntent.action = TOAST_ACTION
            toastIntent.putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, appWidgetId)
            intent.data = intent.toUri(Intent.URI_INTENT_SCHEME).toUri()
            val toastPendingIntent = PendingIntent.getBroadcast(context, 0, toastIntent, PendingIntent.FLAG_UPDATE_CURRENT)
            views.setPendingIntentTemplate(R.id.stack_view, toastPendingIntent)

            getMovie(context)

            if(data != null){
                for(l in data){

                    val awt: AppWidgetTarget = object : AppWidgetTarget(context.applicationContext, R.id.imageView, views, appWidgetId) {
                        override fun onResourceReady(
                            resource: Bitmap,
                            transition: com.bumptech.glide.request.transition.Transition<in Bitmap>?
                        ) {
                            super.onResourceReady(resource, transition)
                        }
                    }

                    var options = RequestOptions().placeholder(R.mipmap.ic_launcher_round).error(R.mipmap.ic_launcher_round)

                    Glide.with(context.applicationContext).asBitmap().load("https://image.tmdb.org/t/p/w342/"+l.poster).apply(options).into(awt)
                }
            }

            appWidgetManager.updateAppWidget(appWidgetId, views)
        }

        fun getMovie(mContext : Context){

            if(cursor != null){
                cursor?.close()
            }
            val identityToken = Binder.clearCallingIdentity()

            cursor = mContext.contentResolver?.query(DatabaseContract.FavoriteColumns.CONTENT_URI, null, "MOVIE", null, null)

            Binder.restoreCallingIdentity(identityToken)

            data = MappingHelper.mapCursorToArrayList(cursor)


        }
    }



    override fun onUpdate(context: Context, appWidgetManager: AppWidgetManager, appWidgetIds: IntArray) {
        // There may be multiple widgets active, so update all of them
        for (appWidgetId in appWidgetIds) {


            updateAppWidget(context, appWidgetManager, appWidgetId)


        }
    }

    override fun onReceive(context: Context, intent: Intent) {
        super.onReceive(context, intent)
        if (intent.action != null) {
            if (intent.action == TOAST_ACTION) {
                val viewIndex = intent.getIntExtra(EXTRA_ITEM, 0)
                Toast.makeText(context, "Touched view $viewIndex", Toast.LENGTH_SHORT).show()
            }
        }
    }
}
