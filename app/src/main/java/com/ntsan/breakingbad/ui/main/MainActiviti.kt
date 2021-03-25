package com.ntsan.breakingbad.ui.main

import android.os.Bundle
import com.ntsan.breakingbad.base.LanguageAwareActivity
import com.ntsan.breakingbad.databinding.MainActivityBinding

class MainActivity : LanguageAwareActivity() {

    private val binding: MainActivityBinding by lazy { MainActivityBinding.inflate(layoutInflater) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
    }
}