package yeapcool.school_book.model

import android.util.Log
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import yeapcool.school_book.Constants
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
                    ?.subscribeOn(Schedulers.computation())
                    ?.filter { it -> it != null }
                    ?.observeOn(AndroidSchedulers.mainThread())
                    ?.onErrorReturn {
                        Log.i(Constants.TAG, it.toString())
                        ServerResponse(Constants.FAIL) }

    fun user(): UserModel = user
}