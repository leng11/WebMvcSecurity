package com.example.SecuredMvcWeb;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClient;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClientService;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.client.RestTemplate;

import com.example.SecuredMvcWeb.Response.GraphQLResponseWithBook;
import com.example.SecuredMvcWeb.Response.GraphQLResponseWithList;

import lombok.extern.slf4j.Slf4j;

@Controller
@Slf4j
public class BookMvcController {
	@Autowired
	OAuth2AuthorizedClientService oauthClientService;
	
	@Autowired
	RestTemplate restTemplate;
	
	@Value("${spring.book.service.url}")
	private String bookServiceUrl;
	
//	static class GraphQLResponseWithList {
//		class Data {
//			List<Book> list;
//		
//			public List<Book> getResult() {
//				return list;
//			}
//			
//			public void setResult(List<Book> list) {
//				this.list = list;
//			}
//		}
//		Data data;
//		
//		public void setData(Data data) {
//			this.data = data;
//		}
//		
//		public Data getData() {
//			return data;
//		}
//	}
//	
//	static class GraphQLResponseWithInstance {
//		static class Data {
//			Book book;
//		
//			public Book getResult() {
//				return book;
//			}
//			
//			public void setResult(Book book) {
//				this.book = book;
//			}
//		}
//		Data data;
//		
//		public void setData(Data data) {
//			this.data = data;
//		}
//		
//		public Data getData() {
//			return data;
//		}
//	}
	
	private final static String GRAPHQL_QUERY_LIST = "{"
														+ '\n' + " list {"
														+ '\n' + "     id"
														+ '\n' + "     name"
														+ '\n' + "     author"
														+ '\n' + "      }"
														+ '\n' + "}";
	private final static String GRAPHQL_QUERY_BOOKS = "query { books(bookNameFilter: __FILTER__ ) { id name author } }";
	private final static String GRAPHQL_QUERY_BOOK_BY_ID = "query { book(id: __ID__) { id name author } }";
	private final static String GRAPHQL_QUERY_BOOK_BY_AUTHOR = "query { bookByAuthor(author:\"__AUTHOR__\") { id name author } }";

	@GetMapping("/list")
	public String list(Model model) {
		HttpEntity<String> entity = new HttpEntity<>(GRAPHQL_QUERY_LIST, createSecuredHttpHeader());
		ResponseEntity<GraphQLResponseWithList<Book>> response = restTemplate.exchange(bookServiceUrl,
																		HttpMethod.POST,
																		entity,
																		new ParameterizedTypeReference<GraphQLResponseWithList<Book>>() {});
		log.info("data: " + response.getBody().getData().toString());
		log.info("result: " + response.getBody().getData().getList());
		model.addAttribute("books", response.getBody().getData().getList());
		return "displayBooks";
	}
	
	@GetMapping("/books/{filter}")
	public String getBooks(@PathVariable("filter") String filter, Model model) {
		HttpEntity<String> entity = new HttpEntity<>(GRAPHQL_QUERY_BOOKS.replace("__ID__", "\"" + filter + "\""),
																								createSecuredHttpHeader());
		ResponseEntity<GraphQLResponseWithList<Book>> response = restTemplate.exchange(bookServiceUrl,
																		HttpMethod.POST,
																		entity,
																		new ParameterizedTypeReference<GraphQLResponseWithList<Book>>() {});
		model.addAttribute("books", response.getBody().getData().getList());
		return "displayBooks";
	}
	
	@GetMapping("/getBookById/{id}")
	public String getBookById(@PathVariable("id") int id, Model model) {
		HttpEntity<String> entity = new HttpEntity<>(GRAPHQL_QUERY_BOOK_BY_ID.replace("__ID__", String.valueOf(id)),
																									createSecuredHttpHeader());
		ResponseEntity<GraphQLResponseWithBook<Book>> response = restTemplate.exchange(bookServiceUrl,
																		HttpMethod.POST,
																		entity,
																		new ParameterizedTypeReference<GraphQLResponseWithBook<Book>>() {});
		
//		ResponseEntity<String> response = restTemplate.exchange(bookServiceUrl,
//				HttpMethod.POST,
//				entity,
//				new ParameterizedTypeReference<String>() {});
				
				
		List<Book> books = new ArrayList<>();
		books.add(response.getBody().getData().getBook());
		model.addAttribute("books", books);
		return "displayBooks";
	}
	
	@GetMapping("/getBookByAuthor/{author}")
	public String getBookById(@PathVariable("author") String author, Model model) {
		HttpEntity<String> entity = new HttpEntity<>(GRAPHQL_QUERY_BOOK_BY_AUTHOR.replace("__AUTHOR__", author),
																							createSecuredHttpHeader());
		ResponseEntity<GraphQLResponseWithList<Book>> response = restTemplate.exchange(bookServiceUrl,
																		HttpMethod.POST,
																		entity,
																		new ParameterizedTypeReference<GraphQLResponseWithList<Book>>() {});
		model.addAttribute("books", response.getBody().getData().getList());
		
		return "displayBooks";
	}

	private HttpHeaders createSecuredHttpHeader() {
		OAuth2AuthenticationToken oauthToken = (OAuth2AuthenticationToken)SecurityContextHolder.getContext().getAuthentication();
		OAuth2AuthorizedClient oauthClient = oauthClientService.loadAuthorizedClient(
																	oauthToken.getAuthorizedClientRegistrationId(),
																	oauthToken.getName());
		
		String jwtAccessToken = oauthClient.getAccessToken().getTokenValue();
		log.info("jwt token: " + jwtAccessToken);
		
		HttpHeaders headers = new HttpHeaders();
		headers.add("Authorization", "Bearer " + jwtAccessToken);
		headers.add("Content-Type", "application/graphql");
		headers.add("Accept", "*/*");
		headers.add("Accept-Encoding", "gzip, deflate, br");
		return headers;
	}
	


// WebClient vs RestTemplate
//	@Autowired
//	OAuth2AuthorizedClientService oauth2ClientService;
//	
////	@Autowired
////	RestTemplate restTemplate;
//
//	@Autowired
//	WebClient webClient;
//	
//	
//	@GetMapping("/albums")
//	public String getAlbums(Model model, 
//			@AuthenticationPrincipal OidcUser principal) {
//
//		
//		String url = "http://localhost:8082/albums";
//
//		List<AlbumRest> albums = webClient.get()
//				.uri(url)
//				.retrieve()
//				.bodyToMono(new ParameterizedTypeReference<List<AlbumRest>>(){})
//				.block();
//	
//        model.addAttribute("albums", albums);
//		
//		
//		return "albums";
//	}
}
