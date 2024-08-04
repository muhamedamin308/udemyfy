package com.example.courseskoinapp.utils

import android.graphics.Color
import android.view.View
import com.example.courseskoinapp.R

object Handlers {
    fun generateRandomColor(): Int {
        val colors = listOf(
            "#CCD5AE",
            "#F7B5CA",
            "#E2DAD6",
            "#B5CFB7",
            "#EECEB9",
            "#B4E380",
            "#FFFED3",
            "#CAF4FF",
            "#FFE0B5",
            "#F0EBE3"
        )
        val randomIndex = colors.indices.random()
        return Color.parseColor(colors[randomIndex])
    }

    private val avatars = arrayOf(
        R.drawable.owl_1989690,
        R.drawable.kangaroo_848559,
        R.drawable.giraffe_1888365,
        R.drawable.koala_3069172,
        R.drawable.octopus_3034552,
        R.drawable.seal_1137904
    )

    fun generateRandomAvatar(): Int = avatars.random()
}

