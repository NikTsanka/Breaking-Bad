package com.ntsan.breakingbad.base

import android.R.id.content
import android.content.Context
import android.os.Bundle
import android.view.View
import androidx.annotation.StringRes
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.ContentFrameLayout
import com.ntsan.breakingbad.R
import com.ntsan.breakingbad.data.storage.DataStore
import com.ntsan.breakingbad.utils.updateLocale

abstract class LanguageAwareActivity : AppCompatActivity() {

    private lateinit var loadingView: View
    private lateinit var contentView: ContentFrameLayout

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setTheme(R.style.Theme_BreakingBad)

        contentView = findViewById<ContentFrameLayout>(content)
        loadingView = layoutInflater.inflate(R.layout.dialog_loading, contentView, false)
    }

    override fun attachBaseContext(newBase: Context?) {
        val newLanguageContext = newBase?.let { updateLocale(it, DataStore.language) }
        super.attachBaseContext(newLanguageContext)
    }

    protected fun showDialog(@StringRes title: Int, @StringRes message: Int) {
        showDialog(title, getString(message))
    }

    protected fun showDialog(@StringRes title: Int, message: String) {
        AlertDialog.Builder(this)
            .setTitle(title)
            .setMessage(message)
            .setNeutralButton(
                R.string.common_Ok
            ) { dialog, _ -> dialog.dismiss() }
            .setCancelable(true)
            .show()
    }

    protected fun showLoading() {
        contentView.addView(loadingView)
    }

    protected fun hideLoading() {
        contentView.removeView(loadingView)
    }
}