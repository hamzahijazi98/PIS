package com.uni.pis


import android.content.Context
import android.os.AsyncTask
import android.widget.Toast
import com.uni.pis.Data.UserData.userData
import java.io.*
import java.net.HttpURLConnection
import java.net.MalformedURLException
import java.net.URL
import java.net.URLEncoder

class BackgroundWorker  constructor(var context: Context) :
    AsyncTask<String?, Void?, String?>() {
    var myCallback: MyCallback
    var result: String? = ""
    enum class userDataOrder(val index: Int) {
        firstName(0), lastName(1),phoneNumber(2),gender(3),
        email(4),birthday(5),city(6),image(7),UserID(8)
    }
    enum class phplinks(val link: String) {
        login("http://www.psutsystems.com/pisystem/user_data.php"),
        signup("http://www.psutsystems.com/pisystem/create_user.php"),
        createvent("http://www.psutsystems.com/pisystem/create_event.php"),
        myfriends("http://www.psutsystems.com/pisystem/my_friends.php"),
        myevent("http://www.psutsystems.com/pisystem/my_events.php"),
        invitedtoevent("http://www.psutsystems.com/pisystem/invited_to_event.php"),
        findfriend("http://www.psutsystems.com/pisystem/find_friend.php"),
        addfriend("http://www.psutsystems.com/pisystem/add_friend.php"),
        notification("http://www.psutsystems.com/pisystem/notification.php"),
        updateuserdata("http://www.psutsystems.com/pisystem/update_user_data.php"),
        invitetomyevent("http://www.psutsystems.com/pisystem/invite_to_my_event.php"),
        updatevent("http://www.psutsystems.com/pisystem/update_event.php"),
        updateAttendanceStatus("http://www.psutsystems.com/pisystem/update_attendance.php"),
        eventinvittee("http://www.psutsystems.com/pisystem/event_invittee.php"),
        checkInviteeID("http://www.psutsystems.com/pisystem/checkInviteeID.php"),
        updateventvideo("http://www.psutsystems.com/pisystem/update_event_video.php")

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
                        BufferedReader(InputStreamReader(inputStream, "UTF-8"))
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
                    BufferedReader(InputStreamReader(inputStream, "UTF-8"))
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
                    val FinviterName =p0[3]
                    val SinviterName = p0[4]
                    val eventdate = p0[5]
                    val LocationID = p0[6]
                    val Description = p0[7]
                    val eventid = p0[8]
                    val eventtype = p0[9]
                    val inviteenumber=p0[10]
                    val InviterID=p0[11]
                    val image=p0[12]
                    val channelUrl=p0[13]

                    val url = URL(phplinks.createvent.link)
                    val httpURLConnection =
                        url.openConnection() as HttpURLConnection
                    httpURLConnection.requestMethod = "POST"
                    httpURLConnection.doOutput = true
                    httpURLConnection.doInput = true
                    val outputStream = httpURLConnection.outputStream
                    val bufferedWriter =
                        BufferedWriter(OutputStreamWriter(outputStream, "UTF-8"))
                    val post_data = (URLEncoder.encode("starttime", "UTF-8") + "=" + URLEncoder.encode(stime, "UTF-8")
                            +"&"
                            + URLEncoder.encode("endtime","UTF-8")+"="+URLEncoder.encode(etime,"UTF-8")
                            +"&"
                            +URLEncoder.encode("firstinvitername","UTF-8")+"="+URLEncoder.encode(FinviterName,"UTF-8")
                            +"&"
                            +URLEncoder.encode("secondinvitername","UTF-8")+"="+URLEncoder.encode(SinviterName,"UTF-8")
                            +"&"
                            +URLEncoder.encode("date","UTF-8")+"="+URLEncoder.encode(eventdate,"UTF-8")
                            +"&"
                            +URLEncoder.encode("locationid","UTF-8")+"="+URLEncoder.encode(LocationID,"UTF-8")
                            +"&"
                            +URLEncoder.encode("description","UTF-8")+"="+URLEncoder.encode(Description,"UTF-8")
                            +"&"
                            +URLEncoder.encode("eventid","UTF-8")+"="+URLEncoder.encode(eventid,"UTF-8")
                            +"&"
                            +URLEncoder.encode("inviteenumber","UTF-8")+"="+URLEncoder.encode(inviteenumber,"UTF-8")
                            +"&"
                            +URLEncoder.encode("eventtype","UTF-8")+"="+URLEncoder.encode(eventtype,"UTF-8")
                            +"&"
                            +URLEncoder.encode("inviterid","UTF-8")+"="+URLEncoder.encode(InviterID,"UTF-8")
                            +"&"
                            +URLEncoder.encode("image","UTF-8")+"="+URLEncoder.encode(image,"UTF-8")
                            +"&"
                            +URLEncoder.encode("channelUrl","UTF-8")+"="+URLEncoder.encode(channelUrl,"UTF-8")


                            )
                    bufferedWriter.write(post_data)
                    bufferedWriter.flush()
                    bufferedWriter.close()
                    outputStream.close()
                    val inputStream = httpURLConnection.inputStream
                    val bufferedReader =
                        BufferedReader(InputStreamReader(inputStream, "UTF-8"))

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

            "myfriends" ->{
                try {
                val userID = p0[1]
                val url = URL(phplinks.myfriends.link)
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
                    BufferedReader(InputStreamReader(inputStream, "UTF-8"))
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
            }}

            "myevents" ->{
                try {
                    val userID = p0[1]
                    val url = URL(phplinks.myevent.link)
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
                        BufferedReader(InputStreamReader(inputStream, "UTF-8"))
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
                }}

            "invitedtoevent" ->{
                try {
                    val userID = p0[1]
                    val url = URL(phplinks.invitedtoevent.link)
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
                        BufferedReader(InputStreamReader(inputStream, "UTF-8"))
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
                }}

            "findfriend" -> {

                try {
                    val userName = p0[1]
                    val url = URL(phplinks.findfriend.link)
                    val httpURLConnection =
                        url.openConnection() as HttpURLConnection
                    httpURLConnection.requestMethod = "POST"
                    httpURLConnection.doOutput = true
                    httpURLConnection.doInput = true
                    val outputStream = httpURLConnection.outputStream
                    val bufferedWriter =
                        BufferedWriter(OutputStreamWriter(outputStream, "UTF-8"))
                    val post_data = URLEncoder.encode(
                        "Name",
                        "UTF-8"
                    ) + "=" + URLEncoder.encode(userName, "UTF-8")
                    bufferedWriter.write(post_data)
                    bufferedWriter.flush()
                    bufferedWriter.close()
                    outputStream.close()
                    val inputStream = httpURLConnection.inputStream
                    val bufferedReader =
                        BufferedReader(InputStreamReader(inputStream, "UTF-8"))
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

            "addfriend" ->  {
                try {
                    val userID = p0[1]
                    val friendID = p0[2]


                    val url = URL(phplinks.addfriend.link)
                    val httpURLConnection =
                        url.openConnection() as HttpURLConnection
                    httpURLConnection.requestMethod = "POST"
                    httpURLConnection.doOutput = true
                    httpURLConnection.doInput = true
                    val outputStream = httpURLConnection.outputStream
                    val bufferedWriter =
                        BufferedWriter(OutputStreamWriter(outputStream, "UTF-8"))
                    val post_data = (URLEncoder.encode("userID", "UTF-8") + "=" + URLEncoder.encode(userID, "UTF-8")
                            +"&"
                            + URLEncoder.encode("friendID","UTF-8")+"="+URLEncoder.encode(friendID,"UTF-8")

                                                   )
                    bufferedWriter.write(post_data)
                    bufferedWriter.flush()
                    bufferedWriter.close()
                    outputStream.close()
                    val inputStream = httpURLConnection.inputStream
                    val bufferedReader =
                        BufferedReader(InputStreamReader(inputStream, "UTF-8"))

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

            "notification" ->  {
                try {
                    val type = p0[1]
                    var notificationID = " "
                    var title = " "
                    var body = " "
                    var data = " "
                    var userID = " "
                    var  friendID = p0[2]
                    if(type=="send"){
                     notificationID = p0[2]!!
                     title = p0[3]!!
                     body = p0[4]!!
                     data = p0[5]!!
                     userID = p0[6]!!
                     friendID = p0[7]!!}




                    val url = URL(phplinks.notification.link)
                    val httpURLConnection =
                        url.openConnection() as HttpURLConnection
                    httpURLConnection.requestMethod = "POST"
                    httpURLConnection.doOutput = true
                    httpURLConnection.doInput = true
                    val outputStream = httpURLConnection.outputStream
                    val bufferedWriter =
                        BufferedWriter(OutputStreamWriter(outputStream, "UTF-8"))
                    val post_data = (URLEncoder.encode("toUserID", "UTF-8") + "=" + URLEncoder.encode(friendID, "UTF-8")
                            +"&"
                            + URLEncoder.encode("fromUserID","UTF-8")+"="+URLEncoder.encode(userID,"UTF-8")
                            +"&"
                            + URLEncoder.encode("notificationID","UTF-8")+"="+URLEncoder.encode(notificationID,"UTF-8")
                            +"&"
                            + URLEncoder.encode("title","UTF-8")+"="+URLEncoder.encode(title,"UTF-8")
                            +"&"
                            + URLEncoder.encode("body","UTF-8")+"="+URLEncoder.encode(body,"UTF-8")
                            +"&"
                            + URLEncoder.encode("data","UTF-8")+"="+URLEncoder.encode(data,"UTF-8")
                            +"&"
                            + URLEncoder.encode("type","UTF-8")+"="+URLEncoder.encode(type,"UTF-8")

                            )
                    bufferedWriter.write(post_data)
                    bufferedWriter.flush()
                    bufferedWriter.close()
                    outputStream.close()
                    val inputStream = httpURLConnection.inputStream
                    val bufferedReader =
                        BufferedReader(InputStreamReader(inputStream, "UTF-8"))

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

            "updateuserdata" ->  {
                try {
                    val user_fname = p0[1]
                    val user_lname = p0[2]
                    val user_gender =p0[3]
                    val user_PhoneNo = p0[4]
                    val user_BirthDate = p0[5]
                    val user_ID = p0[6]
                    val user_city = p0[7]
                    val user_image = p0[8]
                    val url = URL(phplinks.updateuserdata.link)
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
                        BufferedReader(InputStreamReader(inputStream, "UTF-8"))
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

            "invitetomyevent" ->  {
                try {
                    val Perrmision = p0[1]
                    val AttendanceStatus = p0[2]
                    val Event_ID =p0[3]
                    val UserID = p0[4]
                    val inviteenumber = p0[5]


                    val url = URL(phplinks.invitetomyevent.link)
                    val httpURLConnection =
                        url.openConnection() as HttpURLConnection
                    httpURLConnection.requestMethod = "POST"
                    httpURLConnection.doOutput = true
                    httpURLConnection.doInput = true
                    val outputStream = httpURLConnection.outputStream
                    val bufferedWriter =
                        BufferedWriter(OutputStreamWriter(outputStream, "UTF-8"))
                    val post_data = (URLEncoder.encode("Perrmision", "UTF-8") + "=" + URLEncoder.encode(Perrmision, "UTF-8")
                            +"&"
                            + URLEncoder.encode("AttendanceStatus","UTF-8")+"="+URLEncoder.encode(AttendanceStatus,"UTF-8")
                            +"&"
                            +URLEncoder.encode("Event_ID","UTF-8")+"="+URLEncoder.encode(Event_ID,"UTF-8")
                            +"&"
                            +URLEncoder.encode("UserID","UTF-8")+"="+URLEncoder.encode(UserID,"UTF-8")
                            +"&"
                            +URLEncoder.encode("inviteenumber","UTF-8")+"="+URLEncoder.encode(inviteenumber,"UTF-8")


                            )
                    bufferedWriter.write(post_data)
                    bufferedWriter.flush()
                    bufferedWriter.close()
                    outputStream.close()
                    val inputStream = httpURLConnection.inputStream
                    val bufferedReader =
                        BufferedReader(InputStreamReader(inputStream, "UTF-8"))

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

            "updatevent" ->  {
                try {
                    val stime = p0[1]
                    val etime = p0[2]
                    val FinviterName =p0[3]
                    val SinviterName = p0[4]
                    val eventdate = p0[5]
                    val LocationID = p0[6]
                    val Description = p0[7]
                    val inviteenumber=p0[8]
                    val image=p0[9]
                    val Event_ID=p0[10]


                    val url = URL(phplinks.updatevent.link)
                    val httpURLConnection =
                        url.openConnection() as HttpURLConnection
                    httpURLConnection.requestMethod = "POST"
                    httpURLConnection.doOutput = true
                    httpURLConnection.doInput = true
                    val outputStream = httpURLConnection.outputStream
                    val bufferedWriter =
                        BufferedWriter(OutputStreamWriter(outputStream, "UTF-8"))
                    val post_data = (URLEncoder.encode("starttime", "UTF-8") + "=" + URLEncoder.encode(stime, "UTF-8")
                            +"&"
                            + URLEncoder.encode("endtime","UTF-8")+"="+URLEncoder.encode(etime,"UTF-8")
                            +"&"
                            +URLEncoder.encode("firstinvitername","UTF-8")+"="+URLEncoder.encode(FinviterName,"UTF-8")
                            +"&"
                            +URLEncoder.encode("secondinvitername","UTF-8")+"="+URLEncoder.encode(SinviterName,"UTF-8")
                            +"&"
                            +URLEncoder.encode("date","UTF-8")+"="+URLEncoder.encode(eventdate,"UTF-8")
                            +"&"
                            +URLEncoder.encode("locationid","UTF-8")+"="+URLEncoder.encode(LocationID,"UTF-8")
                            +"&"
                            +URLEncoder.encode("description","UTF-8")+"="+URLEncoder.encode(Description,"UTF-8")
                            +"&"
                            +URLEncoder.encode("inviteenumber","UTF-8")+"="+URLEncoder.encode(inviteenumber,"UTF-8")
                            +"&"
                            +URLEncoder.encode("image","UTF-8")+"="+URLEncoder.encode(image,"UTF-8")
                            +"&"
                            +URLEncoder.encode("Event_ID","UTF-8")+"="+URLEncoder.encode(Event_ID,"UTF-8")


                            )
                    bufferedWriter.write(post_data)
                    bufferedWriter.flush()
                    bufferedWriter.close()
                    outputStream.close()
                    val inputStream = httpURLConnection.inputStream
                    val bufferedReader =
                        BufferedReader(InputStreamReader(inputStream, "UTF-8"))

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

            "updateAttendanceStatus" ->  {
                try {
                    val user_ID = p0[1]
                    val Event_ID = p0[2]
                    val AttendanceStatus =p0[3]

                    val url = URL(phplinks.updateAttendanceStatus.link)
                    val httpURLConnection =
                        url.openConnection() as HttpURLConnection
                    httpURLConnection.requestMethod = "POST"
                    httpURLConnection.doOutput = true
                    httpURLConnection.doInput = true
                    val outputStream = httpURLConnection.outputStream
                    val bufferedWriter =
                        BufferedWriter(OutputStreamWriter(outputStream, "UTF-8"))
                    val post_data = (URLEncoder.encode("UserID", "UTF-8") + "=" + URLEncoder.encode(user_ID, "UTF-8")
                            +"&"
                            + URLEncoder.encode("EventID","UTF-8")+"="+URLEncoder.encode(Event_ID,"UTF-8")
                            +"&"
                            +URLEncoder.encode("AttendanceStatus","UTF-8")+"="+URLEncoder.encode(AttendanceStatus,"UTF-8")


                            )
                    bufferedWriter.write(post_data)
                    bufferedWriter.flush()
                    bufferedWriter.close()
                    outputStream.close()
                    val inputStream = httpURLConnection.inputStream
                    val bufferedReader =
                        BufferedReader(InputStreamReader(inputStream, "UTF-8"))
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

            "eventinvittee" ->  {
                try {
                    val Event_ID = p0[1]

                    val url = URL(phplinks.eventinvittee.link)
                    val httpURLConnection =
                        url.openConnection() as HttpURLConnection
                    httpURLConnection.requestMethod = "POST"
                    httpURLConnection.doOutput = true
                    httpURLConnection.doInput = true
                    val outputStream = httpURLConnection.outputStream
                    val bufferedWriter =
                        BufferedWriter(OutputStreamWriter(outputStream, "UTF-8"))
                    val post_data = (URLEncoder.encode("EventID","UTF-8")+"="+URLEncoder.encode(Event_ID,"UTF-8"))
                    bufferedWriter.write(post_data)
                    bufferedWriter.flush()
                    bufferedWriter.close()
                    outputStream.close()
                    val inputStream = httpURLConnection.inputStream
                    val bufferedReader =
                        BufferedReader(InputStreamReader(inputStream, "UTF-8"))
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

            "checkID" ->  {
                try {
                    val Event_ID = p0[1]
                    val Invitee_ID = p0[2]

                    val url = URL(phplinks.checkInviteeID.link)
                    val httpURLConnection =
                        url.openConnection() as HttpURLConnection
                    httpURLConnection.requestMethod = "POST"
                    httpURLConnection.doOutput = true
                    httpURLConnection.doInput = true
                    val outputStream = httpURLConnection.outputStream
                    val bufferedWriter =
                        BufferedWriter(OutputStreamWriter(outputStream, "UTF-8"))
                    val post_data = (URLEncoder.encode("EventID","UTF-8")+"="+URLEncoder.encode(Event_ID,"UTF-8")
                            +"&"
                            + URLEncoder.encode("userID","UTF-8")+"="+URLEncoder.encode(Invitee_ID,"UTF-8"))
                    bufferedWriter.write(post_data)
                    bufferedWriter.flush()
                    bufferedWriter.close()
                    outputStream.close()
                    val inputStream = httpURLConnection.inputStream
                    val bufferedReader =
                        BufferedReader(InputStreamReader(inputStream, "UTF-8"))
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

            "updateventvideo" ->  {
                try {

                    val Event_ID=p0[1]
                    val videoURL=p0[2]


                    val url = URL(phplinks.updateventvideo.link)
                    val httpURLConnection =
                        url.openConnection() as HttpURLConnection
                    httpURLConnection.requestMethod = "POST"
                    httpURLConnection.doOutput = true
                    httpURLConnection.doInput = true
                    val outputStream = httpURLConnection.outputStream
                    val bufferedWriter =
                        BufferedWriter(OutputStreamWriter(outputStream, "UTF-8"))
                    val post_data = (URLEncoder.encode("eventID", "UTF-8") + "=" + URLEncoder.encode(Event_ID, "UTF-8")
                            +"&"
                            + URLEncoder.encode("videoURL","UTF-8")+"="+URLEncoder.encode(videoURL,"UTF-8")

                            )
                    bufferedWriter.write(post_data)
                    bufferedWriter.flush()
                    bufferedWriter.close()
                    outputStream.close()
                    val inputStream = httpURLConnection.inputStream
                    val bufferedReader =
                        BufferedReader(InputStreamReader(inputStream, "UTF-8"))

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
                 try{
                 var data=result!!.split("&")
                 userData.first_name=data[userDataOrder.firstName.index].substringAfter("=")
                 userData.last_name=data[userDataOrder.lastName.index].substringAfter("=")
                 userData.phoneNumber=data[userDataOrder.phoneNumber.index].substringAfter("=")
                 userData.gender=data[userDataOrder.gender.index].substringAfter("=")
                 userData.email=data[userDataOrder.email.index].substringAfter("=")
                 userData.birthdate=data[userDataOrder.birthday.index].substringAfter("=")
                 userData.city=data[userDataOrder.city.index].substringAfter("=")
                 userData.image=data[userDataOrder.image.index].substringAfter("=") }
                 catch (e:KotlinNullPointerException){
                     Toast.makeText(context,e.message,Toast.LENGTH_LONG).show()
                 }
             }

            "event"->{
                var data=result!!.split("&")
//                eventData.Inv_No=data[0].substringAfter("=")

            }

        }
        myCallback.onResult(result)

    }

    override fun onProgressUpdate(vararg values: Void?) {
        Toast.makeText(context,result,Toast.LENGTH_LONG).show()
    }

    interface MyCallback {
        fun onResult(result: String?)
    }





}