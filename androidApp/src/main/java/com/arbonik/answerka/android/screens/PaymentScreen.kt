package com.arbonik.answerka.android.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.material.TextFieldDefaults
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Clear
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.arbonik.answerka.android.R
import com.arbonik.answerka.core.KeyResult
import com.arbonik.answerka.viewmodels.PaymentViewModel
import java.util.Date

@Composable
fun PaymentScreen(
    paymentViewModel: PaymentViewModel
) {
    LaunchedEffect(true) {
        paymentViewModel.loadKey()
    }

    val askQuantity = remember {
        paymentViewModel
            .askQuantity
    }

    val taskQuantity = remember {
        paymentViewModel
            .taskQuantity
    }

    val keyState = paymentViewModel
        .currentKeyResult
        .collectAsState(null)

    var text = paymentViewModel
        .currentKey
        .collectAsState()

    Column(
        modifier = Modifier
            .background(colorResource(id = R.color.back))
            .padding(horizontal = 10.dp)
            .fillMaxHeight(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Image(
            painter = painterResource(id = R.drawable.premium_photo),
            contentDescription = "Buy"
        )
        Text(
            text = "Категория 18+",
            color = Color.White
        )
        Text(
            text = "Получите доступ к самым горячим вопросам (${askQuantity}) и заданиям (${taskQuantity}), чтобы повысить градус игры ;)",
            color = Color.White
        )
        Text(
            text = "Доступ предоставляется на 30 дней",
            color = Color.White
        )
        MainButton(text = stringResource(R.string.buy)) {
            // TODO здесь будет покупка
            // paymentViewModel.generateKey()
        }
        Text(
            text = stringResource(R.string.enter_key),
            color = Color.White
        )
        OutlinedTextField(
            value = text.value ?: "",
            onValueChange = {
                paymentViewModel.activateKey(it)
            },
            label = {
                Text(text = stringResource(R.string.key))
            },
            colors = TextFieldDefaults.outlinedTextFieldColors(
                textColor = Color.White,
                disabledBorderColor = colorResource(id = R.color.green),
                focusedBorderColor = colorResource(id = R.color.green),
                unfocusedBorderColor = colorResource(id = R.color.green),
                errorBorderColor = colorResource(id = R.color.green),
                disabledTextColor = Color.White,
            ),
            singleLine = true
        )
        when (keyState.value) {
            is KeyResult.Success -> {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                ) {
                    Text(
                        modifier = Modifier.weight(3f),
                        text = "Срок действия истекает: ${Date((keyState.value as KeyResult.Success).aKey.workBefore)}",
                        color = Color.White
                    )
                    IconButton(
                        onClick = { paymentViewModel.clearKey() }) {
                        Icon(
                            modifier = Modifier.weight(1f),
                            imageVector = Icons.Filled.Clear,
                            contentDescription = null
                        )
                    }
                }
            }

            is KeyResult.NotCorrect -> Text(
                text = stringResource(R.string.key_incorrected),
                color = Color.White
            )

            is KeyResult.Timeout -> Text(
                text = stringResource(R.string.key_ran_out),
                color = Color.White
            )

            else -> {}
        }
    }
}

@Preview
@Composable
fun PreviewPaymentScreeen() {
//    PaymentScreen(
//        PaymentViewModel(
//            PromoCodeManager(),
//            KeyRepository()
//        )
//    )
}