package com.ezschedule.ezschedule.presenter.utils

fun String.isValidEmail() =
    Regex("^([a-zA-Z0-9_.]+)@([a-zA-Z0-9_.]+)\\.([a-zA-Z]{2,6})$").matches(this)