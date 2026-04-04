package com.example.newapp.ui.search

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.History
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun HistoryRequests(
    historyList: List<String>,
    onClick: (String) -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .heightIn(max = 260.dp),
        shape = RoundedCornerShape(12.dp),           // мягкие углы как на скрине
        colors = CardDefaults.cardColors(
            containerColor = Color(0xFFF2F2F7)       // самый близкий светло-серый цвет
        ),
        border = null,                               // убираем видимую границу
        elevation = CardDefaults.cardElevation(
            defaultElevation = 0.dp
        )
    ) {
        LazyColumn(
            modifier = Modifier.padding(vertical = 6.dp)
        ) {
            itemsIndexed(historyList.take(5)) { index, item ->   // показываем максимум 5, как обычно
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable { onClick(item) }
                        .padding(horizontal = 16.dp, vertical = 14.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        imageVector = Icons.Default.History,
                        contentDescription = null,
                        tint = Color(0xFF8E8E93),          // серый цвет иконки часов
                        modifier = Modifier.size(20.dp)
                    )

                    Spacer(modifier = Modifier.width(12.dp))

                    Text(
                        text = item,
                        style = MaterialTheme.typography.bodyLarge.copy(
                            fontSize = 17.sp,
                            fontWeight = FontWeight.Normal
                        ),
                        color = Color(0xFF1C1C1E)
                    )
                }

                // Тонкий разделитель, как на скриншоте
                if (index < historyList.lastIndex) {
                    HorizontalDivider(
                        modifier = Modifier.padding(horizontal = 52.dp), // отступ от левого края (иконка + отступ)
                        thickness = 0.6.dp,
                        color = Color(0xFFE5E5EA)
                    )
                }
            }
        }
    }
}