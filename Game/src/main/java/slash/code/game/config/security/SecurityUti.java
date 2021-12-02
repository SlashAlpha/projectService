package slash.code.game.config.security;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.JsonElement;
import com.google.gson.JsonIOException;
import com.google.gson.JsonParseException;
import com.google.gson.JsonSyntaxException;
import com.google.gson.internal.Streams;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonToken;
import com.google.gson.stream.MalformedJsonException;
import lombok.experimental.UtilityClass;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import slash.code.game.user.Role;
import slash.code.game.user.TokenDto;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.Reader;
import java.io.StringReader;
import java.util.*;
import java.util.stream.Collectors;

import static java.util.Arrays.stream;
import static org.springframework.http.HttpStatus.FORBIDDEN;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@Slf4j
@UtilityClass
public class SecurityUti {

    private static final String SALT_POKER = "SLASH-POKER-GLOVE-BOX";

    TokenDto tokenFromDealer = new TokenDto();

    public static TokenDto getTokenFromDealer() {
        return tokenFromDealer;
    }

    public static void setTokenFromDealer(TokenDto tokenFromDealer) {
        SecurityUti.tokenFromDealer = tokenFromDealer;
    }

    public String tokenUtiJWT(String userEmail, Collection<GrantedAuthority> userRole, Collection<Role> roles, Integer tokenAvailability) {

        Algorithm algorithm = Algorithm.HMAC256(SALT_POKER.getBytes());
        String token = JWT.create()
                .withSubject(userEmail)
                .withExpiresAt(new Date(System.currentTimeMillis() + tokenAvailability))
                .withIssuer("Slash")
                .withClaim("roles", !(userRole == null) ? userRole.stream().map(GrantedAuthority::getAuthority).collect(Collectors.toList())
                        : roles.stream().map(Role::getName).collect(Collectors.toList()))
                .sign(algorithm);
        return token;
    }

    public void authorizationUtiJWT(String authorizationHeader) {

        DecodedJWT decodedJWT = decodeJwt(authorizationHeader);
        String email = decodedJWT.getSubject();
        String[] roles = decodedJWT.getClaim("roles").asArray(String.class);
        Collection<SimpleGrantedAuthority> authorities = new ArrayList<>();
        stream(roles).forEach(role -> {
            authorities.add(new SimpleGrantedAuthority(role));

        });
        UsernamePasswordAuthenticationToken authenticationToken =
                new UsernamePasswordAuthenticationToken(email, null, authorities);
        SecurityContextHolder.getContext().setAuthentication(authenticationToken);
    }

    public void except(Exception e, HttpServletResponse response) throws IOException {
        log.error("error logging in : {}", e.getMessage());
        response.setHeader("error", e.getMessage());
        response.setStatus(FORBIDDEN.value());
        Map<String, String> error = new HashMap<>();
        error.put("error message", e.getMessage());
        response.setContentType(APPLICATION_JSON_VALUE);
        new ObjectMapper().writeValue(response.getOutputStream(), error);

    }


    public DecodedJWT decodeJwt(String authorizationHeader) {
        String token = authorizationHeader.substring("Bearer ".length());
        Algorithm algorithm = Algorithm.HMAC256(SALT_POKER.getBytes());
        JWTVerifier verifier = JWT.require(algorithm).build();
        DecodedJWT decodedJWT = verifier.verify(token);
        return decodedJWT;
    }

    public Map<String, String> responseToken(HttpServletResponse response, String accessToken, String refreshToken) {
        Map<String, String> tokens = new HashMap<>();
        tokens.put("accessToken", accessToken);
        tokens.put("refreshToken", refreshToken);
        response.setContentType(APPLICATION_JSON_VALUE);
        return tokens;
    }

    public String[] apiUser(int choice) {
        String[] email = {"joshmartini@gmail.com", "juliabeer@outlook.com", "dinavodka@yahoo.com", "robertsinaloa@aol.com",
                "sophiaCarlyle@google.fr", "tobbypartypooper@smellmouth.com", "ratkevin@bossrating.com", "accountingtina@waytogoup.com", "jimmyObryan@novel&wine.com"};
        String[] password = {"érmlgkd", "rdmlkpskfd", "smlkfsmel", "peoifpoq", "emlfkmfl", "zepofezpofk", "zemflkezmf", "rorkmqrlkgr", "fqmlfqmfqemff"};
        if (choice == 1) {
            return email;
        } else if (choice == 2) {
            return password;
        }
        return null;
    }


    public HttpEntity restEntityTokenedHeaders(TokenDto tokens) {
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.set("Authorization", "Bearer " + tokens.getAccessToken());
        HttpEntity entity = new HttpEntity(httpHeaders);
        return entity;
    }

    public static JsonElement parseString(String json) throws JsonSyntaxException {
        return parseReader(new StringReader(json));
    }

    public static JsonElement parseReader(Reader reader) throws JsonIOException, JsonSyntaxException {
        try {
            JsonReader jsonReader = new JsonReader(reader);
            JsonElement element = parseReader(jsonReader);
            if (!element.isJsonNull() && jsonReader.peek() != JsonToken.END_DOCUMENT) {
                throw new JsonSyntaxException("Did not consume the entire document.");
            }
            return element;
        } catch (MalformedJsonException e) {
            throw new JsonSyntaxException(e);
        } catch (IOException e) {
            throw new JsonIOException(e);
        } catch (NumberFormatException e) {
            throw new JsonSyntaxException(e);
        }
    }


    public static JsonElement parseReader(JsonReader reader) throws JsonIOException, JsonSyntaxException {
        boolean lenient = reader.isLenient();
        reader.setLenient(true);
        try {
            return Streams.parse(reader);
        } catch (StackOverflowError e) {
            throw new JsonParseException("Failed parsing JSON source: " + reader + " to Json", e);
        } catch (OutOfMemoryError e) {
            throw new JsonParseException("Failed parsing JSON source: " + reader + " to Json", e);
        } finally {
            reader.setLenient(lenient);
        }
    }

    public JsonElement parse(String json) throws JsonSyntaxException {
        return parseString(json);
    }
}
