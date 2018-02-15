package yeapcool.school_book.start

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.util.Log
import kotlinx.android.synthetic.main.activity_start.*
import yeapcool.school_book.Constants
import yeapcool.school_book.R
import yeapcool.school_book.adapters.LoginRegisterFragmentsPagerAdapter
import yeapcool.school_book.main.MainActivity

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
        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
    }

    override fun toLogin() {
        val adapter = LoginRegisterFragmentsPagerAdapter(supportFragmentManager)
        start_vp_content.adapter = adapter
    }

    override fun onDestroy() {
        super.onDestroy()
    }
}
