package yeapcool.school_book


object Constants {

    const val BASE_URL = "http://192.168.1.156/schoolbook/index.php/"


    // Preferences
    const val PREF_APP = "yeapcool.school_book_preferences"
    const val PREF_isLoggedIn = "preferences_isLoggedIn"
    const val PREF_typeUser = "preferences_type_user"
    const val PREF_unId = "preferences_un_id"
    const val PREF_idUser = "preferences_id_user"
    const val PREF_idClass = "preferences_id_class"
    const val PREF_classNumber = "preferences_class_number"
    const val PREF_classLetter = "preferences_class_letter"
    const val PREF_name = "preferences_name"
    const val PREF_surname = "preferences_surname"

    // Preferences_DEFAULT
    const val PREF_number_def = -1

    // Register_fragment
    const val REGISTER_GOOD = "Вы зарегестрировались :D"

    // Register_fragment
    const val LOGIN_BAD = "Вы  :D"

    const val TICK = "✔"

    const val TAG = "school_book_tag_debug"

    // Operations - Student
    const val OPERATION_LOGIN = "login"
    const val OPERATION_REGISTER = "register"
    const val OPERATION_GET_NEWS = "getNews"
    const val OPERATION_GET_MARKS = "getMarks"
    const val OPERATION_GET_SCHEDULE = "getSchedule"
    const val OPERATION_GET_HOMEWORK = "getHomework"


    // Operations - Teacher
    const val OPERATION_GET_CLASSES_TEACHER = "getClassesTeacher"
    const val OPERATION_GET_STUDENTS = "getStudents"
    const val OPERATION_SET_VISIT = "setVisit"
    const val OPERATION_SET_MARK = "setMark"
    const val OPERATION_GET_SOME_MARKS = "getMarksVisitTeacher"

    // Responses
    const val SUCCESS = "success"
    const val FAIL = "fail"

    // Errors
    const val ERROR = "Ошибка"
    const val ERROR_EMPTY = "Здесь пусто :("
    const val ERROR_NOT_CHOSEN_SPECIALITY = "Не выбрана специальность"
    const val SERVER_ERROR = "Ошибка на сервере"
    const val ERROR_DEFAULT = "Нежданная ошибка"
    const val NO_ERROR = ""

}