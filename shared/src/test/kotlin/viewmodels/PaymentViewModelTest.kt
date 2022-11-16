package viewmodels

import android.content.Context
import com.arbonik.answerka.core.PromoCodeManager
import com.arbonik.answerka.viewmodels.SettingsRepository
import com.arbonik.answerka.viewmodels.PaymentViewModel
import org.junit.Test
import org.koin.java.KoinJavaComponent.inject


// TODO
// https://insert-koin.io/docs/reference/koin-test/testing
// https://insert-koin.io/docs/reference/koin-android/instrumented-testing
class PaymentViewModelTest {
    val context : Context by inject(Context::class.java)
    val promoCodeManager = PromoCodeManager()
    val paymentViewModel = PaymentViewModel(promoCodeManager, SettingsRepository(context))

    @Test
    fun testRun(){

    }
}