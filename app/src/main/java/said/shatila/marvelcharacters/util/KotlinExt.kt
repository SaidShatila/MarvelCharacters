package said.shatila.marvelcharacters.util

import android.util.Log
import java.math.BigInteger
import java.security.MessageDigest
import java.security.NoSuchAlgorithmException

fun String.md5(): String {
    var pass = this
    var encryptedString: String? = null
    val md5: MessageDigest
    try {
        md5 = MessageDigest.getInstance("MD5")
        md5.update(pass.toByteArray(), 0, pass.length)
        pass = BigInteger(1, md5.digest()).toString(16)
        while (pass.length < 32) {
            pass = "0$pass"
        }
        encryptedString = pass
    } catch (e1: NoSuchAlgorithmException) {
        e1.printStackTrace()
    }
    Log.d("provideToMd5:", "hash -> $encryptedString")
    return encryptedString ?: ""
}

fun getUrlImageWithExtension(path: String, extension: String): String = "$path.$extension"

fun String.replaceUrlImage(): String = this.replace("http", "https")
