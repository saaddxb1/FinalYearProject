package com.example.finalyearproject.model

import androidx.annotation.Keep

@Keep
data class Message(
//     var messageid: String?=null,
    var message: String?=null,    // ?= can hold null value    // !! null value nahi a sakti exceptiobn throw karega
    var senderid: String?=null,
    var dateUser: String?= null,
//    var senderName: String?=null,

)

