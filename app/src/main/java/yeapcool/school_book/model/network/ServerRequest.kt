package yeapcool.school_book.model.network

import com.google.gson.annotations.SerializedName
import yeapcool.school_book.model.network.pojo.SchoolClass
import yeapcool.school_book.model.network.pojo.User


data class ServerRequest(var operation: String? = null,
                         var user: User? = null,
                         @SerializedName("class") var schoolClass: SchoolClass? = null)