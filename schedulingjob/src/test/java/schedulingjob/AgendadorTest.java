package schedulingjob;

import static org.junit.Assert.assertTrue;

import java.time.LocalDateTime;
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
	
}
