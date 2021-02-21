package schedulingjob;

import java.util.Collections;
import java.util.List;

public class Agendador {
	
	public static final long TEMPO_MAXIMO_EXECUCAO_POR_JOB = 8;
	
	private final JanelaExecucao agendamento;

	public Agendador(final JanelaExecucao agendamento) {
		this.agendamento = agendamento;
	}

	public List<Long[]> getOrdensDeExecucao() {
		if(!agendamento.possuiJobs()) {
			return Collections.emptyList();
		}
		
		return calcularOrdens();
	}

	private List<Long[]> calcularOrdens() {
		for(Job job : agendamento.getJobs()) {
			validarTempoMaximoExecucaoDoJob(job);
		}
		
		return null;
	}

	private void validarTempoMaximoExecucaoDoJob(Job job) {
		if(!job.tempoEstimadoDentroDoLimite(TEMPO_MAXIMO_EXECUCAO_POR_JOB)) {
			throw new TempoMaximoExecucaoPorJobExcedidoException();
		}
	}

}
