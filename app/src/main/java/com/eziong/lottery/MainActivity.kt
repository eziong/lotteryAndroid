package com.eziong.lottery

import android.animation.ObjectAnimator
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.os.CountDownTimer
import android.view.View
import android.view.inputmethod.EditorInfo
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.android.gms.ads.AdListener
import com.google.android.gms.ads.AdLoader
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.MobileAds
import com.google.android.gms.ads.formats.NativeAdOptions
import com.google.android.gms.ads.formats.UnifiedNativeAd
import com.google.zxing.integration.android.IntentIntegrator
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        MobileAds.initialize(
            this
        ) { }

        val adRequest = AdRequest.Builder().build()
        adView.loadAd(adRequest)



        val lotteryNumbers = arrayListOf(number1, number2, number3, number4, number5, number6)
        val countDownTimer = object : CountDownTimer(2000, 50) {
            override fun onTick(millisUntilFinished: Long) {
                val randomNumberList = arrayOfNulls<Int>(6)
                var i = 0
                while (i < 6) {
                    val randomNumber = (Math.random() * 45 + 1).toInt()
                    if (i == 0) randomNumberList[i] = randomNumber
                    else {
                        if (randomNumber in randomNumberList) {
                            i -= 1
                        } else {
                            randomNumberList[i] = randomNumber
                        }
                    }
                    i += 1
                }

                lotteryNumbers.forEach {
                    i -= 1
                    it.text = randomNumberList[i].toString()
                    when (randomNumberList[i]) {
                        in 1..9 -> it.setBackgroundResource(R.drawable.yellow_ball)
                        in 10..19 -> it.setBackgroundResource(R.drawable.blue_ball)
                        in 20..29 -> it.setBackgroundResource(R.drawable.red_ball)
                        in 30..39 -> it.setBackgroundResource(R.drawable.grey_ball)
                        in 40..49 -> it.setBackgroundResource(R.drawable.green_ball)
                    }
                }
            }

            override fun onFinish() {
            }

        }

        lotteryBtn.setOnClickListener {
            val currentDegree = lotteryBtn.rotation
            ObjectAnimator.ofFloat(lotteryBtn, View.ROTATION, currentDegree, currentDegree + 2700f)
                .setDuration(1900)
                .start()
            countDownTimer.start()

        }
        search_bar.setOnEditorActionListener { v, actionId, event ->
            if (actionId == EditorInfo.IME_ACTION_DONE) {
                if (search_bar.text.toString() != "") {
                    val intent = Intent(this, RecordsActivity::class.java)
                    intent.putExtra("r", findViewById<EditText>(R.id.search_bar).text.toString())
                    startActivity(intent)
                    true
                } else {
                    false
                }

            } else {
                false
            }
        }
    }


    fun startBarcodeReader(view: View) {
        val integrator = IntentIntegrator(this)
        integrator.setDesiredBarcodeFormats(IntentIntegrator.QR_CODE)
        integrator.setPrompt("QR코드를 스캔해주세요")
        integrator.initiateScan()
    }

    fun recordViewClick(view: View) {
        if (search_bar.text.toString() != "") {
            val intent = Intent(this, RecordsActivity::class.java)
            intent.putExtra("r", findViewById<EditText>(R.id.search_bar).text.toString())
            startActivity(intent)
            true
        } else {
            false
        }
    }


    private fun linkUrl(url: String) {
        val intent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
        startActivity(intent)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        val result = IntentIntegrator.parseActivityResult(requestCode, resultCode, data);
        if (result != null) {
            if (result.contents != null) {
                Toast.makeText(this, "${result.contents}", Toast.LENGTH_LONG).show()
                linkUrl("${result.contents}")
            } else {
                Toast.makeText(this, "취소", Toast.LENGTH_SHORT).show()
            }
        } else {
            super.onActivityResult(requestCode, resultCode, data)
        }
    }
}