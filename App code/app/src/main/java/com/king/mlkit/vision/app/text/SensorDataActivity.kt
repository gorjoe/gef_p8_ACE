/*
 * Copyright (C) Jenly, MLKit Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.king.mlkit.vision.app.text

import android.os.Bundle
import android.os.Looper
import android.widget.Switch
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.king.mlkit.vision.app.R
import org.json.JSONObject
import java.net.URL
import java.text.SimpleDateFormat
import java.util.*


/**
 * @author <a href="mailto:jenly1314@gmail.com">Jenly</a>
 */
class SensorDataActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sensor)

        val sensor: TextView = findViewById<TextView>(R.id.sensor)
        val switch: Switch = findViewById<Switch>(R.id.switch1)
        val update: TextView = findViewById<Switch>(R.id.update)

        switch.setOnCheckedChangeListener { _, _ ->
            // do whatever you need to do when the switch is toggled here
            val thread: Thread = object : Thread() {
                override fun run() {
                    Looper.prepare()
                    while (switch.isChecked) {
                        val result = get();

                        try {
                            val jObject = JSONObject(result)
                            val timestamp = jObject.getString("timestamp")
                            val temp = jObject.getDouble("temp")
                            var water = jObject.getString("water")

                            val ntimestamp = timestamp.toLong() * 1000

                            if (water == "1"){
                                water = "wet";
                            }else{
                                water = "dry";
                            }

                            update.text = "Last Update: " + convertLongToTime(ntimestamp);
                            sensor.text = "Temp: " + temp.toString() + "\nBed: " + water;


                        } catch (JSONException: Exception){
                        }

                        sleep(200)
                    }

                }
            }

            thread.start()
            if (!switch.isChecked) {
                thread.interrupt()
            }
        }
    }

    fun get(): String {
        val url = "https://redstone.thocraft.today/get_data.php"

        val feedback = URL(url).readText()

        return feedback.toString()
    }

    fun convertLongToTime(time: Long): String {
        val date = Date(time)
        val format = SimpleDateFormat("yyyy.MM.dd HH:mm")
        format.setTimeZone(TimeZone.getTimeZone("Asia/Hong_Kong"));
        return format.format(date)
    }
}