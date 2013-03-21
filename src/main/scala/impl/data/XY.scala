package impl.data

case class XY(val x:Int, val y:Int)

object XY {
  def parse(input:String) = {
    val coords = input.split(":")
    new XY(coords(0).toInt, coords(1).toInt)
  }
}