package ex.functor

/*
* Functor 의 특징1. Higher Kind Type 이어야 한다.
* - List[_], Either[_, _] 등등 이런 아이들.
* - Either 는 kind 가 두개다.
*
* Either
* - Either 는 right-biased 로 map, flatmap 이 돈다.
* - map 은 하나의 타입에서 또다른 타입으로 옮기는 것인데, 오른쪽 타입을 바꾼다는 얘기다.
* - 이것은 partial unification 개념이라는건데, #찾아봐야징ㅋ
* - 반대로 left-biased 특징을 가진 자료구조도 있는데, 이것은 왼쪽 아이의 타입이 바꿔지겠지? ㅇㅇ맞음!
*
* partial unification
* - (참고: cats 책 p67)
* - (참고: https://gist.github.com/djspiewak/7a81a395c461fd3a09a6941d4cd040f2)
* - 어렵긴한데 읽어보시라. 언젠가 도움이 될 것이니!
*
* 아무튼 오늘은 Functor 를 만들어보자.
* */

/*
* Monad 와 다르게, Functor 니까 HigherKind 타입으로. [_]
* */
trait MyFunctor[F[_]] {
  def map[A, B](fa: F[A])(f: A => B): F[B]
}

case class Id[A](a: A)

object MyFunctorTest {
  /*
  * Functor 인스턴스 만들기
  * 1. 값을 꺼내서
  * 2. 함수를 적용하고
  * 3. 다시 껍데기를 씌운다.
  *
  * 만약 케이스클래스 Id가 이렇게 생겨서 a를 꺼낼 수 없다면,
  * case class Id[A](private val a: A)
  * Functor 인스턴스로 만들 수 없다.
  * */
  implicit object IdMyFunctorInstance extends MyFunctor[Id] {
    override def map[A, B](fa:Id[A])(f:A => B):Id[B] = Id(f(fa.a))
  }
}

/*
* Note
*
* higher kind type 그냥 쓰면 warning 뜬다.
*
* 방법1.
* sbt.build 에 scalacOptions += "-language:higherKinds" 추가하거나
*
* 방법2.
* 사용할때 import 하고 사용하면 된다.
* */