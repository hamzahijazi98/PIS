package com.uni.pis
import android.app.DatePickerDialog
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.*
import android.text.TextUtils
import android.view.View
import kotlinx.android.synthetic.main.activity_sign_up.*
import java.util.*


class SignUp : AppCompatActivity() {
    private val phone_domain= arrayOf("078","077","079")
    private val cities= arrayOf("Amman","Az Zarqa","Irbid","Al Karak","Jerash","Ajloun","Ma'an","Tafilah","Madaba","Aqaba","Balqa","Mafraq")
    //var mFirebaseAuth = FirebaseAuth.getInstance()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_up)




        /*Hamzeh
        val username = findViewById<EditText>(R.id.Email)
        val password = findViewById<EditText>(R.id.password)
        val signup = findViewById<Button>(R.id.signup)
        val loading = findViewById<ProgressBar>(R.id.loading)*/

        //Birthdate (yousef )
        val cal=Calendar.getInstance()
        val year=cal.get(Calendar.YEAR)
        val month=cal.get(Calendar.MONTH)
        val day=cal.get(Calendar.DAY_OF_MONTH )

        //button birth date birthdate
        btn_birthdate.setOnClickListener{
            val date_of_birth= DatePickerDialog(this,
                DatePickerDialog.OnDateSetListener { view, year, month, dayOfMonth ->
                    tv_date.text=(" "+dayOfMonth+" "+month+" "+ year )  },year,month,day)
            date_of_birth.show()}



        val phone_adapter=ArrayAdapter<String>(this,android.R.layout.simple_spinner_item,phone_domain)
        spinner_phone.adapter=phone_adapter

        // city code
        var city=""
        val city_adapter=ArrayAdapter<String>(this,android.R.layout.simple_spinner_dropdown_item,cities)
        spinner_city.adapter=city_adapter

        spinner_city.onItemSelectedListener=object:AdapterView.OnItemSelectedListener
        {

            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                city=cities[position]
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {

            }
        }



        //yousef




        val first_name:String
        val lastname:String
        val email:String
        var password:String
        var phonenumber:String
        var gender:String
        val date_of_birth:String


        btn_signup.setOnClickListener {
           // var username1=username.text.toString().trim()
           //var password1=password.text.toString().trim()

             //yousefffffffff
             //first name valid
             if (TextUtils.isEmpty(et_firstname.getText().toString()))
                 et_firstname.error = "Empty field not allowed ... "
             if(et_firstname.text.toString().length>12)
                 et_firstname.error="Name too long ..."

             //last name valid
             if (TextUtils.isEmpty(et_lastname.getText().toString()))
                 et_lastname.error = "Empty field not allowed ... "
             if(et_lastname.text.toString().length>12)
                 et_lastname.error="Name too long ..."


             //email valid
             if(TextUtils.isEmpty(et_email.text)||!android.util.Patterns.EMAIL_ADDRESS.matcher(et_email.getText()).matches())
                 et_email.error="Empty or Invalid Email Address ..."


             //password valid
             if(TextUtils.isEmpty(et_password.text))
                 et_password.error="Empty field not allowed ..."

             if(TextUtils.isEmpty(et_repassword.text))
                 et_repassword.error="Empty field not allowed ..."


             if(et_password.text.toString().equals(et_repassword.text.toString())) {
                 password=et_password.text.toString()
             }
             else{
                 et_password.error="Password Mismatch"
             }

            //phone number valid
             if(TextUtils.isEmpty(et_phonenumber.text))
                 et_phonenumber.error="Empty field not allowed ... "

             if(et_phonenumber.length()!=7)
                 et_phonenumber.error="Invalid phone number ..."



             spinner_phone.onItemSelectedListener=object :AdapterView.OnItemSelectedListener
             {
                 override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                     phonenumber=phone_domain[position]+et_phonenumber.text.toString()
                 }

                 override fun onNothingSelected(parent: AdapterView<*>?) {

                 }
             }

            //gender check
            if(RB_male.isChecked)
                gender="male"
            else
                gender="female"




         /*   mFirebaseAuth.createUserWithEmailAndPassword(
                username1,password1
            ).addOnCompleteListener {
                if (!it.isSuccessful) {
                    Toast.makeText(
                        this,
                        "Failed to login Username or Password Invalid $username1,$password1",
                        Toast.LENGTH_LONG
                    ).show()
                } else {

                    Toast.makeText(
                        this,
                        "$username1,$password1",
                        Toast.LENGTH_LONG
                    ).show()

                    intent = Intent(this, LoginActivity::class.java)
                    startActivity(intent)
                    finish()
                }
            }*/
        }
    }
}
