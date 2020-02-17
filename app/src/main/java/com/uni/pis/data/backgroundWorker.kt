package com.uni.pis.data

import android.app.AlertDialog;
import android.content.Context;
import android.os.AsyncTask;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;



class BackgroundWorker internal constructor(internal var context: Context) :
    AsyncTask<String, Void, String>() {
    internal lateinit var alertDialog: AlertDialog
    override fun doInBackground(vararg params: String): String? {
        val type = params[0]
        val login_url = "http://https://files.000webhost.com//connection.php"
        if (type == "login") {
            try {
                val user_name = params[1]
                val password = params[2]
                val url = URL(login_url)
                val httpURLConnection = url.openConnection() as HttpURLConnection
                httpURLConnection.setRequestMethod("POST")
                httpURLConnection.setDoOutput(true)
                httpURLConnection.setDoInput(true)
                val outputStream = httpURLConnection.getOutputStream()
                val bufferedWriter = BufferedWriter(OutputStreamWriter(outputStream, "UTF-8"))
                val post_data = (URLEncoder.encode("user_name", "UTF-8") + "=" + URLEncoder.encode(
                    user_name,
                    "UTF-8"
                ) + "&"
                        + URLEncoder.encode("password", "UTF-8") + "=" + URLEncoder.encode(
                    password,
                    "UTF-8"
                ))
                bufferedWriter.write(post_data)
                bufferedWriter.flush()
                bufferedWriter.close()
                outputStream.close()
                val inputStream = httpURLConnection.getInputStream()
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
        alertDialog = AlertDialog.Builder(context).create()
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