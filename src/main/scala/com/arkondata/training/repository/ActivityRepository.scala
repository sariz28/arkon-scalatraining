package com.arkondata.training.repository

import com.arkondata.training.model.Activity
import cats.effect._
import doobie._
import doobie.implicits._
import io.chrisdavenport.log4cats.Logger

trait ActivityRepository[F[_]] {
  def fetchById(activityId: Option[Int]) : F[Option[Activity]]

}

object ActivityRepository{

  def apply[F[_]: Sync: Logger](xa: Transactor[F]): ActivityRepository[F] =
     new ActivityRepository[F] {

      def fetchById(activityId: Option[Int]) : F[Option[Activity]] =
        sql"""select *
              from comercial_activity
              where id = $activityId
           """.query[Activity].option.transact(xa)
    }
}