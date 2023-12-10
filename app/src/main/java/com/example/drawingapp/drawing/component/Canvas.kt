package com.example.drawingapp.drawing.component

import android.view.MotionEvent
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.BlendMode
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.drawscope.inset
import androidx.compose.ui.input.pointer.pointerInteropFilter
import com.example.drawingapp.drawing.DrawingPathRoute

@Suppress("UNUSED_EXPRESSION")
@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun DrawingCanvas(tracks: MutableState<List<DrawingPathRoute>?>, penSize: Float, canvasHeight: PaddingValues) {
    Canvas(
        modifier = Modifier
            .fillMaxSize()
            .pointerInteropFilter { motionEvent: MotionEvent ->
                when (motionEvent.action) {
                    // 描き始めの処理
                    MotionEvent.ACTION_DOWN -> {
                        tracks.value = ArrayList<DrawingPathRoute>().apply {
                            tracks.value?.let { addAll(it) }
                            add(DrawingPathRoute.MoveTo(motionEvent.x, motionEvent.y))
                        }
                    }
                    // 書いてる途中の処理
                    MotionEvent.ACTION_MOVE -> {
                        tracks.value = ArrayList<DrawingPathRoute>().apply {
                            tracks.value?.let { addAll(it) }
                            add(DrawingPathRoute.LineTo(motionEvent.x, motionEvent.y))
                        }
                    }

                    else -> false
                }
                true
            }) {
        val paths = ArrayList<Path>()
        tracks.let {
            var path = Path()
            tracks.value?.forEach { drawingPathRoute ->
                when (drawingPathRoute) {
                    is DrawingPathRoute.MoveTo -> {
                        paths.add(path)
                        path = Path()
                        path.moveTo(drawingPathRoute.x, drawingPathRoute.y)
                    }

                    is DrawingPathRoute.LineTo -> {
                        path.lineTo(drawingPathRoute.x, drawingPathRoute.y)
                    }
                }
            }
            paths.add(path)
        }

        inset(horizontal = penSize, vertical = penSize) {
            paths.forEach {
                drawPath(
                    path = it,
                    color = Color.Black,
                    style = Stroke(width = penSize),
                    blendMode = BlendMode.SrcOver
                )
            }
        }
    }
}