package com.utcl.auth.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.utcl.auth.dto.AccessTokenResponse;
import com.utcl.auth.dto.AuthRequest;
import com.utcl.auth.dto.JwtResponse;
import com.utcl.auth.dto.Product;
import com.utcl.auth.dto.RefreshTokenRequest;
import com.utcl.auth.dto.RefreshTokenResponse;
import com.utcl.auth.dto.SuccesFailResponse;
import com.utcl.auth.entity.RefreshToken;
import com.utcl.auth.entity.UserInfo;
import com.utcl.auth.service.JwtService;
import com.utcl.auth.service.ProductService;
import com.utcl.auth.service.RefreshTokenService;

@RestController
@RequestMapping("/user")
@CrossOrigin(origins = "http://localhost:4200")
public class AuthController {

	//@Autowired
	private ProductService service;
	//@Autowired
	private JwtService jwtService;

	//@Autowired
	private RefreshTokenService refreshTokenService;

	//@Autowired
	private AuthenticationManager authenticationManager;
	
	

	public AuthController(ProductService service, JwtService jwtService, RefreshTokenService refreshTokenService,
			AuthenticationManager authenticationManager) {
		super();
		this.service = service;
		this.jwtService = jwtService;
		this.refreshTokenService = refreshTokenService;
		this.authenticationManager = authenticationManager;
	}

	@Value("${jwt.invalid_acc_token}")
	public String invalidAccToken;
	
	@Value("${jwt.invalid_ref_token}")
	public String invalidRefToken;
	
	@Value("${jwt.valid_acc_token}")
	public String validAccToken;
	
	
	@PostMapping("/signUp")
	public String addNewUser(@RequestBody UserInfo userInfo) {
		return service.addUser(userInfo);
	}

	@GetMapping("/all")
	@PreAuthorize("hasAuthority('ROLE_ADMIN')")
	public List<Product> getAllTheProducts() {
		return service.getProducts();
	}

	@GetMapping("/{id}")
	@PreAuthorize("hasAuthority('ROLE_USER')")
	public Product getProductById(@PathVariable int id) {
		return service.getProduct(id);
	}

	//

	@PostMapping("/login")
	public JwtResponse authenticateAndGetToken(@RequestBody AuthRequest authRequest) {
		Authentication authentication = authenticationManager.authenticate(
				new UsernamePasswordAuthenticationToken(authRequest.getUsername(), authRequest.getPassword()));
		if (authentication.isAuthenticated()) {

			RefreshToken refreshToken = refreshTokenService.createRefreshToken(authRequest.getUsername());
			return JwtResponse.builder().message("Logged in successfully.").code(validAccToken).accessToken(jwtService.generateToken(authRequest.getUsername()))
					.refreshToken(refreshToken.getToken()).build();
		} else {
			throw new UsernameNotFoundException("invalid user request !");
		}
	}

	@PostMapping("/refreshToken")
	public Object refreshToken(@RequestBody RefreshTokenRequest refreshTokenRequest) {
		try {
			return	refreshTokenService.findByToken(refreshTokenRequest.getToken())
			.map(refreshTokenService::verifyExpiration).map(RefreshToken::getUserId).map(userId -> {
				String accessToken = jwtService.generateToken(userId);
				return JwtResponse.builder().accessToken(accessToken).code(validAccToken).refreshToken(refreshTokenRequest.getToken())
						.message("Logged in succesfully. Please use this acces token and refresh token.").build();
			}).orElseThrow(() -> new RuntimeException("Refresh token is expired. Please login again!"));
			
		} catch (Exception e) {
			return SuccesFailResponse.builder().code(invalidRefToken).message("INVALID_REFRESH_TOKEN").detailedMessage(e.getMessage()).build();
		}
	}

	// need to add to permit all in security config
	@GetMapping("/generate_refresh_token/{userId}")
	public RefreshTokenResponse generateRefreshToken(@PathVariable String userId) {
		RefreshToken refreshToken = refreshTokenService.createRefreshToken(userId);
		return RefreshTokenResponse.builder().refreshToken(refreshToken.getToken()).message("This is RefreshToken").build();
	}

	// need to add to permit all in security config
	@GetMapping("/generate_access_token/{userId}")
	public AccessTokenResponse generateAccessToken(@PathVariable String userId) {
		String accessToken = jwtService.generateToken(userId);
		return AccessTokenResponse.builder().accessToken(accessToken).message("This is AccessToken").build();
		
	}

	// need to add to permit all in security config //
	@GetMapping("/is_valid_token/{accessToken}")
	public SuccesFailResponse isValidToken(@PathVariable String accessToken) {
		try {
			if(!jwtService.isInvalid(accessToken)) {
				return SuccesFailResponse.builder().code(validAccToken).message("VALID_TOKEN").detailedMessage("TOKEN IS VALID").build();
			}else {
				return SuccesFailResponse.builder().code(invalidAccToken).message("INVALID_TOKEN").detailedMessage("Token is expired").build();
				
			}
		} catch (Exception e) {
			e.printStackTrace();
			return SuccesFailResponse.builder().code(invalidAccToken).message("INVALID_TOKEN").detailedMessage("Token is expired").build();
		
		}
	}
}
