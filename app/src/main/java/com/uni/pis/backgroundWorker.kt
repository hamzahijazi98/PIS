package com.uni.pis

import android.app.AlertDialog
import android.content.Context
import android.os.AsyncTask
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
        val login_url = "https://pisystemgp.000webhostapp.com/userdata.php"
        if (type == "login") {
            try {
                val personID = p0[1]
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
                    "personID",
                    "UTF-8"
                ) + "=" + URLEncoder.encode(personID, "UTF-8")
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
        var city = result.substringAfter("city")
        var birth_day = result.substringAfter("Birthday").substringBefore("-")
        var birth_month = result.substringAfter("-").substringBefore("-")
        var birth_year = result.substringAfter("-").substringBefore("city")
        var email = result.substringAfter("email").substringBefore("Birthday")
        var gender = result.substringAfter("gender","hello").substringBefore("email","hello")
         userData(firstName,lastName,email,phone,gender,city,birth_day+birth_month+birth_year)
        myCallback.onResult(result)

    }


    interface MyCallback {
        fun onResult(result: String?)
    }




}