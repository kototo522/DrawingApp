package com.example.drawingapp.drawing

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
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
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.example.drawingapp.drawing.component.DrawingCanvas
import com.example.drawingapp.drawing.data.CustomDrawingPath
import com.github.skydoves.colorpicker.compose.AlphaTile
import com.github.skydoves.colorpicker.compose.BrightnessSlider
import com.github.skydoves.colorpicker.compose.ColorEnvelope
import com.github.skydoves.colorpicker.compose.HsvColorPicker
import com.github.skydoves.colorpicker.compose.rememberColorPickerController
import java.io.Serializable

@Composable
fun DrawingScreen() {
    // 描画の履歴の記録のため
    val tracks = rememberSaveable { mutableStateOf<List<CustomDrawingPath>?>(null) }
    var penSize by remember { mutableStateOf(4f) }
    var penColor by remember { mutableStateOf(Color.Black) }
    var showPenSizeSlider by remember { mutableStateOf(false) }
    var showColorPicker by remember { mutableStateOf(false) }

    val controller = rememberColorPickerController()


    Scaffold(
        bottomBar = {
            Column {
                if(showColorPicker) {
                    Column {
                        HsvColorPicker(
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(240.dp)
                                .padding(20.dp),
                            controller = controller,
                            onColorChanged = { colorEnvelope: ColorEnvelope ->
                                penColor = colorEnvelope.color
                            }
                        )
                        Row {
                            BrightnessSlider(
                                modifier = Modifier
                                    .fillMaxWidth(0.8f)
                                    .padding(40.dp)
                                    .height(20.dp),
                                controller = controller,
                            )
                            AlphaTile(
                                modifier = Modifier
                                    .size(40.dp)
                                    .clip(RoundedCornerShape(6.dp))
                                    .border(width = 1.dp, color = Color.Black),
                                controller = controller
                            )
                        }
                    }
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


// 描画の記録するためにpathを表現する
sealed class DrawingPathRoute: Serializable {
    abstract val x: Float
    abstract val y: Float

    data class MoveTo(override val x: Float, override val y: Float): DrawingPathRoute()
    data class LineTo(override val x: Float = 0f, override val y: Float = 0f): DrawingPathRoute()
}
