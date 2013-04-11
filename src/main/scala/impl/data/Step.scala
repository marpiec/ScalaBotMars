package impl.data

import impl.languageutil.MathUtil

object Step {
  private val DIRECTIONS = Array(XY(0, -1), XY(1, -1), XY(1, 0), XY(1, 1), XY(0, 1), XY(-1, 1), XY(-1, 0), XY(-1, -1))
  val DIRECTIONS_COUNT = DIRECTIONS.size
}

case class Step(val direction: Int) {

  def this(xyPoint:XY) = this(Step.DIRECTIONS.indexOf(xyPoint))

  lazy val xy = Step.DIRECTIONS(direction)

  def rotate(delta:Int) = {
    new Step(MathUtil.mod(direction + delta, Step.DIRECTIONS_COUNT))
  }

  override def toString: String = xy.toString
}
