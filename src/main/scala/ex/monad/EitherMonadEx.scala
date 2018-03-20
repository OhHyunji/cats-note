package ex.monad

import cats.Monad

/*
* case class Foo[A](a: A)
* Foo 에 대한 Monad 인스턴스 만드는것은 타입이 한개라 쉽다.
*
* Either 에 대한 Monad 인스턴스는 타입이 두개라 어렵다.
* 이럴 때 3가지 방법이 있다.
* */
object EitherMonadEx {

  //1단계: 타입 한개를 고정
  type F[A] = Either[String, A]
  implicit def eitherMonadInstance1 = new Monad[F] {
    override def pure[A](x:A):F[A] = Right(x)
    override def flatMap[A, B](fa:F[A])(f:A => F[B]):F[B] = ???
    override def tailRecM[A, B](a:A)(f:A => F[Either[A, B]]):F[B] = ???
  }

  //2단계: type lamda 사용 (참고: 빨간책 형식 람다)
  implicit def eitherMonadInstance2[L] = new Monad[({type f[x] = Either[L, x]})#f] {
    override def pure[A](x:A):Either[L, A] = Right(x)
    override def flatMap[A, B](fa:Either[L, A])(f:A => Either[L, B]):Either[L, B] = ???
    override def tailRecM[A, B](a:A)(f:A => Either[L, Either[A, B]]):Either[L, B] = ???
  }

  //3단계: ? 기호 활용
  implicit def eitherMonadInstance3[L]:Monad[Either[L, ?]] = new Monad[Either[L, ?]] {
    override def pure[A](x:A):Either[L, A] = Right(x)
    override def flatMap[A, B](fa:Either[L, A])(f:A => Either[L, B]):Either[L, B] = ???
    override def tailRecM[A, B](a:A)(f:A => Either[L, Either[A, B]]):Either[L, B] = ???
  }
}

/*
* Note
*
* ? 기호를 사용하려면 build.sbt 에 플러그인 추가해야한다.
* addCompilerPlugin("org.spire-math" %% "kind-projector" % "0.9.3")
* */
