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

    fun getIsLoggedIn() = pref.getBoolean(Constants.PREF_isLoggedIn, false)

    fun setTypeUser(type: Int) {
        pref.edit()
                .putInt(Constants.PREF_typeUser, type)
                .apply()
    }

    fun getTypeUser() = pref.getInt(Constants.PREF_typeUser, Constants.PREF_number_def)

    fun setName(name: String?) {
        pref.edit()
                .putString(Constants.PREF_name, name)
                .apply()
    }

    fun getName() = pref.getString(Constants.PREF_name, Constants.ERROR)

    fun setSurname(surname: String?) {
        pref.edit()
                .putString(Constants.PREF_surname, surname)
                .apply()
    }

    fun getSurname() = pref.getString(Constants.PREF_surname, Constants.ERROR)

    fun setUn_id(un_id: String?) {
        pref.edit()
                .putString(Constants.PREF_unId, un_id)
                .apply()
    }

    fun getUn_id() = pref.getString(Constants.PREF_unId, Constants.ERROR)

    fun setId_user(id_user: Int) {
        pref.edit()
                .putInt(Constants.PREF_idUser, id_user)
                .apply()
    }

    fun getId_user() = pref.getInt(Constants.PREF_idUser, Constants.PREF_number_def)

    fun setId_class(id_class: Int) {
        pref.edit()
                .putInt(Constants.PREF_idClass, id_class)
                .apply()
    }

    fun getId_class() = pref.getInt(Constants.PREF_idClass, Constants.PREF_number_def)

    fun setClassNumber(class_number: Int) {
        pref.edit()
                .putInt(Constants.PREF_classNumber, class_number)
                .apply()
    }

    fun getClassNumber() = pref.getInt(Constants.PREF_classNumber, Constants.PREF_number_def)

    fun setClassLetter(class_letter: String?) {
        pref.edit()
                .putString(Constants.PREF_classLetter, class_letter)
                .apply()
    }

    fun getClassLetter() = pref.getString(Constants.PREF_classLetter, Constants.ERROR)
}