package schedulingjob;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static schedulingjob.Agendador.TEMPO_MAXIMO_EXECUCAO_POR_JOB;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.junit.Ignore;
import org.junit.Test;

public class AgendadorTest {
	
	@Test
	public void quandoNaoPossuirJobsParaAgendamentoDeveRetornarUmaListaVazia() {
		JanelaExecucao janela = criarJanelaSemJobs();		
		Agendador agendador = new Agendador(janela);
		
		List<List<Long>> ordensDeExecucao = agendador.getOrdensDeExecucao();
		assertTrue("Nao deve ter ordens de execucao", ordensDeExecucao.isEmpty());
	}

	@Ignore
	private JanelaExecucao criarJanelaSemJobs() {
		JanelaExecucao agendamento = new JanelaExecucao(
										LocalDateTime.now(), 
										LocalDateTime.now().plusDays(1), 
										Collections.<Job>emptyList()
									);
		return agendamento;
	}
	
	@Test(expected = TempoMaximoExecucaoPorJobExcedidoException.class)
	public void quandoTempoDeExecucaoDoJobForMaiorQueTempoLimitePorJob() {
		JanelaExecucao janela = criarJanelaOndeJobPossuaTempoEstimadoMaiorQuePermitido();

		Agendador agendador = new Agendador(janela);
		agendador.getOrdensDeExecucao();
	}

	@Ignore
	private JanelaExecucao criarJanelaOndeJobPossuaTempoEstimadoMaiorQuePermitido() {
		long tempoEstimado = TEMPO_MAXIMO_EXECUCAO_POR_JOB + 1;
		
		Job job = new Job.JobBuilder()
						 .id(1)
						 .dataMaximaConclusao(LocalDateTime.of(2019, 11, 11, 12, 0))
						 .tempoEstimado(tempoEstimado)
						 .build();
		
		JanelaExecucao agendamento = new JanelaExecucao(
				LocalDateTime.of(2019, 11, 10, 9, 0), 
				LocalDateTime.of(2019, 11, 11, 12, 0),  
				Arrays.asList(job)
			);
		return agendamento;
	}
	
	@Test
	public void jobsComOrdemDeExeucaoIgualAOrdemDaDataDeConclusao() {
		JanelaExecucao janela = criarJanelaOndeJobsComOrdemDeExeucaoIgualAOrdemDaDataDeConclusao();
		Agendador agendador = new Agendador(janela);
		
		List<List<Long>> ordensCalculadas = agendador.getOrdensDeExecucao();
		
		List<Long> primeiroConjuntoEsperado = Arrays.asList(1l, 3l);
		List<Long> segundoConjutnoEsperado = Arrays.asList(2l);
		List<List<Long>> conjuntoGeralEsperado = Arrays.asList(primeiroConjuntoEsperado, segundoConjutnoEsperado);
		
		assertEquals(conjuntoGeralEsperado, ordensCalculadas);
	}

	@Ignore
	private JanelaExecucao criarJanelaOndeJobsComOrdemDeExeucaoIgualAOrdemDaDataDeConclusao() {
		Job job1 = new Job.JobBuilder().id(1).dataMaximaConclusao(LocalDateTime.of(2019, 11, 10, 12, 0)).tempoEstimado(2).build();	
		Job job2 = new Job.JobBuilder().id(2).dataMaximaConclusao(LocalDateTime.of(2019, 11, 11, 12, 0)).tempoEstimado(4).build();
		Job job3 = new Job.JobBuilder().id(3).dataMaximaConclusao(LocalDateTime.of(2019, 11, 11, 8, 0)).tempoEstimado(6).build();
		
		return new JanelaExecucao(
				LocalDateTime.of(2019, 11, 10, 9, 0), 
				LocalDateTime.of(2019, 11, 11, 12, 0), 
				Arrays.asList(job1, job2, job3)
			);
	}
	
	@Test(expected = TempoMaximoJanelaExcedidoException.class)
	public void jobsQueExcedemOTempoDaJanela() {
		JanelaExecucao janela = criaJanelaComJobsQueExcedemOTempoDaJanela();
		Agendador agendador = new Agendador(janela);
		agendador.getOrdensDeExecucao();
	}
	
