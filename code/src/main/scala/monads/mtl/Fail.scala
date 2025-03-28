package monads.mtl

import monads.{IO, Monad}
import monads.Monad.lift
import monads.transformers.{OptionT, MonadTransformer}
import monads.Identity

trait Fail[M[_]]:
  def fail[A]: M[A]

object Fail:
  def apply[M[_]: Monad: Fail]: Fail[M] = summon[Fail[M]]

  given optionCanFail[M[_]: Monad]: Fail[OptionT[M, _]] with
    def fail[A]: OptionT[M, A] = OptionT.fail

  given ioCanFail: Fail[IO] with
    def fail[A]: IO[A] = IO(() => throw Exception())

  // format: off
  given transformerCanFail[
    T[_[_],_]: MonadTransformer,
    M[_]: Monad: Fail]: Fail[T[M, _]] with
    def fail[A] = Fail[M].fail.lift[T]
  // format: on

  object Examples extends App:
    object WithExplicitUsing:
      def divide[M[_]: Monad](dividend: Int, divisor: Int)(using
        F: Fail[M]
      ): M[Int] =
        if divisor == 0 then F.fail
        else Monad.pure(dividend / divisor)

    def divide[M[_]: Monad: Fail](
      dividend: Int,
      divisor: Int
    ): M[Int] =
      if divisor == 0 then Fail[M].fail
      else Monad.pure(dividend / divisor)

@main def runFailExample =
  import Fail.Examples.*
  val res1 =
    divide[OptionT[Identity, _]](10, 0).runOptionT // None
  println(res1)
  val res2 = divide[IO](10, 0).unsafeRun() // throws exception
  println(res2) // never reach this
