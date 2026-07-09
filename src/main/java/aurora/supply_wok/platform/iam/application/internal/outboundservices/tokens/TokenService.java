package aurora.supply_wok.platform.iam.application.internal.outboundservices.tokens;

/**
 * Outbound port for bearer token issuance and validation used by IAM commands and queries.
 */
public interface TokenService {

    /**
     * Generates a token for an email principal.
     *
     * @param email principal email
     * @return signed token value
     */
    String generateToken(String email);

    /**
     * Extracts the email from a token.
     *
     * @param token token value
     * @return email embedded in the token
     */
    String getEmailFromToken(String token);

    /**
     * Validates a token.
     *
     * @param token token value
     * @return {@code true} when token is valid; otherwise {@code false}
     */
    boolean validateToken(String token);
}
