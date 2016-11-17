package com.truecaller.chequerboard.domain


case class Chequerboard(size: Int)

object Chequerboard {
  type Path = Vector[Position]
  type PossibleMovements = Vector[Position]

  def isLegalMove(path: Path, position: Position, chequerboard: Chequerboard): Boolean = {
    def inChequerboard(p: Position): Boolean = {
      p.x >= 0 && p.y >= 0 && chequerboard.size > p.x && chequerboard.size > p.y
    }
    inChequerboard(position) && !path.contains(position)
  }
}
