import org.springframework.web.reactive.function.client.ExchangeStrategies
import org.springframework.web.reactive.function.client.WebClient

fun preparedClient(base: String): WebClient {
    return WebClient.builder()
        .exchangeStrategies(
            ExchangeStrategies.builder()
                .codecs { it.defaultCodecs().maxInMemorySize(10_000_000) }
                .build())
        .baseUrl(base)
        .build()
}
