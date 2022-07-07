package com.example.SecuredMvcWeb.Response;

import lombok.Data;

@Data
public class GraphQLResponseWithBook<T> {
	private DataWrapperForBook<T> data;
}
