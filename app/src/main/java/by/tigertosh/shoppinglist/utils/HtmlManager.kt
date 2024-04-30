package by.tigertosh.shoppinglist.utils

import android.annotation.SuppressLint
import android.os.Build.VERSION.SDK_INT
import android.text.Html
import android.text.Spanned

@Suppress("DEPRECATION")
object HtmlManager {
    @SuppressLint("ObsoleteSdkInt")
    fun getFromHtml(text: String): Spanned {
        return if (SDK_INT <= android.os.Build.VERSION_CODES.N) {
            Html.fromHtml(text)
        } else {
            Html.fromHtml(text, Html.FROM_HTML_MODE_COMPACT)
        }
    }

    @SuppressLint("ObsoleteSdkInt")
    fun toHtml(text: Spanned): String {
        return if (SDK_INT <= android.os.Build.VERSION_CODES.N) {
            Html.toHtml(text)
        } else {
            Html.toHtml(text, Html.FROM_HTML_MODE_COMPACT)
        }
    }


}