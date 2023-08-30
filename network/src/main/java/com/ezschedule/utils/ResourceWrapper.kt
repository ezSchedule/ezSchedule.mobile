package com.ezschedule.utils

import android.content.Context

class ResourceWrapper(private val context: Context) {
    fun getString(resString: Int) = context.getString(resString)

    fun getString(resString: Int, vararg formatArgs: Any) =
        context.getString(resString, *formatArgs)
}