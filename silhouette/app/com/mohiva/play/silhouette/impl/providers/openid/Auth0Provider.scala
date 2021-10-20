/**
 * Copyright 2015 Mohiva Organisation (license at mohiva dot com)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.mohiva.play.silhouette.impl.providers.openid

import com.mohiva.play.silhouette.api.{Logger, LoginInfo}
import com.mohiva.play.silhouette.api.util.HTTPLayer
import com.mohiva.play.silhouette.impl.providers._

import scala.concurrent.Future

/**
 * Base Auth0 OpenID Provider.
 *
 */
trait BaseAuth0Provider extends OpenIDProvider {

  /**
   * The content type to parse a profile from.
   */
  override type Content = Unit

  /**
   * Gets the provider ID.
   *
   * @return The provider ID.
   */
  override val id: String = Auth0Provider.ID

  /**
   * Defines the URLs that are needed to retrieve the profile data.
   */
  override protected val urls: Map[String, String] = Map.empty[String, String]

  /**
   * Builds the social profile.
   *
   * @param authInfo The auth info received from the provider.
   * @return On success the build social profile, otherwise a failure.
   */
  override protected def buildProfile(authInfo: OpenIDInfo): Future[Profile] = {
    profileParser.parse((), authInfo)
  }
}

/**
 * The profile parser for the common social profile.
 */
class Auth0ProfileParser extends SocialProfileParser[Unit, CommonSocialProfile, OpenIDInfo] with Logger {

  /**
   * Parses the social profile.
   *
   * @param authInfo The auth info received from the provider.
   * @return The social profile from given result.
   */
  override def parse(data: Unit, authInfo: OpenIDInfo): Future[CommonSocialProfile] =
    Future.successful {     // HDN: Check this
      logger.debug( s"Parsing $authInfo" )
      CommonSocialProfile(
        loginInfo = LoginInfo(Auth0Provider.ID, authInfo.id),
        fullName  = authInfo.attributes.get("fullname"),
        email     = authInfo.attributes.get("email"),
        avatarURL = authInfo.attributes.get("image")          // may be "picture"
      )
    }
}

/**
 * The Auth0 OAuth2 Provider.
 *
 * @param httpLayer The HTTP layer implementation.
 * @param service The OpenID service implementation.
 * @param settings The OpenID provider settings.
 */
class Auth0Provider( protected val httpLayer: HTTPLayer,
                               val service:   OpenIDService,
                               val settings:  OpenIDSettings )
  extends BaseAuth0Provider with CommonSocialProfileBuilder {

  /**
   * The type of this class.
   */
  override type Self = Auth0Provider

  /**
   * The profile parser implementation.
   */
  override val profileParser = new Auth0ProfileParser

  /**
   * Gets a provider initialized with a new settings object.
   *
   * @param f A function which gets the settings passed and returns different settings.
   * @return An instance of the provider initialized with new settings.
   */
  override def withSettings(f: (Settings) => Settings): Auth0Provider = {
    new Auth0Provider(httpLayer, service.withSettings(f), f(settings))
  }
}

/**
 * The companion object.
 */
object Auth0Provider {

  /**
   * The Auth0 constants.
   */
  val ID: String = "auth0"
}
