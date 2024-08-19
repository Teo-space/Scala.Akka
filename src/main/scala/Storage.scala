import akka.actor.Actor

object Storage {
	// in
	final case class Get(key: String)
	final case class Put(key: String, value: String)
	final case class Delete(key: String)

	// out
	final case class GetResult(key: String, value: Option[String])
	case object Ack
}

class Storage extends Actor
{
	// перейдем в начальное состояние
	override def receive: Receive = process(Map.empty)

	def process(store: Map[String, String]): Receive = {

		// в ответ на сообщение Get вернем значение ключа в текущем состоянии
		// актор-отправитель сообщения доступен под именем sender
		case Storage.Get(key) =>
			sender() ! Storage.GetResult(key, store.get(key))

		// в ответ на сообщение Put перейдем в следующее состояние
		// и отправим подтверждение вызывающему
		case Storage.Put(key, value) =>
			context become process(store + (key -> value))
			sender() ! Storage.Ack

		// аналогично
		case Storage.Delete(key) =>
			context become process(store - key)
			sender() ! Storage.Ack
	}
}
