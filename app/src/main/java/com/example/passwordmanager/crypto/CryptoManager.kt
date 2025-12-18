package com.example.passwordmanager.crypto

import android.util.Base64
import javax.crypto.Cipher
import javax.crypto.KeyGenerator
import javax.crypto.SecretKey
import javax.crypto.spec.GCMParameterSpec
import java.security.SecureRandom

object CryptoManager {
    private const val TRANSFORMATION = "AES/GCM/NoPadding"
    private const val IV_SIZE = 12 // 12 bytes for GCM
    private const val TAG_LENGTH = 128 // 128 bits authentication tag

    private val secretKey: SecretKey by lazy {
        val keyGenerator = KeyGenerator.getInstance("AES")
        keyGenerator.init(256)
        keyGenerator.generateKey()
    }

    fun encrypt(text: String): String {
        val cipher = Cipher.getInstance(TRANSFORMATION)

        // Generate random IV for each encryption
        val iv = ByteArray(IV_SIZE)
        SecureRandom().nextBytes(iv)

        val spec = GCMParameterSpec(TAG_LENGTH, iv)
        cipher.init(Cipher.ENCRYPT_MODE, secretKey, spec)

        val encrypted = cipher.doFinal(text.toByteArray())

        // Combine IV + encrypted data
        val combined = iv + encrypted
        return Base64.encodeToString(combined, Base64.DEFAULT)
    }

    fun decrypt(text: String): String {
        val cipher = Cipher.getInstance(TRANSFORMATION)

        // Decode the Base64 string
        val combined = Base64.decode(text, Base64.DEFAULT)

        // Extract IV from the first 12 bytes
        val iv = combined.copyOfRange(0, IV_SIZE)
        val encrypted = combined.copyOfRange(IV_SIZE, combined.size)

        val spec = GCMParameterSpec(TAG_LENGTH, iv)
        cipher.init(Cipher.DECRYPT_MODE, secretKey, spec)

        return String(cipher.doFinal(encrypted))
    }
}