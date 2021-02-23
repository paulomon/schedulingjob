package schedulingjob;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class JanelaExecucao {
	
	private LocalDateTime inicioJanela;
	private LocalDateTime fimJanela;
	private List<Job> jobs;
	
	public JanelaExecucao(LocalDateTime inicioJanela, LocalDateTime fimJanela, List<Job> jobs) {
		this.inicioJanela = inicioJanela;
		this.fimJanela = fimJanela;
		this.jobs = jobs;
	}
	
	public boolean possuiJobs() {
		return !jobs.isEmpty();
	}
	
	private long getTempoDisponivel() {
		return Duration.between(inicioJanela, fimJanela).toHours();
	}

	public boolean janelaPossuiTempoDisponivelParaTodosJobs() {
		long tempoTotalEstimadoJobs = getTempoTotalEstimadoJobs();
		long tempoDisponivel = getTempoDisponivel();
		
		return tempoDisponivel >= tempoTotalEstimadoJobs;
	}

	private long getTempoTotalEstimadoJobs() {
		return jobs.stream().mapToLong(j -> j.getTempoEstimado()).sum();
	}

	public boolean jobsPodemSerExecutadosRespeitandoSuaDataMaximaExecucao() {
		List<Job> jobsOrdenados = getJobsOrdenadorPorDataMaximaConclusao();
		
		LocalDateTime dataTerminoExecucao = inicioJanela; 
		
		for(Job job : jobsOrdenados) {
			Duration tempoEstimadoDeExecucao = Duration.ofHours(job.getTempoEstimado());
			dataTerminoExecucao = dataTerminoExecucao.plus(tempoEstimadoDeExecucao);
			
			if(dataTerminoExecucao.isAfter(job.getDataMaximaConclusao())) {
				return false;
			}
		}
		
		return true;
	}
	
	
	public List<Job> getJobsOrdenadorPorDataMaximaConclusao() {
		List<Job> retorno = new ArrayList<Job>(jobs);
		
		Collections.sort(retorno, (j1, j2) -> {
			return j1.getDataMaximaConclusao().compareTo(j2.getDataMaximaConclusao());	
		});
		
		return retorno;
	}
}
