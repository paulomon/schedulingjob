package schedulingjob;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Agendador {
	
	public static final long TEMPO_MAXIMO_EXECUCAO_POR_JOB = 8;
	
	private final JanelaExecucao janela;
	private final AgrupadorDeJobs agrupadorDeJobs;
	
	public Agendador(final JanelaExecucao janela) {
		this.janela = janela;
		this.agrupadorDeJobs = new AgrupadorDeJobs();
	}

	public List<List<Long>> getOrdensDeExecucao() {
		if(!janela.possuiJobs()) {
			return Collections.emptyList();
		}
		
		if(!janela.janelaPossuiTempoDisponivelParaTodosJobs()) {
			throw new TempoMaximoJanelaExcedidoException();
		}
		
		return calcularOrdens();
	}

	private List<List<Long>> calcularOrdens() {
		
		List<List<Long>> agrupamentoDeConjuntosDeJobs = new ArrayList<List<Long>>();
		
		for(Job job : getJobsOrdenadorPorDataMaximaConclusao()) {
			validarTempoMaximoExecucaoDoJob(job);			
			agruparJob(agrupamentoDeConjuntosDeJobs, job);
		}
		
		if(agrupadorDeJobs.possuiJobs()) {
			agrupamentoDeConjuntosDeJobs.add(agrupadorDeJobs.gerarListaDeJobsELimpar());
		}
		
		return agrupamentoDeConjuntosDeJobs;
	}

	private void agruparJob(List<List<Long>> agrupamentoDeConjuntosDeJobs, Job job) {
		boolean jobAdicionado = agrupadorDeJobs.adicionaJobSePossuirTempo(job);
		
		if(!jobAdicionado) {
			agrupamentoDeConjuntosDeJobs.add(agrupadorDeJobs.gerarListaDeJobsELimpar());
			agrupadorDeJobs.adicionaJobSePossuirTempo(job);
		}
	}

	private List<Job> getJobsOrdenadorPorDataMaximaConclusao() {
		List<Job> jobs = janela.getJobs();
		
		Collections.sort(jobs, (j1, j2) -> {
			return j1.getDataMaximaConclusao().compareTo(j2.getDataMaximaConclusao());	
		});
		
		return jobs;
	}

	private void validarTempoMaximoExecucaoDoJob(Job job) {
		if(!job.tempoEstimadoDentroDoLimite(TEMPO_MAXIMO_EXECUCAO_POR_JOB)) {
			throw new TempoMaximoExecucaoPorJobExcedidoException();
		}
	}

}
