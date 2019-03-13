package com.spaceo.gdrivesyncdemo.util

import android.content.pm.PackageManager
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat

open class RunTimePermission : AppCompatActivity() {

    private var callback: ((Boolean) -> Unit)? = null

    fun requestPermission(permissions: Array<String>, callback: (isGranted: Boolean) -> Unit) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            var granted = true
            for (permission in permissions) {
                if (ContextCompat.checkSelfPermission(this, permission) != PackageManager.PERMISSION_GRANTED) {
                    granted = false
                    break
                }
            }
            if (granted) {
                callback(true)
            } else {
                this.callback = callback
                requestPermissions(permissions, Integer.MAX_VALUE)
            }
        } else callback(true)

    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == Integer.MAX_VALUE) {
            var granted = true
            for (i in 0 until grantResults.size) {
                if (grantResults[i] != PackageManager.PERMISSION_GRANTED) {
                    granted = false
                    break
                }
            }
            if (granted)
                callback?.invoke(true)
            else onDenied()
        }
    }

    private fun onDenied() {
        callback?.invoke(false)
    }

}