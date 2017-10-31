package com.doarte.model;

public enum StatusItem {

	DOADO("Doado"), NECESSIDADE("Necessidade"), PENDENTE("Pendente"),CANCELADO("Cancelado");

	private String descricao;

	StatusItem(String descricao) {
		this.descricao = descricao;
	}

	public String getDescricao() {
		return descricao;
	}

}
