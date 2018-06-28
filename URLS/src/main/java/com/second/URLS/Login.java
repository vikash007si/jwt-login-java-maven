package com.second.URLS;

import javax.ws.rs.Consumes;
import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.security.Key;
import java.util.Date;

import javax.ws.rs.core.UriInfo;

import com.URL.enties.UserDao;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.impl.crypto.MacProvider;

/**
 * Root resource (exposed at "myresource" path)
 */
@Path("Users")
public class Login {

	@GET
	@Produces(MediaType.TEXT_PLAIN)
	public String getIt() {
		return "Got it!";
	}

	@POST
	@Path("/register")
	@Consumes(MediaType.APPLICATION_JSON)
	public Response register(UserDO user) {

		// user.setUserId(email);
		UserDao dao = new UserDao();
		dao.addUser(user);

		return Response.ok().build();

	}

	@POST
	@Path("/login")
	@Produces(MediaType.TEXT_PLAIN)
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	public Response login(@FormParam("email") String email, @FormParam("password") String password,
			@Context UriInfo uriinfo) {
		UserDO user = new UserDO();
		try {
			auhenicae(email, password);
			String token = issueToken(email, uriinfo);

			// Return the token on the response
			return Response.ok().header(HttpHeaders.AUTHORIZATION, "Bearer " + token).build();

		} catch (Exception e) {
			return Response.status(401).build();
		}
	}

	private String issueToken(String login, UriInfo uriinfo) {
		// private KeyGenerator keyGenerator;
		Key key = MacProvider.generateKey();
		byte keyDaa[] = key.getEncoded();
		try (FileOutputStream fos = new FileOutputStream("D:\\FileKey.txt")) {
			fos.write(keyDaa);
			fos.close(); // There is no more need for this line since you had
							// created the instance of "fos" inside the try. And
							// this will automatically close the OutputStream
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		String jwtToken = Jwts.builder().setSubject(login).setIssuer(uriinfo.getAbsolutePath().toString())
				.setIssuedAt(new Date()).setExpiration(new Date(System.currentTimeMillis() + 10 * 60 * 1000))
				.signWith(SignatureAlgorithm.HS512, key).compact();
		return jwtToken;
	}

	@POST
	@Path("/addURL")
	@Produces(MediaType.TEXT_PLAIN)
	@JWTTokenNeeded
	public String addURLs() {
		return "You can add URLS";

	}

	private void auhenicae(String email, String password) throws Exception {
		UserDO user = new UserDO();

		user.setEmail(email);
		user.setPassword(password);
		// user.setUserId(email);
		UserDao dao = new UserDao();
		if (!(dao.readEmployee(email, password))) {
			throw new Exception();
		}

	}

}
