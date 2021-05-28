package com.decagon.android.sq007.implementationTwo.myApi

data class UploadRes(
    val message: String,
    val payload: Payload,
    val status: Int
)

data class Payload(
    val downloadUri: String,
    val fileId: String,
    val fileName: String,
    val fileType: String,
    val uploadStatus: Boolean
)
