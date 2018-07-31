package my.toru.upstreamfcm.model

data class DataStatus(val message_status:String,
                      val original_message:String,
                      val device_registration:String,
                      val message_sent_timestamp:String)

data class ControlMessage(val message_type:String?,
                          val from:String,
                          val message_id:String,
                          val category:String,
                          val data:DataStatus)