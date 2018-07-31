package my.toru.upstreamfcm.util

object Util {
    val SENDER_ID   = "1002272315241"
    val ID          = "1002272315241@gcm.googleapis.com"
    val PWD         = "AAAA6VwV12k:APA91bHFIAeR067TC8-TC-W4XQSBa6tGfcdB3QqQneIgvMij26h-cDILqjRPJqwdphLnqd_hc67iN9Fm4hnn6DBPIW2t7hcPt60K4-YXgBUiBDPqOnaKwZZnOFjYjuoSKr8iPaGP2R0L"
    val HOST        = "fcm-xmpp.googleapis.com"
    val PORT        = 5235 // production
    val TEST_Port   = 5236 // test

    // For the FCM connection
    val FCM_SERVER = "fcm-xmpp.googleapis.com"
    val FCM_PORT = 5236 // testing api
    val FCM_ELEMENT_NAME = "gcm"
    val FCM_NAMESPACE = "google:mobile:data"
    val FCM_SERVER_CONNECTION = "gcm.googleapis.com"

    // For the message process types
    val BACKEND_ACTION_ECHO = "ECHO"
    val BACKEND_ACTION_MESSAGE = "MESSAGE"

    // For the app common payload message attributes (android - xmpp server)
    val PAYLOAD_ATTRIBUTE_MESSAGE = "message"
    val PAYLOAD_ATTRIBUTE_ACTION = "action"
    val PAYLOAD_ATTRIBUTE_RECIPIENT = "recipient"
    val PAYLOAD_ATTRIBUTE_ACCOUNT = "account"
}