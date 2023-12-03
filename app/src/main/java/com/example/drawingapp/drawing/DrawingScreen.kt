package com.example.drawingapp.drawing

import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import com.example.drawingapp.drawing.component.DrawingCanvas
import java.io.Serializable

@Composable
fun DrawingScreen() {
    // 描画の履歴の記録のため
    val tracks = rememberSaveable{ mutableStateOf<List<DrawingPathRoute>?>(null) }
    DrawingCanvas(tracks = tracks)
}

// 描画の記録するためにpathを表現する
sealed class DrawingPathRoute: Serializable {
    abstract val x: Float
    abstract val y: Float

    data class MoveTo(override val x: Float, override val y: Float): DrawingPathRoute()
    data class LineTo(override val x: Float = 0f, override val y: Float = 0f): DrawingPathRoute()
}
