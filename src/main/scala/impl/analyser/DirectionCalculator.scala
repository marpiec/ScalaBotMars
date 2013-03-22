package impl.analyser

import impl.data.XY

object DirectionCalculator {

  val DIRECTIONS_COUNT = 8
  val MAX_RADIANS = 2 * math.Pi

  def getDirection(x:Int, y:Int):Int = {

    val radius = math.sqrt(x * x + y * y)

    var minDistance = Double.MaxValue
    var minDistanceDirection = -1
    for (direction <- 0 until DIRECTIONS_COUNT) {
      val tempX = math.round(math.sin(MAX_RADIANS / DIRECTIONS_COUNT.toDouble * direction.toDouble) * radius).toInt
      val tempY = -math.round(math.cos(MAX_RADIANS / DIRECTIONS_COUNT.toDouble * direction.toDouble) * radius).toInt
      val distance = calculateDistance(x, y, tempX, tempY)
      if (distance < minDistance) {
        minDistance = distance
        minDistanceDirection = direction
      }
    }

    minDistanceDirection
  }

  def getNextStepIntoDirection(direction: Int):XY = {
    val radius = 1 // some arbitrary radius greatly larger than 1

    val targetX = math.round(math.sin(MAX_RADIANS / DIRECTIONS_COUNT.toDouble * direction.toDouble) * radius).toInt
    val targetY = -math.round(math.cos(MAX_RADIANS / DIRECTIONS_COUNT.toDouble * direction.toDouble) * radius).toInt

    var bestXY:XY = null
    var lowestDistance = Double.MaxValue
    for (x <- -1 to 1) {
      for (y <- -1 to 1) {
        val distance = calculateDistance(targetX, targetY, x, y)
         if (distance < lowestDistance) {
           lowestDistance = distance
           bestXY = new XY(x, y)
         }
      }
    }

    bestXY
  }


  private def calculateDistance(x1:Int, y1:Int, x2:Int, y2:Int):Double = {
    math.sqrt((x1 - x2) * (x1 - x2) + (y1 - y2) * (y1 - y2))
  }

}
