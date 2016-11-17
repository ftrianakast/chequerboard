package com.truecaller.chequerboard.domain

import com.truecaller.chequerboard.domain.Chequerboard.Path

trait ChessPieceTravelSolver {
  def findTravelSolution(chequerboard: Chequerboard, startPosition: Position, chessPiece: ChessPiece): Option[Path]
}

object WandorfsHeuristicSolver extends ChessPieceTravelSolver {
  override def findTravelSolution(chequerboard: Chequerboard, startPosition: Position, chessPiece: ChessPiece): Option[Path] = {
    findSolution(chequerboard, startPosition, chessPiece).map(p => p.reverse)
  }

  private def findSolution(chequerboard: Chequerboard, startPosition: Position, chessPiece: ChessPiece): Option[Path] = {

    def findPath(path: Path): Option[Path] = path match {
      case pth if pth.length == chequerboard.size * chequerboard.size => Some(pth)
      case pth =>
        val f = (curr: Option[Path], p: Position) => curr match {
          case Some(_) => curr
          case None => findPath(p +: pth)
        }
        wandorfsSort(pth, chessPiece, chequerboard).foldLeft[Option[Path]](None)(f)
    }

    def wandorfsSort(path: Path, chessPiece: ChessPiece, chequerBoard: Chequerboard): Vector[Position] = {
      val f = (p: Position) => chessPiece.getLegalMoves(p +: path, chequerBoard).length
      val legalMoves = chessPiece.getLegalMoves(path, chequerBoard)
      legalMoves.sortWith((p1, p2) => f(p1) < f(p2))
    }

    findPath(Vector(startPosition))
  }
}
