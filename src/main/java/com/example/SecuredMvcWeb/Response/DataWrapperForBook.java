package com.example.SecuredMvcWeb.Response;

import lombok.Data;

@Data
public class DataWrapperForBook<T> {
	private T book;
}