package com.uni.pis.profile

import android.app.Activity
import android.app.AlertDialog
import android.app.DatePickerDialog
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.webkit.MimeTypeMap
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.RadioButton
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import com.google.firebase.storage.StorageTask
import com.uni.pis.BackgroundWorker
import com.uni.pis.R
import com.uni.pis.Data.UserData.userData
import kotlinx.android.synthetic.main.activity_edit_profile.*
import kotlinx.android.synthetic.main.fragment_profile_page_personal.*
import java.util.*

class EditProfileActivity : AppCompatActivity(),BackgroundWorker.MyCallback {
    private val phone_domain = arrayOf("0XX", "078", "077", "079")
    private val cities = arrayOf(
        "Choose your city", "Amman", "Az Zarqa", "Irbid", "Al Karak",
        "Jerash", "Ajloun", "Ma'an", "Tafilah", "Madaba", "Aqaba", "Balqa", "Mafraq"
    )
    var mFirebaseAuth = FirebaseAuth.getInstance()
    lateinit var mStorageRef: StorageReference
    lateinit var mDatabaseRef: DatabaseReference
    lateinit private var mUploadTask: StorageTask<*>
    var first_name: String= userData.first_name
    var last_name: String= userData.last_name
    var phonenumber: String= userData.phoneNumber
    var gender: String= userData.gender
    var city: String= userData.city
    lateinit var userID:String
    var birth:String= userData.birthdate
    var mImageUri: Uri? =null
    var image= userData.image
    private val IMAGE_PICK_CODE = 1000
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit_profile)
        mStorageRef = FirebaseStorage.getInstance().getReference("uploads");
        et_fname.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
                first_name=s.toString()
                userData.first_name=first_name
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            }

        })
        et_lname.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
                last_name=s.toString()
                userData.last_name=last_name
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            }
        })
        val phone_adapter = ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, phone_domain)
        spin_phone.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onNothingSelected(parent: AdapterView<*>?) {
                phonenumber= userData.phoneNumber
            }
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                if(position!=0)
                    phonenumber = phone_domain[position] + et_phonenum.text.toString()

                et_phonenum.addTextChangedListener(object : TextWatcher {
                    override fun afterTextChanged(s: Editable?) {
                        phonenumber=phone_domain[position]+s.toString()

                    }

                    override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

                    }

                    override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                        if(et_phonenum.text.length!=7)
                            et_phonenum.error="Phone Number Not Valid"
                    }
                })
            } }
        spin_phone.adapter = phone_adapter
        val city_adapter = ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, cities)
        spin_city.adapter = city_adapter
        spin_city.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                if(position==0) {
                    city = userData.city
                }
                else{
                    city=cities[position]
                    userData.city=city
                }
            }
            override fun onNothingSelected(parent: AdapterView<*>?) {
            }
    }


        et_fname.setText(userData.first_name)
        et_lname.setText(userData.last_name)
        et_phonenum.setText(userData.phoneNumber)
        tv_date.text = userData.birthdate
        if(userData.gender=="Male")
            findViewById<RadioButton>(R.id.RB_male).isChecked=true
        else
            findViewById<RadioButton>(R.id.RB_female).isChecked=true

        rg_gender.setOnCheckedChangeListener { group, checkedId ->
            if (checkedId == R.id.RB_male){
                gender = "Male"
                userData.gender=gender
            }
            if (checkedId == R.id.RB_female) {
                gender = "Female"
                userData.gender=gender
            }
        }

        try {
            mStorageRef = FirebaseStorage.getInstance().getReferenceFromUrl(image)
            mStorageRef.getBytes(Long.MAX_VALUE).addOnSuccessListener {
                val bmp = BitmapFactory.decodeByteArray(it, 0, it.size)
                try {
                    iv_profile.setImageBitmap(
                        Bitmap.createScaledBitmap(
                            bmp, iv_profile.width,
                            iv_profile.height, false
                        )
                    )
                } catch (e: IllegalStateException) {
                    Toast.makeText(this, e.message, Toast.LENGTH_LONG).show()
                } catch (e: NullPointerException) {
                    Toast.makeText(this, e.message, Toast.LENGTH_LONG).show()
                } catch (e: Exception) {
                    Toast.makeText(this, e.message, Toast.LENGTH_LONG).show()
                }

            }.addOnFailureListener {
                Toast.makeText(this, it.message, Toast.LENGTH_LONG).show()
            }
        } catch (e: Exception) { Toast.makeText(this, e.message, Toast.LENGTH_LONG).show() }



        btn_birthdate.setOnClickListener{
            val now= Calendar.getInstance()

            val dob= DatePickerDialog(this,
                DatePickerDialog.OnDateSetListener { view, year, month, dayOfMonth ->
                    birth=dayOfMonth.toString()+ "-"+ (month+1).toString() + "-" + year.toString()
                    tv_date.text=birth
                    userData.birthdate=birth
                },
                now.get(Calendar.YEAR),now.get(Calendar.MONTH),now.get(Calendar.DAY_OF_MONTH))
            dob.show()

        }
        iv_Editprofile.setOnClickListener {
            val intent = Intent(Intent.ACTION_PICK)
            intent.type = "image/*"
            startActivityForResult(intent, IMAGE_PICK_CODE)
        }
        btn_Save.setOnClickListener {
            val builder = AlertDialog.Builder(this)
            builder.setMessage("Edit Confirmation")
            builder.setPositiveButton("Confirm"){ _, _ ->
                    try {
                        userID = mFirebaseAuth.currentUser?.uid!!
                        uploadFile()
                    } catch (e: NullPointerException) {
                        Toast.makeText(this, e.message, Toast.LENGTH_LONG).show()
                    }
                Toast.makeText(this, "Update Saved", Toast.LENGTH_LONG).show()

            }
            builder.setNegativeButton("Cancel"){ _, _ ->
                Toast.makeText(this,"Cancelled.", Toast.LENGTH_SHORT).show()
            }
            builder.create().show()

        }



    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        when (requestCode) {
            IMAGE_PICK_CODE -> {
                if (resultCode == Activity.RESULT_OK) {
                    mImageUri = data!!.data
                    iv_Editprofile.setImageURI(mImageUri)
                }
            }
        }
    }

    private fun getFileExtension(uri: Uri): String? {
        val cR = contentResolver
        val mime = MimeTypeMap.getSingleton()
        return mime.getExtensionFromMimeType(cR.getType(uri))
    }
    private fun uploadFile() {
        if (mImageUri != null)
        {
            var fileReference = mStorageRef.child("$first_name$last_name"+System.currentTimeMillis().toString() + "." + getFileExtension(mImageUri!!
            ))
            var uploadTask= fileReference.putFile(mImageUri!!)
                .addOnSuccessListener { taskSnapshot ->
                    Toast.makeText(this, "Upload successful", Toast.LENGTH_LONG)
                        .show()
                    taskSnapshot.metadata?.reference?.downloadUrl?.addOnSuccessListener {
                        image=it.toString()
                        userData.image=image
                        var data = BackgroundWorker(this)
                        data.execute("updateuserdata",first_name,last_name,gender,phonenumber,birth,userID,city,image)
                    }


                }
                .addOnFailureListener { e ->
                    Toast.makeText(this, e.message, Toast.LENGTH_SHORT).show()
                }
        }
        else
        {
            var data = BackgroundWorker(this)
            data.execute("updateuserdata",first_name,last_name,gender,phonenumber,birth,userID,city,image)
        }
    }

    override fun onResult(result: String?) {

    }

}
