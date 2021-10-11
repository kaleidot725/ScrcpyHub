package view.components

import androidx.compose.desktop.ui.tooling.preview.Preview
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.Button
import androidx.compose.material.Card
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import model.entity.Device
import resource.Images
import resource.Strings

@Composable
fun DeviceCard(
    device: Device,
    isRunning: Boolean,
    startScrcpy: ((Device) -> Unit)? = null,
    stopScrcpy: ((Device) -> Unit)? = null,
    goToDetail: ((Device) -> Unit)? = null,
    modifier: Modifier = Modifier
) {
    Card(modifier = modifier) {
        Row(modifier = Modifier.padding(horizontal = 8.dp).height(48.dp)) {
            Image(
                painter = painterResource(Images.DEVICE),
                contentDescription = Images.DEVICE,
                contentScale = ContentScale.Inside,
                modifier = Modifier.width(32.dp).align(Alignment.CenterVertically).padding(end = 4.dp)
            )

            Column(
                modifier = Modifier.fillMaxWidth(fraction = 0.65f).align(Alignment.CenterVertically)
            ) {
                Text(
                    device.name,
                    style = TextStyle(color = Color.Black, fontSize = 16.sp, fontWeight = FontWeight.SemiBold),
                )

                Text(
                    device.id,
                    style = TextStyle(color = Color.Gray, fontSize = 12.sp, fontWeight = FontWeight.SemiBold),
                )
            }

            Button(
                onClick = { if (!isRunning) startScrcpy?.invoke(device) else stopScrcpy?.invoke(device) },
                modifier = Modifier.width(80.dp).height(30.dp).align(Alignment.CenterVertically)
            ) {
                Text(if (!isRunning) Strings.RUN else Strings.STOP, fontSize = 12.sp)
            }

            Image(
                painter = painterResource(Images.DOTS),
                contentScale = ContentScale.FillWidth,
                contentDescription = null,
                modifier = Modifier
                    .width(30.dp)
                    .height(30.dp)
                    .padding(start = 4.dp)
                    .align(Alignment.CenterVertically)
                    .clickable {
                        goToDetail?.invoke(device)
                    }
            )
        }
    }
}

@Preview
@Composable
private fun DeviceCard_Preview() {
    DeviceCard(Device("ID", "NAME"), false)
}