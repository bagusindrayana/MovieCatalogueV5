package com.bagus.moviecataloguev5

import android.app.AlarmManager
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.media.RingtoneManager
import android.os.Build
import android.util.Log
import android.widget.Toast
import androidx.core.app.NotificationCompat
import androidx.core.content.ContextCompat
import com.bagus.moviecataloguev5.api.Client
import com.bagus.moviecataloguev5.api.Route
import com.bagus.moviecataloguev5.model.release.MovieReleaseResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*

class GetDailyRemindderReceiver : BroadcastReceiver() {

    companion object {

        private const val ID_REMINDER = 101
        private const val ID_RELEASE = 102
        const val EXTRA_MESSAGE = "message"
        const val EXTRA_TYPE = "DAILY"
    }

    override fun onReceive(context: Context, intent: Intent) {
        val c: Date = Calendar.getInstance().getTime()
        val df = SimpleDateFormat("yyyy-MM-d")
        val formattedDate: String = df.format(c)
        val type = intent.getStringExtra(EXTRA_TYPE)
        val message = intent.getStringExtra(EXTRA_MESSAGE)

        if(type == "REMINDER"){
            showNotification(context, "We miss you", message, ID_REMINDER)
        } else {
            releaseMovie(formattedDate,formattedDate,context)
        }
        Log.i("TYPE",type)

    }

    fun setRepeatingAlarm(context: Context, time: String, message: String,type:String) {
        if (isDateInvalid(time, "HH:mm")) return
        val alarmManager = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager
        val intent = Intent(context, GetDailyRemindderReceiver::class.java)
        intent.putExtra(EXTRA_MESSAGE, message)
        intent.putExtra(EXTRA_TYPE, type)
        val timeArray = time.split(":".toRegex()).dropLastWhile { it.isEmpty() }.toTypedArray()
        val calendar = Calendar.getInstance()
        calendar.set(Calendar.HOUR_OF_DAY, Integer.parseInt(timeArray[0]))
        calendar.set(Calendar.MINUTE, Integer.parseInt(timeArray[1]))
        calendar.set(Calendar.SECOND, 0)
        val idnotif = if(type == "REMINDER") ID_REMINDER else ID_RELEASE
        val pendingIntent = PendingIntent.getBroadcast(context,idnotif , intent, 0)
        alarmManager.setInexactRepeating(AlarmManager.RTC_WAKEUP, calendar.timeInMillis, AlarmManager.INTERVAL_DAY, pendingIntent)
        Toast.makeText(context, "Daily reminder set up", Toast.LENGTH_SHORT).show()

    }

    fun cancelAlarm(context: Context, type: String) {
        val alarmManager = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager
        val intent = Intent(context, GetDailyRemindderReceiver::class.java)
        val requestCode = if (type == "REMINDER") ID_REMINDER else ID_RELEASE
        val pendingIntent = PendingIntent.getBroadcast(context, requestCode, intent, 0)
        pendingIntent.cancel()
        alarmManager.cancel(pendingIntent)
        Toast.makeText(context, "Notifikasi dibatalkan", Toast.LENGTH_SHORT).show()
    }

    private fun isDateInvalid(date: String, format: String): Boolean {
        return try {
            val df = SimpleDateFormat(format, Locale.getDefault())
            df.isLenient = false
            df.parse(date)
            false
        } catch (e: ParseException) {
            true
        }
    }

    private fun releaseMovie(query1 : String,query2 : String?,context : Context) {
        Log.i("TANGGAL",query1+" - "+query2)
        val Client = Client()
        val apiRoute: Route = Client.getClient()!!.create(Route::class.java)
        apiRoute.getMovieRelease(query1,query2).enqueue(object :
            Callback<MovieReleaseResponse> {
            override fun onResponse(call: Call<MovieReleaseResponse>, response: Response<MovieReleaseResponse>) {

                if(response.code() == 200) {

                    val title = "Film Yang Rilis Hari Ini "
                    var message = ""
                    val notifId = ID_RELEASE
                    var no = 0
                    for(f in response.body().results){
                        if(no < 5){
                            message += f.title+", "
                            showNotification(context, f.title, f.title+" Rilis Hari Ini", notifId+f.id)
                            no++
                        }

                    }

                    showNotification(context, title, message, notifId)
                }

                Log.i("RELEASE",response.raw().request().url().toString())
            }
            override fun onFailure(call: Call<MovieReleaseResponse>, t: Throwable){
                Log.e("Ops", t.message)



            }
        })


    }

    private fun showNotification(context: Context, title: String, message: String, notifId: Int) {
        val CHANNEL_ID = "notifikasi_channel_mcv5"
        val CHANNEL_NAME = "Notifikasi channel"
        val notificationManagerCompat = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        val alarmSound = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION)
        val builder = NotificationCompat.Builder(context, CHANNEL_ID)
            .setContentTitle(title)
            .setSmallIcon(R.drawable.ic_favorite_red)
            .setContentText(message)
            .setColor(ContextCompat.getColor(context, android.R.color.black))
            .setVibrate(longArrayOf(1000, 1000, 1000, 1000, 1000))
            .setSound(alarmSound)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(CHANNEL_ID,
                CHANNEL_NAME,
                NotificationManager.IMPORTANCE_DEFAULT)
            channel.enableVibration(true)
            channel.vibrationPattern = longArrayOf(1000, 1000, 1000, 1000, 1000)
            builder.setChannelId(CHANNEL_ID)
            notificationManagerCompat.createNotificationChannel(channel)
        }
        val notification = builder.build()
        notificationManagerCompat.notify(notifId, notification)
    }
}