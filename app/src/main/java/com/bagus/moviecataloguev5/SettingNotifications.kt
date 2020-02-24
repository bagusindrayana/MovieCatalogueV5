package com.bagus.moviecataloguev5

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Switch
import android.widget.Toast
import com.bagus.moviecataloguev5.R

class SettingNotifications : AppCompatActivity() {
    private lateinit var alarmReceiver: GetDailyRemindderReceiver
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_setting_notifications)

        alarmReceiver = GetDailyRemindderReceiver()

        val sp = SharedPrefManager(this)

        findViewById<Switch>(R.id.switch1).isChecked = sp.getSPBoolean("REMINDER")
        findViewById<Switch>(R.id.switch2).isChecked = sp.getSPBoolean("RELEASE")

        findViewById<Switch>(R.id.switch1).setOnCheckedChangeListener({ _ , isChecked ->
            sp.saveSPBoolean("REMINDER",isChecked)
            if(isChecked){
                alarmReceiver.setRepeatingAlarm(this, "07:00", resources.getString(R.string.daily_reminder_message),"REMINDER")
            } else {
                alarmReceiver.cancelAlarm(this,"REMINDER")
            }
        })

        findViewById<Switch>(R.id.switch2).setOnCheckedChangeListener({ _ , isChecked ->
            sp.saveSPBoolean("RELEASE",isChecked)
            if(isChecked){
                alarmReceiver.setRepeatingAlarm(this, "08:00", resources.getString(R.string.daily_reminder_message),"RELEASE")
            } else {
                alarmReceiver.cancelAlarm(this,"RELEASE")
            }
        })

    }
}
