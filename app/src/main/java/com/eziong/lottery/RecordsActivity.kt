package com.eziong.lottery

import android.app.VoiceInteractor
import android.os.Bundle
import android.os.PersistableBundle
import android.util.Log
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.VolleyError
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.google.android.gms.ads.AdRequest
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.activity_record_item.*
import kotlinx.android.synthetic.main.activity_record_item.number1
import kotlinx.android.synthetic.main.activity_record_item.number2
import kotlinx.android.synthetic.main.activity_record_item.number3
import kotlinx.android.synthetic.main.activity_record_item.number4
import kotlinx.android.synthetic.main.activity_record_item.number5
import kotlinx.android.synthetic.main.activity_record_item.number6
import org.json.JSONObject
import java.io.BufferedReader
import java.io.InputStream
import java.net.URL
import java.util.*

class RecordsActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_record_item)
        request()
        val adRequest = AdRequest.Builder().build()
        recordAds.loadAd(adRequest)
    }

    fun request() {
        val r = intent.getStringExtra("r").toString()
        val url = "https://www.dhlottery.co.kr/common.do?method=getLottoNumber&drwNo=${r}"
        val queue = Volley.newRequestQueue(this)
        val lotteryNumbers = arrayListOf(
            findViewById<TextView>(R.id.number1),
            findViewById<TextView>(R.id.number2),
            findViewById<TextView>(R.id.number3),
            findViewById<TextView>(R.id.number4),
            findViewById<TextView>(R.id.number5),
            findViewById<TextView>(R.id.number6),
            findViewById<TextView>(R.id.bonus)
        )

        var round = findViewById<TextView>(R.id.round)
        var date = findViewById<TextView>(R.id.date)
        var firstWinamnt = findViewById<TextView>(R.id.firstWinamnt)
        var firstPrzwnerCo = findViewById<TextView>(R.id.firstPrzwnerCo)
        var totSellamnt = findViewById<TextView>(R.id.totSellamnt)


        val stringRequest = StringRequest(Request.Method.GET, url,
            Response.Listener<String> { response ->
                val data = JSONObject(response)
                if (data.getString("returnValue") == "success") {
                    var i = 0
                    date.text = data.getString("drwNoDate")
                    round.text = r.toString()

                    lotteryNumbers.forEach {
                        i += 1

                        if (i == 7) it.text = data.getString("bnusNo")
                        else it.text = data.getString("drwtNo${i}")

                        when (it.text.toString().toInt()) {
                            in 1..9 -> it.setBackgroundResource(R.drawable.yellow_ball)
                            in 10..19 -> it.setBackgroundResource(R.drawable.blue_ball)
                            in 20..29 -> it.setBackgroundResource(R.drawable.red_ball)
                            in 30..39 -> it.setBackgroundResource(R.drawable.grey_ball)
                            in 40..49 -> it.setBackgroundResource(R.drawable.green_ball)
                        }
                    }
                    firstWinamnt.text =
                        MoneyCalculate.MoneyFormat(data.getString("firstWinamnt")) + " 원"
                    firstPrzwnerCo.text = data.getString("firstPrzwnerCo") + " 명"
                    totSellamnt.text =
                        MoneyCalculate.MoneyFormat(data.getString("totSellamnt")) + " 원"

                } else {
                    Toast.makeText(this, "유효하지 않은 입력", Toast.LENGTH_LONG).show()
                }

            }, Response.ErrorListener { Log.d("Error", "Request Error") }
        )
        queue.add(stringRequest)
    }


}
