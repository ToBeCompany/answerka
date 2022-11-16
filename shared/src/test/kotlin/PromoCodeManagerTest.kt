import com.arbonik.answerka.core.KeyResult
import com.arbonik.answerka.core.PromoCodeManager
import java.time.Duration
import kotlin.test.Test


class PromoCodeManagerTest {
    val promoCodeManager = PromoCodeManager()

    @Test
    fun testGoodActiveCode(){
        val mounthPromoCode = promoCodeManager.createCode(
            System.currentTimeMillis() + Duration.ofDays(30).toMillis(),
            listOf("18")
        )
        assert(promoCodeManager.isActiveCode(mounthPromoCode) is KeyResult.Success)
    }
    @Test
    fun testTimeoutActiveCode(){
        val badPromoCode = promoCodeManager.createCode(
            System.currentTimeMillis() - Duration.ofDays(1).toMillis(),
            listOf("18")
        )
        assert(promoCodeManager.isActiveCode(badPromoCode) is KeyResult.Timeout)
    }
    @Test
    fun testNotCorrectActiveCode(){
        assert(promoCodeManager.isActiveCode("сын собаки") is KeyResult.NotCorrect)
    }
}