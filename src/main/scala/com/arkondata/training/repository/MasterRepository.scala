package com.arkondata.training.repository

import cats.effect._
import doobie._
import io.chrisdavenport.log4cats.Logger

final case class MasterRepository[F[_]](
                             shopRepo: ShopRepository[F],
                             activityRepo: ActivityRepository[F],
                             stratumRepo: StratumRepository[F],
                             shopTypeRepo: ShopTypeRepository[F]
                           )

object MasterRepository {

  def apply[F[_]: Sync: Logger](xa: Transactor[F]): MasterRepository[F] =
    MasterRepository(
      ShopRepository[F](xa),
      ActivityRepository[F](xa),
      StratumRepository[F](xa),
      ShopTypeRepository[F](xa)

    )
}
