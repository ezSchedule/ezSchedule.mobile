package com.ezschedule.ezschedule.presenter.view

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.ezschedule.ezschedule.R
import com.ezschedule.ezschedule.presenter.viewModel.TenantViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

class MainActivity : AppCompatActivity() {
    private val viewModel by viewModel<TenantViewModel>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        viewModel.getTenant()
        viewModel.liveData.observe(this) {
            Log.i("TEST", it.toString())
        }
    }
}