package com.uni.pis.Events

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.uni.pis.R

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.os.Build
import android.os.Environment
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.zxing.BarcodeFormat
import com.google.zxing.MultiFormatWriter
import com.google.zxing.common.BitMatrix
import kotlinx.android.synthetic.main.activity_qr_code_generate.*
import java.io.ByteArrayOutputStream
import java.io.File
import java.io.FileOutputStream
private const val PERMISSION_REQUEST = 10
class QrCodeGenerate : AppCompatActivity() {
    private var permissions = arrayOf(Manifest.permission.WRITE_EXTERNAL_STORAGE)
    private lateinit var context: Context
    lateinit var userID:String
    var mFirebaseAuth = FirebaseAuth.getInstance()
    override fun onCreate(savedInstanceState: Bundle?) {
        userID=mFirebaseAuth.currentUser!!.uid
        super.onCreate(savedInstanceState)
        if(intent.hasExtra("userID")) {
            userID = intent.extras!!.get("userID").toString()
            setContentView(R.layout.activity_qr_code_generate)
            context = this
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                if (checkPermission(context, permissions)) {

                    generateQRCode(userID)
                } else {
                    requestPermissions(permissions, PERMISSION_REQUEST)
                }
            } else {
                generateQRCode(userID)
            }
        }

    }

    fun generateQRCode(userID: String){
        val bitmap = encodeAsBitmap(userID,300,300,context)
//        createImageFile(bitmap)
        iv_qr_code.setImageBitmap(bitmap)
    }


//    fun createImageFile(bitmapScaled : Bitmap?){
//        val bytes = ByteArrayOutputStream()
//        bitmapScaled?.compress(Bitmap.CompressFormat.PNG, 40, bytes)
//        val filepath = Environment.getExternalStorageDirectory().absolutePath + File.separator + "CodeAndroid.png"
//        val f = File(filepath)
//        f.createNewFile()
//        val fo = FileOutputStream(f)
//        fo.write(bytes.toByteArray())
//        fo.close()
//    }

    fun encodeAsBitmap(str: String, WIDTH: Int, HEIGHT: Int, ctx: Context): Bitmap? {
        val result: BitMatrix
        try {
            result = MultiFormatWriter().encode(str,
                BarcodeFormat.QR_CODE, WIDTH, HEIGHT, null)
        } catch (iae: IllegalArgumentException) {
            return null
        }
        val width = result.width
        val height = result.height
        val pixels = IntArray(width * height)
        for (y in 0 until height) {
            val offset = y * width
            for (x in 0 until width) {
                pixels[offset + x] = if (result.get(x, y)) -0x1000000 else -0x1
            }
        }
        val bitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888)
        bitmap.setPixels(pixels, 0, width, 0, 0, width, height)
//        createImageFile(bitmap)
        return bitmap
    }

    fun checkPermission(context: Context, permissionArray: Array<String>): Boolean {
        var allSuccess = true
        for (i in permissionArray.indices){
            if(checkCallingOrSelfPermission(permissionArray[i]) == PackageManager.PERMISSION_DENIED)
                allSuccess = false
        }
        return allSuccess
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if(requestCode == PERMISSION_REQUEST){
            var allSuccess = true
            for(i in permissions.indices){
                if(grantResults[i] == PackageManager.PERMISSION_DENIED){
                    allSuccess = false
                    var requestAgain = Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && shouldShowRequestPermissionRationale(permissions[i])
                    if(requestAgain){
                        Toast.makeText(context,"Permission denied",Toast.LENGTH_SHORT).show()
                    }else{
                        Toast.makeText(context,"Go to settings and enable the permission",Toast.LENGTH_SHORT).show()
                    }
                }
            }
            if(allSuccess)
                generateQRCode(userID)
        }
    }
}
