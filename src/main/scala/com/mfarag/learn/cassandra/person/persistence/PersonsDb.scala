package com.mfarag.learn.cassandra.person.persistence

import java.util.UUID

import com.datastax.driver.core.ConsistencyLevel
import com.mfarag.learn.cassandra.person.Person
import com.outworkers.phantom.connectors.CassandraConnection
import com.outworkers.phantom.dsl._

import scala.concurrent.Future

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
