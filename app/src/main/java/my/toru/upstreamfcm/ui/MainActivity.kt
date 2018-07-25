package my.toru.upstreamfcm.ui

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.util.Log
import kotlinx.android.synthetic.main.activity_main.*
import my.toru.upstreamfcm.R

class MainActivity : AppCompatActivity() {
    companion object {
        private val TAG = MainActivity::class.java.simpleName
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        fun init(){
            disconnect_btn.isEnabled = false
            send_test_msg_btn.isEnabled = false

            connect_btn.setOnClickListener {
                Log.w(TAG, "connect")
                connect_btn.isEnabled = false
                disconnect_btn.isEnabled = true
                send_test_msg_btn.isEnabled = true
            }

            disconnect_btn.setOnClickListener {
                Log.w(TAG, "disconnect")
                connect_btn.isEnabled = true
                disconnect_btn.isEnabled = false
                send_test_msg_btn.isEnabled = false
            }

            send_test_msg_btn.setOnClickListener {
                Log.w(TAG, "test message")
                connect_btn.isEnabled = false
                disconnect_btn.isEnabled = true
            }
        }

        init()
    }
}