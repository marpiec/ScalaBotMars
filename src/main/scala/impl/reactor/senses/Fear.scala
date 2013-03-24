package impl.reactor.senses

import impl.analyser.{DirectionCalculator, PathFinder, ViewAnalyser}
import impl.data.{EntitiesTypes, XY, DirectionPreferences}

class Fear(val viewAnalyser:ViewAnalyser) {

   def getPreferences():DirectionPreferences = {

     val directionPreferences = new DirectionPreferences()

     val goodPlantsRelative: List[XY] = viewAnalyser.badPlants ::: viewAnalyser.badBeasts ::: viewAnalyser.enemyMiniBots ::: viewAnalyser.enemyBots

     goodPlantsRelative.foreach(plantPositionRelative => {
       val pathFinder = new PathFinder(viewAnalyser)
       val pathSize = PathFinder.calculateRequiredSteps(plantPositionRelative)
       val nextStep = pathFinder.findNextStepTo(plantPositionRelative)
       //val path = pathFinder.findShortestPathTo(plantPositionRelative, distanceMap)

         //println("Beast in "+plantPositionRelative+ "  \tdirection "+nextStep+"   \t distance:"+pathSize)


         val pathCost = if (EntitiesTypes.isBadBeast(viewAnalyser.getViewPointFromRelative(plantPositionRelative)))
           math.pow(pathSize * 1.5, 1.5)
         else if(EntitiesTypes.isBadPlant(viewAnalyser.getViewPointFromRelative(plantPositionRelative)))
           pathSize
         else if(EntitiesTypes.isEnemyBot(viewAnalyser.getViewPointFromRelative(plantPositionRelative)))
           math.pow(pathSize, 0.5)
         else if(EntitiesTypes.isEnemyMiniBot(viewAnalyser.getViewPointFromRelative(plantPositionRelative)))
           math.pow(pathSize, 0.5)
         else
           1.0

         val nutritionPrize = if (EntitiesTypes.isBadBeast(viewAnalyser.getViewPointFromRelative(plantPositionRelative)))
           if(pathSize > 7) 0 else 200
         else if(EntitiesTypes.isBadPlant(viewAnalyser.getViewPointFromRelative(plantPositionRelative))) {
           if(pathSize > 1) 0 else 150
         } else if(EntitiesTypes.isEnemyBot(viewAnalyser.getViewPointFromRelative(plantPositionRelative)))
           1000
         else if(EntitiesTypes.isEnemyMiniBot(viewAnalyser.getViewPointFromRelative(plantPositionRelative)))
           1000
         else
          1

       if(EntitiesTypes.isBadPlant(viewAnalyser.getViewPointFromRelative(plantPositionRelative))) {
         directionPreferences.decreasePreferenceSharp(DirectionCalculator.getDirection(nextStep.x, nextStep.y), nutritionPrize / pathCost) // w zaleznosci od odleglosci
       } else {
           directionPreferences.decreasePreference(DirectionCalculator.getDirection(nextStep.x, nextStep.y), nutritionPrize / pathCost) // w zaleznosci od odleglosci
       }

     })

     //println("Direction fear: "+directionPreferences)

     directionPreferences
   }

 }
