package com.mfarag.learn.cassandra

import java.util.UUID

import com.datastax.driver.core.utils.UUIDs
import com.outworkers.phantom.connectors.{CassandraConnection, ContactPoints}
import com.outworkers.phantom.dsl.{Database, PartitionKey, Table, _}
import org.scalatest.concurrent.ScalaFutures
import org.scalatest.{FunSuite, Matchers}

import scala.concurrent.Future

class HelloCassandraTest extends FunSuite with Matchers with ScalaFutures {

  val connector: CassandraConnection = ContactPoints(Seq("localhost")).keySpace("test")
  val persons = new PersonsDb(connector)
  persons.create()

  test("Finding a lone person in an empty database should return nothing") {


    val person: Future[Option[Person]] = persons.lone(UUIDs.timeBased())

    whenReady(person)(_ shouldBe empty)
  }

}


trait Persons {
  def lone(id: UUID): Future[Option[Person]]
}

class PersonsDb(override val connector: CassandraConnection) extends Database[PersonsDb](connector) with Persons {

  object PersonTable extends PersonTable with connector.Connector


  def lone(id: UUID): Future[Option[Person]] = {
    PersonTable
      .select
      .where(_.id eqs id)
      .consistencyLevel_=(ConsistencyLevel.ONE)
      .one()
  }

}


abstract class PersonTable extends Table[PersonTable, Person] {

  override def tableName: String = "person"

  object id extends TimeUUIDColumn with PartitionKey {
    override lazy val name = "id"
  }

  object name extends StringColumn


}

case class Person(id: UUID, name: String)