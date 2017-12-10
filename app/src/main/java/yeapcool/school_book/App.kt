package yeapcool.school_book

import android.app.Application
import yeapcool.school_book.model.preferences.PreferencesInst


class App : Application() {

    override fun onCreate() {
        super.onCreate()

        PreferencesInst.init(applicationContext)
    }

}