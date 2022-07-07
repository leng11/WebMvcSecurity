package com.example.SecuredMvcWeb;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@AllArgsConstructor
@Builder
public class Book {
	private long id;
	private String name;
	private String author;
	
	public Book() {
	}

}
