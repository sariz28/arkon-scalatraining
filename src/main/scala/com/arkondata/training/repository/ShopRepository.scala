package com.arkondata.training.repository

import com.arkondata.training.model._

import doobie._
import doobie.implicits._
import doobie.util.ExecutionContexts
import cats._
import cats.data._
import cats.effect._
import cats.implicits._


trait ShopRepository{
  def findShopTypeByName(xa: Transactor[IO], name: String): IO[Option[ShopType]]
}