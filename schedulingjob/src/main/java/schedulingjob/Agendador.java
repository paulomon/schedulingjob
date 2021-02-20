package schedulingjob;

import java.util.Collections;
import java.util.List;

public class Agendador {
	
	private final JanelaExecucao agendamento;

	public Agendador(final JanelaExecucao agendamento) {
		this.agendamento = agendamento;
	}

	public List<Long[]> getOrdensDeExecucao() {
		return Collections.emptyList();
	}

}
