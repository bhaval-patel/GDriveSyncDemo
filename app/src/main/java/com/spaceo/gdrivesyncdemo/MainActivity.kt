package com.spaceo.gdrivesyncdemo

import android.content.Intent
import android.os.Bundle
import android.os.Environment
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.Scopes
import com.google.android.gms.common.api.Scope
import com.google.api.client.extensions.android.http.AndroidHttp
import com.google.api.client.googleapis.extensions.android.gms.auth.GoogleAccountCredential
import com.google.api.client.json.gson.GsonFactory
import com.google.api.services.drive.Drive
import com.google.api.services.drive.DriveScopes
import com.spaceo.gdrivesyncdemo.util.DriveServiceHelper
import com.spaceo.gdrivesyncdemo.util.RunTimePermission
import kotlinx.android.synthetic.main.activity_main.*
import java.io.File
import java.io.FileOutputStream
import java.io.FileWriter
import java.lang.StringBuilder
import java.util.*
import java.util.jar.Manifest


class MainActivity : RunTimePermission() {

    private val RQ_GOOGLE_SIGN_IN = 210
    private val BACKUP_FILE = "test.txt"
    private var isRestore = false
    private var mGoogleApiClient: GoogleSignInClient? = null
    private var mDriveServiceHelper: DriveServiceHelper? = null
    private var lastUploadFileId: String? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        btnBackup.setOnClickListener {
            requestPermission(arrayOf(android.Manifest.permission.WRITE_EXTERNAL_STORAGE)) { isGranted ->
                if (isGranted) {
                    isRestore = false
                    if (mDriveServiceHelper == null)
                        googleAuth()
                    else {
                        val uploadTask = mDriveServiceHelper?.uploadFile(BACKUP_FILE, generateFile())
                        uploadTask?.addOnCompleteListener {
                            lastUploadFileId = uploadTask.result
                            println("lastUploadFileId==>$lastUploadFileId")
                            Toast.makeText(this@MainActivity, "Backup upload successfully", Toast.LENGTH_LONG).show()
                        }
                    }
                }
            }
        }

        btnRestore.setOnClickListener {
            requestPermission(arrayOf(android.Manifest.permission.WRITE_EXTERNAL_STORAGE)) { isGranted ->
                if (isGranted) {
                    isRestore = true
                    if (mDriveServiceHelper == null)
                        googleAuth()
                    else {
                        if (null != lastUploadFileId) {
                            val downloadTask = mDriveServiceHelper?.readFile(lastUploadFileId)
                            downloadTask?.addOnCompleteListener {
                                println("Name==>${downloadTask.result?.first}")
                                println("Content==>${downloadTask.result?.second}")
                                Toast.makeText(this@MainActivity, "Backup download successfully", Toast.LENGTH_LONG)
                                    .show()
                            }
                        }
                    }
                }
            }
        }
    }

    private fun googleAuth() {
        val signInOptions = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestServerAuthCode(getString(R.string.web_client_id))
            .requestScopes(Scope(Scopes.PROFILE), Scope("https://www.googleapis.com/auth/drive.file"))
            .build()
        mGoogleApiClient = GoogleSignIn.getClient(this, signInOptions)
        startActivityForResult(mGoogleApiClient!!.signInIntent, RQ_GOOGLE_SIGN_IN)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == RQ_GOOGLE_SIGN_IN && resultCode == RESULT_OK) {
            val task = GoogleSignIn.getSignedInAccountFromIntent(data)
            task.addOnSuccessListener {
                val credential = GoogleAccountCredential.usingOAuth2(
                    this,
                    Collections.singleton(DriveScopes.DRIVE_FILE)
                )
                credential.selectedAccount = it.account
                val googleDriveService = Drive.Builder(
                    AndroidHttp.newCompatibleTransport(), GsonFactory(),
                    credential
                ).setApplicationName(getString(R.string.app_name)).build()

                mDriveServiceHelper = DriveServiceHelper(googleDriveService)
                if (isRestore) {
                    if (null != lastUploadFileId) {
                        val downloadTask = mDriveServiceHelper?.readFile(lastUploadFileId)
                        downloadTask?.addOnCompleteListener {
                            println("Name==>${downloadTask.result?.first}")
                            println("Content==>${downloadTask.result?.second}")
                            Toast.makeText(this@MainActivity, "Backup download successfully", Toast.LENGTH_LONG).show()
                        }
                    }
                } else {
                    val uploadTask = mDriveServiceHelper?.uploadFile(BACKUP_FILE, generateFile())
                    uploadTask?.addOnCompleteListener {
                        lastUploadFileId = uploadTask.result
                        println("lastUploadFileId==>$lastUploadFileId")
                        Toast.makeText(this@MainActivity, "Backup upload successfully", Toast.LENGTH_LONG).show()
                    }
                }
            }
        }
    }

    private fun generateFile(): File {
        Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOCUMENTS).mkdir()
        val file = File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOCUMENTS), BACKUP_FILE)
        if (!file.exists())
            file.createNewFile()

        val data = StringBuilder()
        for (i in 0..50) {
            data.append("$i. First string is here to be written. First string is here to be written. First string is here to be written. First string is here to be written. First string is here to be written. First string is here to be written. First string is here to be written. First string is here to be written. First string is here to be written. First string is here to be written. First string is here to be written. First string is here to be written. First string is here to be written. First string is here to be written. First string is here to be written. First string is here to be written. First string is here to be written. First string is here to be written. First string is here to be written. First string is here to be written. First string is here to be written. First string is here to be written. First string is here to be written. First string is here to be written. First string is here to be written. First string is here to be written. First string is here to be written. First string is here to be written. First string is here to be written. First string is here to be written. First string is here to be written. First string is here to be written. First string is here to be written. First string is here to be written. First string is here to be written. First string is here to be written. First string is here to be written. First string is here to be written. First string is here to be written. First string is here to be written. First string is here to be written. First string is here to be written. First string is here to be written. First string is here to be written. First string is here to be written. First string is here to be written. First string is here to be written. First string is here to be written. First string is here to be written. First string is here to be written. First string is here to be written. First string is here to be written. First string is here to be written. First string is here to be written. First string is here to be written. First string is here to be written. First string is here to be written. First string is here to be written. First string is here to be written. First string is here to be written. First string is here to be written. First string is here to be written. First string is here to be written. First string is here to be written. First string is here to be written. First string is here to be written. First string is here to be written. First string is here to be written. First string is here to be written. First string is here to be written. First string is here to be written. First string is here to be written. First string is here to be written. First string is here to be written. First string is here to be written. First string is here to be written. First string is here to be written. First string is here to be written.\n\n\n")
        }
        val fileOutputStream = FileOutputStream(file, true)
        fileOutputStream.write(data.toString().toByteArray())

        return file
    }

    override fun onDestroy() {
        super.onDestroy()
        if (null != mGoogleApiClient)
            mGoogleApiClient!!.signOut()
    }

}
