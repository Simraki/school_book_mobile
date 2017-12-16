package yeapcool.school_book.regiser

import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.RadioGroup
import kotlinx.android.synthetic.main.fragment_register.*
import yeapcool.school_book.OnSwipeTouchListener
import yeapcool.school_book.R
import yeapcool.school_book.adapters.specialityAdapter.SpecialityAdapter
import yeapcool.school_book.login.LoginFragment
import yeapcool.school_book.model.network.pojo.SchoolClass


class RegisterFragment : Fragment(), IRegister.View, View.OnClickListener, RadioGroup.OnCheckedChangeListener {

    private lateinit var presenter: RegisterPresenter

    private var typeUser = 1
    private var classNumber: String? = null
    private var classLetter: String? = null
    private var birthDay: String? = null
    private var birthMonth: String? = null

    private var adapterSpec: SpecialityAdapter? = null

    private var initializedStudent = false
    private var initializedTeacher = false
    private var initializedParent = false

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? =
            inflater?.inflate(R.layout.fragment_register, container, false)

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

        register_layout_main.setOnTouchListener(OnSwipeTouchListener(context, onSwipeRight = { presenter.toLogin() }))


        presenter = RegisterPresenter()

        val arrayBirthDay = Array(31, { it -> it + 1 })

        val adapterBirthDay = ArrayAdapter(context, android.R.layout.simple_spinner_dropdown_item, arrayBirthDay)

        val adapterBirthMonth = ArrayAdapter.createFromResource(context, R.array.birthMonth, android.R.layout.simple_spinner_dropdown_item)

        register_spinner_birthDay.adapter = adapterBirthDay
        register_spinner_birthMonth.adapter = adapterBirthMonth

