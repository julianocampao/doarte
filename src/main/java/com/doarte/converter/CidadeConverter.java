package com.doarte.converter;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.doarte.model.Cidade;

@Service
public class CidadeConverter implements Converter<String, Cidade> {

	@Override
	public Cidade convert(String codigo) {

		if (!StringUtils.isEmpty(codigo)) {
			Cidade go = new Cidade();
			go.setId(Long.valueOf(codigo));
			return go;
		}

		return null;
	}

}
