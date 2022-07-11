package com.example.SecuredMvcWeb.Response;

import java.util.List;

import lombok.Data;

@Data
public class DataWrapperForBookByAuthur<T> {
	private List<T> bookByAuthur;

}
