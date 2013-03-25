package notused

import impl.data.{XY, EntitiesTypes}
import impl.analyser.ViewAnalyser

/**
 *
 */
class DistanceMapCreator(val viewAnalyser: ViewAnalyser) {

  val MAX_STEPS = viewAnalyser.viewDistance
  val viewSize = viewAnalyser.viewSize
  val viewCenter = viewAnalyser.viewDistance

  def createDistanceMap(): Array[Array[Int]] = {
    val map = Array.fill(viewSize, viewSize)(Int.MaxValue)
    map(viewCenter)(viewCenter) = 0

    for (step <- 1 to MAX_STEPS) {
      val maxXandY = math.min(step, viewAnalyser.viewDistance)
      for (y <- -maxXandY to maxXandY) {
        for (x <- -maxXandY to maxXandY) {
          val point = viewAnalyser.getViewPoint(viewCenter + x, viewCenter + y)
          if (map(viewCenter + x)(viewCenter + y) > step && !EntitiesTypes.isWall(point) && !EntitiesTypes.isInvisible(point)) {
            if (findNeighbourWithStep(step - 1, map, viewCenter + x, viewCenter + y).isDefined) {
              map(viewCenter + x)(viewCenter + y) = step
            }
          }
        }
      }
    }

    println("Distance Map 1")
    for (y <- 0 until viewSize) {
      for (x <- 0 until viewSize) {
        if (map(x)(y) == Int.MaxValue) {
          print("  .")
        } else if (map(x)(y) < 10) {
          print("  ")
          print(map(x)(y))
        } else {
          print(" ")
          print(map(x)(y))
        }
      }
      println()
    }
    println()
    map
  }

  def findNeighbourWithStep(step: Int, distanceMap: Array[Array[Int]], sourceX: Int, sourceY: Int): Option[XY] = {
    val minX = if (sourceX == 0) 0 else -1
    val minY = if (sourceY == 0) 0 else -1

    val maxX = if (sourceX == distanceMap.size - 1) 0 else 1
    val maxY = if (sourceY == distanceMap.size - 1) 0 else 1

    for (x <- minX to maxX) {
      for (y <- minY to maxY) {
        if (distanceMap(sourceX + x)(sourceY + y) == step) {
          return Option(new XY(sourceX + x, sourceY + y))
        }
      }
    }
    None
  }
}
