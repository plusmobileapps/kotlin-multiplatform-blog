import io.ktor.util.hex
import javax.crypto.Mac
import javax.crypto.spec.SecretKeySpec

val hashKey = hex(System.getenv("SECRET_KEY"))

val hmacKey = SecretKeySpec(hashKey, "HmacSHA1")

fun hash(password: String): String = with(Mac.getInstance("HmacSHA1")) {
    init(hmacKey)
    hex(this.doFinal(password.toByteArray(Charsets.UTF_8)))
}

private val userIdPattern = "[a-zA-Z0-9_\\.]".toRegex()

internal fun userNameValid(userId: String): Boolean = userId.matches(userIdPattern)