	@Ignore
	private JanelaExecucao criaJanelaComJobsQueExcedemOTempoDaJanela() {
		Job job1 = new Job.JobBuilder().id(1).dataMaximaConclusao(LocalDateTime.of(2019, 11, 10, 12, 0)).tempoEstimado(5).build();
		Job job2 = new Job.JobBuilder().id(1).dataMaximaConclusao(LocalDateTime.of(2019, 11, 10, 15, 0)).tempoEstimado(7).build();
		
		return new JanelaExecucao(
				LocalDateTime.of(2019, 11, 10, 7, 0), 
				LocalDateTime.of(2019, 11, 10, 15, 0), 
				Arrays.asList(job1, job2)
			);
	}
	
	@Test
	public void jobsNaoPodemSerAgrupadosDevidoAoLimiteDeTempoDoAgrupamento() {
		JanelaExecucao janela = criarJanelaOndeJobsNaoPodemSerAgrupadosDevidoAoLimiteDeTempoDoAgrupamento();
		Agendador agendador = new Agendador(janela);
		
		List<List<Long>> ordensCalculadas = agendador.getOrdensDeExecucao();
		
		List<Long> primeiroConjuntoEsperado = Arrays.asList(1l);
		List<Long> segundoConjuntoEsperado = Arrays.asList(3l);
		List<Long> terceiroConjutoEsperado = Arrays.asList(2l);
		List<List<Long>> conjuntoGeralEsperado = Arrays.asList(primeiroConjuntoEsperado, segundoConjuntoEsperado, terceiroConjutoEsperado);
		
		assertEquals(conjuntoGeralEsperado, ordensCalculadas);
	}
	
	@Ignore
	private JanelaExecucao criarJanelaOndeJobsNaoPodemSerAgrupadosDevidoAoLimiteDeTempoDoAgrupamento() {
		Job job1 = new Job.JobBuilder().id(1).dataMaximaConclusao(LocalDateTime.of(2019, 11, 10, 17, 0)).tempoEstimado(TEMPO_MAXIMO_EXECUCAO_POR_JOB).build();	
		Job job2 = new Job.JobBuilder().id(2).dataMaximaConclusao(LocalDateTime.of(2019, 11, 11, 12, 0)).tempoEstimado(TEMPO_MAXIMO_EXECUCAO_POR_JOB).build();
		Job job3 = new Job.JobBuilder().id(3).dataMaximaConclusao(LocalDateTime.of(2019, 11, 11, 8, 0)).tempoEstimado(TEMPO_MAXIMO_EXECUCAO_POR_JOB).build();
		
		return new JanelaExecucao(
				LocalDateTime.of(2019, 11, 10, 9, 0), 
				LocalDateTime.of(2019, 11, 11, 12, 0), 
				Arrays.asList(job1, job2, job3)
			);
	}
	
	@Test(expected = DataMaximaConclusaoJobExcedidaException.class)
	public void quandoNaoEPossivelRespeitarADataMaximaConclusaoDoJob() {
		JanelaExecucao janela = criaJanelaComJobOndeNaoEPossivelRespeitarADataMaximaDeExecucaoDoJob();
		Agendador agendador = new Agendador(janela);
		agendador.getOrdensDeExecucao();
	}
	
	@Ignore
	private JanelaExecucao criaJanelaComJobOndeNaoEPossivelRespeitarADataMaximaDeExecucaoDoJob(){
		Job job1 = new Job.JobBuilder().id(1).dataMaximaConclusao(LocalDateTime.of(2019, 11, 10, 12, 0)).tempoEstimado(4).build();
		Job job2 = new Job.JobBuilder().id(1).dataMaximaConclusao(LocalDateTime.of(2019, 11, 10, 12, 0)).tempoEstimado(3).build();
		
		return new JanelaExecucao(
				LocalDateTime.of(2019, 11, 10, 9, 0), 
				LocalDateTime.of(2019, 11, 11, 12, 0), 
				Arrays.asList(job1, job2)
			);
	}
	
}
