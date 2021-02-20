package schedulingjob;

import java.util.Collections;
import java.util.List;

public class Agendador {
	
	private final Agendamento agendamento;

	public Agendador(final Agendamento agendamento) {
		this.agendamento = agendamento;
	}

	public List<Long[]> getOrdensDeExecucao() {
		return Collections.emptyList();
	}

}
