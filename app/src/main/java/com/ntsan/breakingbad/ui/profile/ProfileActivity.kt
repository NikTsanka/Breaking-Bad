package com.ntsan.breakingbad.ui.profile

import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.bumptech.glide.Glide
import com.ntsan.breakingbad.R
import com.ntsan.breakingbad.base.LanguageAwareActivity
import com.ntsan.breakingbad.data.storage.DataStore
import com.ntsan.breakingbad.databinding.ActivityProfileBinding
import com.ntsan.breakingbad.ui.dialogs.LanguagePickerBottomSheet
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.io.IOException

class ProfileActivity : LanguageAwareActivity() {

    private lateinit var binding: ActivityProfileBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setTheme(R.style.Theme_BreakingBad)

        binding = ActivityProfileBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.languageBtn.setOnClickListener {
            val languagePickerBottomSheet = LanguagePickerBottomSheet()
            languagePickerBottomSheet.show(supportFragmentManager, "tag")
        }

        binding.logoutBtn.setOnClickListener {
            onBackPressed()
        }
    }
}