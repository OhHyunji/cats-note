package ex.functor

import cats.Functor

/*
 * monad, monadic, functor
 *
 * monad
 * - 값을 꺼낼 수 있는 타입클래스
 *
 * monadic
 * - 모나드로 만들 수 있는 자료구조를 모나딕이라고 한다.
 * - Option 은 모나드가 아니고, 모나딕이다.
 * - Option 은 모나드랑 상관은 없지만 map 을 사용할 수 있기 때문에 모나딕이라 한다. #공부해야징ㅋ
 * - 모나딕이면 모나드가 될 수 있다.
 * - 모나딕이면 모나드 타입 인스턴스를 만들 수 있다.
 *
 * 데이터타입, 타입클래스
 * - 데이터타입이랑 타입클래스는 장르가 다르다.
 * - Option 은 데이터타입이다.
 * - Option 을 타입클래스라고 표현하지는 않는다.
 *
 * type constructor, higher kind type
 * - cats 책에 나와있음. (p69)
 * - List: type constructor
 * - List[Int]: type
 *
 * value -> type 으로 바꿀 수 있고
 * function -> type constructor 로 바꿀 수 있다.
 *
 * Functor in Cats
 * - cats 책에 나와있음. (p71)
 * */

object CatsFunctorEx extends App {

  import cats.implicits._
  import cats.syntax.list._   //TODO: 이것은 뭐때문에 import 하는거지

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
  implicit val catsIdFunctorInstance = new Functor[Id] {
    override def map[A, B](fa: Id[A])(f: A => B): Id[B] = Id(f(fa.a))
  }

  val id = Id(10)
  println(id.fmap(_ * 100))
  /*
  * Functor 인스턴스만 만들면 여기에 달려있는 method 들도 따라온다.
  * id. 해서 보면 fmap 말고도 Functor 인스턴스라서 생긴 method 들이 쭉 보인다.
  * */
}