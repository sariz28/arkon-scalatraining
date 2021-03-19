package com.arkondata.training

import com.arkondata.training.model._
import com.arkondata.training.repository._
import doobie._
import doobie.implicits._
import doobie.util.ExecutionContexts
import cats._
import cats.data._
import cats.effect._
import cats.implicits._
import fs2.Stream

object Main extends IOApp {

  val xa = PostgresConnection.createConnection("shopsapi", "sa", "1234")
  val shopRep = ShopRepositoryImp.findShopTypeByName(xa, "shop type 1")
  shopRep.unsafeRunSync()
         .foreach(println)

}

