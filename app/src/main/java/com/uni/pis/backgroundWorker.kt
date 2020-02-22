package com.uni.pis
import android.app.AlertDialog
import android.content.Context
import android.os.AsyncTask
import java.io.BufferedReader
import java.io.BufferedWriter
import java.io.IOException
import java.io.InputStream
import java.io.InputStreamReader
import java.io.OutputStream
import java.io.OutputStreamWriter
import java.net.HttpURLConnection
import java.net.MalformedURLException
import java.net.URL
import java.net.URLEncoder



class BackgroundWorker ( var context1: Context) : AsyncTask<String, Void, String>() {

    lateinit var alertDialog: AlertDialog
    override fun doInBackground(vararg params: String): String? {
        val type = params[0]
        val loginUrl = "http://https://files.000webhost.com//connection.php"
        if (type == "login") {
            try {
                val personID = params[1]
                val url = URL(loginUrl)
                val httpURLConnection = url.openConnection() as HttpURLConnection
                httpURLConnection.requestMethod="POST"
                httpURLConnection.doOutput=true
                httpURLConnection.doInput=true
                val outputStream = httpURLConnection.outputStream
                val bufferedWriter = BufferedWriter(OutputStreamWriter(outputStream, "UTF-8"))
                val postData = URLEncoder.encode("personID", "UTF-8") + "=" + URLEncoder.encode( personID, "UTF-8")
                bufferedWriter.write(postData)
                bufferedWriter.flush()
                bufferedWriter.close()
                outputStream.close()
                val inputStream = httpURLConnection.inputStream
                val bufferedReader = BufferedReader(InputStreamReader(inputStream, "iso-8859-1"))
                var result = ""
                var line = ""
                line = bufferedReader.readLine()
                while ( line!= null) {
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
        alertDialog = AlertDialog.Builder(context1).create()
        alertDialog.setTitle("Login Status")
    }

    override fun onPostExecute(result: String) {
        alertDialog.setMessage(result)
        alertDialog.show()
    }

    override fun onProgressUpdate(vararg values: Void) {
        super.onProgressUpdate(*values)
    }
}