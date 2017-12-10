package yeapcool.school_book.model

import yeapcool.school_book.model.network.pojo.User


object UserModel {

    private var user: User? = null

    fun getUser(): User? = user

    fun saveUser(user: User) {
        this.user = user
    }

    fun clear() {
        user = null
    }
}