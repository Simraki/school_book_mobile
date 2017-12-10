package yeapcool.school_book.model.network

import com.google.gson.annotations.SerializedName
import yeapcool.school_book.model.network.pojo.SchoolClass
import yeapcool.school_book.model.network.pojo.User


data class ServerRequest(var operation: String,
                         var user: User,
                         @SerializedName("class") var schoolClass: SchoolClass)