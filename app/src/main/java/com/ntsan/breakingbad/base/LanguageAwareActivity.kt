package com.ntsan.breakingbad.base

import androidx.appcompat.app.AppCompatActivity

abstract class LanguageAwareActivity : AppCompatActivity() {

    /*   private lateinit var loadingView: View
       private lateinit var contentView: ContentFrameLayout

       override fun onCreate(savedInstanceState: Bundle?) {
           super.onCreate(savedInstanceState)
           setTheme(R.style.Theme_BreakingBad)

           contentView = findViewById<ContentFrameLayout>(android.R.id.content)
           loadingView = layoutInflater.inflate(R.layout.dialog_loading, contentView, false)

       }

       override fun attachBaseContext(newBase: Context?) {
           val newLangContext = newBase?.let { updateLocale(it, DataStore.language) }
           super.attachBaseContext(newLangContext)
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
   */
}