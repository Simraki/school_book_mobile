package yeapcool.school_book


object Constants {

    const val BASE_URL = "http://192.168.1.155/schoolbook/index.php/"

    // Preferences
    const val PREF_APP = "yeapcool.school_book_preferences"
    const val PREF_isLoggedIn = "preferences_isLoggedIn"
    const val PREF_typeUser = "preferences_type_user"

    // Preferences_DEFAULT
    const val PREF_isLoggedIn_def = false
    const val PREF_typeUser_def = -1


    const val TICK = "✔"


    const val TAG = "school_book_tag_debug"

    // Operations
    const val OPERATION_LOGIN = "login"
    const val OPERATION_REGISTER = "register"

    // Responses
    const val SUCCESS = "success"

    // Errors
    const val ERROR_EMPTY = "Здесь пусто :("
    const val ERROR_DEFAULT = "Ошибка"
    const val NO_ERROR = ""

}