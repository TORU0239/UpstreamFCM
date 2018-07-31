package my.toru.upstreamfcm.ui

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.util.Log
import kotlinx.android.synthetic.main.activity_main.*
import my.toru.upstreamfcm.R
import my.toru.upstreamfcm.remote.CloudConnection
import java.util.concurrent.Executors

class MainActivity : AppCompatActivity() {
    companion object {
        private val TAG = MainActivity::class.java.simpleName
    }

    private lateinit var cloudConnection:CloudConnection

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

                cloudConnection = CloudConnection()

                val threadPool = Executors.newFixedThreadPool(3)
                threadPool.execute {
                    cloudConnection.connect()
                }
                threadPool.shutdown()
            }

            disconnect_btn.setOnClickListener {
                Log.w(TAG, "disconnect")
                connect_btn.isEnabled = true
                disconnect_btn.isEnabled = false
                send_test_msg_btn.isEnabled = false

                val threadPool = Executors.newFixedThreadPool(3)
                threadPool.execute {
                    cloudConnection.disconnect()
                }
                threadPool.shutdown()

                cloudConnection.disconnect()
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