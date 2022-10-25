package com.rarible.tzkt.model

import com.rarible.core.client.WebClientResponseProxyException
import org.springframework.web.reactive.function.client.WebClientResponseException

class TzktBadRequest(ex: WebClientResponseException) : WebClientResponseProxyException(ex)
