import akka.actor.{ ActorSystem, Actor, ActorRef, Props}
import scala.io.StdIn.readLine

object StorageApp extends App {
	println("Start")
	// все акторы принадлежат одной из систем акторов
	private val actorSystem = ActorSystem()

	private val storage: ActorRef = actorSystem.actorOf(Props[Storage]())

	private val client: ActorRef = actorSystem.actorOf(Props[Client]())

	client ! Client.Connect(storage)
}









