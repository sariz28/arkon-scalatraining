package com.arkondata.training.repository

import com.arkondata.training.model._
import cats.effect._
import cats.implicits._
import doobie._
import doobie.implicits._
import io.chrisdavenport.log4cats.Logger

trait ShopRepository[F[_]] {
  def fetchAll(limit: Int = 50,
               offset: Int = 0): F[List[Shop]]
  def fetchNearbyShops(limit: Int = 5,
                       lat: BigDecimal,
                       long: BigDecimal,
                       shopId: Int = -1): F[List[Shop]]
  def fetchShopsInRadius(radius: Int = 50,
                         lat: BigDecimal,
                         long: BigDecimal,
                         shopId: Int = -1): F[List[Shop]]
}


object ShopRepository {

  def apply[F[_] : Sync : Logger](xa: Transactor[F]): ShopRepository[F] =
    new ShopRepository[F] {

      val selectShop: Fragment = {
        fr"""
          SELECT s.id,
                 s.name,
                 s.business_name,
                 s.address,
                 s.phone_number,
                 s.email,
                 s.website,
                 ST_Y(position::geometry) lat,
                 ST_X(position::geometry) long,
                 s.activity_id,
                 s.stratum_id,
                 s.shop_type_id

        """
      }

      def distanceShopSql(lat:BigDecimal, long:BigDecimal, shopId: Int):Fragment =
        selectShop ++ fr""" , ST_Distance(ST_POINT($long, $lat), position) distance
                             FROM shop s
                             WHERE id != $shopId
                             ORDER BY  distance"""

      def fetchAll(limit: Int = 50,
                   offset: Int = 0): F[List[Shop]] =
        Logger[F].info(s"ShopRepository fetchAll") *>
          (selectShop ++
            sql""" FROM shop s
                    ORDER BY id DESC
                    OFFSET $offset
                    LIMIT $limit;""").query[Shop]
            .to[List]
            .transact(xa)

      def fetchNearbyShops(limit: Int = 5,
                           lat: BigDecimal,
                           long: BigDecimal,
                           shopId: Int = -1): F[List[Shop]] =
        Logger[F].info(s"ShopRepository fetchNearbyShops") *>
          (distanceShopSql(lat, long, shopId) ++
            sql""" LIMIT $limit;""").query[Shop]
            .to[List]
            .transact(xa)

      def fetchShopsInRadius(radius: Int = 5,
                             lat: BigDecimal,
                             long: BigDecimal,
                             shopId: Int = -1): F[List[Shop]] =
        Logger[F].info(s"ShopRepository fetchNearbyShops") *>
          (sql"""SELECT *
                 FROM (""" ++ distanceShopSql(lat, long, shopId) ++
                sql""" ) shop_distance
                 where distance <= $radius""".stripMargin).query[Shop]
            .to[List]
            .transact(xa)
    }
}