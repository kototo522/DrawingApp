package com.example.drawingapp.drawing.data

import androidx.compose.ui.graphics.Color
import com.example.drawingapp.drawing.DrawingPathRoute


data class CustomDrawingPath(
    val path: DrawingPathRoute,
    val color: Color,
    val size: Float
)