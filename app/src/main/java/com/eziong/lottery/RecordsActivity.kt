package com.eziong.lottery

import android.os.Bundle
import android.os.PersistableBundle
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import java.io.BufferedReader
import java.io.InputStream
import java.net.URL
import java.util.*

class RecordsActivity: AppCompatActivity() {



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        VolleyService(10).recordVolley(this) { testSuccess ->
            if (testSuccess) {
                setContentView(R.layout.activity_record_item)
                Toast.makeText(this, "통신 성공!", Toast.LENGTH_LONG).show()
            } else {
                Toast.makeText(this, "통신 실패...!", Toast.LENGTH_LONG).show()
            }
        }
    }
}
