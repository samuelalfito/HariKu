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
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
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
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.hariku.R
import com.hariku.core.ui.components.Routes
import com.hariku.feature_pin.data.local.entity.Pin
import com.hariku.feature_pin.presentation.PinScreenViewModel
import org.koin.androidx.compose.koinViewModel

private enum class Stage {
    CREATE,
    CONFIRM,
    INPUT
}

@Composable
fun PinScreenFull( /*TODO: Fitur ganti Pin (tunggu tombol ganti pin)*/
    navController: NavController,
    viewModel: PinScreenViewModel = koinViewModel()
) {
    
    var pinValue by remember { mutableStateOf("") }
    var createdPin by remember { mutableStateOf("") } //ADDED
    var errorMessage by remember { mutableStateOf<String?>(null) } //ADDED
    var stage by remember { mutableStateOf(Stage.CREATE) }

    val pin: Pin? by viewModel.pin.observeAsState()

    val maxPinLength = 4
    //val list = remember { mutableStateListOf<Int>() }

    if (pin != null) {
        stage = Stage.INPUT
    }

    //Testing, buka komentar kalo mau ganti pin soalnya belum ada fitur ganti pin
//    if(pin != null){
//        viewModel.deletePin(pin = pin!!)
//        stage = Stage.CREATE
//    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(color = Color(0xFFF7ECE3))
    ) {
        Column(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            Column(
                modifier = Modifier.fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Spacer(modifier = Modifier.height(50.dp))
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 24.dp, vertical = 12.dp),
                    horizontalArrangement = Arrangement.End
                ) {
                    Text(
                        text = when(stage){
                            Stage.CREATE -> {
                                "Lewati"
                            }
                            Stage.CONFIRM -> {
                                ""
                            }
                            Stage.INPUT -> {
                                ""
                            }
                        },
                        style = TextStyle(
                            fontSize = 16.sp,
                            fontFamily = FontFamily.Default,
                            fontWeight = FontWeight(600),
                            color = Color(0xFF242424),
                            textDecoration = TextDecoration.Underline,
                        ),
                        modifier = Modifier
                            .clickable {
                                if (stage == Stage.CREATE){
                                    navController.navigate(Routes.Home.route) {
                                        popUpTo(0) { inclusive = true }
                                    }
                                }
                            }
                    )
                }

                Spacer(modifier = Modifier.height(32.dp))

                Text(
                    text = when(stage){
                        Stage.CREATE -> {
                            "Tetapkan Pin Keamanan"
                        }
                        Stage.CONFIRM -> {
                            "Konfirmasi Pin"
                        }
                        Stage.INPUT -> {
                            "Masukkan Pin"
                        }
                    },
                    style = TextStyle(
                        fontSize = 24.sp,
                        fontFamily = FontFamily.Default,
                        fontWeight = FontWeight.Bold,
                        color = Color(0xFF242424),
                        textAlign = TextAlign.Center,
                    ),
                    modifier = Modifier.padding(horizontal = 40.dp)
                )

                Spacer(modifier = Modifier.height(16.dp))

                Text(
                    text = when(stage){
                        Stage.CREATE -> {
                            "Setiap membuka aplikasi, Urai akan meminta pin keamanan"
                        }
                        Stage.CONFIRM -> {
                            ""
                        }
                        Stage.INPUT -> {
                            ""
                        }
                    },
                    style = TextStyle(
                        fontSize = 14.sp,
                        lineHeight = 20.sp,
                        fontFamily = FontFamily.Default,
                        fontWeight = FontWeight.Normal,
                        color = Color(0xFF242424),
                        textAlign = TextAlign.Center,
                    ),
                    modifier = Modifier.padding(horizontal = 60.dp)
                )

                if (errorMessage != null) { //ADDED
                    Spacer(modifier = Modifier.height(16.dp))
                    Text(
                        text = errorMessage!!,
                        style = TextStyle(
                            fontSize = 14.sp,
                            color = Color.Red,
                            textAlign = TextAlign.Center,
                        ),
                        modifier = Modifier.padding(horizontal = 40.dp)
                    )
                }
            }

            Spacer(modifier = Modifier.height(32.dp))

            Column(
                modifier = Modifier.fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Bottom
            ){
                PinDotsComposable(
                    count = maxPinLength,
                    filled = pinValue.length
                )

                Spacer(modifier = Modifier.height(48.dp))

                NumpadComposable(
                    onNumberClick = { number ->
                        if (pinValue.length < maxPinLength) {
                            pinValue += number
                        }
                        if (pinValue.length == maxPinLength) {
                            when (stage) {
                                Stage.CREATE -> {
                                    createdPin = pinValue
                                    pinValue = ""
                                    stage = Stage.CONFIRM
                                }
                                Stage.CONFIRM -> {
                                    if (pinValue == createdPin) {
                                        // SUCCESS: PINs match, navigate to home

                                        viewModel.addPin(pin = Pin(pin = createdPin))

                                        navController.navigate(Routes.Home.route) {
                                            popUpTo(0) { inclusive = true }
                                        }
                                    } else {
                                        // FAILURE: PINs do not match, show error and reset
                                        errorMessage = "PIN tidak cocok. Silakan buat ulang."
                                        pinValue = ""
                                        createdPin = ""
                                        stage = Stage.CREATE
                                    }
                                }
                                Stage.INPUT -> {
                                    if(pinValue == pin?.pin) {
                                        navController.navigate(Routes.Home.route) {
                                            popUpTo(0) { inclusive = true }
                                        }
                                    }
                                    else {
                                        // FAILURE: PINs do not match, show error and reset
                                        errorMessage = "PIN tidak cocok."
                                        pinValue = ""
                                        createdPin = ""
                                        stage = Stage.CREATE
                                    }
                                }
                            }
                        }
                    },
                    onBackspaceClick = {
                        if (pinValue.isNotEmpty()) {
                            pinValue = pinValue.dropLast(1)
                            errorMessage = null // Clear error on backspace
                        }
                    }
                )
                Spacer(modifier = Modifier.height(200.dp))
            }
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
    }
}


