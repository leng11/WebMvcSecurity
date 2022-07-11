package com.example.SecuredMvcWeb.Response;

import lombok.Data;

@Data
public class GraphQLResponseWithBookByAuthor<T> {
	DataWrapperForBookByAuthur<T> data;

}
