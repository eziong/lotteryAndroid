package com.eziong.lottery

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.widget.TextView
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import org.json.JSONObject
import java.util.*

/* VolleyService.kt */

class VolleyService(val round: Int) {

    val url = "https://www.dhlottery.co.kr/common.do?method=getLottoNumber&drwNo=${round}"

    fun recordVolley(context: Context, success: (Boolean) -> Unit) {

        val myJson = JSONObject()
        val requestBody = myJson.toString()
        /* myJson에 아무 데이터도 put 하지 않았기 때문에 requestBody는 "{}" 이다 */

        val recordRequest =
            object : StringRequest(Method.GET, url, Response.Listener { response ->
                println("서버 Response 수신: $response")

                val info = response.toString()

                val view: View =
                    LayoutInflater.from(context).inflate(R.layout.activity_record_item, null)
                val date = view.findViewById<TextView>(R.id.date)
                view.findViewById<TextView>(R.id.round).setText("1")
                view.findViewById<TextView>(R.id.number1).setText("1")
                view.findViewById<TextView>(R.id.number2).setText("1")
                view.findViewById<TextView>(R.id.number3).setText("1")
                view.findViewById<TextView>(R.id.number4).setText("1")
                view.findViewById<TextView>(R.id.number5).setText("1")
                view.findViewById<TextView>(R.id.number6).setText("1")

                println(view.findViewById<TextView>(R.id.round).text)
                println(view.findViewById<TextView>(R.id.number5).text)


                success(true)
            }, Response.ErrorListener { error ->
                Log.d("ERROR", "서버 Response 가져오기 실패: $error")
                success(false)
            }) {
                override fun getBodyContentType(): String {
                    return "application/json; charset=utf-8"
                }

                override fun getBody(): ByteArray {
                    return requestBody.toByteArray()
                }
                /* getBodyContextType에서는 요청에 포함할 데이터 형식을 지정한다.
                 * getBody에서는 요청에 JSON이나 String이 아닌 ByteArray가 필요하므로, 타입을 변경한다. */
            }

        Volley.newRequestQueue(context).add(recordRequest)
    }
}