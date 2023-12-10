package com.example.drawingapp.drawing.component

import java.io.Serializable

// 描画の記録するためにpathを表現する
sealed class DrawingPathRoute: Serializable {
    abstract val x: Float
    abstract val y: Float

    data class MoveTo(override val x: Float, override val y: Float): DrawingPathRoute()
    data class LineTo(override val x: Float = 0f, override val y: Float = 0f): DrawingPathRoute()
}