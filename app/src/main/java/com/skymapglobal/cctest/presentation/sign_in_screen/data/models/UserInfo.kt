package com.skymapglobal.cctest.presentation.sign_in_screen.data.models

import kotlinx.serialization.*
import kotlinx.serialization.json.*
import kotlinx.serialization.descriptors.*
import kotlinx.serialization.encoding.*

@Serializable
data class UserInfo(
    @SerialName("access_token")
    val name: String,
)
