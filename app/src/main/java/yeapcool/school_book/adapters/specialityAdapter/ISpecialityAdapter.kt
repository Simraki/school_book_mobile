package yeapcool.school_book.adapters.specialityAdapter


interface ISpecialityAdapter {

    interface View {
        fun getItem(position: Int): String?
    }

    interface Model {
        fun notifyAdapter()

        fun getList() : ArrayList<String>
    }

}