package schedulingjob;

import java.time.Duration;
import java.time.LocalDateTime;
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
	
	public List<Job> getJobs(){
		return jobs;
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
}
