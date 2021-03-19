package com.arkondata.training.repository

import doobie._
import doobie.implicits._
import doobie.util.ExecutionContexts
import cats._
import cats.data._
import cats.effect._
import cats.implicits._
import fs2.Stream


object PostgresConnection {

  implicit val cs = IO.contextShift(ExecutionContexts.synchronous)
  def createConnection(dbName: String, user: String, password:String ) : Transactor[IO] = {
    Transactor.fromDriverManager[IO](
      "org.postgresql.Driver",
      "jdbc:postgresql:" + dbName,
      user,
      password,
      Blocker.liftExecutionContext(ExecutionContexts.synchronous)
    )
  }
}