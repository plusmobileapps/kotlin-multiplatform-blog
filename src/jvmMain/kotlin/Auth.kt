import io.ktor.util.KtorExperimentalAPI
import io.ktor.util.hex
import javax.crypto.Mac
import javax.crypto.spec.SecretKeySpec

const val MIN_USER_ID_LENGTH = 4
const val MIN_PASSWORD_LENTH = 6

private val secretKey = System.getenv("SECRET_KEY")

@KtorExperimentalAPI
val hashKey = hex(secretKey)
//val hashKey = "asdf3".toByteArray()

val hmacKey = SecretKeySpec(hashKey, "HmacSHA1")

@KtorExperimentalAPI
fun hash(password: String): String = with(Mac.getInstance("HmacSHA1")) {
    init(hmacKey)
    hex(this.doFinal(password.toByteArray(Charsets.UTF_8)))
}

private val userIdPattern = "[a-zA-Z0-9_\\.]+".toRegex()

internal fun userNameValid(userId: String): Boolean = userId.matches(userIdPattern)