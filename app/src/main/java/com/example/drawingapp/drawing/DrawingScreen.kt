package com.example.drawingapp.drawing

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.ExitToApp
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.BottomAppBarDefaults
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.FloatingActionButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Slider
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment.Companion.CenterVertically
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import com.example.drawingapp.drawing.component.ColorPicker
import com.example.drawingapp.drawing.component.DrawingCanvas
import com.example.drawingapp.drawing.data.CustomDrawingPath

@Composable
fun DrawingScreen() {
    // 描画の履歴の記録のため
    val tracks = rememberSaveable { mutableStateOf<List<CustomDrawingPath>?>(null) }
    var penSize by remember { mutableFloatStateOf(4f) }
    var penColor by remember { mutableStateOf(Color.Black) }
    var showPenSizeSlider by remember { mutableStateOf(false) }
    var showColorPicker by remember { mutableStateOf(false) }

    Scaffold(
        bottomBar = {
            Column {
                if(showColorPicker) {
                    ColorPicker(penColor = penColor) { color -> penColor = color }
                }
                if(showPenSizeSlider){
                    Row {
                        Slider(
                            value = penSize,
                            valueRange = 0f..100f,
                            onValueChange = {
                                penSize = it
                            },
                            modifier = Modifier.fillMaxWidth(0.8f)
                        )
                        Text(text = String.format("%.2f", penSize), modifier = Modifier.align(CenterVertically))
                    }
                }
                BottomAppBar(
                    actions = {
                        IconButton(onClick = { tracks.value = null }) {
                            Icon(Icons.Filled.Delete, contentDescription = "削除")
                        }
                        IconButton(onClick = { showPenSizeSlider = !showPenSizeSlider }) {
                            Icon(
                                Icons.Filled.Edit,
                                contentDescription = "ペンサイズ変更",
                            )
                        }
                        IconButton(onClick = { showColorPicker = !showColorPicker}) {
                            Icon(
                                Icons.Filled.MoreVert,
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
            }
            }
    ) {
        DrawingCanvas(tracks = tracks, penSize = penSize, penColor = penColor, canvasHeight = it)
    }
}
