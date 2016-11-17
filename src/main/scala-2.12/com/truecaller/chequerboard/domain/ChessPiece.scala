package com.truecaller.chequerboard.domain


import com.truecaller.chequerboard.domain.Chequerboard.{Path, PossibleMovements}

import scalaz.Scalaz._

trait ChessPiece {
  val getDeltas: Vector[Position]

  def getLegalMoves(currentVisitedPath: Path, chequerboard: Chequerboard): PossibleMovements = {
    def movesFrom(position: Position): Vector[Position] = {
      getDeltas.map(coordinate => coordinate |+| position)
    }
    currentVisitedPath match {
      case (p +: _) => movesFrom(p).filter(p => Chequerboard.isLegalMove(currentVisitedPath, p, chequerboard))
    }
  }
}

object Pawn extends ChessPiece {
  override val getDeltas: Vector[Position] = {
    val pst = Vector((0, -3), (2, -2), (3, 0), (2, 2), (0, 3), (-2, 2), (-3, 0), (-2, -2))
    pst.map(p => Position(p._1, p._2))
  }
}