package schedulingjob;

import static org.junit.Assert.assertTrue;
import static schedulingjob.Agendador.TEMPO_MAXIMO_EXECUCAO_POR_JOB;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.junit.Test;

public class AgendadorTest {
	
	@Test
	public void quandoNaoPossuirJobsParaAgendamentoDeveRetornarUmaListaVazia() {
		JanelaExecucao agendamento = new JanelaExecucao(
										LocalDateTime.now(), 
										LocalDateTime.now().plusDays(1), 
										Collections.<Job>emptyList()
									);
		
		Agendador agendador = new Agendador(agendamento);
		
		List<Long[]> ordensDeExecucao = agendador.getOrdensDeExecucao();
		assertTrue("Nao deveria ter ordens de execucao", ordensDeExecucao.isEmpty());
	}
	
	@Test(expected = TempoMaximoExecucaoPorJobExcedidoException.class)
	public void quandoTempoDeExecucaoDoJobForMaiorQueTempoLimitePorJob() {
		Job job = new Job
			.JobBuilder()
			.id(1)
			.tempoEstimado(TEMPO_MAXIMO_EXECUCAO_POR_JOB + 1)
			.build();
		
		JanelaExecucao agendamento = new JanelaExecucao(
				LocalDateTime.now(), 
				LocalDateTime.now().plusDays(1), 
				Arrays.asList(job)
			);

		Agendador agendador = new Agendador(agendamento);
		agendador.getOrdensDeExecucao();
	}
	
}
