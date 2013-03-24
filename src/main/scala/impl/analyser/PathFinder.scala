package impl.analyser

import impl.data.{EntitiesTypes, XY}

object PathFinder {
  def calculateRequiredSteps(relativeSource:XY): Int = math.max(math.abs(relativeSource.x), math.abs(relativeSource.y))
  def calculateRequiredSteps(relativeSource:XY,relativeDestination:XY): Int = math.max(math.abs(relativeSource.x-relativeDestination.x), math.abs(relativeSource.y-relativeDestination.y))
}

class PathFinder(viewAnalyser: ViewAnalyser) {

  val viewDistance = viewAnalyser.viewDistance
  val MAX_STEPS = viewAnalyser.viewDistance //moze zmienic to gdzy bedzie pamietal mape

  def findNextStepTo(relativeTarget: XY):XY = {
    var lowestLineDistance = Double.MaxValue
    var lowestDistanceStep:XY = null

    val minX = if (relativeTarget.x == -viewDistance) 0 else -1
    val minY = if (relativeTarget.y == -viewDistance) 0 else -1

    val maxX = if (relativeTarget.x == viewDistance) 0 else 1
    val maxY = if (relativeTarget.y == viewDistance) 0 else 1

    for (x <- minX to maxX) {
      for (y <- minY to maxY) {
        val viewPoint = viewAnalyser.getViewPoint(x + viewDistance, y + viewDistance)
        if (!EntitiesTypes.isWall(viewPoint)) {
          val lineDistance = math.sqrt((relativeTarget.x - x) * (relativeTarget.x - x) + (relativeTarget.y - y) * (relativeTarget.y - y))
          if (lineDistance < lowestLineDistance) {
            lowestLineDistance = lineDistance
            lowestDistanceStep = new XY(x, y)
          }
        }
      }
    }
    lowestDistanceStep
  }                 /*

  def findShortestPathTo(relativeTarget: XY, distanceMap: Array[Array[Int]]): List[XY] = {

    val viewCenter = viewAnalyser.viewDistance

    var relativeCurrentPoint = new XY(relativeTarget.x, relativeTarget.y)
    var path = List[XY]()

    //var lastDistance = distanceMap(relativeCurrentPoint.x)(relativeCurrentPoint.y)
    var lastDistance = calculateDistance(relativeCurrentPoint.x, relativeCurrentPoint.y)
    if (lastDistance <= MAX_STEPS) {
      while (relativeCurrentPoint.x != 0 || relativeCurrentPoint.y != 0) {
        val nextStep = findNeighbourWithStep(lastDistance - 1, relativeCurrentPoint.x, relativeCurrentPoint.y).get
        path ::= nextStep
        relativeCurrentPoint = new XY(nextStep.x + relativeCurrentPoint.x, nextStep.y + relativeCurrentPoint.y)
        lastDistance -= 1
      }
    } else {
      // No path
    }

    print("Path to "+relativeTarget+" (distance="+calculateDistance(relativeTarget.x, relativeTarget.y)+") += ")
    path.foreach(point => {
      print(point+" -> ")
    })
    println

    path
  }

  def findNeighbourWithStep(step: Int, relativeSourceX:Int, relativeSourceY:Int): Option[XY] = {
    var lowestLineDistance = Double.MaxValue
    var lowestDistanceOption:Option[XY] = None

    for (x <- -1 to 1) {
      for (y <- -1 to 1) {
        if (calculateDistance(relativeSourceX + x, relativeSourceY + y) == step) {
          val lineDistance = math.sqrt((relativeSourceX + x) * (relativeSourceX + x) + (relativeSourceY + y) * (relativeSourceY))
          if (lineDistance < lowestLineDistance) {
            lowestLineDistance = lineDistance
            lowestDistanceOption = Option(new XY(x, y))
          }
        }
      }
    }
    lowestDistanceOption
  }
                                */
  def calculateDistance(relativeSourceX:Int, relativeSourceY:Int): Int = math.max(math.abs(relativeSourceX), math.abs(relativeSourceY))
        /*
  def findNeighbourWithStepOld(step: Int, sourceX: Int, sourceY: Int): Option[XY] = {
    val minX = if (sourceX == 0) 0 else -1
    val minY = if (sourceY == 0) 0 else -1

    val maxX = if (sourceX == distanceMap.size - 1) 0 else 1
    val maxY = if (sourceY == distanceMap.size - 1) 0 else 1

    var lowestLineDistance = Double.MaxValue
    var lowestDistanceOption:Option[XY] = None
    for (x <- minX to maxX) {
      for (y <- minY to maxY) {
        if (distanceMap(sourceX + x)(sourceY + y) == step) {
          val lineDistance = math.sqrt((sourceX + x - viewAnalyser.viewDistance) * (sourceX + x- viewAnalyser.viewDistance) + (sourceY + y- viewAnalyser.viewDistance) * (sourceY + y- viewAnalyser.viewDistance))
          if (lineDistance < lowestLineDistance) {
            lowestLineDistance = lineDistance
            lowestDistanceOption = Option(new XY(sourceX + x, sourceY + y))
          }
        }
      }
    }
    return lowestDistanceOption
  }         */

}
