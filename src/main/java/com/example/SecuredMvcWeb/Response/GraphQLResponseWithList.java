package com.example.SecuredMvcWeb.Response;

import lombok.Data;

@Data
public class GraphQLResponseWithList<T> {
	private DataWrapperForList<T> data;

}
