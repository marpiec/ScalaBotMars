package notused

import scala.Array
import impl.data.{EntitiesTypes, XY}
import impl.analyser.ViewAnalyser

/**
 * @author Marcin Pieciukiewicz
 */
class DistanceMapCreator2(val viewAnalyser:ViewAnalyser) {
  val MAX_STEPS = viewAnalyser.viewDistance
  val viewSize = viewAnalyser.viewSize
  val viewCenter = viewAnalyser.viewDistance

  def createDistanceMap():Array[Array[Int]] = {

    val map =  Array.fill(viewSize, viewSize)(Int.MaxValue)

    def fillMapIter(step:Int, points:List[XY]) {

    /*  val minX = if (x == 0) 0 else -1
      val minY = if (y == 0) 0 else -1

      val maxX = if (x == viewSize - 1) 0 else 1
      val maxY = if (y == viewSize - 1) 0 else 1               */

      var newPoints = List[XY]()

      points.foreach(point => {
        val x = point.x
        val y = point.y
        for(deltaY <- -1 to 1) {
          for(deltaX <- -1 to 1) {
            val currentValue = map(x+deltaX)(y+deltaY)
            val currentMapPoint = viewAnalyser.getViewPoint(x+deltaX, y+deltaY)
            if (currentValue > step && !EntitiesTypes.isWall(currentMapPoint)&& !EntitiesTypes.isInvisible(currentMapPoint)) {
              map(x+deltaX)(y+deltaY) = step
              newPoints ::= new XY(x+deltaX, y+deltaY)
              //fillMapIter(step + 1, x+deltaX, y+deltaY)
            }
          }
        }
      })

      if (step < MAX_STEPS && newPoints.nonEmpty) {
        fillMapIter(step + 1, newPoints)
      }


    }

    map(viewCenter)(viewCenter) = 0
    fillMapIter(1, List(new XY(viewCenter,viewCenter)))


    println("Distance Map 2")
    for (y <- 0 until viewSize) {
      for (x <- 0 until viewSize) {
        if (map(x)(y) == Int.MaxValue) {
          print("  .")
        } else if(map(x)(y)<10) {
          print("  ")
          print(map(x)(y))
        } else {
          print(" ")
          print(map(x)(y))
        }
      }
      println()
    }
    println()
    map
  }
}
