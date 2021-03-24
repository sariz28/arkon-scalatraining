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
  def fetchById(id: Int): F[Option[Shop]]
}


object ShopRepository{

  def fromTransactor[F[_]: Sync: Logger](xa: Transactor[F]): ShopRepository[F] =
    new ShopRepository[F] {

      val select: Fragment =
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
                 ca.name activity_name,
                 s.stratum_id,
                 st.name stratum_name,
                 s.shop_type_id,
                 sty.name shop_type_name
          FROM   shop s
          INNER JOIN comercial_activity ca
          ON s.activity_id = ca.id
          INNER JOIN stratum st
          ON s.stratum_id = st.id
          INNER JOIN shop_type sty
          ON s.shop_type_id = sty.id
        """

      def fetchAll(limit: Int = 50,
                   offset: Int = 0): F[List[Shop]] =
        Logger[F].info(s"ShopRepository.fetchAll") *>
          select.query[Shop]
            .stream
            .drop(offset)
            .take(limit)
            .compile
            .toList
            .transact(xa)

      def fetchById(id: Int): F[Option[Shop]]=
        Logger[F].info(s"ShopRepository.fetchAll") *>
          (select ++ sql"WHERE s.id = $id").query[Shop].option.transact(xa)
    }
}