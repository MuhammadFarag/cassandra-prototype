package com.mfarag.learn.cassandra.person.persistence

import com.mfarag.learn.cassandra.person.Person
import com.outworkers.phantom.dsl.{PartitionKey, Table}

abstract class PersonTable extends Table[PersonTable, Person] {

  override def tableName: String = "person"

  object id extends TimeUUIDColumn with PartitionKey {
    override lazy val name = "id"
  }

  object name extends StringColumn

}
