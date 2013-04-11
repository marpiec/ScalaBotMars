package impl.analyser

import impl.data.{Step, XY}

object PathFinder {

  val tanPi8 = math.tan(math.Pi / 8)

  def calculateRequiredSteps(relativeXY: XY): Int = math.max(math.abs(relativeXY.x), math.abs(relativeXY.y))

  def calculateRequiredSteps(source: XY, destination: XY): Int = math.max(math.abs(source.x - destination.x),
    math.abs(source.y - destination.y))

  def findNextStepTo(viewAnalyser: ViewAnalyser, relativeTarget: XY): Step = {
    var newX = 0
    var newY = 0
    val absX = math.abs(relativeTarget.x).toDouble
    val absY = math.abs(relativeTarget.y).toDouble

    if (absX < absY * tanPi8) {
      newX = 0
      newY = 1
    } else if (absY < absX * tanPi8) {
      newX = 1
      newY = 0
    } else {
      newX = 1
      newY = 1
    }

    if (relativeTarget.x < 0) {
      newX = -newX
    }
    if (relativeTarget.y < 0) {
      newY = -newY
    }

    new Step(XY(newX, newY))
  }

  def findNextStepAndDistance(viewAnalyser: ViewAnalyser, relativeTarget: XY): (Step, Int) = {
    (findNextStepTo(viewAnalyser, relativeTarget), calculateRequiredSteps(relativeTarget))
  }

}
