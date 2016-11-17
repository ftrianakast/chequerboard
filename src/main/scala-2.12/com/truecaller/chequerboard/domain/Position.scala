package com.truecaller.chequerboard.domain

import scalaz.Monoid

case class Position(val x: Int, val y: Int)

object Position {

  implicit object PositionMonoid extends Monoid[Position] {
    override def zero: Position = Position(0, 0)

    override def append(f1: Position, f2: => Position): Position = Position(f1.x + f2.x, f1.y + f2.y)
  }

}