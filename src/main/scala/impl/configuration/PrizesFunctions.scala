package impl.configuration

import impl.data.EntitiesTypes

/**
 * 
 */
object PrizesFunctions {

  def fear(entityType: Char, pathLength: Int):Double = {

    var pathCost = 1.0
    var nutritions = 1.0

    entityType match {
      case EntitiesTypes.BAD_BEAST => {
        pathCost = math.pow(pathLength * 1.5, 1.5)
        nutritions = if (pathLength > 7) 0 else 200
      }
      case EntitiesTypes.ENEMY_MINI_BOT => {
        pathCost = math.pow(pathLength * 2, 0.5)
        nutritions = 1000
      }
      case EntitiesTypes.ENEMY_BOT => {
        pathCost = math.pow(pathLength, 0.5)
        nutritions = 1000
      }
    }

    - nutritions / pathCost
  }


  def hunger(entityType: Char, pathLength: Int):Double = {
    var pathCost = 1.0
    var nutritions = 1.0

    entityType match {
      case EntitiesTypes.GOOD_BEAST => {
        pathCost = math.pow(pathLength * 1.5, 1.5)
        nutritions = 200
      }
      case EntitiesTypes.GOOD_PLANT => {
        pathCost = math.pow(pathLength, 1.5)
        nutritions = 100
      }
    }

    nutritions / pathCost
  }

  def goHome(pathLength: Int, energy:Double):Double = {
    energy * energy / 100 / pathLength
  }

  def loner(pathLength: Int): Double = {
    - pathLength * pathLength / 10.0
  }

}
