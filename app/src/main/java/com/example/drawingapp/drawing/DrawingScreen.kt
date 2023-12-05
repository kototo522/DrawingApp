package com.example.drawingapp.drawing

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.ExitToApp
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.BottomAppBarDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.FloatingActionButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import com.example.drawingapp.drawing.component.DrawingCanvas
import java.io.Serializable

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DrawingScreen() {
    // 描画の履歴の記録のため
    var tracks = rememberSaveable { mutableStateOf<List<DrawingPathRoute>?>(null) }
    var penSize by remember { mutableStateOf(4f) }

    Scaffold(
        bottomBar = {
            BottomAppBar(
                actions = {
                    IconButton(onClick = { tracks.value = null }) {
                        Icon(Icons.Filled.Delete, contentDescription = "削除")
                    }
                    IconButton(onClick = { /* do something */ }) {
                        Icon(
                            Icons.Filled.Edit,
                            contentDescription = "ペンサイズ変更",
                        )
                    }
                    IconButton(onClick = { /* do something */ }) {
                        Icon(
                            Icons.Filled.Add,
                            contentDescription = "色の変更",
                        )
                    }
                    IconButton(onClick = { /* do something */  }) {
                        Icon(
                            Icons.Filled.Add,
                            contentDescription = "ペンの変更",
                        )
                    }
                },
                floatingActionButton = {
                    FloatingActionButton(
                        onClick = {  },
                        containerColor = BottomAppBarDefaults.bottomAppBarFabColor,
                        elevation = FloatingActionButtonDefaults.bottomAppBarFabElevation()
                    ) {
                        Icon(Icons.Filled.ExitToApp, "保存")
                    }
                }
            )
        },
    ) {
        DrawingCanvas(tracks = tracks, penSize = penSize, canvasHeight = it)
    }
}


// 描画の記録するためにpathを表現する
sealed class DrawingPathRoute: Serializable {
    abstract val x: Float
    abstract val y: Float

    data class MoveTo(override val x: Float, override val y: Float): DrawingPathRoute()
    data class LineTo(override val x: Float = 0f, override val y: Float = 0f): DrawingPathRoute()
}
