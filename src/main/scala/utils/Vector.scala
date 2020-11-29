package utils

class Vector(val x: Int, val y: Int) {
    def +(that: Vector): Vector = new Vector(this.x + that.x, this.y + that.y)
    def -(that: Vector): Vector = new Vector(this.x - that.x, this.y - that.y)
    def *(that: Int): Vector = new Vector(this.x * that, this.y * that)
    def abs: Int = Math.abs(x) + Math.abs(y)

    override def toString: String = f"Vector($x,$y)"

    def direction(maxDistance: Int): Vector = {
        if (this.abs <= maxDistance) {
            this
        } else {
            val diagonally: Int = Math.min(
                maxDistance / 2,
                Math.min(Math.abs(this.x), Math.abs(this.y))
            )
            val notDiagonally = maxDistance - diagonally * 2

            val diagonalDirection = new Vector(
                Math.signum(this.x).toInt,
                Math.signum(this.y).toInt
            )
            val diagonalVector = diagonalDirection * diagonally

            val notDiagonalVector = new Vector(
                diagonalDirection.x * Math.min(Math.abs(this.x - diagonalVector.x), Math.abs(diagonalDirection.x * notDiagonally)),
                diagonalDirection.y * Math.min(Math.abs(this.y - diagonalVector.y), Math.abs(diagonalDirection.y * notDiagonally))
            )

            diagonalVector + notDiagonalVector
        }
    }
}
