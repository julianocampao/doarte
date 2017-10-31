package com.doarte.converter;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.doarte.model.Categoria;

@Service
public class CategoriaConverter implements Converter<String, Categoria> {

	@Override
	public Categoria convert(String codigo) {

		if (!StringUtils.isEmpty(codigo)) {
			Categoria go = new Categoria();
			go.setId(Long.valueOf(codigo));
			return go;
		}

		return null;
	}

}
