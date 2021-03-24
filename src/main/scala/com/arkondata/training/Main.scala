package com.arkondata.training

import com.arkondata.training.grahql.{GraphQL, GraphQLProvider, GraphQLRoutes}
import com.arkondata.training.repository.{PostgresConnection, ShopRepository}
import com.arkondata.training.schema.QueryType
import cats.effect._
import cats.implicits._
import doobie._
import io.chrisdavenport.log4cats.Logger
import io.chrisdavenport.log4cats.slf4j.Slf4jLogger
import _root_.sangria.schema._
import org.http4s._
import org.http4s.implicits._
import org.http4s.server.Server
import org.http4s.server.blaze._

import scala.concurrent.ExecutionContext
import scala.concurrent.ExecutionContext.global




object Main extends IOApp {

  def graphQL[F[_]: Effect: ContextShift: Logger](
                                                   transactor: Transactor[F],
                                                   blockingContext: ExecutionContext
                                                 ): GraphQL[F] =
    GraphQLProvider[F](
      Schema(
        query    = QueryType[F]
      ),
      ShopRepository.fromTransactor(transactor).pure[F],
      blockingContext
    )

  // Resource that mounts the given `routes` and starts a server.
  def server[F[_]: ConcurrentEffect: ContextShift: Timer](
                                                           routes: HttpRoutes[F]
                                                         ): Resource[F, Server[F]] =
    BlazeServerBuilder[F](global)
      .bindHttp(8080, "localhost")
      .withHttpApp(routes.orNotFound)
      .resource

  // Resource that constructs our final server.
  def resource[F[_]: ConcurrentEffect: ContextShift: Timer](
                                                             implicit L: Logger[F]
                                                           ): Resource[F, Server[F]] =
    for {
      b   <- Blocker[F]
      xa  <- PostgresConnection.transactor[F](b)
      gql  = graphQL[F](xa, b.blockingContext)
      rts  = GraphQLRoutes[F](gql)
      svr <- server[F](rts)
    } yield svr

  // Our entry point starts the server and blocks forever.
  def run(args: List[String]): IO[ExitCode] = {
    implicit val log = Slf4jLogger.getLogger[IO]
    resource[IO].use(_ => IO.never.as(ExitCode.Success))
  }



}

