package yeapcool.school_book.model.data


data class Mark(var student: String? = null,
                var subject: String? = null,
                var value: Int? = null,
                var final: Boolean = false,
                var description: String? = null,
                var date: String? = null)