package com.thomasvitale.keycloak.controller;

import com.auth0.jwt.JWT;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.thomasvitale.keycloak.repository.BookRepository;
import org.keycloak.KeycloakSecurityContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.swing.*;

@Controller
public class LibraryController {
	private final HttpServletRequest request;
	private final BookRepository bookRepository;

	@Autowired
	public LibraryController(HttpServletRequest request, BookRepository bookRepository) {
		this.request = request;
		this.bookRepository = bookRepository;
	}

	@GetMapping(value = "/")
	public String getHome() {
		return "index";
	}

	@GetMapping(value = "/books")
	public String getBooks(Model model) {
		//configCommonAttributes(model);
		model.addAttribute("books", bookRepository.readAll());
		return "books";
	}

	@GetMapping(value = "/manager")
	public String getManager(Model model) {
		//configCommonAttributes(model);
		model.addAttribute("books", bookRepository.readAll());
		return "manager";
	}

	@GetMapping(value = "/realms/rtl/login-actions/action-token")
	public String loginEmailVerify(@RequestParam String key, Model model) {

		try {
			DecodedJWT jwt = JWT.decode(key);

			String out = "";
			String subj;
			subj = "Subject:    " + jwt.getSubject();
			out += subj;  out += "\n";
			model.addAttribute("userid", jwt.getSubject());
			model.addAttribute("books", bookRepository.readAll());
			return "verify";
		} catch (Exception ex){
			//Invalid token

		}
		return null;
	}

	@GetMapping(value = "/logout")
	public String logout() throws ServletException {
		request.logout();
		return "redirect:/";
	}

	private void configCommonAttributes(Model model) {
		model.addAttribute("name", getKeycloakSecurityContext().getIdToken().getGivenName());
	}

	/**
	 * The KeycloakSecurityContext provides access to several pieces of information
	 * contained in the security token, such as user profile information.
	 */
	private KeycloakSecurityContext getKeycloakSecurityContext() {
		return (KeycloakSecurityContext) request.getAttribute(KeycloakSecurityContext.class.getName());
	}
}
