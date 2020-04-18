package com.uni.pis.ui

import android.Manifest
import android.app.DatePickerDialog
import android.content.Intent
import android.graphics.Color
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.service.autofill.UserData
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.webkit.MimeTypeMap
import android.widget.*
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import com.google.firebase.storage.StorageTask
import com.karumi.dexter.Dexter
import com.karumi.dexter.PermissionToken
import com.karumi.dexter.listener.PermissionDeniedResponse
import com.karumi.dexter.listener.PermissionGrantedResponse
import com.karumi.dexter.listener.PermissionRequest
import com.karumi.dexter.listener.single.PermissionListener
import com.uni.pis.BackgroundWorker
import com.uni.pis.Data.UserData.UserDataGoogle
import com.uni.pis.Data.UserData.userData
import com.uni.pis.R
import com.uni.pis.ui.login.LoginActivity
import kotlinx.android.synthetic.main.activity_sign_up.*
import java.util.*

class SignUp : AppCompatActivity(), BackgroundWorker.MyCallback {
    private val phone_domain = arrayOf("0XX", "078", "077", "079")
    private val cities = arrayOf(
        "Choose your city", "Amman", "Az Zarqa", "Irbid", "Al Karak",
        "Jerash", "Ajloun", "Ma'an", "Tafilah", "Madaba", "Aqaba", "Balqa", "Mafraq"
    )
    private val IMAGE_PICK_CODE = 1000
    var mFirebaseAuth = FirebaseAuth.getInstance()
    lateinit var mStorageRef: StorageReference
    lateinit var mDatabaseRef: DatabaseReference
    private lateinit var mUploadTask: StorageTask<*>
    var first_name: String=""
    var last_name: String=""
    var email: String=""
    var password: String=""
    var phonenumber: String=""
    var gender: String=""
    var city: String=""
    var birth: String=""
    lateinit var imageStoragelink: String
    lateinit var userID:String
    var indexPhoneNum:Int =0
    var indexCity:Int =0
    lateinit var mImageUri: Uri
    lateinit var  UserData: UserDataGoogle

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_up)
        if(intent.hasExtra("Bundle")){
            val bundle = intent.getBundleExtra("Bundle")
            val Userdata = bundle.getParcelable<UserDataGoogle>("userinformation")
            if (Userdata != null) {
                UserData = Userdata
                Toast.makeText(this, UserData.first_name, Toast.LENGTH_LONG).show()
                Toast.makeText(this, UserData.email, Toast.LENGTH_LONG).show()
            }
        }



        mStorageRef = FirebaseStorage.getInstance().getReference("uploads")
        mDatabaseRef = FirebaseDatabase.getInstance().getReference("uploads")

        //Birthdate
        btn_birthdate.setOnClickListener {
            val now = Calendar.getInstance()
            val dob = DatePickerDialog(
                this, DatePickerDialog.OnDateSetListener { view, year, month, dayOfMonth ->
                    birth = dayOfMonth.toString() + "-" + (month + 1).toString() + "-" + year.toString()
                    tv_date.text = birth
                },
                now.get(Calendar.YEAR), now.get(Calendar.MONTH), now.get(Calendar.DAY_OF_MONTH)
            )
            dob.show()

        }
        var radiobuttonmale:RadioButton=findViewById(R.id.RB_male)
        var radiobuttonfemale:RadioButton=findViewById(R.id.RB_female)

        //spinner for phone number
        val phone_adapter =
            ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, phone_domain)
        spinner_phone.adapter = phone_adapter
        spinner_phone.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                if (position != 0) {
                    phonenumber = phone_domain[position] + et_phonenumber.text.toString()
                    indexPhoneNum = position
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
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                if (position != 0) {
                    city = cities[position]
                    indexCity = position
                }
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {

            }
        }
        RG_gender.setOnCheckedChangeListener { group, checkedId ->
            if (checkedId == R.id.RB_male)
                gender = "Male"
            if (checkedId == R.id.RB_female)
                gender = "Female"
        }
        btn_uploadimage.setOnClickListener {
            Dexter.withActivity(this)
                .withPermission(Manifest.permission.READ_EXTERNAL_STORAGE)
                .withListener(object : PermissionListener {
                    override fun onPermissionGranted(response: PermissionGrantedResponse) {
                        val intent = Intent(Intent.ACTION_PICK)
                        intent.type = "image/*"
                        startActivityForResult(intent, IMAGE_PICK_CODE)
                    }

                    override fun onPermissionDenied(response: PermissionDeniedResponse) {
                        response.requestedPermission
                    }

                    override fun onPermissionRationaleShouldBeShown(permission: PermissionRequest, token: PermissionToken) {
                        token.continuePermissionRequest()
                    }
                })
                .check()
        }
        et_firstname.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                if (s!!.length > 15) {
                    et_firstname.error = "Too Long Input ..."
                    first_name = ""
                }
                if (s.isEmpty()) {
                    et_firstname.error = "Empty Field Not Allowed ..."
                    first_name = ""
                } else
                    first_name = et_firstname.text.toString()
            }
        })
        et_lastname.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                if (s!!.length > 15) {
                    et_lastname.error = "Too Long Input ..."
                    last_name = ""
                }
                if (s.isEmpty()) {
                    et_lastname.error = "Empty Field Not Allowed ..."
                    last_name = ""
                } else
                    last_name = et_lastname.text.toString()
            }
        })
        et_email.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {}
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                et_email.onFocusChangeListener = View.OnFocusChangeListener { _, _ ->
                    if (!android.util.Patterns.EMAIL_ADDRESS.matcher(et_email.text).matches())
                        et_email.error = "Invalid Email Address ..."
                }
                if (s!!.isEmpty()) {
                    et_email.error = "Empty Field Not Allowed ..."
                    email = ""
                } else
                    email = et_email.text.toString()
            }
        })
        et_password.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {

            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                et_password.onFocusChangeListener = View.OnFocusChangeListener { _, _ ->
                    if (s!!.length < 6)
                        et_password.error = "Minimum Length Password is 6"
                }
                if (s!!.isEmpty())
                    et_password.error = "Empty Field Not Allowed ..."
            }
        })
        et_repassword.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                et_repassword.onFocusChangeListener = View.OnFocusChangeListener { _, _ ->
                    if (et_password.text.toString() != et_repassword.text.toString()) {
                        et_repassword.error = "PassWord MissMatch ..."
                        password = ""
                    }
                }
                if (s!!.isEmpty()) {
                    et_repassword.error = "Empty Field Not Allowed ..."
                    password = ""
                }
                if (et_password.text.toString() == et_repassword.text.toString())
                    password = et_repassword.text.toString()
            }
        })
        et_phonenumber.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {

            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {

                if (s!!.length > 7) {
                    et_phonenumber.error = "Invalid Phone Number ..."
                    phonenumber = ""
                }
                if (s.isEmpty()) {
                    et_phonenumber.error = "Empty Field Not Allowed ..."
                    phonenumber = ""
                } else {
                    phonenumber = phone_domain[indexPhoneNum] + et_phonenumber.text.toString()
                }

            }
        })
        btn_signup.setOnClickListener {


            if (Is_Vaild()) {
                loading.visibility = View.VISIBLE
                mFirebaseAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener {
                    if (!it.isSuccessful) {
                        try {
                            userID = mFirebaseAuth.currentUser?.uid!!
                            uploadFile()

                        } catch (e: NullPointerException) {
                            Toast.makeText(this, e.message, Toast.LENGTH_LONG).show()
                        }
                        Toast.makeText(this, "Successful sign up", Toast.LENGTH_LONG).show()

                    }
                }

            }
            else {
                if (indexCity == 0)
                    citySpin_error.visibility = View.VISIBLE
                else
                    citySpin_error.visibility = View.GONE
                if (indexPhoneNum == 0)
                    phoneSpin_error.visibility = View.VISIBLE
                else
                    phoneSpin_error.visibility = View.GONE
                if(!radiobuttonmale.isChecked && !radiobuttonfemale.isChecked)
                    tv_gendererror.visibility=View.VISIBLE
                else
                    tv_gendererror.visibility=View.GONE



                Toast.makeText(this, "Failed You Have Error Field Or Invalid", Toast.LENGTH_LONG).show()
            }
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        when(requestCode) {
            IMAGE_PICK_CODE ->{
                mImageUri = data?.data!!
                profile_img.setImageURI(data.data)
                              }
        }
    }
    fun Is_Vaild(): Boolean {
        return first_name!=""&&last_name!=""&&email!=""&&password!=""&&phonenumber!=""&&gender!=""&&city!=""&&birth!=""&& indexPhoneNum!=0&& indexCity!=0 }
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
        if (mImageUri != null)
        {
            var fileReference = mStorageRef.child("$first_name$last_name"+System.currentTimeMillis().toString() + "." + getFileExtension(mImageUri))
             var uploadTask= fileReference.putFile(mImageUri)
                 .addOnSuccessListener { taskSnapshot ->
                 Toast.makeText(this, "Upload successful", Toast.LENGTH_LONG)
                     .show()
                     taskSnapshot.metadata?.reference?.downloadUrl?.addOnSuccessListener {
                         imageStoragelink=it.toString()
                         var data = BackgroundWorker(this)
                         data.execute("signup",first_name,last_name,gender,phonenumber,email,birth,userID,city,imageStoragelink)
                        // var data = BackgroundWorker(this)
                         //data.execute("updateuserdata",first_name,last_name,gender,phonenumber,birth,userID,city,imageStoragelink)

                     }


                 }
                 .addOnFailureListener { e ->
                Toast.makeText(this, e.message, Toast.LENGTH_SHORT).show()
                 }
        }
        else
        {
            Toast.makeText(this, "No file selected", Toast.LENGTH_SHORT).show()
        }
    }
}

