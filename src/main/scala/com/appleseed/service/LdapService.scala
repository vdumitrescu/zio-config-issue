package com.appleseed.service

import com.appleseed.config.LdapConfig
import zio.*

trait LdapService:
  def readAttributes(user: String): Task[Boolean]

object LdapService:
  def readAttributes(user: String): RIO[LdapService, Boolean] =
    ZIO.serviceWithZIO[LdapService](_.readAttributes(user))

case class LdapServiceImpl(config: LdapConfig) extends LdapService:
  def readAttributes(user: String): Task[Boolean] =
    ZIO.log(s"Reading attributes for user $user using ${config.user}@${config.url}").as(true)

object LdapServiceImpl:
  val live: RLayer[LdapConfig, LdapService] = ZLayer.fromFunction(LdapServiceImpl.apply)
