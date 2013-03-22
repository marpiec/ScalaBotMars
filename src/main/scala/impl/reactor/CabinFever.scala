package impl.reactor

import impl.analyser.{DirectionCalculator, PathFinder, ViewAnalyser}
import impl.data.{XY, DirectionPreferences}

/**
 * 
 */
class CabinFever(val viewAnalyser:ViewAnalyser, val distanceMap:Array[Array[Int]]) {

  val viewSize = viewAnalyser.viewSize

  val HORIZON = 10

  def getPreferences():DirectionPreferences = {

    val directionPreferences = new DirectionPreferences()

    val pathFinder = new PathFinder(viewAnalyser)

    for (y <- -HORIZON until HORIZON) {
      val path: List[XY] = pathFinder.findShortestPathTo(new XY(HORIZON, y), distanceMap)
      increatePreferences(path, directionPreferences)
    }

    for (y <- -HORIZON until HORIZON) {
      val path: List[XY] = pathFinder.findShortestPathTo(new XY(-HORIZON, y), distanceMap)
      increatePreferences(path, directionPreferences)
    }

    for (x <- -HORIZON+1 until HORIZON-1) {
      val path: List[XY] = pathFinder.findShortestPathTo(new XY(x, HORIZON), distanceMap)
      increatePreferences(path, directionPreferences)
    }

    for (x <- -HORIZON+1 until HORIZON-1) {
      val path: List[XY] = pathFinder.findShortestPathTo(new XY(x, -HORIZON), distanceMap)
      increatePreferences(path, directionPreferences)
    }

    println("CabinFever preferences: "+directionPreferences)

    //println(distanceMap.toString)

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
    println()
               */
    directionPreferences
  }

  def increatePreferences(path: List[XY], directionPreferences: DirectionPreferences) {
    if (path.nonEmpty) {
      val firstStep = path.head
      directionPreferences.increasePreference(DirectionCalculator.getDirection(firstStep.x, firstStep.y), 0.01 * path.size)
    }
  }
}
