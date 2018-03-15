package ex.functor

import cats.Functor

/*
* Monad 는 합성이 안되서 Monad Transformer 를 만들어야 하지만
* Functor 는 그냥 compose 쓰면 된다. (함수합성)
* */
object CatsFunctorComposeEx extends App {

  import cats.implicits._

  val result = Functor[List].compose[Option].map(List(Option(1)))(_ + 1)
  println(result)
}
