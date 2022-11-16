package com.arbonik.answerka.core

import com.arbonik.answerka.entity.AKey
import com.arbonik.answerka.entity.AKeySerializable
import java.io.ByteArrayInputStream
import java.io.ByteArrayOutputStream
import java.io.ObjectInputStream
import java.io.ObjectOutputStream
import java.security.Key
import java.util.Base64
import javax.crypto.Cipher
import javax.crypto.SecretKeyFactory
import javax.crypto.spec.DESKeySpec

actual class PromoCodeManager {
    private val DES: String = "DES"

    actual fun isActiveCode(
        string: String
    ): KeyResult = try {
        val promoCode = decode(string)
        if (promoCode.workBefore > System.currentTimeMillis()) {
            KeyResult.Success(promoCode)
        } else {
            KeyResult.Timeout
        }
    } catch (e: Throwable) {
        KeyResult.NotCorrect
    }

    actual fun createCode(
        endTime: Long,
        dataAccess: List<String>
    ): String = encode(
        AKeySerializable(
        System.currentTimeMillis() + endTime, dataAccess
        )
    )

    private fun encode(aKeySerializable: AKey): String {
        val c = Cipher.getInstance(DES)
        val kf = SecretKeyFactory.getInstance(DES)
        val keySpec = DESKeySpec(List(8) { it.toString() }.joinToString { "" }.toByteArray())
        val key: Key? = kf.generateSecret(keySpec)
        c.init(Cipher.ENCRYPT_MODE, key)
        val byteOutputStream = ByteArrayOutputStream()
        val objectOutputStream = ObjectOutputStream(byteOutputStream)
        objectOutputStream.writeObject(aKeySerializable)
        objectOutputStream.flush()
        val encrypt = c.doFinal(byteOutputStream.toByteArray())
        return Base64.getEncoder().encodeToString(encrypt)
    }

    private fun decode(string: String): AKey {
        val c = Cipher.getInstance(DES)
        val kf = SecretKeyFactory.getInstance(DES)
        val keySpec = DESKeySpec(List(8) { it.toString() }.joinToString { "" }.toByteArray())
        val key: Key? = kf.generateSecret(keySpec)
        c.init(Cipher.DECRYPT_MODE, key)
        val encrypt : ByteArray = c.doFinal(Base64.getDecoder().decode(string))
        val inStream = ByteArrayInputStream(encrypt)
        return ObjectInputStream(inStream).readObject() as AKeySerializable
    }
}