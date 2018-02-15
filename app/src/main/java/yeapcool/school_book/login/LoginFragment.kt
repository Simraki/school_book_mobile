package yeapcool.school_book.login

import android.content.Intent
import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kotlinx.android.synthetic.main.fragment_login.*
import yeapcool.school_book.R
import yeapcool.school_book.main.MainActivity


class LoginFragment : Fragment(), ILogin.View {

    private lateinit var presenter: LoginPresenter

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? =
            inflater?.inflate(R.layout.fragment_login, container, false)

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        init()
    }

    override fun onResume() {
        super.onResume()

        presenter.bind(this)
    }

    override fun onPause() {
        super.onPause()

        presenter.unbind()
    }

    override fun onDestroy() {
        super.onDestroy()

        presenter.clearTh()
    }

    private fun init() {
        presenter = LoginPresenter()
        login_btn_login.setOnClickListener { listenerBtnLogin() }
    }

    private fun listenerBtnLogin() {
        val email = login_et_email.text.toString()
        val password = login_et_pass.text.toString()

        presenter.clickBtnLogin(email, password)
    }

    override fun showLoad() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun showSnackbar(message: String) {
        val v = view
        v?.let {
            Snackbar.make(v, message, Snackbar.LENGTH_SHORT).show()
        }
    }

    override fun toMain() {
        val intent = Intent(context, MainActivity::class.java)
        startActivity(intent)
        activity.finish()
    }

    override fun errorEmail(error: String) {
        login_til_email.error = error
    }

    override fun errorPassword(error: String) {
        login_til_pass.error = error
    }
}