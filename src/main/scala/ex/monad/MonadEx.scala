package ex.monad

import cats.Monad

/*
* Foo 에 대한 Monad 인스턴스 만들기 연습
* */
object MonadEx extends App {

  import cats.implicits._

  case class Foo[A](a: A)

  implicit val fooMonadInstance = new Monad[Foo] {
    override def pure[A](x:A):Foo[A] = Foo(x)
    override def flatMap[A, B](fa:Foo[A])(f:A => Foo[B]):Foo[B] = f(fa.a)
    override def tailRecM[A, B](a:A)(f:A => Foo[Either[A, B]]):Foo[B] = ???
  }

  /*
  * 이제 foo 도 flatMap 쓸 수 있다.
  * */
  val foo = Foo(10)
  val result = foo.flatMap(a =>
    Foo((a*100).toString)
  )
  println(s"===> $result")
}
