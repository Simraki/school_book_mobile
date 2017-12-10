package yeapcool.school_book.model.network

import com.google.gson.annotations.SerializedName
import yeapcool.school_book.model.network.pojo.SchoolClass
import yeapcool.school_book.model.network.pojo.User


data class ServerResponse(var result: String,
                          var message: String,
                          var user: User,
                          @SerializedName("class") var schoolClass: SchoolClass)