package com.uni.pis

import android.app.Activity
import android.app.DatePickerDialog
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.os.Handler
import android.view.View
import android.webkit.MimeTypeMap
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import com.google.firebase.storage.StorageTask
import com.uni.pis.data.Upload
import com.uni.pis.ui.login.LoginActivity
import kotlinx.android.synthetic.main.activity_sign_up.*
import java.util.*


class SignUp : AppCompatActivity(), BackgroundWorker.MyCallback {
    private val phone_domain = arrayOf("0XX", "078", "077", "079")
    private val cities = arrayOf(
        "Choose your city", "Amman", "Az Zarqa", "Irbid", "Al Karak",
        "Jerash", "Ajloun", "Ma'an", "Tafilah", "Madaba", "Aqaba", "Balqa", "Mafraq"
    )
    var mFirebaseAuth = FirebaseAuth.getInstance()
    lateinit var mStorageRef: StorageReference
    lateinit var mDatabaseRef: DatabaseReference
    lateinit private var mUploadTask: StorageTask<*>
    lateinit var first_name: String
    lateinit var last_name: String
    lateinit var email: String
    lateinit var password: String
    lateinit var phonenumber: String
    lateinit var gender: String
    lateinit var city: String
    lateinit var date_of_birth:DatePickerDialog
    lateinit var userID:String
    lateinit var birth:String
    var index:Int =0
    lateinit var mImageUri: Uri

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_up)
        mStorageRef = FirebaseStorage.getInstance().getReference("uploads");
        mDatabaseRef = FirebaseDatabase.getInstance().getReference("uploads");
//Birthdate
        val cal = Calendar.getInstance()
        val year = cal.get(Calendar.YEAR)
        val month = cal.get(Calendar.MONTH)
        val day = cal.get(Calendar.DAY_OF_MONTH)
        btn_birthdate.setOnClickListener{
            val now=Calendar.getInstance()
            var birth:String
            val dob=DatePickerDialog(this,DatePickerDialog.OnDateSetListener { view, year, month, dayOfMonth ->
                birth=dayOfMonth.toString()+ "/"+ (month+1).toString() + "/" + year.toString()
            },
                now.get(Calendar.YEAR),now.get(Calendar.MONTH),now.get(Calendar.DAY_OF_MONTH))
            dob.show()

        }


//spinner for phone number
        val phone_adapter =
            ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, phone_domain)

        spinner_phone.adapter = phone_adapter
        spinner_phone.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                if (position == 0){
                    btn_signup.isEnabled=false
                }
                else{
                    phonenumber = phone_domain[position] + et_phonenumber.text.toString()
                    index=position
                }

            }

            override fun onNothingSelected(parent: AdapterView<*>?) {

            }
        }



