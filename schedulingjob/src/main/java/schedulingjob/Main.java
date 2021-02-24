package schedulingjob;

import java.time.LocalDateTime;
import java.util.Arrays;

public class Main {
	
	public static void main(String ...args) {
		Job job1 = new Job.JobBuilder().id(1).dataMaximaConclusao(LocalDateTime.of(2019, 11, 10, 12, 0)).tempoEstimado(2).build();	
		Job job2 = new Job.JobBuilder().id(2).dataMaximaConclusao(LocalDateTime.of(2019, 11, 11, 12, 0)).tempoEstimado(4).build();
		Job job3 = new Job.JobBuilder().id(3).dataMaximaConclusao(LocalDateTime.of(2019, 11, 11, 8, 0)).tempoEstimado(6).build();
		
		JanelaExecucao janelaExecucao = new JanelaExecucao(
				LocalDateTime.of(2019, 11, 10, 9, 0), 
				LocalDateTime.of(2019, 11, 11, 12, 0), 
				Arrays.asList(job1, job2, job3)
			);
		
		Agendador agendador = new Agendador(janelaExecucao);
		System.out.println(agendador.getOrdensDeExecucao());
	}

}
