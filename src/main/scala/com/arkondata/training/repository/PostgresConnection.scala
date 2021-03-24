package com.arkondata.training.repository

import cats.effect._
import doobie.hikari._
import doobie.util.ExecutionContexts


object PostgresConnection {

  def transactor[F[_]: Async: ContextShift](
                                             blocker: Blocker
                                           ): Resource[F, HikariTransactor[F]] =
    ExecutionContexts.fixedThreadPool[F](10).flatMap { ce =>
      HikariTransactor.newHikariTransactor(
        "org.postgresql.Driver",
        "jdbc:postgresql:shopsapi",
        "sa",
        "1234",
        ce,
        blocker
      )
    }


}