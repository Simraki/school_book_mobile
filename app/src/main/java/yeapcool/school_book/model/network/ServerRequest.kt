package yeapcool.school_book.model.network

import com.google.gson.annotations.SerializedName
import yeapcool.school_book.model.data.*


data class ServerRequest(var operation: String? = null,
                         var user: User? = null,
                         var mark: Mark? = null,
                         var getterNews: GetterNews? = null,
                         var getterMarks: GetterMarks? = null,
                         var getterStudents: GetterStudents? = null,
                         var schedule: Schedule? = null,
                         var visit: Visit? = null,
                         @SerializedName("class") var schoolClass: SchoolClass? = null,
                         var homework: Homework? = null)