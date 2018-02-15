package yeapcool.school_book.adapters

import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentStatePagerAdapter
import yeapcool.school_book.login.LoginFragment
import yeapcool.school_book.regiser.RegisterFragment


class LoginRegisterFragmentsPagerAdapter(fm: FragmentManager) : FragmentStatePagerAdapter(fm) {
    val COUNT_ITEMS = 2

    override fun getItem(position: Int): Fragment? = when (position) {
        0 -> LoginFragment()
        1 -> RegisterFragment()
        else -> null
    }

    override fun getCount(): Int = COUNT_ITEMS

}