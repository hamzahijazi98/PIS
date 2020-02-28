package com.uni.pis

import android.app.AlertDialog
import android.content.Context
import android.os.AsyncTask
import android.widget.Toast
import com.uni.pis.data.userData
import java.io.*
import java.net.HttpURLConnection
import java.net.MalformedURLException
import java.net.URL
import java.net.URLEncoder

class BackgroundWorker  constructor(var context: Context) :
    AsyncTask<String?, Void?, String?>() {
    var myCallback: MyCallback
    init {
        myCallback = context as MyCallback
    }
    override fun doInBackground(vararg p0: String?): String? {
        val type = p0[0]
        val login_url = "http://www.psutsystems.com/userdata.php"
        if (type == "login") {

            try {
                val userID = p0[1]
                val url = URL(login_url)
                val httpURLConnection =
                    url.openConnection() as HttpURLConnection
                httpURLConnection.requestMethod = "POST"
                httpURLConnection.doOutput = true
                httpURLConnection.doInput = true
                val outputStream = httpURLConnection.outputStream
                val bufferedWriter =
                    BufferedWriter(OutputStreamWriter(outputStream, "UTF-8"))
                val post_data = URLEncoder.encode(
                    "userID",
                    "UTF-8"
                ) + "=" + URLEncoder.encode(userID, "UTF-8")
                bufferedWriter.write(post_data)
                bufferedWriter.flush()
                bufferedWriter.close()
                outputStream.close()
                val inputStream = httpURLConnection.inputStream
                val bufferedReader =
                    BufferedReader(InputStreamReader(inputStream, "iso-8859-1"))
                var result: String? = ""
                var line: String? = ""
                while (bufferedReader.readLine().also { line = it } != null) {
                    result += line
                }
                bufferedReader.close()
                inputStream.close()
                httpURLConnection.disconnect()
                return result
            } catch (e: MalformedURLException) {
                e.printStackTrace()
            } catch (e: IOException) {
                e.printStackTrace()
            }
        }
        if (type == "signup") {
            try {
                val signup_url = "http://www.psutsystems.com/createuser.php"
                val user_fname = p0[1]
                val user_lname = p0[2]
                val user_gender =p0[3]
                val user_PhoneNo = p0[4]
                val user_Email = p0[5]
                val user_BirthDate = p0[6]
                val user_ID = p0[7]
                val user_city = p0[8]
                val url = URL(signup_url)
                val httpURLConnection =
                    url.openConnection() as HttpURLConnection
                httpURLConnection.requestMethod = "POST"
                httpURLConnection.doOutput = true
                httpURLConnection.doInput = true
                val outputStream = httpURLConnection.outputStream
                val bufferedWriter =
                    BufferedWriter(OutputStreamWriter(outputStream, "UTF-8"))
                val post_data = (URLEncoder.encode("id", "UTF-8") + "=" + URLEncoder.encode(user_ID, "UTF-8")
                        +"&"
                        + URLEncoder.encode("firstname","UTF-8")+"="+URLEncoder.encode(user_fname,"UTF-8")
                        +"&"
                        +URLEncoder.encode("lastname","UTF-8")+"="+URLEncoder.encode(user_lname,"UTF-8")
                        +"&"
                        +URLEncoder.encode("gender","UTF-8")+"="+URLEncoder.encode(user_gender,"UTF-8")
                        +"&"
                        +URLEncoder.encode("phoneno","UTF-8")+"="+URLEncoder.encode(user_PhoneNo,"UTF-8")
                        +"&"
                        +URLEncoder.encode("email","UTF-8")+"="+URLEncoder.encode(user_Email,"UTF-8")
                        +"&"
                        +URLEncoder.encode("birthdate","UTF-8")+"="+URLEncoder.encode(user_BirthDate,"UTF-8")
                        +"&"
                        +URLEncoder.encode("city","UTF-8")+"="+URLEncoder.encode(user_city,"UTF-8")
                        )
                bufferedWriter.write(post_data)
                bufferedWriter.flush()
                bufferedWriter.close()
                outputStream.close()
                val inputStream = httpURLConnection.inputStream
                val bufferedReader =
                    BufferedReader(InputStreamReader(inputStream, "iso-8859-1"))
                var result: String? = ""
                var line: String? = ""
                while (bufferedReader.readLine().also { line = it } != null) {
                    result += line
                }
                bufferedReader.close()
                inputStream.close()
                httpURLConnection.disconnect()
                return result
            } catch (e: MalformedURLException) {
                e.printStackTrace()
            } catch (e: IOException) {
                e.printStackTrace()
            }
        }
        return null
    }

    override fun onPreExecute() {

    }

    override fun onPostExecute(result: String?) {

        var firstName = result!!.substringAfter("Fname").substringBefore("Lname")
        var lastName = result.substringAfter("Lname").substringBefore("phone")
        var phone = result.substringAfter("phone").substringBefore("gender")
        var city = result.substringAfter("city").substringBefore("\"")
        var birthday = result.substringAfter("Birthday").substringBefore("city")
        var email = result.substringAfter("email").substringBefore("Birthday")
        var gender = result.substringAfter("gender","hello").substringBefore("email","hello")
        var birth_day = birthday.substring(0,4)
        var birth_month = birthday.substring(5,6)
        var birth_year = birthday.substring(7,12)
         userData(firstName,lastName,email,phone,gender,city,"$birth_day-$birth_month-$birth_year")
        myCallback.onResult(result)

    }


    interface MyCallback {
        fun onResult(result: String?)
    }




}