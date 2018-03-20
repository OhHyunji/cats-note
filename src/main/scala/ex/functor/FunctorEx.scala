package ex.functor

import cats.Functor

/*
* Id 에 대한 Functor 인스턴스 만들기 연습
* */
object FunctorEx extends App {

  import cats.implicits._
  import cats.syntax.list._

  val xs = List(1, 2, 3)

  xs.map(_ * 10)  //스칼라 내장함수 map 쓴 것
  Functor[List].map(xs)(_ * 10)   //Functor map 쓴 것
  xs.fmap(_ * 10)   //Functor map 쓴 것
  /*
  * fmap
  * - cats 에서 구현해놓은 것이라, import cats.implicits._ 해야함.
  *
  * fmap 들어가보면
  * - Functor 의 method 로 구현된 것을 확인할 수 있고,
  * - apply 보면 argument 에 implicit 으로 Functor[F] 를 받는 것을 확인할 수 있다.
  * - 위에서 cats implicit 인스턴스들을 import 했기 때문에
  * - Functor[List] 가 apply 에 implicit argument 로 넘어갔을 것이고, 그래서 xs.fmap 사용할 수 있는 것이다.
  * - 이 모든것을 컴파일러가 알아서 해준다.
  * */

  case class Id[A](a: A)

  /*
  * Functor 인스턴스 생성
  * - Functor[List] 이런 아이들은 cats 에 implicit 인스턴스가 있지만,
  * - Functor[Id]는 없을 테니, Functor[Id]의 implicit 인스턴스를 생성해야
  * - Id 타입에서도 .fmap 을 사용할 수 있다.
  *
  * object 는 singleton 이니까 implicit object 이렇게 해도 (되긴)되고
  * implicit object catsIdFunctorInstance extends Functor[Id] {
  *  override def map[A, B](fa: Id[A])(f: A => B): Id[B] = Id(f(fa.a))
  * }
  *
  * 아래처럼 implicit val 이렇게 해도 된다.
  * */
  implicit val idFunctorInstance = new Functor[Id] {
    override def map[A, B](fa: Id[A])(f: A => B): Id[B] = Id(f(fa.a))
  }

  val id = Id(10)
  println(id.fmap(_ * 100))
  /*
  * Functor 인스턴스만 만들면 여기에 달려있는 method 들도 따라온다.
  * id. 해서 보면 fmap 말고도 Functor 인스턴스라서 생긴 method 들이 쭉 보인다.
  * */
}

/*
 * Note
 *
 * type constructor, higher kind type
 * - (참고: cats 책 p69)
 * - List: type constructor
 * - List[Int]: type
 *
 * value -> type 으로 바꿀 수 있고,
 * function -> type constructor 로 바꿀 수 있다.
 *
 * Functor in Cats
 * - (참고: cats 책 p71)
 * */