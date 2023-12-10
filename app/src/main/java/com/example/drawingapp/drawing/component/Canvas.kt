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
import com.example.drawingapp.drawing.data.CustomDrawingPath

@Suppress("UNUSED_EXPRESSION")
@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun DrawingCanvas(tracks: MutableState<List<CustomDrawingPath>?>, penSize: Float, canvasHeight: PaddingValues) {
    Canvas(
        modifier = Modifier
            .fillMaxSize()
            .pointerInteropFilter { motionEvent: MotionEvent ->
                when (motionEvent.action) {
                    // 描き始めの処理
                    MotionEvent.ACTION_DOWN -> {
                        tracks.value = ArrayList<CustomDrawingPath>().apply {
                            tracks.value?.let { addAll(it) }
                            add(CustomDrawingPath(
                                path = DrawingPathRoute.MoveTo(motionEvent.x, motionEvent.y),
                                color = Color.Black,
                                size = penSize
                            ))
                        }
                    }
                    // 書いてる途中の処理
                    MotionEvent.ACTION_MOVE -> {
                        tracks.value = ArrayList<CustomDrawingPath>().apply {
                            tracks.value?.let { addAll(it) }
                            add(CustomDrawingPath(
                                path = DrawingPathRoute.LineTo(motionEvent.x, motionEvent.y),
                                color = Color.Black,
                                size = penSize
                            ))
                        }
                    }

                    else -> false
                }
                true
            }) {
        val paths = ArrayList<Path>()
        var currentPath = Path()
        var currentSize = penSize

        tracks.let {
            var path = Path()
            tracks.value?.forEach { customDrawingPath ->
                when (customDrawingPath.path) {
                    is DrawingPathRoute.MoveTo -> {
                        paths.add(currentPath)
                        currentPath = Path().apply { moveTo(customDrawingPath.path.x, customDrawingPath.path.y) }
                        currentSize = customDrawingPath.size
                    }

                    is DrawingPathRoute.LineTo -> {
                        drawPath(
                            path = currentPath.apply { lineTo(customDrawingPath.path.x, customDrawingPath.path.y) },
                            color = Color.Black,
                            style = Stroke(width = currentSize),
                            blendMode = BlendMode.SrcOver
                        )
                    }
                }
            }
            paths.add(currentPath)
        }
    }
}