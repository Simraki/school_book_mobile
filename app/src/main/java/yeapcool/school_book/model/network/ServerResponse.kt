package yeapcool.school_book.model.network

import com.google.gson.annotations.SerializedName
import yeapcool.school_book.model.data.*


data class ServerResponse(var result: String,
                          var message: String? = null,
                          var user: User? = null,
                          var users: ArrayList<User>? = null,
                          @SerializedName("class") var schoolClass: SchoolClass? = null,
                          var getterNews: GetterNews? = null,
                          var news: ArrayList<News>? = null,
                          var getterMarks: GetterMarks? = null,
                          var marks: ArrayList<Mark>? = null,
                          var schedule: Schedule? = null,
                          var links: ArrayList<Link>? = null,
                          var homework: ArrayList<News>? = null)