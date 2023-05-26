package com.mhmd.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

@Composable
fun Chip(
    title: String,
    modifier: Modifier = Modifier,
    isSelected: Boolean = false,
    iconRes: Int? = null,
    iconColor: Color? = null,
    selectedBackGroundColor: Color? = null,
    selectedTextColor: Color? = null,
    height: Dp = 32.dp,
    onClick: () -> Unit = {}
) {
    val borderColor =
        if (isSelected) selectedBackGroundColor ?: MaterialTheme.colors.primary else Color.Transparent
    Button(
        modifier = modifier.height(height),
        shape = RoundedCornerShape(corner = CornerSize(8.dp)),
        elevation = ButtonDefaults.elevation(
            defaultElevation = 0.dp,
            pressedElevation = 0.dp,
            disabledElevation = 0.dp
        ),
        border = BorderStroke(.5.dp, borderColor),
        contentPadding = PaddingValues(horizontal = 8.dp),
        colors = ButtonDefaults.buttonColors(
            backgroundColor = if (isSelected) selectedBackGroundColor
                ?: GrayTransparentColor else GrayTransparentColor
        ),
        onClick = {
            onClick()
        }
    ) {
        Row(
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically
        ) {
            if (iconRes != null) Image(
                modifier = Modifier
                    .size(24.dp)
                    .padding(end = 8.dp),
                painter = painterResource(id = iconRes),
                colorFilter = if (iconColor == null) null else ColorFilter.tint(iconColor),
                contentDescription = ""
            )
            Text(
                text = title,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
                modifier = Modifier
                    .wrapContentWidth(Alignment.CenterHorizontally),
                style = MaterialTheme.typography.caption.copy(
                    color = if (isSelected) selectedTextColor ?: MaterialTheme.colors.primary
                    else MaterialTheme.colors.onSurface
                )
            )
        }
    }
}
