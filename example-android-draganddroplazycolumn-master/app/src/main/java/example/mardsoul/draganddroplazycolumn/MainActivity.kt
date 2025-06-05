package example.mardsoul.draganddroplazycolumn

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.outlined.Clear
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import example.mardsoul.draganddroplazycolumn.ui.LazyColumnApp
import example.mardsoul.draganddroplazycolumn.ui.theme.ExampleLazyColumnTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            ExampleLazyColumnTheme {
                Scaffold(
                    topBar = {
                        Row(
                            modifier = Modifier
                                .padding(horizontal = 20.dp, vertical = 30.dp)
                                .fillMaxWidth()
                                .height(30.dp),
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            Icon(
                                imageVector = Icons.Filled.KeyboardArrowDown,
                                modifier = Modifier.size(36.dp),
                                tint = Color(0xFFb6b6b6),
                                contentDescription = null
                            )
                            Spacer(modifier = Modifier.weight(1f))
                            Text(
                                text = "Моя волна «Стали другими»",
                                color = Color.White,
                                fontWeight = FontWeight.Bold
                            )
                            Spacer(modifier = Modifier.weight(1f))
                            Icon(
                                painter = painterResource(id = R.drawable.wireless),
                                tint = Color(0xFFb6b6b6),
                                modifier = Modifier.size(36.dp),
                                contentDescription = null
                            )
                            Spacer(modifier = Modifier.weight(1f))
                            Icon(
                                imageVector = Icons.Outlined.Clear,
                                tint = Color(0xFFb6b6b6),
                                contentDescription = null
                            )
                        }
                    },
                    modifier = Modifier.fillMaxSize(),
                    containerColor = Color(0xFF808080)
                ) { innerPadding ->
                    LazyColumnApp(modifier = Modifier.padding(innerPadding))
                }
            }
        }
    }
}