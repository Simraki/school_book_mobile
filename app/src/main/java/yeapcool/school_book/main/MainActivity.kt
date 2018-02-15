package yeapcool.school_book.main

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.content.ContextCompat
import android.support.v7.app.AppCompatActivity
import com.aurelhubert.ahbottomnavigation.AHBottomNavigation
import com.aurelhubert.ahbottomnavigation.AHBottomNavigationItem
import kotlinx.android.synthetic.main.activity_main.*
import yeapcool.school_book.R
import yeapcool.school_book.marksStudent.MarksStudentFragment
import yeapcool.school_book.marksTeacher.MarksTeacherFragment
import yeapcool.school_book.news.NewsFragment
import yeapcool.school_book.options.OptionsFragment
import yeapcool.school_book.tomorrow.TomorrowFragment


class MainActivity : AppCompatActivity(), IMain.View, AHBottomNavigation.OnTabSelectedListener {

    private lateinit var presenter: MainPresenter

    private var nInitApp = true

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        init()
    }

    private fun init() {
        presenter = MainPresenter()

        setSupportActionBar(main_toolbar_toolbar)

        main_bt_navigation.addItem(AHBottomNavigationItem("Новости", R.drawable.ic_news))
        main_bt_navigation.addItem(AHBottomNavigationItem("Завтра", R.drawable.ic_tomorrow))
        main_bt_navigation.addItem(AHBottomNavigationItem("Сообщения", R.drawable.ic_messages))
        main_bt_navigation.addItem(AHBottomNavigationItem("Оценки", R.drawable.ic_marks))
        main_bt_navigation.addItem(AHBottomNavigationItem("Опции", R.drawable.ic_options))

        main_bt_navigation.titleState = AHBottomNavigation.TitleState.ALWAYS_HIDE
        main_bt_navigation.isForceTint = false
        main_bt_navigation.accentColor = ContextCompat.getColor(this, R.color.colorPrimary)
        main_bt_navigation.inactiveColor = ContextCompat.getColor(this, R.color.colorBottomNavBarInActive)
        main_bt_navigation.defaultBackgroundColor = ContextCompat.getColor(this, R.color.colorSilverOverLight)

        main_bt_navigation.setOnTabSelectedListener(this)
        main_bt_navigation.currentItem = 0
    }

    override fun onTabSelected(position: Int, wasSelected: Boolean): Boolean {
        var fragment: Fragment? = null

        when (position) {
            0 -> {
                title = "Новости"
                fragment = NewsFragment()
            }
            1 -> {
                title = "Расписание"
                fragment = TomorrowFragment()
            }
            2 -> {
                title = "Сообщения"
            }
            3 -> {
                title = "Оценки"
                when (presenter.getType()) {
                    1 -> fragment = MarksStudentFragment()
                    2 -> fragment = MarksTeacherFragment()
                    3 -> {
                    }
                }
            }
            4 -> {
                title = "Меню"
                fragment = OptionsFragment()
            }
        }

        if (fragment != null && !nInitApp) {
            val currentFragment = supportFragmentManager.findFragmentById(R.id.main_layout_content)

            if (currentFragment != null && fragment::class.java.toString() != currentFragment.tag) {
                supportFragmentManager
                        .beginTransaction()
                        .replace(R.id.main_layout_content, fragment, fragment::class.java.toString())
                        .commit()
            }
        } else if (fragment != null && nInitApp) {
            supportFragmentManager
                    .beginTransaction()
                    .replace(R.id.main_layout_content, fragment, fragment::class.java.toString())
                    .commit()
            nInitApp = false
        }
        return true
    }


    override fun onResume() {
        super.onResume()

        presenter.bind(this)
    }

    override fun onPause() {
        super.onPause()

        presenter.unbind()
    }

}