package yeapcool.school_book.model.preferences

import android.content.Context
import android.content.SharedPreferences
import yeapcool.school_book.Constants


class ModelPreferences(context: Context) {

    private var pref: SharedPreferences = context.getSharedPreferences(Constants.PREF_APP, Context.MODE_PRIVATE)

    fun clear() {
        pref.edit()
                .clear()
                .apply()
    }

    fun setIsLoggedIn(b: Boolean) {
        pref.edit()
                .putBoolean(Constants.PREF_isLoggedIn, b)
                .apply()
    }

    fun getIsLoggedIn() = pref.getBoolean(Constants.PREF_isLoggedIn, Constants.PREF_isLoggedIn_def)

    fun setTypeUser(type: Int) {
        pref.edit()
                .putInt(Constants.PREF_typeUser, type)
                .apply()
    }

    fun getTypeUser() = pref.getInt(Constants.PREF_typeUser, Constants.PREF_typeUser_def)
}