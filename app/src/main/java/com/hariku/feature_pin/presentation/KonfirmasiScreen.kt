import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.hariku.R

@Composable
fun ConfirmPinScreen() {
    var pinValue by remember { mutableStateOf("") }
    val maxPinLength = 4

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(color = Color(0xFFF7ECE3))
    ) {
        Column(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            StatusBarComposableConfirm()

            Spacer(modifier = Modifier.height(80.dp))

            Text(
                text = "Konfirmasi Pin",
                style = TextStyle(
                    fontSize = 24.sp,
                    fontFamily = FontFamily.Default,
                    fontWeight = FontWeight.Bold,
                    color = Color(0xFF242424),
                    textAlign = TextAlign.Center,
                ),
                modifier = Modifier.padding(horizontal = 40.dp)
            )


            Spacer(modifier = Modifier.height(48.dp))

            PinDotsComposableConfirm(
                count = maxPinLength,
                filled = pinValue.length
            )

            Spacer(modifier = Modifier.height(48.dp))

            NumpadComposableConfirm(
                onNumberClick = { number ->
                    if (pinValue.length < maxPinLength) {
                        pinValue += number
                    }
                },
                onBackspaceClick = {
                    if (pinValue.isNotEmpty()) {
                        pinValue = pinValue.dropLast(1)
                    }
                }
            )

            Spacer(modifier = Modifier.weight(1f))
        }

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(150.dp)
                .align(Alignment.BottomCenter)
        ) {
            Image(
                painter = painterResource(id = R.drawable.kocheng),
                contentDescription = "Ilustrasi Bawah",
                modifier = Modifier.fillMaxSize()
            )
        }

        Box(
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .padding(bottom = 16.dp)
                .width(134.dp)
                .height(5.dp)
                .background(Color.Black, RoundedCornerShape(100))
        )
    }
}

@Composable
fun StatusBarComposableConfirm() {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(52.dp)
            .padding(horizontal = 30.dp, vertical = 15.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = "9:41",
            style = TextStyle(
                fontSize = 18.sp,
                fontWeight = FontWeight(600),
                color = Color.Black,
            )
        )
        Row(
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Image(
                painter = painterResource(id = R.drawable.ic_signal),
                contentDescription = "Sinyal",
                modifier = Modifier.size(20.dp)
            )
            Image(
                painter = painterResource(id = R.drawable.ic_wifi),
                contentDescription = "Wifi",
                modifier = Modifier.size(20.dp)
            )
            Image(
                painter = painterResource(id = R.drawable.ic_battery),
                contentDescription = "Baterai",
                modifier = Modifier.size(20.dp)
            )
        }
    }
}

@Composable
fun PinDotsComposableConfirm(count: Int, filled: Int) {
    Row(
        horizontalArrangement = Arrangement.spacedBy(34.dp),
    ) {
        repeat(count) { index ->
            val color = if (index < filled) Color(0xFF242424) else Color(0xFFBEBEBE)
            Box(
                modifier = Modifier
                    .size(15.dp)
                    .background(color, CircleShape)
            )
        }
    }
}

@Composable
fun NumpadComposableConfirm(
    onNumberClick: (String) -> Unit,
    onBackspaceClick: () -> Unit
) {
    val buttonSize = 72.dp
    val spacing = 24.dp

    Column(
        verticalArrangement = Arrangement.spacedBy(spacing),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Row(horizontalArrangement = Arrangement.spacedBy(spacing)) {
            NumberButtonConfirm(number = "1", size = buttonSize, onClick = { onNumberClick("1") })
            NumberButtonConfirm(number = "2", size = buttonSize, onClick = { onNumberClick("2") })
            NumberButtonConfirm(number = "3", size = buttonSize, onClick = { onNumberClick("3") })
        }
        Row(horizontalArrangement = Arrangement.spacedBy(spacing)) {
            NumberButtonConfirm(number = "4", size = buttonSize, onClick = { onNumberClick("4") })
            NumberButtonConfirm(number = "5", size = buttonSize, onClick = { onNumberClick("5") })
            NumberButtonConfirm(number = "6", size = buttonSize, onClick = { onNumberClick("6") })
        }
        Row(horizontalArrangement = Arrangement.spacedBy(spacing)) {
            NumberButtonConfirm(number = "7", size = buttonSize, onClick = { onNumberClick("7") })
            NumberButtonConfirm(number = "8", size = buttonSize, onClick = { onNumberClick("8") })
            NumberButtonConfirm(number = "9", size = buttonSize, onClick = { onNumberClick("9") })
        }
        Row(horizontalArrangement = Arrangement.spacedBy(spacing)) {
            Spacer(modifier = Modifier.size(buttonSize)) // Placeholder untuk tata letak
            NumberButtonConfirm(number = "0", size = buttonSize, onClick = { onNumberClick("0") })

            // --- Tombol Backspace ---
            Box(
                modifier = Modifier
                    .size(buttonSize)
                    .background(Color.White, CircleShape)
                    .clip(CircleShape)
                    .clickable { onBackspaceClick() },
                contentAlignment = Alignment.Center
            ) {
                Image(
                    painter = painterResource(id = R.drawable.backspace),
                    contentDescription = "Backspace",
                    modifier = Modifier.size(32.dp)
                )
            }
        }
    }
}


@Composable
fun NumberButtonConfirm(
    number: String,
    size: Dp,
    onClick: () -> Unit
) {
    Box(
        modifier = Modifier
            .size(size)
            .background(Color.White, CircleShape)
            .clip(CircleShape)
            .clickable { onClick() },
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = number,
            style = TextStyle(
                fontSize = 38.sp,
                fontWeight = FontWeight(500),
                color = Color(0xFF242424),
            )
        )
    }
}


@Preview(showBackground = true, widthDp = 430, heightDp = 932)
@Composable
fun ConfirmPinScreenPreview() {
    ConfirmPinScreen()
}
