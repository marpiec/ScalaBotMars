package impl.analyser

import impl.data.{EntitiesTypes, XY}

object PathFinder {
  def calculateRequiredSteps(relativeSource:XY): Int = math.max(math.abs(relativeSource.x), math.abs(relativeSource.y))
  def calculateRequiredSteps(source:XY,destination:XY): Int = math.max(math.abs(source.x-destination.x),
    math.abs(source.y-destination.y))

}

class PathFinder(viewAnalyser: ViewAnalyser) {

  val viewDistance = viewAnalyser.viewDistance

  def findNextStepTo(relativeTarget: XY):XY = {
    var lowestLineDistanceSquared = Double.MaxValue
    var lowestDistanceStep:XY = null

    for (x <- -1 to 1) {
      for (y <- -1 to 1) {
        val viewPoint = viewAnalyser.getViewPoint(x + viewDistance, y + viewDistance)
        if (EntitiesTypes.isSafeEntity(viewPoint)) {
          val lineDistanceSquared = calculateLineDistanceSquared(relativeTarget.x - x, relativeTarget.y - y)
          if (lineDistanceSquared < lowestLineDistanceSquared) {
            lowestLineDistanceSquared = lineDistanceSquared
            lowestDistanceStep = new XY(x, y)
          }
        }
      }
    }
    lowestDistanceStep
  }

  private def calculateLineDistanceSquared(x:Int, y:Int) = x * x + y * y


}
