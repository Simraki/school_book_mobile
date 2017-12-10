package yeapcool.school_book.start

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import yeapcool.school_book.R
import yeapcool.school_book.login.LoginFragment

class StartActivity : AppCompatActivity(), IStart.View {

    private lateinit var presenter: StartPresenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_start)

        init()
    }

    override fun onResume() {
        super.onResume()

        presenter.bind(this)
        presenter.isLoggedIn()
    }

    override fun onPause() {
        super.onPause()

        presenter.unbind()
    }

    private fun init() {
        presenter = StartPresenter()
    }

    override fun toMain() {
        /*val intent = Intent(this, MainActivity::class.java)
        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
        startActivity(intent)*/
    }

    override fun toLogin() {
        supportFragmentManager
                .beginTransaction()
                .replace(R.id.start_layout_content, LoginFragment(), LoginFragment::class.java.toString())
                .commit()
    }
}
