package impl.reactor

import impl.analyser.{DirectionCalculator, PathFinder, ViewAnalyser}
import impl.data.{EntitiesTypes, XY, DirectionPreferences}

class Fear(val viewAnalyser:ViewAnalyser, distanceMap:Array[Array[Int]]) {

   def getPreferences():DirectionPreferences = {

     val directionPreferences = new DirectionPreferences()

     val goodPlantsRelative: List[XY] = viewAnalyser.badPlants ::: viewAnalyser.badBeasts

     goodPlantsRelative.foreach(plantPositionRelative => {
       val pathFinder = new PathFinder(viewAnalyser)
       val path = pathFinder.findShortestPathTo(plantPositionRelative, distanceMap)
       if (path.nonEmpty) {
         val firstStep = path.head
         println("Beast in "+plantPositionRelative+ "  \tdirection "+firstStep+"   \t distance:"+path.size)


         val pathCost = if (EntitiesTypes.isBadBeast(viewAnalyser.getViewPointFromRelative(plantPositionRelative)))
           math.pow(path.size * 1.5, 1.5)
         else
           math.pow(path.size, 1.5)

         val nutritionPrize = if (EntitiesTypes.isBadBeast(viewAnalyser.getViewPointFromRelative(plantPositionRelative)))
           200
         else
           50

           directionPreferences.decreasePreference(DirectionCalculator.getDirection(firstStep.x, firstStep.y), nutritionPrize / pathCost) // w zaleznosci od odleglosci
       }
     })

     println("Direction fear: "+directionPreferences)

     directionPreferences
   }



 }
