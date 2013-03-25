package impl.analyser

import impl.data.{EntitiesTypes, XY}

/**
 *
 */
class ViewAnalyser(view: String) {

  val viewSize = math.sqrt(view.length).toInt

  val viewDistance = (viewSize - 1) / 2

  def getViewPointFromRelative(xy: XY): Char = view.charAt((xy.y + viewDistance) * viewSize + (xy.x + viewDistance))

  def getViewPoint(x: Int, y: Int): Char = view.charAt(y * viewSize + x)

  def getViewPointRelative(x: Int, y: Int): Char = view.charAt((y + viewDistance) * viewSize + (x + viewDistance))

  lazy val goodPlants = findEntitiesOfType(EntitiesTypes.GOOD_PLANT)

  lazy val goodBeasts = findEntitiesOfType(EntitiesTypes.GOOD_BEAST)

  lazy val badPlants = findEntitiesOfType(EntitiesTypes.BAD_PLANT)

  lazy val badBeasts = findEntitiesOfType(EntitiesTypes.BAD_BEAST)

  lazy val enemyBots = findEntitiesOfType(EntitiesTypes.ENEMY_BOT)

  lazy val enemyMiniBots = findEntitiesOfType(EntitiesTypes.ENEMY_MINI_BOT)

  lazy val myBots = findEntitiesOfType(EntitiesTypes.MY_BOT)

  lazy val myMiniBots = findEntitiesOfType(EntitiesTypes.MY_MINI_BOT)

  def enemiesCount = badPlants.size + enemyBots.size + enemyMiniBots.size

  private def findEntitiesOfType(entityType: Char): List[XY] = {

    var foundEntities = List[XY]()
    for (y <- 0 until viewSize) {
      for (x <- 0 until viewSize) {
        if (getViewPoint(x, y) == entityType) {
          foundEntities ::= new XY(x - viewDistance, y - viewDistance)
        }
      }
    }
    foundEntities
  }
}
