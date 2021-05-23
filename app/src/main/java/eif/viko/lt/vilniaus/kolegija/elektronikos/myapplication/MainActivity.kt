package eif.viko.lt.vilniaus.kolegija.elektronikos.myapplication

import android.content.res.Resources
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.text.selection.DisableSelection
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import eif.viko.lt.vilniaus.kolegija.elektronikos.myapplication.ui.theme.MyApplicationTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MyApplicationTheme {
                // A surface container using the 'background' color from the theme
                Surface(color = MaterialTheme.colors.background) {
                    Greeting("Android")
                }
            }
        }
    }
}

@Composable
fun Greeting(name: String) {
    val temp = listOf(
        "Labas",
        "Krabas",
        "BNabas",
        "Labas",
        "Krabas",
        "BNabas",
        "Labas",
        "Krabas",
        "BNabas",
        "Labas",
        "Krabas",
        "BNabas",
        "Labas",
        "Krabas",
        "BNabas",
        "Labas",
        "Krabas",
        "BNabas",
        "Labas",
        "Krabas",
        "BNabas"
    )


    val size = 64.dp

    LazyRow(Modifier.fillMaxHeight()) {
        items(temp) {

            Card(
                elevation = 4.dp, modifier = Modifier
                    .fillParentMaxWidth()
                    .padding(10.dp)
            ) {

                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = Modifier.padding(10.dp)
                ) {

                    Row() {
                        Text(
                            text = "Mobilusis telefonas Motorola V.box (v100) Didžioji britanija 2000 m.",
                            modifier = Modifier.fillParentMaxWidth(),
                            maxLines = 20,
                            fontSize = 20.sp,
                            textAlign = TextAlign.Center,
                            fontWeight = FontWeight.Bold,
                            color = MaterialTheme.colors.primary
                        )
                    }
                    Row() {

//                        Column(Modifier.align(Alignment.CenterVertically)) {
//                            Row() {
//                                IconButton(modifier = Modifier.size(size), onClick = { /*TODO*/ }) {
//                                    Icon(painter = painterResource(id = R.drawable.ic_baseline_play_arrow_64),
//                                        contentDescription = null,
//                                        tint = colorResource(id = R.color.green)
//                                    )
//                                }
//                            }
//                            Row() {
//                                IconButton(modifier = Modifier.size(size), onClick = { /*TODO*/ }) {
//                                    Icon(painter = painterResource(id = R.drawable.ic_baseline_stop_64),
//                                        contentDescription = null,
//                                        tint = colorResource(id = R.color.red)
//                                    )
//                                }
//                            }
//                            Row() {
//                                IconButton(modifier = Modifier.size(size), onClick = { /*TODO*/ }) {
//                                    Icon(painter = painterResource(id = R.drawable.viewmodel),
//                                        contentDescription = null,
//                                        tint = colorResource(id = R.color.preview))
//                                }
//                            }
//                        }


                        Image(
                            painter = painterResource(id = R.drawable.oscilografas),
                            contentDescription = null,
                            modifier = Modifier.fillParentMaxWidth()
                        )


                    }
                    Row() {
                        DisableSelection {
                            Text(
                                "Dviejų diapazonų ampermetras TSRS 1967 m. Padidinto tikslumo dviejų diapozonų rodiklinis fiksuotos ritės - judančio magneto ampermetras skirtas matuoti nuolatinę ir kintamą srovę. Matavimo skalės: 0-2.5 ampero ir 0-5 voltai. Kintamos srovės dažnis 45 - 1500Hz. Matavimo paklaidos visuose diapazonuose 0.5%.",
                                maxLines = 20, textAlign = TextAlign.Center,
                                modifier = Modifier.fillParentMaxWidth()
                            )

                        }
                    }
                    Row() {
                        IconButton(modifier = Modifier.size(size), onClick = { /*TODO*/ }) {
                            Icon(
                                painter = painterResource(id = R.drawable.ic_baseline_play_arrow_64),
                                contentDescription = null,
                                tint = colorResource(id = R.color.green)
                            )
                        }
                        IconButton(modifier = Modifier.size(size), onClick = { /*TODO*/ }) {
                            Icon(
                                painter = painterResource(id = R.drawable.ic_baseline_stop_64),
                                contentDescription = null,
                                tint = colorResource(id = R.color.red)
                            )
                        }
                        IconButton(modifier = Modifier.size(size), onClick = { /*TODO*/ }) {
                            Icon(
                                painter = painterResource(id = R.drawable.viewmodel),
                                contentDescription = null,
                                tint = colorResource(id = R.color.preview)
                            )
                        }
                    }
                }

            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    MyApplicationTheme {
        Greeting("Android")
    }
}