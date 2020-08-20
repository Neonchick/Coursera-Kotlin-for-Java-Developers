package mastermind

data class Evaluation(val rightPosition: Int, val wrongPosition: Int)

fun evaluateGuess(secret: String, guess: String): Evaluation
{
    var mSecret = secret;
    var mGuess = guess;
    var i = 0;
    var rp = 0
    var wp = 0;
    while (i < mSecret.length)
    {
        if (mSecret[i] == mGuess[i])
        {
            mSecret = mSecret.removeRange(i, i + 1);
            mGuess = mGuess.removeRange(i, i + 1);
            rp++;
        } else i++;
    }
    for (i in mSecret)
        for (j in mGuess.indices)
            if (i == mGuess[j])
            {
                mGuess = mGuess.removeRange(j, j + 1);
                wp++
                break
            }
    return Evaluation(rp, wp);
}
