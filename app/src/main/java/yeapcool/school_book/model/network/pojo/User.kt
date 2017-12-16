package yeapcool.school_book.model.network.pojo


data class User(var id_user: Int? = null,
                var un_id: String? = null,
                var email: String? = null,
                var password: String? = null,
                var name: String? = null,
                var surname: String? = null,
                var type: Int? = null,
                var birthDate: String? = null,
                var rcu: String? = null,
                var speciality: ArrayList<String>? = null,
                var classStudent: SchoolClass? = null)