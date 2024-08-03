package com.example.courseskoinapp.ui.auth

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.courseskoinapp.databinding.ActivityAuthenticationBinding

class AuthenticationActivity : AppCompatActivity() {
    private lateinit var binding: ActivityAuthenticationBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAuthenticationBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }
}