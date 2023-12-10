package com.example.drawingapp.drawing.component

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.github.skydoves.colorpicker.compose.AlphaTile
import com.github.skydoves.colorpicker.compose.BrightnessSlider
import com.github.skydoves.colorpicker.compose.ColorEnvelope
import com.github.skydoves.colorpicker.compose.HsvColorPicker
import com.github.skydoves.colorpicker.compose.rememberColorPickerController
@Composable
fun ColorPicker(penColor: Color, onColorChange: (Color) -> Unit) {
    val controller = rememberColorPickerController()
    Column {
        HsvColorPicker(
            modifier = Modifier
                .fillMaxWidth()
                .height(240.dp)
                .padding(20.dp),
            controller = controller,
            initialColor = penColor,
            onColorChanged = { colorEnvelope: ColorEnvelope ->
                onColorChange(colorEnvelope.color)
            }
        )
        Row {
            BrightnessSlider(
                modifier = Modifier
                    .fillMaxWidth(0.8f)
                    .padding(40.dp)
                    .height(20.dp),
                controller = controller,
                initialColor = penColor,
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