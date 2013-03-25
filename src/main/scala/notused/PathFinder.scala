package notused

/**
 *
 */
class PathFinder {

  /*

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
