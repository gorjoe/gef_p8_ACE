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

import android.graphics.Color
import android.os.Bundle
import android.os.Looper
import android.widget.ToggleButton
import androidx.appcompat.app.AppCompatActivity
import com.king.mlkit.vision.app.R
import java.net.URL

/**
 * @author <a href="mailto:jenly1314@gmail.com">Jenly</a>
 */
class HomeActivity() : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        val btn_light: ToggleButton = findViewById<ToggleButton>(R.id.btn_light)
        val btn_door: ToggleButton = findViewById<ToggleButton>(R.id.btn_door)

        System.setProperty("http.keepAlive", "false");

        btn_light.setOnCheckedChangeListener{ _, isChecked  ->

            val thread: Thread = object : Thread() {
                override fun run() {
                    Looper.prepare()
                    for (i in 1..1) {
                        try {
                            if (isChecked) {
                                // The toggle is enabled
                                btn_light.setBackgroundColor(Color.GREEN)
                                get("light", 1)

                            } else {
                                // The toggle is disabled
                                btn_light.setBackgroundColor(Color.RED)
                                get("light", 0)
                            }

                        } catch (IOException: Exception){
                        }

                        this.interrupt()
                    }
                }
            }
            thread.start()
        }

        btn_door.setOnCheckedChangeListener{ _, isChecked  ->

            val thread: Thread = object : Thread() {
                override fun run() {
                    Looper.prepare()
                    for (i in 1..1) {
                        try {
                            if (isChecked) {
                                // The toggle is enabled
                                btn_door.setBackgroundColor(Color.GREEN)
                                get("door", 1)

                            } else {
                                // The toggle is disabled
                                btn_door.setBackgroundColor(Color.RED)
                                get("door", 0)
                            }

                        } catch (IOException: Exception){
                        }

                        this.interrupt()
                    }
                }
            }
            thread.start()
        }

    }

    fun get(a: kotlin.String, b: Int): String {
        //Log.d("TEMP", a)
        //Log.d("WATER", b.toString())
        val url = "https://redstone.thocraft.today/home/create_data.php?arg1=" + a + "&arg2=" + b

        val feedback = URL(url).readText()

        return feedback.toString()
    }
}