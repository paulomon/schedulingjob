package schedulingjob;

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
}
