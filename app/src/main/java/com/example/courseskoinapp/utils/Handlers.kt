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
}

