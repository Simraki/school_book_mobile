package yeapcool.school_book.model.network.api

import io.reactivex.Observable
import retrofit2.http.Body
import retrofit2.http.POST
import yeapcool.school_book.Constants
import yeapcool.school_book.model.network.ServerRequest
import yeapcool.school_book.model.network.ServerResponse


interface IApi {

    @POST(Constants.BASE_URL)
    fun post(@Body request: ServerRequest)
            : Observable<ServerResponse>
}