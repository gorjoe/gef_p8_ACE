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
package com.king.mlkit.vision.app

import android.Manifest
import android.content.Intent
import android.os.Bundle
import android.provider.MediaStore
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.king.mlkit.vision.app.text.HomeActivity
import com.king.mlkit.vision.app.text.SensorDataActivity
import com.king.mlkit.vision.app.text.TextRecognitionActivity
import com.king.mlkit.vision.camera.CameraScan
import com.king.mlkit.vision.camera.util.PermissionUtils


class MainActivity : AppCompatActivity() {

    var isQRCode = false

    companion object{

        const val REQUEST_CODE_PHOTO = 1
        const val REQUEST_CODE_REQUEST_EXTERNAL_STORAGE = 2

        const val REQUEST_CODE_SCAN_CODE = 3
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if(resultCode == RESULT_OK){
            when(requestCode){
                REQUEST_CODE_PHOTO -> processPhoto(data)
                REQUEST_CODE_SCAN_CODE -> processScanResult(data)
            }
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if(requestCode == REQUEST_CODE_REQUEST_EXTERNAL_STORAGE && PermissionUtils.requestPermissionsResult(
                Manifest.permission.READ_EXTERNAL_STORAGE,
                permissions,
                grantResults
            )){
            startPickPhoto()
        }
    }

    fun getContext() = this

    private fun processScanResult(data: Intent?){
        val text = CameraScan.parseScanResult(data)
        Toast.makeText(this, text, Toast.LENGTH_SHORT).show()
    }

    private fun processPhoto(data: Intent?){
        data?.let {
            try{
                //val src = MediaStore.Images.Media.getBitmap(contentResolver, it.data)
            }catch (e: Exception){
                e.printStackTrace()
                Toast.makeText(getContext(), e.message, Toast.LENGTH_SHORT).show()
            }

        }
    }

    private fun startActivity(cls: Class<*>) {
        startActivity(Intent(this, cls))
    }

    private fun pickPhotoClicked(isQRCode: Boolean){
        this.isQRCode = isQRCode
        if(PermissionUtils.checkPermission(getContext(), Manifest.permission.READ_EXTERNAL_STORAGE)){
            startPickPhoto()
        }else{
            PermissionUtils.requestPermission(
                this,
                Manifest.permission.READ_EXTERNAL_STORAGE,
                REQUEST_CODE_REQUEST_EXTERNAL_STORAGE
            )
        }
    }

    private fun startPickPhoto(){
        val pickIntent = Intent(
            Intent.ACTION_PICK,
            MediaStore.Images.Media.EXTERNAL_CONTENT_URI
        )
        pickIntent.setDataAndType(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, "image/*")
        startActivityForResult(pickIntent, REQUEST_CODE_PHOTO)
    }


    fun onClick(v: View){
        when (v.id){
            R.id.btn12 -> startActivity(TextRecognitionActivity::class.java)
            R.id.btn13 -> startActivity(SensorDataActivity:: class.java)
            R.id.btn14 -> startActivity(HomeActivity:: class.java)
            /*{
                val i = Intent(this@MainActivity, SensorDataActivity::class.java)
                startActivity(i)
            }*/
        }
    }
}