package hello.test;

import org.junit.Test;

import akka.actor.ActorSystem;
import akka.actor.TypedActor;
import akka.actor.TypedProps;
import sample.typed.Squarer;
import sample.typed.SquarerImpl;

public class TypedActorSyncTest {

  @Test
  public void createATypedActor() throws Exception {
    final ActorSystem system = ActorSystem.create("Main");
    final Squarer mySquarer = TypedActor.get(system)
        .typedActorOf(new TypedProps<SquarerImpl>(Squarer.class, SquarerImpl.class));

    mySquarer.squareNow(10);
    mySquarer.doSg();
//    System.out.println(mySquarer.getState());

    TypedActor.get(system).stop(mySquarer);
  }

}