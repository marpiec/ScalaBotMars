package impl.analyser

import impl.data.XY

class PathFinder(viewAnalyser: ViewAnalyser) {

  val MAX_STEPS = viewAnalyser.viewDistance //moze zmienic to gdzy bedzie pamietal mape



  def findShortestPathTo(relativeTarget:XY, distanceMap:Array[Array[Int]]): List[XY] = {

    val viewCenter = viewAnalyser.viewDistance

    var currentPoint = new XY(relativeTarget.x + viewCenter, relativeTarget.y + viewCenter)
    var path = List[XY]()
    var lastDistance = distanceMap(currentPoint.x)(currentPoint.y)
    if (lastDistance <=MAX_STEPS) {
      while(distanceMap(currentPoint.x)(currentPoint.y) > 0) {
        val nextPoint = findNeighbourWithStep(lastDistance - 1, distanceMap, currentPoint.x, currentPoint.y).get
        path ::= new XY(currentPoint.x - nextPoint.x , currentPoint.y - nextPoint.y)
        currentPoint = nextPoint
        lastDistance -= 1
      }
    } else {
      println("No path")
    }

    print(relativeTarget)
    print(" -----> ")

    path.foreach(ste => {
      print(ste)
      print(" ")
    })
    println()
    println()


    path
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
