package impl.reactor

import impl.analyser.{PathFinder, DirectionCalculator, ViewAnalyser}
import impl.data.{XY, DirectionPreferences}

class FoodFinder(val viewAnalyser:ViewAnalyser, distanceMap:Array[Array[Int]]) {

  def getDirectionPreferences():DirectionPreferences = {

    val directionPreferences = new DirectionPreferences()

    val goodPlants: List[XY] = viewAnalyser.goodPlants ::: viewAnalyser.goodBeasts

    var bestDirection = 0
    var shortestDistance = Int.MaxValue
    goodPlants.foreach(plantPosition => {
      val direction = DirectionCalculator.getDirection(plantPosition.x, plantPosition.y)
      val pathFinder = new PathFinder(viewAnalyser)
      val path = pathFinder.findShortestPathTo(plantPosition, distanceMap)
      if (path.nonEmpty) {
        val firstStep = path.head
        println("getDirection "+firstStep.x +"  "+firstStep.y+" "+DirectionCalculator.getDirection(firstStep.x, firstStep.y))
        if (path.size < shortestDistance) {
          shortestDistance = path.size
          bestDirection = DirectionCalculator.getDirection(firstStep.x, firstStep.y)
        }
      }
     // directionPreferences.increasePreference(DirectionCalculator.getDirection(firstStep.x, firstStep.y), 100 - path.size) // w zaleznosci od odleglosci
    })
    if (goodPlants.nonEmpty) {
      directionPreferences.increasePreference(bestDirection, 100 - shortestDistance)
    }
    directionPreferences
  }



}