// city code
        val city_adapter =
            ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, cities)
        spinner_city.adapter = city_adapter
        spinner_city.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                if (position == 0 && index==0)
                    btn_signup.isEnabled = false
                else if(index!=0){
                    city = cities[position]
                    btn_signup.isEnabled = true
                }
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {}
        }

        RG_gender.setOnCheckedChangeListener { group, checkedId ->
            if (checkedId == R.id.RB_male)
                gender = "Male"
            if (checkedId == R.id.RB_female)
                gender = "Female"
        }


        btn_uploadimage.setOnClickListener {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M)
                if (checkSelfPermission(android.Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_DENIED) {
                    //permission denied
                    val permissions = arrayOf(android.Manifest.permission.READ_EXTERNAL_STORAGE)
                    requestPermissions(permissions, PERMISSION_PICK_CODE)
                } else {
                    pick_image_from_gallery()
                }
            else {
                pick_image_from_gallery()
            }
        }
        btn_signup.setOnClickListener {
            var valid=Is_Vaild()

            if(valid){

                loading.visibility= View.VISIBLE
                mFirebaseAuth.createUserWithEmailAndPassword(email,password).addOnCompleteListener {
                    if (!it.isSuccessful) {
                        try {
                            userID=mFirebaseAuth.currentUser?.uid!!
                            var data = BackgroundWorker(this)
                            data.execute("signup",first_name,last_name,gender,phonenumber,email,birth,userID,city)
                            uploadFile()
                        }
                        catch (e: NullPointerException)
                        {
                            Toast.makeText(this,e.message,Toast.LENGTH_LONG).show()
                        }
                        Toast.makeText(this,"Successful sign up",Toast.LENGTH_LONG).show()

                    }
                }

            }
            else {
                Toast.makeText(this, "Failed you have error field or Invalid", Toast.LENGTH_LONG)
                    .show()
                btn_signup.isEnabled=true
            }






        }

    }


    private val IMAGE_PICK_CODE = 1000
    private val PERMISSION_PICK_CODE = 1001
    private fun pick_image_from_gallery() {
        val intent = Intent(Intent.ACTION_PICK)
        intent.type = "image/*"
        startActivityForResult(intent, IMAGE_PICK_CODE)


    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        when (requestCode) {
            PERMISSION_PICK_CODE -> {
                if (grantResults.size > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED)
                    pick_image_from_gallery()
            }
            else -> {
                Toast.makeText(this, "Permission Denied", Toast.LENGTH_LONG).show()
            }
        }

    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == Activity.RESULT_OK && requestCode == IMAGE_PICK_CODE)
            profile_img.setImageURI(data?.data)
        mImageUri=data?.data!!

    }

    fun Is_Vaild(): Boolean {

        var valid =true

        // first name
        if (et_firstname.text.isEmpty()){
            et_firstname.error = "Empty field not allowed ... "
            valid=false
        }
        if (et_firstname.text.toString().trim().length > 20){
            et_firstname.error = "Name too long"
            valid=false
        }
        else
            first_name = et_firstname.text.toString()


        //last name
        if (et_lastname.text.isEmpty()){
            et_lastname.error = "Empty field not allowed ... "
            valid=false
        }
        if (et_lastname.text.toString().trim().length > 20){
            et_lastname.error = "Name too long"
            valid=false
        }
        else
            last_name = et_lastname.text.toString()


        //email
        if (et_email.text.isEmpty()){
            et_email.error = "Empty field not allowed ..."
            valid=false}
        if (!android.util.Patterns.EMAIL_ADDRESS.matcher(et_email.text).matches()){
            et_email.error = "Invalid Email Address ..."
            valid=false}
        else
            email = et_email.text.toString()


        //password
        if (et_password.text.isEmpty()){
            et_password.error = "Empty field not allowed ..."
            valid=false}
        if (et_password.text.trim().length < 5){
            et_password.error = "Short Password"
            valid=false}

        //repassword
        if (et_repassword.text.isEmpty()){
            et_repassword.error = "Empty field not allowed ..."
            valid=false}
        if (et_repassword.text.trim().length < 5){
            et_repassword.error = "Short Password"
            valid=false}

        if (!et_repassword.text.toString().equals(et_password.text.toString())){
            et_repassword.error = "MissMatch Password"
            valid=false}
        else
            password = et_password.text.toString()

        //phone number
        if (et_phonenumber.text.isEmpty()){
            et_phonenumber.error = "Empty field not allowed ... "
            valid=false}
        if (et_phonenumber.length() != 7) {
            et_phonenumber.error = "Invalid phone number ..."
            valid=false
        }
        else
            phonenumber = phone_domain[index] + et_phonenumber.text.toString()

        return valid
    }

    override fun onResult(result: String?) {
        loading.visibility = View.GONE
        intent = Intent(this, LoginActivity::class.java)
        startActivity(intent)
        finish()
    }
    private fun getFileExtension(uri: Uri): String? {
        val cR = contentResolver
        val mime = MimeTypeMap.getSingleton()
        return mime.getExtensionFromMimeType(cR.getType(uri))
    }

    private fun uploadFile() {
        if (mImageUri != null) {
            var fileReference = mStorageRef.child(System.currentTimeMillis().toString() + "." + getFileExtension(mImageUri))
            mUploadTask = fileReference.putFile(mImageUri)
                .addOnSuccessListener { taskSnapshot ->
                    val handler = Handler()
                    handler.postDelayed(Runnable { loading.setProgress(0) }, 500)
                    Toast.makeText(this, "Upload successful", Toast.LENGTH_LONG)
                        .show()
                    val upload = Upload(
                        first_name+last_name,
                        taskSnapshot.toString()
                    )
                    val uploadId = mDatabaseRef.push().key
                    mDatabaseRef.child(uploadId.toString()).setValue(upload)
                }
                .addOnFailureListener { e ->
                    Toast.makeText(
                        this,
                        e.message,
                        Toast.LENGTH_SHORT
                    ).show()
                }
                .addOnProgressListener { taskSnapshot ->
                    val progress =
                        100.0 * taskSnapshot.bytesTransferred / taskSnapshot.totalByteCount
                    loading.setProgress(progress.toInt())
                }
        } else {
            Toast.makeText(this, "No file selected", Toast.LENGTH_SHORT).show()
        }
    }

}







