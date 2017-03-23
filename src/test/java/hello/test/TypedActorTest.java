//package hello.test;
//
//import java.util.ArrayList;
//import java.util.List;
//import java.util.concurrent.TimeUnit;
//
//import org.junit.Assert;
//import org.junit.Test;
//
//import akka.actor.ActorContext;
//import akka.actor.ActorRef;
//import akka.actor.ActorSystem;
//import akka.actor.TypedActor;
//import akka.actor.TypedActorExtension;
//import akka.actor.TypedProps;
//import akka.japi.Creator;
//import akka.japi.Option;
//import akka.routing.RoundRobinGroup;
//import sample.typed.Named;
//import sample.typed.Squarer;
//import sample.typed.SquarerImpl;
//import scala.concurrent.Await;
//import scala.concurrent.Future;
//import scala.concurrent.duration.Duration;
//
//public class TypedActorTest {
//
//  ActorSystem system = ActorSystem.create("Main");
//
//  @Test
//  public void mustGetTheTypedActorExtension() {
//    Object someReference = null;
//
//    // Returns the Typed Actor Extension
//    TypedActorExtension extension = TypedActor.get(system); // system is an
//                                                            // instance of
//                                                            // ActorSystem
//
//    // Returns whether the reference is a Typed Actor Proxy or not
//    TypedActor.get(system).isTypedActor(someReference);
//
//    // Returns the backing Akka Actor behind an external Typed Actor Proxy
//    TypedActor.get(system).getActorRefFor(someReference);
//
//    // Returns the current ActorContext,
//    // method only valid within methods of a TypedActor implementation
//    ActorContext context = TypedActor.context();
//
//    // Returns the external proxy of the current Typed Actor,
//    // method only valid within methods of a TypedActor implementation
//    Squarer sq = TypedActor.<Squarer>self();
//
//    // Returns a contextual instance of the Typed Actor Extension
//    // this means that if you create other Typed Actors with this,
//    // they will become children to the current Typed Actor.
//    TypedActor.get(TypedActor.context());
//  }
//
//  @Test
//  public void createATypedActor() throws Exception {
//    Squarer mySquarer = TypedActor.get(system)
//        .typedActorOf(new TypedProps<SquarerImpl>(Squarer.class, SquarerImpl.class));
//    Squarer otherSquarer = TypedActor.get(system)
//        .typedActorOf(new TypedProps<SquarerImpl>(Squarer.class, new Creator<SquarerImpl>() {
//          @Override
//          public SquarerImpl create() {
//            return new SquarerImpl("foo");
//          }
//        }), "name");
//
//    mySquarer.squareDontCare(10);
//
//    Future<Integer> fSquare = mySquarer.square(10); // A Future[Int]
//
//    Option<Integer> oSquare = mySquarer.squareNowPlease(10); // Option[Int]
//
//    int iSquare = mySquarer.squareNow(10); // Int
//
//    Assert.assertEquals(100, Await.result(fSquare, Duration.create(3, TimeUnit.SECONDS)).intValue());
//
//    Assert.assertEquals(100, oSquare.get().intValue());
//
//    Assert.assertEquals(100, iSquare);
//
//    TypedActor.get(system).stop(mySquarer);
//
//    TypedActor.get(system).poisonPill(otherSquarer);
//  }
//
//  @Test
//  public void createHierarchies() {
//
//    Squarer childSquarer = TypedActor.get(TypedActor.context())
//        .typedActorOf(new TypedProps<SquarerImpl>(Squarer.class, SquarerImpl.class));
//    // Use "childSquarer" as a Squarer
//  }
//
//  @Test
//  public void proxyAnyActorRef() {
//
//    final ActorRef actorRefToRemoteActor = system.deadLetters();
//    Squarer typedActor = TypedActor.get(system).typedActorOf(new TypedProps<Squarer>(Squarer.class),
//        actorRefToRemoteActor);
//    // Use "typedActor" as a FooBar
//  }
//
//  @Test
//  public void typedRouterPattern() {
//    // prepare routees
//    TypedActorExtension typed = TypedActor.get(system);
//
//    Named named1 = typed.typedActorOf(new TypedProps<Named>(Named.class));
//
//    Named named2 = typed.typedActorOf(new TypedProps<Named>(Named.class));
//
//    List<Named> routees = new ArrayList<Named>();
//    routees.add(named1);
//    routees.add(named2);
//
//    List<String> routeePaths = new ArrayList<String>();
//    routeePaths.add(typed.getActorRefFor(named1).path().toStringWithoutAddress());
//    routeePaths.add(typed.getActorRefFor(named2).path().toStringWithoutAddress());
//
//    // prepare untyped router
//    ActorRef router = system.actorOf(new RoundRobinGroup(routeePaths).props(), "router");
//
//    // prepare typed proxy, forwarding MethodCall messages to `router`
//    Named typedRouter = typed.typedActorOf(new TypedProps<Named>(Named.class), router);
//
//    System.out.println("actor was: " + typedRouter.name()); // name-243
//    System.out.println("actor was: " + typedRouter.name()); // name-614
//    System.out.println("actor was: " + typedRouter.name()); // name-243
//    System.out.println("actor was: " + typedRouter.name()); // name-614
//
//    typed.poisonPill(named1);
//    typed.poisonPill(named2);
//    typed.poisonPill(typedRouter);
//  }
//
//}