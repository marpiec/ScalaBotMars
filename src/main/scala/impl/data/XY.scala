package impl.data

case class XY(val x: Int, val y: Int) {

  override def toString = x + ":" + y;

}

object XY {

  //def apply(x: Int, y: Int) = new XY(x, y)

  def parse(input: String) = {
    val coords = input.split(":")
    new XY(coords(0).toInt, coords(1).toInt)
  }

}