package impl.data


object EntitiesTypes {

  val INVISIBLE = '?'
  val EMPTY = '_'
  val WALL = 'W'
  val MY_BOT = 'M'
  val ENEMY_BOT = 'm'
  val MY_MINI_BOT = 'S'
  val ENEMY_MINI_BOT = 's'
  val GOOD_PLANT = 'P'
  val BAD_PLANT = 'p'
  val GOOD_BEAST = 'B'
  val BAD_BEAST = 'b'

  def isInvisible(pointValue: Char)    = INVISIBLE == pointValue
  def isEmpty(pointValue: Char)        = EMPTY == pointValue
  def isWall(pointValue: Char)         = WALL == pointValue
  def isMyBot(pointValue: Char)        = MY_BOT == pointValue
  def isEnemyBot(pointValue: Char)     = ENEMY_BOT == pointValue
  def isMyMiniBot(pointValue: Char)    = MY_MINI_BOT == pointValue
  def isEnemyMiniBot(pointValue: Char) = ENEMY_MINI_BOT == pointValue
  def isGoodPlant(pointValue: Char)    = GOOD_PLANT == pointValue
  def isBadPlant(pointValue: Char)     = BAD_PLANT == pointValue
  def isGoodBeast(pointValue: Char)    = GOOD_BEAST == pointValue
  def isBadBeast(pointValue: Char)     = BAD_BEAST == pointValue

  def notSafeEntity(pointValue: Char) = isWall(pointValue) || isBadBeast(pointValue) ||
    isBadPlant(pointValue) || isEnemyBot(pointValue) || isEnemyMiniBot(pointValue)
}
