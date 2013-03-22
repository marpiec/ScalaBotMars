package impl.analyser

import impl.data.{XY, EntitiesTypes}

/**
 * 
 */
class DistanceMapCreator(val viewAnalyser:ViewAnalyser) {

  val MAX_STEPS = viewAnalyser.viewDistance * 2
  val viewSize = viewAnalyser.viewSize
  val viewCenter = viewAnalyser.viewDistance

  def createDistanceMap():Array[Array[Int]] = {
    val distanceMap = Array.fill(viewSize, viewSize)(Int.MaxValue)
    distanceMap(viewCenter)(viewCenter) = 0

    for (step <- 1 to MAX_STEPS) {
      val maxXandY = math.min(step, viewAnalyser.viewDistance)
      for (y <- -maxXandY to maxXandY) {
        for (x <- -maxXandY to maxXandY) {
          val point = viewAnalyser.getViewPoint(viewCenter + x, viewCenter + y)
          if (distanceMap(viewCenter + x)(viewCenter + y) > step && !EntitiesTypes.isWall(point)&& !EntitiesTypes.isInvisible(point)) {
            if (findNeighbourWithStep(step - 1, distanceMap, viewCenter + x, viewCenter + y).isDefined) {
              distanceMap(viewCenter + x)(viewCenter + y) = step
            }
          }
        }
      }
    }
    /*
    for (y <- 0 until viewSize) {
      for (x <- 0 until viewSize) {
        if (distanceMap(x)(y) == Int.MaxValue) {
          print("  .")
        } else if(distanceMap(x)(y)<10) {
          print("  ")
          print(distanceMap(x)(y))
        } else {
          print(" ")
          print(distanceMap(x)(y))
        }
      }
      println()
    }
    println()       */
    distanceMap
  }

  def findNeighbourWithStep(step: Int, distanceMap: Array[Array[Int]], sourceX: Int, sourceY: Int):Option[XY] = {
    val minX = if (sourceX == 0) 0 else -1
    val minY = if (sourceY == 0) 0 else -1

    val maxX = if (sourceX == distanceMap.size - 1) 0 else 1
    val maxY = if (sourceY == distanceMap.size - 1) 0 else 1

    for (x <- minX to maxX) {
      for (y <- minY to maxY) {
        if (distanceMap(sourceX+x)(sourceY+y) == step) {
          return Option(new XY(sourceX+x, sourceY+y))
        }
      }
    }
    return None
  }
}