        register_spinner_birthDay.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>,
                                        itemSelected: View, selectedItemPosition: Int, selectedId: Long) {

                birthDay = arrayBirthDay[selectedItemPosition].toString()
            }

            override fun onNothingSelected(parent: AdapterView<*>) {}
        }

        register_spinner_birthMonth.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>,
                                        itemSelected: View, selectedItemPosition: Int, selectedId: Long) {

                val array = resources.getStringArray(R.array.birthMonth)
                birthMonth = array[selectedItemPosition]
            }

            override fun onNothingSelected(parent: AdapterView<*>) {}
        }

        register_rg_typeUser.setOnCheckedChangeListener(this)
        register_btn_backToStepOne.setOnClickListener(this)
        register_btn_backToStepTwo.setOnClickListener(this)
        register_btn_toStepTwo.setOnClickListener(this)
        register_btn_toStepThree.setOnClickListener(this)
        register_btn_register.setOnClickListener(this)
    }

    private fun initStudent() {

        if (!initializedStudent) {

            val arrayClassNumber = Array(11, { it -> it + 1 })

            val adapterNumber = ArrayAdapter(context, android.R.layout.simple_spinner_dropdown_item, arrayClassNumber)
            val adapterLetter = ArrayAdapter.createFromResource(context, R.array.classLetter, android.R.layout.simple_spinner_dropdown_item)

            register_spinner_classNumber.adapter = adapterNumber
            register_spinner_classLetter.adapter = adapterLetter

            register_spinner_classNumber.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
                override fun onItemSelected(parent: AdapterView<*>,
                                            itemSelected: View, selectedItemPosition: Int, selectedId: Long) {

                    classNumber = arrayClassNumber[selectedItemPosition].toString()
                }

                override fun onNothingSelected(parent: AdapterView<*>) {}
            }

            register_spinner_classLetter.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
                override fun onItemSelected(parent: AdapterView<*>,
                                            itemSelected: View, selectedItemPosition: Int, selectedId: Long) {

                    val array = resources.getStringArray(R.array.classLetter)
                    classLetter = array[selectedItemPosition]
                }

                override fun onNothingSelected(parent: AdapterView<*>) {}
            }
        }

    }

    private fun initTeacher() {

        if (!initializedTeacher) {
            val speciality = arrayListOf("Литературное чтение", "Основы духовной культуры", "Обществознание", "Окружающий мир",
                    "Музыка", "Технология", "ИЗО", "ОБЖ", "Физкультура", "Литература", "Химия", "Информатика", "История", "Биология", "География", "Физика"
                    , "Английский язык", "Русский язык", "Математика")

            adapterSpec = SpecialityAdapter(context, speciality)


            register_rv_specialty.layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
            register_rv_specialty.adapter = adapterSpec
        }

    }

    private fun initParent() {}

    override fun showSnackbar(message: String) {
        val v = view
        v?.let {
            Snackbar.make(v, message, Snackbar.LENGTH_SHORT).show()
        }
    }

    override fun toStepOne() {
        if (register_layout_stepOne.visibility != View.VISIBLE) {
            register_layout_stepOne.visibility = View.VISIBLE
            register_layout_stepTwo.visibility = View.GONE
            register_layout_stepThree.visibility = View.GONE
        }
    }

    override fun toStepTwo() {
        if (register_layout_stepTwo.visibility != View.VISIBLE) {
            register_layout_stepOne.visibility = View.GONE
            register_layout_stepTwo.visibility = View.VISIBLE
            register_layout_stepThree.visibility = View.GONE

            register_rb_parent.visibility = View.GONE               // Заглушка на родителя
        }
        when (typeUser) {
            1 -> register_layout_stepThreeStudent.visibility = View.GONE
            2 -> register_layout_stepThreeTeacher.visibility = View.GONE
            3 -> register_layout_stepThreeParent.visibility = View.GONE
        }
    }

    override fun toStepThree() {
        if (register_layout_stepThree.visibility != View.VISIBLE) {
            register_layout_stepOne.visibility = View.GONE
            register_layout_stepTwo.visibility = View.GONE
            register_layout_stepThree.visibility = View.VISIBLE

            when (typeUser) {
                1 -> {
                    initStudent()
                    register_layout_stepThreeStudent.visibility = View.VISIBLE
                }
                2 -> {
                    initTeacher()
                    register_layout_stepThreeTeacher.visibility = View.VISIBLE
                }
                3 -> register_layout_stepThreeParent.visibility = View.VISIBLE
            }
        }
    }

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.register_btn_toStepTwo -> presenter.clickBtnToStepTwo(register_et_email.text.toString(), register_et_pass.text.toString(), birthDay, birthMonth)
            R.id.register_btn_toStepThree -> presenter.clickBtnToStepThree(register_et_name.text.toString(), register_et_surname.text.toString(), typeUser)
            R.id.register_btn_backToStepOne -> presenter.clickBtnBackToStepOne()
            R.id.register_btn_backToStepTwo -> presenter.clickBtnBackToStepTwo()
            R.id.register_btn_register -> when (typeUser) {
                1 -> {
                    val schoolClass = SchoolClass(number = classNumber, letter = classLetter)
                    presenter.clickBtnRegisterStudent(schoolClass)
                }
                2 -> {
                    val list = adapterSpec?.getList()
                    presenter.clickBtnRegisterTeacher(list)
                }
                3 -> {

                }
            }
        }
    }

    override fun onCheckedChanged(group: RadioGroup?, checkedId: Int) {
        when (checkedId) {
            -1 -> typeUser = -1
            R.id.register_rb_student -> typeUser = 1
            R.id.register_rb_teacher -> typeUser = 2
            R.id.register_rb_parent -> typeUser = 3
        }
    }


    override fun errorEmail(error: String) {
        register_til_email.error = error
    }

    override fun errorPassword(error: String) {
        register_til_pass.error = error
    }

    override fun errorName(error: String) {
        register_til_name.error = error
    }

    override fun errorSurname(error: String) {
        register_til_surname.error = error
    }

    override fun toLogin() {
        activity.supportFragmentManager
                .beginTransaction()
                .setCustomAnimations(R.animator.fragment_slide_right_start, R.animator.fragment_slide_right_end)
                //.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
                .replace(R.id.start_layout_content, LoginFragment(), LoginFragment::class.java.toString())
                .commit()
    }
}