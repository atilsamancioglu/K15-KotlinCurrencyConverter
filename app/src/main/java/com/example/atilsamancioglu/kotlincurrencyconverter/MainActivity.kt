package com.example.atilsamancioglu.kotlincurrencyconverter

import android.os.AsyncTask
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import kotlinx.android.synthetic.main.activity_main.*
import org.json.JSONObject
import java.io.InputStreamReader
import java.net.HttpURLConnection
import java.net.URL

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

    }

    fun get (view: View){

        val downloadData = Download()

        try {

            val url = "http://api.fixer.io/latest?base="
            val chosenBase = editText.text.toString()

            downloadData.execute(url+chosenBase)

        } catch (e: Exception){
            e.printStackTrace()
        }


    }



    inner class Download : AsyncTask<String,Void,String>(){

        override fun doInBackground(vararg p0: String?): String {

            var result = ""
            var url : URL
            val httpURLConnection : HttpURLConnection

            try {

                url = URL(p0[0])
                httpURLConnection = url.openConnection() as HttpURLConnection
                val inputStream = httpURLConnection.inputStream
                val inputStreamReader = InputStreamReader(inputStream)

                var data = inputStreamReader.read()

                while (data > 0) {
                    val character = data.toChar()
                    result += character

                    data = inputStreamReader.read()
                }


                return result

            } catch (e: Exception) {
                e.printStackTrace()
                return result
            }

        }

        override fun onPostExecute(result: String?) {
            super.onPostExecute(result)

                try {

                    val jSONObject = JSONObject(result)
                    println(jSONObject)
                    val base = jSONObject.getString("base")
                    println(base)
                    val date = jSONObject.getString("date")
                    println(date)
                    val rates = jSONObject.getString("rates")
                    println(rates)

                    val newJsonObject = JSONObject(rates)
                    val chf = newJsonObject.getString("CHF")
                    println(chf)
                    val czk = newJsonObject.getString("CZK")
                    val tl = newJsonObject.getString("TRY")

                    chfText.text = "CHF: " + chf
                    czkText.text = "CZK: " + czk
                    tryText.text = "TRY: " + tl


                } catch (e: Exception) {
                    e.printStackTrace()
                }



        }
    }



}
