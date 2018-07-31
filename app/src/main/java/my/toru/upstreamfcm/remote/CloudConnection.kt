package my.toru.upstreamfcm.remote

import android.os.HandlerThread
import android.util.Log
import com.google.gson.Gson
import my.toru.upstreamfcm.model.ControlMessage
import my.toru.upstreamfcm.model.MessageHelper
import my.toru.upstreamfcm.util.Util
import org.jivesoftware.smack.*
import org.jivesoftware.smack.packet.Stanza
import org.jivesoftware.smack.provider.ExtensionElementProvider
import org.jivesoftware.smack.provider.ProviderManager
import org.jivesoftware.smack.roster.Roster
import org.jivesoftware.smack.sm.predicates.ForEveryStanza
import org.jivesoftware.smack.tcp.XMPPTCPConnection
import org.jivesoftware.smack.tcp.XMPPTCPConnectionConfiguration
import org.xmlpull.v1.XmlPullParser
import org.xmlpull.v1.XmlPullParserException
import java.io.IOException
import java.lang.Exception
import java.net.InetAddress
import javax.net.ssl.SSLSocketFactory

class CloudConnection:HandlerThread(TAG), StanzaListener{

    companion object {
        private val TAG = CloudConnection::class.java.simpleName
        private val ACK     = "ack"
        private val NACK    = "nack"
        private val NORMAL  = "normal"
        private val RECEIPT = "receipt"
        private val CONTROL = "control"

        private val ID = "1002272315241@gcm.googleapis.com"
        private val pwd = "AAAA6VwV12k:APA91bHFIAeR067TC8-TC-W4XQSBa6tGfcdB3QqQneIgvMij26h-cDILqjRPJqwdphLnqd_hc67iN9Fm4hnn6DBPIW2t7hcPt60K4-YXgBUiBDPqOnaKwZZnOFjYjuoSKr8iPaGP2R0L"
        private val host = "fcm-xmpp.googleapis.com"
        //        int port = 5236; // test
        private val port = 5235 // production
    }

    init {
        ProviderManager.addExtensionProvider(Util.FCM_ELEMENT_NAME, Util.FCM_NAMESPACE,
                object : ExtensionElementProvider<FCMPacketExtension>() {
                    @Throws(XmlPullParserException::class, IOException::class, SmackException::class)
                    override fun parse(parser: XmlPullParser, initialDepth: Int): FCMPacketExtension {
                        val json = parser.nextText()
                        return FCMPacketExtension(json)
                    }
                })
    }

    private lateinit var connection:XMPPTCPConnection
    private var connected = false

    override fun processStanza(packet: Stanza?) {}

    fun connect(){
        val ipAddress = InetAddress.getByName(host)
        val config = XMPPTCPConnectionConfiguration.builder()
                .apply {
                    setUsernameAndPassword(ID, pwd)
                    setXmppDomain("FCM XMPP Client Connection Server (Android)")
                    setPort(port)
                    setHostAddress(ipAddress)
                    setSecurityMode(ConnectionConfiguration.SecurityMode.ifpossible)
                    setSendPresence(false)
                    setSocketFactory(SSLSocketFactory.getDefault())
                    setDebuggerEnabled(false)
                }.build()

        connection = XMPPTCPConnection(config)
        connection.let{
            ReconnectionManager.getInstanceFor(it).enableAutomaticReconnection()
            Roster.getInstanceFor(it).isRosterLoadedAtLogin = false

            it.addConnectionListener(object:ConnectionListener{
                override fun connected(connection: XMPPConnection?) {
                    connected = true
                    Log.w(TAG, "connected")
                }

                override fun connectionClosed() {
                    Log.w(TAG, "connection closed")
                }

                override fun connectionClosedOnError(e: Exception?) {}

                override fun reconnectionSuccessful() {}

                override fun authenticated(connection: XMPPConnection?, resumed: Boolean) {}

                override fun reconnectionFailed(e: Exception?) {}

                override fun reconnectingIn(seconds: Int) {}
            })

            it.addAsyncStanzaListener({
                val fcmPacket = it.getExtension(Util.FCM_NAMESPACE) as FCMPacketExtension
                val json = fcmPacket.json
                val jsonMap = Gson().fromJson(json, Map::class.java)
                val msg = Gson().fromJson(json, ControlMessage::class.java)

                if (msg.message_type == null) { // normal upstream message
                    val inMessage = MessageHelper.createInMessage(json)
//                    handleUpstreamMessage(inMessage)
                }
                else{
                    when(msg.message_type) {
                        ACK     -> Log.w(TAG, "default case")
                        NACK    -> Log.w(TAG, "default case")
                        NORMAL  -> Log.w(TAG, "default case")
                        RECEIPT -> Log.w(TAG, "default case")
                        CONTROL -> Log.w(TAG, "default case")
                        else -> Log.w(TAG, "default case")
                    }
                }

            },{
                it.hasExtension(Util.FCM_ELEMENT_NAME, Util.FCM_NAMESPACE)
            })

            it.addPacketInterceptor(StanzaListener {
                Log.i(TAG, it.toXML().toString())
            }, ForEveryStanza.INSTANCE)

            SASLAuthentication.unBlacklistSASLMechanism("PLAIN")
            SASLAuthentication.blacklistSASLMechanism("DIGEST-MD5")

            it.connect()
            Log.w(TAG, "id::$ID, pwd:$pwd")
            it.login(ID, pwd)
        }
    }

    fun disconnect(){
        if(connected) connection.disconnect()
    }
}