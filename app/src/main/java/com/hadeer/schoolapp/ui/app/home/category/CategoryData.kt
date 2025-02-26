package com.hadeer.schoolapp.ui.app.home.category

import com.hadeer.schoolapp.R

object CategoryData {

    val DATA : List<Category> = listOf(
        Category(
            1,
            R.string.attendance_category_name,
            R.drawable.attendance_category_icv
        ),
        Category(
            2,
            R.string.quiz_category_name,
            R.drawable.quiz_category_icv
        ),
        Category(
            3,
            R.string.assignment_category_name,
            R.drawable.assignment_category_icv
        ),
        Category(
            4,
            R.string.classSchedual_category_name,
            R.drawable.class_schedual_icv
        ),
        Category(
            5,
            R.string.results_category_name,
            R.drawable.exam_category_icv
        ),
        Category(
            6,
            R.string.schoolHoliday_category_name,
            R.drawable.school_holiday_category_icv
        ),
        Category(
            7,
            R.string.examSchedual_category_name,
            R.drawable.exam_schedual_category_icv
        ),
    )
}