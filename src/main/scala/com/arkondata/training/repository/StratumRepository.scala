package com.arkondata.training.repository

import com.arkondata.training.model.Stratum
import cats.effect._
import doobie._
import doobie.implicits._
import io.chrisdavenport.log4cats.Logger


trait StratumRepository[F[_]] {
  def fetchById(stratumId: Option[Int]) : F[Option[Stratum]]

}
object StratumRepository {

  def apply[F[_]: Sync: Logger](xa: Transactor[F]): StratumRepository[F] =
    new StratumRepository[F] {

      def fetchById(stratumId: Option[Int]) : F[Option[Stratum]] =
        sql"""select *
              from stratum
              where id = $stratumId
           """.query[Stratum].option.transact(xa)
    }

}
