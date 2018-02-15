package yeapcool.school_book.options

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import kotlinx.android.synthetic.main.fragment_options.*
import yeapcool.school_book.R


class OptionsFragment : Fragment(), IOptions.View {

    override lateinit var imageUser: ImageView
    private lateinit var presenter: OptionsPresenter


    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? =
            inflater?.inflate(R.layout.fragment_options, container, false)

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

    override fun setUser(name: String, type: Int) {
        options_tv_fullNameUser.text = name

        when (type) {
            -1 -> {
                options_layout_options.visibility = View.GONE
            }
            1 -> {
                options_layout_ktp.visibility = View.GONE
            }
            2 -> {
                options_layout_statistics.visibility = View.GONE
                options_layout_classmates.visibility = View.GONE
                options_layout_teachers.visibility = View.GONE
                options_layout_visits.visibility = View.GONE
            }
        }
    }

    private fun init() {
        presenter = OptionsPresenter()

        imageUser = options_iv_imageUser

        options_layout_exit.setOnClickListener { presenter.clickExit() }
    }

    override fun finish() {
        activity.finish()
    }
}