package impl.languageutil

/**
 * 
 */
object CollectionUtil {

  def findIndexOfMaxElement(collection: Array[Double]): Int = {
    val size = collection.size
    var index = -1
    var maxValue = - Double.MaxValue
    for (i <- 0 until size) {
      val value = collection(i)
      if (value > maxValue) {
        index = i
        maxValue = value
      }
    }
    index
  }

}
