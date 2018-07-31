package my.toru.upstreamfcm.remote

import org.jivesoftware.smack.packet.ExtensionElement
import org.jivesoftware.smack.packet.Message
import org.jivesoftware.smack.packet.Stanza

class FCMPacketExtension(var json:String) : ExtensionElement{

    companion object {
        const val FCM_ELEMENT_NAME = "gcm"
        const val FCM_NAMESPACE = "google:mobile:data"
    }

    override fun toXML(): String = String.format("<%s xmlns=\"%s\">%s</%s>", elementName, namespace, json, FCM_ELEMENT_NAME)

    override fun getNamespace(): String = FCM_NAMESPACE

    override fun getElementName(): String = FCM_ELEMENT_NAME

    fun toPacket():Stanza{
        val message = Message()
        message.addExtension(this)
        return message
    }
}