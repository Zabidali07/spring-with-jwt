package com.zabid.threadhouse.security.utils;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Service;

import com.zabid.threadhouse.exception.UserAuthenticationException;
import com.zabid.threadhouse.pojo.UserDTO;
import com.zabid.threadhouse.services.UserServices;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

@Service
public class JwtTokenUtils {
	
	@Autowired
	private UserServices userService;

	private static final String ROLES_KEY = "roles";

	private final String jwtSecret;

	private final long tokenValidityDuration;

	@Autowired
	public JwtTokenUtils(@Value("${jwt.secret}") String secret,
			@Value("${jwt.tokenValidityInMiliSeconds}") long tokenValidity) {
		this.jwtSecret = secret;
		this.tokenValidityDuration = 1000 * tokenValidity;
	}

	/*
	 * Create a token how claims is used -- claims?? jwts.builder() is used to
	 * generate a token subject – which is a value that identifies the principal
	 * user. This value can be a user ID or userName for which this JWT was
	 * generated, expDate – this is the value when the JWT should expire, secret –
	 * this value is a secret key which is usually a unique alpha-numeric String of
	 * characters and should be kept private. It is this secret key with which the
	 * JWT will be signed using one of the security algorithms like for example HMAC
	 * SHA256.
	 */
	public String createToken(String userName, List<String> roles) {
		Claims claims = Jwts.claims().setSubject(userName);
		claims.put(ROLES_KEY, roles);

		Date now = new Date();
		Date validTill = new Date(System.currentTimeMillis() + tokenValidityDuration);

		return Jwts.builder().setClaims(claims).setIssuedAt(now).setExpiration(validTill)
				.signWith(SignatureAlgorithm.HS256, jwtSecret).compact();

	}

	// Represents the token for an authentication request
	// Once the request has been authenticated, the <tt>Authentication</tt> will
	// usually be
	// * stored in a thread-local <tt>SecurityContext</tt> managed by the
	// * {@link SecurityContextHolder} by the authentication mechanism
	public Authentication getAuthentication(String token) {
		
		Claims claims = Jwts.parser().setSigningKey(jwtSecret).parseClaimsJws(token).getBody();
		UserDTO userDTO = userService.getUserDtoByUsername(claims.getSubject());
		List<GrantedAuthority> authorities = getGrantedAuthorities(userDTO.getRoles());
		return new UsernamePasswordAuthenticationToken(claims.getSubject(), token, authorities);
	}

	private List<GrantedAuthority> getGrantedAuthorities(List<String> roles) {
		List<GrantedAuthority> authorities = new ArrayList<>();
		roles.forEach(role -> authorities.add(new SimpleGrantedAuthority(role)));

		return authorities;

	}

	public boolean validateToken(String token) {
		try {
			Jws<Claims> claims = Jwts.parser().setSigningKey(jwtSecret).parseClaimsJws(token);
			if (claims.getBody().getExpiration().before(new Date())) {
				return false;
			}
			return true;

		} catch (JwtException | IllegalArgumentException e) {
			throw new UserAuthenticationException("Invalid or Expired JWT token"); // Add a global exception here

		}
	}
	
	public String resolveToken(HttpServletRequest request) {
		String bearerToken = request.getHeader("Authorization");
		if(bearerToken != null && bearerToken.startsWith("Bearer ")) {
			return bearerToken.substring(7, bearerToken.length());
		}
		return null;
	}

}
