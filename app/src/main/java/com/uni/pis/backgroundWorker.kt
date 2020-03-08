package com.uni.pis


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
    enum class userDataOrder(val index: Int) {
        firstName(0), lastName(1),phoneNumber(2),gender(3),
        email(4),birthday(5),city(6),image(7)
    }
    enum class phplinks(val link: String) {
        login("http://www.psutsystems.com/userdata.php"),signup("http://www.psutsystems.com/createuser.php"),friends("http://www.psutsystems.com/myfriends.php")
    }
    init {
        myCallback = context as MyCallback
    }
    lateinit var type :String
    override fun doInBackground(vararg p0: String?): String? {
        type = p0[0].toString()
        when (type ) {
            "login" -> {

                try {
                    val userID = p0[1]
                    val url = URL(phplinks.login.link)
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

            "signup" ->  {
            try {
                val user_fname = p0[1]
                val user_lname = p0[2]
                val user_gender =p0[3]
                val user_PhoneNo = p0[4]
                val user_Email = p0[5]
                val user_BirthDate = p0[6]
                val user_ID = p0[7]
                val user_city = p0[8]
                val user_image = p0[9]
                val url = URL(phplinks.signup.link)
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
                        +"&"
                        +URLEncoder.encode("image","UTF-8")+"="+URLEncoder.encode(user_image,"UTF-8")

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

            "createEvent" ->  {
                try {
                    val stime = p0[1]
                    val etime = p0[2]
                    val Finviter =p0[3]
                    val Sinviter = p0[4]
                    val eventdate = p0[5]
                    val LocationID = p0[6]
                    val Description = p0[7]
                    val eventid = p0[8]
                    val eventtype = p0[9]
                    val url = URL(phplinks.signup.link)
                    val httpURLConnection =
                        url.openConnection() as HttpURLConnection
                    httpURLConnection.requestMethod = "POST"
                    httpURLConnection.doOutput = true
                    httpURLConnection.doInput = true
                    val outputStream = httpURLConnection.outputStream
                    val bufferedWriter =
                        BufferedWriter(OutputStreamWriter(outputStream, "UTF-8"))
                    val post_data = (URLEncoder.encode("id", "UTF-8") + "=" + URLEncoder.encode(stime, "UTF-8")
                            +"&"
                            + URLEncoder.encode("firstname","UTF-8")+"="+URLEncoder.encode(etime,"UTF-8")
                            +"&"
                            +URLEncoder.encode("lastname","UTF-8")+"="+URLEncoder.encode(Finviter,"UTF-8")
                            +"&"
                            +URLEncoder.encode("gender","UTF-8")+"="+URLEncoder.encode(Sinviter,"UTF-8")
                            +"&"
                            +URLEncoder.encode("phoneno","UTF-8")+"="+URLEncoder.encode(eventdate,"UTF-8")
                            +"&"
                            +URLEncoder.encode("email","UTF-8")+"="+URLEncoder.encode(LocationID,"UTF-8")
                            +"&"
                            +URLEncoder.encode("birthdate","UTF-8")+"="+URLEncoder.encode(Description,"UTF-8")
                            +"&"
                            +URLEncoder.encode("city","UTF-8")+"="+URLEncoder.encode(eventid,"UTF-8")
                            +"&"
                            +URLEncoder.encode("image","UTF-8")+"="+URLEncoder.encode(eventtype,"UTF-8")

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
        }
        return null
    }

    override fun onPreExecute() {

    }

    override fun onPostExecute(result: String?) {
        when (type ) {
             "login"->{
                 var data=result!!.split("&")
                 userData.first_name=data[userDataOrder.firstName.index].substringAfter("=")
                 userData.last_name=data[userDataOrder.lastName.index].substringAfter("=")
                 userData.phoneNumber=data[userDataOrder.phoneNumber.index].substringAfter("=")
                 userData.gender=data[userDataOrder.gender.index].substringAfter("=")
                 userData.email=data[userDataOrder.email.index].substringAfter("=")
                 userData.birthdate=data[userDataOrder.birthday.index].substringAfter("=")
                 userData.city=data[userDataOrder.city.index].substringAfter("=")
                 userData.image=data[userDataOrder.image.index].substringAfter("=") }

            "friends"->{}

        }
        myCallback.onResult(result)

    }


    interface MyCallback {
        fun onResult(result: String?)
    }




}