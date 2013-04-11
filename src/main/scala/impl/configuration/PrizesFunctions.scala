package impl.configuration

import impl.data.EntitiesTypes
import impl.servercommunication.data.LastSteps

/**
 *
 */
object PrizesFunctions {


  def fear(entityType: Char, pathLength: Int): Double = {

    var pathCost = 1.0
    var nutritions = 1.0

    entityType match {
      case EntitiesTypes.BAD_BEAST => {
        pathCost = pathLength * 0.7
        nutritions = if (pathLength > 2) 0 else 200
      }
      case EntitiesTypes.ENEMY_MINI_BOT => {
        pathCost = pathLength * 0.5
        nutritions = 500
      }
      case EntitiesTypes.ENEMY_BOT => {
        pathCost = pathLength
        nutritions = 500 * 0.7
      }
    }

    -nutritions / pathCost
  }


  def hunger(entityType: Char, pathLength: Int): Double = {
    var pathCost = 1.0
    var nutritions = 1.0

    entityType match {
      case EntitiesTypes.GOOD_BEAST => {
        pathCost = pathLength * 1.5
        nutritions = 200
      }
      case EntitiesTypes.GOOD_PLANT => {
        pathCost = pathLength
        nutritions = 100
      }
    }

    nutritions / pathCost
  }

  def goHome(pathLength: Int, energy: Double, foodCount: Int, masterVisible: Boolean, time:Int): Double = {
    val scale = if (masterVisible) 1.0 else 0.1
    scale * energy * energy / 100 / pathLength * (1 + (math.max(0, (math.max(time, 4500) - 4500)).toDouble / 500.0) * 30)
  }

  def loner(pathLength: Int): Double = {
    -pathLength * pathLength / 10.0 * pathLength / 10.0
  }

  def explorer(steps: LastSteps): Double = {
    val change = steps.calculateChange
    val distance = math.max(math.abs(change.x), math.abs(change.y))
    if (distance == 0) {
      100
    } else {
      100 / distance
    }
  }

}
