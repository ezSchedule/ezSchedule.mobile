package com.ezschedule.ezschedule.presenter.view

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.ezschedule.ezschedule.databinding.ActivityMainBinding
import com.ezschedule.ezschedule.presenter.viewModel.TenantViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

class MainActivity : AppCompatActivity() {
    private val viewModel by viewModel<TenantViewModel>()
    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnRequest.setOnClickListener {
            viewModel.getTenant()
        }

        setupObserver()
    }

    private fun setupObserver() {
        viewModel.liveData.observe(this) {
            Log.i("TEST", it.toString())
        }
    }
}