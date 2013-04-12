package impl.analyser

import impl.data.{EntitiesTypes, XY}

/**
 *
 */
class ViewAnalyser(view: String) {

  val viewSize = math.sqrt(view.length).toInt
  val viewDistance = (viewSize - 1) / 2

  var goodPlants = List[XY]()
  var goodBeasts = List[XY]()
  var badPlants = List[XY]()
  var badBeasts = List[XY]()
  var enemyBots = List[XY]()
  var enemyMiniBots = List[XY]()
  var myBots = List[XY]()
  var myMiniBots = List[XY]()

  //init lists
  findEntities()


  def getViewPointFromRelative(xy: XY): Char = view.charAt((xy.y + viewDistance) * viewSize + (xy.x + viewDistance))

  def getViewPoint(x: Int, y: Int): Char = view.charAt(y * viewSize + x)

  def getViewPointRelative(x: Int, y: Int): Char = view.charAt((y + viewDistance) * viewSize + (x + viewDistance))

  def enemiesCount = badPlants.size + enemyBots.size + enemyMiniBots.size

  lazy val nearestEnemyDistance = {

    var nearest = Integer.MAX_VALUE

    (enemyBots ::: enemyMiniBots ::: badBeasts).foreach(enemy => {
      val distance = PathFinder.calculateRequiredSteps(enemy)
      if (nearest > distance) {
        nearest = distance
      }
    })
    nearest
  }

  private def findEntities() {
    for (y <- 0 until viewSize) {
      for (x <- 0 until viewSize) {
        addEntityToProperList(getViewPoint(x, y), new XY(x - viewDistance, y - viewDistance))
      }
    }
  }

  private def addEntityToProperList(viewPoint: Char, xyLocation: XY) {
    viewPoint match {
      case EntitiesTypes.GOOD_PLANT => goodPlants ::= xyLocation
      case EntitiesTypes.GOOD_BEAST => goodBeasts ::= xyLocation
      case EntitiesTypes.BAD_PLANT => badPlants ::= xyLocation
      case EntitiesTypes.BAD_BEAST => badBeasts ::= xyLocation
      case EntitiesTypes.ENEMY_BOT => enemyBots ::= xyLocation
      case EntitiesTypes.ENEMY_MINI_BOT => enemyMiniBots ::= xyLocation
      case EntitiesTypes.MY_BOT => myBots ::= xyLocation
      case EntitiesTypes.MY_MINI_BOT => myMiniBots ::= xyLocation
      case _ => {}
    }
  }

}
