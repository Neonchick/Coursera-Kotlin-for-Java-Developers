package rationals

import java.lang.IllegalArgumentException
import java.math.BigInteger

data class Rational
private constructor(val n: BigInteger, val d: BigInteger) : Comparable<Rational>
{
    companion object
    {
        fun createRational(n: BigInteger, d: BigInteger) = normRational(n, d)
        private fun normRational(n: BigInteger, d: BigInteger): Rational
        {
            if (d == 0.toBigInteger())
                throw IllegalArgumentException("Zero denominator")
            val gcd = d.gcd(n)
            val sign = d.signum().toBigInteger();
            return Rational(n / gcd * sign, d / gcd * sign)
        }
    }

    operator fun plus(other: Rational) = createRational(n * other.d + other.n * d, d * other.d)

    operator fun minus(other: Rational) = createRational(n * other.d - other.n * d, d * other.d)

    operator fun times(other: Rational) = createRational(n * other.n, d * other.d)

    operator fun div(other: Rational) = createRational(n * other.d, d * other.n)

    operator fun unaryMinus() = Rational(-this.n, this.d)

    override fun compareTo(other: Rational) = (this.n * other.d).compareTo(other.n * this.d)

    override fun toString(): String
    {
        var str = n.toString()
        if (d != 1.toBigInteger())
            str += "/$d"
        return str
    }
}

infix fun Int.divBy(d: Int) = Rational.createRational(this.toBigInteger(), d.toBigInteger())

infix fun Long.divBy(d: Long) = Rational.createRational(this.toBigInteger(), d.toBigInteger())

infix fun BigInteger.divBy(d: BigInteger) = Rational.createRational(this, d)

fun String.toRational(): Rational
{
    fun String.toBigIntOrFail(): BigInteger =
            this.toBigIntegerOrNull() ?: throw IllegalArgumentException(
                    "Not \"n/n\" or \"n\" in string: $this@toRational")
    if ('/' in this)
        return Rational.createRational(this.substringBefore('/').toBigInteger(),
                this.substringAfter('/').toBigInteger())
    return Rational.createRational(this.toBigInteger(), 1.toBigInteger())
}

fun main()
{
    val half = 1 divBy 2
    val third = 1 divBy 3

    val sum: Rational = half + third
    println(5 divBy 6 == sum)

    val difference: Rational = half - third
    println(1 divBy 6 == difference)

    val product: Rational = half * third
    println(1 divBy 6 == product)

    val quotient: Rational = half / third
    println(3 divBy 2 == quotient)

    val negation: Rational = -half
    println(-1 divBy 2 == negation)

    println((2 divBy 1).toString() == "2")
    println((-2 divBy 4).toString() == "-1/2")
    println("117/1098".toRational().toString() == "13/122")

    val twoThirds = 2 divBy 3
    println(half < twoThirds)

    println(half in third..twoThirds)

    println(2000000000L divBy 4000000000L == 1 divBy 2)

    println("912016490186296920119201192141970416029".toBigInteger() divBy
            "1824032980372593840238402384283940832058".toBigInteger() == 1 divBy 2)
}




