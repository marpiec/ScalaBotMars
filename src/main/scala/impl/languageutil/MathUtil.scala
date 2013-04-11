package impl.languageutil

/**
 * 
 */
object MathUtil {

  def mod(a: Int, b: Int) = {
    if (a >= 0) {
      a - b * (a/b)
    } else {
      a - b * (a/b) + b
    }
  }
}
