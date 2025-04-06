package dev.sgd.currencymate.domain.error;

import lombok.Getter;

@Getter
public enum ErrorEnum {

    /* Common */
    UNKNOWN_ERROR(
            """
            😵 Something went wrong on our side while processing your request

            Don't worry — it's not your fault
            Please try again in a few moments 🙏
            """
    ),
    REQUEST_VALIDATION_ERROR(
            """
            🤨 Oops! Looks like some of your request parameters are invalid

            Please double-check the API documentation and make sure all required fields are correct
            We believe in you! 💪
            """
    ),
    ADAPTER_ERROR(
            """
            🔌 Failed to retrieve data from one of our external providers

            This usually happens when the upstream service is down or slow
            Please try again later — we’re keeping an eye on it 👀
            """
    ),

    /* More specific */
    FIND_EXCHANGE_RATE_PROVIDER_ERROR(
            """
            😟 We don't yet have a suitable provider for the requested currencies

            Try using different currency pairs (like BTC → USD or USD → CNY)
            Our team is constantly learning and improving! 🤖✨
            """
    );


    private final String message;
    private final String code;

    ErrorEnum(String message) {
        this.message = message;
        this.code = this.name();
    }

}