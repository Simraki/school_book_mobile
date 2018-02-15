package yeapcool.school_book

import android.content.Context
import android.util.DisplayMetrics
import yeapcool.school_book.model.network.ServerResponse


object HelpMethods {

    fun responseError(response: ServerResponse): String {
        val message = response.message
        return if (message != null && !message.isEmpty())
            message
        else
            Constants.ERROR
    }

    fun dpToPx(context: Context, dp: Int) = Math.round(dp * (context.resources.displayMetrics.xdpi / DisplayMetrics.DENSITY_DEFAULT))

}