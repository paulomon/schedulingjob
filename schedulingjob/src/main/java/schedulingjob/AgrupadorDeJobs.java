package schedulingjob;

import static schedulingjob.Agendador.TEMPO_MAXIMO_EXECUCAO_POR_JOB;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class AgrupadorDeJobs {
	
	private final List<Job> jobs = new ArrayList<Job>();
	
	public boolean adicionaJobSePossuirTempo(Job job) {
		
		if(incluirTempoEstimadoExcedeTempoMaximo(job.getTempoEstimado())) {
			return false;
		}
		
		jobs.add(job);
		
		return true;
	}

	private boolean incluirTempoEstimadoExcedeTempoMaximo(long tempoEstimado) {
		long tempoTotalExecucaoAtual = jobs.stream().mapToLong(j -> j.getTempoEstimado()).sum();
		
		if((tempoEstimado + tempoTotalExecucaoAtual) > TEMPO_MAXIMO_EXECUCAO_POR_JOB) {
			return true;
		}
		
		return false;
	}

	public List<Long> gerarListaDeJobsELimpar() {
		List<Long> ids = jobs.stream().mapToLong(j -> j.getId()).boxed().collect(Collectors.toList());
		jobs.clear();
		
		return ids;
	}
	
	public boolean possuiJobs() {
		return !jobs.isEmpty();
	}
}
