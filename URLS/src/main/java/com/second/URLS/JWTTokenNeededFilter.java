package com.second.URLS;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.security.Key;

import javax.annotation.Priority;
import javax.crypto.spec.SecretKeySpec;
import javax.ws.rs.Priorities;
import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerRequestFilter;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.Provider;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

@Provider
@JWTTokenNeeded
@Priority(Priorities.AUTHENTICATION)
public class JWTTokenNeededFilter implements ContainerRequestFilter {

	@Override
	public void filter(ContainerRequestContext requestContext) throws IOException {

		// Get the HTTP Authorization header from the request
		String authorizationHeader = requestContext.getHeaderString(HttpHeaders.AUTHORIZATION);

		// Extract the token from the HTTP Authorization header
		String token = authorizationHeader.substring("Bearer".length());

		try {

			// Validate the token
			// Key key = MacProvider.generateKey();
			Path path = Paths.get("D:\\FileKey.txt");
			byte[] data = Files.readAllBytes(path);
			Key key = new SecretKeySpec(data, SignatureAlgorithm.HS512.getJcaName());
			Jwts.parser().setSigningKey(key).parseClaimsJws(token);
			// assert
			// Jwts.parser().setSigningKey(key).parseClaimsJws(token).getBody().getSubject().equals("jyot1@viks.com");
			System.out.println("valid oken +" + token);

		} catch (Exception e) {
			System.out.println("Invalid oken +" + token);
			requestContext.abortWith(Response.status(Response.Status.UNAUTHORIZED).build());
		}
	}
}