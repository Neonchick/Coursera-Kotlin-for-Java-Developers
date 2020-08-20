package nicestring

fun String.isNice(): Boolean
{
    val zip = this.zipWithNext()
    val if1 = zip.none { (f, s) -> (f == 'b' && (s == 'u' || s == 'a' || s == 'e')) }
    val if2 = this.count { letter -> "aeiou".any { it == letter } } >= 3
    val if3 = zip.any { (f, s) -> f == s }
    return listOf(if1, if2, if3).count { it } >= 2
}