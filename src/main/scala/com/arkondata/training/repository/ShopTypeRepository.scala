package com.arkondata.training.repository

import com.arkondata.training.model.ShopType
import cats.effect._
import doobie._
import doobie.implicits._
import io.chrisdavenport.log4cats.Logger

trait ShopTypeRepository[F[_]] {
  def fetchById(shopTypeId: Option[Int]) : F[Option[ShopType]]

}
object ShopTypeRepository {

  def apply[F[_]: Sync: Logger](xa: Transactor[F]): ShopTypeRepository[F] =
    new ShopTypeRepository[F] {

      def fetchById(shopTypeId: Option[Int]) : F[Option[ShopType]] =
        sql"""select *
              from shop_type
              where id = $shopTypeId
           """.query[ShopType].option.transact(xa)
    }

}

