package schedulingjob;

import java.time.LocalDateTime;
import java.util.List;

public class Agendamento {
	
	private LocalDateTime inicioJanela;
	private LocalDateTime fimJanela;
	private List<Job> jobs;
	
	public Agendamento(LocalDateTime inicioJanela, LocalDateTime fimJanela, List<Job> jobs) {
		this.inicioJanela = inicioJanela;
		this.fimJanela = fimJanela;
		this.jobs = jobs;
	}
}
