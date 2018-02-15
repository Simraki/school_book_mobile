package yeapcool.school_book.adapters.marksTeacherAdapter

import android.support.constraint.ConstraintLayout
import android.support.v7.widget.CardView
import android.support.v7.widget.RecyclerView
import android.transition.ChangeBounds
import android.transition.TransitionManager
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import yeapcool.school_book.R
import yeapcool.school_book.model.data.User


class MarksTeacherViewHolder(val view: View, val listenerVisit: ((Int) -> Unit)?, val listenerTwo: ((Int) -> Unit)?, val listenerThree: ((Int) -> Unit)?,
                             val listenerFour: ((Int) -> Unit)?, val listenerFive: ((Int) -> Unit)?, val listenerDelete: ((Int) -> Unit)?) : RecyclerView.ViewHolder(view) {

    val cv = itemView.findViewById<CardView>(R.id.rowMarksTeacher_cv_marksTeacher)
    val layoutStudent = itemView.findViewById<ConstraintLayout>(R.id.rowMarksTeacher_layout_student)
    val layoutMarks = itemView.findViewById<ConstraintLayout>(R.id.rowMarksTeacher_layout_marks)
    val name = itemView.findViewById<TextView>(R.id.rowMarksTeacher_tv_name)
    val marks = itemView.findViewById<TextView>(R.id.rowMarksTeacher_tv_marks)
    val nVisit = itemView.findViewById<TextView>(R.id.rowMarksTeacher_tv_Nvisit)
    val markTwo = itemView.findViewById<TextView>(R.id.rowMarksTeacher_tv_mark_two)
    val markThree = itemView.findViewById<TextView>(R.id.rowMarksTeacher_tv_mark_three)
    val markFour = itemView.findViewById<TextView>(R.id.rowMarksTeacher_tv_mark_four)
    val markFive = itemView.findViewById<TextView>(R.id.rowMarksTeacher_tv_mark_five)
    val deleteMark = itemView.findViewById<ImageView>(R.id.rowMarksTeacher_iv_deleteMark)

    fun bind (item: User?, unfolded: ArrayList<Boolean>, position: Int) {

        val n = item?.name
        val surn = item?.surname
        val marksVisit = item?.marksVisit;

        if (n != null && surn != null && !n.isEmpty() && !surn.isEmpty()) {
           val text = surn + " " + n
            name.text = text
        }

        if (marksVisit != null && !marksVisit.isEmpty()) {
            var textMarksVisit = ""
            marksVisit.forEach { it ->
                textMarksVisit = textMarksVisit + it + ", "
            }
            if (!textMarksVisit.isEmpty())
                textMarksVisit = textMarksVisit.substring(0, textMarksVisit.length - 2)
            marks.text = textMarksVisit
        }


        listenerVisit?.let {
            nVisit.setOnClickListener { listenerVisit.invoke(position) }
        }
        listenerTwo?.let {
            markTwo.setOnClickListener { listenerTwo.invoke(position) }
        }
        listenerThree?.let {
            markThree.setOnClickListener { listenerThree.invoke(position) }
        }
        listenerFour?.let {
            markFour.setOnClickListener { listenerFour.invoke(position) }
        }
        listenerFive?.let {
            markFive.setOnClickListener { listenerFive.invoke(position) }
        }
        listenerDelete?.let {
            deleteMark.setOnClickListener { listenerDelete.invoke(position) }
        }

        layoutMarks.visibility = if (unfolded[position]) View.VISIBLE else View.GONE

        layoutStudent.setOnClickListener {
            unfolded[position] = !unfolded[position]

            val expand = layoutMarks.visibility == View.GONE

            val transition = ChangeBounds()
            transition.duration = 200

            layoutMarks.visibility = if (expand) View.VISIBLE else View.GONE

            TransitionManager.beginDelayedTransition(itemView as ViewGroup, transition)
            cv.isActivated = expand
        }
    }
}