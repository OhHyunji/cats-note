package ex.monad

import scala.concurrent.Future

/*
* 모나드는 타입클래스다.
* 어떤게 타입이고, 어떤게 타입클래스인지 알아야한다.
* */
object MonadIntro extends App {

  import cats.implicits._
  import scala.concurrent.ExecutionContext.Implicits._

  /*
  * 모나드의 특징1. Sequence
  * 위 for comprehension 에서 모나드가 아니었다면
  * 순서를 보장하기 위해 코드가 복잡했을 것이다.
  * */
  val ten = 10

  for{
    a <- ten.pure[Future]
    b <- ten.pure[Future]
    c <- ten.pure[Future]
  } yield a+b+c


  /*
  * 모나드의 특징2. Flatmap
  *
  * 플랫맵
  * 1. Sequence
  * 2. 에러핸들링
  * 3. right-biased
  *
  * 플랫맵은 그 전 것이 끝나고 나서 그 다음 것을 호출한다. (Sequence)
  *
  * 플랫맵에서 실패나는 순간, 끝이다. (에러핸들링)
  * - railway oriented programming (참고: https://fsharpforfunandprofit.com/rop/)
  * - 플랫맵이 동작하는 방식이 아래 그림이다. (이어진 railway)
  * - 앞에서 실패나는 순간 빨간길로 빠져버린다.
  * - 에러가 발생하는 순간 그 다음꺼 진행하지 않는다.
  *
  * 그래서 Option 을 플랫맵으로 composition 하면
  * 중간에 None 이 있으면 뒤에꺼 보지도 않고 return None 하는거다.
  * */

  /*
  * right-biased exmaple
  * */
  val a: Either[Throwable, Int] = Right(10)
  def f(x:Int): Either[Throwable, String] = Right(x.toString)
  def g(ex:Throwable): Either[Throwable, String] = Right(ex.toString)

  val b = a.flatMap(f)
  /*
  * right-biased 이기 때문에
  * eitherA.flatMap(f)  OK.
  * eitherA.flatMap(g)  Type mismatch Error.
  * */

  /*
  * a leftSide 에 g 적용하려면
  * */
  b.left.flatMap(g)
}


/*
* Note
*
* Cats Type Classes 계층구조
* https://github.com/tpolecat/cats-infographic
* - Monad, Monoid 는 전혀 다르다.
* - 이 많은 계층구조 중 거의 모나드만 쓰고 있다.
*
* cats.instances.FutureInstances
* - MonadError: 에러 핸들링
* - StackSafeMonad[Future]: StackOverFlow 관련 처리들
*
* FutureInstances 를 살펴보면
* - 모나드에서 Future 에러핸들링을 제공해주고있다.
* - 메서드들 보면 future.recover 이런 아이들을 한번 감싸고있는데,
* - 이런 메서드들 덕분에 트위터/스칼라 퓨처를 따로따로 핸들링하지 않아도 된다.
* - 트위터/퓨처 따로따로 핸들링하지 말고 cats 를 통해서 호출하자.
* - Future.successful 대신 cats 에서 제공하는 pure 를 쓰자.
* - 이런면에서 보면 cats 가 미들웨어 역할을 하는 것 같다.
*
* 트위터 퓨처에 대한 cats 인스턴스는 catbird 에서 제공중.
* - 그래서 트위터/스칼라 퓨처 api 가 달라도 cats 하나로 퉁칠 수 있따.
* - 이게 cats 가 가져다주는 힘인 것 같다.
* - 더 정확히 말하면 cats 라기 보다, 타입클래스가 가져다주는 힘이다.
* - 디자인패턴의 adopter 패턴이랑 비슷하다.
*
* (참고: cats 책 p91)
* * */