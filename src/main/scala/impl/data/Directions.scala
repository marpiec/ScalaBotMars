package impl.data

object Directions {
  private val DIRECTIONS = Array(XY(0, -1), XY(1, -1), XY(1, 0), XY(1, 1), XY(0, 1), XY(-1, 1), XY(-1, 0), XY(-1, -1))

  val DIRECTIONS_COUNT = DIRECTIONS.size

  def getDirectionFor(xy: XY) = DIRECTIONS.indexOf(xy)

  def getStepForDirection(direction: Int) = DIRECTIONS(direction)

  def getStepForDirectionModulo(direction: Int) = {
    val directionModulo = if (direction < 0) {
      direction + DIRECTIONS_COUNT
    } else if (direction >= DIRECTIONS_COUNT) {
      direction - DIRECTIONS_COUNT
    } else {
      direction
    }
    DIRECTIONS(directionModulo)
  }

}
