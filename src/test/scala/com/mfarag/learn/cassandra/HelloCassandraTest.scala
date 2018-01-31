package com.mfarag.learn.cassandra

import com.datastax.driver.core.utils.UUIDs
import com.mfarag.learn.cassandra.person.Person
import com.mfarag.learn.cassandra.person.persistence.PersonsDb
import com.outworkers.phantom.connectors.{CassandraConnection, ContactPoints}
import com.outworkers.phantom.dsl._
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