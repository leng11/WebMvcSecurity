package com.example.SecuredMvcWeb.Response;

import java.util.List;

import lombok.Data;

@Data
public class DataWrapperForList<T> {
	private List<T> list;

}
