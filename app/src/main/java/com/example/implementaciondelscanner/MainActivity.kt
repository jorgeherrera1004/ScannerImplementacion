package com.example.implementaciondelscanner

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.StrictMode
import android.widget.Toast
import com.example.implementaciondelscanner.databinding.ActivityMainBinding
import com.google.zxing.integration.android.IntentIntegrator
import com.google.zxing.integration.android.IntentResult
import kotlinx.android.synthetic.main.*
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.qractivity.*

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.btnScan.setOnClickListener { initScanner()}
        val policy =
            StrictMode.ThreadPolicy.Builder().permitAll().build()
        StrictMode.setThreadPolicy(policy)
        reservas.setOnClickListener() {
            var intent = Intent(this,listado_reserva::class.java)
            startActivity(intent)
        }
    }
    private fun initScanner() {
        val integrator =IntentIntegrator(this)
        integrator.setDesiredBarcodeFormats(IntentIntegrator.QR_CODE)
        integrator.setPrompt("Enfoca el codigo de barras del cliente")
        integrator.setBeepEnabled(true)
        integrator.initiateScan()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        val result =IntentIntegrator.parseActivityResult(requestCode, resultCode, data)
        if(result != null){
            if (result.contents == null){
                Toast.makeText(this,"Cancelado",Toast.LENGTH_SHORT).show()
            }
            else {
                val intent = Intent(this,ver_reserva::class.java).apply {
                    putExtra("qr","${result.contents}")
                    putExtra("key","qr")
                }
                startActivity(intent)
            }
        }else{}

        super.onActivityResult(requestCode, resultCode, data)

    }


}