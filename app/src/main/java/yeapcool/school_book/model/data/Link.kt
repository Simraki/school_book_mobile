package yeapcool.school_book.model.data

import com.google.gson.annotations.SerializedName


data class Link (@SerializedName("class") var schoolClass: SchoolClass,
                 var subject: String)