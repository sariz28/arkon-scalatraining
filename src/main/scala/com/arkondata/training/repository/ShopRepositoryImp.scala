package com.arkondata.training.repository

import com.arkondata.training.model._
import doobie._
import doobie.implicits._
import doobie.util.ExecutionContexts
import cats._
import cats.data._
import cats.effect._
import cats.implicits._
import fs2.Stream


object ShopRepositoryImp extends ShopRepository{

  override def findShopTypeByName(xa: Transactor[IO], name: String): IO[Option[ShopType]]= {
    sql"""select id, name
          from shop_type
          where name = $name
       """.query[ShopType]
          .option
          .transact(xa)
  }
}