@Composable
fun PinDotsComposable(count: Int, filled: Int) {
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
fun NumpadComposable(
    onNumberClick: (Int) -> Unit,
    onBackspaceClick: () -> Unit,
) {
    val buttonSize = 72.dp
    val spacing = 24.dp
    
    Column(
        verticalArrangement = Arrangement.spacedBy(spacing),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Row(horizontalArrangement = Arrangement.spacedBy(spacing)) {
            NumberButton(number = "1", size = buttonSize, onClick = { onNumberClick(1) })
            NumberButton(number = "2", size = buttonSize, onClick = { onNumberClick(2) })
            NumberButton(number = "3", size = buttonSize, onClick = { onNumberClick(3) })
        }
        Row(horizontalArrangement = Arrangement.spacedBy(spacing)) {
            NumberButton(number = "4", size = buttonSize, onClick = { onNumberClick(4) })
            NumberButton(number = "5", size = buttonSize, onClick = { onNumberClick(5) })
            NumberButton(number = "6", size = buttonSize, onClick = { onNumberClick(6) })
        }
        Row(horizontalArrangement = Arrangement.spacedBy(spacing)) {
            NumberButton(number = "7", size = buttonSize, onClick = { onNumberClick(7) })
            NumberButton(number = "8", size = buttonSize, onClick = { onNumberClick(8) })
            NumberButton(number = "9", size = buttonSize, onClick = { onNumberClick(9) })
        }
        Row(horizontalArrangement = Arrangement.spacedBy(spacing)) {
            Spacer(modifier = Modifier.size(buttonSize))
            NumberButton(number = "0", size = buttonSize, onClick = { onNumberClick(0) })
            
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
fun NumberButton(
    number: String,
    size: Dp,
    onClick: () -> Unit,
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


@Preview(showBackground = true, showSystemUi = true)
@Composable
fun PinScreenFullPreview() {
    PinScreenFull(rememberNavController())
}
