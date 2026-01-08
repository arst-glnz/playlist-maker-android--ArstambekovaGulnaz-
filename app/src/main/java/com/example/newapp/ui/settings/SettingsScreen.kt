package com.example.newapp.ui.settings

import android.content.Intent
import android.net.Uri
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.automirrored.filled.ArrowForwardIos
import androidx.compose.material.icons.filled.Share
import androidx.compose.material.icons.filled.SupportAgent
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Switch
import androidx.compose.material3.SwitchDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.font.FontWeight.Companion.Medium
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.net.toUri
import com.example.newapp.R

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SettingScreen(
    onBackClick: () -> Unit
) {
    //для письма в поддержку
    val email = stringResource(R.string.mail)
    val subject = stringResource(R.string.email_header)
    val body = stringResource(R.string.email_text)

    //для открытия ссылки на офферту
    val context = LocalContext.current
    val url = stringResource(R.string.offer_url)

    var checkedState by remember { mutableStateOf(false) }
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
            .padding(top = 16.dp, start = 16.dp, end = 16.dp),
    ) {
        Box(
            modifier = Modifier
                .height(56.dp)
                .fillMaxWidth(),
        ) {
            IconButton(
                onClick = onBackClick,
                modifier = Modifier
                    .padding(top = 14.dp)
                    .size(24.dp)
            ) {
                Icon(
                    imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                    contentDescription = "Назад",
                    tint = Color.Black,
                    modifier = Modifier
                        .size(24.dp)
                )
            }
            Text(
                text = stringResource(R.string.settings),
                fontSize = 22.sp,
                fontWeight = Medium,
                modifier = Modifier.padding(start = 48.dp, top = 14.dp)
            )
        }

        Spacer(modifier = Modifier.height(24.dp))
        Row(
            modifier = Modifier
                .height(61.dp)
                .fillMaxWidth()
                .fillMaxHeight(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Absolute.SpaceBetween
        ) {
            Text(
                text = stringResource(R.string.light_or_dark),
                color = Color.Black,
                fontSize = 16.sp,
                fontWeight = FontWeight.Normal,
            )

            Switch(
                checked = checkedState,
                { checkedState = it },
                colors = SwitchDefaults.colors(
                    checkedThumbColor = colorResource(R.color.yandex_purple),
                    checkedTrackColor = colorResource(R.color.yandex_purple_black),
                    uncheckedThumbColor = colorResource(R.color.grey),
                    uncheckedTrackColor = colorResource(R.color.ll_grey),
                    uncheckedBorderColor = Color.White,
                )
            )
        }
        SettingsButton(
            icon = Icons.Default.Share,
            stringResource(R.string.sharing),
            onClick = {
                val message = ""
                val shareIntent = Intent(Intent.ACTION_SEND).apply {
                    type = "text/plain"
                    putExtra(Intent.EXTRA_TEXT, message)
                }
                val chooser = Intent.createChooser(
                    shareIntent,
                    ""
                )
                context.startActivity(chooser)
            }
        )
        SettingsButton(
            icon = Icons.Default.SupportAgent,
            txt = stringResource(R.string.support),
            onClick = {
                val intent = Intent(Intent.ACTION_SENDTO).apply {
                    data = Uri.parse("mailto:")
                    putExtra(Intent.EXTRA_EMAIL, arrayOf(email))
                    putExtra(Intent.EXTRA_SUBJECT, subject)
                    putExtra(Intent.EXTRA_TEXT, body)
                }
                context.startActivity(intent)
            }
        )
        SettingsButton(
            icon = Icons.AutoMirrored.Default.ArrowForwardIos,
            stringResource(R.string.user_offer),
            onClick = {
                val intent = Intent(Intent.ACTION_VIEW, url.toUri())
                context.startActivity(intent)
            }
        )
    }
}


@Composable
fun SettingsButton(
    icon: ImageVector,
    txt: String,
    onClick: () -> Unit
) {
    Button(
        onClick =  onClick,
        modifier = Modifier
            .height(61.dp)
            .fillMaxWidth(),
        colors = ButtonDefaults.buttonColors(Color.White),
        elevation = null,
        contentPadding = PaddingValues(horizontal = 0.dp)
    ) {
        Row(
            modifier = Modifier
                .height(61.dp)
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {

            Text(
                text = txt,
                color = Color.Black,
                fontSize = 16.sp,
                fontWeight = FontWeight.Normal,
            )

            Icon(
                imageVector = icon,
                tint = colorResource(R.color.grey),
                contentDescription = null,
            )
        }
    }
}