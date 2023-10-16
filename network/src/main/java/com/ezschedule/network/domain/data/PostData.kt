package com.ezschedule.network.domain.data

import com.google.gson.annotations.SerializedName
import java.util.Date

data class PostData(
    @SerializedName("date_time_post")
    val dateTimePost: Date,
    @SerializedName("id_condominium")
    val idCondominium: Long,
    @SerializedName("is_edited")
    val isEdited: Boolean,
    val textTitle: String?,
    @SerializedName("text_content")
    val textContent: String,
    @SerializedName("type_message")
    val typeMessage: String
)