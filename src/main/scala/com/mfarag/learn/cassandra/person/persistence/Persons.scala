package com.mfarag.learn.cassandra.person.persistence

import java.util.UUID

import com.mfarag.learn.cassandra.person.Person

import scala.concurrent.Future

trait Persons {
  def lone(id: UUID): Future[Option[Person]]
}
