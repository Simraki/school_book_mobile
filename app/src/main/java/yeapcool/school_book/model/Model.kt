package yeapcool.school_book.model

import io.reactivex.Observable
import io.reactivex.schedulers.Schedulers
import yeapcool.school_book.model.network.ServerRequest
import yeapcool.school_book.model.network.ServerResponse
import yeapcool.school_book.model.network.api.Api
import yeapcool.school_book.model.preferences.ModelPreferences
import yeapcool.school_book.model.preferences.PreferencesInst


class Model {

    private val pref = PreferencesInst.get()
    private val api = Api.getService()
    private val user = UserModel

    fun pref(): ModelPreferences? = pref

    fun network(serverRequest: ServerRequest): Observable<ServerResponse>? =
            api?.post(serverRequest)
                    ?.subscribeOn(Schedulers.newThread())
                    ?.filter { it -> it != null }

    fun user(): UserModel = user
}