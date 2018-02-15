package yeapcool.school_book.model.preferences

import android.content.Context
import android.util.Log
import yeapcool.school_book.Constants


object PreferencesInst {

    var instance: ModelPreferences? = null

    fun init(context: Context) {
        instance = ModelPreferences(context)
    }

    fun get() = instance

    fun clear() {
        instance?.clear()
        instance = null
    }
}