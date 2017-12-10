package yeapcool.school_book.model.network.pojo


data class User(var id_user: Int,
                var un_id: String,
                var email: String,
                var name: String,
                var surname: String,
                var type: Int,
                var birthDate: String,
                var rcu: String,
                var speciality: ArrayList<String>,
                var classStudent: SchoolClass)