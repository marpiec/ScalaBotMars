package impl.function

import impl.data.XY

class ReactFunction(val generation: Int, val name: String, val time: Int, val view: String,
                    val energy: Int, val masterOption: Option[XY], val collisionOption: Option[XY],
                    val slaves: Int, val other: Map[String, String]) {


  val viewDistance = calculateViewDistance


  def calculateViewDistance: Double = {
    if (view.isEmpty) {
      0
    }
    else {
      (math.sqrt(view.length) - 1) / 2
    }
  }

  def viewToFormattedString() = {
    val sb = new StringBuilder()
    val viewSize = math.sqrt(view.length).toInt
    for (y <- 0 until viewSize) {
      for (x <- 0 until viewSize) {
        sb.append(view.charAt(x + y * viewSize)).append(" ")
      }
      sb.append("\n")
    }
    sb.toString
  }
}
